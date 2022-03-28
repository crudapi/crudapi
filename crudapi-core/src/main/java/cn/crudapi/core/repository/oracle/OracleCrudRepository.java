package cn.crudapi.core.repository.oracle;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import cn.crudapi.core.constant.ApiErrorCode;
import cn.crudapi.core.entity.ColumnEntity;
import cn.crudapi.core.entity.TableEntity;
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
