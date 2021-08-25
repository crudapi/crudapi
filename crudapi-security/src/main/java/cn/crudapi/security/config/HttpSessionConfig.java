package cn.crudapi.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

@EnableJdbcHttpSession(maxInactiveIntervalInSeconds = 3600)
public class HttpSessionConfig {
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {
	    return new HttpSessionEventPublisher();
	}
}
