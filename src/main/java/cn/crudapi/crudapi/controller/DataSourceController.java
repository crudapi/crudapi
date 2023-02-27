package cn.crudapi.crudapi.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.crudapi.crudapi.config.datasource.DataSourceContextHolder;
import cn.crudapi.crudapi.service.DataSourceService;
import io.swagger.annotations.Api;

@Api(tags ="数据源")
@RestController
@RequestMapping("/api/crudapi")
public class DataSourceController {
	private static final Logger log = LoggerFactory.getLogger(DataSourceController.class);
	
	@Autowired
	private DataSourceService dataSourceService;
	
	@GetMapping(value="/dataSources")
    public List<Map<String, Object>> queryForList() {
        return dataSourceService.queryForList();
    }
	
	@GetMapping(value="/metaDatas")
    public List<Map<String, Object>> getMetaDatas() {
		log.info("DataSourceController->getMetaDatas dataSource = " + DataSourceContextHolder.getDataSource());
		
		List<Map<String, Object>> mapList = dataSourceService.getMetaDatas();
        
        return mapList;
    }
}
