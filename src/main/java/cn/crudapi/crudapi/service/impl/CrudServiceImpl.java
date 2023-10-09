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
		if (log.isDebugEnabled()) { 
			log.debug("CrudServiceImpl->getSqlQuotation"); 
		}
		
		return crudTemplate.getSqlQuotation();
	}
	
	@Override
	public String getDataBaseType() {
		if (log.isDebugEnabled()) { 
			log.debug("CrudServiceImpl->getDataBaseType");
		}
		
		return crudTemplate.getDataBaseType();
	}
	
	@Override
	public JdbcTemplate getJdbcTemplate() {
		if (log.isDebugEnabled()) { 
			log.debug("CrudServiceImpl->getJdbcTemplate"); 
		}
		
		return crudTemplate.getJdbcTemplate();
	}
	
	@Override
	public void execute(String sql) { 
		if (log.isDebugEnabled()) { 
			log.debug("CrudServiceImpl->execute"); 
		}
		
		crudTemplate.execute(sql);
	}
	
	@Override
	public List<Map<String, Object>> queryForList(String sql, Map<String, ?> paramMap) {
		if (log.isDebugEnabled()) { 
			log.debug("CrudServiceImpl->queryForList"); 
		}
		
		List<Map<String, Object>> mapList =  crudTemplate.queryForList(sql, paramMap);
		
		return mapList;
	}
		
	@Override
	public List<Map<String, Object>> getMetadatas() {
		if (log.isDebugEnabled()) { 
			log.debug("CrudServiceImpl->getMetadatas");
		}
		
		return crudTemplate.getMetadatas();
	}
	
	@Override
	public Map<String, Object> getMetadata(String tableName) {
		if (log.isDebugEnabled()) { 
			log.debug("CrudServiceImpl->getMetadata");
		}
		
		return crudTemplate.getMetadata(tableName);
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
