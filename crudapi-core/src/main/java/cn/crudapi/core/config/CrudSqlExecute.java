package cn.crudapi.core.config;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;

import cn.crudapi.core.dto.TableDTO;
import cn.crudapi.core.entity.ColumnEntity;
import cn.crudapi.core.entity.IndexEntity;
import cn.crudapi.core.entity.TableEntity;
import cn.crudapi.core.enumeration.DataTypeEnum;
import cn.crudapi.core.enumeration.EngineEnum;
import cn.crudapi.core.enumeration.IndexTypeEnum;
import cn.crudapi.core.query.Condition;
import cn.crudapi.core.repository.CrudAbstractFactory;

public class CrudSqlExecute {
	private static final Logger log = LoggerFactory.getLogger(CrudSqlExecute.class);
	
	@Nullable
	private volatile CrudAbstractFactory crudFactory;

	public CrudSqlExecute() {
		super();
		log.debug("CrudSqlExecute->Constructor");
	}
	
	public CrudSqlExecute(CrudAbstractFactory crudFactory) {
		super();
		log.debug("CrudSqlExecute->Constructor crudFactory");
		this.crudFactory = crudFactory;
	}
	
	
	public CrudAbstractFactory getCrudFactory() {
		return crudFactory;
	}

	public String toUpdateColumnSql(TableEntity tableEntity, ColumnEntity oldColumnEntity, ColumnEntity columnEntity) {
    	log.debug("CrudSqlExecute->toUpdateColumnSql");
		return crudFactory.toUpdateColumnSql(tableEntity, oldColumnEntity, columnEntity);
    }
        
    public String toUpdateColumnIndexSql(TableEntity tableEntity, ColumnEntity oldColumnEntity, ColumnEntity columnEntity) {
    	log.debug("CrudSqlExecute->toUpdateColumnIndexSql");
		return crudFactory.toUpdateColumnIndexSql(tableEntity, oldColumnEntity, columnEntity);
    }
    
    public List<String> toDeleteColumnSql(TableEntity tableEntity, ColumnEntity columnEntity) {
    	log.debug("CrudSqlExecute->toDeleteColumnSql");
		return crudFactory.toDeleteColumnSql(tableEntity, columnEntity);
    }
    
	public List<String> toAddColumnSql(TableEntity tableEntity, ColumnEntity columnEntity) { 
		log.debug("CrudSqlExecute->toAddColumnSql");
		return crudFactory.toAddColumnSql(tableEntity, columnEntity);
	}
	
	public String toUpdateIndexSql(String tableName, IndexTypeEnum oldIndexType, String oldIndexName, IndexEntity indexEntity) {
		log.debug("CrudSqlExecute->toUpdateIndexSql");
		return crudFactory.toUpdateIndexSql(tableName, oldIndexType, oldIndexName, indexEntity);
	}
	
	public String toDeleteIndexSql(String tableName, IndexTypeEnum oldIndexType, String oldIndexName) {
		log.debug("CrudSqlExecute->toDeleteIndexSql");
		return crudFactory.toDeleteIndexSql(tableName, oldIndexType, oldIndexName);
	}
	
	public String toRenameTableSql(String oldTableName, String newTableName) { 
		log.debug("CrudSqlExecute->toRenameTableSql");
		return crudFactory.toRenameTableSql(oldTableName, newTableName);
	}
	
    public String toSetTableEngineSql(String tableName, EngineEnum engine) {
    	log.debug("CrudSqlExecute->toSetTableEngineSql");
		return crudFactory.toSetTableEngineSql(tableName, engine);
    }
    
    public String toAddIndexSql(String tableName, IndexEntity indexEntity) { 
    	log.debug("CrudSqlExecute->toAddIndexSql");
		return crudFactory.toAddIndexSql(tableName, indexEntity);
    }
	
	public List<String> toCreateTableSql(TableEntity tableEntity) { 
		log.debug("CrudSqlExecute->toCreateTableSql");
		return crudFactory.toCreateTableSql(tableEntity);
	}
	
	public String getSqlQuotation() { 
		log.debug("CrudSqlExecute->getSqlQuotation");
		return crudFactory.getSqlQuotation();
	}
	
