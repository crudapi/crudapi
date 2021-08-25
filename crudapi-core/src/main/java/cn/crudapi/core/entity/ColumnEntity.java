package cn.crudapi.core.entity;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import cn.crudapi.core.enumeration.DataTypeEnum;
import cn.crudapi.core.enumeration.IndexStorageEnum;
import cn.crudapi.core.enumeration.IndexTypeEnum;
import cn.crudapi.core.util.DbUtils;

public class ColumnEntity implements Sqlable, BaseEntity {
	private static final String TABLE_NAME = "ca_meta_column";
	
	private Long id;

	private String name;

	private String caption;

	private String description;

	private Timestamp createdDate;

	private Timestamp lastModifiedDate;

    private Integer displayOrder;

	private DataTypeEnum dataType;

	private IndexTypeEnum indexType;

	private IndexStorageEnum indexStorage;

	private String indexName;

	private Integer length;

	private Integer precision;

	private Integer scale;

	private String defaultValue;

	private Long seqId;

	private Boolean unsigned;

	private Boolean autoIncrement;

	private Boolean nullable;

	private Boolean insertable;

	private Boolean updatable;

	private Boolean queryable;
	
	private Boolean displayable;
	
	private Boolean systemable;

	private Long tableId;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public DataTypeEnum getDataType() {
		return dataType;
	}

	public void setDataType(DataTypeEnum dataType) {
		this.dataType = dataType;
	}

	public IndexTypeEnum getIndexType() {
		return indexType;
	}

	public void setIndexType(IndexTypeEnum indexType) {
		this.indexType = indexType;
	}

	public IndexStorageEnum getIndexStorage() {
		return indexStorage;
	}

	public void setIndexStorage(IndexStorageEnum indexStorage) {
		this.indexStorage = indexStorage;
	}

	public String getIndexName() {
		return indexName;
	}

	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
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

	public Long getSeqId() {
		return seqId;
	}

	public void setSeqId(Long seqId) {
		this.seqId = seqId;
	}

	public Boolean getUnsigned() {
		return unsigned;
	}

	public void setUnsigned(Boolean unsigned) {
		this.unsigned = unsigned;
	}

	public Boolean getAutoIncrement() {
		return autoIncrement;
	}

