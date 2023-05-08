package cn.crudapi.crudapi.config.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceExtProperties {
	private String caption;
	
	private String databaseType;
	
	private String metadataTablePrefix;
	
	private String metadataDatabaseNaming;
	
	private String businessTablePrefix;
	
	private String businessDatabaseNaming;

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getDatabaseType() {
		return databaseType;
	}

	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}

	public String getMetadataTablePrefix() {
		return metadataTablePrefix;
	}

	public void setMetadataTablePrefix(String metadataTablePrefix) {
		this.metadataTablePrefix = metadataTablePrefix;
	}

	public String getMetadataDatabaseNaming() {
		return metadataDatabaseNaming;
	}

	public void setMetadataDatabaseNaming(String metadataDatabaseNaming) {
		this.metadataDatabaseNaming = metadataDatabaseNaming;
	}

	public String getBusinessTablePrefix() {
		return businessTablePrefix;
	}

	public void setBusinessTablePrefix(String businessTablePrefix) {
		this.businessTablePrefix = businessTablePrefix;
	}

	public String getBusinessDatabaseNaming() {
		return businessDatabaseNaming;
	}

	public void setBusinessDatabaseNaming(String businessDatabaseNaming) {
		this.businessDatabaseNaming = businessDatabaseNaming;
	}
}
