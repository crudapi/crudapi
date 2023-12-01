package cn.crudapi.crudapi.repository.mssql;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.crudapi.crudapi.repository.CrudAbstractRepository;

@Component
public class MsSqlCrudRepository extends CrudAbstractRepository {
	private static final Logger log = LoggerFactory.getLogger(MsSqlCrudRepository.class);
	
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
	public List<Map<String, Object>> getMetadatas() {
		String sql = processTemplateToString("select-table.sql.ftl", "tableSchema", getSchema());
		List<Map<String, Object>> tableList = getJdbcTemplate().queryForList(sql);
		
		return tableList;
	}
}
