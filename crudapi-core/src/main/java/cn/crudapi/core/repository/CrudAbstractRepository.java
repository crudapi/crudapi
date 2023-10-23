package cn.crudapi.core.repository;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import cn.crudapi.core.constant.ApiErrorCode;
import cn.crudapi.core.datasource.config.DynamicDataSourceProvider;
import cn.crudapi.core.dto.ColumnDTO;
import cn.crudapi.core.dto.IndexDTO;
import cn.crudapi.core.dto.IndexLineDTO;
import cn.crudapi.core.dto.TableDTO;
import cn.crudapi.core.entity.ColumnEntity;
import cn.crudapi.core.entity.IndexEntity;
import cn.crudapi.core.entity.TableEntity;
import cn.crudapi.core.enumeration.DataTypeEnum;
import cn.crudapi.core.enumeration.EngineEnum;
import cn.crudapi.core.enumeration.IndexStorageEnum;
import cn.crudapi.core.enumeration.IndexTypeEnum;
import cn.crudapi.core.enumeration.OperatorTypeEnum;
import cn.crudapi.core.query.CompositeCondition;
import cn.crudapi.core.query.Condition;
import cn.crudapi.core.query.LeafCondition;
import cn.crudapi.core.template.TemplateParse;
import cn.crudapi.core.exception.BusinessException;

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
	
	public List<String> toCreateTableSql(TableEntity tableEntity) {
		String createTableSql = processTemplateToString("create-table.sql.ftl", tableEntity);
		
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
		
		return sqls;
    }
	
	public List<String> toDropTableSql(TableEntity tableEntity) {
		String  sql = processTemplateToString("drop-table.sql.ftl", tableEntity);
		List<String> sqls = new ArrayList<String>();
		String[] subSqls = sql.split(";");
		for (String t : subSqls) {
			String subSql = t.trim();
			if (!subSql.isEmpty()) {
				sqls.add(t);
			}
		}
		
		return sqls;
    }
	
    public String toRenameTableSql(String oldTableName, String newTableName) {
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("oldTableName", oldTableName);
		map.put("newTableName", newTableName);
		
		return processTemplateToString("rename-table.sql.ftl", map);
    }

    public String toSetTableEngineSql(String tableName, EngineEnum engine) {
        Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", tableName);
		map.put("engine",  engine.getCode());
		
		return processTemplateToString("rename-engine.sql.ftl", map);
    }

    public String toDeleteIndexSql(String tableName, IndexTypeEnum oldIndexType, String oldIndexName) {
    	Map<String, Object> map = new HashMap<String, Object>();
 		map.put("tableName", tableName);
 		map.put("oldIndexType", oldIndexType);
 		map.put("oldIndexName", oldIndexName);
 		
 		return processTemplateToString("drop-index.sql.ftl", map);
    }
    
    public String toUpdateColumnSql(TableEntity tableEntity, ColumnEntity oldColumnEntity,  ColumnEntity columnEntity) {
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", tableEntity.getTableName());
		map.put("oldColumnName", oldColumnEntity.getName());
		map.put("oldColumnNullable", oldColumnEntity.getNullable());
		map.put("oldColumnEntity", oldColumnEntity);
		map.put("columnEntity", columnEntity);
		
		return processTemplateToString("update-column.sql.ftl", map);
    }

    public String toUpdateColumnIndexSql(TableEntity tableEntity, ColumnEntity oldColumnEntity,  ColumnEntity columnEntity) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("tableName", tableEntity.getTableName());
		map.put("oldColumnName", oldColumnEntity.getName());
        map.put("oldIndexType", oldColumnEntity.getIndexType());
 	    map.put("oldIndexName", oldColumnEntity.getIndexName());
		map.put("columnEntity", columnEntity);
		
		return processTemplateToString("update-column-index.sql.ftl", map);
    }
    
	public List<String> toAddColumnSql(TableEntity tableEntity, ColumnEntity columnEntity) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", tableEntity.getTableName());
		map.put("tableEntity", tableEntity);
		map.put("columnEntity", columnEntity);
		
		String sql = processTemplateToString("add-column.sql.ftl", map);
		
		List<String> sqls = new ArrayList<String>();
		String[] subSqls = sql.split(";");
		for (String t : subSqls) {
			String subSql = t.trim();
			if (!subSql.isEmpty()) {
				sqls.add(t);
			}
		}
		
		return sqls;
    }
	

    public  List<String> toDeleteColumnSql(TableEntity tableEntity, ColumnEntity columnEntity) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tableName", tableEntity.getTableName());
		map.put("columnName", columnEntity.getName());
		map.put("tableEntity", tableEntity);
		map.put("columnEntity", columnEntity);
		
		String sql = processTemplateToString("drop-column.sql.ftl", map);
		
		List<String> sqls = new ArrayList<String>();
		String[] subSqls = sql.split(";");
		for (String t : subSqls) {
			String subSql = t.trim();
			if (!subSql.isEmpty()) {
				sqls.add(t);
			}
		}
		
		return sqls;
    }

    public String toAddIndexSql(String tableName, IndexEntity indexEntity) {
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", tableName);
		map.put("indexEntity", indexEntity);
		
		return processTemplateToString("add-index.sql.ftl", map);
    }

    public String toUpdateIndexSql(String tableName, IndexTypeEnum oldIndexType, String oldIndexName, IndexEntity indexEntity) {
    	Map<String, Object> map = new HashMap<String, Object>();
		map.put("tableName", tableName);
		map.put("oldIndexType", oldIndexType);
		map.put("oldIndexName", oldIndexName);
		map.put("indexEntity", indexEntity);
		
		return processTemplateToString("update-index.sql.ftl", map);
    }

	public String toSqlName(String colunmName) {
        if (colunmName.equals("*")) {
          return colunmName;
        } else {
          String[] colunmNameArray = colunmName.split("\\.");
          List<String> colunmNameList = new ArrayList<String>();
          for (String t :colunmNameArray) {
        	  StringBuilder sb = new StringBuilder();
        	  sb.append(getSqlQuotation());
        	  sb.append(t);
        	  sb.append(getSqlQuotation());
        	  colunmNameList.add(sb.toString());
          }
          return String.join(".", colunmNameList);
        }
    }
	
	public String toSqlValue(String colunmName) {
        StringBuilder sb = new StringBuilder();
	    sb.append(":");
        sb.append(colunmName);
        return sb.toString();
    }
	
	public String toInserSql(String tableName, List<String> columnNameList) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO {0}({1}) VALUES({2})");
       
        List<String> nameList = new ArrayList<String>();
        List<String> valueList = new ArrayList<String>();
        columnNameList.stream().forEach(t -> {
            nameList.add(toSqlName(t));
            valueList.add(toSqlValue(t));
        });
        String columnNames = String.join(",", nameList);
        String columnValues = String.join(",", valueList);

        String pattern = sb.toString();
        Object[] arguments = { toSqlName(tableName), columnNames, columnValues };

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }
	
	public String toBatchInserSql(String tableName, List<String> columnNameList, String action) {
		String dateBase = getDateBaseName();
        StringBuilder sb = new StringBuilder();
        if (action == null) {
        	sb.append("INSERT INTO {0}({1}) VALUES({2})");
        } else {
        	if (dateBase.equals("mysql")) {
        		sb.append("REPLACE INTO {0}({1}) VALUES({2})");
        	} else {
        		sb.append("INSERT INTO {0}({1}) VALUES({2})");
        	}
        }
        
        List<String> nameList = new ArrayList<String>();
        List<String> valueList = new ArrayList<String>();
        columnNameList.stream().forEach(t -> {
            nameList.add(toSqlName(t));
            valueList.add(toSqlValue(t));
        });
        String columnNames = String.join(",", nameList);
        String columnValues = String.join(",", valueList);

        String pattern = sb.toString();
        Object[] arguments = { toSqlName(tableName), columnNames, columnValues };

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }
	
	public String toUpdateSql(String tableName, List<String> columnNameList, String keyColumnName) {
		List<String> keyColumnNames = new ArrayList<String>();
		keyColumnNames.add(keyColumnName);
        return toUpdateSql(tableName, columnNameList, keyColumnNames);
    }
	
	public String toUpdateSql(String tableName, List<String> columnNameList, List<String> keyColumnNames) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE {0} SET {1} WHERE {2}");

        List<String> nameAndValueList = new ArrayList<String>();
        columnNameList.stream().forEach(t -> {
            nameAndValueList.add(toSqlName(t) + "=:" + t);
        });
        String nameAndValues = String.join(",", nameAndValueList);

        List<String> whereNameAndValuesList = new ArrayList<String>();
        keyColumnNames.stream().forEach(t -> {
        	whereNameAndValuesList.add(toSqlName(t) + "=:" + t);
        });
        String whereNameAndValues = String.join(" AND ", whereNameAndValuesList);

        String pattern = sb.toString();
        Object[] arguments = { toSqlName(tableName), nameAndValues, whereNameAndValues};

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }

	public String toDeleteSql(String tableName, Condition condition) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM {0} WHERE {1}");
        
        String pattern = sb.toString();
        Object[] arguments = { toSqlName(tableName), condition.toQuerySql()};

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }
	
	public String toDeleteSql(String tableName, String keyColumnName) {
		List<String> keyColumnNames = new ArrayList<String>();
		keyColumnNames.add(keyColumnName);
        return toDeleteSql(tableName, keyColumnNames);
    }
	
	public String toDeleteSql(String tableName, List<String> keyColumnNames) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM {0} WHERE {1}");

        List<String> whereNameAndValuesList = new ArrayList<String>();
        keyColumnNames.stream().forEach(t -> {
        	whereNameAndValuesList.add(toSqlName(t) + "=:" + t);
        });
        String whereNameAndValues = String.join(" AND ", whereNameAndValuesList);

        String pattern = sb.toString();
        Object[] arguments = { toSqlName(tableName), whereNameAndValues};

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }
	
	public String toDeleteSql(String tableName) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM {0}");

        String pattern = sb.toString();
        Object[] arguments = { toSqlName(tableName) };

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }
	
	//get one sql
	public String toGetSql(String tableName, List<String> selectNameList, List<String> keyColumnNames) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT {0} FROM {1}");
        
        List<String> nameList = new ArrayList<String>();
        selectNameList.stream().forEach(t -> {
            nameList.add(toSqlName(t));
        });
        String columnNames = String.join(",", selectNameList);

        List<Object> argumentList = new ArrayList<Object>();
        argumentList.add(columnNames);
        argumentList.add(toSqlName(tableName));
        
        if (keyColumnNames != null && keyColumnNames.size() > 0) {
        	sb.append(" WHERE {2}");
        	List<String> whereNameAndValuesList = new ArrayList<String>();
        	keyColumnNames.stream().forEach(t -> {
        		whereNameAndValuesList.add(toSqlName(t) + "=:" + t);
        	});
        	String whereNameAndValues = String.join(" AND ", whereNameAndValuesList);

        	argumentList.add(whereNameAndValues);
        }
        
        String pattern = sb.toString();
        Object[] arguments = argumentList.toArray();

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }
	
	public String toGetForUpdateSql(String tableName, List<String> selectNameList, List<String> keyColumnNames) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT {0} FROM {1}");
        
        List<String> nameList = new ArrayList<String>();
        selectNameList.stream().forEach(t -> {
            nameList.add(toSqlName(t));
        });
        String columnNames = String.join(",", selectNameList);

        List<Object> argumentList = new ArrayList<Object>();
        argumentList.add(columnNames);
        argumentList.add(toSqlName(tableName));
        
        if (keyColumnNames != null && keyColumnNames.size() > 0) {
        	sb.append(" WHERE {2}");
        	List<String> whereNameAndValuesList = new ArrayList<String>();
        	keyColumnNames.stream().forEach(t -> {
        		whereNameAndValuesList.add(toSqlName(t) + "=:" + t);
        	});
        	String whereNameAndValues = String.join(" AND ", whereNameAndValuesList);

        	argumentList.add(whereNameAndValues);
        }
        
        sb.append(" FOR UPDATE");
        String pattern = sb.toString();
        Object[] arguments = argumentList.toArray();

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }
	
	public String toGetSql(String tableName, List<String> keyColumnNames) {
		List<String> selectNameList = new ArrayList<String>();
		selectNameList.add("*");
		return toGetSql(tableName, selectNameList, keyColumnNames);
    }
	
	public String toGetSql(String tableName, String keyColumnName) {
		List<String> keyColumnNames = new ArrayList<String>();
		keyColumnNames.add(keyColumnName);
		return toGetSql(tableName, keyColumnNames);
    }
	
	public String toGetForUpdateSql(String tableName, List<String> keyColumnNames) {
		List<String> selectNameList = new ArrayList<String>();
		selectNameList.add("*");
		return toGetForUpdateSql(tableName, selectNameList, keyColumnNames);
    }
	
	public String toGetForUpdateSql(String tableName, String keyColumnName) {
		List<String> keyColumnNames = new ArrayList<String>();
		keyColumnNames.add(keyColumnName);
		return toGetForUpdateSql(tableName, keyColumnNames);
    }
	
	//get list sql
	//SELECT * FROM `ca_meta_column` ORDER by tableId asc, name asc;
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
        
        if (limit != null) {
        	if (offset != null) {
        		sb.append(" ");
        		sb.append(getLimitOffsetSql());
            } else {
            	sb.append(" LIMIT :limit");
            }
        }
        
        String pattern = sb.toString();
        Object[] arguments = argumentList.toArray();

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }
	
	public String toSelectSql(String tableName, Condition condition, String orderby, Integer offset, Integer limit) {
		return toSelectSql(tableName, null, condition, orderby, offset, limit);
    }
	
	public String toSelectSql(String tableName, Condition condition, Integer offset, Integer limit) {
		return toSelectSql(tableName, condition, null, offset, limit);
    }
	
	public String toSelectSql(String tableName, Integer offset, Integer limit) {
		return toSelectSql(tableName, null, offset, limit);
    }
	
	//SELECT count(*) FROM `ca_meta_column` ;
	public String toCountSql(String tableName, Condition condition) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT count(*) FROM {0}");
        
        List<Object> argumentList = new ArrayList<Object>();
        argumentList.add(toSqlName(tableName));
        
        if (condition != null) {
        	sb.append(" WHERE {1}");
        	argumentList.add(condition.toQuerySql());
        }
        
        String pattern = sb.toString();
        Object[] arguments = argumentList.toArray();

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }
	
	public String toCountSql(String tableName) {
		return toCountSql(tableName, null);
    }
	
	/* create创建 */
	public Long create(String tableName, Map<String, Object> map) {
		log.info("CrudAbstractRepository->create {}", tableName);
		
		KeyHolder keyHolder = insert(tableName, map, null);
		
		Number key = keyHolder.getKey();
		if (key != null) {
			return keyHolder.getKey().longValue();
		} else {
			return Long.parseLong(map.get(COLUMN_ID).toString());
		}
	}
	
	public Map<String, Object> create(String tableName, Map<String, Object> map, String[] keyColumnNames,  boolean autoIncrement) {
		log.info("CrudAbstractRepository->create {}", tableName);
		
		KeyHolder keyHolder = null;
		
		if (autoIncrement) {
			keyHolder = insert(tableName, map,  new String[] { getSqlQuotation() + keyColumnNames[0] + getSqlQuotation() });
		} else {
			keyHolder = insert(tableName, map, null);
		}
		
		Map<String, Object> autoKey = keyHolder.getKeys();
		if (autoIncrement && autoKey != null) {
			Map<String, Object> key = new HashMap<String, Object>();
			key.put(keyColumnNames[0], keyHolder.getKey().longValue());
			
			return key;
		} else {
			Map<String, Object> key = new HashMap<String, Object>();
			for (String keyColumnName : keyColumnNames) {
				key.put(keyColumnName, map.get(keyColumnName));
			}
			return key;
		}
	}
	
	public Long create(String tableName, Object obj) {
		log.info("CrudAbstractRepository->create {}", tableName);
		
		Map<String, Object> map = convertObjectToMap(obj);

		return this.create(tableName, map);
	}
	
	public Map<String, Object> create(String tableName, Object obj, String[] keyColumnNames, boolean autoIncrement) {
		log.info("CrudAbstractRepository->create {}", tableName);
		
		Map<String, Object> map = convertObjectToMap(obj);

		return this.create(tableName, map, keyColumnNames, autoIncrement);
	}

	public int[] batchCreateMap(String tableName, List<Map<String, Object>> mapList) {
		if (mapList == null || mapList.size() == 0) {
			return new int[]{};
		}
		
		log.info("CrudAbstractRepository->batchCreateMap {}", tableName);
		
		int[] rows = this.batchInsert(tableName, mapList);
		
		return rows;
	}

	public int[] batchCreateObj(String tableName, List<Object> objList) {
		log.info("CrudAbstractRepository->batchCreateMap {}", tableName);
		
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		objList.stream().forEach(t -> {
			mapList.add(convertObjectToMap(t));
		});
		
		int[] rows = this.batchCreateMap(tableName, mapList);
		
		return rows;
	}
	
	/* put更新全部字段 */
	public void put(String tableName, Long id, Map<String, Object> dataMap) {
		log.info("CrudAbstractRepository->put");
		this.update(tableName, id, dataMap);
	}
	
	public void put(String tableName, Map<String, Object> keyMap, Map<String, Object> dataMap) {
		log.info("CrudAbstractRepository->put");
		this.update(tableName, keyMap, dataMap);
	}
	
	public void put(String tableName, Map<String, Object> keyMap, Object obj) {
		log.info("CrudAbstractRepository->put");
		this.update(tableName, keyMap, convertObjectToMap(obj));
	}
	
	public void put(String tableName, Long id, Object obj) {
		log.info("CrudAbstractRepository->put");
		this.update(tableName, id, convertObjectToMap(obj));
	}
	
	public int[] batchPutMap(String tableName, List<Map<String, Object>> mapList) {
		if (mapList == null || mapList.size() == 0) {
			return new int[]{};
		}
		
		log.info("CrudAbstractRepository->batchPutMap {}", tableName);
		
		int[] rows = this.batchUpdate(tableName, mapList);
		
		return rows;
	}
	
	public int[] batchPutObj(String tableName, List<Object> objList) {
		log.info("CrudAbstractRepository->batchPutObj {}", tableName);
		
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		objList.stream().forEach(t -> {
			mapList.add(convertObjectToMap(t, true));
		});
		
		int[] rows = this.batchPutMap(tableName, mapList);
		
		return rows;
	}
	
	/* patch更新部分非null字段 */
	public void patch(String tableName, Long id, Map<String, Object> dataMap) {
		log.info("CrudAbstractRepository->patch");
		this.update(tableName, id, filterNullFiled(dataMap));
	}
	
	public void patch(String tableName, Map<String, Object> keyMap, Map<String, Object> dataMap) {
		log.info("CrudAbstractRepository->patch");
		this.update(tableName, keyMap, filterNullFiled(dataMap));
	}
	
	public void patch(String tableName, Map<String, Object> keyMap, Object obj) {
		log.info("CrudAbstractRepository->patch");
		this.update(tableName, keyMap, convertObjectToMapIgnoreNull(obj));
	}
	
	public void patch(String tableName, Long id, Object obj) {
		log.info("CrudAbstractRepository->patch");
		this.update(tableName, id, convertObjectToMapIgnoreNull(obj));
	}
	
	public int delete(String tableName) {
		String sql = toDeleteSql(tableName);
		log.info("CrudAbstractRepository->delete {}", sql);
		
		int rows = namedParameterJdbcTemplate.update(sql, new HashMap<String, Object>());
		
		log.info("CrudAbstractRepository->delete->rows, {}", rows);
		
		return rows;
	}
	
	public int delete(String tableName, Map<String, Object> keyMap) {
		List<String> primaryNameList = convertToPrimaryNameList(keyMap);
				
		String sql = toDeleteSql(tableName, primaryNameList);
		log.info("CrudAbstractRepository->delete {}", sql);
		
		int rows = namedParameterJdbcTemplate.update(sql, convertToSqlParameterSource(keyMap));
		
		log.info("CrudAbstractRepository->delete->rows, {}", rows);

		return rows;
	}
	
	public int delete(String tableName, Long id) {
		Map<String, Object> keyMap = convertToKeyMap(id);
		return this.delete(tableName, keyMap);
	}
	
	public int delete(String tableName, Condition condition) {
		condition.build(getSqlQuotation(), 0, null);
		
		String sql = toDeleteSql(tableName, condition);
		log.info("CrudAbstractRepository->delete {}", sql);
		
		Map<String, Object> paramMap = condition.toQueryValueMap();
		
		int rows = namedParameterJdbcTemplate.update(sql, convertToSqlParameterSource(paramMap));
		
		log.info("CrudAbstractRepository->delete->rows, {}", rows);
		
		return rows;
	}
	
	public Map<String, Object> get(String tableName, Map<String, Object> keyMap) {
		return this.getForMap(tableName, keyMap);
	}
	
	public Map<String, Object> get(String tableName, Long id) {
		return this.getForMap(tableName, id);
	}
	
	public Map<String, Object> getForUpdate(String tableName, Long id) {
		return this.getForUpdateMap(tableName, id);
	}
	
	public <T> T get(String tableName, Map<String, Object> keyMap, Class<T> classType) {
		return this.getForObject(tableName, keyMap, classType);
	}
	
	public <T> T get(String tableName, Long id, Class<T> classType) {
		return this.getForObject(tableName, id, classType);
	}
	
	public Long count(String tableName) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		String sql = toCountSql(tableName);
		log.info("CrudAbstractRepository->count {}", sql);
		
		Long count = this.queryForSingleColumnValue(sql, paramMap, Long.class);
		
        return count;
	}
	
	public Long count(String tableName, Map<String, DataTypeEnum> dataTypeMap, Condition condition) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (condition != null) {
			condition.build(getSqlQuotation(), 0, dataTypeMap);
			paramMap = condition.toQueryValueMap();
		}
		
		String sql = toCountSql(tableName, condition);
		log.info("CrudAbstractRepository->count {}", sql);
		
		Long count = this.queryForSingleColumnValue(sql, paramMap, Long.class);
		
        return count;
	}
	
	public Long count(String tableName, Condition condition) { 
		return this.count(tableName, null, condition);
	}
	
	//SELECT table_name FROM information_schema.TABLES WHERE table_name ='yourname';
	public boolean isExistTable(String tableName) {
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
		
		
		Long count = this.count("information_schema.tables", condition);
		
        return count > 0;
	}
	
	public void dropTable(TableEntity tableEntity) {
		List<String> sqls = toDropTableSql(tableEntity); 
		for (String sql : sqls) {
			execute(sql);
		}
	}
	
	public List<Map<String, Object>> list(String tableName, Map<String, DataTypeEnum> dataTypeMap, List<String> selectNameList, Condition condition, String orderby, Integer offset, Integer limit) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (condition != null) {
			condition.build(getSqlQuotation(), 0, dataTypeMap);
			paramMap = condition.toQueryValueMap();
		}
		
		String sql = toSelectSql(tableName, selectNameList, condition, orderby, offset, limit);
		log.info("CrudAbstractRepository->list {}", sql);
		
		paramMap.put("offset", offset);
		paramMap.put("limit", limit);
		
		List<Map<String, Object>> mapList = this.queryForList(sql, paramMap);
		
		log.info("CrudAbstractRepository->list->{}", mapList.size());

        return mapList;
	}
	
	public List<Map<String, Object>> list(String sql, Map<String, Object> paramMap) {
		List<Map<String, Object>> mapList = this.queryForList(sql, paramMap);
		
		log.info("CrudAbstractRepository->list->{}", mapList.size());

        return mapList;
	}
	
	public Long count(String sql, Map<String, Object> paramMap) {
		Long count = this.queryForSingleColumnValue(sql, paramMap, Long.class);
		
		log.info("CrudAbstractRepository->count->{}", count);

        return count;
	}

	public List<Map<String, Object>> list(String tableName, Map<String, DataTypeEnum> dataTypeMap, Condition condition, String orderby, Integer offset, Integer limit) {
        return this.list(tableName, dataTypeMap, null, condition, orderby, offset, limit);
	}
	
	public List<Map<String, Object>> list(String tableName, String orderby, Integer offset, Integer limit) {
        return list(tableName, null, null, orderby, offset, limit);
	}
	
	public List<Map<String, Object>> list(String tableName) {
        return list(tableName, null, null, null);
	}
	
	public <T> List<T> list(String tableName, Condition condition, String orderby, Integer offset, Integer limit, Class<T> classType) { 
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if (condition != null) {
			condition.build(getSqlQuotation(), 0, null);
			paramMap = condition.toQueryValueMap();
		}
		
		String sql = toSelectSql(tableName, condition, orderby, offset, limit);
		log.info("CrudAbstractRepository->list {}", sql);
		
		paramMap.put("offset", offset);
		paramMap.put("limit", limit);
		
		List<T> mapList = this.queryForList(sql, paramMap, classType);
		
		log.info("CrudAbstractRepository->list->{}", mapList.size());

        return mapList;
	}
	
	public <T> List<T> list(String tableName, String orderby, Integer offset, Integer limit, Class<T> classType) { 
		return this.list(tableName, null, orderby, offset, limit, classType);
	}
	
	public <T> List<T> list(String tableName, Class<T> classType) {
		return list(tableName, null, null, null, classType);
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return namedParameterJdbcTemplate.getJdbcTemplate();
	}
	
	public void execute(String sql) {
		namedParameterJdbcTemplate.getJdbcTemplate().execute(sql);
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
	
	@SuppressWarnings("unchecked")
	public TableDTO reverseMetaData(String tableName) {
		Map<String, Object> metaDataMap = getMetaData(tableName);
		TableDTO tableDTO = new TableDTO();
		tableDTO.setName(tableName);
		
		Object tableCommentObj = metaDataMap.get("Comment");
		String tableCommentStr = (tableCommentObj == null) ? null : tableCommentObj.toString();
		
		String tableCaption = StringUtils.isBlank(tableCommentStr) ?  tableName: tableCommentStr;
		String engine =  metaDataMap.get("Engine").toString().toUpperCase();
		EngineEnum engineEnum = EngineEnum.valueOf(engine);

		tableDTO.setPluralName(tableName);
		tableDTO.setCaption(tableCaption);
		tableDTO.setDescription(tableCaption);
		tableDTO.setTableName(tableName);
		tableDTO.setEngine(engineEnum);
		tableDTO.setReverse(true);
		tableDTO.setReadOnly(false);
		
		List<Map<String, Object>> columns = (List<Map<String, Object>>)metaDataMap.get("columns");
		List<Map<String, Object>> indexs = (List<Map<String, Object>>)metaDataMap.get("indexs");
		
		//索引分组
		Map<String, List<Map<String, Object>>> indexMap = new HashMap<String, List<Map<String, Object>>>();
		for (Map<String, Object> t : indexs) {
			String indexName = t.get("Key_name").toString();
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
				String columnName = t.get("Column_name").toString();
				signleIndexMap.put(columnName,  t);
			} else {
				unionIndexMap.put(key, value);
			}
		}
		
		//组装columnDTOList
		Integer displayOrder = 1;
		List<ColumnDTO> columnDTOList = new ArrayList<ColumnDTO>();
		for (Map<String, Object> column : columns) {
			ColumnDTO columnDTO = new ColumnDTO();

			String name = column.get("Field").toString();
			Object commentObj = column.get("Comment");
			String commentStr = (commentObj == null) ? null : commentObj.toString();
			
			String caption = StringUtils.isBlank(commentStr) ? name : commentStr;
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
			String dataTypeRowStr = column.get("Type").toString().toUpperCase();
			Boolean unsigned = dataTypeRowStr.indexOf("UNSIGNED") >= 0;
			Integer length = 200;
			Integer precision = null;
			Integer scale = null; 
			
			String[] typeArr = dataTypeRowStr.split("\\(");
	        if (typeArr.length > 1) {
	          String lengthOrprecisionScale = typeArr[1].split("\\)")[0];
	          if (lengthOrprecisionScale.indexOf(",") > 0) {
	            String precisionStr = lengthOrprecisionScale.split(",")[0];
	            precision = Integer.parseInt(precisionStr.toString());
	            String scaleStr = lengthOrprecisionScale.split(",")[1];
	            scale = Integer.parseInt(scaleStr.toString());
	          } else {
	            String lengthStr = lengthOrprecisionScale;
	            length = Integer.parseInt(lengthStr.toString());
	          }
	        }
			
	        String dataTypeRow = typeArr[0].replace("UNSIGNED", "").trim();
	        if (dataTypeRow.equals("BIT")) {
	          dataTypeRow = "BOOL";
	          length = null;
	        }
	        DataTypeEnum dataType = DataTypeEnum.valueOf(dataTypeRow);

			columnDTO.setDataType(dataType);
	        columnDTO.setUnsigned(unsigned);
			columnDTO.setLength(length);
			columnDTO.setPrecision(precision);
			columnDTO.setScale(scale);
			
			//是否可以为空
			String isNullable = column.get("Null").toString();
			if (isNullable.equals("YES")) {
				columnDTO.setNullable(true);
			} else {
				columnDTO.setNullable(false);
			}
			
			String extra = column.get("Extra").toString();
			if (extra.equals("auto_increment")) {
				columnDTO.setAutoIncrement(true);
				columnDTO.setInsertable(false);
				columnDTO.setDisplayable(true);
			} else {
				columnDTO.setAutoIncrement(false);
			}
			
			//默认值
			Object columnDefault = column.get("Default");
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
				Boolean isFulltext = false;
				
				Object keyNameObj = signleIndex.get("Key_name");
				if (keyNameObj != null) {
					isPrimary = keyNameObj.toString().equals("PRIMARY");
				}
				
				Object indexTypeObj = signleIndex.get("Index_type");
				if (indexTypeObj != null) {
					isFulltext = indexTypeObj.toString().equals("FULLTEXT");
				}
				
				Object nonuniqueObj = signleIndex.get("Non_unique");
				if (nonuniqueObj != null) {
					isUnique = nonuniqueObj.toString().equals("0");
				}
				
				if (isPrimary) {
					columnDTO.setIndexType(IndexTypeEnum.PRIMARY);
				} else if (isFulltext) {
					columnDTO.setIndexType(IndexTypeEnum.FULLTEXT);
				}  else if (isUnique) {
					columnDTO.setIndexType(IndexTypeEnum.UNIQUE);
					columnDTO.setIndexStorage(IndexStorageEnum.valueOf(indexTypeObj.toString()));
				} else {
					columnDTO.setIndexType(IndexTypeEnum.INDEX);
					columnDTO.setIndexStorage(IndexStorageEnum.valueOf(indexTypeObj.toString()));
				}
				
				columnDTO.setIndexName(keyNameObj.toString());
			}
			
			columnDTOList.add(columnDTO);
		}
		
		tableDTO.setColumnDTOList(columnDTOList);
		
		List<IndexDTO> indexDTOList =  new ArrayList<IndexDTO>();
		for (Map.Entry<String, List<Map<String, Object>>>  e : unionIndexMap.entrySet()) {
			IndexDTO indexDTO = new IndexDTO();
			String indexName = e.getKey();
			indexDTO.setName(indexName);
			indexDTO.setCaption(indexName);
			
			List<IndexLineDTO> indexLineDTOList = new ArrayList<IndexLineDTO>();
			List<Map<String, Object>> values = e.getValue();
			for (Map<String, Object> t : values) {
				Object comment = t.get("Comment");
				String caption = (comment != null ? comment.toString() : indexName);
				indexDTO.setDescription(caption);
				
				String columnName = t.get("Column_name").toString();
				Boolean isPrimary = false;
				Boolean isUnique = false;
				Boolean isFulltext = false;
				
				Object keyNameObj = t.get("Key_name");
				if (keyNameObj != null) {
					isPrimary = keyNameObj.toString().equals("PRIMARY");
				}
				
				Object indexTypeObj = t.get("Index_type");
				if (indexTypeObj != null) {
					isFulltext = indexTypeObj.toString().equals("FULLTEXT");
				}
				
				Object nonuniqueObj = t.get("Non_unique");
				if (nonuniqueObj != null) {
					isUnique = nonuniqueObj.toString().equals("0");
				}
				
				ColumnDTO columnDTO = new ColumnDTO();
				columnDTO.setName(columnName);
				
				IndexLineDTO indexLineDTO = new IndexLineDTO();
				indexLineDTO.setColumnDTO(columnDTO);
				
				if (isPrimary) {
					indexDTO.setIndexType(IndexTypeEnum.PRIMARY);
				} else if (isFulltext) {
					indexDTO.setIndexType(IndexTypeEnum.FULLTEXT);
				}  else if (isUnique) {
					indexDTO.setIndexType(IndexTypeEnum.UNIQUE);
					indexDTO.setIndexStorage(IndexStorageEnum.valueOf(indexTypeObj.toString()));
				} else {
					indexDTO.setIndexType(IndexTypeEnum.INDEX);
					indexDTO.setIndexStorage(IndexStorageEnum.valueOf(indexTypeObj.toString()));
				}
				
				indexLineDTOList.add(indexLineDTO);
			}
			
			indexDTO.setIndexLineDTOList(indexLineDTOList);
			
			indexDTOList.add(indexDTO);
		}
		
		tableDTO.setIndexDTOList(indexDTOList);
		
		return tableDTO;
	}
	
	
	protected KeyHolder insert(String tableName, Map<String, Object> map, String[] keyColumnNames) {
		log.info("CrudAbstractRepository->insert {}", tableName);
		
		List<String> columnNameList = new ArrayList<String>();
		for (String key : map.keySet()) {
			columnNameList.add(key);
        }
		
		String sql = toInserSql(tableName, columnNameList);
		
		log.info("CrudAbstractRepository->insert {}", sql);
		
	    KeyHolder keyHolder = new GeneratedKeyHolder();
		 
	    int rows = namedParameterJdbcTemplate.update(sql, convertToSqlParameterSource(map), keyHolder, keyColumnNames);
		
	    log.info("CrudAbstractRepository->insert->rows, {}", rows);
	    
		return keyHolder;
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
	
	private Map<String, Object> convertToKeyMap(Long id) { 
		Map<String, Object> keyMap = new HashMap<String, Object>();
		keyMap.put(COLUMN_ID, id);
		return keyMap;
	}
	
	private SqlParameterSource convertToSqlParameterSource(Map<String, ?> paramMap) {
		return new SqlParameterSource() {
			@Override
			public boolean hasValue(String paramName) {
				return paramMap.containsKey(paramName);
			}
			@SuppressWarnings("unchecked")
			@Override
			public Object getValue(String paramName) throws IllegalArgumentException {
				Object obj = paramMap.get(paramName);
				if (obj instanceof List) {
					List<Object> objList = (ArrayList<Object>)(obj);
					
					List<String> strList = new ArrayList<String>();
					for (Object t : objList) {
						strList.add(t.toString());
					}
					String str = String.join(",", strList);
					return str;
				}
				
				return obj;
			}
		};
	}
	
	private List<String> convertToPrimaryNameList(Map<String, Object> keyMap) {
		//主键或者唯一性索引字段列表
		List<String> primaryNameList = new ArrayList<String>();
		for (Map.Entry<String, Object> entry : keyMap.entrySet()) {
			String key = entry.getKey();
			primaryNameList.add(key);
        }
		
		return primaryNameList;
	}
	
	private Map<String, Object> filterNullFiled(Map<String, ?> map) {
		Map<String, Object> newMap = new HashMap<String, Object>();
		
		for (Map.Entry<String, ?> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
            if (value != null) {
            	if (value.toString().equals("DBNULL")) {
            		newMap.put(key, null);
            	} else {
            		newMap.put(key, value);
            	}
            }
        }
		
        return newMap;
	}
	
	private Map<String, Object> convertObjectToMap(Object obj) {
		return this.convertObjectToMap(obj, false);
	}
	
	private Map<String, Object> convertObjectToMap(Object obj, Boolean isUpdate) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			Class<? extends Object> classType = obj.getClass();
	        Field[] declaredFields = classType.getDeclaredFields();
	        for (Field f : declaredFields) {
	            f.setAccessible(true);
	            if (!Modifier.isStatic(f.getModifiers())) {
	            	String type = f.getType().getSimpleName();
		            String key = f.getName();
		            Object value = f.get(obj);
		            
		            if (!isUpdate && key.equals("id")) { 
		            	log.debug(key);
		            	continue;
		            }
		            
		            if (key.equals("reverse")) { 
		            	log.debug(key);
		            	continue;
		            }
		            
		            if (type.equals("List")) { 
		            	log.debug(type);
		            	continue;
		            }
		            
		            if (type.indexOf("ColumnEntity") >= 0) { 
		            	log.debug(type);
		            	continue;
		            }
		            
		            if (type.indexOf("TableEntity") >= 0) { 
		            	log.debug(type);
		            	continue;
		            }
		            
		            
	            	if (type.indexOf("Enum") >= 0) {
	            		map.put(key, value != null ? value.toString() : null);
	            	} else {
	            		map.put(key, value);
	            	}
	            }
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
        return map;
	}
	
	private Map<String, Object> convertObjectToMapIgnoreNull(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			Class<? extends Object> classType = obj.getClass();
	        Field[] declaredFields = classType.getDeclaredFields();
	        for (Field f : declaredFields) {
	            f.setAccessible(true);
	            if (!Modifier.isStatic(f.getModifiers())) {
	            	String type = f.getType().getSimpleName();
		            String key = f.getName();
		            Object value = f.get(obj);
		            
		            if (key.equals("id")) { 
		            	log.debug(key);
		            	continue;
		            }
		            
		            if (key.equals("reverse")) { 
		            	log.debug(key);
		            	continue;
		            }
		            
		            if (type.equals("List")) { 
		            	log.debug(type);
		            	continue;
		            }
		            
		            if (type.indexOf("ColumnEntity") >= 0) { 
		            	log.debug(type);
		            	continue;
		            }
		            
		            if (type.indexOf("TableEntity") >= 0) { 
		            	log.debug(type);
		            	continue;
		            }
		            
		            if (value != null) {
		            	if (type.indexOf("Enum") >= 0) {
		            		map.put(key, value != null ? value.toString() : null);
		            	} else {
		            		map.put(key, value);
		            	}
		            }
	            }
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
        return map;
	}
	
	@SuppressWarnings("unchecked")
	private int[] batchInsert(String tableName, List<Map<String, Object>> mapList) {
		log.info("CrudAbstractRepository->batchInsert {}", tableName);
		
		List<String> columnNameList = new ArrayList<String>();
		for (String key : mapList.get(0).keySet()) {
			columnNameList.add(key);
        }
		
		String sql = toBatchInserSql(tableName, columnNameList, "REPLACE");
		
		log.info("CrudAbstractRepository->batchInsert {}", sql);
		
		Map<String, Object>[] batchValues = mapList.toArray(new Map[0]);
		
	    int[] rows = namedParameterJdbcTemplate.batchUpdate(sql, batchValues);
		
	    log.info("CrudAbstractRepository->batchInsert->rows, {}", rows);
	    
	    return rows;
	}
	
	private int update(String tableName, Map<String, Object> keyMap, Map<String, Object> dataMap) {
		//需要更新的字段名称列表
		List<String> columnNameList = new ArrayList<String>();
		for (String key : dataMap.keySet()) {
			if (!keyMap.containsKey(key)) {
				columnNameList.add(key);
			}
        }

		//主键或者唯一性索引字段列表
		List<String> primaryNameList = new ArrayList<String>();
		for (Map.Entry<String, Object> entry : keyMap.entrySet()) {
			String key = entry.getKey();
			primaryNameList.add(key);
			dataMap.put(key, entry.getValue());
        }
		
		String sql = toUpdateSql(tableName, columnNameList, primaryNameList);
		log.info("CrudAbstractRepository->update {}", sql);
		
		int rows = namedParameterJdbcTemplate.update(sql, convertToSqlParameterSource(dataMap));
		
		log.info("CrudAbstractRepository->update->rows, {}", rows);

        return rows;
	}
	
	private void update(String tableName, Long id, Map<String, Object> dataMap) {
		Map<String, Object> keyMap = convertToKeyMap(id);
		this.update(tableName, keyMap, dataMap);
	}
	
	@SuppressWarnings("unchecked")
	private int[] batchUpdate(String tableName, List<Map<String, Object>> mapList) {
		//需要更新的字段名称列表
		List<String> columnNameList = new ArrayList<String>();
		for (String key : mapList.get(0).keySet()) {
			if (!key.equals(COLUMN_ID)) {
				columnNameList.add(key);
			}
        }

		String sql = toUpdateSql(tableName, columnNameList, COLUMN_ID);
		log.info("CrudAbstractRepository->batchUpdate {}", sql);
		
		Map<String, Object>[] batchValues = mapList.toArray(new Map[0]);
		
	    int[] rows = namedParameterJdbcTemplate.batchUpdate(sql, batchValues);
		
	    log.info("CrudAbstractRepository->batchUpdate->rows, {}", rows);
	    
	    return rows;
	}
	
	private Map<String, Object> queryForMap(String sql, Map<String, ?> paramMap) {
        return namedParameterJdbcTemplate.queryForMap(sql, paramMap);
	}
	
	private <T> T queryForObject(String sql, Map<String, ?> paramMap, Class<T> classType) {
        return namedParameterJdbcTemplate.queryForObject(sql, paramMap, new BeanPropertyRowMapper<T>(classType));
	}

	private Map<String, Object> getForMap(String tableName, Map<String, Object> keyMap) {
		List<String> primaryNameList = this.convertToPrimaryNameList(keyMap);
		
		String sql = toGetSql(tableName, primaryNameList);
		log.info("CrudAbstractRepository->getForMap {}", sql);
		
		Map<String, Object> objMap = this.queryForMap(sql, keyMap);
		
		log.info("CrudAbstractRepository->getForMap->{}", objMap);

        return objMap;
	}
	
	private Map<String, Object> getForMap(String tableName, Long id) {
		Map<String, Object> keyMap = convertToKeyMap(id);
		return this.getForMap(tableName, keyMap);
	}
	
	private Map<String, Object> getForUpdateMap(String tableName, Map<String, Object> keyMap) {
		List<String> primaryNameList = this.convertToPrimaryNameList(keyMap);
		
		String sql = toGetForUpdateSql(tableName, primaryNameList);
		log.info("CrudAbstractRepository->getForUpdateMap {}", sql);
		
		Map<String, Object> objMap = this.queryForMap(sql, keyMap);
		
		log.info("CrudAbstractRepository->getForUpdateMap->{}", objMap);

        return objMap;
	}
	
	private Map<String, Object> getForUpdateMap(String tableName, Long id) {
		Map<String, Object> keyMap = convertToKeyMap(id);
		return this.getForUpdateMap(tableName, keyMap);
	}

	private <T> T getForObject(String tableName, Map<String, Object> keyMap, Class<T> classType) {
		List<String> primaryNameList = this.convertToPrimaryNameList(keyMap);
		
		String sql = toGetSql(tableName, primaryNameList);
		
		log.info("CrudAbstractRepository->get {}", sql);
		
		T obj = this.queryForObject(sql, keyMap, classType);
		
		log.info("CrudAbstractRepository->getForObject->{}", obj);

        return obj;
	}
	
	private <T> T getForObject(String tableName, Long id, Class<T> classType) {
		Map<String, Object> keyMap = convertToKeyMap(id);
		return this.getForObject(tableName, keyMap, classType);
	}
	
	private <T> T queryForSingleColumnValue(String sql, Map<String, ?> paramMap, Class<T> classType) {
        return namedParameterJdbcTemplate.queryForObject(sql, paramMap, classType);
	}
	
	private List<Map<String, Object>> queryForList(String sql, Map<String, ?> paramMap) {
        return namedParameterJdbcTemplate.queryForList(sql, paramMap);
	}
	
	private <T> List<T> queryForList(String sql, Map<String, ?> paramMap, Class<T> classType) {
        return namedParameterJdbcTemplate.query(sql, paramMap, new BeanPropertyRowMapper<T>(classType));
	}
}
