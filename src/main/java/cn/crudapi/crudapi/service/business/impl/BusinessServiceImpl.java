package cn.crudapi.crudapi.service.business.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.crudapi.crudapi.config.datasource.DataSourceContextHolder;
import cn.crudapi.crudapi.config.datasource.DynamicDataSourceProperties;
import cn.crudapi.crudapi.service.CrudService;
import cn.crudapi.crudapi.service.business.BusinessService;
import cn.crudapi.crudapi.service.system.ConfigService;
import cn.crudapi.crudapi.service.system.DataSourceService;
import cn.crudapi.crudapi.util.CrudapiUtils;

@Service
public class BusinessServiceImpl implements BusinessService {
	@Autowired
	private CrudService crudService;
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private DataSourceService dataSourceService;
	
	
	@Override
	public List<Map<String, Object>> list(String resourceName) {
		String tableName = this.getTableName(resourceName);
		
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		List<Map<String, Object>> mapList = crudService.queryForList("SELECT * FROM `" + tableName + "`", paramsMap);
		return mapList;
    }
	
	public String getTableName(String dataSourceName, String resourceName) {
		DynamicDataSourceProperties dynamicDataSourceProperties = dataSourceService.getDynamicDataSourcePropertiesByName(dataSourceName);
		
		Map<String, Object> defaultConfig = configService.getDefault();
		String apiResourceNaming = defaultConfig.get("apiResourceNaming").toString();
		String apiParamNaming = defaultConfig.get("apiParamNaming").toString();
		String objectNaming = defaultConfig.get("objectNaming").toString();
		
		String businessTablePrefix = dynamicDataSourceProperties.getBusinessTablePrefix();
		String businessDatabaseNaming = dynamicDataSourceProperties.getBusinessDatabaseNaming();
		
		String tableName = CrudapiUtils.convert(resourceName, apiResourceNaming, businessDatabaseNaming);
		
		return tableName;
    }
	
	public String getTableName(String resourceName) {
		return this.getTableName(DataSourceContextHolder.getDataSource(), resourceName);
    }
}
