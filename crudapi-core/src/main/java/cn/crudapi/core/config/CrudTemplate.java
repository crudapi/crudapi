package cn.crudapi.core.config;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;

import cn.crudapi.core.entity.ColumnEntity;
import cn.crudapi.core.entity.IndexEntity;
import cn.crudapi.core.entity.TableEntity;
import cn.crudapi.core.enumeration.DataTypeEnum;
import cn.crudapi.core.enumeration.EngineEnum;
import cn.crudapi.core.query.Condition;
import cn.crudapi.core.repository.CrudAbstractFactory;
import cn.crudapi.core.repository.CrudAbstractRepository;

public class CrudTemplate {
	private static final Logger log = LoggerFactory.getLogger(CrudTemplate.class);
	
	@Nullable
	private volatile CrudAbstractFactory crudFactory;

	public CrudTemplate() {
		super();
		log.info("CrudTemplate->Constructor");
	}
	
	public CrudTemplate(CrudAbstractFactory crudFactory) {
		super();
		log.info("CrudTemplate->Constructor crudFactory");
		this.crudFactory = crudFactory;
	}
	
	public String toUpdateColumnSql(String tableName, String oldColumnName, ColumnEntity columnEntity) {
    	log.info("CrudTemplate->toUpdateColumnSql");
		return crudFactory.toUpdateColumnSql(tableName, oldColumnName, columnEntity);
    }
        
    public String toUpdateColumnIndexSql(String tableName, String oldIndexName, ColumnEntity columnEntity) {
    	log.info("CrudTemplate->toUpdateColumnIndexSql");
		return crudFactory.toUpdateColumnIndexSql(tableName, oldIndexName, columnEntity);
    }
    
    public String toDeleteColumnSql(String tableName, String columnName) {
    	log.info("CrudTemplate->toDeleteColumnSql");
		return crudFactory.toDeleteColumnSql(tableName, columnName);
    }
    
	public String toAddColumnSql(String tableName, ColumnEntity columnEntity) { 
		log.info("CrudTemplate->toAddColumnSql");
		return crudFactory.toAddColumnSql(tableName, columnEntity);
	}
	
	public String toUpdateIndexSql(String tableName, String oldIndexName, IndexEntity indexEntity) {
		log.info("CrudTemplate->toUpdateIndexSql");
		return crudFactory.toUpdateIndexSql(tableName, oldIndexName, indexEntity);
	}
	
	public String toDeleteIndexSql(String tableName, String oldIndexName) {
		log.info("CrudTemplate->toDeleteIndexSql");
		return crudFactory.toDeleteIndexSql(tableName, oldIndexName);
	}
	
	public String toRenameTableSql(String oldTableName, String newTableName) { 
		log.info("CrudTemplate->toRenameTableSql");
		return crudFactory.toRenameTableSql(oldTableName, newTableName);
	}
	
    public String toSetTableEngineSql(String tableName, EngineEnum engine) {
    	log.info("CrudTemplate->toSetTableEngineSql");
		return crudFactory.toSetTableEngineSql(tableName, engine);
    }
    
    public String toAddIndexSql(String tableName, IndexEntity indexEntity) { 
    	log.info("CrudTemplate->toAddIndexSql");
		return crudFactory.toAddIndexSql(tableName, indexEntity);
    }
	
	public String toCreateTableSql(TableEntity tableEntity) { 
		log.info("CrudTemplate->toCreateTableSql");
		return crudFactory.toCreateTableSql(tableEntity);
	}
	
	public List<String> toCreateIndexSqlList(TableEntity tableEntity) { 
		log.info("CrudTemplate->toCreateIndexSqlList");
		return crudFactory.toCreateIndexSqlList(tableEntity);
	}
	
	public String getSqlQuotation() { 
		log.info("CrudTemplate->getSqlQuotation");
		return crudFactory.getSqlQuotation();
	}
	
	public Long create(String tableName, Object obj) {
		log.info("CrudTemplate->create");
		return crudFactory.create(tableName, obj);
	}
	
	public Map<String, Object> create(String tableName, Object obj, String[] keyColumnNames) {
		log.info("CrudTemplate->create");
		return crudFactory.create(tableName, obj, keyColumnNames);
	}
	
	public Long create(String tableName, Map<String, Object> map) {
		log.info("CrudTemplate->create");
		return crudFactory.create(tableName, map);
	}
	
	public Map<String, Object> create(String tableName, Map<String, Object> map, String[] keyColumnNames) {
		log.info("CrudTemplate->create");
		return crudFactory.create(tableName, map, keyColumnNames);
	}
	
	public int[] batchCreateMap(String tableName, List<Map<String, Object>> mapList) {
		log.info("CrudTemplate->batchCreateMap");
		return crudFactory.batchCreateMap(tableName, mapList);
	}

	public int[] batchCreateObj(String tableName, List<Object> objList) { 
		log.info("CrudTemplate->batchCreateObj");
		return crudFactory.batchCreateObj(tableName, objList);
	}
	
	public int[] batchPutMap(String tableName, List<Map<String, Object>> mapList) {
		log.info("CrudTemplate->batchPutMap");
		return crudFactory.batchPutMap(tableName, mapList);
	}

	public int[] batchPutObj(String tableName, List<Object> objList) { 
		log.info("CrudTemplate->batchPutObj");
		return crudFactory.batchPutObj(tableName, objList);
	}
	
	public void put(String tableName, Long id, Map<String, Object> dataMap) {
		log.info("CrudTemplate->put");
		crudFactory.put(tableName, id, dataMap);
	}
	
