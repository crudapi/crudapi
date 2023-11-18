package cn.crudapi.crudapi.model;

public class Column {
	private Integer displayOrder;
	 
	private String name;

	private String caption;

	private String description;

	private String dataType;

	private Boolean unsigned;

	private Long length;

	private Integer precision;

	private Integer scale;

	private String defaultValue;
	
	private Boolean nullable;

	private Boolean insertable;

	private Boolean updatable;

	private Boolean queryable;
	
	private Boolean systemable;
	
	/* constraint */ 
	private String constraintName;
	
	private Boolean primary;
	
	private Boolean autoIncrement;

	private Boolean unique;
	
	private Boolean foreign;
	
	private String referenceTableName;
	
	private String referenceColumnName;
	
	private String deleteRule;
	
	private String updateRule;

	/* index */ 
	private String indexName; //PRIMARY, others
	
	private String indexType; //PRIMARY, UNIQUE, INDEX(BTREE, HASH), FULLTEXT

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

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

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Boolean getUnsigned() {
		return unsigned;
	}

	public void setUnsigned(Boolean unsigned) {
		this.unsigned = unsigned;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public Integer getPrecision() {
		return precision;
	}

	public void setPrecision(Integer precision) {
		this.precision = precision;
	}

	public Integer getScale() {
		return scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Boolean getNullable() {
		return nullable;
	}

	public void setNullable(Boolean nullable) {
		this.nullable = nullable;
	}

	public Boolean getInsertable() {
		return insertable;
	}

	public void setInsertable(Boolean insertable) {
		this.insertable = insertable;
	}

	public Boolean getUpdatable() {
		return updatable;
	}

	public void setUpdatable(Boolean updatable) {
		this.updatable = updatable;
	}

	public Boolean getQueryable() {
		return queryable;
	}

	public void setQueryable(Boolean queryable) {
		this.queryable = queryable;
	}

	public Boolean getSystemable() {
		return systemable;
	}

	public void setSystemable(Boolean systemable) {
		this.systemable = systemable;
	}

	public String getConstraintName() {
		return constraintName;
	}

	public void setConstraintName(String constraintName) {
		this.constraintName = constraintName;
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

	public String getReferenceTableName() {
		return referenceTableName;
	}

	public void setReferenceTableName(String referenceTableName) {
		this.referenceTableName = referenceTableName;
	}

	public String getReferenceColumnName() {
		return referenceColumnName;
	}

	public void setReferenceColumnName(String referenceColumnName) {
		this.referenceColumnName = referenceColumnName;
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

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public String getIndexType() {
		return indexType;
	}

	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}
}
