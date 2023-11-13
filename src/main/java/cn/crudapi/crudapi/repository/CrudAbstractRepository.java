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
import cn.crudapi.crudapi.model.Constraint;
import cn.crudapi.crudapi.model.Index;
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
		table.setMetadata(map);
		
		String tableComment = map.get("tableComment") != null ? map.get("tableComment").toString() : null;
	    table.setName(tableName);
	    table.setCaption(StringUtils.hasLength(tableComment) ? tableComment : tableName);
	    table.setDescription(tableComment);
	    
	    //索引分组
	    List<Map<String, Object>> indexs = (ArrayList<Map<String, Object>>)map.get("indexs");
  		Map<String, List<Map<String, Object>>> indexMap = new HashMap<String, List<Map<String, Object>>>();
  		for (Map<String, Object> t : indexs) {
  			String indexName = t.get("indexName").toString();
  			List<Map<String, Object>> indexColumnNames = indexMap.get(indexName);
  			if (indexColumnNames == null) {
  				indexColumnNames = new ArrayList<Map<String, Object>>();
  				indexColumnNames.add(t);
  				indexMap.put(indexName, indexColumnNames);
  			} else {
  				indexColumnNames.add(t);
  			}
  		}
  		
		//单列索引和联合索引
		Map<String, Map<String, Object>> signleIndexMap = new HashMap<String, Map<String, Object>>();
		Map<String, List<Map<String, Object>>> unionIndexMap = new HashMap<String, List<Map<String, Object>>>();
		for (Map.Entry<String, List<Map<String, Object>>>  e : indexMap.entrySet()) {
			String key = e.getKey();
			List<Map<String, Object>> value = e.getValue();
			if (value.size() == 1) {
				Map<String, Object> t = value.get(0);
				String columnName = t.get("columnName").toString();
				signleIndexMap.put(columnName,  t);
			} else {
				unionIndexMap.put(key, value);
			}
		}
		
		//联合索引
		List<Index> indexList =  new ArrayList<Index>();
		for (Map.Entry<String, List<Map<String, Object>>> e : unionIndexMap.entrySet()) {
			Index index = new Index();
			String indexName = e.getKey();
			index.setName(indexName);
			
			String caption = null;
			String indexType = null;
			Boolean unique = false;
			
			List<Column> indexColumnList = new ArrayList<Column>();
			List<Map<String, Object>> values = e.getValue();
			for (Map<String, Object> t : values) {
				Object indexComment = t.get("indexComment");
				caption = (indexComment != null ? indexComment.toString() : indexName);
				
				indexType = t.get("indexType").toString();
				unique = !t.get("nonUnique").toString().toUpperCase().equals("TRUE");
				
				String columnName = t.get("columnName").toString();
				Column column = new Column();
				column.setName(columnName);
				indexColumnList.add(column);
			}
			
			index.setCaption(caption);
			index.setDescription(caption);
			index.setIndexType(indexType);
			index.setColumnList(indexColumnList);
			
			if (!unique) {
				indexList.add(index);
			} else {
				log.info("[index add]{} is constraint, skip!", indexName);
			}
		}
		
		table.setIndexList(indexList);
	    
		//联合约束
		List<Constraint> constraintList = new ArrayList<Constraint>();
		for (Map.Entry<String, List<Map<String, Object>>> e : unionIndexMap.entrySet()) {
		    Constraint constraint = new Constraint();
		    String constraintName = e.getKey();
		    constraint.setName(constraintName);
		    
		    String caption = null;
		    Boolean primary = false;
		    Boolean unique = false;
		    
		    List<Column> constraintColumnList = new ArrayList<Column>();
		    List<Map<String, Object>> values = e.getValue();
		    for (Map<String, Object> t : values) {
		        Object constraintComment = t.get("indexComment");
		        caption = (constraintComment != null ? constraintComment.toString() : constraintName);
		        
		        primary = t.get("indexName").toString().toUpperCase().equals("PRIMARY");
				unique = !t.get("nonUnique").toString().toUpperCase().equals("TRUE");
				
		        String columnName = t.get("columnName").toString();
		        Column column = new Column();
		        column.setName(columnName);
		        constraintColumnList.add(column);
		    }
		    
		    constraint.setCaption(caption);
		    constraint.setDescription(caption);
		    constraint.setPrimary(primary);
		    constraint.setUnique(unique);
		    constraint.setColumnList(constraintColumnList);
		    
		    if (unique) {
		    	constraintList.add(constraint);
		    } else {
				log.info("[constraint add]{} is index, skip!", constraintName);
			}
		}

		table.setConstraintList(constraintList);
		
	    List<Column> columnList = new ArrayList<Column>();
		List<Map<String, Object>> columns = (ArrayList<Map<String, Object>>)map.get("columns");
	    for (Map<String, Object> t : columns) {
	    	Column column = new Column();
	    	
	    	column.setDisplayOrder(t.get("ordinalPosition") != null ? Integer.parseInt(t.get("ordinalPosition").toString()) : null);
	    	
	    	String columnName = t.get("columnName").toString();
	    	column.setName(columnName);
	    	
	    	String columnComment = t.get("columnComment") != null ? t.get("columnComment").toString() : null;
	    	column.setCaption(StringUtils.hasLength(columnComment) ? columnComment : columnName);
	    	column.setDescription(columnComment);
	    	
	    	String dataType = t.get("dataType").toString();
	    	column.setDataType(dataType);
	    	
	    	String columnType = t.get("columnType").toString();
	    	Boolean unsigned = columnType.toUpperCase().indexOf("UNSIGNED") >= 0;
	    	column.setUnsigned(unsigned);
	    	
	    	Integer numericPrecision = t.get("numericPrecision") != null ? Integer.parseInt(t.get("numericPrecision").toString()) : null;
	    	Integer datetimePrecision = t.get("datetimePrecision") != null ? Integer.parseInt(t.get("datetimePrecision").toString()) : null;
	    	
	    	column.setLength(t.get("characterMaximumLength") != null ? Long.parseLong(t.get("characterMaximumLength").toString()) : null);
	    	column.setPrecision(numericPrecision != null ? numericPrecision : datetimePrecision);
	    	column.setScale(t.get("numericScale") != null ? Integer.parseInt(t.get("numericScale").toString()) : null);
	    	column.setDefaultValue(t.get("columnDefault") != null ? t.get("columnDefault").toString() : null);
	    	column.setNullable(Boolean.parseBoolean(t.get("nullable").toString()));
	    	
	    	
	    	String extra = t.get("extra").toString();
	    	Boolean autoIncrement = extra.toUpperCase().equals("AUTO_INCREMENT") ? true : false;
	    	column.setAutoIncrement(autoIncrement);
	    	
//	    	String columnKey = t.get("columnKey").toString();
//	    	Boolean primary = columnKey.toUpperCase().equals("PRI") ? true : false;
//	    	column.setPrimary(primary);
	    	
	    	//索引
			Map<String, Object> signleIndex = signleIndexMap.get(columnName);
			if (signleIndex != null) {
				Boolean primary = signleIndex.get("indexName").toString().toUpperCase().equals("PRIMARY");
				Boolean unique = !signleIndex.get("nonUnique").toString().toUpperCase().equals("TRUE");
				column.setPrimary(primary);
				column.setUnique(unique);
				
				if (unique) {
					column.setConstraintName(signleIndex.get("indexName").toString());
					column.setIndexType(signleIndex.get("indexType").toString());
			    } else {
			    	column.setIndexName(signleIndex.get("indexName").toString());
					column.setIndexType(signleIndex.get("indexType").toString());
				}
				 
				
			} else {
				column.setPrimary(false);
				column.setUnique(false);
			}
	    	
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
