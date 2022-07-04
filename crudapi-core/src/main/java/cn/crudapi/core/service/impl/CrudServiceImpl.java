package cn.crudapi.core.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.crudapi.core.config.CrudTemplate;
import cn.crudapi.core.dto.TableDTO;
import cn.crudapi.core.entity.ColumnEntity;
import cn.crudapi.core.entity.IndexEntity;
import cn.crudapi.core.entity.TableEntity;
import cn.crudapi.core.enumeration.DataTypeEnum;
import cn.crudapi.core.enumeration.EngineEnum;
import cn.crudapi.core.enumeration.IndexTypeEnum;
import cn.crudapi.core.query.Condition;
import cn.crudapi.core.service.CrudService;

@Service
public class CrudServiceImpl implements CrudService {
	private static final Logger log = LoggerFactory.getLogger(CrudServiceImpl.class);
	
	@Autowired
	private CrudTemplate crudTemplate;
	
	@Override
	public String toUpdateColumnSql(TableEntity tableEntity, ColumnEntity oldColumnEntity,  ColumnEntity columnEntity) {
    	log.debug("CrudServiceImpl->toUpdateColumnSql");
		return crudTemplate.toUpdateColumnSql(tableEntity, oldColumnEntity, columnEntity);
    }
       
	@Override
    public String toUpdateColumnIndexSql(TableEntity tableEntity, ColumnEntity oldColumnEntity,  ColumnEntity columnEntity) {
    	log.debug("CrudServiceImpl->toUpdateColumnIndexSql");
		return crudTemplate.toUpdateColumnIndexSql(tableEntity, oldColumnEntity, columnEntity);
    }
    
    @Override
    public List<String> toDeleteColumnSql(TableEntity tableEntity, ColumnEntity columnEntity) {
    	log.debug("CrudServiceImpl->toDeleteColumnSql");
		return crudTemplate.toDeleteColumnSql(tableEntity, columnEntity);
    }
    
    @Override
	public List<String> toAddColumnSql(TableEntity tableEntity, ColumnEntity columnEntity) { 
		log.debug("CrudServiceImpl->toAddColumnSql");
		return crudTemplate.toAddColumnSql(tableEntity, columnEntity);
	}
	
	@Override
	public String toUpdateIndexSql(String tableName, IndexTypeEnum oldIndexType, String oldIndexName, IndexEntity indexEntity) {
		log.debug("CrudServiceImpl->toUpdateIndexSql");
		return crudTemplate.toUpdateIndexSql(tableName, oldIndexType, oldIndexName, indexEntity);
	}
	
	@Override
	public String toDeleteIndexSql(String tableName, IndexTypeEnum oldIndexType, String oldIndexName) {
		log.debug("CrudServiceImpl->toDeleteIndexSql");
		return crudTemplate.toDeleteIndexSql(tableName, oldIndexType, oldIndexName);
	}
	
	@Override
	public String toRenameTableSql(String oldTableName, String newTableName) { 
		log.debug("CrudServiceImpl->toRenameTableSql");
		return crudTemplate.toRenameTableSql(oldTableName, newTableName);
	}
	
	@Override
    public String toSetTableEngineSql(String tableName, EngineEnum engine) {
    	log.debug("CrudServiceImpl->toSetTableEngineSql");
		return crudTemplate.toSetTableEngineSql(tableName, engine);
    }
	
	@Override
	public String toAddIndexSql(String tableName, IndexEntity indexEntity) { 
    	log.debug("CrudServiceImpl->toAddIndexSql");
		return crudTemplate.toAddIndexSql(tableName, indexEntity);
    }
	
	@Override
	public List<String> toCreateTableSql(TableEntity tableEntity) { 
		log.debug("CrudServiceImpl->toCreateTableSql");
		return crudTemplate.toCreateTableSql(tableEntity);
	}
	
	@Override
	public String getSqlQuotation() {
		log.debug("CrudServiceImpl->getSqlQuotation");
		return crudTemplate.getSqlQuotation();
	}
	
	@Override
	public String getDateBaseName() {
		log.debug("CrudServiceImpl->getDateBaseName");
		return crudTemplate.getDateBaseName();
	}
	
	@Override
	public Long create(String tableName, Object obj) {
		log.debug("CrudServiceImpl->create");
		return crudTemplate.create(tableName, obj);
	}
	
	@Override
	public Map<String, Object> create(String tableName, Object obj, String[] keyColumnNames, boolean autoIncrement) {
		log.debug("CrudServiceImpl->create");
		return crudTemplate.create(tableName, obj, keyColumnNames, autoIncrement);
	}
	
