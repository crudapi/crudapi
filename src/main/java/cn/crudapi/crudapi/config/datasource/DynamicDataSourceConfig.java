package cn.crudapi.crudapi.config.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
public class DynamicDataSourceConfig {
	private static final Logger log = LoggerFactory.getLogger(DynamicDataSourceConfig.class);
	
	@Resource
	private DataSourceProperties dataSourceProperties;
    
    @Bean(name = "dataSource")
    public DataSource getDataSource(){
    	log.info("[init bean]dataSource: DynamicDataSourceConfig->getDataSource");
    	DataSourceBuilder<?> builder = DataSourceBuilder.create();
        builder.driverClassName(dataSourceProperties.getDriverClassName());
        builder.username(dataSourceProperties.getUsername());
        builder.password(dataSourceProperties.getPassword());
        builder.url(dataSourceProperties.getUrl());
        return builder.build();
    }

    @Primary
    @Bean("dynamicDataSource")
    public DynamicDataSource dynamicDataSource(){
    	log.info("[init bean]dynamicDataSource: DynamicDataSourceConfig->DynamicDataSource");
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(getDataSource());

        Map<Object,Object> targetDataSourcesMap = new HashMap<>();
        dynamicDataSource.setTargetDataSources(targetDataSourcesMap);
        return dynamicDataSource;
    }
 
    //事务管理器DataSourceTransactionManager构造参数需要DataSource
    //这里可以看到我们给的是dynamicDS这个bean
    @Bean
    public PlatformTransactionManager transactionManager(){
    	log.info("[init bean]PlatformTransactionManager: DynamicDataSourceConfig->transactionManager");
        return new DataSourceTransactionManager(dynamicDataSource());
    }
 
    //这里的JdbcTemplate构造参数同样需要一个DataSource,为了实现数据源切换查询，
    //这里使用的也是dynamicDS这个bean
    @Bean(name = "jdbcTemplate")
    public JdbcTemplate getJdbc(){
    	log.info("[init bean]JdbcTemplate: DynamicDataSourceConfig->getJdbc");
        return new JdbcTemplate(dynamicDataSource());
    }
    
    //这里的JdbcTemplate构造参数同样需要一个DataSource,为了实现数据源切换查询，
    //这里使用的也是dynamicDS这个bean
    @Bean(name = "namedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate getNamedJdbc(){
    	log.info("[init bean]NamedParameterJdbcTemplate: DynamicDataSourceConfig->getNamedJdbc");
        return new NamedParameterJdbcTemplate(dynamicDataSource());
    }
}