	public void setAutoIncrement(Boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
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

	public Boolean getDisplayable() {
		return displayable;
	}

	public void setDisplayable(Boolean displayable) {
		this.displayable = displayable;
	}

	public Boolean getSystemable() {
		return systemable;
	}

	public void setSystemable(Boolean systemable) {
		this.systemable = systemable;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}
	
	@Override
	public String toString() {
		return "ColumnEntity [id=" + id + ", name=" + name + ", caption=" + caption + ", description=" + description
				+ ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate + ", displayOrder="
				+ displayOrder + ", dataType=" + dataType + ", indexType=" + indexType + ", indexStorage="
				+ indexStorage + ", indexName=" + indexName + ", length=" + length + ", precision=" + precision
				+ ", scale=" + scale + ", defaultValue=" + defaultValue + ", seqId=" + seqId + ", unsigned=" + unsigned
				+ ", autoIncrement=" + autoIncrement + ", nullable=" + nullable + ", insertable=" + insertable
				+ ", updatable=" + updatable + ", queryable=" + queryable + ", displayable=" + displayable
				+ ", systemable=" + systemable + ", tableId=" + tableId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColumnEntity other = (ColumnEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toSql() {
		String pattern = "{0} {1}";
		String dbDataType = dataType.toString();
		
		if (dbDataType.equals("BOOL")) {
			dbDataType = "BIT";
		} else if (dbDataType.equals("ATTACHMENT")) {
			dbDataType = "VARCHAR";
		} else if (dbDataType.equals("PASSWORD")) {
			dbDataType = "VARCHAR";
		}
		
		Object[] arguments = { DbUtils.toNameSql(name), dbDataType };

		// name, dataType
		StringBuilder sb = new StringBuilder(MessageFormat.format(pattern, arguments));
		
		// length, precision, scale
		sb.append(DbUtils.toSql(dataType, length, precision, scale));

		// unsigned
		if (Objects.equals(unsigned, true) && DbUtils.isNumber(dataType)) {
			sb.append(" UNSIGNED");
		}

		// autoIncrement
		if (Objects.equals(autoIncrement, true)) {
			sb.append(" NOT NULL AUTO_INCREMENT");
		} else {
			if (Objects.equals(nullable, true)) {
				sb.append(" NULL");
			} else {
				sb.append(" NOT NULL");
			}

			if (defaultValue != null) {
				sb.append(" DEFAULT ");
				sb.append(DbUtils.toDefaultValueSql(dataType, defaultValue));
			}
		}
		
		sb.append(" COMMENT ''");
		sb.append(StringUtils.isEmpty(caption) ? name : caption );
		sb.append("''");
		
		System.out.println(sb.toString());
		
		return sb.toString();
	}

	public String toIndexSql() {
		if (indexType == null || indexType.equals(IndexTypeEnum.NONE)) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		sb.append(DbUtils.toIndexSql(indexType, indexStorage, name, indexName, null));

		return sb.toString();
	}

	
	@Override
	public String getDataBaseTableName() {
		return TABLE_NAME;
	}

	@Override
	public Long getRecId() {
		return id;
	}

	@Override
	public List<String> getColumnNames(Boolean isUpdate) {
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("name");
		columnNames.add("caption"); 
		columnNames.add("description");
		if (isUpdate == false) { 
			columnNames.add("createdDate"); 
		}
		columnNames.add("lastModifiedDate");
		columnNames.add("displayOrder");
		columnNames.add("dataType");
		columnNames.add("indexType");
		columnNames.add("indexStorage");
		columnNames.add("indexName");
		columnNames.add("length");
		columnNames.add("precision");
		columnNames.add("scale");
		columnNames.add("defaultValue");
		columnNames.add("seqId");
		columnNames.add("unsigned");
		columnNames.add("autoIncrement");
		columnNames.add("nullable");
		columnNames.add("insertable");
		columnNames.add("updatable");
		columnNames.add("queryable");
		columnNames.add("displayable");
		columnNames.add("systemable");
		
		columnNames.add("tableId");
		return columnNames;
	}

	@Override
	public List<Object> getColumnValues(Boolean isUpdate) {
		List<Object> columnValues = new ArrayList<Object>();
		columnValues.add(name);
		columnValues.add(caption);
		columnValues.add(description);
		if (isUpdate == false) { 
			columnValues.add(createdDate); 
		}
		columnValues.add(lastModifiedDate);
		columnValues.add(displayOrder);
		columnValues.add(dataType != null ? dataType.toString() : null);
		columnValues.add(indexType != null ? indexType.toString() : null);
		columnValues.add(indexStorage != null ? indexStorage.toString() : null);
		columnValues.add(indexName);
		columnValues.add(length);
		columnValues.add(precision);
		columnValues.add(scale);
		columnValues.add(defaultValue);
		columnValues.add(seqId);
		columnValues.add(unsigned);
		columnValues.add(autoIncrement);
		columnValues.add(nullable);
		columnValues.add(insertable);
		columnValues.add(updatable);
		columnValues.add(queryable);
		columnValues.add(displayable);
		columnValues.add(systemable);
		columnValues.add(tableId);
		return columnValues;
	}

	@Override
	public List<String> getColumnNamesIgnoreNull(Boolean isUpdate) {
		List<String> columnNames = new ArrayList<String>();
		if (name != null) { columnNames.add("name"); }
		if (caption != null) { columnNames.add("caption"); }
		if (description != null) { columnNames.add("description"); }
		if (createdDate != null && isUpdate == false) { columnNames.add("createdDate"); }
		if (lastModifiedDate != null) { columnNames.add("lastModifiedDate"); }
		if (displayOrder != null) { columnNames.add("displayOrder"); }
		if (dataType != null) { columnNames.add("dataType"); }
		if (indexType != null) { columnNames.add("indexType"); }
		if (indexStorage != null) { columnNames.add("indexStorage"); }
		if (indexName != null) { columnNames.add("indexName"); }
		if (length != null) { columnNames.add("length"); }
		if (precision != null) { columnNames.add("precision"); }
		if (scale != null) { columnNames.add("scale"); }
		if (defaultValue != null) { columnNames.add("defaultValue"); }
		if (seqId != null) { columnNames.add("seqId"); }
		if (unsigned != null) { columnNames.add("unsigned"); }
		if (autoIncrement != null) { columnNames.add("autoIncrement"); }
		if (nullable != null) { columnNames.add("nullable"); }
		if (insertable != null) { columnNames.add("insertable"); }
		if (updatable != null) { columnNames.add("updatable"); }
		if (queryable != null) { columnNames.add("queryable"); }
		if (displayable != null) { columnNames.add("displayable"); }
		if (systemable != null) { columnNames.add("systemable"); }
		if (tableId != null) { columnNames.add("tableId"); }
		return columnNames;
	}

	@Override
	public List<Object> getColumnValuesIgnoreNull(Boolean isUpdate) {
		List<Object> columnValues = new ArrayList<Object>();
		if (name != null) { columnValues.add(name); }
		if (caption != null) { columnValues.add(caption); }
		if (description != null) { columnValues.add(description); }
		if (createdDate != null && isUpdate == false) { columnValues.add(createdDate); }
		if (lastModifiedDate != null) { columnValues.add(lastModifiedDate); }
		if (displayOrder != null) { columnValues.add(displayOrder); }
		if (dataType != null) { columnValues.add(dataType.toString()); }
		if (indexType != null) { columnValues.add(indexType.toString()); }
		if (indexStorage != null) { columnValues.add(indexStorage.toString()); }
		if (indexName != null) { columnValues.add(indexName); }
		if (length != null) { columnValues.add(length); }
		if (precision != null) { columnValues.add(precision); }
		if (scale != null) { columnValues.add(scale); }
		if (defaultValue != null) { columnValues.add(defaultValue); }
		if (seqId != null) { columnValues.add(seqId); }
		if (unsigned != null) { columnValues.add(unsigned); }
		if (autoIncrement != null) { columnValues.add(autoIncrement); }
		if (nullable != null) { columnValues.add(nullable); }
		if (insertable != null) { columnValues.add(insertable); }
		if (updatable != null) { columnValues.add(updatable); }
		if (queryable != null) { columnValues.add(queryable); }
		if (displayable != null) { columnValues.add(displayable); }
		if (systemable != null) { columnValues.add(systemable); }
		if (tableId != null) { columnValues.add(tableId); }
		return columnValues;
	}
}
