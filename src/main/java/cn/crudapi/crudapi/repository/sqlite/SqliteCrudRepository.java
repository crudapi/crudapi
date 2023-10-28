package cn.crudapi.crudapi.repository.sqlite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.crudapi.crudapi.model.Table;
import cn.crudapi.crudapi.repository.CrudAbstractRepository;

@Component
public class SqliteCrudRepository extends CrudAbstractRepository {
	private static final Logger log = LoggerFactory.getLogger(SqliteCrudRepository.class);

	@Override
	public String getSqlQuotation() {
		return "`";
	}
	
	@Override
	public String getLimitOffsetSql() {
		return ""; //LIMIT :limit OFFSET :offset
	}
	
	@Override
	public List<Map<String, Object>> getMetadatas() {
		List<Map<String, Object>> tableList = getJdbcTemplate().queryForList("SELECT * FROM `SQLITE_MASTER` WHERE `TYPE` = 'table' AND `name` != 'sqlite_sequence'");
		
		List<Map<String, Object>> newMapList =  new ArrayList<Map<String, Object>>();
		for (Map<String, Object> t : tableList) {
			Map<String, Object> newMap = new HashMap<String, Object>();
			newMap.put("tableSchema", this.getSchema());
			newMap.put("tableName", t.get("name"));
			newMap.put("comment", t.get("sql"));
			log.info(t.get("sql").toString());
			newMapList.add(newMap);
		}
		return newMapList;
	}
	
	@Override
	public Table getMetadata(String tableName) {
		log.info("getMetadata: " + tableName);
		
		return null;
	}
}
