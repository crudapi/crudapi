package cn.crudapi.crudapi.config.execute;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import cn.crudapi.crudapi.model.Table;


public class CrudTemplate {
	private static final Logger log = LoggerFactory.getLogger(CrudTemplate.class);
	
	private volatile DynamicCrudSqlExecute dynamicCrudSqlExecute;

	public CrudTemplate() {
		if (log.isDebugEnabled()) { 
			log.debug("CrudTemplate->Constructor"); 
		}
	}
	
	public CrudTemplate(DynamicCrudSqlExecute dynamicCrudSqlExecute) {
		if (log.isDebugEnabled()) { 
			log.debug("CrudTemplate->Constructor dynamicCrudSqlExecute"); 
		}
		
		this.dynamicCrudSqlExecute = dynamicCrudSqlExecute;
	}


	public String getSqlQuotation() { 
		if (log.isDebugEnabled()) { 
			log.debug("CrudTemplate->getSqlQuotation"); 
		}
		
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().getSqlQuotation();
	}
	
	public String getDataBaseType() {
		if (log.isDebugEnabled()) { 
			log.debug("CrudTemplate->getDataBaseType");
		}
		
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().getDataBaseType();
	}
	
	public JdbcTemplate getJdbcTemplate() {
		if (log.isDebugEnabled()) { 
			log.debug("CrudTemplate->getJdbcTemplate"); 
		}
		
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().getJdbcTemplate();
	}
	
	public void execute(String sql) { 
		if (log.isDebugEnabled()) { 
			log.debug("CrudTemplate->execute"); 
		}
		
		dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().execute(sql);
	}
	
	public List<Map<String, Object>> queryForList(String sql, Map<String, ?> paramMap) {
		if (log.isDebugEnabled()) { 
			log.debug("CrudTemplate->queryForList"); 
		}
		
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().queryForList(sql, paramMap);
	}
	
	public List<Map<String, Object>> getMetadatas() {
		if (log.isDebugEnabled()) { 
			log.debug("CrudTemplate->getMetadatas");
		}
		
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().getMetadatas();
	}
	
	public Table getMetadata(String tableName) {
		if (log.isDebugEnabled()) { 
			log.debug("CrudTemplate->getMetadata");
		}
		
		return dynamicCrudSqlExecute.determineTargetDataSource().getCrudFactory().getMetadata(tableName);
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
