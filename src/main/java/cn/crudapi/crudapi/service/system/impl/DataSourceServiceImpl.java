package cn.crudapi.crudapi.service.system.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.crudapi.crudapi.service.CrudService;
import cn.crudapi.crudapi.service.system.DataSourceService;

@Service
public class DataSourceServiceImpl implements DataSourceService {
	@Autowired
	private CrudService crudService;
	
	@Override
	public List<Map<String, Object>> list() {
        return crudService.queryForList("SELECT * FROM `ca_system_data_source`", new HashMap<String, Object>());
    }
}
