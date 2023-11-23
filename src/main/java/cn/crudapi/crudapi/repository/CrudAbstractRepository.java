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
		
		sql = processTemplateToString("select-foreign-constraint.sql.ftl", mapParams);
		log.info("sql = " + sql);
		List<Map<String, Object>> foreignConstraintList = this.queryForListAndConvert(sql, dbParams);
		map.put("foreignConstraints", foreignConstraintList);
		
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public Table getMetadata(String tableName) {
		Map<String, Object> map = this.getRawMetadata(tableName);
		
		Table table = new Table(tableName);
		table.setMetadata(map);
		
		String tableComment = map.get("tableComment") != null ? map.get("tableComment").toString() : null;
	    table.setCaption(StringUtils.hasLength(tableComment) ? tableComment : tableName);
	    table.setDescription(tableComment);
	    
	    //索引分组
	    List<Map<String, Object>> indexs = (ArrayList<Map<String, Object>>)map.get("indexs");
  		Map<String, List<Map<String, Object>>> indexMap = new HashMap<String, List<Map<String, Object>>>();
  		for (Map<String, Object> t : indexs) {
  			String indexName = t.get("indexName").toString();
  			List<Map<String, Object>> indexColumnList = indexMap.get(indexName);
  			if (indexColumnList == null) {
  				indexColumnList = new ArrayList<Map<String, Object>>();
  				indexColumnList.add(t);
  				indexMap.put(indexName, indexColumnList);
  			} else {
  				indexColumnList.add(t);
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
		
		//外建约束分组
        List<Map<String, Object>> constraints = (ArrayList<Map<String, Object>>)map.get("constraints");
        Map<String, List<Map<String, Object>>> constraintMap = new HashMap<String, List<Map<String, Object>>>();
        for (Map<String, Object> t : constraints) {
        	if (t.get("referencedTableName") == null) {
               continue;
            }
        	 
            String constraintName = t.get("constraintName").toString();
            List<Map<String, Object>> constraintColumnList = constraintMap.get(constraintName);
            if (constraintColumnList == null) {
            	constraintColumnList = new ArrayList<Map<String, Object>>();
            	constraintColumnList.add(t);
                constraintMap.put(constraintName, constraintColumnList);
            } else {
            	constraintColumnList.add(t);
            }
        }
        
        //单列外建约束和联合外建约束
        Map<String, Map<String, Object>> signleConstraintMap = new HashMap<String, Map<String, Object>>();
        Map<String, List<Map<String, Object>>> unionConstraintMap = new HashMap<String, List<Map<String, Object>>>();
        for (Map.Entry<String, List<Map<String, Object>>>  e : constraintMap.entrySet()) {
            String key = e.getKey();
            List<Map<String, Object>> value = e.getValue();
            if (value.size() == 1) {
                Map<String, Object> t = value.get(0);
                String columnName = t.get("columnName").toString();
                signleConstraintMap.put(columnName,  t);
            } else {
                unionConstraintMap.put(key, value);
            }
        }
        
		//联合索引和约束：索引中除了primary和unique，其它的是约束
		List<Index> indexList =  new ArrayList<Index>();
		List<Constraint> constraintList = new ArrayList<Constraint>();
		for (Map.Entry<String, List<Map<String, Object>>> e : unionIndexMap.entrySet()) {
			String indexName = e.getKey();
			
			String caption = null;
			String indexType = null;
			Boolean primary = false;
			Boolean unique = false;
		    
			List<Column> indexColumnList = new ArrayList<Column>();
			List<Column> constraintColumnList = new ArrayList<Column>();
			 
			List<Map<String, Object>> values = e.getValue();
			for (Map<String, Object> t : values) {
				Object indexComment = t.get("indexComment");
				caption = (indexComment != null && StringUtils.hasLength(indexComment.toString())) ? indexComment.toString() : indexName;
				
				indexType = t.get("indexType").toString();
				primary = t.get("indexName").toString().toUpperCase().equals("PRIMARY");
				unique = !t.get("nonUnique").toString().toUpperCase().equals("TRUE");
				
				String columnName = t.get("columnName").toString();
				Column column = new Column(columnName);
				column.setName(columnName);
				
				if (unique) {
					constraintColumnList.add(column);
				} else {
					indexColumnList.add(column);
				}
			}

			if (unique) {
				log.info("[unionIndex add]{} is constraint!", indexName);
				Constraint constraint = new Constraint();
				constraint.setName(indexName);
				constraint.setCaption(caption);
			    constraint.setDescription(caption);
			    constraint.setPrimary(primary);
			    constraint.setUnique(unique);
			    constraint.setForeign(false);
			    constraint.setColumnList(constraintColumnList);
			    constraintList.add(constraint);
			} else {
			    log.info("[unionIndex add]{} is index!", indexName);
				Index index = new Index();
				index.setName(indexName);
				index.setCaption(caption);
				index.setDescription(caption);
				index.setIndexType(indexType);
				index.setColumnList(indexColumnList);
				indexList.add(index);   
			}
		}
		
		table.setIndexList(indexList);
	    
		//联合外建约束
		List<Map<String, Object>> foreignConstraints = (ArrayList<Map<String, Object>>)map.get("foreignConstraints");
        for (Map.Entry<String, List<Map<String, Object>>> e : unionConstraintMap.entrySet()) {
            Constraint constraint = new Constraint();
            String constraintName = e.getKey();
            constraint.setName(constraintName);
            
            String caption = null;
            String refTableName = null;
            List<Column> constraintColumnList = new ArrayList<Column>();
            List<Column> refColumnList = new ArrayList<Column>();
            List<Map<String, Object>> values = e.getValue();
            for (Map<String, Object> t : values) {
                caption = constraintName;
                refTableName =  t.get("referencedTableName").toString();	
                
                String columnName = t.get("columnName").toString();
                Column column = new Column(columnName);
                column.setName(columnName);
                constraintColumnList.add(column);
                
                String refColumnName = t.get("referencedColumnName").toString();
                Column refColumn = new Column(columnName);
                refColumn.setName(refColumnName);
                refColumnList.add(refColumn);
            }
            
            constraint.setCaption(caption);
            constraint.setDescription(caption);
            constraint.setPrimary(false);
            constraint.setUnique(false);
            constraint.setForeign(true);
            constraint.setReferenceTable(new Table(refTableName));
            constraint.setColumnList(constraintColumnList);
            constraint.setReferenceColumnList(refColumnList);
            
            Map<String, Object> foreignConstraint = foreignConstraints.stream()
            .filter(s -> constraintName.equals(s.get("constraintName")))
            .findFirst().get();
            constraint.setUpdateRule(foreignConstraint.get("updateRule").toString());
            constraint.setDeleteRule(foreignConstraint.get("deleteRule").toString());
            
            constraintList.add(constraint);
        }

		table.setConstraintList(constraintList);
		
	    List<Column> columnList = new ArrayList<Column>();
		List<Map<String, Object>> columns = (ArrayList<Map<String, Object>>)map.get("columns");
	    for (Map<String, Object> t : columns) {
	    	String columnName = t.get("columnName").toString();
	    	Column column = new Column(columnName);
	    	
	    	column.setDisplayOrder(t.get("ordinalPosition") != null ? Integer.parseInt(t.get("ordinalPosition").toString()) : null);
	    	
	    	Object columnComment = t.get("columnComment");
	    	String caption = (columnComment != null && StringUtils.hasLength(columnComment.toString())) ? columnComment.toString() : columnName;
			
	    	column.setCaption(caption);
	    	column.setDescription(caption);
	    	
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
	    	
	    	column.setPrimary(false);
			column.setUnique(false);
			column.setForeign(false);
			
	    	//单列索引和约束：索引中除了primary和unique，其它的是约束
			//创建主键索引或者唯一索引的时候同时创建了相应的约束；但是约束是逻辑上的概念；索引是一个数据结构既包含逻辑的概念也包含物理的存储方式。
			Map<String, Object> signleIndex = signleIndexMap.get(columnName);
			if (signleIndex != null) {
				Object indexComment = signleIndex.get("indexComment");
				String indexCaption = (indexComment != null && StringUtils.hasLength(indexComment.toString())) ? indexComment.toString() : signleIndex.get("indexName").toString();
				
				Boolean primary = signleIndex.get("indexName").toString().toUpperCase().equals("PRIMARY");
				Boolean unique = !signleIndex.get("nonUnique").toString().toUpperCase().equals("TRUE");
				column.setPrimary(primary);
				column.setUnique(unique);
				
				if (unique) {
					column.setIndexName(signleIndex.get("indexName").toString());
					column.setIndexCaption(indexCaption);
					column.setIndexDescription(indexCaption);
					column.setConstraintName(signleIndex.get("indexName").toString());
					column.setIndexType(signleIndex.get("indexType").toString());
			    } else {
			    	column.setIndexName(signleIndex.get("indexName").toString());
			    	column.setIndexCaption(indexCaption);
					column.setIndexDescription(indexCaption);
					column.setIndexType(signleIndex.get("indexType").toString());
				}
			}
			
			//单列外建约束
			Map<String, Object> signleConstraint = signleConstraintMap.get(columnName);
			if (signleConstraint != null) {	
				String constraintName = signleConstraint.get("constraintName").toString();
				column.setConstraintName(constraintName);
				column.setReferenceTable(new Table(signleConstraint.get("referencedTableName").toString()));
				column.setReferenceColumn(new Column(signleConstraint.get("referencedColumnName").toString()));
				Map<String, Object> foreignConstraint = foreignConstraints.stream()
		            .filter(s -> constraintName.equals(s.get("constraintName")))
		            .findFirst().get();
					column.setUpdateRule(foreignConstraint.get("updateRule").toString());
					column.setDeleteRule(foreignConstraint.get("deleteRule").toString());
				          
				column.setForeign(true);
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
