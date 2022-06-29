package cn.crudapi.security.component;

import cn.hutool.core.collection.CollUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Component
public class DynamicAccessDecisionManager implements AccessDecisionManager {
	private static final Logger log = LoggerFactory.getLogger(DynamicAccessDecisionManager.class);
	
    @Override
    public void decide(Authentication authentication, Object object,
                       Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        // 当接口未被配置资源时直接放行
        if (CollUtil.isEmpty(configAttributes)) {
        	log.info("empty configAttributes decide passed！");
            return;
        }
        
        for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            if (grantedAuthority.getAuthority().equals("ROLE_SUPER_ADMIN")) {
            	log.info("ROLE_SUPER_ADMIN decide passed！");
                return;
            }
        }
        
        FilterInvocation fi = (FilterInvocation) object;
        
        String method = fi.getRequest().getMethod();
        log.info("decide method = " + method);
        
        List<String> needAuthorityList = new ArrayList<String>();
        
        Iterator<ConfigAttribute> iterator = configAttributes.iterator();
        Boolean isAuth = null;
        while (iterator.hasNext()) {
        	Boolean isPassed = false;
            ConfigAttribute configAttribute = iterator.next();
            //将访问所需资源或用户拥有资源进行比对
            String needAuthority = configAttribute.getAttribute();
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                if (grantedAuthority.getAuthority().equals("ROLE_SUPER_ADMIN")
                	|| needAuthority.trim().equals(grantedAuthority.getAuthority())) {
                	isPassed = true;
                }
            }
            if (!isPassed) {
                needAuthorityList.add(needAuthority);
            }
            log.info(needAuthority + " = " + isPassed);
            
            if (isAuth == null) {
            	isAuth = isPassed;
            } else {
            	isAuth |= isPassed;
            }
        }
        
        if (!isAuth) {
        	 throw new AccessDeniedException("对不起，您没有资源：" + String.join(",", needAuthorityList) +"的访问权限！");
        }
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
