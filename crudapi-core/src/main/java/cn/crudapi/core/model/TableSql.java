package cn.crudapi.core.model;

import java.util.ArrayList;
import java.util.List;


public class TableSql {
	private List<String> sqlList;

	private ColumnSql columnSql;

	private IndexSql indexSql;

	public TableSql() {
		sqlList = new ArrayList<String>();
		columnSql = new ColumnSql();
		indexSql = new IndexSql();
	}

	public List<String> getSqlList() {
		return sqlList;
	}

	public void setSqlList(List<String> sqlList) {
		this.sqlList = sqlList;
	}

	public ColumnSql getColumnSql() {
		return columnSql;
	}

	public void setColumnSql(ColumnSql columnSql) {
		this.columnSql = columnSql;
	}

	public IndexSql getIndexSql() {
		return indexSql;
	}

	public void setIndexSql(IndexSql indexSql) {
		this.indexSql = indexSql;
	}

	@Override
	public String toString() {
		return "TableSql [sqlList=" + sqlList + ", columnSql=" + columnSql + ", indexSql=" + indexSql + "]";
	}
}
