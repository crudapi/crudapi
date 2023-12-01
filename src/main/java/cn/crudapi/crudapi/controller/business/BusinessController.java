package cn.crudapi.crudapi.controller.business;

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
import cn.crudapi.crudapi.service.business.BusinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags ="业务数据-增删改查")
@RestController
@RequestMapping("/api/crudapi/business")
public class BusinessController {
	private static final Logger log = LoggerFactory.getLogger(BusinessController.class);
	
	@Autowired
	private BusinessService businessService;
	
	@ApiOperation("获取业务数据列表")
	@GetMapping(value = "/{resourceName}")
    public List<Map<String, Object>> list(@PathVariable("resourceName") String resourceName) {
		log.info("BusinessController->list dataSource = " + DataSourceContextHolder.getDataSource());
		
		List<Map<String, Object>> mapList = businessService.list(resourceName);
	
        return mapList;
    }
}
