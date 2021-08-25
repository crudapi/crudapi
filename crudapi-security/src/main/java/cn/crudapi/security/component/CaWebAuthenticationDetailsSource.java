package cn.crudapi.security.component;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@Component
public class CaWebAuthenticationDetailsSource implements 
	AuthenticationDetailsSource<HttpServletRequest, CaWebAuthenticationDetails> {

	@Override
	public CaWebAuthenticationDetails buildDetails(HttpServletRequest request) {
		return new CaWebAuthenticationDetails(request);
	}
}
