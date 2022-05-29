package cn.crudapi.core.datasource.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.datasource.hikari")
public class DynamicDataSourceProvider implements DataSourceProvider {
	@Autowired
	private DynamicDataSource dynamicDataSource;
	
	private List<Map<String, DataSourceProperties>> dataSources;
    
	private DataSource buildDataSource(DataSourceProperties prop) {
        DataSourceBuilder<?> builder = DataSourceBuilder.create();
        builder.driverClassName(prop.getDriverClassName());
        builder.username(prop.getUsername());
        builder.password(prop.getPassword());
        builder.url(prop.getUrl());
        return builder.build();
    }
    
    @Override
    public List<DataSource> provide() {
    	Map<Object,Object> targetDataSourcesMap = new HashMap<>();
    	List<DataSource> res = new ArrayList<>();
    	if (dataSources != null) {
            dataSources.forEach(map -> {
                Set<String> keys = map.keySet();
                keys.forEach(key -> {
                    DataSourceProperties properties = map.get(key);
                    DataSource dataSource = buildDataSource(properties);
                    targetDataSourcesMap.put(key, dataSource);
                   
                });
            });
            
            //更新dynamicDataSource
            dynamicDataSource.setTargetDataSources(targetDataSourcesMap);
            dynamicDataSource.afterPropertiesSet();
    	}
        
        return res;
    }

    @PostConstruct
    public void init() {
        provide();
    }
    
    public List<Map<String, DataSourceProperties>> getDataSources() {
        return dataSources;
    }
    
    public void setDataSources(List<Map<String, DataSourceProperties>> dataSources) {
        this.dataSources = dataSources;
    }
    
    public List<Map<String, String>> getDataSourceNames() {
    	List<Map<String, String>> dataSourceNames = new ArrayList<Map<String, String>>();
    	Map<String, String> dataSourceMap = new HashMap<String, String>();
    	dataSourceMap.put("name", "primary");
    	dataSourceMap.put("caption", "主数据源");
    	dataSourceNames.add(dataSourceMap);
    	if (dataSources != null) {
    		dataSources.forEach(map -> {
	            Set<String> keys = map.keySet();
	            keys.forEach(key -> {
	            	Map<String, String> t = new HashMap<String, String>();
	            	t.put("name", key);
	            	t.put("caption", key);
	            	dataSourceNames.add(t);
	            });
	        });
    	}
       
        return dataSourceNames;
    }
}
