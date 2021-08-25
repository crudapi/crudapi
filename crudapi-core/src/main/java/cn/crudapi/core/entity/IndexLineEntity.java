package cn.crudapi.core.entity;

import java.util.ArrayList;
import java.util.List;

public class IndexLineEntity implements Sqlable, BaseEntity {
	private static final String TABLE_NAME = "ca_meta_index_line";
	
	private Long id;
	
	private Long columnId;
	
	private ColumnEntity columnEntity;

	private Long indexId;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getColumnId() {
		return columnId;
	}

	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}

	public ColumnEntity getColumnEntity() {
		return columnEntity;
	}

	public void setColumnEntity(ColumnEntity columnEntity) {
		this.columnEntity = columnEntity;
	}

	public Long getIndexId() {
		return indexId;
	}

	public void setIndexId(Long indexId) {
		this.indexId = indexId;
	}

	public static String getTableName() {
		return TABLE_NAME;
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
		IndexLineEntity other = (IndexLineEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "IndexLineEntity [id=" + id + ", columnId=" + columnId + ", columnEntity=" + columnEntity + ", indexId="
				+ indexId + "]";
	}

	@Override
	public String toSql() {
		return null;
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
		columnNames.add("columnId"); 
		columnNames.add("indexId");
		return columnNames;
	}

	@Override
	public List<Object> getColumnValues(Boolean isUpdate) {
		List<Object> columnValues = new ArrayList<Object>();
		columnValues.add(columnId);
		columnValues.add(indexId);
		return columnValues;
	}

	@Override
	public List<String> getColumnNamesIgnoreNull(Boolean isUpdate) {
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("columnId"); 
		columnNames.add("indexId");
		return columnNames;
	}

	@Override
	public List<Object> getColumnValuesIgnoreNull(Boolean isUpdate) {
		List<Object> columnValues = new ArrayList<Object>();
		columnValues.add(columnId);
		columnValues.add(indexId);
		return columnValues;
	}
}
