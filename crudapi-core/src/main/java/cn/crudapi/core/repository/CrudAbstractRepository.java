package cn.crudapi.core.repository;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

import cn.crudapi.core.entity.ColumnEntity;
import cn.crudapi.core.entity.IndexEntity;
import cn.crudapi.core.entity.IndexLineEntity;
import cn.crudapi.core.entity.TableEntity;
import cn.crudapi.core.enumeration.DataTypeEnum;
import cn.crudapi.core.enumeration.EngineEnum;
import cn.crudapi.core.enumeration.IndexStorageEnum;
import cn.crudapi.core.enumeration.IndexTypeEnum;
import cn.crudapi.core.enumeration.OperatorTypeEnum;
import cn.crudapi.core.query.Condition;
import cn.crudapi.core.query.LeafCondition;
import cn.crudapi.core.util.ToolUtils;

public abstract class CrudAbstractRepository {
	private static final Logger log = LoggerFactory.getLogger(CrudAbstractRepository.class);
	
	public static final String COLUMN_ID = "id";
	 
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public String getSqlQuotation() {
		return "`";
	}
	
	public String getLimitOffsetSql() {
		return "LIMIT :offset, :limit";
	}
	
    public String toSql(DataTypeEnum dataType, Integer length, Integer precision, Integer scale) {
        StringBuilder sb = new StringBuilder();

        switch (dataType) {
        case BIT:    
        case TINYINT:
        case SMALLINT:
        case MEDIUMINT:
        case INT:
        case BIGINT:
            if (length != null) {
                sb.append("(");
                sb.append(length.toString());
                sb.append(")");
            }
            break;
        case FLOAT:
        case DOUBLE:
        case DECIMAL:
            if (precision != null && scale != null) {
                sb.append("(");
                sb.append(precision.toString());
                sb.append(",");
                sb.append(scale.toString());
                sb.append(")");
            }
            break;
        case DATE:
        case TIME:
        case YEAR:
        case DATETIME:
        case TIMESTAMP:
            break;
        case CHAR:
        case VARCHAR:
        case TINYTEXT:
        case TEXT:
        case MEDIUMTEXT:
        case LONGTEXT:
        case PASSWORD:
        case ATTACHMENT:
            if (length != null) {
                sb.append("(");
                sb.append(length.toString());
                sb.append(")");
            }
            break;
        default:
            break;
        }

        return sb.toString();
    }
	public Boolean isNumber(DataTypeEnum dataType) {
        Boolean ret = false;

        switch (dataType) {
        case BIT:
        case TINYINT:
        case SMALLINT:
        case MEDIUMINT:
        case INT:
        case BIGINT:
        case FLOAT:
        case DOUBLE:
        case DECIMAL:
        case BOOL:
            ret = true;
            break;
        default:
            break;
        }

        return ret;
    }

    public String toDefaultValueSql(DataTypeEnum dataType, String defaultValue) {
        StringBuilder sb = new StringBuilder();

        switch (dataType) {
        case BIT:    
        case TINYINT:
        case SMALLINT:
        case MEDIUMINT:
        case INT:
        case BIGINT:
        case FLOAT:
        case DOUBLE:
        case DECIMAL:
        case BOOL:
            sb.append(defaultValue.toString());
            break;
        case DATE:
        case TIME:
        case YEAR:
        case DATETIME:
        case TIMESTAMP:
            sb.append("''");
            sb.append(defaultValue.toString());
            sb.append("''");
            break;
        case CHAR:
        case VARCHAR:
        case TINYTEXT:
        case TEXT:
        case MEDIUMTEXT:
        case LONGTEXT:
        case PASSWORD:
        case ATTACHMENT:
            sb.append("''");
            sb.append(defaultValue.toString());
            sb.append("''");
            break;
        default:
            break;
        }

        return sb.toString();
    }

    public String toIndexSql(String tableName, IndexTypeEnum indexType, IndexStorageEnum indexStorage, String columnName, String indexName, String indexCaption) {
        List<String> columnNameList = new ArrayList<String>();
        columnNameList.add(columnName);

        return toFullIndexSql(tableName, indexType, indexStorage, indexName, indexCaption, columnNameList);
    }

    public String toIndexSql(String tableName, IndexTypeEnum indexType, IndexStorageEnum indexStorage, List<IndexLineEntity> indexLineEntityList, String indexName, String indexCaption) {
        List<String> columnNameList = new ArrayList<String>();
        indexLineEntityList.stream().forEach(t -> columnNameList.add(t.getColumnEntity().getName()));

        return toFullIndexSql(tableName, indexType, indexStorage, indexName, indexCaption, columnNameList);
    }

