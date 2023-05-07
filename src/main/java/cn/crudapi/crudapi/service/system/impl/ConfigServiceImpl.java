package cn.crudapi.crudapi.service.system.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.crudapi.crudapi.constant.SystemConsts;
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
		
		String sql = "SELECT * FROM `" + SystemConsts.TABLE_CONFIG +  "` WHERE is_default = :is_default AND status = :status AND is_deleted = :is_deleted";
		
		List<Map<String, Object>> mapList = crudService.queryForList(sql, paramsMap);
	
		return mapList.get(0);
	}
}
