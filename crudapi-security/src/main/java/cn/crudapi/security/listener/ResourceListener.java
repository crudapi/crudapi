package cn.crudapi.security.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import cn.crudapi.core.event.BusinessEvent;
import cn.crudapi.security.component.DynamicSecurityMetadataSource;

@Component
public class ResourceListener implements ApplicationListener<BusinessEvent> {
	private static final Logger log = LoggerFactory.getLogger(ResourceListener.class);
	
	@Autowired
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

	@Override
	@Async
	public void onApplicationEvent(BusinessEvent event) {
		log.info("ResourceListener tableName = " + event.getTableName());
		
		if ("resource".equalsIgnoreCase(event.getTableName())) {
			dynamicSecurityMetadataSource.clearDataSource();
		}
	}
}
