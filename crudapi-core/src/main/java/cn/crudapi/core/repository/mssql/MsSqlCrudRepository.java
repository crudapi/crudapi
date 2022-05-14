package cn.crudapi.core.repository.mssql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import cn.crudapi.core.constant.ApiErrorCode;
import cn.crudapi.core.dto.ColumnDTO;
import cn.crudapi.core.dto.IndexDTO;
import cn.crudapi.core.dto.IndexLineDTO;
import cn.crudapi.core.dto.TableDTO;
import cn.crudapi.core.enumeration.DataTypeEnum;
import cn.crudapi.core.enumeration.EngineEnum;
import cn.crudapi.core.enumeration.IndexTypeEnum;
import cn.crudapi.core.enumeration.OperatorTypeEnum;
import cn.crudapi.core.exception.BusinessException;
import cn.crudapi.core.query.CompositeCondition;
import cn.crudapi.core.query.LeafCondition;
import cn.crudapi.core.repository.CrudAbstractRepository;

@Component
public class MsSqlCrudRepository extends CrudAbstractRepository {
	private static final Logger log = LoggerFactory.getLogger(MsSqlCrudRepository.class);
	
	@Override
	public String getDateBaseName() {
		return "mssql";
	}
	
	@Override
	public String getSqlQuotation() {
		return "\"";
	}
	
	public String getSchema() {
		return "dbo";
	}
	
	@Override
	public String getLimitOffsetSql() {
		//offset X rows fetch next Y rows only
		return "OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY";
	}

	
	@Override
	public boolean isExistTable(String tableName) {
		LeafCondition condition1 = new LeafCondition();
		condition1.setColumnName("table_schema");
		condition1.setValue(getSchema());
		condition1.setOperatorType(OperatorTypeEnum.EQ);
		
		LeafCondition condition2 = new LeafCondition();
		condition2.setColumnName("table_name");
		condition2.setValue("tableName");
		condition2.setOperatorType(OperatorTypeEnum.EQ);
		
		CompositeCondition condition = new CompositeCondition();
		condition.add(condition1);
		condition.add(condition2);
		
		
		Long count = this.count("information_schema.tables", condition);
		
        return count > 0;
	}
	
	@Override
	public List<Map<String, Object>> getMetaDatas() {
		String sql = processTemplateToString("select-table.sql.ftl", "tableSchema", getSchema());
		List<Map<String, Object>> tableList = getJdbcTemplate().queryForList(sql);
		
		return tableList;
	}
	
	@Override
	public Map<String, Object> getMetaData(String tableName) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		
		LeafCondition condition1 = new LeafCondition();
		condition1.setColumnName("table_schema");
		condition1.setValue(getSchema());
		condition1.setOperatorType(OperatorTypeEnum.EQ);
		
		LeafCondition condition2 = new LeafCondition();
		condition2.setColumnName("table_name");
		condition2.setValue(tableName);
		condition2.setOperatorType(OperatorTypeEnum.EQ);
		
		CompositeCondition condition = new CompositeCondition();
		condition.add(condition1);
		condition.add(condition2);
		
		List<Map<String, Object>> mapList = this.list("information_schema.tables", null, condition, null, null, null);
		
		if (mapList.size() == 0) {
			throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, getSchema() + "." + tableName + "is not exist!");
		}
		
		
		map = mapList.get(0);
		
		List<Map<String, Object>> columnList = this.list("information_schema.columns", null, condition, null, null, null);
		map.put("columns", columnList);
		
		Map<String, Object> mapParams = new HashMap<String, Object>();
		mapParams.put("tableName", tableName);
		mapParams.put("tableSchema", getSchema());
		
		String sql = processTemplateToString("select-column.sql.ftl", mapParams);
		List<Map<String, Object>> columnExtList = jdbcTemplate.queryForList(sql);
		map.put("columnExts", columnExtList);
		
		sql = processTemplateToString("select-table-comment.sql.ftl", mapParams);
		List<Map<String, Object>> tableCommentList = jdbcTemplate.queryForList(sql);
		map.put("tableComment", tableCommentList.get(0));
		
		sql = processTemplateToString("select-column-comment.sql.ftl", mapParams);
		List<Map<String, Object>> columnCommentList = jdbcTemplate.queryForList(sql);
		map.put("columnComments", columnCommentList);
		
		sql = processTemplateToString("select-index.sql.ftl", mapParams);
		List<Map<String, Object>> indexLsit = jdbcTemplate.queryForList(sql);
		
		map.put("indexs", indexLsit);
		
