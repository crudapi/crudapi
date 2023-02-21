package cn.crudapi.crudapi.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfiguration {
	@Bean
	public DataSourceAspect dataSourceAspect() {
		return new DataSourceAspect();
	}
	
	@Bean
	public LoggingAspect loggingAspect() {
		return new LoggingAspect();
	}
}
