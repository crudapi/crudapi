package cn.crudapi.core.entity;

import java.util.List;

public interface BaseEntity {
	Long getRecId();
	String getDataBaseTableName();
	List<String> getColumnNames(Boolean isUpdate);
	List<Object> getColumnValues(Boolean isUpdate);
	List<String> getColumnNamesIgnoreNull(Boolean isUpdate);
	List<Object> getColumnValuesIgnoreNull(Boolean isUpdate);
}
