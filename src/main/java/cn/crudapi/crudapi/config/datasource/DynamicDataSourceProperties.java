package cn.crudapi.crudapi.config.datasource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

public class DynamicDataSourceProperties extends DataSourceProperties {
	private String caption;
	
	/**
	 * Type of the database .
	 */
	private String databaseType;
	
	private String status;
	
	private Boolean deleted;
	

	public String getDatabaseType() {
		return databaseType;
	}

	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

}
