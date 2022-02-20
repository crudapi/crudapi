package cn.crudapi.core.entity;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import cn.crudapi.core.enumeration.DataTypeEnum;
import cn.crudapi.core.enumeration.IndexStorageEnum;
import cn.crudapi.core.enumeration.IndexTypeEnum;

public class ColumnEntity {
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
}
