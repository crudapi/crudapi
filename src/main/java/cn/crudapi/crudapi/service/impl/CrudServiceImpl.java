package cn.crudapi.crudapi.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.crudapi.crudapi.config.execute.CrudTemplate;
import cn.crudapi.crudapi.service.CrudService;

@Service
public class CrudServiceImpl implements CrudService {
	private static final Logger log = LoggerFactory.getLogger(CrudServiceImpl.class);
	
	@Autowired
	private CrudTemplate crudTemplate;
	
	@Override
	public String getSqlQuotation() {
		log.debug("CrudServiceImpl->getSqlQuotation");
		return crudTemplate.getSqlQuotation();
	}
	
	@Override
	public String getDateBaseName() {
		log.debug("CrudServiceImpl->getDateBaseName");
		return crudTemplate.getDateBaseName();
	}
	
	@Override
	public JdbcTemplate getJdbcTemplate() {
		log.debug("CrudServiceImpl->getJdbcTemplate");
		return crudTemplate.getJdbcTemplate();
	}
	
	@Override
	public void execute(String sql) { 
		log.debug("CrudServiceImpl->execute");
		crudTemplate.execute(sql);
	}
	
	@Override
	public List<Map<String, Object>> queryForList(String sql, Map<String, ?> paramMap) {
		log.debug("CrudServiceImpl->queryForList");
		return crudTemplate.queryForList(sql, paramMap);
	}
		
	@Override
	public List<Map<String, Object>> getMetaDatas() {
		log.debug("CrudServiceImpl->getMetaDatas");
		return crudTemplate.getMetaDatas();
	}
	
	@Override
	public Map<String, Object> getMetaData(String tableName) {
		log.debug("CrudServiceImpl->getMetaData");
		return crudTemplate.getMetaData(tableName);
	}

	@Override
	public String processTemplateToString(String templateName, String key, Object value) {
		return crudTemplate.processTemplateToString(templateName, key, value);
	}
	
	@Override
	public String processTemplateToString(String templateName, Object dataModel) {
		return crudTemplate.processTemplateToString(templateName, dataModel);
	}
	
	@Override
	public String processTemplateToString(String templateBase, String templateName, String key, Object value) {
		return crudTemplate.processTemplateToString(templateBase,  templateName, key, value);
	}
	
	@Override
	public String processTemplateToString(String templateBase, String templateName, Map<String, Object> map) {
		return crudTemplate.processTemplateToString(templateBase,templateName, map);
	}
}
