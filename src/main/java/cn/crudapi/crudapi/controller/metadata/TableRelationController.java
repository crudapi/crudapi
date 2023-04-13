package cn.crudapi.crudapi.controller.metadata;

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

@Api(tags ="元数据-表关系")
@RestController
@RequestMapping("/api/crudapi/metadata/table-relations")
public class TableRelationController {
	private static final Logger log = LoggerFactory.getLogger(TableRelationController.class);
	
	@Autowired
	private DataSourceService dataSourceService;
	
	@ApiOperation("获取表关系列表")
	@GetMapping()
    public List<Map<String, Object>> list() {
		log.info("TableRelationController->list dataSource = " + DataSourceContextHolder.getDataSource());
		
		List<Map<String, Object>> mapList = dataSourceService.getMetaDatas();
        
        return mapList;
    }
}
