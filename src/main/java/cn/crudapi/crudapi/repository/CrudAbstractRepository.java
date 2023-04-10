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
	
	public String getDateBaseName() {
		return "sql";
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
		log.info("CrudAbstractRepository->queryForList", sql);
		List<Map<String, Object>> mapList = namedParameterJdbcTemplate.queryForList(sql, paramMap);
		
		List<Map<String, Object>> newMapList = new ArrayList<>();
		for (Map<String, Object> t : mapList) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			for(Map.Entry<String, Object> entry : t.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (key.toLowerCase().startsWith("is_")) {
					key = key.substring(3);
					
					if (value != null ) {
						String valueStr = value.toString();
						if (valueStr.equals("1") || valueStr.toLowerCase().equals("true")) {
							value = true;
		    			} else {
		    				value = false;
		    			}
					}
				}
				
				map.put(CrudapiUtils.underlineToCamel(key), value);
			}
			newMapList.add(map);
		}
		
		return newMapList;
	}
	
	public List<Map<String, Object>> getMetaDatas() {
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
	
	public Map<String, Object> getMetaData(String tableName) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String sql = "SHOW TABLE STATUS LIKE '" + tableName + "'";
		map = namedParameterJdbcTemplate.getJdbcTemplate().queryForMap(sql);
	
		sql = "SHOW FULL COLUMNS FROM " + getSqlQuotation() + tableName + getSqlQuotation();
		List<Map<String, Object>> descList = namedParameterJdbcTemplate.getJdbcTemplate().queryForList(sql);
		map.put("columns", descList);
		
		sql = "SHOW INDEX FROM " + getSqlQuotation() + tableName +  getSqlQuotation();
		List<Map<String, Object>> indexList = namedParameterJdbcTemplate.getJdbcTemplate().queryForList(sql);
		map.put("indexs", indexList);
		
		return map;
	}
	
	public String processTemplateToString(String templateName, String key, Object value) {
		return templateParse.processTemplateToString(getDateBaseName(), templateName, key, value);
	}
	
	public String processTemplateToString(String templateName, Object dataModel) {
		return templateParse.processTemplateToString(getDateBaseName(), templateName, dataModel);
	}
	
	public String processTemplateToString(String templateBase, String templateName, String key, Object value) {
		return templateParse.processTemplateToString(templateBase, getDateBaseName(), templateName, key, value);
	}
	
	public String processTemplateToString(String templateBase, String templateName, Map<String, Object> map) {
		return templateParse.processTemplateToString(templateBase, getDateBaseName(), templateName, map);
	}
}
