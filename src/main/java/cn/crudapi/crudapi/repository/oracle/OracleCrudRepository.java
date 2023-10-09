package cn.crudapi.crudapi.repository.oracle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import cn.crudapi.crudapi.constant.ApiErrorCode;
import cn.crudapi.crudapi.exception.BusinessException;
import cn.crudapi.crudapi.repository.CrudAbstractRepository;

@Component
public class OracleCrudRepository extends CrudAbstractRepository {
	private static final Logger log = LoggerFactory.getLogger(OracleCrudRepository.class);

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
	
	@Override
	public List<Map<String, Object>> getMetadatas() {
		String sql = processTemplateToString("select-tables.sql.ftl", "tableSchema", getSchema());
		List<Map<String, Object>> tableList = getJdbcTemplate().queryForList(sql);
		
		return tableList;
	}
	
	@Override
	public Map<String, Object> getMetadata(String tableName) {
		log.info("getMetadata: " + tableName);
		
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
}
