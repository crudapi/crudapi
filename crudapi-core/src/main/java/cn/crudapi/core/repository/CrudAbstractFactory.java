package cn.crudapi.core.repository;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.crudapi.core.dto.TableDTO;
import cn.crudapi.core.entity.ColumnEntity;
import cn.crudapi.core.entity.IndexEntity;
import cn.crudapi.core.entity.TableEntity;
import cn.crudapi.core.enumeration.DataTypeEnum;
import cn.crudapi.core.enumeration.EngineEnum;
import cn.crudapi.core.enumeration.IndexTypeEnum;
import cn.crudapi.core.query.Condition;

public abstract class CrudAbstractFactory {
	private static final Logger log = LoggerFactory.getLogger(CrudAbstractFactory.class);
	
	public abstract CrudAbstractRepository getCrudRepository();
	
    public String toUpdateColumnSql(TableEntity tableEntity, ColumnEntity oldColumnEntity, ColumnEntity columnEntity) {
    	log.debug("CrudAbstractFactory->toUpdateColumnSql");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.toUpdateColumnSql(tableEntity, oldColumnEntity, columnEntity);
    }

    public String toUpdateColumnIndexSql(TableEntity tableEntity, ColumnEntity oldColumnEntity, ColumnEntity columnEntity) {
    	log.debug("CrudAbstractFactory->toUpdateColumnIndexSql");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.toUpdateColumnIndexSql(tableEntity, oldColumnEntity, columnEntity);
    }
    
    public List<String> toDeleteColumnSql(TableEntity tableEntity, ColumnEntity columnEntity) {
    	log.debug("CrudAbstractFactory->toDeleteColumnSql");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.toDeleteColumnSql(tableEntity, columnEntity);
    }
    
	public List<String> toAddColumnSql(TableEntity tableEntity, ColumnEntity columnEntity) { 
		log.debug("CrudAbstractFactory->toAddColumnSql");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.toAddColumnSql(tableEntity, columnEntity);
	}
	
	public String toUpdateIndexSql(String tableName, IndexTypeEnum oldIndexType, String oldIndexName, IndexEntity indexEntity) {
		log.debug("CrudAbstractFactory->toUpdateIndexSql");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.toUpdateIndexSql(tableName, oldIndexType, oldIndexName, indexEntity);
	}
	
	public String toDeleteIndexSql(String tableName, IndexTypeEnum oldIndexType, String oldIndexName) {
		log.debug("CrudAbstractFactory->toDeleteIndexSql");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.toDeleteIndexSql(tableName, oldIndexType, oldIndexName);
	}
	
	public String toRenameTableSql(String oldTableName, String newTableName) { 
		log.debug("CrudAbstractFactory->toRenameTableSql");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.toRenameTableSql(oldTableName, newTableName);
	}
	
    public String toSetTableEngineSql(String tableName, EngineEnum engine) {
    	log.debug("CrudAbstractFactory->toSetTableEngineSql");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.toSetTableEngineSql(tableName, engine);
    }
    
    public String toAddIndexSql(String tableName, IndexEntity indexEntity) { 
    	log.debug("CrudAbstractFactory->toAddIndexSql");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.toAddIndexSql(tableName, indexEntity);
    }
	
	public List<String> toCreateTableSql(TableEntity tableEntity) { 
		log.debug("CrudAbstractFactory->toCreateTableSql");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.toCreateTableSql(tableEntity);
	}
	
	
	public String getSqlQuotation() { 
		log.debug("CrudAbstractFactory->getSqlQuotation");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.getSqlQuotation();
	}
	
	public String getDateBaseName() {
		log.debug("CrudAbstractFactory->getDateBaseName");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.getDateBaseName();
	}
	
	public Long create(String tableName, Object obj) {
		log.debug("CrudAbstractFactory->create");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.create(tableName, obj);
	}
	
	public Map<String, Object> create(String tableName, Object obj, String[] keyColumnNames, boolean autoIncrement) {
		log.debug("CrudAbstractFactory->create");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.create(tableName, obj, keyColumnNames, autoIncrement);
	}
	
	public Long create(String tableName, Map<String, Object> map) {
		log.debug("CrudAbstractFactory->create");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.create(tableName, map);
	}
	
