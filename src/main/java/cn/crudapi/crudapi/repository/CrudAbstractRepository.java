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
import cn.crudapi.crudapi.constant.Naming;
import cn.crudapi.crudapi.template.TemplateParse;
import cn.crudapi.crudapi.util.CrudapiUtils;

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
		return this.namedParameterJdbcTemplate.queryForList(sql, paramMap);
	}
	
	public List<Map<String, Object>> queryForListAndConvert(String sql, Map<String, ?> paramMap) {
		log.info("CrudAbstractRepository->queryForList sql: {}", sql);
		List<Map<String, Object>> mapList = this.queryForList(sql, paramMap);
		
		return CrudapiUtils.convert(mapList, Naming.UPPER_UNDERSCORE, Naming.LOWER_CAMEL);
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
		log.info("tableSchema = {}", tableSchema);
		log.info("tableName = {}", tableName);
		
		Map<String, Object> mapParams = new HashMap<String, Object>();
		mapParams.put("tableSchema", ":tableSchema");
		mapParams.put("tableName",  ":tableName");
		
		Map<String, Object> dbParams = new HashMap<String, Object>();
		mapParams.put("tableSchema", tableSchema);
		mapParams.put("tableName",  tableName);
		
		String sql = processTemplateToString("select-table.sql.ftl", mapParams);
		log.info("sql = " + sql);
		Map<String, Object> map = this.queryForListAndConvert(sql, dbParams).get(0);
	
		sql = processTemplateToString("select-column.sql.ftl", mapParams);
		log.info("sql = " + sql);
		List<Map<String, Object>> columnList = this.queryForListAndConvert(sql, dbParams);
		map.put("columns", columnList);
		
		sql = processTemplateToString("select-index.sql.ftl", mapParams);
		log.info("sql = " + sql);
		List<Map<String, Object>> indexList = this.queryForListAndConvert(sql, dbParams);
		map.put("indexs", indexList);
		
		sql = processTemplateToString("select-constraint.sql.ftl", mapParams);
		log.info("sql = " + sql);
		List<Map<String, Object>> constraintList = this.queryForListAndConvert(sql, dbParams);
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
