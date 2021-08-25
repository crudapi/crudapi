package cn.crudapi.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import cn.crudapi.core.util.JsonUtils;


@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
	private static final Logger log = LoggerFactory.getLogger(AuthenticationSuccessHandlerImpl.class);
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		log.info("AuthenticationSuccessHandlerImpl onAuthenticationSuccess");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		    
		PrintWriter out = response.getWriter();
		out.write(JsonUtils.toJson(authentication));
	}
}
