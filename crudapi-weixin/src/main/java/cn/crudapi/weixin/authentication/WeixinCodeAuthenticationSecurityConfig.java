package cn.crudapi.weixin.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import cn.crudapi.weixin.service.WeixinService;
import cn.crudapi.security.service.CaUserDetailsService;


@Component
public class WeixinCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Autowired
    private CaUserDetailsService userDetailsService;
  
    @Autowired
	private WeixinService weixinService;
	
    @Autowired
	private AuthenticationFailureHandler myAuthenticationFailureHandler;
	
	@Autowired
	private AuthenticationSuccessHandler myAuthenticationSuccessHandler;
	
	
    @Override
    public void configure(HttpSecurity http) throws Exception {
        WeixinCodeAuthenticationFilter weixinCodeAuthenticationFilter = new WeixinCodeAuthenticationFilter();
        weixinCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        weixinCodeAuthenticationFilter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        weixinCodeAuthenticationFilter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
     
        WeixinCodeAuthenticationProvider weixinCodeAuthenticationProvider = new WeixinCodeAuthenticationProvider();
        weixinCodeAuthenticationProvider.setUserDetailsService(userDetailsService);
        weixinCodeAuthenticationProvider.setWeixinService(weixinService);
        
        http.authenticationProvider(weixinCodeAuthenticationProvider)
                .addFilterAfter(weixinCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
