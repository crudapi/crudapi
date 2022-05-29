package cn.crudapi.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import cn.crudapi.service.aop.DataSourceAspect;
import cn.crudapi.service.aop.LoggingAspect;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {
	@Bean
	public LoggingAspect loggingAspect() {
		return new LoggingAspect();
	}
	
	@Bean
	public DataSourceAspect dataSourceAspect() {
		return new DataSourceAspect();
	}
	
}
