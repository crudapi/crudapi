package cn.crudapi.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import cn.crudapi.core.util.JsonUtils;


@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
	private static final Logger log = LoggerFactory.getLogger(LogoutSuccessHandlerImpl.class);
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		log.info("LogoutSuccessHandlerImpl onLogoutSuccess");
		
	    response.setHeader("Cache-Control","no-cache");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json");
	    
	    
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.write(JsonUtils.toJson(authentication));
	}
}
