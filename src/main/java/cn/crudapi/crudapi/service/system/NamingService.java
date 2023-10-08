package cn.crudapi.crudapi.service.system;

import java.util.List;
import java.util.Map;

public interface NamingService {
	String getTableName(String resourceName);

	String getTableName(String tableType, String resourceName);

	List<Map<String, Object>> convert(List<Map<String, Object>> mapList);

	List<Map<String, Object>> convert(String tableType, List<Map<String, Object>> mapList);
}
