package cn.crudapi.crudapi.service;

import java.util.List;
import java.util.Map;

public interface DataSourceService {
	List<Map<String, Object>> queryForList();

	List<Map<String, Object>> getMetaDatas();
}
