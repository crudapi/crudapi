package cn.crudapi.security.exception;

import org.springframework.security.core.AuthenticationException;

public class VerificationCodeException extends AuthenticationException {
	private static final long serialVersionUID = 1L;

	public VerificationCodeException() {
		super("图像验证码校验失败");
	}
	
	public VerificationCodeException(String msg) {
		super(msg);
	}
}
