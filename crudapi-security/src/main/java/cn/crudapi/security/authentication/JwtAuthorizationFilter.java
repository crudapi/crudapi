package cn.crudapi.security.authentication;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import cn.crudapi.security.dto.UserDTO;
import cn.crudapi.security.util.JwtUtil;


public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
	private static final Logger log = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
	
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String tokenHeader = request.getHeader(JwtUtil.TOKEN_HEADER);
        // 如果请求头中没有Authorization信息则直接放行了
        if (tokenHeader == null || !tokenHeader.startsWith(JwtUtil.TOKEN_PREFIX)) {
        	log.info("JwtAuthorizationFilter Authorization empty!");
            chain.doFilter(request, response);
            return;
        }
        
    	log.info("JwtAuthorizationFilter Authorization valid!");
    	
        // 如果请求头中有token，则进行解析，并且设置认证信息
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));
        super.doFilterInternal(request, response, chain);
    }

    // 这里从token中获取用户信息并新建一个token
    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {
        String token = tokenHeader.replace(JwtUtil.TOKEN_PREFIX, "");
        
        try {
        	String username = JwtUtil.getUsername(token);
            UserDTO userDTO = JwtUtil.getUserDTO(token);
            
            if (username != null){
                return new UsernamePasswordAuthenticationToken(userDTO, null, userDTO.getAuthorities());
            }
        } catch(Exception exception) {
            log.error(exception.getMessage());
        }
		return null;
    }
}
