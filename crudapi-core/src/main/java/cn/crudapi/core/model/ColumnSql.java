package cn.crudapi.core.model;

import java.util.ArrayList;
import java.util.List;

public class ColumnSql {
	private List<Long> deleteColumnIdList;
	
	private List<String> deleteSqlList;

	private List<String> updateSqlList;

	private List<String> addSqlList;

	public ColumnSql() {
		deleteSqlList = new ArrayList<String>();
		updateSqlList = new ArrayList<String>();
		addSqlList = new ArrayList<String>();
		deleteColumnIdList = new ArrayList<Long>();
	}

	public List<Long> getDeleteColumnIdList() {
		return deleteColumnIdList;
	}

	public void setDeleteColumnIdList(List<Long> deleteColumnIdList) {
		this.deleteColumnIdList = deleteColumnIdList;
	}

	public List<String> getDeleteSqlList() {
		return deleteSqlList;
	}

	public void setDeleteSqlList(List<String> deleteSqlList) {
		this.deleteSqlList = deleteSqlList;
	}

	public List<String> getUpdateSqlList() {
		return updateSqlList;
	}

	public void setUpdateSqlList(List<String> updateSqlList) {
		this.updateSqlList = updateSqlList;
	}

	public List<String> getAddSqlList() {
		return addSqlList;
	}

	public void setAddSqlList(List<String> addSqlList) {
		this.addSqlList = addSqlList;
	}

	@Override
	public String toString() {
		return "ColumnSql [deleteColumnIdList=" + deleteColumnIdList + ", deleteSqlList=" + deleteSqlList
				+ ", updateSqlList=" + updateSqlList + ", addSqlList=" + addSqlList + "]";
	}
}
