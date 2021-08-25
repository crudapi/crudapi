package cn.crudapi.service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import cn.crudapi.core.service.FileService;

@Configuration
@EnableScheduling // 启用定时任务
public class SchedulingConfig {
	private static final Logger log = LoggerFactory.getLogger(SchedulingConfig.class);
	
	@Autowired
	private FileService fileService;

	@Scheduled(cron = "${job.file.cron}") // 每隔50秒执行一次
	public void fileServiceDoClean() {
		try {
			fileService.clean();
		} catch (Exception e) {
			log.error(e.getMessage());
			System.err.println(e);
		}
	}
}