package cn.crudapi.crudapi.service.business.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.crudapi.crudapi.service.CrudService;
import cn.crudapi.crudapi.service.business.BusinessService;
import cn.crudapi.crudapi.service.system.NamingService;

@Service
public class BusinessServiceImpl implements BusinessService {
	@Autowired
	private CrudService crudService;
	
	@Autowired
	private NamingService namingService;
	
	@Override
	public List<Map<String, Object>> list(String resourceName) {
		String tableName = namingService.getTableName(resourceName);
		
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		List<Map<String, Object>> mapList = crudService.queryForList("SELECT * FROM `" + tableName + "`", paramsMap);
		
		return namingService.convert(mapList);
    }
}