	public String getDateBaseName() { 
		log.debug("CrudSqlExecute->getDateBaseName");
		return crudFactory.getDateBaseName();
	}
	
	public Long create(String tableName, Object obj) {
		log.debug("CrudSqlExecute->create");
		return crudFactory.create(tableName, obj);
	}
	
	public Map<String, Object> create(String tableName, Object obj, String[] keyColumnNames, boolean autoIncrement) {
		log.debug("CrudSqlExecute->create");
		return crudFactory.create(tableName, obj, keyColumnNames, autoIncrement);
	}
	
	public Long create(String tableName, Map<String, Object> map) {
		log.debug("CrudSqlExecute->create");
		return crudFactory.create(tableName, map);
	}
	
	public Map<String, Object> create(String tableName, Map<String, Object> map, String[] keyColumnNames, boolean autoIncrement) {
		log.debug("CrudSqlExecute->create");
		return crudFactory.create(tableName, map, keyColumnNames, autoIncrement);
	}
	
	public int[] batchCreateMap(String tableName, List<Map<String, Object>> mapList) {
		log.debug("CrudSqlExecute->batchCreateMap");
		return crudFactory.batchCreateMap(tableName, mapList);
	}

	public int[] batchCreateObj(String tableName, List<Object> objList) { 
		log.debug("CrudSqlExecute->batchCreateObj");
		return crudFactory.batchCreateObj(tableName, objList);
	}
	
	public int[] batchPutMap(String tableName, List<Map<String, Object>> mapList) {
		log.debug("CrudSqlExecute->batchPutMap");
		return crudFactory.batchPutMap(tableName, mapList);
	}

	public int[] batchPutObj(String tableName, List<Object> objList) { 
		log.debug("CrudSqlExecute->batchPutObj");
		return crudFactory.batchPutObj(tableName, objList);
	}
	
	public void put(String tableName, Long id, Map<String, Object> dataMap) {
		log.debug("CrudSqlExecute->put");
		crudFactory.put(tableName, id, dataMap);
	}
	
	public void put(String tableName, Map<String, Object> keyMap, Map<String, Object> dataMap) {
		log.debug("CrudSqlExecute->put");
		crudFactory.put(tableName, keyMap, dataMap);
	}
	
	public void put(String tableName, Map<String, Object> keyMap, Object obj) {
		log.debug("CrudSqlExecute->put");
		crudFactory.put(tableName, keyMap, obj);
	}
	
	public void put(String tableName, Long id, Object obj) {
		log.debug("CrudSqlExecute->put");
		crudFactory.put(tableName, id, obj);
	}
	
	public void patch(String tableName, Long id, Map<String, Object> dataMap) {
		log.debug("CrudSqlExecute->patch");
		crudFactory.patch(tableName, id, dataMap);
	}
	
	public void patch(String tableName, Map<String, Object> keyMap, Map<String, Object> dataMap) {
		log.debug("CrudSqlExecute->patch");
		crudFactory.patch(tableName, keyMap, dataMap);
	}
	
	public void patch(String tableName, Map<String, Object> keyMap, Object obj) {
		log.debug("CrudSqlExecute->patch");
		crudFactory.patch(tableName, keyMap, obj);
	}
	
	public void patch(String tableName, Long id, Object obj) {
		log.debug("CrudSqlExecute->patch");
		crudFactory.patch(tableName, id, obj);
	}
	
	public int delete(String tableName) {
		log.debug("CrudSqlExecute->delete");
		return crudFactory.delete(tableName);
	}
	
	public int delete(String tableName, Map<String, Object> keyMap) {
		log.debug("CrudSqlExecute->delete");
		return crudFactory.delete(tableName, keyMap);
	}
	
	public int delete(String tableName, Long id) {
		log.debug("CrudSqlExecute->delete");
		return crudFactory.delete(tableName, id);
	}
	
	public int delete(String tableName, Condition condition) {
		log.debug("CrudSqlExecute->delete");
		return crudFactory.delete(tableName, condition);
	}
	
	public Map<String, Object> get(String tableName, Map<String, Object> keyMap) {
		log.debug("CrudSqlExecute->get");
		return crudFactory.get(tableName, keyMap);
	}
	
