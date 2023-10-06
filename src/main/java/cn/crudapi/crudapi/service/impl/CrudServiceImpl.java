package cn.crudapi.crudapi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.crudapi.crudapi.config.datasource.DataSourceContextHolder;
import cn.crudapi.crudapi.config.datasource.DynamicDataSourceProperties;
import cn.crudapi.crudapi.config.execute.CrudTemplate;
import cn.crudapi.crudapi.property.SystemConfigProperties;
import cn.crudapi.crudapi.service.CrudService;
import cn.crudapi.crudapi.service.system.ConfigService;
import cn.crudapi.crudapi.service.system.DataSourceService;
import cn.crudapi.crudapi.util.CrudapiUtils;

@Service
public class CrudServiceImpl implements CrudService {
	private static final Logger log = LoggerFactory.getLogger(CrudServiceImpl.class);
	
	@Autowired
	private CrudTemplate crudTemplate;
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private DataSourceService dataSourceService;
	
	@Override
	public String getSqlQuotation() {
		if (log.isDebugEnabled()) { 
			log.debug("CrudServiceImpl->getSqlQuotation"); 
		}
		
		return crudTemplate.getSqlQuotation();
	}
	
	@Override
	public String getDateBaseName() {
		if (log.isDebugEnabled()) { 
			log.debug("CrudServiceImpl->getDateBaseName"); 
		}
		
		return crudTemplate.getDateBaseName();
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
		
		DynamicDataSourceProperties dynamicDataSourceProperties = dataSourceService.getDynamicDataSourcePropertiesByName(DataSourceContextHolder.getDataSource());
		
		String businessTablePrefix = dynamicDataSourceProperties.getBusinessTablePrefix();
		String businessDatabaseNaming = dynamicDataSourceProperties.getBusinessDatabaseNaming();
		String metadataTablePrefix = dynamicDataSourceProperties.getMetadataTablePrefix();
		String metadataDatabaseNaming = dynamicDataSourceProperties.getMetadataDatabaseNaming();
		
		SystemConfigProperties systemConfigProperties = configService.getDefault();
		String objectNaming = systemConfigProperties.getObjectNaming();
		
		List<Map<String, Object>> newMapList = new ArrayList<>();
		for (Map<String, Object> t : mapList) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			for(Map.Entry<String, Object> entry : t.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (key.toLowerCase().startsWith("is_")) {
					key = key.substring(3);
					
					if (value != null ) {
						String valueStr = value.toString();
						if (valueStr.equals("1") || valueStr.toLowerCase().equals("true")) {
							value = true;
		    			} else {
		    				value = false;
		    			}
					}
				}
				
				String newKey = CrudapiUtils.convert(key, businessDatabaseNaming, systemConfigProperties.getObjectNaming());
				
				map.put(newKey, value);
			}
			newMapList.add(map);
		}
		
		return newMapList;
	}
		
	@Override
	public List<Map<String, Object>> getMetaDatas() {
		if (log.isDebugEnabled()) { 
			log.debug("CrudServiceImpl->getMetaDatas"); 
		}
		
		return crudTemplate.getMetaDatas();
	}
	
	@Override
	public Map<String, Object> getMetaData(String tableName) {
		if (log.isDebugEnabled()) { 
			log.debug("CrudServiceImpl->getMetaData"); 
		}
		
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
