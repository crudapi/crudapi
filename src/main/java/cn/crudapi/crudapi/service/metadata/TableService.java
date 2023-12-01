package cn.crudapi.crudapi.service.metadata;

import java.util.List;
import java.util.Map;

import cn.crudapi.crudapi.model.Table;

public interface TableService {
	List<Map<String, Object>> list();

	Table get(String tableName);
}
