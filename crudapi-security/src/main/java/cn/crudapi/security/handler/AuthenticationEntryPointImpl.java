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
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import cn.crudapi.core.constant.ApiErrorCode;
import cn.crudapi.core.error.ApiError;
import cn.crudapi.core.util.JsonUtils;


@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
	private static final Logger log = LoggerFactory.getLogger(AuthenticationEntryPointImpl.class);
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		log.info("AuthenticationEntryPointImpl commence");
		
        response.setHeader("Cache-Control","no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		
		ApiError apiError = new ApiError(authException.getMessage(), ApiErrorCode.AUTH_UNAUTHORIZED, "未登陆");
		PrintWriter out = response.getWriter();
		out.write(JsonUtils.toJson(apiError));
	}
}
