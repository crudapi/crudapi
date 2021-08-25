package cn.crudapi.security.service;

import java.util.List;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

public interface ThirdAuthenticationService {
	List<SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>> getSecurityConfigurerAdapters();
}
