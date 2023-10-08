package cn.crudapi.crudapi.repository.postsql;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.crudapi.crudapi.repository.CrudAbstractRepository;

@Component
public class PostSqlCrudRepository extends CrudAbstractRepository {
	private static final Logger log = LoggerFactory.getLogger(PostSqlCrudRepository.class);


	public String getSchema() {
		return "public";
	}
	
	@Override
	public String getSqlQuotation() {
		return "\"";
	}
	
	@Override
	public String getLimitOffsetSql() {
		return "LIMIT :limit OFFSET :offset";
	}
	
	@Override
	public List<Map<String, Object>> getMetaDatas() {
		log.info("getMetaDatas");
		
		String sql = processTemplateToString("select-table.sql.ftl", "tableSchema", getSchema());
		List<Map<String, Object>> tableList = getJdbcTemplate().queryForList(sql);
		
		return tableList;
	}
}
