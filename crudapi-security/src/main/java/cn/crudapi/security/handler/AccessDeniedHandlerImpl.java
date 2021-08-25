package cn.crudapi.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import cn.crudapi.core.constant.ApiErrorCode;
import cn.crudapi.core.error.ApiError;
import cn.crudapi.core.util.JsonUtils;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
	private static final Logger log = LoggerFactory.getLogger(AccessDeniedHandlerImpl.class);
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		log.info("AccessDeniedHandlerImpl handle");
		
		response.setHeader("Cache-Control","no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
		response.setStatus(HttpStatus.FORBIDDEN.value());
		
		ApiError apiError = new ApiError(accessDeniedException.getMessage(), ApiErrorCode.AUTH_FORBIDDEN, "没有权限");
		PrintWriter out = response.getWriter();
		out.write(JsonUtils.toJson(apiError));
	}
}