	public Map<String, Object> create(String tableName, Map<String, Object> map, String[] keyColumnNames, boolean autoIncrement) {
		log.debug("CrudAbstractFactory->create");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.create(tableName, map, keyColumnNames, autoIncrement);
	}
	
	public int[] batchCreateMap(String tableName, List<Map<String, Object>> mapList) {
		log.debug("CrudAbstractFactory->batchCreateMap");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.batchCreateMap(tableName, mapList);
	}

	public int[] batchCreateObj(String tableName, List<Object> objList) { 
		log.debug("CrudAbstractFactory->batchCreateObj");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.batchCreateObj(tableName, objList);
	}
	
	public void put(String tableName, Long id, Map<String, Object> dataMap) {
		log.debug("CrudAbstractFactory->put");
		CrudAbstractRepository repository = this.getCrudRepository();
		repository.put(tableName, id, dataMap);
	}
	
	public void put(String tableName, Map<String, Object> keyMap, Map<String, Object> dataMap) {
		log.debug("CrudAbstractFactory->put");
		CrudAbstractRepository repository = this.getCrudRepository();
		repository.put(tableName, keyMap, dataMap);
	}
	
	public void put(String tableName, Map<String, Object> keyMap, Object obj) {
		log.debug("CrudAbstractFactory->put");
		CrudAbstractRepository repository = this.getCrudRepository();
		repository.put(tableName, keyMap, obj);
	}
	
	public void put(String tableName, Long id, Object obj) {
		log.debug("CrudAbstractFactory->put");
		CrudAbstractRepository repository = this.getCrudRepository();
		repository.put(tableName, id, obj);
	}
	
	public int[] batchPutMap(String tableName, List<Map<String, Object>> mapList) {
		log.debug("CrudAbstractFactory->batchPutMap");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.batchPutMap(tableName, mapList);
	}

	public int[] batchPutObj(String tableName, List<Object> objList) { 
		log.debug("CrudAbstractFactory->batchPutObj");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.batchPutObj(tableName, objList);
	}
	
	public void patch(String tableName, Long id, Map<String, Object> dataMap) {
		log.debug("CrudAbstractFactory->patch");
		CrudAbstractRepository repository = this.getCrudRepository();
		repository.patch(tableName, id, dataMap);
	}
	
	public void patch(String tableName, Map<String, Object> keyMap, Map<String, Object> dataMap) {
		log.debug("CrudAbstractFactory->patch");
		CrudAbstractRepository repository = this.getCrudRepository();
		repository.patch(tableName, keyMap, dataMap);
	}
	
	public void patch(String tableName, Map<String, Object> keyMap, Object obj) {
		log.debug("CrudAbstractFactory->patch");
		CrudAbstractRepository repository = this.getCrudRepository();
		repository.patch(tableName, keyMap, obj);
	}
	
	public void patch(String tableName, Long id, Object obj) {
		log.debug("CrudAbstractFactory->patch");
		CrudAbstractRepository repository = this.getCrudRepository();
		repository.patch(tableName, id, obj);
	}
	
	public int delete(String tableName) {
		log.debug("CrudAbstractFactory->delete");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.delete(tableName);
	}
		
	public int delete(String tableName, Map<String, Object> keyMap) {
		log.debug("CrudAbstractFactory->delete");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.delete(tableName, keyMap);
	}
	
	public int delete(String tableName, Long id) {
		log.debug("CrudAbstractFactory->delete");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.delete(tableName, id);
	}
	
	public int delete(String tableName, Condition condition) {
		log.debug("CrudAbstractFactory->delete");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.delete(tableName, condition);
	}
	
	public Map<String, Object> get(String tableName, Map<String, Object> keyMap) {
		log.debug("CrudAbstractFactory->get");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.get(tableName, keyMap);
	}
	
	public Map<String, Object> get(String tableName, Long id) {
		log.debug("CrudAbstractFactory->get");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.get(tableName, id);
	}
	
	public Map<String, Object> getForUpdate(String tableName, Long id) {
		log.debug("CrudAbstractFactory->getForUpdate");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.getForUpdate(tableName, id);
	}
	
	public <T> T get(String tableName, Map<String, Object> keyMap, Class<T> classType) {
		log.debug("CrudAbstractFactory->get");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.get(tableName, keyMap, classType);
	}
	
