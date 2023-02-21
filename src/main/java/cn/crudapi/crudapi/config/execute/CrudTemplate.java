package cn.crudapi.crudapi.config.execute;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;


public class CrudTemplate {
	private static final Logger log = LoggerFactory.getLogger(CrudTemplate.class);
	
	private volatile DynamicCrudSqlExecute dynamicCrudSqlExecute;

	public CrudTemplate() {
		log.debug("CrudTemplate->Constructor");
	}
	
	public CrudTemplate(DynamicCrudSqlExecute dynamicCrudSqlExecute) {
		this.dynamicCrudSqlExecute = dynamicCrudSqlExecute;
		log.debug("CrudTemplate->Constructor dynamicCrudSqlExecute");
	}


	public String getSqlQuotation() { 
		log.debug("CrudTemplate->getSqlQuotation");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().getSqlQuotation();
	}
	
	public String getDateBaseName() { 
		log.debug("CrudTemplate->getDateBaseName");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().getDateBaseName();
	}
	
	public JdbcTemplate getJdbcTemplate() {
		log.debug("CrudTemplate->getJdbcTemplate");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().getJdbcTemplate();
	}
	
	public void execute(String sql) { 
		log.debug("CrudTemplate->execute");
		dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().execute(sql);
	}
	
	public List<Map<String, Object>> queryForList(String sql, Map<String, ?> paramMap) {
		log.debug("CrudTemplate->queryForList");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().queryForList(sql, paramMap);
	}
	
	public List<Map<String, Object>> getMetaDatas() {
		log.debug("CrudTemplate->getMetaDatas");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().getMetaDatas();
	}
	
	public Map<String, Object> getMetaData(String tableName) {
		log.debug("CrudTemplate->getMetaData");
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().getMetaData(tableName);
	}
	
	public String processTemplateToString(String templateName, String key, Object value) {
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().processTemplateToString(templateName, key, value);
	}
	
	public String processTemplateToString(String templateName, Object dataModel) {
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().processTemplateToString(templateName, dataModel);
	}
	
	public String processTemplateToString(String templateBase, String templateName, String key, Object value) {
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().processTemplateToString(templateBase,  templateName, key, value);
	}
	
	public String processTemplateToString(String templateBase, String templateName, Map<String, Object> map) {
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().processTemplateToString(templateBase,templateName, map);
	}
}
