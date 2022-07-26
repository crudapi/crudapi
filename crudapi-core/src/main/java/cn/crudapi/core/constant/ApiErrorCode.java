package cn.crudapi.core.constant;

import org.springframework.http.HttpStatus;

public class ApiErrorCode {
    public static final String DEFAULT_ERROR = "DEFAULT_ERROR";
    public static final String VALIDATED_ERROR = "VALIDATED_ERROR";
    public static final String REST_ERROR = "REST_ERROR";
    public static final String JSON_ERROR = "JSON_ERROR";
    public static final String SQL_ERROR = "SQL_ERROR";
    
    public static final String TABLE_NAME_NOT_EMPTY = "TABLE_NAME_NOT_EMPTY";
    public static final String TABLE_COUNT_ERROR = "TABLE_COUNT_ERROR";
    public static final String TABLE_RESOURCE_NAME_NOT_EMPTY = "TABLE_RESOURCE_NAME_NOT_EMPTY";
    public static final String DUPLICATE_TABLE_NAME = "DUPLICATE_TABLE_NAME";
    public static final String TABLE_ENGINE_NOT_EMPTY = "TABLE_ENGINE_NOT_EMPTY";
    public static final String COLUMN_NOT_EMPTY = "COLUMN_NOT_EMPTY";
    public static final String COLUMN_NAME_NOT_EMPTY = "COLUMN_NAME_NOT_EMPTY";
    public static final String COLUMN_TYPE_NOT_EMPTY = "COLUMN_TYPE_NOT_EMPTY";
    public static final String DUPLICATE_COLUMN_NAME = "DUPLICATE_COLUMN_NAME";
    public static final String DUPLICATE_COLUMN_CAPTION = "DUPLICATE_COLUMN_CAPTION";
    public static final String INDEX_NAME_NOT_EMPTY = "INDEX_NAME_NOT_EMPTY";
    public static final String INDEX_TYPE_NOT_EMPTY = "INDEX_TYPE_NOT_EMPTY";
    public static final String INDEX_LINE_NOT_EMPTY = "INDEX_LINE_NOT_EMPTY";
    public static final String COLUMN_LENGTH_INVALID = "COLUMN_LENGTH_INVALID";
    public static final String COLUMN_AUTO_INC_MUST_PRIMARY = "COLUMN_AUTO_INC_MUST_PRIMARY";
    public static final String PRIMARY_MUST_NOT_NULL = "PRIMARY_MUST_NOT_NULL";
    public static final String PRIMARY_INDEX_NAME_MUST_EMPTY = "PRIMARY_INDEX_NAME_MUST_EMPTY";
    public static final String PRIMARY_COUNT_GT_ONE = "PRIMARY_COUNT_GT_ONE";
    public static final String DUPLICATE_INDEX_NAME = "DUPLICATE_INDEX_NAME";

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
