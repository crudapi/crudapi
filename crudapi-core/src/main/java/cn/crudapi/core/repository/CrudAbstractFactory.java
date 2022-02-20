package cn.crudapi.core.repository;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.crudapi.core.entity.ColumnEntity;
import cn.crudapi.core.entity.IndexEntity;
import cn.crudapi.core.entity.TableEntity;
import cn.crudapi.core.enumeration.DataTypeEnum;
import cn.crudapi.core.enumeration.EngineEnum;
import cn.crudapi.core.query.Condition;

public abstract class CrudAbstractFactory {
	private static final Logger log = LoggerFactory.getLogger(CrudAbstractFactory.class);
	
	public abstract CrudAbstractRepository getCrudRepository();
	
    public String toUpdateColumnSql(String tableName, String oldColumnName, ColumnEntity columnEntity) {
    	log.info("CrudAbstractFactory->toUpdateColumnSql");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.toUpdateColumnSql(tableName, oldColumnName, columnEntity);
    }
        
    public String toUpdateColumnIndexSql(String tableName, String oldIndexName, ColumnEntity columnEntity) {
    	log.info("CrudAbstractFactory->toUpdateColumnIndexSql");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.toUpdateColumnIndexSql(tableName, oldIndexName, columnEntity);
    }
    
    public String toDeleteColumnSql(String tableName, String columnName) {
    	log.info("CrudAbstractFactory->toDeleteColumnSql");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.toDeleteColumnSql(tableName, columnName);
    }
    
	public String toAddColumnSql(String tableName, ColumnEntity columnEntity) { 
		log.info("CrudAbstractFactory->toAddColumnSql");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.toAddColumnSql(tableName, columnEntity);
	}
	
	public String toUpdateIndexSql(String tableName, String oldIndexName, IndexEntity indexEntity) {
		log.info("CrudAbstractFactory->toUpdateIndexSql");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.toUpdateIndexSql(tableName, oldIndexName, indexEntity);
	}
	
	public String toDeleteIndexSql(String tableName, String oldIndexName) {
		log.info("CrudAbstractFactory->toDeleteIndexSql");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.toDeleteIndexSql(tableName, oldIndexName);
	}
	
	public String toRenameTableSql(String oldTableName, String newTableName) { 
		log.info("CrudAbstractFactory->toRenameTableSql");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.toRenameTableSql(oldTableName, newTableName);
	}
	
    public String toSetTableEngineSql(String tableName, EngineEnum engine) {
    	log.info("CrudAbstractFactory->toSetTableEngineSql");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.toSetTableEngineSql(tableName, engine);
    }
    
    public String toAddIndexSql(String tableName, IndexEntity indexEntity) { 
    	log.info("CrudAbstractFactory->toAddIndexSql");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.toAddIndexSql(tableName, indexEntity);
    }
	
	public String toCreateTableSql(TableEntity tableEntity) { 
		log.info("CrudAbstractFactory->toCreateTableSql");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.toCreateTableSql(tableEntity);
	}
	
	public List<String> toCreateIndexSqlList(TableEntity tableEntity) { 
		log.info("CrudAbstractFactory->toCreateIndexSqlList");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.toCreateIndexSqlList(tableEntity);
	}
	
	public String getSqlQuotation() { 
		log.info("CrudAbstractFactory->getSqlQuotation");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.getSqlQuotation();
	}
	
	public Long create(String tableName, Object obj) {
		log.info("CrudAbstractFactory->create");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.create(tableName, obj);
	}
	
	public Map<String, Object> create(String tableName, Object obj, String[] keyColumnNames) {
		log.info("CrudAbstractFactory->create");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.create(tableName, obj, keyColumnNames);
	}
	
	public Long create(String tableName, Map<String, Object> map) {
		log.info("CrudAbstractFactory->create");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.create(tableName, map);
	}
	
	public Map<String, Object> create(String tableName, Map<String, Object> map, String[] keyColumnNames) {
		log.info("CrudAbstractFactory->create");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.create(tableName, map, keyColumnNames);
	}
	
	public int[] batchCreateMap(String tableName, List<Map<String, Object>> mapList) {
		log.info("CrudAbstractFactory->batchCreateMap");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.batchCreateMap(tableName, mapList);
	}

	public int[] batchCreateObj(String tableName, List<Object> objList) { 
		log.info("CrudAbstractFactory->batchCreateObj");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.batchCreateObj(tableName, objList);
	}
	
	public void put(String tableName, Long id, Map<String, Object> dataMap) {
		log.info("CrudAbstractFactory->put");
		CrudAbstractRepository repository = this.getCrudRepository();
		repository.put(tableName, id, dataMap);
	}
	
	public void put(String tableName, Map<String, Object> keyMap, Map<String, Object> dataMap) {
		log.info("CrudAbstractFactory->put");
		CrudAbstractRepository repository = this.getCrudRepository();
		repository.put(tableName, keyMap, dataMap);
	}
	
	public void put(String tableName, Map<String, Object> keyMap, Object obj) {
		log.info("CrudAbstractFactory->put");
		CrudAbstractRepository repository = this.getCrudRepository();
		repository.put(tableName, keyMap, obj);
	}
	
	public void put(String tableName, Long id, Object obj) {
		log.info("CrudAbstractFactory->put");
		CrudAbstractRepository repository = this.getCrudRepository();
		repository.put(tableName, id, obj);
	}
	
	public int[] batchPutMap(String tableName, List<Map<String, Object>> mapList) {
		log.info("CrudAbstractFactory->batchPutMap");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.batchPutMap(tableName, mapList);
	}

