package cn.crudapi.security.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import cn.crudapi.security.dto.UserDTO;
import cn.crudapi.security.util.JwtUtil;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 短信登录的鉴权过滤器，模仿 UsernamePasswordAuthenticationFilter 实现
 * @author jitwxs
 * @since 2019/1/9 13:52
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	private static final Logger log = LoggerFactory.getLogger(SmsCodeAuthenticationFilter.class);
	
	/**
     * form表单中手机号码的字段name
     */
    public static final String SPRING_SECURITY_FORM_MOBILE_KEY = "mobile";

    private String mobileParameter = SPRING_SECURITY_FORM_MOBILE_KEY;
    /**
     * 是否仅 POST 方式
     */
    private boolean postOnly = true;

    public SmsCodeAuthenticationFilter() {
        // 短信登录的请求 post 方式的 /sms/login
        super(new AntPathRequestMatcher("/api/auth/mobile/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String mobile = obtainMobile(request);

        if (mobile == null) {
            mobile = "";
        }

        mobile = mobile.trim();

        SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(mobile);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParameter);
    }

    protected void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public String getMobileParameter() {
        return mobileParameter;
    }

    public void setMobileParameter(String mobileParameter) {
        Assert.hasText(mobileParameter, "Mobile parameter must not be empty or null");
        this.mobileParameter = mobileParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }
    
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        UserDTO jwtUser = (UserDTO) authResult.getPrincipal();
        log.info("jwtUser:" + jwtUser.toString());

        String token = JwtUtil.createToken(jwtUser.getUsername(), jwtUser);
        String tokenStr = JwtUtil.TOKEN_PREFIX + token;
        response.setHeader("token", tokenStr);
        
        this.getSuccessHandler().onAuthenticationSuccess(request, response, authResult);
    }
}