    public String toRenameTableSql(String oldTableName, String newTableName) {
        StringBuilder sb = new StringBuilder();

        sb.append("ALTER TABLE {0} RENAME {1}");

        String pattern = sb.toString();
        Object[] arguments = { toSqlName(oldTableName), toSqlName(newTableName) };

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }

    public String toSetTableEngineSql(String tableName, EngineEnum engine) {
        StringBuilder sb = new StringBuilder();

        sb.append("ALTER TABLE {0} ENGINE={1}");

        String pattern = sb.toString();
        Object[] arguments = { toSqlName(tableName), engine.getCode() };

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }

    public String toDeleteColumnSql(String tableName, String columnName) {
        StringBuilder sb = new StringBuilder();

        sb.append("ALTER TABLE {0} DROP {1}");

        String pattern = sb.toString();
        Object[] arguments = { toSqlName(tableName), toSqlName(columnName) };

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }

    public String toDeleteIndexSql(String tableName, String oldIndexName) {
        StringBuilder sb = new StringBuilder();

        sb.append("ALTER TABLE {0} DROP INDEX {1}");

        String pattern = sb.toString();
        Object[] arguments = { toSqlName(tableName), toSqlName(oldIndexName) };

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }

    public String toFullIndexSql(String tableName, IndexTypeEnum indexType, IndexStorageEnum indexStorage, String indexName,  String indexCaption, List<String> columnNameList) {
        if (indexType == null || indexType.equals(IndexTypeEnum.NONE)) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        List<String> newColumnNameList = new ArrayList<String>();
        columnNameList.stream().forEach(t -> newColumnNameList.add(toSqlName(t)));

        String columnNames = "(" + String.join(",", newColumnNameList) + ")";
        String newIndeName = StringUtils.isBlank(indexName) ? "" : toSqlName(indexName) + " ";

        switch (indexType) {
        case PRIMARY:
            sb.append("PRIMARY KEY ");
            sb.append(columnNames);
            break;
        case UNIQUE:
            sb.append("UNIQUE ");
            sb.append(newIndeName);
            sb.append(columnNames);
            break;
        case INDEX:
            sb.append("INDEX ");
            sb.append(newIndeName);
            sb.append(columnNames);
            break;
        case FULLTEXT:
            sb.append("FULLTEXT ");
            sb.append(newIndeName);
            sb.append(columnNames);
            break;
        default:
            break;
        }

        if (indexStorage != null) {
            sb.append(" USING ");
            sb.append(indexStorage.toString());
        }
        
        
        if (!StringUtils.isEmpty(indexCaption)) {
        	sb.append(" COMMENT ''");
     		sb.append(indexCaption);
     		sb.append("''");
        }
       
        return sb.toString();
    }
    
	public String toColumnSql(ColumnEntity columnEntity) {
		DataTypeEnum dataType = columnEntity.getDataType();
		String name = columnEntity.getName();
		String caption = columnEntity.getCaption();
		Integer length = columnEntity.getLength();
		Integer precision = columnEntity.getPrecision();
		Integer scale = columnEntity.getScale();
		Boolean unsigned = columnEntity.getUnsigned();
		Boolean autoIncrement = columnEntity.getAutoIncrement();
		Boolean nullable = columnEntity.getNullable();
		String defaultValue = columnEntity.getDefaultValue();
		
		String pattern = "{0} {1}";
		String dbDataType = dataType.toString();
		
		if (dbDataType.equals("BOOL")) {
			dbDataType = "BIT";
		} else if (dbDataType.equals("ATTACHMENT")) {
			dbDataType = "VARCHAR";
		} else if (dbDataType.equals("PASSWORD")) {
			dbDataType = "VARCHAR";
		}
		
		Object[] arguments = { toSqlName(name), dbDataType };

		// name, dataType
		StringBuilder sb = new StringBuilder(MessageFormat.format(pattern, arguments));
		
		// length, precision, scale
		sb.append(toSql(dataType, length, precision, scale));

		// unsigned
		if (Objects.equals(unsigned, true) && isNumber(dataType)) {
			sb.append(" UNSIGNED");
		}

		// autoIncrement
		if (Objects.equals(autoIncrement, true)) {
			sb.append(" NOT NULL AUTO_INCREMENT");
		} else {
			if (Objects.equals(nullable, true)) {
				sb.append(" NULL");
			} else {
				sb.append(" NOT NULL");
			}

			if (defaultValue != null) {
				sb.append(" DEFAULT ");
				sb.append(toDefaultValueSql(dataType, defaultValue));
			}
		}
		
		sb.append(" COMMENT ''");
		sb.append(StringUtils.isEmpty(caption) ? name : caption );
		sb.append("''");
		
		System.out.println(sb.toString());
		
		return sb.toString();
	}
	