	public int[] batchPutObj(String tableName, List<Object> objList) { 
		log.info("CrudAbstractFactory->batchPutObj");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.batchPutObj(tableName, objList);
	}
	
	public void patch(String tableName, Long id, Map<String, Object> dataMap) {
		log.info("CrudAbstractFactory->patch");
		CrudAbstractRepository repository = this.getCrudRepository();
		repository.patch(tableName, id, dataMap);
	}
	
	public void patch(String tableName, Map<String, Object> keyMap, Map<String, Object> dataMap) {
		log.info("CrudAbstractFactory->patch");
		CrudAbstractRepository repository = this.getCrudRepository();
		repository.patch(tableName, keyMap, dataMap);
	}
	
	public void patch(String tableName, Map<String, Object> keyMap, Object obj) {
		log.info("CrudAbstractFactory->patch");
		CrudAbstractRepository repository = this.getCrudRepository();
		repository.patch(tableName, keyMap, obj);
	}
	
	public void patch(String tableName, Long id, Object obj) {
		log.info("CrudAbstractFactory->patch");
		CrudAbstractRepository repository = this.getCrudRepository();
		repository.patch(tableName, id, obj);
	}
	
	public int delete(String tableName) {
		log.info("CrudAbstractFactory->delete");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.delete(tableName);
	}
		
	public int delete(String tableName, Map<String, Object> keyMap) {
		log.info("CrudAbstractFactory->delete");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.delete(tableName, keyMap);
	}
	
	public int delete(String tableName, Long id) {
		log.info("CrudAbstractFactory->delete");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.delete(tableName, id);
	}
	
	public int delete(String tableName, Condition condition) {
		log.info("CrudAbstractFactory->delete");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.delete(tableName, condition);
	}
	
	public Map<String, Object> get(String tableName, Map<String, Object> keyMap) {
		log.info("CrudAbstractFactory->get");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.get(tableName, keyMap);
	}
	
	public Map<String, Object> get(String tableName, Long id) {
		log.info("CrudAbstractFactory->get");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.get(tableName, id);
	}
	
	public <T> T get(String tableName, Map<String, Object> keyMap, Class<T> classType) {
		log.info("CrudAbstractFactory->get");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.get(tableName, keyMap, classType);
	}
	
	public <T> T get(String tableName, Long id, Class<T> classType) {
		log.info("CrudAbstractFactory->get");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.get(tableName, id, classType);
	}
	
	public Long count(String tableName, Map<String, DataTypeEnum> dataTypeMap, Condition condition) { 
		log.info("CrudAbstractFactory->count");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.count(tableName, dataTypeMap, condition);
	}
	
	public Long count(String tableName, Condition condition) { 
		log.info("CrudAbstractFactory->count");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.count(tableName, condition);
	}
	
	public Long count(String tableName) { 
		log.info("CrudAbstractFactory->count");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.count(tableName);
	}
	
	public boolean isExistTable(String tableName) {
		log.info("CrudAbstractFactory->isExistTable");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.isExistTable(tableName);
	}
	
	public void dropTable(String tableName) { 
		log.info("CrudAbstractFactory->dropTable");
		CrudAbstractRepository repository = this.getCrudRepository();
		repository.dropTable(tableName);
	}
	
	public List<Map<String, Object>> list(String tableName, Map<String, DataTypeEnum> dataTypeMap, List<String> selectNameList, Condition condition, String orderby, Integer offset, Integer limit) {
		log.info("CrudAbstractFactory->list");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.list(tableName, dataTypeMap, selectNameList, condition, orderby, offset, limit);
	}
	
	public List<Map<String, Object>> list(String tableName, Map<String, DataTypeEnum> dataTypeMap, Condition condition, String orderby, Integer offset, Integer limit) {
		log.info("CrudAbstractFactory->list");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.list(tableName, dataTypeMap, condition, orderby, offset, limit);
	}
	
	public List<Map<String, Object>> list(String tableName, String orderby, Integer offset, Integer limit) {
		log.info("CrudAbstractFactory->list");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.list(tableName, orderby, offset, limit);
	}
	
	public List<Map<String, Object>> list(String tableName) {
		log.info("CrudAbstractFactory->list");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.list(tableName);
	}

	public <T> List<T> list(String tableName, Condition condition, String orderby, Integer offset, Integer limit, Class<T> classType) { 
		log.info("CrudAbstractFactory->list");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.list(tableName, condition, orderby, offset, limit, classType);
	}
	
	public <T> List<T> list(String tableName, String orderby, Integer offset, Integer limit, Class<T> classType) { 
		log.info("CrudAbstractFactory->list");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.list(tableName, orderby, offset, limit, classType);
	}
	
	public <T> List<T> list(String tableName, Class<T> classType) { 
		log.info("CrudAbstractFactory->list");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.list(tableName, classType);
	}
	
	public JdbcTemplate getJdbcTemplate() {
		log.info("CrudAbstractFactory->getJdbcTemplate");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.getJdbcTemplate();
	}
	
	public void execute(String sql) { 
		log.info("CrudAbstractFactory->execute");
		CrudAbstractRepository repository = this.getCrudRepository();
		repository.execute(sql);
	}
	
	public List<Map<String, Object>> getMetaDatas() {
		log.info("CrudAbstractFactory->getMetaDatas");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.getMetaDatas();
	}
	
	public Map<String, Object> getMetaData(String tableName) {
		log.info("CrudAbstractFactory->getMetaData");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.getMetaData(tableName);
	}
}
