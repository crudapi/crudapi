package cn.crudapi.crudapi.config.datasource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

public class DynamicDataSourceProperties extends DataSourceProperties {
	/**
	 * Type of the database .
	 */
	private String dataBaseType;
	
	private Boolean deleted;
	
	private String caption;

	public String getDataBaseType() {
		return dataBaseType;
	}

	public void setDataBaseType(String dataBaseType) {
		this.dataBaseType = dataBaseType;
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
