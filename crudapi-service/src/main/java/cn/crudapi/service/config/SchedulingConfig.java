package cn.crudapi.service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import cn.crudapi.core.service.JobService;

@Configuration
@EnableScheduling // 启用定时任务
public class SchedulingConfig {
	private static final Logger log = LoggerFactory.getLogger(SchedulingConfig.class);
	
	@Autowired
	private JobService jobService;

	//@Scheduled(cron = "${job.file.cron}") // 定时清理无效文件
	public void fileServiceDoClean() {
		try {
			jobService.clean();
		} catch (Exception e) {
			log.error(e.getMessage());
			System.err.println(e);
		}
	}
}