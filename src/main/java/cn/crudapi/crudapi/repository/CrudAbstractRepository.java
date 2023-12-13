package cn.crudapi.crudapi.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
		
		sql = processTemplateToString("select-table-constraint.sql.ftl", mapParams);
		log.info("sql = " + sql);
		List<Map<String, Object>> tableConstraintList = this.queryForListAndConvert(sql, dbParams);
		map.put("tableConstraints", tableConstraintList);
		
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
  		
		//约束分组
        List<Map<String, Object>> constraints = (ArrayList<Map<String, Object>>)map.get("constraints");
        Map<String, List<Map<String, Object>>> constraintMap = new HashMap<String, List<Map<String, Object>>>();
        for (Map<String, Object> t : constraints) {
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

		//遍历索引
		List<Index> indexList =  new ArrayList<Index>();
		for (Map.Entry<String, List<Map<String, Object>>> e : indexMap.entrySet()) {
			String indexName = e.getKey();
			
			String caption = null;
			String indexType = null;
		    
			List<Column> indexColumnList = new ArrayList<Column>();
			List<Map<String, Object>> values = e.getValue();
			for (Map<String, Object> t : values) {
				Object indexComment = t.get("indexComment");
				caption = (indexComment != null && StringUtils.hasLength(indexComment.toString())) ? indexComment.toString() : indexName;
				
				indexType = t.get("indexType").toString();
				
				String columnName = t.get("columnName").toString();
				Column column = new Column(columnName);
				column.setName(columnName);
				indexColumnList.add(column);
			}

			log.info("[index add]{} !", indexName);
			Index index = new Index(indexName);
			index.setCaption(caption);
			index.setDescription(caption);
			index.setIndexType(indexType);
			index.setColumnList(indexColumnList);
			indexList.add(index); 
		}
		
		table.setIndexList(indexList);
	    
		//遍历约束
		List<Constraint> constraintList = new ArrayList<Constraint>();
		List<Map<String, Object>> foreignConstraints = (ArrayList<Map<String, Object>>)map.get("foreignConstraints");
		List<Map<String, Object>> tableConstraints = (ArrayList<Map<String, Object>>)map.get("tableConstraints");
        for (Map.Entry<String, List<Map<String, Object>>> e : constraintMap.entrySet()) {
        	String constraintName = e.getKey();
            List<Map<String, Object>> values = e.getValue();
            
            Constraint constraint = new Constraint(constraintName);
            String caption = null;
        	
            String refTableName = null;
            List<Column> constraintColumnList = new ArrayList<Column>();
            List<Column> refColumnList = new ArrayList<Column>();
            for (Map<String, Object> t : values) {
                caption = constraintName;
                String columnName = t.get("columnName").toString();
                Column column = new Column(columnName);
                column.setName(columnName);
                constraintColumnList.add(column);
                
                if (values.get(0).get("referencedTableName") != null) { 
                	 refTableName =  t.get("referencedTableName").toString();
                     String refColumnName = t.get("referencedColumnName").toString();
                     Column refColumn = new Column(columnName);
                     refColumn.setName(refColumnName);
                     refColumnList.add(refColumn);
            	}
            }
            
            constraint.setCaption(caption);
            constraint.setDescription(caption);
            constraint.setColumnList(constraintColumnList);
            if (refTableName != null) {
            	 constraint.setReferenceTable(new Table(refTableName));
                 constraint.setReferenceColumnList(refColumnList);
            }
           
            //UNIQUE，PRIMARY KEY，FOREIGN KEY
            Optional<Map<String, Object>> tableConstraintOp = tableConstraints.stream()
                    .filter(s -> constraintName.equals(s.get("constraintName"))).findFirst();
            if (tableConstraintOp.isPresent()) {
            	 Map<String, Object> tableConstraint = tableConstraintOp.get();
            	 String constraintType = tableConstraint.get("constraintType").toString();
            	 Boolean primary = constraintType.equals("PRIMARY KEY");
            	 Boolean unique = constraintType.equals("UNIQUE");
            	 Boolean foreign = constraintType.equals("FOREIGN KEY");
            	 
                 constraint.setPrimary(primary);
                 constraint.setUnique(primary || unique);
                 constraint.setForeign(foreign);
            }
           
            Optional<Map<String, Object>> foreignConstraintOp = foreignConstraints.stream()
            		.filter(s -> constraintName.equals(s.get("constraintName"))).findFirst();
            if (foreignConstraintOp.isPresent()) {
           	   Map<String, Object> foreignConstraint = foreignConstraintOp.get();
           	   constraint.setUpdateRule(foreignConstraint.get("updateRule").toString());
               constraint.setDeleteRule(foreignConstraint.get("deleteRule").toString()); 
            }
            
            log.info("[constraint add]{} !", constraintName);
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
	    	
	    	column.setLength(t.get("characterMaximumLength") != null ? Integer.parseInt(t.get("characterMaximumLength").toString()) : null);
	    	column.setPrecision(numericPrecision != null ? numericPrecision : datetimePrecision);
	    	column.setScale(t.get("numericScale") != null ? Integer.parseInt(t.get("numericScale").toString()) : null);
	    	column.setDefaultValue(t.get("columnDefault") != null ? t.get("columnDefault").toString() : null);
	    	column.setNullable(Boolean.parseBoolean(t.get("nullable").toString()));
	    	
	    	
	    	String extra = t.get("extra").toString();
	    	Boolean autoIncrement = extra.toUpperCase().equals("AUTO_INCREMENT") ? true : false;
	    	column.setAutoIncrement(autoIncrement);
	    	
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
