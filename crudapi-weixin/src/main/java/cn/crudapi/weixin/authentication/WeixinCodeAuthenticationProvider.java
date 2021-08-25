package cn.crudapi.weixin.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.crudapi.security.exception.VerificationCodeException;
import cn.crudapi.security.service.CaUserDetailsService;
import cn.crudapi.weixin.service.WeixinService;

import javax.servlet.http.HttpServletRequest;

/**
 * 短信登陆鉴权 Provider，要求实现 AuthenticationProvider 接口
 * @author jitwxs
 * @since 2019/1/9 13:59
 */

public class WeixinCodeAuthenticationProvider implements AuthenticationProvider {
	private static final Logger log = LoggerFactory.getLogger(WeixinCodeAuthenticationProvider.class);
	
    private CaUserDetailsService userDetailsService;
    
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

	private WeixinService weixinService;
	
    public WeixinService getWeixinService() {
		return weixinService;
	}

	public void setWeixinService(WeixinService weixinService) {
		this.weixinService = weixinService;
	}

	@Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	WeixinCodeAuthenticationToken authenticationToken = (WeixinCodeAuthenticationToken) authentication;

        String code = (String) authenticationToken.getPrincipal();

        checkWeixinCode(code);
        
        String openId = null;
        try {
        	openId = weixinService.getOpenId(code);
        } catch (Exception exception) {
        	//openId = "ou6Dmt3Qsk51sGZGyOz-tOTVna9E";//"subscribe":0,
        	//openId = "ou6Dmt1nzc5jgS7qDSIRwTRTREhs";//"subscribe":1
        	throw new BadCredentialsException(exception.getMessage());
	   	}
        
        if (openId == null || openId.isEmpty() ) {
            throw new BadCredentialsException("微信openId不能为空");
        }
        
//        Boolean isSubscribe = false;
//        try {
//        	isSubscribe = weixinService.checkSubscribe(openId);
//        } catch (Exception exception) {
//        	throw new BadCredentialsException(exception.getMessage());
//	   	}
//        
//        if (!isSubscribe) {
//            //throw new BadCredentialsException("请先关注微信公众号");
//            System.out.println("请先关注微信公众号");
//        }
//        
        UserDetails userDetails = userDetailsService.loadUserByOpenId(openId);
        check(userDetails);
        
        // 此时鉴权成功后，应当重新 new 一个拥有鉴权的 authenticationResult 返回
        WeixinCodeAuthenticationToken authenticationResult = new WeixinCodeAuthenticationToken(userDetails, userDetails.getAuthorities());

        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }
    
    public void check(UserDetails user) {
		if (!user.isAccountNonLocked()) {
			log.debug("User account is locked");

			throw new LockedException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.locked",
					"User account is locked"));
		}

		if (!user.isEnabled()) {
			log.debug("User account is disabled");

			throw new DisabledException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.disabled",
					"User is disabled"));
		}

		if (!user.isAccountNonExpired()) {
			log.debug("User account is expired");

			throw new AccountExpiredException(messages.getMessage(
					"AbstractUserDetailsAuthenticationProvider.expired",
					"User account has expired"));
		}
	}

    private void checkWeixinCode(String code) {
    	try {
    		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	        
	        if (code == null || code.isEmpty() ) {
	            throw new BadCredentialsException("微信Code不能为空");
	        }
    	} catch (Exception exception) {
    		 log.error("exception.getMessage", exception);
    		 throw new VerificationCodeException(exception.getMessage());
    	}
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 判断 authentication 是不是 SmsCodeAuthenticationToken 的子类或子接口
        return WeixinCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public CaUserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(CaUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
