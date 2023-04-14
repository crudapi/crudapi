package cn.crudapi.crudapi.service.system.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.crudapi.crudapi.service.CrudService;
import cn.crudapi.crudapi.service.system.ConfigService;

@Service
public class ConfigServiceImpl implements ConfigService {
	@Autowired
	private CrudService crudService;

	@Override
	public Map<String, Object> getDefault() {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("is_default", true);
		paramsMap.put("status", "ACTIVE");
		paramsMap.put("is_deleted", false);
		List<Map<String, Object>> mapList = crudService.queryForList("SELECT * FROM `ca_system_config`", paramsMap);
	
		return mapList.get(0);
	}
}
