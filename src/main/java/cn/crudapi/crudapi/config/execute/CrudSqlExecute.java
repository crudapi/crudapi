package cn.crudapi.crudapi.config.execute;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;

import cn.crudapi.crudapi.repository.CrudAbstractFactory;

public class CrudSqlExecute {
	private static final Logger log = LoggerFactory.getLogger(CrudSqlExecute.class);
	
	@Nullable
	private volatile CrudAbstractFactory crudFactory;

	public CrudSqlExecute() {
		log.debug("CrudSqlExecute->Constructor");
	}
	
	public CrudSqlExecute(CrudAbstractFactory crudFactory) {
		log.debug("CrudSqlExecute->Constructor crudFactory");
		this.crudFactory = crudFactory;
	}
	
	public CrudAbstractFactory getCrudFactory() {
		return crudFactory;
	}

	public String getSqlQuotation() { 
		log.debug("CrudSqlExecute->getSqlQuotation");
		return crudFactory.getSqlQuotation();
	}
	
	public String getDateBaseName() { 
		log.debug("CrudSqlExecute->getDateBaseName");
		return crudFactory.getDateBaseName();
	}
	
	public JdbcTemplate getJdbcTemplate() {
		log.debug("CrudSqlExecute->getJdbcTemplate");
		return crudFactory.getJdbcTemplate();
	}
	
	public void execute(String sql) { 
		log.debug("CrudSqlExecute->execute");
		crudFactory.execute(sql);
	}
	
	public List<Map<String, Object>> getMetaDatas() {
		log.debug("CrudSqlExecute->getMetaDatas");
		return crudFactory.getMetaDatas();
	}
	
	public Map<String, Object> getMetaData(String tableName) {
		log.debug("CrudSqlExecute->getMetaData");
		return crudFactory.getMetaData(tableName);
	}
	
	public String processTemplateToString(String templateName, String key, Object value) {
		return crudFactory.processTemplateToString(templateName, key, value);
	}
	
	public String processTemplateToString(String templateName, Object dataModel) {
		return crudFactory.processTemplateToString(templateName, dataModel);
	}
	
	public String processTemplateToString(String templateBase, String templateName, String key, Object value) {
		return crudFactory.processTemplateToString(templateBase,  templateName, key, value);
	}
	
	public String processTemplateToString(String templateBase, String templateName, Map<String, Object> map) {
		return crudFactory.processTemplateToString(templateBase,templateName, map);
	}
}
