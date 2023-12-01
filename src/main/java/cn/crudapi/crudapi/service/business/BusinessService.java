package cn.crudapi.crudapi.service.business;

import java.util.List;
import java.util.Map;

public interface BusinessService {
	List<Map<String, Object>> list(String resourceName);
}
