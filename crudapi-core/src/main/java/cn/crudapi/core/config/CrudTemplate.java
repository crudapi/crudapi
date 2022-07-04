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

public class CrudTemplate {
	private static final Logger log = LoggerFactory.getLogger(CrudTemplate.class);
	
	private volatile DynamicCrudSqlExecute dynamicCrudSqlExecute;

	public CrudTemplate() {
		super();
		log.debug("CrudTemplate->Constructor");
	}
	
	public CrudTemplate(DynamicCrudSqlExecute dynamicCrudSqlExecute) {
		super();
		this.dynamicCrudSqlExecute = dynamicCrudSqlExecute;
		log.debug("CrudTemplate->Constructor cynamicCrudSqlExecute");
	}


	public String toUpdateColumnSql(TableEntity tableEntity, ColumnEntity oldColumnEntity, ColumnEntity columnEntity) {
    	log.debug("CrudTemplate->toUpdateColumnSql");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().toUpdateColumnSql(tableEntity, oldColumnEntity, columnEntity);
    }
        
    public String toUpdateColumnIndexSql(TableEntity tableEntity, ColumnEntity oldColumnEntity, ColumnEntity columnEntity) {
    	log.debug("CrudTemplate->toUpdateColumnIndexSql");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().toUpdateColumnIndexSql(tableEntity, oldColumnEntity, columnEntity);
    }
    
    public List<String> toDeleteColumnSql(TableEntity tableEntity, ColumnEntity columnEntity) {
    	log.debug("CrudTemplate->toDeleteColumnSql");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().toDeleteColumnSql(tableEntity, columnEntity);
    }
    
	public List<String> toAddColumnSql(TableEntity tableEntity, ColumnEntity columnEntity) { 
		log.debug("CrudTemplate->toAddColumnSql");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().toAddColumnSql(tableEntity, columnEntity);
	}
	
	public String toUpdateIndexSql(String tableName, IndexTypeEnum oldIndexType, String oldIndexName, IndexEntity indexEntity) {
		log.debug("CrudTemplate->toUpdateIndexSql");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().toUpdateIndexSql(tableName, oldIndexType, oldIndexName, indexEntity);
	}
	
	public String toDeleteIndexSql(String tableName, IndexTypeEnum oldIndexType, String oldIndexName) {
		log.debug("CrudTemplate->toDeleteIndexSql");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().toDeleteIndexSql(tableName, oldIndexType, oldIndexName);
	}
	
	public String toRenameTableSql(String oldTableName, String newTableName) { 
		log.debug("CrudTemplate->toRenameTableSql");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().toRenameTableSql(oldTableName, newTableName);
	}
	
    public String toSetTableEngineSql(String tableName, EngineEnum engine) {
    	log.debug("CrudTemplate->toSetTableEngineSql");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().toSetTableEngineSql(tableName, engine);
    }
    
    public String toAddIndexSql(String tableName, IndexEntity indexEntity) { 
    	log.debug("CrudTemplate->toAddIndexSql");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().toAddIndexSql(tableName, indexEntity);
    }
	
	public List<String> toCreateTableSql(TableEntity tableEntity) { 
		log.debug("CrudTemplate->toCreateTableSql");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().toCreateTableSql(tableEntity);
	}
	
	public String getSqlQuotation() { 
		log.debug("CrudTemplate->getSqlQuotation");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().getSqlQuotation();
	}
	
	public String getDateBaseName() { 
		log.debug("CrudTemplate->getDateBaseName");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().getDateBaseName();
	}
	
	public Long create(String tableName, Object obj) {
		log.debug("CrudTemplate->create");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().create(tableName, obj);
	}
	
	public Map<String, Object> create(String tableName, Object obj, String[] keyColumnNames, boolean autoIncrement) {
		log.debug("CrudTemplate->create");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().create(tableName, obj, keyColumnNames, autoIncrement);
	}
	
