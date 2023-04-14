package cn.crudapi.crudapi.controller.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.crudapi.crudapi.config.datasource.DataSourceContextHolder;
import cn.crudapi.crudapi.service.CrudService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags ="业务数据-增删改查")
@RestController
@RequestMapping("/api/crudapi/business")
public class CrudController {
	private static final Logger log = LoggerFactory.getLogger(CrudController.class);
	
	@Autowired
	private CrudService crudService;
	
	@ApiOperation("获取业务数据列表")
	@GetMapping(value = "/{tableName}")
    public List<Map<String, Object>> list(@PathVariable("tableName") String tableName) {
		log.info("CrudController->list dataSource = " + DataSourceContextHolder.getDataSource());
		
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		List<Map<String, Object>> mapList = crudService.queryForList("SELECT * FROM `" + tableName + "`", paramsMap);
	
        return mapList;
    }
}
