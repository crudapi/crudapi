package cn.crudapi.crudapi.model;

import java.util.List;

public class Constraint {
	private String name;

	private String caption;

	private String description;
	
	private Boolean primary;
	
	private Boolean autoIncrement;

	private Boolean unique;
	
	private Boolean foreign;
	
	private List<Column> columnList;
	
	private String referenceTableName;
	
	private List<Column> referenceColumnList;
	
	private String referenceOption;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getPrimary() {
		return primary;
	}

	public void setPrimary(Boolean primary) {
		this.primary = primary;
	}

	public Boolean getAutoIncrement() {
		return autoIncrement;
	}

	public void setAutoIncrement(Boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}

	public Boolean getUnique() {
		return unique;
	}

	public void setUnique(Boolean unique) {
		this.unique = unique;
	}

	public Boolean getForeign() {
		return foreign;
	}

	public void setForeign(Boolean foreign) {
		this.foreign = foreign;
	}

	public List<Column> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<Column> columnList) {
		this.columnList = columnList;
	}

	public String getReferenceTableName() {
		return referenceTableName;
	}

	public void setReferenceTableName(String referenceTableName) {
		this.referenceTableName = referenceTableName;
	}

	public List<Column> getReferenceColumnList() {
		return referenceColumnList;
	}

	public void setReferenceColumnList(List<Column> referenceColumnList) {
		this.referenceColumnList = referenceColumnList;
	}

	public String getReferenceOption() {
		return referenceOption;
	}

	public void setReferenceOption(String referenceOption) {
		this.referenceOption = referenceOption;
	}
}
