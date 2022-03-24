package cn.crudapi.security.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class DynamicSecurityFilter extends AbstractSecurityInterceptor implements Filter {
	private static final Logger log = LoggerFactory.getLogger(DynamicSecurityFilter.class);
	
    @Autowired
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;
    
    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Autowired
    public void setMyAccessDecisionManager(DynamicAccessDecisionManager dynamicAccessDecisionManager) {
        super.setAccessDecisionManager(dynamicAccessDecisionManager);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        FilterInvocation fi = new FilterInvocation(servletRequest, servletResponse, filterChain);
        
        String reqUrl =  request.getRequestURI();
        String reqMethod =  request.getMethod();
        
        //OPTIONS请求直接放行
        if(reqMethod.equals(HttpMethod.OPTIONS.toString())){
        	log.debug("DynamicSecurityFilter doFilter OPTIONS passed!");
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            return;
        }
        
        //白名单请求直接放行
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String ignoreUrl : ignoreUrlsConfig.getUrls()) {
        	String reqPath = reqUrl;
        	
        	String[] ignoreUrlArray = ignoreUrl.split(",");
        	if (ignoreUrlArray.length > 1) {
        		reqPath = reqUrl + "," + reqMethod;
        	} 
        	
        	if(pathMatcher.match(ignoreUrl, reqPath)){
        		log.info("DynamicSecurityFilter doFilter " + reqPath + " try match " + ignoreUrl + " passed!");
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
                return;
            } else {
            	log.debug("DynamicSecurityFilter doFilter " + reqPath + " try match " + ignoreUrl + " continued!");
            }
        }
        //此处会调用AccessDecisionManager中的decide方法进行鉴权操作
        InterceptorStatusToken token = super.beforeInvocation(fi);
        
        log.debug("DynamicSecurityFilter getChain doFilter...");
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return dynamicSecurityMetadataSource;
    }

}
