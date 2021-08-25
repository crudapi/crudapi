package cn.crudapi.core.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import cn.crudapi.core.enumeration.IndexStorageEnum;
import cn.crudapi.core.enumeration.IndexTypeEnum;
import cn.crudapi.core.util.DbUtils;

public class IndexEntity implements Sqlable, BaseEntity {
	private static final String TABLE_NAME = "ca_meta_index";
	
	private Long id;

	private String name;

	private String caption;

	private String description;

	private Timestamp createdDate;

	private Timestamp lastModifiedDate;

	private IndexTypeEnum indexType;

	private IndexStorageEnum indexStorage;

	private Long tableId;

	private List<IndexLineEntity> indexLineEntityList;


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

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public List<IndexLineEntity> getIndexLineEntityList() {
		return indexLineEntityList;
	}

	public void setIndexLineEntityList(List<IndexLineEntity> indexLineEntityList) {
		this.indexLineEntityList = indexLineEntityList;
	}


	@Override
	public String toString() {
		return "IndexEntity [id=" + id + ", name=" + name + ", caption=" + caption + ", description=" + description
				+ ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate + ", indexType=" + indexType
				+ ", indexStorage=" + indexStorage + ", tableId=" + tableId + ", indexLineEntityList="
				+ indexLineEntityList + "]";
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
		IndexEntity other = (IndexEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toSql() {
		return DbUtils.toIndexSql(indexType, indexStorage, indexLineEntityList, name, caption);
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
		columnNames.add("indexType");
		columnNames.add("indexStorage");
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
		columnValues.add(indexType != null ? indexType.toString() : null);
		columnValues.add(indexStorage != null ? indexStorage.toString() : null);
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
		if (indexType != null) { columnNames.add("indexType"); }
		if (indexStorage != null) { columnNames.add("indexStorage"); }
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
		if (indexType != null) { columnValues.add(indexType.toString()); }
		if (indexStorage != null) { columnValues.add(indexStorage.toString()); }
		if (tableId != null) { columnValues.add(tableId.toString()); }
		return columnValues;
	}
}
