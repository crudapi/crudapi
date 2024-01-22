package cn.crudapi.security.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.type.TypeReference;

import cn.crudapi.core.enumeration.ConditionTypeEnum;
import cn.crudapi.core.query.CompositeCondition;
import cn.crudapi.core.query.Condition;
import cn.crudapi.core.service.TableService;
import cn.crudapi.core.util.ConditionUtils;
import cn.crudapi.core.util.JsonUtils;
import cn.crudapi.core.dto.GrantedAuthorityDTO;
import cn.crudapi.core.dto.ResourceDTO;
import cn.crudapi.core.dto.RoleDTO;
import cn.crudapi.core.dto.TablePermissionDTO;
import cn.crudapi.core.dto.UserDTO;
import cn.crudapi.security.service.ResourceService;
import cn.crudapi.security.service.CaUserDetailsService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, CaUserDetailsService {
	private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	private static final String USER_TABLE_NAME = "user";
	private static final String ROLE_TABLE_NAME = "role";
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private TableService tableService;
	
	@Autowired
	private ResourceService resourceService;
	
	@SuppressWarnings("unchecked")
	public List<ResourceDTO> listResource(List<RoleDTO> roleDTOs) {
		List<ResourceDTO> resourceDTOs = new  ArrayList<ResourceDTO>();
		
		if (CollectionUtils.isEmpty(roleDTOs)) {
			return resourceDTOs;
		}
		
		List<Object> roleIdObjs = new ArrayList<Object>();
		for (RoleDTO role : roleDTOs) {
			roleIdObjs.add(role.getId());
		}
		
		Condition condition = ConditionUtils.toCondition("id", roleIdObjs);
    	List<Map<String, Object>> mapList = tableService.list(ROLE_TABLE_NAME, null, "resource", null, null, condition, null, null, null);
    	
    	
    	for (Map<String, Object> roleMap : mapList) {
    		Object resourceLinesObj = roleMap.get("resourceLines");
    		if (resourceLinesObj != null) {
    			List<Map<String, Object>> resourceLines = (List<Map<String, Object>>)resourceLinesObj;
    			for (Map<String, Object> resourceLine : resourceLines) {
    				Map<String, Object> resourceMap = (Map<String, Object>)resourceLine.get("resource");
    				
    				ResourceDTO resourceDTO = new ResourceDTO();
    				resourceDTO.setId(Long.parseLong(resourceMap.get("id").toString()));
    				resourceDTO.setCode(Objects.toString(resourceMap.get("code")));
    				resourceDTO.setName(Objects.toString(resourceMap.get("name")));
    				resourceDTO.setUrl(Objects.toString(resourceMap.get("url")));
    				resourceDTO.setAction(Objects.toString(resourceMap.get("action")));
    				resourceDTO.setRemark(Objects.toString(resourceMap.get("remark")));
    				
    				resourceDTOs.add(resourceDTO);
    			}
    		}
		}
		
    	return resourceDTOs;
	}
	
	@SuppressWarnings("unchecked")
	public List<TablePermissionDTO> listTablePermission(List<RoleDTO> roleDTOs) {
		List<TablePermissionDTO> tablePermissionDTOs = new ArrayList<TablePermissionDTO>();
		
		if (CollectionUtils.isEmpty(roleDTOs)) {
			return tablePermissionDTOs;
		}
		
		List<Object> roleIdObjs = new ArrayList<Object>();
		for (RoleDTO role : roleDTOs) {
			roleIdObjs.add(role.getId());
		}
		
		Condition condition = ConditionUtils.toCondition("id", roleIdObjs);
    	List<Map<String, Object>> mapList = tableService.list(ROLE_TABLE_NAME, null, "tablePermission", null, null, condition, null, null, null);
    	
    	
    	for (Map<String, Object> roleMap : mapList) {
    		Object tablePermissionLinesObj = roleMap.get("tablePermissionLines");
    		if (tablePermissionLinesObj != null) {
    			List<Map<String, Object>> tablePermissionLines = (List<Map<String, Object>>)tablePermissionLinesObj;
    			for (Map<String, Object> tablePermissionLine : tablePermissionLines) {
    				if (tablePermissionLine.get("tablePermission") != null) {
    					Map<String, Object> tablePermissionMap = (Map<String, Object>)tablePermissionLine.get("tablePermission");
    					TablePermissionDTO tablePermissionDTO = new TablePermissionDTO();
        				tablePermissionDTO.setId(Long.parseLong(tablePermissionMap.get("id").toString()));
        				tablePermissionDTO.setTableId(Long.parseLong(tablePermissionMap.get("tableId").toString()));
        				tablePermissionDTO.setName(Objects.toString(tablePermissionMap.get("name")));
        				
        				List<Map<String, Object>> permissions = JsonUtils.toObject(tablePermissionMap.get("value").toString(), new TypeReference<List<Map<String, Object>>>(){});
    					
        				tablePermissionDTO.setPermissions(permissions);
        				
        				tablePermissionDTOs.add(tablePermissionDTO);
    				}
    			}
    		}
		}
		
    	return tablePermissionDTOs;
	}
	
	@SuppressWarnings("unchecked")
	private UserDTO loadUserByCondition(Condition condition) {
		log.info("loadUserByCondition {}", condition.toString());
		
    	List<Map<String, Object>> mapList = tableService.list(USER_TABLE_NAME, null, "role", null, null, condition, null, null, null);
    	if (mapList.size() == 0) {
    		return null;
    	}

		Map<String, Object> userMap = mapList.get(0);
		
		UserDTO userDTO = new UserDTO();
		userDTO.setId(Long.parseLong(userMap.get("id").toString()));
		userDTO.setOpenId(Objects.toString(userMap.get("openId")));
		userDTO.setName(Objects.toString(userMap.get("name")));
		userDTO.setUsername(Objects.toString(userMap.get("username")));
		userDTO.setRealname(Objects.toString(userMap.get("realname")));
		userDTO.setMobile(Objects.toString(userMap.get("mobile")));
		userDTO.setEmail(Objects.toString(userMap.get("email")));
		userDTO.setPassword(Objects.toString(userMap.get("password")));
		userDTO.setToken(Objects.toString(userMap.get("token")));
		
		String enabledStr = Objects.toString(userMap.get("enabled"));
		String accountNonExpiredStr = Objects.toString(userMap.get("accountNonExpired"));
		String accountNonLockedStr = Objects.toString(userMap.get("accountNonLocked"));
		String credentialsNonExpiredStr = Objects.toString(userMap.get("credentialsNonExpired"));
		
		userDTO.setEnabled((enabledStr != null) && (enabledStr.equalsIgnoreCase("1") || enabledStr.equalsIgnoreCase("true")));
		userDTO.setAccountNonExpired((accountNonExpiredStr != null) && (accountNonExpiredStr.equalsIgnoreCase("1") || accountNonExpiredStr.equalsIgnoreCase("true")));
		userDTO.setAccountNonLocked((accountNonLockedStr != null) && (accountNonLockedStr.equalsIgnoreCase("1") || accountNonLockedStr.equalsIgnoreCase("true")));
		userDTO.setCredentialsNonExpired((credentialsNonExpiredStr != null) && (credentialsNonExpiredStr.equalsIgnoreCase("1") || credentialsNonExpiredStr.equalsIgnoreCase("true")));

		//role
		List<RoleDTO> roleDTOs = new ArrayList<RoleDTO>();
		List<String> roles = new ArrayList<String>();
		
		Object roleLinesObj = userMap.get("roleLines");
		if (roleLinesObj != null) {
			List<Map<String, Object>> roleLines = (List<Map<String, Object>>)roleLinesObj;
			for (Map<String, Object> roleLine : roleLines) {
				Map<String, Object> roleMap = (Map<String, Object>)roleLine.get("role");
				
				RoleDTO roleDTO = new RoleDTO();
				roleDTO.setId(Long.parseLong(roleMap.get("id").toString()));
				roleDTO.setCode(Objects.toString(roleMap.get("code")));
				roleDTO.setName(Objects.toString(roleMap.get("name")));
				
				roleDTOs.add(roleDTO);
				roles.add(roleDTO.getCode());
			}
		}
		
		userDTO.setResources(listResource(roleDTOs));
		userDTO.setTablePermissions(listTablePermission(roleDTOs));
		userDTO.setRoles(roleDTOs);
		
		List<GrantedAuthorityDTO> roleToAuthorities =  createAuthorityList(roles);
		List<GrantedAuthorityDTO> resourceToAuthorities = resourceService.getAuthorities(userDTO.getResources());
		
		List<GrantedAuthorityDTO> authorities = new ArrayList<GrantedAuthorityDTO>();
		authorities.addAll(roleToAuthorities);
		authorities.addAll(resourceToAuthorities);
		
		userDTO.setAuthorities(authorities);
		
		return userDTO;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("loadUserByUsername {}", username);
		
		Condition condition1 = ConditionUtils.toCondition("username", username);
		Condition condition2 = ConditionUtils.toCondition("mobile", username);
		Condition condition3 = ConditionUtils.toCondition("email", username);
    	CompositeCondition compositeCondition = new CompositeCondition();
    	compositeCondition.setConditionType(ConditionTypeEnum.OR);
    	compositeCondition.add(condition1);
    	compositeCondition.add(condition2);
    	compositeCondition.add(condition3);
    	
    	UserDTO userDTO = loadUserByCondition(compositeCondition);
    	if (userDTO == null) {
    		throw new UsernameNotFoundException("用户不存在");
    	}
		
    	return userDTO;
	}
	
	@Override
	public UserDetails loadOrCreateUserByMobile(String mobile) throws UsernameNotFoundException {
		log.info("loadOrCreateUserByMobile {}", mobile);
		
		Condition condition = ConditionUtils.toCondition("mobile", mobile);
    	
    	UserDTO userDTO = loadUserByCondition(condition);
    	if (userDTO == null) {
    		Map<String, Object> map = new HashMap<String, Object>();
    		String randomStr = RandomStringUtils.randomAlphanumeric(10);
			//log.info("randomStr {}", randomStr);
			String password = passwordEncoder.encode(randomStr);
			String username = getGuid();
			String token = getGuid();

			map.put("realname", "手机用户");
			map.put("mobile", mobile);
			map.put("name", username);
			map.put("username", username);
			map.put("password", password);
			map.put("token", token);
			map.put("enabled", true);
			map.put("accountNonExpired", true);
			map.put("accountNonLocked", true);
			map.put("credentialsNonExpired", true);
			create(map);
			return loadUserByCondition(condition);
    	} else {
    		return userDTO;
    	}
	}
	
	@Override
	public UserDetails loadOrCreateUserByUsername(String username, String rolename) {
		log.info("loadOrCreateUserByUsername {}", username);
		
		Condition condition = ConditionUtils.toCondition("username", username);
    	
    	UserDTO userDTO = loadUserByCondition(condition);
    	if (userDTO == null) {
    		Map<String, Object> map = new HashMap<String, Object>();
    		String randomStr = RandomStringUtils.randomAlphanumeric(10);
			//log.info("randomStr {}", randomStr);
			String password = passwordEncoder.encode(randomStr);
			String token = getGuid();

			map.put("realname", username);
			map.put("name", username);
			map.put("username", username);
			map.put("password", password);
			map.put("token", token);
			map.put("enabled", true);
			map.put("accountNonExpired", true);
			map.put("accountNonLocked", true);
			map.put("credentialsNonExpired", true);
			
			Condition roleCondition = ConditionUtils.toCondition("code", rolename);
		 	List<Map<String, Object>> mapList = tableService.list(ROLE_TABLE_NAME, null, null, null, null, roleCondition, null, null, null);
	    	
		 	if (mapList.size() == 0) {
	    		throw new UsernameNotFoundException(rolename + "不存在");
	    	}
		 	
		 	Object roleId = null;
	    	for (Map<String, Object> roleMap : mapList) {
	    		roleId = roleMap.get("id");
	    		break;
	    	}
			
	    	if (roleId != null) {
	    		List<Map<String, Object>> roleLines = new ArrayList<Map<String, Object>>();
	    		Map<String, Object> roleLine = new HashMap<String, Object>();
	    		roleLine.put("name", rolename);
	    		roleLine.put("roleId", roleId);
	    		roleLines.add(roleLine);
	    		
	    		map.put("roleLines", roleLines);
	    	}
	    	
			create(map);
			return loadUserByCondition(condition);
    	} else {
    		return userDTO;
    	}
	}
	
	@Override
	public UserDetails loadUserByOpenId(String openId) {
		log.info("loadUserByOpenId {}", openId);
		
		Condition condition = ConditionUtils.toCondition("openId", openId);
		UserDTO userDTO = loadUserByCondition(condition);
    	if (userDTO == null) {
    		Map<String, Object> map = new HashMap<String, Object>();
    		String randomStr = RandomStringUtils.randomAlphanumeric(10);
			//log.info("randomStr {}", randomStr);
			String password = passwordEncoder.encode(randomStr);
			String username = getGuid();
			String token = getGuid();

			map.put("openId", openId);
			map.put("realname", "微信用户");
			map.put("name", username);
			map.put("username", username);
			map.put("password", password);
			map.put("token", token);
			map.put("enabled", true);
			map.put("accountNonExpired", true);
			map.put("accountNonLocked", true);
			map.put("credentialsNonExpired", true);
			create(map);
			return loadUserByCondition(condition);
    	} else {
    		return userDTO;
    	}
	}
	
	@Override
	public String create(Map<String, Object> map) {
		return tableService.create(USER_TABLE_NAME, map, null);
	}
	
	@Override
	public void delete(String id) {
		log.info("delete {}", id);
		tableService.delete(USER_TABLE_NAME, id, false, null);
	}
	

	@Override
	public void delete(List<String> ids) {
		tableService.delete(USER_TABLE_NAME, ids, false, null);
	}

	@Override
	public UserDTO get(String id) {
		log.info("get {}", id);
		Condition condition = ConditionUtils.toCondition("id", id);
		UserDTO userDTO = loadUserByCondition(condition);
		
		return userDTO;
	}

	private String getGuid() {
		UUID guid = UUID.randomUUID();
		log.info("guid {}", guid);
		
		return guid.toString().replaceAll("-", "");
	}
	
	public static List<GrantedAuthorityDTO> createAuthorityList(List<String> authorities) {
		List<GrantedAuthorityDTO> grantedAuthorities = new ArrayList<GrantedAuthorityDTO>(authorities.size());

		for (String authority : authorities) {
			grantedAuthorities.add(new GrantedAuthorityDTO(authority));
		}

		return grantedAuthorities;
	}
}
