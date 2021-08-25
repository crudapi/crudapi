package cn.crudapi.security.service;

import javax.servlet.http.HttpSession;

public interface CaptchaService {
	void checkSmsCode(String mobile, String code, HttpSession session);
	
	void sendSmsCode(String mobile, HttpSession session);
}
