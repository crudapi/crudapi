package cn.crudapi.crudapi.controller.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.crudapi.crudapi.config.datasource.DataSourceContextHolder;
import cn.crudapi.crudapi.property.SystemConfigProperties;
import cn.crudapi.crudapi.service.system.ConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags ="系统-配置")
@RestController
@RequestMapping("/api/crudapi/system/configs")
public class ConfigController {
	private static final Logger log = LoggerFactory.getLogger(ConfigController.class);
	
	@Autowired
	private ConfigService configService;
	
	@ApiOperation("获取默认配置")
	@GetMapping("/default")
    public SystemConfigProperties getDefault() {
		log.info("ConfigController.getDefault dataSource = " + DataSourceContextHolder.getDataSource());
		
        return configService.getDefault();
    }
}
