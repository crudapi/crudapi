package cn.crudapi.core.repository.oracle;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import cn.crudapi.core.constant.ApiErrorCode;
import cn.crudapi.core.dto.ColumnDTO;
import cn.crudapi.core.dto.IndexDTO;
import cn.crudapi.core.dto.IndexLineDTO;
import cn.crudapi.core.dto.TableDTO;
import cn.crudapi.core.entity.ColumnEntity;
import cn.crudapi.core.entity.TableEntity;
import cn.crudapi.core.enumeration.DataTypeEnum;
import cn.crudapi.core.enumeration.EngineEnum;
import cn.crudapi.core.enumeration.IndexTypeEnum;
import cn.crudapi.core.enumeration.OperatorTypeEnum;
import cn.crudapi.core.exception.BusinessException;
import cn.crudapi.core.query.CompositeCondition;
import cn.crudapi.core.query.Condition;
import cn.crudapi.core.query.LeafCondition;
import cn.crudapi.core.repository.CrudAbstractRepository;

@Component
public class OracleCrudRepository extends CrudAbstractRepository {
	private static final Logger log = LoggerFactory.getLogger(OracleCrudRepository.class);

	@Override
	public String getDateBaseName() {
		return "oracle";
	}
	
	public String getSchema() {
		return "crudapi";
	}
	
	@Override
	public String getSqlQuotation() {
		return "\"";
	}
	
	@Override
	public String getLimitOffsetSql() {
		return ""; //LIMIT :limit OFFSET :offset
	}
	
	public List<String> toCreateTableSql(TableEntity tableEntity) {
		String createTableSql = processTemplateToString("create-table.sql.ftl", tableEntity);
		String createSequenceSql = processTemplateToString("create-sequence.sql.ftl", tableEntity);
		String createTriggerSql = processTemplateToString("create-trigger.sql.ftl", tableEntity);
		
		if (createTableSql == null) {
			throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, "create-table.sql is empty!");
		}
		
		List<String> sqls = new ArrayList<String>();
		String[] subSqls = createTableSql.split(";");
		for (String t : subSqls) {
			String subSql = t.trim();
			if (!subSql.isEmpty()) {
				sqls.add(t);
			}
		}
		
		if (createSequenceSql != null && !createSequenceSql.isEmpty())  {
			sqls.add(createSequenceSql);
		}
		
		if (createSequenceSql != null && !createTriggerSql.isEmpty())  {
			sqls.add(createTriggerSql);
		}
		