	public Long create(String tableName, Map<String, Object> map) {
		log.debug("CrudTemplate->create");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().create(tableName, map);
	}
	
	public Map<String, Object> create(String tableName, Map<String, Object> map, String[] keyColumnNames, boolean autoIncrement) {
		log.debug("CrudTemplate->create");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().create(tableName, map, keyColumnNames, autoIncrement);
	}
	
	public int[] batchCreateMap(String tableName, List<Map<String, Object>> mapList) {
		log.debug("CrudTemplate->batchCreateMap");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().batchCreateMap(tableName, mapList);
	}

	public int[] batchCreateObj(String tableName, List<Object> objList) { 
		log.debug("CrudTemplate->batchCreateObj");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().batchCreateObj(tableName, objList);
	}
	
	public int[] batchPutMap(String tableName, List<Map<String, Object>> mapList) {
		log.debug("CrudTemplate->batchPutMap");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().batchPutMap(tableName, mapList);
	}

	public int[] batchPutObj(String tableName, List<Object> objList) { 
		log.debug("CrudTemplate->batchPutObj");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().batchPutObj(tableName, objList);
	}
	
	public void put(String tableName, Long id, Map<String, Object> dataMap) {
		log.debug("CrudTemplate->put");
		dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().put(tableName, id, dataMap);
	}
	
	public void put(String tableName, Map<String, Object> keyMap, Map<String, Object> dataMap) {
		log.debug("CrudTemplate->put");
		dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().put(tableName, keyMap, dataMap);
	}
	
	public void put(String tableName, Map<String, Object> keyMap, Object obj) {
		log.debug("CrudTemplate->put");
		dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().put(tableName, keyMap, obj);
	}
	
	public void put(String tableName, Long id, Object obj) {
		log.debug("CrudTemplate->put");
		dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().put(tableName, id, obj);
	}
	
	public void patch(String tableName, Long id, Map<String, Object> dataMap) {
		log.debug("CrudTemplate->patch");
		dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().patch(tableName, id, dataMap);
	}
	
	public void patch(String tableName, Map<String, Object> keyMap, Map<String, Object> dataMap) {
		log.debug("CrudTemplate->patch");
		dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().patch(tableName, keyMap, dataMap);
	}
	
	public void patch(String tableName, Map<String, Object> keyMap, Object obj) {
		log.debug("CrudTemplate->patch");
		dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().patch(tableName, keyMap, obj);
	}
	
	public void patch(String tableName, Long id, Object obj) {
		log.debug("CrudTemplate->patch");
		dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().patch(tableName, id, obj);
	}
	
	public int delete(String tableName) {
		log.debug("CrudTemplate->delete");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().delete(tableName);
	}
	
	public int delete(String tableName, Map<String, Object> keyMap) {
		log.debug("CrudTemplate->delete");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().delete(tableName, keyMap);
	}
	
	public int delete(String tableName, Long id) {
		log.debug("CrudTemplate->delete");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().delete(tableName, id);
	}
	
	public int delete(String tableName, Condition condition) {
		log.debug("CrudTemplate->delete");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().delete(tableName, condition);
	}
	
	public Map<String, Object> get(String tableName, Map<String, Object> keyMap) {
		log.debug("CrudTemplate->get");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().get(tableName, keyMap);
	}
	
	public Map<String, Object> get(String tableName, Long id) {
		log.debug("CrudTemplate->get");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().get(tableName, id);
	}
	
	public Map<String, Object> getForUpdate(String tableName, Long id) {
		log.debug("CrudTemplate->getForUpdate");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().getForUpdate(tableName, id);
	}
	
	
	public <T> T get(String tableName, Map<String, Object> keyMap, Class<T> classType) {
		log.debug("CrudTemplate->get");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().get(tableName, keyMap, classType);
	}
	
	public <T> T get(String tableName, Long id, Class<T> classType) {
		log.debug("CrudTemplate->get");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().get(tableName, id, classType);
	}
	
