package cn.crudapi.security.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;

public class CaWebAuthenticationDetails extends WebAuthenticationDetails {
	private static final Logger log = LoggerFactory.getLogger(CaWebAuthenticationDetails.class);
	
	private static final long serialVersionUID = 1L;
	private boolean imageCodeIsRight;
	
	public boolean getImageCodeIsRight() {
		return this.imageCodeIsRight;
	}
	
	public CaWebAuthenticationDetails(HttpServletRequest request) {
		super(request);
		
		String imageCode = request.getParameter("captcha");
		HttpSession session = request.getSession();
		String savedImageCode = (String)session.getAttribute("captcha");
		
		log.info("imageCode=" + imageCode);
		log.info("savedImageCode=" + savedImageCode);
		
		if (!StringUtils.isEmpty(imageCode)) {
			session.removeAttribute("captcha");
			
			if (!StringUtils.isEmpty(imageCode) 
				&& imageCode.equals(savedImageCode)) {
				this.imageCodeIsRight = true;
			}
		}
		
		this.imageCodeIsRight = true;
	}
}
