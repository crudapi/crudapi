package cn.crudapi.security.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import cn.crudapi.security.exception.VerificationCodeException;
import cn.crudapi.security.service.CaptchaService;


@Service
public class CaptchaServiceImpl implements CaptchaService {
	private static final Logger log = LoggerFactory.getLogger(CaptchaServiceImpl.class);
	
	@Override
	public void checkSmsCode(String mobile, String code, HttpSession session) {
		try {
	        @SuppressWarnings("unchecked")
			Map<String, Object> smsCode = (Map<String, Object>)session.getAttribute("smsCode");
	        if(smsCode == null) {
	            throw new BadCredentialsException("未检测到申请验证码");
	        }
	        
	        if (mobile == null) {
	            throw new BadCredentialsException("手机号码不能为空");
	        }
	        
	        if (code == null) {
	            throw new BadCredentialsException("验证码不能为空");
	        }

	        String applyMobile = (String) smsCode.get("mobile");
	        int applyCode = (int) smsCode.get("code");
	        if(!applyMobile.equals(mobile)) {
	            throw new BadCredentialsException("申请的手机号码与登录手机号码不一致");
	        }
	       
	        if(applyCode != Integer.parseInt(code)) {
	            throw new BadCredentialsException("验证码错误");
	        }
	        
	        session.removeAttribute("smsCode");
    	} catch (Exception exception) {
    		 log.error("exception.getMessage", exception);
    		 throw new VerificationCodeException(exception.getMessage());
    	}
	}

	@Override
	public void sendSmsCode(String mobile, HttpSession session) {
		int code = (int) Math.ceil(Math.random() * 9000 + 1000);

	    Map<String, Object> map = new HashMap<>(16);
	    map.put("mobile", mobile);
	    map.put("code", code);

	    session.setAttribute("smsCode", map);

	    log.info("默认 {}：为 {} 设置短信验证码：{}", session.getId(), mobile, code);
	}
}
