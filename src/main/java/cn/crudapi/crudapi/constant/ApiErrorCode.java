package cn.crudapi.crudapi.constant;

import org.springframework.http.HttpStatus;

public class ApiErrorCode {
    public static final String DEFAULT_ERROR = "DEFAULT_ERROR";
    public static final String VALIDATED_ERROR = "VALIDATED_ERROR";
    public static final String REST_ERROR = "REST_ERROR";
    public static final String JSON_ERROR = "JSON_ERROR";
    public static final String SQL_ERROR = "SQL_ERROR";
    
    public static final String API_RESOURCE_NOT_FOUND = "API_RESOURCE_NOT_FOUND";
    public static final String AUTH_UNAUTHORIZED = "UNAUTHORIZED";
    public static final String AUTH_FORBIDDEN = "FORBIDDEN";
    public static final String AUTH_ERROR = "AUTH_ERROR";
    
    public static HttpStatus getHttpStatus(String code) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        switch (code) {
	        case API_RESOURCE_NOT_FOUND:
	            httpStatus = HttpStatus.NOT_FOUND;
	            break;
	        case AUTH_UNAUTHORIZED:
	            httpStatus = HttpStatus.UNAUTHORIZED;
	            break;
	        case AUTH_FORBIDDEN:
	            httpStatus = HttpStatus.FORBIDDEN;
	            break;
	        default:
	            break;
        }

        return httpStatus;
    }

    public static String getMessage(String code) {
        return code;
    }

    private ApiErrorCode() {

    }
}
