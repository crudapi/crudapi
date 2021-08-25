package cn.crudapi.core.model;

import java.util.ArrayList;
import java.util.List;


public class IndexSql {
	private List<String> deleteSqlList;

	private List<String> updateSqlList;

	private List<String> addSqlList;
	
	private List<Long> deleteIndexIdList;

	public IndexSql() {
		deleteSqlList = new ArrayList<String>();
		updateSqlList = new ArrayList<String>();
		addSqlList = new ArrayList<String>();
		deleteIndexIdList = new ArrayList<Long>();
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

	public List<Long> getDeleteIndexIdList() {
		return deleteIndexIdList;
	}

	public void setDeleteIndexIdList(List<Long> deleteIndexIdList) {
		this.deleteIndexIdList = deleteIndexIdList;
	}

	@Override
	public String toString() {
		return "IndexSql [deleteSqlList=" + deleteSqlList + ", updateSqlList=" + updateSqlList + ", addSqlList="
				+ addSqlList + ", deleteIndexIdList=" + deleteIndexIdList + "]";
	}
}
