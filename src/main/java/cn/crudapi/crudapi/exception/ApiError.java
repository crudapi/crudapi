package cn.crudapi.crudapi.exception;


public class ApiError {
	private String exception;
	private String code;
	private String message;
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ApiError(String exception, String code, String message) {
		super();
		this.exception = exception;
		this.code = code;
		this.message = message;
	}
}