	public void put(String tableName, Map<String, Object> keyMap, Map<String, Object> dataMap) {
		log.info("CrudTemplate->put");
		crudFactory.put(tableName, keyMap, dataMap);
	}
	
	public void put(String tableName, Map<String, Object> keyMap, Object obj) {
		log.info("CrudTemplate->put");
		crudFactory.put(tableName, keyMap, obj);
	}
	
	public void put(String tableName, Long id, Object obj) {
		log.info("CrudTemplate->put");
		crudFactory.put(tableName, id, obj);
	}
	
	public void patch(String tableName, Long id, Map<String, Object> dataMap) {
		log.info("CrudTemplate->patch");
		crudFactory.patch(tableName, id, dataMap);
	}
	
	public void patch(String tableName, Map<String, Object> keyMap, Map<String, Object> dataMap) {
		log.info("CrudTemplate->patch");
		crudFactory.patch(tableName, keyMap, dataMap);
	}
	
	public void patch(String tableName, Map<String, Object> keyMap, Object obj) {
		log.info("CrudTemplate->patch");
		crudFactory.patch(tableName, keyMap, obj);
	}
	
	public void patch(String tableName, Long id, Object obj) {
		log.info("CrudTemplate->patch");
		crudFactory.patch(tableName, id, obj);
	}
	
	public int delete(String tableName) {
		log.info("CrudTemplate->delete");
		return crudFactory.delete(tableName);
	}
	
	public int delete(String tableName, Map<String, Object> keyMap) {
		log.info("CrudTemplate->delete");
		return crudFactory.delete(tableName, keyMap);
	}
	
	public int delete(String tableName, Long id) {
		log.info("CrudTemplate->delete");
		return crudFactory.delete(tableName, id);
	}
	
	public int delete(String tableName, Condition condition) {
		log.info("CrudTemplate->delete");
		return crudFactory.delete(tableName, condition);
	}
	
	public Map<String, Object> get(String tableName, Map<String, Object> keyMap) {
		log.info("CrudTemplate->get");
		return crudFactory.get(tableName, keyMap);
	}
	
	public Map<String, Object> get(String tableName, Long id) {
		log.info("CrudTemplate->get");
		return crudFactory.get(tableName, id);
	}
	
	public <T> T get(String tableName, Map<String, Object> keyMap, Class<T> classType) {
		log.info("CrudTemplate->get");
		return crudFactory.get(tableName, keyMap, classType);
	}
	
	public <T> T get(String tableName, Long id, Class<T> classType) {
		log.info("CrudTemplate->get");
		return crudFactory.get(tableName, id, classType);
	}
	
	public Long count(String tableName, Map<String, DataTypeEnum> dataTypeMap, Condition condition) { 
		log.info("CrudTemplate->count");
		return crudFactory.count(tableName, dataTypeMap, condition);
	}
	
	public Long count(String tableName, Condition condition) { 
		log.info("CrudTemplate->count");
		return crudFactory.count(tableName, condition);
	}
	
	public Long count(String tableName) { 
		log.info("CrudTemplate->count");
		return crudFactory.count(tableName);
	}
	
	public boolean isExistTable(String tableName) { 
		log.info("CrudTemplate->isExistTable");
		return crudFactory.isExistTable(tableName);
	}
	
	public void dropTable(String tableName) { 
		log.info("CrudTemplate->dropTable");
		crudFactory.dropTable(tableName);
	}
	
	public List<Map<String, Object>> list(String tableName, Map<String, DataTypeEnum> dataTypeMap, List<String> selectNameList, Condition condition, String orderby, Integer offset, Integer limit) {
		log.info("CrudTemplate->list");
		return crudFactory.list(tableName, dataTypeMap, selectNameList, condition, orderby, offset, limit);
	}
	
	public List<Map<String, Object>> list(String tableName, Map<String, DataTypeEnum> dataTypeMap, Condition condition, String orderby, Integer offset, Integer limit) {
		log.info("CrudTemplate->list");
		return crudFactory.list(tableName, dataTypeMap, condition, orderby, offset, limit);
	}
	
	public List<Map<String, Object>> list(String tableName, String orderby, Integer offset, Integer limit) {
		log.info("CrudTemplate->list");
		return crudFactory.list(tableName, orderby, offset, limit);
	}

	public List<Map<String, Object>> list(String tableName) {
		log.info("CrudTemplate->list");
		return crudFactory.list(tableName);
	}
	
	public <T> List<T> list(String tableName, Condition condition, String orderby, Integer offset, Integer limit, Class<T> classType) { 
		log.info("CrudTemplate->list");
		return crudFactory.list(tableName, condition, orderby, offset, limit, classType);
	}
	
	public <T> List<T> list(String tableName, String orderby, Integer offset, Integer limit, Class<T> classType) { 
		log.info("CrudTemplate->list");
		return crudFactory.list(tableName, orderby, offset, limit, classType);
	}
	
	public <T> List<T> list(String tableName, Class<T> classType) { 
		log.info("CrudTemplate->list");
		return crudFactory.list(tableName, classType);
	}
	
	public JdbcTemplate getJdbcTemplate() {
		log.info("CrudTemplate->getJdbcTemplate");
		return crudFactory.getJdbcTemplate();
	}
	
	public void execute(String sql) { 
		log.info("CrudTemplate->execute");
		crudFactory.execute(sql);
	}
	
	public List<Map<String, Object>> getMetaDatas() {
		log.info("CrudTemplate->getMetaDatas");
		return crudFactory.getMetaDatas();
	}
	
	public Map<String, Object> getMetaData(String tableName) {
		log.info("CrudTemplate->getMetaData");
		return crudFactory.getMetaData(tableName);
	}
}
