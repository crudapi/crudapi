package cn.crudapi.crudapi.exception;

import java.text.MessageFormat;
import java.util.Arrays;

import cn.crudapi.crudapi.constant.ApiErrorCode;


public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String code;

    private Object[] args;

    public BusinessException(String code, Object... args) {
        super();
        this.code = code;
        this.args = args;
    }
    
    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}


	@Override
	public String toString() {
		return "BusinessException [code=" + code + ", args=" + Arrays.toString(args) + "]";
	}
	

	@Override
    public String getMessage() {
    	String  msg = ApiErrorCode.getMessage(code);
    	if (args.length > 0) {
    		 msg = MessageFormat.format("{0}", args);
    	}
       
        return msg;
    }
}
