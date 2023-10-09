package cn.crudapi.crudapi.service.metadata;

import java.util.List;
import java.util.Map;

public interface TableService {
	List<Map<String, Object>> list();

	Map<String, Object> get(String tableName);
}
