package cn.crudapi.security.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.crudapi.core.util.JsonUtils;
import cn.crudapi.core.dto.UserDTO;
import cn.crudapi.security.service.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {
	private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);
	
	public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
	
	public static final long EXPIRITION = 90L * 24 * 60 * 60 * 1000; //90 days

	public static final String APPSECRET_KEY = "crudapi_secret_!@#$%^&*~=0123456789";

	private static final String USERNAME_CLAIMS = "username";
	
    private static final String OBJECT_CLAIMS = "object";
    
	/**
	 * 生成token
	 * @param username
	 * @param role
	 * @return
	 */
	public static String createToken(String username, Object obj) {
		String objJson = JsonUtils.toJson(obj);
		
		long now = System.currentTimeMillis();
		long exp = System.currentTimeMillis() + EXPIRITION;
		
		log.info("now = " + now + ", EXPIRITION = " + EXPIRITION + ", exp = " + exp);
		log.info(objJson);
		
		Map<String,Object> map = new HashMap<>();	
		map.put(OBJECT_CLAIMS, objJson);
		
		String token = Jwts
				.builder()
				.setSubject(username)
				.setClaims(map)
				.claim(USERNAME_CLAIMS, username)
				.setIssuedAt(new Date())
				.setExpiration(new Date(exp))
				.signWith(SignatureAlgorithm.HS256, APPSECRET_KEY).compact();
		return token;
	}

	public static Claims checkJWT(String token) {
		try {
			final Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
			return claims;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取用户名
	 * @param token
	 * @return
	 */
	public static String getUsername(String token){
    	Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
    	return claims.get(USERNAME_CLAIMS).toString();
    }
	
	/**
	 * 获取用户角色
	 * @param token
	 * @return
	 */
	public static UserDTO getUserDTO(String token) {
    	Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
    	String jsonStr = claims.get(OBJECT_CLAIMS).toString();
    	log.info(jsonStr);
    	return JsonUtils.toObject(jsonStr, UserDTO.class);
    }
    
    /**
     * 是否过期
     * @param token
     * @return
     */
    public static boolean isExpiration(String token){
    	Claims claims = Jwts.parser().setSigningKey(APPSECRET_KEY).parseClaimsJws(token).getBody();
    	return claims.getExpiration().before(new Date());
    }
}
