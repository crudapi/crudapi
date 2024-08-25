package cn.crudapi.security.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.crudapi.core.annotation.CurrentUser;
import cn.crudapi.core.dto.ResourceDTO;
import cn.crudapi.core.dto.TableDTO;
import cn.crudapi.core.dto.UserDTO;
import cn.crudapi.core.query.Condition;
import cn.crudapi.core.service.TableMetadataService;
import cn.crudapi.core.service.TableService;
import cn.crudapi.core.util.ConditionUtils;
import cn.crudapi.core.util.RequestUtils;
import cn.crudapi.security.service.CaUserDetailsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags ="用户认证和管理")
@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private CaUserDetailsService userDetailsService;
	
	@Autowired
	private TableService tableService;
	
	@Autowired
	private TableMetadataService tableMetadataService;
	
    @PreAuthorize("principal.id.equals(#id)")
	@ApiOperation(value="获取用户详情")
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> get(@PathVariable("id") String id) {
		UserDTO userDTO  = userDetailsService.get(id);
		
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
	}
    
    
    @SuppressWarnings("unchecked")
	@ApiOperation(value="查询路由")
   	@GetMapping(value="/me/route")
   	public ResponseEntity<List<Map<String, Object>>> listUserRoute(@AuthenticationPrincipal UserDetails userDetails) {
   		
    	List<Object> roles = new ArrayList<Object>();
    	for (GrantedAuthority grantedAuthority : userDetails.getAuthorities()) {
    		String authority = grantedAuthority.getAuthority();
    		if (authority.startsWith("ROLE_")) {
    			roles.add(authority);
    		}
    	}
    	
    	Condition condition = ConditionUtils.toCondition("code", roles);
    	
    	Map<String, Boolean> isExistMap = new HashMap<String, Boolean>();
    	
    	List<Map<String, Object>> routeList = new ArrayList<Map<String, Object>>();
    	List<Map<String, Object>> mapList = tableService.list("role", null, null, null, null, condition, null, null, null, null);
    	for (Map<String, Object> t : mapList) {
    		Object roleRouteLines = t.get("roleRouteLines");
    		if (roleRouteLines != null) {
    			List<Map<String, Object>> roleRouteLinesMapList = (List<Map<String, Object>>)roleRouteLines;
    			
    			for (Map<String, Object> roleRouteLinesMap : roleRouteLinesMapList) {
    				Map<String, Object> routeMap = (Map<String, Object>)roleRouteLinesMap.get("route");
    				String routeId = routeMap.get("id").toString();
    				if (isExistMap.get(routeId) == null || !isExistMap.get(routeId)) {
    					isExistMap.put(routeId, true);
    					routeList.add(routeMap);
    				}
    			}
    		}
    	}
    	
    	
   		return new ResponseEntity<List<Map<String, Object>>>(routeList, HttpStatus.OK);
   	}
    
    @SuppressWarnings("unchecked")
   	@ApiOperation(value="查询菜单")
  	@GetMapping(value="/me/menu")
  	public ResponseEntity<List<Map<String, Object>>> listUserMenu(@AuthenticationPrincipal UserDetails userDetails) {
	   	List<Object> roles = new ArrayList<Object>();
	   	for (GrantedAuthority grantedAuthority : userDetails.getAuthorities()) {
	   		String authority = grantedAuthority.getAuthority();
	   		if (authority.startsWith("ROLE_")) {
	   			roles.add(authority);
	   		}
	   	}
	   	
	   	Condition condition = ConditionUtils.toCondition("code", roles);
	   	
	   	Map<String, Boolean> isExistMap = new HashMap<String, Boolean>();
	   	
	   	List<Map<String, Object>> menuList = new ArrayList<Map<String, Object>>();
	   	List<Map<String, Object>> mapList = tableService.list("role", null, null, null, null, condition, null, null, null, null);
	   	for (Map<String, Object> t : mapList) {
	   		Object roleMenuLines = t.get("roleMenuLines");
	   		if (roleMenuLines != null) {
	   			List<Map<String, Object>> roleMenuLineMapList = (List<Map<String, Object>>)roleMenuLines;
	   			
	   			for (Map<String, Object> roleMenuLineMap : roleMenuLineMapList) {
	   				Map<String, Object> routeMap = (Map<String, Object>)roleMenuLineMap.get("menu");
	   				String menuId = routeMap.get("id").toString();
	   				if (isExistMap.get(menuId) == null || !isExistMap.get(menuId)) {
	   					isExistMap.put(menuId, true);
	   					menuList.add(routeMap);
	   				}
	   			}
	   		}
	   	}
	   	
   	
  		return new ResponseEntity<List<Map<String, Object>>>(menuList, HttpStatus.OK);
  	}
    
    @SuppressWarnings("unchecked")
    @ApiOperation(value="查询我看到的表")
  	@GetMapping("/me/table")
  	public ResponseEntity<List<TableDTO>> list(
  			@RequestParam(value = "filter", required = false) String filter,
  			@RequestParam(value = "search", required = false) String search,
  			@RequestParam(value = "offset", required = false) Integer offset,
  			@RequestParam(value = "limit", required = false) Integer limit,
  			@RequestParam(value = "orderby", required = false) String orderby,
  			HttpServletRequest request,
  			@AuthenticationPrincipal UserDetails userDetails) {
      	Condition condition = ConditionUtils.toCondition(RequestUtils.getParams(request));
  		
  		List<TableDTO> tableDTOList = tableMetadataService.list(filter, search, condition, offset, limit, orderby);

  		Boolean isSuperAdmin = false;
  		List<Object> roles = new ArrayList<Object>();
    	for (GrantedAuthority grantedAuthority : userDetails.getAuthorities()) {
    		String authority = grantedAuthority.getAuthority();
    		if (authority.startsWith("ROLE_")) {
    			roles.add(authority);
    		}
    		if (authority.equals("ROLE_SUPER_ADMIN")) {
    			isSuperAdmin = true;
    			
    			return new ResponseEntity<List<TableDTO>>(tableDTOList, HttpStatus.OK);
    		}
    	}
    	
    	Condition codeCondition = ConditionUtils.toCondition("code", roles);
    	
    	Map<String, Boolean> isExistMap = new HashMap<String, Boolean>();
    	
    	List<Map<String, Object>> resourceList = new ArrayList<Map<String, Object>>();
    	List<Map<String, Object>> mapList = tableService.list("role", null, "resource", null, null, codeCondition, null, null, null, null);
    	for (Map<String, Object> t : mapList) {
    		Object resourceLines = t.get("resourceLines");
    		if (resourceLines != null) {
    			List<Map<String, Object>> resourceLineMapList = (List<Map<String, Object>>)resourceLines;
    			
    			for (Map<String, Object> resourceLineMap : resourceLineMapList) {
    				Map<String, Object> resourceMap = (Map<String, Object>)resourceLineMap.get("resource");
    				String resourceId = resourceMap.get("id").toString();
    				if (isExistMap.get(resourceId) == null || !isExistMap.get(resourceId)) {
    					isExistMap.put(resourceId, true);
    					resourceList.add(resourceMap);
    				}
    			}
    		}
    	}
    	
 
    	PathMatcher pathMatcher = new AntPathMatcher();
    	List<TableDTO> authTableDTOList = new ArrayList<TableDTO>();
		for (TableDTO tableDTO : tableDTOList) {
            String reqPath = "/api/business/" + tableDTO.getName() + "_GET";
            
            for (Map<String, Object> resource : resourceList) {
            	String resUrl = resource.get("url") + "_" + resource.get("action");
            	
            	if (pathMatcher.match(resUrl, reqPath)){
            		authTableDTOList.add(tableDTO);
            		break;
                }
            }
        }
		
		
        
  		return new ResponseEntity<List<TableDTO>>(authTableDTOList, HttpStatus.OK);
  	}
}