	public <T> T get(String tableName, Long id, Class<T> classType) {
		log.debug("CrudAbstractFactory->get");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.get(tableName, id, classType);
	}
	
	public Long count(String tableName, Map<String, DataTypeEnum> dataTypeMap, Condition condition) { 
		log.debug("CrudAbstractFactory->count");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.count(tableName, dataTypeMap, condition);
	}
	
	public Long count(String tableName, Condition condition) { 
		log.debug("CrudAbstractFactory->count");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.count(tableName, condition);
	}
	
	public Long count(String tableName) { 
		log.debug("CrudAbstractFactory->count");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.count(tableName);
	}
	
	public boolean isExistTable(String tableName) {
		log.debug("CrudAbstractFactory->isExistTable");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.isExistTable(tableName);
	}
	
	public void dropTable(TableEntity tableEntity) { 
		log.debug("CrudAbstractFactory->dropTable");
		CrudAbstractRepository repository = this.getCrudRepository();
		repository.dropTable(tableEntity);
	}
	
	public List<Map<String, Object>> list(String tableName, Map<String, DataTypeEnum> dataTypeMap, List<String> selectNameList, Condition condition, String orderby, Integer offset, Integer limit) {
		log.debug("CrudAbstractFactory->list");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.list(tableName, dataTypeMap, selectNameList, condition, orderby, offset, limit);
	}
	
	public List<Map<String, Object>> list(String tableName, Map<String, DataTypeEnum> dataTypeMap, Condition condition, String orderby, Integer offset, Integer limit) {
		log.debug("CrudAbstractFactory->list");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.list(tableName, dataTypeMap, condition, orderby, offset, limit);
	}
	
	public List<Map<String, Object>> list(String tableName, String orderby, Integer offset, Integer limit) {
		log.debug("CrudAbstractFactory->list");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.list(tableName, orderby, offset, limit);
	}
	
	public List<Map<String, Object>> list(String tableName) {
		log.debug("CrudAbstractFactory->list");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.list(tableName);
	}

	public <T> List<T> list(String tableName, Condition condition, String orderby, Integer offset, Integer limit, Class<T> classType) { 
		log.debug("CrudAbstractFactory->list");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.list(tableName, condition, orderby, offset, limit, classType);
	}
	
	public <T> List<T> list(String tableName, String orderby, Integer offset, Integer limit, Class<T> classType) { 
		log.debug("CrudAbstractFactory->list");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.list(tableName, orderby, offset, limit, classType);
	}
	
	public <T> List<T> list(String tableName, Class<T> classType) { 
		log.debug("CrudAbstractFactory->list");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.list(tableName, classType);
	}
	
	public List<Map<String, Object>> list(String sql, Map<String, Object> paramMap) { 
		log.debug("CrudAbstractFactory->list");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.list(sql, paramMap);
	}
	
	public JdbcTemplate getJdbcTemplate() {
		log.debug("CrudAbstractFactory->getJdbcTemplate");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.getJdbcTemplate();
	}
	
	public void execute(String sql) { 
		log.debug("CrudAbstractFactory->execute");
		CrudAbstractRepository repository = this.getCrudRepository();
		repository.execute(sql);
	}
	
	public List<Map<String, Object>> getMetaDatas() {
		log.debug("CrudAbstractFactory->getMetaDatas");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.getMetaDatas();
	}
	
	public Map<String, Object> getMetaData(String tableName) {
		log.debug("CrudAbstractFactory->getMetaData");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.getMetaData(tableName);
	}
	
	public TableDTO reverseMetaData(String tableName) {
		log.debug("CrudAbstractFactory->reverseMetaData");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.reverseMetaData(tableName);
	}
	

	public String processTemplateToString(String templateName, String key, Object value) {
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.processTemplateToString(templateName, key, value);
	}
	
	public String processTemplateToString(String templateName, Object dataModel) {
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.processTemplateToString(templateName, dataModel);
	}
	
	public String processTemplateToString(String templateBase, String templateName, String key, Object value) {
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.processTemplateToString(templateBase,  templateName, key, value);
	}
	
	public String processTemplateToString(String templateBase, String templateName, Map<String, Object> map) {
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.processTemplateToString(templateBase,templateName, map);
	}
}
