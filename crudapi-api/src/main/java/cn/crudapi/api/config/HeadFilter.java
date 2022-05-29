package cn.crudapi.api.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import cn.crudapi.core.datasource.config.DataSourceContextHolder;

@WebFilter(filterName = "headFilter", urlPatterns = "/*")
public class HeadFilter extends OncePerRequestFilter {
	private static final Logger log = LoggerFactory.getLogger(HeadFilter.class);
	
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    	if (!"/api/auth/login".equals(request.getRequestURI())
    		&& !"/api/auth/jwt/login".equals(request.getRequestURI())
    		&& !"/api/auth/logout".equals(request.getRequestURI())
    		&& !"/api/metadata/dataSources".equals(request.getRequestURI())) {
    		String dataSource = request.getParameter("dataSource");
        	HeadRequestWrapper headRequestWrapper = new HeadRequestWrapper(request);
        	if (StringUtils.isEmpty(dataSource)) {
        		dataSource = headRequestWrapper.getHeader("dataSource");
                if (StringUtils.isEmpty(dataSource)) {
                	dataSource = "primary";
                	headRequestWrapper.addHead("dataSource", dataSource);
                }
            }
        	
        	DataSourceContextHolder.setHeaderDataSource(dataSource);
        	
            log.info(request.getRequestURI() + ", dataSource: " + dataSource);
            
            // finish
            filterChain.doFilter(headRequestWrapper, response);
		} else {
		    log.info(request.getRequestURI() + ", skip set dataSource!");
			filterChain.doFilter(request, response);
		}
    }
}