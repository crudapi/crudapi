package cn.crudapi.crudapi.service.system.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.crudapi.crudapi.config.datasource.DynamicDataSourceProperties;
import cn.crudapi.crudapi.config.datasource.DynamicDataSourceProvider;
import cn.crudapi.crudapi.service.system.DataSourceService;

@Service
public class DataSourceServiceImpl implements DataSourceService {
	private static final Logger log = LoggerFactory.getLogger(ConfigServiceImpl.class);

	@Autowired
	private DynamicDataSourceProvider dynamicDataSourceProvider;

	@Override
	public List<Map<String, DynamicDataSourceProperties>> list() {
		List<Map<String, DynamicDataSourceProperties>> dynamicDataSourcePropertiesList = dynamicDataSourceProvider.queryDynamicDataSourcePropertiesList();
		
		return dynamicDataSourcePropertiesList;
	}

	@Override
	public DynamicDataSourceProperties getDynamicDataSourcePropertiesByName(String name) {
		DynamicDataSourceProperties dynamicDataSourceProperties = dynamicDataSourceProvider.queryDynamicDataSourceProperties();
		if (dynamicDataSourceProperties.getName().equals(name)) {
			return dynamicDataSourceProperties;
		}
		
		List<Map<String, DynamicDataSourceProperties>> dynamicDataSourcePropertiesList = dynamicDataSourceProvider.queryDynamicDataSourcePropertiesList();
		
		for (Map<String, DynamicDataSourceProperties> t : dynamicDataSourcePropertiesList) {
			if (t.containsKey(name)) {
				DynamicDataSourceProperties d = t.get(name);
				return d;
			}
		}

		return null;
	}
}
