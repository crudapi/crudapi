package cn.crudapi.crudapi.model;

import java.util.List;

public class Index {
	private String name;

	private String caption;

	private String description;
	
	private String indexType;

	private String indexStorage;
	
	private List<Column> columnList;

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

	public String getIndexType() {
		return indexType;
	}

	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}

	public String getIndexStorage() {
		return indexStorage;
	}

	public void setIndexStorage(String indexStorage) {
		this.indexStorage = indexStorage;
	}

	public List<Column> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<Column> columnList) {
		this.columnList = columnList;
	}
}
