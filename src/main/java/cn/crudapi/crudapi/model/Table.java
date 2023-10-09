package cn.crudapi.crudapi.model;

import java.util.List;

public class Table {
	private String name;

	private String caption;

	private String description;
	
	private List<Column> columnList;

	private List<Index> indexList;
	
	private List<Constraint> constraintList;

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

	public List<Column> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<Column> columnList) {
		this.columnList = columnList;
	}

	public List<Index> getIndexList() {
		return indexList;
	}

	public void setIndexList(List<Index> indexList) {
		this.indexList = indexList;
	}

	public List<Constraint> getConstraintList() {
		return constraintList;
	}

	public void setConstraintList(List<Constraint> constraintList) {
		this.constraintList = constraintList;
	}
}
