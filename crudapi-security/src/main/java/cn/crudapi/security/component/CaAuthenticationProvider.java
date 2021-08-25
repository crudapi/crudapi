package cn.crudapi.security.component;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import cn.crudapi.security.exception.VerificationCodeException;

@Component
public class CaAuthenticationProvider extends DaoAuthenticationProvider {

	public CaAuthenticationProvider(UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		this.setUserDetailsService(userDetailsService);
		this.setPasswordEncoder(passwordEncoder);
	}
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		if (authentication.getDetails() instanceof CaWebAuthenticationDetails) {
			CaWebAuthenticationDetails details = (CaWebAuthenticationDetails)authentication.getDetails();
			if (details == null || !details.getImageCodeIsRight()) {
				throw new VerificationCodeException();
			}
		}
		
		super.additionalAuthenticationChecks(userDetails, authentication);
	}
}
