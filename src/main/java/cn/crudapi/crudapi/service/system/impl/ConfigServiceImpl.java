package cn.crudapi.crudapi.service.system.impl;


import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import cn.crudapi.crudapi.property.SystemConfigProperties;
import cn.crudapi.crudapi.service.system.ConfigService;

@EnableConfigurationProperties({ SystemConfigProperties.class })
@Service
public class ConfigServiceImpl implements ConfigService {
	private static final Logger log = LoggerFactory.getLogger(ConfigServiceImpl.class);

	@Resource
	private SystemConfigProperties systemConfigProperties;

	@Override
	public SystemConfigProperties getDefault() {
		log.info("ConfigServiceImpl->getDefault");
		return systemConfigProperties;
	}
}
