package cn.crudapi.crudapi.repository;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class CrudAbstractFactory {
	private static final Logger log = LoggerFactory.getLogger(CrudAbstractFactory.class);
	
	public abstract CrudAbstractRepository getCrudRepository();
	
    public String getSqlQuotation() { 
		log.debug("CrudAbstractFactory->getSqlQuotation");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.getSqlQuotation();
	}
	
	public String getDateBaseName() {
		log.debug("CrudAbstractFactory->getDateBaseName");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.getDateBaseName();
	}
	
	
	public JdbcTemplate getJdbcTemplate() {
		log.debug("CrudAbstractFactory->getJdbcTemplate");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.getJdbcTemplate();
	}
	
	public void execute(String sql) { 
		log.debug("CrudAbstractFactory->execute");
		CrudAbstractRepository repository = this.getCrudRepository();
		repository.execute(sql);
	}
	
	
	public List<Map<String, Object>> queryForList(String sql, Map<String, ?> paramMap) {
		log.debug("CrudAbstractFactory->queryForList");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.queryForList(sql, paramMap);
	}
	
	public List<Map<String, Object>> getMetaDatas() {
		log.debug("CrudAbstractFactory->getMetaDatas");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.getMetaDatas();
	}
	
	public Map<String, Object> getMetaData(String tableName) {
		log.debug("CrudAbstractFactory->getMetaData");
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.getMetaData(tableName);
	}
	
	public String processTemplateToString(String templateName, String key, Object value) {
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.processTemplateToString(templateName, key, value);
	}
	
	public String processTemplateToString(String templateName, Object dataModel) {
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.processTemplateToString(templateName, dataModel);
	}
	
	public String processTemplateToString(String templateBase, String templateName, String key, Object value) {
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.processTemplateToString(templateBase,  templateName, key, value);
	}
	
	public String processTemplateToString(String templateBase, String templateName, Map<String, Object> map) {
		CrudAbstractRepository repository = this.getCrudRepository();
		return repository.processTemplateToString(templateBase,templateName, map);
	}
}