	@Override
	public Long create(String tableName, Map<String, Object> map) {
		log.debug("CrudServiceImpl->create");
		return crudTemplate.create(tableName, map);
	}
	
	@Override
	public Map<String, Object> create(String tableName, Map<String, Object> map, String[] keyColumnNames, boolean autoIncrement) {
		log.debug("CrudServiceImpl->create");
		return crudTemplate.create(tableName, map, keyColumnNames, autoIncrement);
	}
	
	@Override
	public int[] batchCreateMap(String tableName, List<Map<String, Object>> mapList) {
		log.debug("CrudServiceImpl->batchCreateMap");
		return crudTemplate.batchCreateMap(tableName, mapList);
	}

	@Override
	public int[] batchCreateObj(String tableName, List<Object> objList) { 
		log.debug("CrudServiceImpl->batchCreateObj");
		return crudTemplate.batchCreateObj(tableName, objList);
	}
	
	@Override
	public int[] batchPutMap(String tableName, List<Map<String, Object>> mapList) {
		log.debug("CrudServiceImpl->batchPutMap");
		return crudTemplate.batchPutMap(tableName, mapList);
	}

	@Override
	public int[] batchPutObj(String tableName, List<Object> objList) { 
		log.debug("CrudServiceImpl->batchPutObj");
		return crudTemplate.batchPutObj(tableName, objList);
	}
	
	@Override
	public void put(String tableName, Long id, Map<String, Object> dataMap) {
		log.debug("CrudServiceImpl->put");
		crudTemplate.put(tableName, id, dataMap);
	}
	
	@Override
	public void put(String tableName, Map<String, Object> keyMap, Map<String, Object> dataMap) {
		log.debug("CrudServiceImpl->put");
		crudTemplate.put(tableName, keyMap, dataMap);
	}
	
	@Override
	public void put(String tableName, Map<String, Object> keyMap, Object obj) {
		log.debug("CrudServiceImpl->put");
		crudTemplate.put(tableName, keyMap, obj);
	}
	
	@Override
	public void put(String tableName, Long id, Object obj) {
		log.debug("CrudServiceImpl->put");
		crudTemplate.put(tableName, id, obj);
	}
	
	@Override
	public void patch(String tableName, Long id, Map<String, Object> dataMap) {
		log.debug("CrudServiceImpl->patch");
		crudTemplate.patch(tableName, id, dataMap);
	}
	
	@Override
	public void patch(String tableName, Map<String, Object> keyMap, Map<String, Object> dataMap) {
		log.debug("CrudServiceImpl->patch");
		crudTemplate.patch(tableName, keyMap, dataMap);
	}
	
	@Override
	public void patch(String tableName, Map<String, Object> keyMap, Object obj) {
		log.debug("CrudServiceImpl->patch");
		crudTemplate.patch(tableName, keyMap, obj);
	}
	
	@Override
	public void patch(String tableName, Long id, Object obj) {
		log.debug("CrudServiceImpl->patch");
		crudTemplate.patch(tableName, id, obj);
	}
	
	@Override
	public int delete(String tableName) {
		log.debug("CrudServiceImpl->delete");
		return crudTemplate.delete(tableName);
	}
	
	@Override
	public int delete(String tableName, Map<String, Object> keyMap) {
		log.debug("CrudServiceImpl->delete");
		return crudTemplate.delete(tableName, keyMap);
	}
	
	@Override
	public int delete(String tableName, Long id) {
		log.debug("CrudServiceImpl->delete");
		return crudTemplate.delete(tableName, id);
	}

	@Override
	public int delete(String tableName, Condition condition) {
		log.debug("CrudServiceImpl->delete");
		return crudTemplate.delete(tableName, condition);
	}
	
	@Override
	public Map<String, Object> get(String tableName, Map<String, Object> keyMap) {
		log.debug("CrudServiceImpl->get");
		return crudTemplate.get(tableName, keyMap);
	}
	
	@Override
	public Map<String, Object> get(String tableName, Long id) {
		log.debug("CrudServiceImpl->get");
		return crudTemplate.get(tableName, id);
	}
	
	@Override
	public Map<String, Object> getForUpdate(String tableName, Long id) {
		log.debug("CrudServiceImpl->getForUpdate");
		return crudTemplate.getForUpdate(tableName, id);
	}
	
	@Override
	public <T> T get(String tableName, Map<String, Object> keyMap, Class<T> classType) {
		log.debug("CrudServiceImpl->get");
		return crudTemplate.get(tableName, keyMap, classType);
	}
	
	@Override
	public <T> T get(String tableName, Long id, Class<T> classType) {
		log.debug("CrudServiceImpl->get");
		return crudTemplate.get(tableName, id, classType);
	}
	
