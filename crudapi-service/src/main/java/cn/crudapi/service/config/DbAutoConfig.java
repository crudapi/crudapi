package cn.crudapi.service.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


@Configuration
public class DbAutoConfig implements InitializingBean {
	private static final Logger log = LoggerFactory.getLogger(DbAutoConfig.class);
	
    @Autowired
    private Environment environment;

    @Override
    public void afterPropertiesSet() throws Exception {
        String dbActioin = environment.getProperty("DB_ACTION");
        log.info("DB_ACTION = " + dbActioin);

        if ("INIT_DATA".equals(dbActioin)) {
        	log.info("init data finished, exit!");
             System.exit(0);
        } else {
        	log.info("skip init data, continue!");
        }
    }
}