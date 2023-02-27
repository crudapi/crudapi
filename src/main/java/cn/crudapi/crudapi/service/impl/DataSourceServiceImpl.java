package cn.crudapi.crudapi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.crudapi.crudapi.service.CrudService;
import cn.crudapi.crudapi.service.DataSourceService;

@Service
public class DataSourceServiceImpl implements DataSourceService {
	@Autowired
	private CrudService crudService;
	
	@Override
	public List<Map<String, Object>> queryForList() {
        return crudService.queryForList("SELECT * FROM `DATA_SOURCE`", new HashMap<String, Object>());
    }
	
	@Override
	public List<Map<String, Object>> getMetaDatas() {
        return crudService.getMetaDatas();
    }
}