	public String toColumnIndexSql(String tableName, ColumnEntity columnEntity) {
		String name = columnEntity.getName();
		IndexTypeEnum indexType = columnEntity.getIndexType();
		IndexStorageEnum indexStorage = columnEntity.getIndexStorage();
		String indexName = columnEntity.getIndexName();
		
		if (indexType == null || indexType.equals(IndexTypeEnum.NONE)) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		sb.append(toIndexSql(tableName, indexType, indexStorage, name, indexName, null));

		return sb.toString();
	}
	
	public String toIndexSql(String tableName, IndexEntity indexEntity) {
		return toIndexSql(tableName,
				indexEntity.getIndexType(), 
				indexEntity.getIndexStorage(), 
				indexEntity.getIndexLineEntityList(), 
				indexEntity.getName(), 
				indexEntity.getName());
	}
	
	public String toCreateTableSql(TableEntity tableEntity) {
        List<ColumnEntity> columnEntityList = tableEntity.getColumnEntityList();
        String caption = tableEntity.getCaption();
        String name = tableEntity.getName();
        String tableName = tableEntity.getTableName();
        EngineEnum engine = tableEntity.getEngine();
        
		StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE {0} (");
        sb.append(ToolUtils.getLineSeparator());

        List<String> sqlList = new ArrayList<String>();
        for (ColumnEntity columnEntity : columnEntityList) {
            sqlList.add(toColumnSql(columnEntity));
        }

        for (ColumnEntity columnEntity : columnEntityList) {
        	String indexSql = toColumnIndexSql(tableName, columnEntity);
            if (StringUtils.isNotBlank(indexSql)) {
                sqlList.add(indexSql);
            }
        }

        String delimiter = "," + ToolUtils.getLineSeparator();

        sb.append(String.join(delimiter, sqlList));
        sb.append(ToolUtils.getLineSeparator());
        sb.append(") ENGINE={1} DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci");
        
        sb.append(" COMMENT ''");
		sb.append(StringUtils.isEmpty(caption) ? name : caption );
		sb.append("''");

        String pattern = sb.toString();
        Object[] arguments = { toSqlName(tableName), engine.getCode() };

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
	}
	
	public List<String> toCreateIndexSqlList(TableEntity tableEntity) {
		String tableName = tableEntity.getTableName();
		List<IndexEntity> indexEntityList = tableEntity.getIndexEntityList();
		
		List<String> sqlList = new ArrayList<String>();
		if (indexEntityList != null) {
			for (IndexEntity indexEntity : indexEntityList) {
				String sql = toAddIndexSql(tableName, indexEntity);
				
				if (!StringUtils.isBlank(sql)) {
					sqlList.add(sql);
				}
			}	
		}
		
		return sqlList;
	}

	public String toAddColumnSql(String tableName, ColumnEntity columnEntity) {
        StringBuilder sb = new StringBuilder();

        sb.append("ALTER TABLE {0} ADD ");
        sb.append(toColumnSql(columnEntity));

        String indexSql = toColumnIndexSql(tableName, columnEntity);
        if (!StringUtils.isBlank(indexSql)) {
            sb.append(", ADD ");
            sb.append(indexSql);
        }

        String pattern = sb.toString();
        Object[] arguments = { toSqlName(tableName) };

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }

    public String toUpdateColumnSql(String tableName, String oldColumnName, ColumnEntity columnEntity) {
        StringBuilder sb = new StringBuilder();

        sb.append("ALTER TABLE {0} CHANGE {1} ");
        sb.append(toColumnSql(columnEntity));

        String pattern = sb.toString();
        Object[] arguments = { toSqlName(tableName), toSqlName(oldColumnName) };

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }

    public String toUpdateColumnIndexSql(String tableName, String oldIndexName, ColumnEntity columnEntity) {
        StringBuilder sb = new StringBuilder();

        String indexSql = toColumnIndexSql(tableName, columnEntity);
        if (StringUtils.isAllEmpty(oldIndexName)) {
        	 if (!StringUtils.isBlank(indexSql)) {
        		 sb.append("ALTER TABLE {0} ADD ");
            	 sb.append(indexSql);
             }
        } else {
        	 sb.append("ALTER TABLE {0} DROP INDEX {1}");
        	 if (!StringUtils.isBlank(indexSql)) {
                 sb.append(", ADD ");
                 sb.append(indexSql);
             }
        }
        
        String sql = "";
    	String pattern = sb.toString();
        if (StringUtils.isAllEmpty(oldIndexName)) {
            Object[] arguments = { toSqlName(tableName)};
            sql = MessageFormat.format(pattern, arguments);
        } else {
            Object[] arguments = { toSqlName(tableName), toSqlName(oldIndexName) };
            sql = MessageFormat.format(pattern, arguments);
        }
        
        return sql;
    }

    public String toAddIndexSql(String tableName, IndexEntity indexEntity) {
        StringBuilder sb = new StringBuilder();

        sb.append("ALTER TABLE {0} ADD ");
       
        String indexSql = toIndexSql(tableName, indexEntity);

        if (StringUtils.isBlank(indexSql)) {
            return null;
        }
        sb.append(indexSql);

        String pattern = sb.toString();
        Object[] arguments = { toSqlName(tableName) };

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
    }

    public String toUpdateIndexSql(String tableName, String oldIndexName, IndexEntity indexEntity) {
        StringBuilder sb = new StringBuilder();

        sb.append("ALTER TABLE {0} DROP INDEX {1}");

        String indexSql = toIndexSql(tableName, indexEntity);
        if (!StringUtils.isBlank(indexSql)) {
            sb.append(", ADD ");
            sb.append(indexSql);
        }

        String pattern = sb.toString();
        Object[] arguments = { toSqlName(tableName), toSqlName(oldIndexName) };

        String sql = MessageFormat.format(pattern, arguments);

        return sql;
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
	
	public String toDropTableSql(String tableName) {
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE IF EXISTS {0}");

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
	
	public Map<String, Object> create(String tableName, Map<String, Object> map, String[] keyColumnNames) {
		log.info("CrudAbstractRepository->create {}", tableName);
		
		KeyHolder keyHolder = insert(tableName, map, keyColumnNames);
		
		Map<String, Object> key = keyHolder.getKeys();
		if (key == null) {
			key = new HashMap<String, Object>();
			for (String keyColumnName : keyColumnNames) {
				key.put(keyColumnName, map.get(keyColumnName));
			}
		}
		
		return key;
	}
	
	public Long create(String tableName, Object obj) {
		log.info("CrudAbstractRepository->create {}", tableName);
		
		Map<String, Object> map = convertObjectToMap(obj);

		return this.create(tableName, map);
	}
	
	public Map<String, Object> create(String tableName, Object obj, String[] keyColumnNames) {
		log.info("CrudAbstractRepository->create {}", tableName);
		
		Map<String, Object> map = convertObjectToMap(obj);

		return this.create(tableName, map, keyColumnNames);
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
		LeafCondition condition = new LeafCondition();
		condition.setColumnName("table_name");
		condition.setValue(tableName);
		condition.setOperatorType(OperatorTypeEnum.EQ);
		
		Long count = this.count("information_schema.TABLES", null, condition);
		
        return count > 0;
	}
	
	public void dropTable(String tableName) {
		String sql = toDropTableSql(tableName); 
		log.info(sql);
		execute(sql);
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
		return namedParameterJdbcTemplate.getJdbcTemplate().queryForList("SHOW TABLE STATUS");
	}
	
	public Map<String, Object> getMetaData(String tableName) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String sql = "SHOW TABLE STATUS LIKE '" + tableName + "'";
		map = namedParameterJdbcTemplate.getJdbcTemplate().queryForMap(sql);
	
		sql = "SHOW FULL COLUMNS FROM " + getSqlQuotation() + tableName + getSqlQuotation();
		List<Map<String, Object>> descLsit = namedParameterJdbcTemplate.getJdbcTemplate().queryForList(sql);
		map.put("columns", descLsit);
		
		sql = "SHOW INDEX FROM " + getSqlQuotation() + tableName +  getSqlQuotation();
		List<Map<String, Object>> indexLsit = namedParameterJdbcTemplate.getJdbcTemplate().queryForList(sql);
		map.put("indexs", indexLsit);
		
		return map;
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
			@Override
			public Object getValue(String paramName) throws IllegalArgumentException {
				return paramMap.get(paramName);
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
            	newMap.put(key, value);
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
		            	log.info(key);
		            	continue;
		            }
		            
		            if (key.equals("reverse")) { 
		            	log.info(key);
		            	continue;
		            }
		            
		            if (type.equals("List")) { 
		            	log.info(type);
		            	continue;
		            }
		            
		            if (type.indexOf("ColumnEntity") >= 0) { 
		            	log.info(type);
		            	continue;
		            }
		            
		            if (type.indexOf("TableEntity") >= 0) { 
		            	log.info(type);
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
		            
		            if (key.equals("reverse")) { 
		            	log.info(key);
		            	continue;
		            }
		            
		            if (type.equals("List")) { 
		            	log.info(type);
		            	continue;
		            }
		            
		            if (type.indexOf("ColumnEntity") >= 0) { 
		            	log.info(type);
		            	continue;
		            }
		            
		            if (type.indexOf("TableEntity") >= 0) { 
		            	log.info(type);
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
		
		String sql = toInserSql(tableName, columnNameList);
		
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
			columnNameList.add(key);
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
			columnNameList.add(key);
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
