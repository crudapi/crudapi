package cn.crudapi.core.repository.oracle;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import cn.crudapi.core.enumeration.OperatorTypeEnum;
import cn.crudapi.core.query.CompositeCondition;
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
	
	public Map<String, Object> create(String tableName, Map<String, Object> map, String[] keyColumnNames) {
		log.info("OracleCrudRepository->create {}", tableName);
		
		KeyHolder keyHolder = insert(tableName, map, null);
		
		Map<String, Object> key = keyHolder.getKeys();
		if (key == null || key.get(COLUMN_ID) == null) {
			key = new HashMap<String, Object>();
			for (String keyColumnName : keyColumnNames) {
				key.put(keyColumnName, map.get(keyColumnName));
			}
		}
		
		return key;
	}
}
