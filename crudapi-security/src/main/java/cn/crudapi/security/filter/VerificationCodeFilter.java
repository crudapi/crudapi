package cn.crudapi.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import cn.crudapi.security.exception.VerificationCodeException;
import cn.crudapi.security.handler.AuthenticationFailureHandlerImpl;

public class VerificationCodeFilter extends OncePerRequestFilter {
	private AuthenticationFailureHandler authenticationFailureHandler = new AuthenticationFailureHandlerImpl();
	
	public void verificationCode(HttpServletRequest request) throws VerificationCodeException {
		String requestCode = request.getParameter("captcha");
		HttpSession session = request.getSession();
		String savedCode = (String)session.getAttribute("captcha");
		if (!StringUtils.isEmpty(requestCode)) {
			session.removeAttribute("captcha");
		} 
		
		if (StringUtils.isEmpty(requestCode) 
			|| StringUtils.isEmpty(savedCode)
			|| !requestCode.equals(savedCode)) {
			throw new VerificationCodeException();
		}
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (!"/api/auth/login".equals(request.getRequestURI())) {
			filterChain.doFilter(request, response);
		} else {
			try {
				verificationCode(request);
				filterChain.doFilter(request, response);
			} catch (VerificationCodeException e) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, e);
			}
		}
	}
}
