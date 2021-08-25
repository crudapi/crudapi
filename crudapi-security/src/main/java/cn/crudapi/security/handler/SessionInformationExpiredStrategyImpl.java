package cn.crudapi.security.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import cn.crudapi.core.constant.ApiErrorCode;
import cn.crudapi.core.error.ApiError;
import cn.crudapi.core.util.JsonUtils;

@Component
public class SessionInformationExpiredStrategyImpl implements SessionInformationExpiredStrategy {
	private static final Logger log = LoggerFactory.getLogger(SessionInformationExpiredStrategyImpl.class);
	
	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
		log.info("SessionInformationExpiredStrategyImpl onInvalidSessionDetected");
		
		HttpServletResponse response = event.getResponse();
		
		response.setHeader("Cache-Control","no-cache");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json");
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		
		ApiError apiError = new ApiError("ExpiredSession", ApiErrorCode.AUTH_UNAUTHORIZED, "会话过期");
		PrintWriter out = response.getWriter();
		out.write(JsonUtils.toJson(apiError));
	}
}
