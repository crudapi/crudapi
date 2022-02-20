package cn.crudapi.core.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.crudapi.core.config.CrudTemplate;
import cn.crudapi.core.entity.ColumnEntity;
import cn.crudapi.core.entity.IndexEntity;
import cn.crudapi.core.entity.TableEntity;
import cn.crudapi.core.enumeration.DataTypeEnum;
import cn.crudapi.core.enumeration.EngineEnum;
import cn.crudapi.core.query.Condition;
import cn.crudapi.core.service.CrudService;

@Service
public class CrudServiceImpl implements CrudService {
	private static final Logger log = LoggerFactory.getLogger(CrudServiceImpl.class);
	
	@Autowired
	private CrudTemplate crudTemplate;
	
	@Override
	public String toUpdateColumnSql(String tableName, String oldColumnName, ColumnEntity columnEntity) {
    	log.info("CrudServiceImpl->toUpdateColumnSql");
		return crudTemplate.toUpdateColumnSql(tableName, oldColumnName, columnEntity);
    }
       
	@Override
    public String toUpdateColumnIndexSql(String tableName, String oldIndexName, ColumnEntity columnEntity) {
    	log.info("CrudServiceImpl->toUpdateColumnIndexSql");
		return crudTemplate.toUpdateColumnIndexSql(tableName, oldIndexName, columnEntity);
    }
    
    @Override
    public String toDeleteColumnSql(String tableName, String columnName) {
    	log.info("CrudServiceImpl->toDeleteColumnSql");
		return crudTemplate.toDeleteColumnSql(tableName, columnName);
    }
    
    @Override
	public String toAddColumnSql(String tableName, ColumnEntity columnEntity) { 
		log.info("CrudServiceImpl->toAddColumnSql");
		return crudTemplate.toAddColumnSql(tableName, columnEntity);
	}
	
	@Override
	public String toUpdateIndexSql(String tableName, String oldIndexName, IndexEntity indexEntity) {
		log.info("CrudServiceImpl->toUpdateIndexSql");
		return crudTemplate.toUpdateIndexSql(tableName, oldIndexName, indexEntity);
	}
	
	@Override
	public String toDeleteIndexSql(String tableName, String oldIndexName) {
		log.info("CrudServiceImpl->toDeleteIndexSql");
		return crudTemplate.toDeleteIndexSql(tableName, oldIndexName);
	}
	
	@Override
	public String toRenameTableSql(String oldTableName, String newTableName) { 
		log.info("CrudServiceImpl->toRenameTableSql");
		return crudTemplate.toRenameTableSql(oldTableName, newTableName);
	}
	
	@Override
    public String toSetTableEngineSql(String tableName, EngineEnum engine) {
    	log.info("CrudServiceImpl->toSetTableEngineSql");
		return crudTemplate.toSetTableEngineSql(tableName, engine);
    }
	
	@Override
	public String toAddIndexSql(String tableName, IndexEntity indexEntity) { 
    	log.info("CrudServiceImpl->toAddIndexSql");
		return crudTemplate.toAddIndexSql(tableName, indexEntity);
    }
	
	@Override
	public String toCreateTableSql(TableEntity tableEntity) { 
		log.info("CrudServiceImpl->toCreateTableSql");
		return crudTemplate.toCreateTableSql(tableEntity);
	}
	
	@Override
	public List<String> toCreateIndexSqlList(TableEntity tableEntity) { 
		log.info("CrudServiceImpl->toCreateIndexSqlList");
		return crudTemplate.toCreateIndexSqlList(tableEntity);
	}
	
	@Override
	public String getSqlQuotation() {
		log.info("CrudServiceImpl->getSqlQuotation");
		return crudTemplate.getSqlQuotation();
	}
	
	@Override
	public Long create(String tableName, Object obj) {
		log.info("CrudServiceImpl->create");
		return crudTemplate.create(tableName, obj);
	}
	
