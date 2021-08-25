package cn.crudapi.weixin.authentication;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 短信登录的鉴权过滤器，模仿 UsernamePasswordAuthenticationFilter 实现
 * @author jitwxs
 * @since 2019/1/9 13:52
 */
public class WeixinCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    /**
     * form表单中手机号码的字段name
     */
    public static final String SPRING_SECURITY_FORM_WEIXIN_KEY = "code";

    private String codeParameter = SPRING_SECURITY_FORM_WEIXIN_KEY;
    /**
     * 是否仅 POST 方式
     */
    private boolean postOnly = true;

    public WeixinCodeAuthenticationFilter() {
        // 短信登录的请求 post 方式的 /sms/login
        super(new AntPathRequestMatcher("/api/auth/weixin/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String code = obtainWeixinCode(request);

        if (code == null) {
        	code = "";
        }

        code = code.trim();

        WeixinCodeAuthenticationToken authRequest = new WeixinCodeAuthenticationToken(code);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected String obtainWeixinCode(HttpServletRequest request) {
        return request.getParameter(codeParameter);
    }

    protected void setDetails(HttpServletRequest request, WeixinCodeAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public String getCodeParameter() {
        return codeParameter;
    }

    public void setCodeParameter(String codeParameter) {
        Assert.hasText(codeParameter, "Code parameter must not be empty or null");
        this.codeParameter = codeParameter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }
}

