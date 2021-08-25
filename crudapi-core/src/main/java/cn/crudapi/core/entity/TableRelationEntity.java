package cn.crudapi.core.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import cn.crudapi.core.enumeration.TableRelationTypeEnum;


public class TableRelationEntity implements BaseEntity {
	private static final String TABLE_NAME = "ca_meta_table_relation";
	
	private Long id;

	private String name;

	private String caption;

	private String description;

	private Timestamp createdDate;

	private Timestamp lastModifiedDate;

    private TableRelationTypeEnum relationType;

    private Long fromTableId;

    private Long toTableId;

	private Long fromColumnId;

	private Long toColumnId;
	
	private TableEntity fromTableEntity;

    private TableEntity toTableEntity;

	private ColumnEntity fromColumnEntity;

	private ColumnEntity toColumnEntity;
	
	
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

	public TableRelationTypeEnum getRelationType() {
		return relationType;
	}

	public void setRelationType(TableRelationTypeEnum relationType) {
		this.relationType = relationType;
	}

	public Long getFromTableId() {
		return fromTableId;
	}

	public void setFromTableId(Long fromTableId) {
		this.fromTableId = fromTableId;
	}

	public Long getToTableId() {
		return toTableId;
	}

	public void setToTableId(Long toTableId) {
		this.toTableId = toTableId;
	}

	public Long getFromColumnId() {
		return fromColumnId;
	}

	public void setFromColumnId(Long fromColumnId) {
		this.fromColumnId = fromColumnId;
	}

	public Long getToColumnId() {
		return toColumnId;
	}

	public void setToColumnId(Long toColumnId) {
		this.toColumnId = toColumnId;
	}

	public TableEntity getFromTableEntity() {
		return fromTableEntity;
	}

	public void setFromTableEntity(TableEntity fromTableEntity) {
		this.fromTableEntity = fromTableEntity;
	}

	public TableEntity getToTableEntity() {
		return toTableEntity;
	}

	public void setToTableEntity(TableEntity toTableEntity) {
		this.toTableEntity = toTableEntity;
	}

	public ColumnEntity getFromColumnEntity() {
		return fromColumnEntity;
	}

	public void setFromColumnEntity(ColumnEntity fromColumnEntity) {
		this.fromColumnEntity = fromColumnEntity;
	}

	public ColumnEntity getToColumnEntity() {
		return toColumnEntity;
	}

	public void setToColumnEntity(ColumnEntity toColumnEntity) {
		this.toColumnEntity = toColumnEntity;
	}
	
	
	@Override
	public String toString() {
		return "TableRelationEntity [id=" + id + ", name=" + name + ", caption=" + caption + ", description="
				+ description + ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate
				+ ", relationType=" + relationType + ", fromTableId=" + fromTableId + ", toTableId=" + toTableId
				+ ", fromColumnId=" + fromColumnId + ", toColumnId=" + toColumnId + ", fromTableEntity="
				+ fromTableEntity + ", toTableEntity=" + toTableEntity + ", fromColumnEntity=" + fromColumnEntity
				+ ", toColumnEntity=" + toColumnEntity + "]";
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
		TableRelationEntity other = (TableRelationEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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
		columnNames.add("relationType");
		columnNames.add("fromTableId");
		columnNames.add("toTableId");
		columnNames.add("fromColumnId");
		columnNames.add("toColumnId");
		
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
		columnValues.add(relationType != null ? relationType.toString() : null);
		columnValues.add(fromTableId);
		columnValues.add(toTableId);
		columnValues.add(fromColumnId);
		columnValues.add(toColumnId);
		
		return columnValues;
	}

	@Override
	public List<String> getColumnNamesIgnoreNull(Boolean isUpdate) {
		List<String> columnNames = new ArrayList<String>();
		if (name != null) { columnNames.add("name"); }
		if (caption != null) { columnNames.add("caption"); } 
		if (description != null) { columnNames.add("description"); }
		if (createdDate != null && isUpdate == false) { 
			columnNames.add("createdDate");
		}
		if (lastModifiedDate != null) {columnNames.add("lastModifiedDate"); }
		if (relationType != null) {columnNames.add("relationType"); }
		if (fromTableId != null) {columnNames.add("fromTableId"); }
		if (toTableId != null) {columnNames.add("toTableId"); }
		if (fromColumnId != null) {columnNames.add("fromColumnId"); }
		if (toColumnId != null) {columnNames.add("toColumnId"); }
		
		return columnNames;
	}

	@Override
	public List<Object> getColumnValuesIgnoreNull(Boolean isUpdate) {
		List<Object> columnValues = new ArrayList<Object>();
		if (name != null) {columnValues.add(name); }
		if (caption != null) {columnValues.add(caption); }
		if (description != null) {columnValues.add(description); }
		if (createdDate != null && isUpdate == false) { 
			columnValues.add(createdDate); 
		}
		if (lastModifiedDate != null) {columnValues.add(lastModifiedDate); }
		if (relationType != null) {columnValues.add(relationType != null ? relationType.toString() : null); }
		if (fromTableId != null) {columnValues.add(fromTableId); }
		if (toTableId != null) {columnValues.add(toTableId); }
		if (fromColumnId != null) {columnValues.add(fromColumnId); }
		if (toColumnId != null) {columnValues.add(toColumnId); }
		
		return columnValues;
	}
}
