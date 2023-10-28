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
import org.springframework.util.StringUtils;

import cn.crudapi.crudapi.config.datasource.DynamicDataSourceProvider;
import cn.crudapi.crudapi.constant.Naming;
import cn.crudapi.crudapi.model.Column;
import cn.crudapi.crudapi.model.Table;
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
	
	public Map<String, Object> getRawMetadata(String tableName) {
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
	
	@SuppressWarnings("unchecked")
	public Table getMetadata(String tableName) {
		Map<String, Object> map = this.getRawMetadata(tableName);
		
		Table table = new Table();
		String tableComment = map.get("tableComment") != null ? map.get("tableComment").toString() : null;
	    table.setName(tableName);
	    table.setCaption(StringUtils.hasLength(tableComment) ? tableComment : tableName);
	    table.setDescription(tableComment);
	    
	    List<Column> columnList = new ArrayList<Column>();
		List<Map<String, Object>> columns = (ArrayList<Map<String, Object>>)map.get("columns");
	    for (Map<String, Object> t : columns) {
	    	Column column = new Column();
	    	String columnName = t.get("columnName").toString();
	    	String columnComment = t.get("columnComment") != null ? t.get("columnComment").toString() : null;
	    	
	    	column.setName(columnName);
	    	column.setCaption(StringUtils.hasLength(columnComment) ? columnComment : columnName);
	    	column.setDescription(columnComment);
	    	
	    	String dataType = t.get("dataType").toString();
	    	column.setDataType(dataType);
	    	
	    	
	    	Boolean unsigned = dataType.toUpperCase().indexOf("UNSIGNED") >= 0;
	    	column.setUnsigned(unsigned);
	    	column.setLength(t.get("characterMaximumLength") != null ? Long.parseLong(t.get("characterMaximumLength").toString()) : null);
	    	column.setPrecision(t.get("numericPrecision") != null ? Integer.parseInt(t.get("numericPrecision").toString()) : null);
	    	column.setScale(t.get("numericScale") != null ? Integer.parseInt(t.get("numericScale").toString()) : null);
	    	column.setDefaultValue(t.get("columnDefault") != null ? t.get("columnDefault").toString() : null);
	    	column.setNullable(Boolean.parseBoolean(t.get("nullable").toString()));
	    	

	    	
	    	columnList.add(column);
	    }
	    
	    
	    table.setColumnList(columnList);
	   
	    
		return table;
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
