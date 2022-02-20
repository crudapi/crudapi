package cn.crudapi.core.entity;

import java.sql.Timestamp;
import java.util.List;

import cn.crudapi.core.enumeration.EngineEnum;

public class TableEntity {
	private Long id;

	private String name;

	private String caption;

	private String description;

	private Timestamp createdDate;

	private Timestamp lastModifiedDate;

	private String pluralName;

	private String tableName;

    private EngineEnum engine;

    private Boolean createPhysicalTable;
    
    private Boolean reverse;
    
    private Boolean systemable;
    
    private Boolean readOnly;

    private List<ColumnEntity> columnEntityList;

    private List<IndexEntity> indexEntityList;
    
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

	public String getPluralName() {
		return pluralName;
	}

	public void setPluralName(String pluralName) {
		this.pluralName = pluralName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public EngineEnum getEngine() {
		return engine;
	}

	public void setEngine(EngineEnum engine) {
		this.engine = engine;
	}

	public Boolean getCreatePhysicalTable() {
		return createPhysicalTable;
	}

	public void setCreatePhysicalTable(Boolean createPhysicalTable) {
		this.createPhysicalTable = createPhysicalTable;
	}

	public Boolean getReverse() {
		return reverse;
	}

	public void setReverse(Boolean reverse) {
		this.reverse = reverse;
	}

	public Boolean getSystemable() {
		return systemable;
	}

	public void setSystemable(Boolean systemable) {
		this.systemable = systemable;
	}

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public List<ColumnEntity> getColumnEntityList() {
		return columnEntityList;
	}

	public void setColumnEntityList(List<ColumnEntity> columnEntityList) {
		this.columnEntityList = columnEntityList;
	}

	public List<IndexEntity> getIndexEntityList() {
		return indexEntityList;
	}

	public void setIndexEntityList(List<IndexEntity> indexEntityList) {
		this.indexEntityList = indexEntityList;
	}
	
	
	@Override
	public String toString() {
		return "TableEntity [id=" + id + ", name=" + name + ", caption=" + caption + ", description=" + description
				+ ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate + ", pluralName="
				+ pluralName + ", tableName=" + tableName + ", engine=" + engine + ", createPhysicalTable="
				+ createPhysicalTable + ", reverse=" + reverse + ", systemable=" + systemable + ", readOnly=" + readOnly
				+ "]";
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
		TableEntity other = (TableEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
