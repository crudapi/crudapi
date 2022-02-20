package cn.crudapi.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import cn.crudapi.core.entity.ColumnEntity;
import cn.crudapi.core.entity.IndexEntity;
import cn.crudapi.core.entity.TableEntity;
import cn.crudapi.core.enumeration.DataTypeEnum;
import cn.crudapi.core.enumeration.EngineEnum;
import cn.crudapi.core.query.Condition;

public interface CrudService {
	String toUpdateColumnSql(String tableName, String oldColumnName, ColumnEntity columnEntity);
    
	String toUpdateColumnIndexSql(String tableName, String oldIndexName, ColumnEntity columnEntity);
    
    String toDeleteColumnSql(String tableName, String columnName);
    
    String toAddColumnSql(String tableName, ColumnEntity columnEntity);
	
    String toUpdateIndexSql(String tableName, String oldIndexName, IndexEntity indexEntity);
	
	String toDeleteIndexSql(String tableName, String oldIndexName);
	
	String toRenameTableSql(String oldTableName, String newTableName);
	
	String toSetTableEngineSql(String tableName, EngineEnum engine);
	
	String toAddIndexSql(String tableName, IndexEntity indexEntity);
	
	String toCreateTableSql(TableEntity tableEntity);
	
	List<String> toCreateIndexSqlList(TableEntity tableEntity);
	
	String getSqlQuotation();
	
	Long create(String tableName, Object obj);

	Map<String, Object> create(String tableName, Object obj, String[] keyColumnNames);

	Long create(String tableName, Map<String, Object> map);

	Map<String, Object> create(String tableName, Map<String, Object> map, String[] keyColumnNames);
	
	int[] batchCreateMap(String tableName, List<Map<String, Object>> mapList);
	
	int[] batchCreateObj(String tableName, List<Object> objList);

	void put(String tableName, Long id, Map<String, Object> dataMap);
	
	void put(String tableName, Map<String, Object> keyMap, Map<String, Object> dataMap);
	
	void put(String tableName, Map<String, Object> keyMap, Object obj);
	
	void put(String tableName, Long id, Object obj);
	
	int[] batchPutMap(String tableName, List<Map<String, Object>> mapList);
	
	int[] batchPutObj(String tableName, List<Object> objList);
	
	void patch(String tableName, Long id, Map<String, Object> dataMap);
	
	void patch(String tableName, Map<String, Object> keyMap, Map<String, Object> dataMap);
	
	void patch(String tableName, Map<String, Object> keyMap, Object obj);
	
	void patch(String tableName, Long id, Object obj);

	int delete(String tableName);
	
	int delete(String tableName, Map<String, Object> keyMap);
	
	int delete(String tableName, Long id);
	
	int delete(String tableName, Condition condition);
	
	Map<String, Object> get(String tableName, Map<String, Object> keyMap);
	
	Map<String, Object> get(String tableName, Long id);
	
	<T> T get(String tableName, Map<String, Object> keyMap, Class<T> classType);
	
	<T> T get(String tableName, Long id, Class<T> classType);
	
	Long count(String tableName, Map<String, DataTypeEnum> dataTypeMap, Condition condition);
	
	Long count(String tableName, Condition condition);
	
	Long count(String tableName);
	
	boolean isExistTable(String tableName);
	
	void dropTable(String tableName);
	
	List<Map<String, Object>> list(String tableName, Map<String, DataTypeEnum> dataTypeMap, List<String> selectNameList, Condition condition, String orderby, Integer offset, Integer limit);

	List<Map<String, Object>> list(String tableName, Map<String, DataTypeEnum> dataTypeMap, Condition condition, String orderby, Integer offset, Integer limit);

	List<Map<String, Object>> list(String tableName, String orderby, Integer offset, Integer limit);

	List<Map<String, Object>> list(String tableName);
	
	<T> List<T> list(String tableName, Condition condition, String orderby, Integer offset, Integer limit, Class<T> classType);

	<T> List<T> list(String tableName, String orderby, Integer offset, Integer limit, Class<T> classType);

	<T> List<T> list(String tableName, Class<T> classType);

	JdbcTemplate getJdbcTemplate();
	
	void execute(String sql);
	
	List<Map<String, Object>> getMetaDatas();
	
	Map<String, Object> getMetaData(String tableName);

}
