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
import cn.crudapi.crudapi.constant.DataSourceConsts;

@WebFilter(filterName = "headFilter", urlPatterns = "/*")
public class HeadFilter extends OncePerRequestFilter {
    private static final String PARAM_DATASOURCE = "dataSource";

	private static final Logger log = LoggerFactory.getLogger(HeadFilter.class);
	
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    	HeadRequestWrapper headRequestWrapper = new HeadRequestWrapper(request);
    	
    	
    	String dataSource = DataSourceConsts.DEFAULT;
    	if (!"/api/auth/login".equals(request.getRequestURI())
    		&& !"/api/auth/jwt/login".equals(request.getRequestURI())
    		&& !"/api/auth/logout".equals(request.getRequestURI())
    		&& !request.getRequestURI().startsWith("/api/crudapi/system/")) {
    		dataSource = request.getParameter(PARAM_DATASOURCE);
        	if (ObjectUtils.isEmpty(dataSource)) {
        		dataSource = headRequestWrapper.getHeader(PARAM_DATASOURCE);
                if (ObjectUtils.isEmpty(dataSource)) {
                	dataSource = DataSourceConsts.DEFAULT;
                }
            }
		} else {
		    log.info(request.getRequestURI() + ", use default dataSource!");
		}
    	
    	headRequestWrapper.addHead(PARAM_DATASOURCE, dataSource);
    	DataSourceContextHolder.setHeaderDataSource(dataSource);
    	
        log.info(request.getRequestURI() + ", dataSource: " + dataSource);
        
        // finish
        filterChain.doFilter(headRequestWrapper, response);
    }
}