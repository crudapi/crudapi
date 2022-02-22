package cn.crudapi.weixin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import cn.crudapi.weixin.service.WeixinService;


@Configuration
@EnableScheduling // 启用定时任务
public class WeixinSchedulingConfig {
	private static final Logger log = LoggerFactory.getLogger(WeixinSchedulingConfig.class);
	
	@Autowired
	private WeixinService weixinService;

	//@Scheduled(cron = "${job.weixin.cron}") // 每隔50秒执行一次
	public void weixinSync() {
		try {
			//log.info("任务扫描定时任务：开始……");
			if (weixinService.isExpires()) {
				weixinService.getWeixinAccessToken();
			}
			//log.info("任务扫描定时任务：结束。");
		} catch (Exception e) {
			log.error(e.getMessage());
			System.err.println(e);
		}
	}
}