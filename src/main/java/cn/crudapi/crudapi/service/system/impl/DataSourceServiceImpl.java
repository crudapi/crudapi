package cn.crudapi.crudapi.service.system.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.crudapi.crudapi.config.datasource.DynamicDataSourceProperties;
import cn.crudapi.crudapi.config.datasource.DynamicDataSourceProvider;
import cn.crudapi.crudapi.constant.SystemConsts;
import cn.crudapi.crudapi.service.CrudService;
import cn.crudapi.crudapi.service.system.DataSourceService;

@Service
public class DataSourceServiceImpl implements DataSourceService {
	@Autowired
	private CrudService crudService;
	
	@Autowired
	private DynamicDataSourceProvider dynamicDataSourceProvider;

	@Override
	public List<Map<String, Object>> list() {
        return crudService.queryForList("SELECT * FROM `" + SystemConsts.TABLE_DATA_SOURCE +  "`", new HashMap<String, Object>());
    }

	@Override
	public DynamicDataSourceProperties getDynamicDataSourcePropertiesByName(String name) {
		List<Map<String, DynamicDataSourceProperties>> dynamicDataSourcePropertiesList = dynamicDataSourceProvider.getDynamicDataSourcePropertiesList();

		for (Map<String, DynamicDataSourceProperties> t : dynamicDataSourcePropertiesList) {
			if (t.containsKey(name)) {
				DynamicDataSourceProperties d = t.get(name);
				return d;
			}

		}

		return null;
	}
}
