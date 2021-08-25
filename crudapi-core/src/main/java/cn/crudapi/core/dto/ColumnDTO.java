package cn.crudapi.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cn.crudapi.core.enumeration.DataTypeEnum;
import cn.crudapi.core.enumeration.IndexStorageEnum;
import cn.crudapi.core.enumeration.IndexTypeEnum;

@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ColumnDTO extends AuditDTO {
	private Long id;

    private Integer displayOrder;

	private DataTypeEnum dataType;

	private Boolean unsigned;

	private IndexTypeEnum indexType;

	private IndexStorageEnum indexStorage;

	private String indexName;

	private Integer length;

	private Integer precision;

	private Integer scale;

	private Boolean autoIncrement;

	private Long seqId;

	private Boolean nullable;

	private String defaultValue;

	private Boolean insertable;

	private Boolean updatable;

	private Boolean queryable;
	
	private Boolean displayable;
	
	private Boolean systemable;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Boolean getUnsigned() {
		return unsigned;
	}

	public void setUnsigned(Boolean unsigned) {
		this.unsigned = unsigned;
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

	public Boolean getAutoIncrement() {
		return autoIncrement;
	}

	public void setAutoIncrement(Boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}

	public Long getSeqId() {
		return seqId;
	}

	public void setSeqId(Long seqId) {
		this.seqId = seqId;
	}

	public Boolean getNullable() {
		return nullable;
	}

	public void setNullable(Boolean nullable) {
		this.nullable = nullable;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
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
		ColumnDTO other = (ColumnDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