	public Long count(String tableName, Map<String, DataTypeEnum> dataTypeMap, Condition condition) { 
		log.debug("CrudTemplate->count");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().count(tableName, dataTypeMap, condition);
	}
	
	public Long count(String tableName, Condition condition) { 
		log.debug("CrudTemplate->count");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().count(tableName, condition);
	}
	
	public Long count(String tableName) { 
		log.debug("CrudTemplate->count");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().count(tableName);
	}
	
	public boolean isExistTable(String tableName) { 
		log.debug("CrudTemplate->isExistTable");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().isExistTable(tableName);
	}
	
	public void dropTable(TableEntity tableEntity) { 
		log.debug("CrudTemplate->dropTable");
		dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().dropTable(tableEntity);
	}
	
	public List<Map<String, Object>> list(String tableName, Map<String, DataTypeEnum> dataTypeMap, List<String> selectNameList, Condition condition, String orderby, Integer offset, Integer limit) {
		log.debug("CrudTemplate->list");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().list(tableName, dataTypeMap, selectNameList, condition, orderby, offset, limit);
	}
	
	public List<Map<String, Object>> list(String tableName, Map<String, DataTypeEnum> dataTypeMap, Condition condition, String orderby, Integer offset, Integer limit) {
		log.debug("CrudTemplate->list");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().list(tableName, dataTypeMap, condition, orderby, offset, limit);
	}
	
	public List<Map<String, Object>> list(String tableName, String orderby, Integer offset, Integer limit) {
		log.debug("CrudTemplate->list");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().list(tableName, orderby, offset, limit);
	}

	public List<Map<String, Object>> list(String tableName) {
		log.debug("CrudTemplate->list");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().list(tableName);
	}
	
	public <T> List<T> list(String tableName, Condition condition, String orderby, Integer offset, Integer limit, Class<T> classType) { 
		log.debug("CrudTemplate->list");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().list(tableName, condition, orderby, offset, limit, classType);
	}
	
	public <T> List<T> list(String tableName, String orderby, Integer offset, Integer limit, Class<T> classType) { 
		log.debug("CrudTemplate->list");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().list(tableName, orderby, offset, limit, classType);
	}
	
	public <T> List<T> list(String tableName, Class<T> classType) { 
		log.debug("CrudTemplate->list");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().list(tableName, classType);
	}
	
	public List<Map<String, Object>> list(String sql, Map<String, Object> paramMap) { 
		log.debug("CrudTemplate->list");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().list(sql, paramMap);
	}
	
	public Long count(String sql, Map<String, Object> paramMap) {
		log.debug("CrudTemplate->count");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().count(sql, paramMap);
	}
	
	
	public JdbcTemplate getJdbcTemplate() {
		log.debug("CrudTemplate->getJdbcTemplate");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().getJdbcTemplate();
	}
	
	public void execute(String sql) { 
		log.debug("CrudTemplate->execute");
		dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().execute(sql);
	}
	
	public List<Map<String, Object>> getMetaDatas() {
		log.debug("CrudTemplate->getMetaDatas");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().getMetaDatas();
	}
	
	public Map<String, Object> getMetaData(String tableName) {
		log.debug("CrudTemplate->getMetaData");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().getMetaData(tableName);
	}
	
	public TableDTO reverseMetaData(String tableName) {
		log.debug("CrudServiceImpl->reverseMetaData");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().reverseMetaData(tableName);
	}
	public String processTemplateToString(String templateName, String key, Object value) {
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().processTemplateToString(templateName, key, value);
	}
	
	public String processTemplateToString(String templateName, Object dataModel) {
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().processTemplateToString(templateName, dataModel);
	}
	
	public String processTemplateToString(String templateBase, String templateName, String key, Object value) {
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().processTemplateToString(templateBase,  templateName, key, value);
	}
	
	public String processTemplateToString(String templateBase, String templateName, Map<String, Object> map) {
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().processTemplateToString(templateBase,templateName, map);
	}
}