	@Override
	public Map<String, Object> create(String tableName, Object obj, String[] keyColumnNames) {
		log.info("CrudServiceImpl->create");
		return crudTemplate.create(tableName, obj, keyColumnNames);
	}
	
	@Override
	public Long create(String tableName, Map<String, Object> map) {
		log.info("CrudServiceImpl->create");
		return crudTemplate.create(tableName, map);
	}
	
	@Override
	public Map<String, Object> create(String tableName, Map<String, Object> map, String[] keyColumnNames) {
		log.info("CrudServiceImpl->create");
		return crudTemplate.create(tableName, map, keyColumnNames);
	}
	
	@Override
	public int[] batchCreateMap(String tableName, List<Map<String, Object>> mapList) {
		log.info("CrudServiceImpl->batchCreateMap");
		return crudTemplate.batchCreateMap(tableName, mapList);
	}

	@Override
	public int[] batchCreateObj(String tableName, List<Object> objList) { 
		log.info("CrudServiceImpl->batchCreateObj");
		return crudTemplate.batchCreateObj(tableName, objList);
	}
	
	@Override
	public int[] batchPutMap(String tableName, List<Map<String, Object>> mapList) {
		log.info("CrudServiceImpl->batchPutMap");
		return crudTemplate.batchPutMap(tableName, mapList);
	}

	@Override
	public int[] batchPutObj(String tableName, List<Object> objList) { 
		log.info("CrudServiceImpl->batchPutObj");
		return crudTemplate.batchPutObj(tableName, objList);
	}
	
	@Override
	public void put(String tableName, Long id, Map<String, Object> dataMap) {
		log.info("CrudServiceImpl->put");
		crudTemplate.put(tableName, id, dataMap);
	}
	
	@Override
	public void put(String tableName, Map<String, Object> keyMap, Map<String, Object> dataMap) {
		log.info("CrudServiceImpl->put");
		crudTemplate.put(tableName, keyMap, dataMap);
	}
	
	@Override
	public void put(String tableName, Map<String, Object> keyMap, Object obj) {
		log.info("CrudServiceImpl->put");
		crudTemplate.put(tableName, keyMap, obj);
	}
	
	@Override
	public void put(String tableName, Long id, Object obj) {
		log.info("CrudServiceImpl->put");
		crudTemplate.put(tableName, id, obj);
	}
	
	@Override
	public void patch(String tableName, Long id, Map<String, Object> dataMap) {
		log.info("CrudServiceImpl->patch");
		crudTemplate.patch(tableName, id, dataMap);
	}
	
	@Override
	public void patch(String tableName, Map<String, Object> keyMap, Map<String, Object> dataMap) {
		log.info("CrudServiceImpl->patch");
		crudTemplate.patch(tableName, keyMap, dataMap);
	}
	
	@Override
	public void patch(String tableName, Map<String, Object> keyMap, Object obj) {
		log.info("CrudServiceImpl->patch");
		crudTemplate.patch(tableName, keyMap, obj);
	}
	
	@Override
	public void patch(String tableName, Long id, Object obj) {
		log.info("CrudServiceImpl->patch");
		crudTemplate.patch(tableName, id, obj);
	}
	
	@Override
	public int delete(String tableName) {
		log.info("CrudServiceImpl->delete");
		return crudTemplate.delete(tableName);
	}
	
	@Override
	public int delete(String tableName, Map<String, Object> keyMap) {
		log.info("CrudServiceImpl->delete");
		return crudTemplate.delete(tableName, keyMap);
	}
	
	@Override
	public int delete(String tableName, Long id) {
		log.info("CrudServiceImpl->delete");
		return crudTemplate.delete(tableName, id);
	}

	@Override
	public int delete(String tableName, Condition condition) {
		log.info("CrudServiceImpl->delete");
		return crudTemplate.delete(tableName, condition);
	}
	
	@Override
	public Map<String, Object> get(String tableName, Map<String, Object> keyMap) {
		log.info("CrudServiceImpl->get");
		return crudTemplate.get(tableName, keyMap);
	}
	
