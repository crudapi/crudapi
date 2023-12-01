package cn.crudapi.crudapi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Constraint {
	private String name;

	private String caption;

	private String description;
	
	private Boolean primary;
	
	private Boolean unique;
	
	private Boolean foreign;
	
	@JsonProperty("columns")
	private List<Column> columnList;
	
	private Table referenceTable;
	
	@JsonProperty("referenceColumns")
	private List<Column> referenceColumnList;
	
	private String deleteRule;
	
	private String updateRule;

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

	public Table getReferenceTable() {
		return referenceTable;
	}

	public void setReferenceTable(Table referenceTable) {
		this.referenceTable = referenceTable;
	}

	public List<Column> getReferenceColumnList() {
		return referenceColumnList;
	}

	public void setReferenceColumnList(List<Column> referenceColumnList) {
		this.referenceColumnList = referenceColumnList;
	}

	public String getDeleteRule() {
		return deleteRule;
	}

	public void setDeleteRule(String deleteRule) {
		this.deleteRule = deleteRule;
	}

	public String getUpdateRule() {
		return updateRule;
	}

	public void setUpdateRule(String updateRule) {
		this.updateRule = updateRule;
	}
}
