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
	
	public void repairMeataData(String tableName, List<String> columnNameLsit) {
		for (String columnName : columnNameLsit) {
			String sql = "ALTER TABLE `" + tableName + "`";
			if (columnName.equals("id")) {
				sql += " ADD `id` BIGINT NOT NULL AUTO_INCREMENT, ADD PRIMARY KEY (`id`);";
			} else if (columnName.equals("name")) {
				sql += " ADD `name` VARCHAR(200) NOT NULL;";
			} else if (columnName.equals("fullTextBody")) {
				sql += " ADD `fullTextBody` TEXT NULL, ADD FULLTEXT `ft_fulltext_body` (`fullTextBody`);";
			} else if (columnName.equals("createdDate")) {
				sql += " ADD `createdDate` DATETIME NOT NULL;";
			} else if (columnName.equals("lastModifiedDate")) {
				sql += " ADD `lastModifiedDate` DATETIME NULL DEFAULT NULL;";
			}
			
			log.info(sql);
			
			getJdbcTemplate().execute(sql);
		}
	}
}
