package cn.crudapi.crudapi.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import cn.crudapi.crudapi.config.datasource.DynamicDataSourceProvider;
import cn.crudapi.crudapi.template.TemplateParse;

public abstract class CrudAbstractRepository {
	private static final Logger log = LoggerFactory.getLogger(CrudAbstractRepository.class);
	
	public static final String COLUMN_ID = "id";
	 
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private TemplateParse templateParse;
	
	@Autowired
	private DynamicDataSourceProvider dynamicDataSourceProvider;
	
	public String getDataBaseType() {
		return dynamicDataSourceProvider.getDatabaseType();
	}
	
	public String getSchema() {
		return dynamicDataSourceProvider.getDatabaseName();
	}
	
	public String getSqlQuotation() {
		return "`";
	}
	
	public String getLimitOffsetSql() {
		return "LIMIT :offset, :limit";
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return namedParameterJdbcTemplate.getJdbcTemplate();
	}
	
	public void execute(String sql) {
		namedParameterJdbcTemplate.getJdbcTemplate().execute(sql);
	}
	
	public List<Map<String, Object>> queryForList(String sql, Map<String, ?> paramMap) {
		log.info("CrudAbstractRepository->queryForList sql: {}", sql);
		return namedParameterJdbcTemplate.queryForList(sql, paramMap);
	}
	
	public List<Map<String, Object>> getMetadatas() {
		String tableSchema = getSchema();
		log.info("tableSchema = " + tableSchema);
		List<Map<String, Object>> mapList =  namedParameterJdbcTemplate.getJdbcTemplate().queryForList("SHOW TABLE STATUS");
		List<Map<String, Object>> newMapList =  new ArrayList<Map<String, Object>>();
		for (Map<String, Object> t : mapList) {
			Map<String, Object> newMap = new HashMap<String, Object>();
			newMap.put("tableSchema", tableSchema);
			newMap.put("tableName", t.get("Name"));
			newMap.put("comment", t.get("Comment"));
			newMapList.add(newMap);
		}
		return newMapList;
	}
	
	public Map<String, Object> getMetadata(String tableName) {
		String tableSchema = getSchema();
		log.info("tableSchema = " + tableSchema);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String sql = "SELECT `TABLE_SCHEMA`, `TABLE_NAME`, `TABLE_TYPE`, `CREATE_TIME`, `UPDATE_TIME`, `TABLE_COMMENT` FROM `INFORMATION_SCHEMA`.`TABLES`" 
				+ " WHERE TABLE_SCHEMA = '" + tableSchema + "' AND TABLE_NAME = '" + tableName + "'" ;
		log.info("sql = " + sql);
		map = namedParameterJdbcTemplate.getJdbcTemplate().queryForMap(sql);
	
		sql = "SELECT `TABLE_SCHEMA`, `TABLE_NAME`, `COLUMN_NAME`, `ORDINAL_POSITION`, `COLUMN_DEFAULT`, `IS_NULLABLE`, `DATA_TYPE`, `CHARACTER_MAXIMUM_LENGTH`, `CHARACTER_OCTET_LENGTH`, `NUMERIC_PRECISION`, `NUMERIC_SCALE`, `DATETIME_PRECISION`, `COLUMN_TYPE`, `COLUMN_KEY`, `EXTRA`, `COLUMN_COMMENT` FROM `INFORMATION_SCHEMA`.`COLUMNS`" 
				+ " WHERE TABLE_SCHEMA = '" + tableSchema + "' AND TABLE_NAME = '" + tableName + "'" ;
		log.info("sql = " + sql);
		
		List<Map<String, Object>> columnList = namedParameterJdbcTemplate.getJdbcTemplate().queryForList(sql);
		map.put("columns", columnList);
		
		sql = "SELECT `TABLE_SCHEMA`, `TABLE_NAME`, `NON_UNIQUE`, `INDEX_SCHEMA`, `INDEX_NAME`, `SEQ_IN_INDEX`, `COLUMN_NAME`, `NULLABLE`, `INDEX_TYPE`, `COMMENT`, `INDEX_COMMENT` FROM `INFORMATION_SCHEMA`.`STATISTICS`"
				+ " WHERE TABLE_SCHEMA = '" + tableSchema + "' AND TABLE_NAME = '" + tableName + "'" ;
		log.info("sql = " + sql);
		
		List<Map<String, Object>> indexList = namedParameterJdbcTemplate.getJdbcTemplate().queryForList(sql);
		map.put("indexs", indexList);
		
		sql = "SELECT `TABLE_SCHEMA`, `CONSTRAINT_NAME`, `TABLE_NAME`, `COLUMN_NAME`, `ORDINAL_POSITION`, `POSITION_IN_UNIQUE_CONSTRAINT`, `REFERENCED_TABLE_SCHEMA`, `REFERENCED_TABLE_NAME`, `REFERENCED_COLUMN_NAME` FROM `INFORMATION_SCHEMA`.`KEY_COLUMN_USAGE`" 
				+ " WHERE TABLE_SCHEMA = '" + tableSchema + "' AND TABLE_NAME = '" + tableName + "'" ;
		log.info("sql = " + sql);
		
		List<Map<String, Object>> constraintList = namedParameterJdbcTemplate.getJdbcTemplate().queryForList(sql);
		map.put("constraints", constraintList);
		
		return map;
	}
	
	public String processTemplateToString(String templateName, String key, Object value) {
		return templateParse.processTemplateToString(getDataBaseType(), templateName, key, value);
	}
	
	public String processTemplateToString(String templateName, Object dataModel) {
		return templateParse.processTemplateToString(getDataBaseType(), templateName, dataModel);
	}
	
	public String processTemplateToString(String templateBase, String templateName, String key, Object value) {
		return templateParse.processTemplateToString(templateBase, getDataBaseType(), templateName, key, value);
	}
	
	public String processTemplateToString(String templateBase, String templateName, Map<String, Object> map) {
		return templateParse.processTemplateToString(templateBase, getDataBaseType(), templateName, map);
	}
}
