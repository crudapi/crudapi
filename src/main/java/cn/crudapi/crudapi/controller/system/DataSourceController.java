package cn.crudapi.crudapi.controller.system;

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
import io.swagger.annotations.ApiOperation;

@Api(tags ="配置")
@RestController
@RequestMapping("/api/crudapi/system/data-sources")
public class DataSourceController {
	private static final Logger log = LoggerFactory.getLogger(DataSourceController.class);
	
	@Autowired
	private DataSourceService dataSourceService;
	
	@ApiOperation("获取数据源列表")
	@GetMapping()
    public List<Map<String, Object>> list() {
		log.info("DataSourceController.list dataSource = " + DataSourceContextHolder.getDataSource());
		
        return dataSourceService.queryForList();
    }
}