	public Map<String, Object> get(String tableName, Long id) {
		log.debug("CrudSqlExecute->get");
		return crudFactory.get(tableName, id);
	}
	
	public Map<String, Object> getForUpdate(String tableName, Long id) {
		log.debug("CrudSqlExecute->getForUpdate");
		return crudFactory.getForUpdate(tableName, id);
	}
	
	
	public <T> T get(String tableName, Map<String, Object> keyMap, Class<T> classType) {
		log.debug("CrudSqlExecute->get");
		return crudFactory.get(tableName, keyMap, classType);
	}
	
	public <T> T get(String tableName, Long id, Class<T> classType) {
		log.debug("CrudSqlExecute->get");
		return crudFactory.get(tableName, id, classType);
	}
	
	public Long count(String tableName, Map<String, DataTypeEnum> dataTypeMap, Condition condition) { 
		log.debug("CrudSqlExecute->count");
		return crudFactory.count(tableName, dataTypeMap, condition);
	}
	
	public Long count(String tableName, Condition condition) { 
		log.debug("CrudSqlExecute->count");
		return crudFactory.count(tableName, condition);
	}
	
	public Long count(String tableName) { 
		log.debug("CrudSqlExecute->count");
		return crudFactory.count(tableName);
	}
	
	public boolean isExistTable(String tableName) { 
		log.debug("CrudSqlExecute->isExistTable");
		return crudFactory.isExistTable(tableName);
	}
	
	public void dropTable(TableEntity tableEntity) { 
		log.debug("CrudSqlExecute->dropTable");
		crudFactory.dropTable(tableEntity);
	}
	
	public List<Map<String, Object>> list(String tableName, Map<String, DataTypeEnum> dataTypeMap, List<String> selectNameList, Condition condition, String orderby, Integer offset, Integer limit) {
		log.debug("CrudSqlExecute->list");
		return crudFactory.list(tableName, dataTypeMap, selectNameList, condition, orderby, offset, limit);
	}
	
	public List<Map<String, Object>> list(String tableName, Map<String, DataTypeEnum> dataTypeMap, Condition condition, String orderby, Integer offset, Integer limit) {
		log.debug("CrudSqlExecute->list");
		return crudFactory.list(tableName, dataTypeMap, condition, orderby, offset, limit);
	}
	
	public List<Map<String, Object>> list(String tableName, String orderby, Integer offset, Integer limit) {
		log.debug("CrudSqlExecute->list");
		return crudFactory.list(tableName, orderby, offset, limit);
	}

	public List<Map<String, Object>> list(String tableName) {
		log.debug("CrudSqlExecute->list");
		return crudFactory.list(tableName);
	}
	
	public <T> List<T> list(String tableName, Condition condition, String orderby, Integer offset, Integer limit, Class<T> classType) { 
		log.debug("CrudSqlExecute->list");
		return crudFactory.list(tableName, condition, orderby, offset, limit, classType);
	}
	
	public <T> List<T> list(String tableName, String orderby, Integer offset, Integer limit, Class<T> classType) { 
		log.debug("CrudSqlExecute->list");
		return crudFactory.list(tableName, orderby, offset, limit, classType);
	}
	
	public <T> List<T> list(String tableName, Class<T> classType) { 
		log.debug("CrudSqlExecute->list");
		return crudFactory.list(tableName, classType);
	}
	
	public JdbcTemplate getJdbcTemplate() {
		log.debug("CrudSqlExecute->getJdbcTemplate");
		return crudFactory.getJdbcTemplate();
	}
	
	public void execute(String sql) { 
		log.debug("CrudSqlExecute->execute");
		crudFactory.execute(sql);
	}
	
	public List<Map<String, Object>> getMetaDatas() {
		log.debug("CrudSqlExecute->getMetaDatas");
		return crudFactory.getMetaDatas();
	}
	
	public Map<String, Object> getMetaData(String tableName) {
		log.debug("CrudSqlExecute->getMetaData");
		return crudFactory.getMetaData(tableName);
	}
	
	public TableDTO reverseMetaData(String tableName) {
		log.debug("CrudServiceImpl->reverseMetaData");
		return crudFactory.reverseMetaData(tableName);
	}
}