		return sqls;
    }
	
	public List<String> toAddColumnSql(TableEntity tableEntity, ColumnEntity columnEntity) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", tableEntity.getTableName());
		map.put("tableEntity", tableEntity);
		map.put("columnEntity", columnEntity);
		
		String sql = processTemplateToString("add-column.sql.ftl", map);
		String createSequenceSql = processTemplateToString("create-column-sequence.sql.ftl", map);
		String createTriggerSql = processTemplateToString("create-column-trigger.sql.ftl", map);
		
		List<String> sqls = new ArrayList<String>();
		String[] subSqls = sql.split(";");
		for (String t : subSqls) {
			String subSql = t.trim();
			if (!subSql.isEmpty()) {
				sqls.add(t);
			}
		}
		
		if (createSequenceSql != null && !createSequenceSql.isEmpty())  {
			sqls.add(createSequenceSql);
		}
		
		if (createSequenceSql != null && !createTriggerSql.isEmpty())  {
			sqls.add(createTriggerSql);
		}
		
		return sqls;
    }
	
	public String toSelectSql(String tableName, List<String> selectNameList, Condition condition, String orderby, Integer offset, Integer limit) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT {0} FROM {1}");
        
        if (selectNameList == null) {
        	selectNameList = new ArrayList<String>();
        	selectNameList.add("*");
        }
		
        List<String> nameList = new ArrayList<String>();
        selectNameList.stream().forEach(t -> {
            nameList.add(toSqlName(t));
        });
        String columnNames = String.join(",", nameList);

        List<Object> argumentList = new ArrayList<Object>();
        argumentList.add(columnNames);
        argumentList.add(toSqlName(tableName));
        
        int paramIndex = 2;
        if (condition != null) {
        	sb.append(" WHERE {");
        	sb.append(paramIndex++);
        	sb.append("}");
        	argumentList.add(condition.toQuerySql());
        }
        
        if (orderby != null && !orderby.isEmpty()) {
        	sb.append(" ORDER BY {");
        	sb.append(paramIndex++);
        	sb.append("}");
        	argumentList.add(orderby);
        }
        
        String sql = null;
        if (offset != null) {
        	String pattern = sb.toString();
            Object[] arguments = argumentList.toArray();

            sql = MessageFormat.format(pattern, arguments);
            
            Map<String, Object> map = new HashMap<String, Object>();
    		map.put("columnNames", columnNames);
    		map.put("sql", sql);
    		map.put("offset", offset);
    		map.put("limit", limit);
    		
            String newSql = processTemplateToString("crud/select.sql.ftl", map);
    		
            return newSql;
        } else {
        	 String pattern = sb.toString();
             Object[] arguments = argumentList.toArray();

             sql = MessageFormat.format(pattern, arguments);
             
             return sql;
        }
    }
	
	@Override
	public boolean isExistTable(String tableName) {
		//select count(*) from user_tables where table_name =upper('表名')
		LeafCondition condition1 = new LeafCondition();
		condition1.setColumnName("TABLE_NAME");
		condition1.setValue(tableName);
		condition1.setOperatorType(OperatorTypeEnum.EQ);
		
		CompositeCondition condition = new CompositeCondition();
		condition.add(condition1);
		
		Long count = this.count("USER_TABLES", condition);
		
        return count > 0;
	}
	
	@Override
	public List<Map<String, Object>> getMetaDatas() {
		String sql = processTemplateToString("select-tables.sql.ftl", "tableSchema", getSchema());
		List<Map<String, Object>> tableList = getJdbcTemplate().queryForList(sql);
		
		return tableList;
	}
	
	@Override
	public Map<String, Object> getMetaData(String tableName) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		
		Map<String, Object> mapParams = new HashMap<String, Object>();
		mapParams.put("tableName", tableName);
		mapParams.put("tableSchema", getSchema());
		
		String sql = processTemplateToString("select-table.sql.ftl", mapParams);
		List<Map<String, Object>> tableList = getJdbcTemplate().queryForList(sql);
		
		if (tableList.size() == 0) {
			throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, getSchema() + "." + tableName + "is not exist!");
		}
		
		map = tableList.get(0);
		
		sql = processTemplateToString("select-column.sql.ftl", mapParams);
		List<Map<String, Object>> columnList = jdbcTemplate.queryForList(sql);
		map.put("columns", columnList);
		
		map.put("columnComments", new ArrayList<Map<String, Object>>());
		
		sql = processTemplateToString("select-index.sql.ftl", mapParams);
		List<Map<String, Object>> indexList = jdbcTemplate.queryForList(sql);
		
		map.put("indexs", indexList);
		
		sql = processTemplateToString("select-primary.sql.ftl", mapParams);
		List<Map<String, Object>> primaryList = jdbcTemplate.queryForList(sql);
		
		if (tableList.size() > 0) {
			map.put("primary", primaryList.get(0));
		} else {
			map.put("primary", new HashMap<String, Object>());
		}
		
		map.put("indexComments", new ArrayList<Map<String, Object>>());
		
		return map;
	}
	

	@SuppressWarnings("unchecked")
	public TableDTO reverseMetaData(String tableName) {
		Map<String, Object> metaDataMap = getMetaData(tableName);
		TableDTO tableDTO = new TableDTO();
		tableDTO.setName(tableName);
		
		Object tableComment = metaDataMap.get("tableComment");
		String tableCaption = (tableComment != null ? tableComment.toString() : tableName);
		
		tableDTO.setPluralName(tableName);
		tableDTO.setCaption(tableCaption);
		tableDTO.setDescription(tableCaption);
		tableDTO.setTableName(tableName);
		tableDTO.setEngine(EngineEnum.INNODB);
		tableDTO.setReverse(true);
		tableDTO.setReadOnly(false);
		
		List<Map<String, Object>> columns = (List<Map<String, Object>>)metaDataMap.get("columns");
		List<Map<String, Object>> indexs = (List<Map<String, Object>>)metaDataMap.get("indexs");
		List<Map<String, Object>> columnComments = (List<Map<String, Object>>)metaDataMap.get("columnComments");
		List<Map<String, Object>> indexComments = (List<Map<String, Object>>)metaDataMap.get("indexComments");
		
		//索引分组
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
		
		//列注释
		Map<String, String> columnCommentMap = new HashMap<String, String>();
		for (Map<String, Object> comment : columnComments) {
			Object commentObj = comment.get("comment");
			String commentStr = (commentObj != null ? commentObj.toString(): null);
			columnCommentMap.put(comment.get("columnName").toString(), commentStr);
		}
		
		//索引注释
		Map<String, String> indexCommentMap = new HashMap<String, String>();
		for (Map<String, Object> comment : indexComments) {
			Object commentObj = comment.get("comment");
			String commentStr = (commentObj != null ? commentObj.toString(): null);
			indexCommentMap.put(comment.get("indexName").toString(), commentStr);
		}
		
		Map<String, Object> primaryMap = (Map<String, Object>)metaDataMap.get("primary");
		String primaryIndexName = primaryMap.get("CONSTRAINT_NAME").toString();
		
		//组装columnDTOList
		Integer displayOrder = 1;
		List<ColumnDTO> columnDTOList = new ArrayList<ColumnDTO>();
		for (Map<String, Object> column : columns) {
			ColumnDTO columnDTO = new ColumnDTO();

			String name = column.get("columnName").toString();
			Object commentObj = column.get("comment");
			String caption = (commentObj != null ? commentObj.toString() : name);
			columnDTO.setName(name);
			columnDTO.setCaption(caption);
			columnDTO.setDescription(caption);
			columnDTO.setInsertable(true);
			columnDTO.setUpdatable(true);
			columnDTO.setQueryable(true);
			columnDTO.setDisplayable(false);
			columnDTO.setUnsigned(false);
			columnDTO.setMultipleValue(false);
			columnDTO.setDisplayOrder(displayOrder++);
			
			//数据类型
//			DATE
//			CHAR
//			BLOB
//			NUMBER
//			FLOAT
//			VARCHAR2
//			LONG
			//长度精度
			Integer length = 200;
			Integer precision = null;
			Integer scale = null; 
			
			Object dataLength = column.get("dataLength");
			if (dataLength != null) {
				length = Integer.parseInt(dataLength.toString());
			}
			
			Object dataPrecision = column.get("dataPrecision");
			if (dataPrecision != null) {
				precision = Integer.parseInt(dataPrecision.toString());
			}
			
			Object dataScale = column.get("dataScale");
			if (dataScale != null) {
				scale = Integer.parseInt(dataScale.toString());
			}
			columnDTO.setLength(length);
			columnDTO.setPrecision(precision);
			columnDTO.setScale(scale);
			
			String dataTypeRow = column.get("dataType").toString();
			DataTypeEnum dataType = DataTypeEnum.VARCHAR;
			switch (dataTypeRow) {
				case "NUMBER": 
					if (precision != null && precision.equals(1) 
						&& scale != null && scale.equals(0)) {
						dataType = DataTypeEnum.BOOL;
					} else if (scale != null && scale.equals(0)) {
						dataType = DataTypeEnum.BIGINT;
					} else {
						dataType = DataTypeEnum.DECIMAL;
					}
					break;
	            case "FLOAT": 
	            	dataType = DataTypeEnum.FLOAT;
	            	break;
	            case "VARCHAR2":
	            	dataType = DataTypeEnum.VARCHAR;
	                break;
	            case "CHAR":
	            	dataType = DataTypeEnum.CHAR;
	                break;
	            case "LONG":
	            	dataType = DataTypeEnum.LONGTEXT;
	                break;
	            case "BLOB":
	            	dataType = DataTypeEnum.BLOB;
	                break;
	            case "DATE":
	            	dataType = DataTypeEnum.DATE;
	                break;
	            default:
	                break;
	        }
			columnDTO.setDataType(dataType);
			
			//是否可以为空
			String isNullable = column.get("nullable").toString();
			if (isNullable.equals("Y")) {
				columnDTO.setNullable(true);
			} else {
				columnDTO.setNullable(false);
			}
			
			//默认值
			Object columnDefault = column.get("dataDefault");
			String defaultValue = null;
			if (columnDefault != null) {
				defaultValue = columnDefault.toString();
				columnDTO.setDefaultValue(defaultValue);
			}
			
			//索引
			Map<String, Object> signleIndex = signleIndexMap.get(name);
			if (signleIndex != null) {
				Boolean isPrimary = false;
				Boolean isUnique = false;
				
				String indexName = signleIndex.get("indexName").toString();
				if (primaryIndexName.equals(indexName)) {
					isPrimary = true;
					//autoIncrement
					columnDTO.setAutoIncrement(true);
					columnDTO.setInsertable(false);
					columnDTO.setDisplayable(true);
				} else {
					Object indexTypeObj = signleIndex.get("uniqueness");
					if (indexTypeObj != null) {
						isUnique = indexTypeObj.toString().equals("UNIQUE");
					}
				}
				
				if (isPrimary) {
					columnDTO.setIndexType(IndexTypeEnum.PRIMARY);
				} else if (isUnique) {
					columnDTO.setIndexType(IndexTypeEnum.UNIQUE);
				} else {
					columnDTO.setIndexType(IndexTypeEnum.INDEX);
				}
				
				columnDTO.setIndexName(indexName);
			}
			
			columnDTOList.add(columnDTO);
		}
		
		tableDTO.setColumnDTOList(columnDTOList);
		
		List<IndexDTO> indexDTOList =  new ArrayList<IndexDTO>();
		for (Map.Entry<String, List<Map<String, Object>>>  e : unionIndexMap.entrySet()) {
			IndexDTO indexDTO = new IndexDTO();
			String indexName = e.getKey();
			String comment = indexCommentMap.get(indexName);
			String caption = (comment != null ? comment : indexName);
			
			indexDTO.setName(indexName);
			indexDTO.setCaption(indexName);
			indexDTO.setDescription(caption);
			
			List<IndexLineDTO> indexLineDTOList = new ArrayList<IndexLineDTO>();
			List<Map<String, Object>> values = e.getValue();
			for (Map<String, Object> t : values) {
				String columnName = t.get("columnName").toString();
				Boolean isPrimary = false;
				Boolean isUnique = false;
				
				if (primaryIndexName.equals(indexName)) {
					isPrimary = true;
				} else {
					Object indexTypeObj = t.get("uniqueness");
					if (indexTypeObj != null) {
						isUnique = indexTypeObj.toString().equals("UNIQUE");
					}
				}
				
				ColumnDTO columnDTO = new ColumnDTO();
				columnDTO.setName(columnName);
				
				IndexLineDTO indexLineDTO = new IndexLineDTO();
				indexLineDTO.setColumnDTO(columnDTO);
				if (isPrimary) {
					indexDTO.setIndexType(IndexTypeEnum.PRIMARY);
				} else if (isUnique) {
					indexDTO.setIndexType(IndexTypeEnum.UNIQUE);
				} else {
					indexDTO.setIndexType(IndexTypeEnum.INDEX);
				}
				
				indexLineDTOList.add(indexLineDTO);
			}
			
			indexDTO.setIndexLineDTOList(indexLineDTOList);
			
			indexDTOList.add(indexDTO);
		}
		
		tableDTO.setIndexDTOList(indexDTOList);
		
		return tableDTO;
	}
	
	@Override
	public Long create(String tableName, Map<String, Object> map) {
		log.info("OracleCrudRepository->create {}", tableName);
		
		KeyHolder keyHolder = null;
		if (map.get(COLUMN_ID) != null) {
			keyHolder = insert(tableName, map, null);
		} else {
			keyHolder = insert(tableName, map,  new String[] { getSqlQuotation() + COLUMN_ID + getSqlQuotation() });
		}
				
		return Long.parseLong(keyHolder.getKeyList().get(0).get(COLUMN_ID).toString());
	}
	
	@Override
	public Map<String, Object> create(String tableName, Map<String, Object> map, String[] keyColumnNames, boolean autoIncrement) {
		log.info("OracleCrudRepository->create {}", tableName);
		
		KeyHolder keyHolder = null;
		
		if (autoIncrement) {
			keyHolder = insert(tableName, map,  new String[] { getSqlQuotation() + keyColumnNames[0] + getSqlQuotation() });
		} else {
			keyHolder = insert(tableName, map, null);
		}
		
		Map<String, Object> autoKey = keyHolder.getKeys();
		if (autoIncrement && autoKey != null && autoKey.get(keyColumnNames[0]) != null) {
			return autoKey;
		} else {
			Map<String, Object> key = new HashMap<String, Object>();
			for (String keyColumnName : keyColumnNames) {
				key.put(keyColumnName, map.get(keyColumnName));
			}
			return key;
		}
	}
}
