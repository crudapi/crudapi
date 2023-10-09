package cn.crudapi.crudapi.service.metadata.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.crudapi.crudapi.service.CrudService;
import cn.crudapi.crudapi.service.metadata.TableService;

@Service
public class TableServiceImpl implements TableService {
	@Autowired
	private CrudService crudService;
	
	@Override
	public List<Map<String, Object>> list() {
		 return crudService.getMetadatas();
    }

	@Override
	public Map<String, Object> get(String tableName) {
		 return crudService.getMetadata(tableName);
    }
}