	@Override
	public Long count(String tableName, Map<String, DataTypeEnum> dataTypeMap, Condition condition) {
		log.debug("CrudServiceImpl->count");
		return crudTemplate.count(tableName, dataTypeMap, condition);
	}
	
	@Override
	public Long count(String tableName, Condition condition) {
		log.debug("CrudServiceImpl->count");
		return crudTemplate.count(tableName, condition);
	}

	@Override
	public Long count(String tableName) { 
		log.debug("CrudServiceImpl->count");
		return crudTemplate.count(tableName);
	}
	
	@Override
	public boolean isExistTable(String tableName) { 
		log.debug("CrudServiceImpl->isExistTable");
		return crudTemplate.isExistTable(tableName);
	}
	
	@Override
	public void dropTable(TableEntity tableEntity) { 
		log.debug("CrudTemplate->dropTable");
		crudTemplate.dropTable(tableEntity);
	}
	
	@Override
	public List<Map<String, Object>> list(String tableName, Map<String, DataTypeEnum> dataTypeMap, List<String> selectNameList, Condition condition, String orderby, Integer offset, Integer limit) {
		log.debug("CrudServiceImpl->list");
		return crudTemplate.list(tableName, dataTypeMap, selectNameList, condition, orderby, offset, limit);
	}
	
	@Override
	public List<Map<String, Object>> list(String tableName, Map<String, DataTypeEnum> dataTypeMap, Condition condition, String orderby, Integer offset, Integer limit) {
		log.debug("CrudServiceImpl->list");
		return crudTemplate.list(tableName, dataTypeMap, condition, orderby, offset, limit);
	}

	@Override
	public List<Map<String, Object>> list(String tableName, String orderby, Integer offset, Integer limit) {
		log.debug("CrudServiceImpl->list");
		return crudTemplate.list(tableName, orderby, offset, limit);
	}

	@Override
	public List<Map<String, Object>> list(String tableName) {
		log.debug("CrudServiceImpl->list");
		return crudTemplate.list(tableName);
	}

	@Override
	public <T> List<T> list(String tableName, Condition condition, String orderby, Integer offset, Integer limit, Class<T> classType) {
		log.debug("CrudServiceImpl->list");
		return crudTemplate.list(tableName, condition, orderby, offset, limit, classType);
	}
	
	@Override
	public <T> List<T> list(String tableName, String orderby, Integer offset, Integer limit, Class<T> classType) { 
		log.debug("CrudServiceImpl->list");
		return crudTemplate.list(tableName, orderby, offset, limit, classType);
	}
	
	@Override
	public <T> List<T> list(String tableName, Class<T> classType) { 
		log.debug("CrudServiceImpl->list");
		return crudTemplate.list(tableName, classType);
	}
	
	@Override
	public List<Map<String, Object>> list(String sql, Map<String, Object> paramMap) { 
		log.debug("CrudServiceImpl->list");
		return crudTemplate.list(sql, paramMap);
	}
	
	@Override
	public Long count(String sql, Map<String, Object> paramMap) {
		log.debug("CrudServiceImpl->count");
		return crudTemplate.count(sql, paramMap);
	}
	
	@Override
	public JdbcTemplate getJdbcTemplate() {
		log.debug("CrudServiceImpl->getJdbcTemplate");
		return crudTemplate.getJdbcTemplate();
	}
	
	@Override
	public void execute(String sql) { 
		log.debug("CrudServiceImpl->execute");
		crudTemplate.execute(sql);
	}
	
	@Override
	public List<Map<String, Object>> getMetaDatas() {
		log.debug("CrudServiceImpl->getMetaDatas");
		return crudTemplate.getMetaDatas();
	}
	
	@Override
	public Map<String, Object> getMetaData(String tableName) {
		log.debug("CrudServiceImpl->getMetaData");
		return crudTemplate.getMetaData(tableName);
	}

	@Override
	public TableDTO reverseMetaData(String tableName) {
		log.debug("CrudServiceImpl->reverseMetaData");
		return crudTemplate.reverseMetaData(tableName);
	}
	
	@Override
	public String processTemplateToString(String templateName, String key, Object value) {
		return crudTemplate.processTemplateToString(templateName, key, value);
	}
	
	@Override
	public String processTemplateToString(String templateName, Object dataModel) {
		return crudTemplate.processTemplateToString(templateName, dataModel);
	}
	
	@Override
	public String processTemplateToString(String templateBase, String templateName, String key, Object value) {
		return crudTemplate.processTemplateToString(templateBase,  templateName, key, value);
	}
	
	@Override
	public String processTemplateToString(String templateBase, String templateName, Map<String, Object> map) {
		return crudTemplate.processTemplateToString(templateBase,templateName, map);
	}
}
