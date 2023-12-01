package cn.crudapi.crudapi.service.system.impl;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.crudapi.crudapi.config.datasource.DataSourceContextHolder;
import cn.crudapi.crudapi.config.datasource.DynamicDataSourceProperties;
import cn.crudapi.crudapi.constant.DataSourceConsts;
import cn.crudapi.crudapi.constant.Naming;
import cn.crudapi.crudapi.constant.TablePrefixConsts;
import cn.crudapi.crudapi.constant.TableTypeConsts;
import cn.crudapi.crudapi.property.SystemConfigProperties;
import cn.crudapi.crudapi.service.system.ConfigService;
import cn.crudapi.crudapi.service.system.DataSourceService;
import cn.crudapi.crudapi.service.system.NamingService;
import cn.crudapi.crudapi.util.CrudapiUtils;

@Service
public class NamingServiceImpl implements NamingService {
	private static final Logger log = LoggerFactory.getLogger(NamingServiceImpl.class);

	@Autowired
	private ConfigService configService;
	
	@Autowired
	private DataSourceService dataSourceService;
	
	@Override
	public List<Map<String, Object>> convert(List<Map<String, Object>> mapList) {
		return this.convert(TableTypeConsts.BUSINESS, mapList);
	}
	
	@Override
	public List<Map<String, Object>> convert(String tableType, List<Map<String, Object>> mapList) {
		String dataSourceName = DataSourceContextHolder.getDataSource();
		String fromNaming = null;
		String toNaming = null;
		
		SystemConfigProperties defaultConfig = configService.getDefault();
		String objectNaming = defaultConfig.getObjectNaming();
		toNaming = objectNaming;
		
		
		if (DataSourceConsts.DEFAULT.equals(dataSourceName)) {
			fromNaming = Naming.LOWER_UNDERSCORE;
		} else {
			DynamicDataSourceProperties dynamicDataSourceProperties = dataSourceService.getDynamicDataSourcePropertiesByName(dataSourceName);
			
			String metadataDatabaseNaming = dynamicDataSourceProperties.getMetadataDatabaseNaming();
			String businessDatabaseNaming = dynamicDataSourceProperties.getBusinessDatabaseNaming();
			
			if (TableTypeConsts.METADATA.equals(tableType)) {
				fromNaming = metadataDatabaseNaming;
			} else if (TableTypeConsts.BUSINESS.equals(tableType)) {
				fromNaming = businessDatabaseNaming;	
			}
		}
		
		
		return CrudapiUtils.convert(mapList, fromNaming, toNaming);
	}
		
	
	@Override
	public String getTableName(String resourceName) {
		return this.getTableName(TableTypeConsts.BUSINESS, resourceName);
	}
	
	@Override
	public String getTableName(String tableType, String resourceName) {
		return this.getTableName(DataSourceContextHolder.getDataSource(), tableType, resourceName);
    }
	
	private String getTableName(String dataSourceName, String tableType, String resourceName) {
		String fromNaming = null;
		String toNaming = null;
		
		SystemConfigProperties defaultConfig = configService.getDefault();
		String apiResourceNaming = defaultConfig.getApiResourceNaming();
		fromNaming = apiResourceNaming;
		
		String tablePrefix = "";
		
		if (DataSourceConsts.DEFAULT.equals(dataSourceName)) {
			toNaming = Naming.LOWER_UNDERSCORE;
			tablePrefix = TablePrefixConsts.CA_SYSTEM;
		} else {
			DynamicDataSourceProperties dynamicDataSourceProperties = dataSourceService.getDynamicDataSourcePropertiesByName(dataSourceName);
			
			String metadataTablePrefix = dynamicDataSourceProperties.getMetadataTablePrefix();
			String metadataDatabaseNaming = dynamicDataSourceProperties.getMetadataDatabaseNaming();
			String businessTablePrefix = dynamicDataSourceProperties.getBusinessTablePrefix();
			String businessDatabaseNaming = dynamicDataSourceProperties.getBusinessDatabaseNaming();
			
			if (TableTypeConsts.METADATA.equals(tableType)) {
				tablePrefix = metadataTablePrefix;
				toNaming = metadataDatabaseNaming;
			} else if (TableTypeConsts.BUSINESS.equals(tableType)) {
				tablePrefix = businessTablePrefix;
				toNaming = businessDatabaseNaming;	
			}
		}
		
			
		String tableName = CrudapiUtils.convert(resourceName, fromNaming, toNaming);
		
		if (!tableName.toLowerCase().contains(tablePrefix.toLowerCase())) {
			tableName = tablePrefix + tableName;
		}
		
		log.info("getTableName tableName = {}", tableName);
		
		return tableName;
    }
}
