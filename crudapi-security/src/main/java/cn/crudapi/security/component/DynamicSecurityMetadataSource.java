package cn.crudapi.security.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import cn.hutool.core.util.URLUtil;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	private static final Logger log = LoggerFactory.getLogger(DynamicSecurityMetadataSource.class);
	
    private static Map<String, ConfigAttribute> configAttributeMap = null;
    
    @Autowired
    private DynamicSecurityService dynamicSecurityService;

    @PostConstruct
    public void loadDataSource() {
        configAttributeMap = dynamicSecurityService.loadDataSource();
    }

    public void clearDataSource() {
    	log.info("DynamicSecurityMetadataSource clearDataSource");
        configAttributeMap.clear();
        configAttributeMap = null;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        if (configAttributeMap == null)  {
        	this.loadDataSource();
        }
        List<ConfigAttribute>  configAttributes = new ArrayList<>();
        
        FilterInvocation fi = (FilterInvocation) o;
        
        String method = fi.getRequest().getMethod();
        log.info("getAttributes method = " + method);
        
        //获取当前访问的路径
        String url = fi.getRequestUrl();
        log.info("getAttributes url = " + url);
        
        String path = URLUtil.getPath(url) + "_"+ method;
        
        log.info("getAttributes url = " + url);
        log.info("getAttributes path = " + path);
        
        PathMatcher pathMatcher = new AntPathMatcher();
        Iterator<String> iterator = configAttributeMap.keySet().iterator();
        //获取访问该路径所需资源
        while (iterator.hasNext()) {
            String pattern = iterator.next();
            //log.info("match = " + pattern);
            if (pathMatcher.match(pattern, path)) {
            	log.info("match success = " + pattern + ", " + path);
                configAttributes.add(configAttributeMap.get(pattern));
            }
        }
        // 未设置操作请求权限，返回空集合
        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
