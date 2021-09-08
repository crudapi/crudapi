package cn.crudapi.core.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.crudapi.core.entity.TableEntity;

@Component
public class TableMetadataRepository extends CaMetadataRepository<TableEntity> {
	private static final Logger log = LoggerFactory.getLogger(TableMetadataRepository.class);
	 
	public List<Map<String, Object>> getMetaDatas() {
		return getJdbcTemplate().queryForList("SHOW TABLE STATUS");
	}
	
	public Map<String, Object> getMetaData(String tableName) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		String sql = "SHOW TABLE STATUS LIKE '" + tableName + "'";
		map = getJdbcTemplate().queryForMap(sql);
	
		sql = "SHOW FULL COLUMNS FROM `" + tableName + "`";
		List<Map<String, Object>> descLsit = getJdbcTemplate().queryForList(sql);
		map.put("columns", descLsit);
		
		sql = "SHOW INDEX FROM `" + tableName + "`";
		List<Map<String, Object>> indexLsit = getJdbcTemplate().queryForList(sql);
		map.put("indexs", indexLsit);
		
		return map;
	}
}
