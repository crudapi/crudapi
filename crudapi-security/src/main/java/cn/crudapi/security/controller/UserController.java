package cn.crudapi.security.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.crudapi.core.dto.UserDTO;
import cn.crudapi.security.service.CaUserDetailsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags ="用户认证和管理")
@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private CaUserDetailsService userDetailsService;
	
    @PreAuthorize("principal.id.equals(#id)")
	@ApiOperation(value="获取用户详情")
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> get(@PathVariable("id") String id) {
		UserDTO userDTO  = userDetailsService.get(id);
		
		return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
	}
}