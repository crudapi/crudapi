package cn.crudapi.crudapi.config.api;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import cn.crudapi.crudapi.config.datasource.DataSourceContextHolder;

@WebFilter(filterName = "headFilter", urlPatterns = "/*")
public class HeadFilter extends OncePerRequestFilter {
	private static final Logger log = LoggerFactory.getLogger(HeadFilter.class);
	
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    	HeadRequestWrapper headRequestWrapper = new HeadRequestWrapper(request);
    	
    	
    	String dataSource = "primary";
    	if (!"/api/auth/login".equals(request.getRequestURI())
    		&& !"/api/auth/jwt/login".equals(request.getRequestURI())
    		&& !"/api/auth/logout".equals(request.getRequestURI())
    		&& !"/api/crudapi/system/data-sources".equals(request.getRequestURI())) {
    		dataSource = request.getParameter("dataSource");
        	if (ObjectUtils.isEmpty(dataSource)) {
        		dataSource = headRequestWrapper.getHeader("dataSource");
                if (ObjectUtils.isEmpty(dataSource)) {
                	dataSource = "primary";
                }
            }
		} else {
		    log.info(request.getRequestURI() + ", use primary dataSource!");
		}
    	
    	headRequestWrapper.addHead("dataSource", dataSource);
    	DataSourceContextHolder.setHeaderDataSource(dataSource);
    	
        log.info(request.getRequestURI() + ", dataSource: " + dataSource);
        
        // finish
        filterChain.doFilter(headRequestWrapper, response);
    }
}