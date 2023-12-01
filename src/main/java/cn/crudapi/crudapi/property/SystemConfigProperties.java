package cn.crudapi.crudapi.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "crudapi.system.config")
public class SystemConfigProperties {
	private String apiResourceNaming;
	
	private String apiParamNaming;
	
	private String objectNaming;

	public String getApiResourceNaming() {
		return apiResourceNaming;
	}

	public void setApiResourceNaming(String apiResourceNaming) {
		this.apiResourceNaming = apiResourceNaming;
	}

	public String getApiParamNaming() {
		return apiParamNaming;
	}

	public void setApiParamNaming(String apiParamNaming) {
		this.apiParamNaming = apiParamNaming;
	}

	public String getObjectNaming() {
		return objectNaming;
	}

	public void setObjectNaming(String objectNaming) {
		this.objectNaming = objectNaming;
	}
}
