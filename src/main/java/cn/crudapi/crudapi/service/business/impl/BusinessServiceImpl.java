package cn.crudapi.crudapi.service.business.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.crudapi.crudapi.service.CrudService;
import cn.crudapi.crudapi.service.business.BusinessService;

@Service
public class BusinessServiceImpl implements BusinessService {
	@Autowired
	private CrudService crudService;
	
	@Override
	public List<Map<String, Object>> list(String tableName) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		List<Map<String, Object>> mapList = crudService.queryForList("SELECT * FROM `" + tableName + "`", paramsMap);
		return mapList;
    }
}
