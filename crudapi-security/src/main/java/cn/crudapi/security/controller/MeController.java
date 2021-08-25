package cn.crudapi.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api(tags ="用户认证和管理")
@RestController()
@RequestMapping("/api/users")
public class MeController {
	@GetMapping("/me1")
	@ResponseBody
	public Object getMeDetail() {
	    return SecurityContextHolder.getContext().getAuthentication();
	}

	@GetMapping("/me2")
	@ResponseBody
	public Object getMeDetail(Authentication authentication){
	    return authentication;
	}
	
	@GetMapping("/me3")
	@ResponseBody
	public Object getMeDetail(@AuthenticationPrincipal UserDetails userDetails){
	    return userDetails;
	}
}
