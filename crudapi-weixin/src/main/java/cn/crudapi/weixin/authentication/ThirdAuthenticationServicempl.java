package cn.crudapi.weixin.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Service;

import cn.crudapi.security.service.ThirdAuthenticationService;

@Service
public class ThirdAuthenticationServicempl implements ThirdAuthenticationService {
	@Autowired
	private WeixinCodeAuthenticationSecurityConfig weixinCodeAuthenticationSecurityConfig;
	
	@Override
	public List<SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>> getSecurityConfigurerAdapters() {
		List<SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>> securityConfigurerAdapters = 
				new ArrayList<SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>>();
		
		securityConfigurerAdapters.add(weixinCodeAuthenticationSecurityConfig);
		
		return securityConfigurerAdapters;
	}
}
