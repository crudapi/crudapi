package cn.crudapi.security.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cn.crudapi.core.dto.UserDTO;
import cn.crudapi.security.util.JwtUtil;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
	public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

	private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
	private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
	
	private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, 
    		AuthenticationSuccessHandler myAuthenticationSuccessHandler,
    		AuthenticationFailureHandler myAuthenticationFailureHandler) {
        this.authenticationManager = authenticationManager;
        setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        setAuthenticationFailureHandler(myAuthenticationFailureHandler);
     
        super.setFilterProcessesUrl("/api/auth/jwt/login");
    }

	@Nullable
	protected String obtainPassword(HttpServletRequest request) {
		return request.getParameter(passwordParameter);
	}

	@Nullable
	protected String obtainUsername(HttpServletRequest request) {
		return request.getParameter(usernameParameter);
	}
	
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        // 从输入流中获取到登录的信息
    	String username = obtainUsername(request);
		String password = obtainPassword(request);

		if (username == null) {
			username = "";
		}

		if (password == null) {
			password = "";
		}

		username = username.trim();
		
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);
		return authenticationManager.authenticate(authRequest);
    }

    // 成功验证后调用的方法
    // 如果验证成功，就生成token并返回
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

