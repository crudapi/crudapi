package cn.crudapi.security.authentication;

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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.crudapi.security.exception.VerificationCodeException;
import cn.crudapi.security.service.CaptchaService;
import cn.crudapi.security.service.impl.UserDetailsServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 短信登陆鉴权 Provider，要求实现 AuthenticationProvider 接口
 * @author jitwxs
 * @since 2019/1/9 13:59
 */
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {
	private static final Logger log = LoggerFactory.getLogger(SmsCodeAuthenticationProvider.class);
	
	private UserDetailsService userDetailsService;
    
	private CaptchaService captchaService;
	
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken authenticationToken = (SmsCodeAuthenticationToken) authentication;

        String mobile = (String) authenticationToken.getPrincipal();

        checkSmsCode(mobile);

        UserDetailsServiceImpl userDetailsServiceImpl = (UserDetailsServiceImpl)userDetailsService;
        UserDetails userDetails = userDetailsServiceImpl.loadOrCreateUserByMobile(mobile);
        check(userDetails);
        
        // 此时鉴权成功后，应当重新 new 一个拥有鉴权的 authenticationResult 返回
        SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(userDetails, userDetails.getAuthorities());

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

    private void checkSmsCode(String mobile) {
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String inputCode = request.getParameter("smsCode");
    	captchaService.checkSmsCode(mobile, inputCode, request.getSession());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 判断 authentication 是不是 SmsCodeAuthenticationToken 的子类或子接口
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

	public CaptchaService getCaptchaService() {
		return captchaService;
	}

	public void setCaptchaService(CaptchaService captchaService) {
		this.captchaService = captchaService;
	}
}
