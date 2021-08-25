package cn.crudapi.core.handler;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.crudapi.core.constant.ApiErrorCode;
import cn.crudapi.core.error.ApiError;
import cn.crudapi.core.exception.BusinessException;
import cn.crudapi.core.util.SqlExceptionUtil;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	  
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public ResponseEntity<ApiError> businessErrorHandler(HttpServletRequest req, BusinessException e) {
    	log.error("businessErrorHandler", e);
    	log.error("BusinessException Handler Host {} invokes url {} ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getMessage());

        ApiError apiError = new ApiError(e.getClass().getName(), e.getCode(), e.getMessage());

        return new ResponseEntity<ApiError>(apiError, ApiErrorCode.getHttpStatus(e.getCode()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ApiError> bindException(HttpServletRequest req, MethodArgumentNotValidException e) {
    	log.error("bindException", e);
    	log.error("bindException Handler Host {} invokes url {} ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getMessage());

        ApiError apiError = new ApiError(e.getClass().getName(), ApiErrorCode.VALIDATED_ERROR, e.getMessage());

        return new ResponseEntity<ApiError>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
    	log.error("defaultErrorHandler", e);
        log.error("DefaultException Handler Host {} invokes url {} ERROR: {}", req.getRemoteHost(), req.getRequestURL(), e.getMessage());

        ApiError apiError = new ApiError(e.getClass().getName(), ApiErrorCode.DEFAULT_ERROR, SqlExceptionUtil.parse(e.getMessage()));

        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
        	 return new ResponseEntity<ApiError>(apiError, HttpStatus.NOT_FOUND);
        } else {
        	 return new ResponseEntity<ApiError>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}