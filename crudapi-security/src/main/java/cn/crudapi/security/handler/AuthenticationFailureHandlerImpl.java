package cn.crudapi.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import cn.crudapi.core.constant.ApiErrorCode;
import cn.crudapi.core.error.ApiError;
import cn.crudapi.core.util.JsonUtils;

@Component
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {
	private static final Logger log = LoggerFactory.getLogger(AuthenticationFailureHandlerImpl.class);
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		log.info("AuthenticationFailureHandlerImpl onAuthenticationFailure");
		
		response.setHeader("Cache-Control","no-cache");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json");
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		
		ApiError apiError = new ApiError(exception.getMessage(), ApiErrorCode.AUTH_ERROR, "登录失败, " + exception.getMessage());
		PrintWriter out = response.getWriter();
		out.write(JsonUtils.toJson(apiError));
	}
}
