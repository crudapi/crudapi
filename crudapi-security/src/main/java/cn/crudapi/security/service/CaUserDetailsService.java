package cn.crudapi.security.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import cn.crudapi.core.dto.UserDTO;

public interface CaUserDetailsService {
	UserDetails loadUserByOpenId(String openId);

	String create(Map<String, Object> map);

	UserDTO get(String id);

	void delete(String id);

	void delete(List<String> ids);

	UserDetails loadOrCreateUserByMobile(String mobile) throws UsernameNotFoundException;

	UserDetails loadOrCreateUserByUsername(String username, String rolename);
}