	@Override
	public Map<String, Object> get(String tableName, Long id) {
		log.info("CrudServiceImpl->get");
		return crudTemplate.get(tableName, id);
	}
	
	@Override
	public <T> T get(String tableName, Map<String, Object> keyMap, Class<T> classType) {
		log.info("CrudServiceImpl->get");
		return crudTemplate.get(tableName, keyMap, classType);
	}
	
	@Override
	public <T> T get(String tableName, Long id, Class<T> classType) {
		log.info("CrudServiceImpl->get");
		return crudTemplate.get(tableName, id, classType);
	}
	
	@Override
	public Long count(String tableName, Map<String, DataTypeEnum> dataTypeMap, Condition condition) {
		log.info("CrudServiceImpl->count");
		return crudTemplate.count(tableName, dataTypeMap, condition);
	}
	
	@Override
	public Long count(String tableName, Condition condition) {
		log.info("CrudServiceImpl->count");
		return crudTemplate.count(tableName, condition);
	}

	@Override
	public Long count(String tableName) { 
		log.info("CrudServiceImpl->count");
		return crudTemplate.count(tableName);
	}
	
	@Override
	public boolean isExistTable(String tableName) { 
		log.info("CrudServiceImpl->isExistTable");
		return crudTemplate.isExistTable(tableName);
	}
	
	@Override
	public void dropTable(String tableName) { 
		log.info("CrudTemplate->dropTable");
		crudTemplate.dropTable(tableName);
	}
	
	@Override
	public List<Map<String, Object>> list(String tableName, Map<String, DataTypeEnum> dataTypeMap, List<String> selectNameList, Condition condition, String orderby, Integer offset, Integer limit) {
		log.info("CrudServiceImpl->list");
		return crudTemplate.list(tableName, dataTypeMap, selectNameList, condition, orderby, offset, limit);
	}
	
	@Override
	public List<Map<String, Object>> list(String tableName, Map<String, DataTypeEnum> dataTypeMap, Condition condition, String orderby, Integer offset, Integer limit) {
		log.info("CrudServiceImpl->list");
		return crudTemplate.list(tableName, dataTypeMap, condition, orderby, offset, limit);
	}

	@Override
	public List<Map<String, Object>> list(String tableName, String orderby, Integer offset, Integer limit) {
		log.info("CrudServiceImpl->list");
		return crudTemplate.list(tableName, orderby, offset, limit);
	}

	@Override
	public List<Map<String, Object>> list(String tableName) {
		log.info("CrudServiceImpl->list");
		return crudTemplate.list(tableName);
	}

	@Override
	public <T> List<T> list(String tableName, Condition condition, String orderby, Integer offset, Integer limit, Class<T> classType) {
		log.info("CrudServiceImpl->list");
		return crudTemplate.list(tableName, condition, orderby, offset, limit, classType);
	}
	
	@Override
	public <T> List<T> list(String tableName, String orderby, Integer offset, Integer limit, Class<T> classType) { 
		log.info("CrudServiceImpl->list");
		return crudTemplate.list(tableName, orderby, offset, limit, classType);
	}
	
	@Override
	public <T> List<T> list(String tableName, Class<T> classType) { 
		log.info("CrudServiceImpl->list");
		return crudTemplate.list(tableName, classType);
	}
	
	@Override
	public JdbcTemplate getJdbcTemplate() {
		log.info("CrudServiceImpl->getJdbcTemplate");
		return crudTemplate.getJdbcTemplate();
	}
	
	@Override
	public void execute(String sql) { 
		log.info("CrudServiceImpl->execute");
		crudTemplate.execute(sql);
	}
	
	@Override
	public List<Map<String, Object>> getMetaDatas() {
		log.info("CrudServiceImpl->getMetaDatas");
		return crudTemplate.getMetaDatas();
	}
	
	@Override
	public Map<String, Object> getMetaData(String tableName) {
		log.info("CrudServiceImpl->getMetaData");
		return crudTemplate.getMetaData(tableName);
	}
}
