package cn.crudapi.security.component;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "secure.ignored")
@EnableConfigurationProperties
@Component
public class IgnoreUrlsConfig {
    private List<String> urls = new ArrayList<>();

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}
}