//		sql = processTemplateToString("select-index-comment.sql.ftl", mapParams);
//		List<Map<String, Object>> indexCommentList = jdbcTemplate.queryForList(sql);
//		
		map.put("indexComments", new ArrayList<Map<String, Object>>());
		
		return map;
	}
	

	@SuppressWarnings("unchecked")
	public TableDTO reverseMetaData(String tableName) {
		Map<String, Object> metaDataMap = getMetaData(tableName);
		TableDTO tableDTO = new TableDTO();
		tableDTO.setName(tableName);
		
		Object tableComment = ((Map<String, Object>)metaDataMap.get("tableComment")).get("comment");
		String tableCaption = (tableComment != null ? tableComment.toString() : tableName);
		
		tableDTO.setPluralName(tableName);
		tableDTO.setCaption(tableCaption);
		tableDTO.setDescription(tableCaption);
		tableDTO.setTableName(tableName);
		tableDTO.setEngine(EngineEnum.INNODB);
		tableDTO.setReverse(true);
		tableDTO.setReadOnly(false);
		
		List<Map<String, Object>> columns = (List<Map<String, Object>>)metaDataMap.get("columns");
		List<Map<String, Object>> columnExts = (List<Map<String, Object>>)metaDataMap.get("columnExts");
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
		
		//autoIncrement分组
		Map<String, Boolean> autoIncrementMap = new HashMap<String, Boolean>();
		for (Map<String, Object> t : columnExts) {
			String columnName = t.get("columnName").toString();
			if (t.get("isIdentity").toString().equals("1")) {
				autoIncrementMap.put(columnName, true);
			} else {
				autoIncrementMap.put(columnName, false);
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
		
		//组装columnDTOList
		List<ColumnDTO> columnDTOList = new ArrayList<ColumnDTO>();
		for (Map<String, Object> column : columns) {
			ColumnDTO columnDTO = new ColumnDTO();

			String name = column.get("COLUMN_NAME").toString();
			String comment = columnCommentMap.get(name);
			String caption = (comment != null ? comment : name);
			columnDTO.setName(name);
			columnDTO.setCaption(name);
			columnDTO.setDescription(caption);
			columnDTO.setInsertable(true);
			columnDTO.setUpdatable(true);
			columnDTO.setQueryable(true);
			columnDTO.setDisplayable(false);
			columnDTO.setUnsigned(false);
			columnDTO.setMultipleValue(false);
			
			//数据类型
//			bigint
//			binary
//			bit
//			char
//			date
//			datetime
//			decimal
//			float
//			int
//			text
//			time
//			varchar
			String DATA_TYPE = column.get("DATA_TYPE").toString();
			DataTypeEnum dataType = DataTypeEnum.VARCHAR;
			switch (DATA_TYPE) {
				case "bit": 
					dataType = DataTypeEnum.BOOL;
					break;
				case "int": 
	            	dataType = DataTypeEnum.INT;
	            	break;
	            case "bigint": 
	            	dataType = DataTypeEnum.BIGINT;
	            	break;
	            case "float": 
	            	dataType = DataTypeEnum.FLOAT;
	            	break;
	            case "decimal": 
	            	dataType = DataTypeEnum.DECIMAL;
	            	break;
	            case "varchar":
	            	dataType = DataTypeEnum.VARCHAR;
	                break;
	            case "char":
	            	dataType = DataTypeEnum.CHAR;
	                break;
	            case "text":
	            	dataType = DataTypeEnum.TEXT;
	                break;
	            case "binary":
	            	dataType = DataTypeEnum.BLOB;
	                break;
	            case "date":
	            	dataType = DataTypeEnum.DATE;
	                break;
	            case "time":
	            	dataType = DataTypeEnum.TIME;
	                break;
	            case "datetime":
	            	dataType = DataTypeEnum.DATETIME;
	                break;
	            default:
	                break;
	        }
			columnDTO.setDataType(dataType);
			
			//长度精度
			Integer length = 200;
			Integer precision = null;
			Integer scale = null; 
			
			Object characterMaximumLength = column.get("CHARACTER_MAXIMUM_LENGTH");
			if (characterMaximumLength != null) {
				length = Integer.parseInt(characterMaximumLength.toString());
			}
			
			Object numericPrecision = column.get("NUMERIC_PRECISION");
			if (numericPrecision != null) {
				precision = Integer.parseInt(numericPrecision.toString());
			}
			
			Object numericScale = column.get("NUMERIC_SCALE");
			if (numericScale != null) {
				scale = Integer.parseInt(numericScale.toString());
			}
			columnDTO.setLength(length);
			columnDTO.setPrecision(precision);
			columnDTO.setScale(scale);
			
			//是否可以为空
			String isNullable = column.get("IS_NULLABLE").toString();
			if (isNullable.equals("YES")) {
				columnDTO.setNullable(true);
			} else {
				columnDTO.setNullable(false);
			}
			
			//autoIncrement
			columnDTO.setAutoIncrement(false);
			Boolean autoIncrement = autoIncrementMap.get(name);
			if (autoIncrement != null) {
				columnDTO.setAutoIncrement(autoIncrement);
				if (autoIncrement) {
					columnDTO.setInsertable(false);
					columnDTO.setDisplayable(true);
				}
			}
			
			//默认值
			Object columnDefault = column.get("COLUMN_DEFAULT");
			String defaultValue = null;
			if (columnDefault != null) {
				defaultValue = columnDefault.toString();
				defaultValue = defaultValue.substring(2, defaultValue.length() - 2);
				columnDTO.setDefaultValue(defaultValue);
			}
			
			
			//索引
			Map<String, Object> signleIndex = signleIndexMap.get(name);
			if (signleIndex != null) {
				Boolean isPrimary = false;
				Boolean isUnique = false;
				
				String indexName = signleIndex.get("indexName").toString();
				Object indexTypeObj = signleIndex.get("indexType");
				if (indexTypeObj != null) {
					isPrimary = indexTypeObj.toString().equals("PK");
					isUnique = indexTypeObj.toString().equals("UQ");
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
				
				Object indexTypeObj = t.get("indexType");
				if (indexTypeObj != null) {
					isPrimary = indexTypeObj.toString().equals("PK");
					isUnique = indexTypeObj.toString().equals("UQ");
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
		log.info("MsSqlCrudRepository->create {}", tableName);
	
		return super.create(tableName, map);
	}
}
