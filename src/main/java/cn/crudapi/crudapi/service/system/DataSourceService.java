package cn.crudapi.crudapi.service.system;

import java.util.List;
import java.util.Map;

import cn.crudapi.crudapi.config.datasource.DynamicDataSourceProperties;

public interface DataSourceService {
	List<Map<String, Object>> list();

	DynamicDataSourceProperties getDynamicDataSourcePropertiesByName(String name);
}
