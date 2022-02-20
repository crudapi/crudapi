package cn.crudapi.core.entity;

import java.sql.Timestamp;

import cn.crudapi.core.enumeration.TableRelationTypeEnum;

public class TableRelationEntity {
	
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
}
