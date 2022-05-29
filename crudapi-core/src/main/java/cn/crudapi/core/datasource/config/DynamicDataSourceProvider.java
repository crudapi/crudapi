package cn.crudapi.core.datasource.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(DataSourceProperties.class)
@ConfigurationProperties(prefix = "spring.datasource.hikari")
public class DynamicDataSourceProvider implements DataSourceProvider {
	@Autowired
	private DynamicDataSource dynamicDataSource;
	
	private List<Map<String, DataSourceProperties>> dataSources;
    
	@Resource
	private DataSourceProperties dataSourceProperties;
    
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
    	Map<String, String> dataSourceNameMap = new HashMap<String, String>();
    	dataSourceNameMap.put("name", "primary");
    	dataSourceNameMap.put("caption", "primary");
    	dataSourceNameMap.put("database", parseDatabaseName(dataSourceProperties));
    	dataSourceNames.add(dataSourceNameMap);
    	
    	if (dataSources != null) {
    		dataSources.forEach(map -> {
    			Set<Map.Entry<String, DataSourceProperties>> entrySet = map.entrySet();
    	        for (Map.Entry<String, DataSourceProperties> entry : entrySet) {
    	        	Map<String, String> t = new HashMap<String, String>();
    	        	t.put("name", entry.getKey());
    	        	t.put("caption", entry.getKey());
    	        	DataSourceProperties p = entry.getValue();
    	        	t.put("database", parseDatabaseName(p));
    	        	
    	        	dataSourceNames.add(t);
    	        }
	        });
    	}
       
        return dataSourceNames;
    }
    
    public String getDatabaseName() {
    	List<Map<String, String>> dataSourceNames = this.getDataSourceNames();
    	String dataSource = DataSourceContextHolder.getDataSource();
    	
    	return dataSourceNames.stream()
    	.filter(t -> t.get("name").toString().equals(dataSource))
    	.findFirst().get().get("database");
    }
    
    
    private String parseDatabaseName(DataSourceProperties p) {
    	String url = p.getUrl();
    	String databaseName = "";
    	if (url.toLowerCase().indexOf("databasename") >= 0) {
    		String[] urlArr = p.getUrl().split(";");
    		for (String u : urlArr) {
    			if (u.toLowerCase().indexOf("databasename") >= 0) {
    				String[] uArr = u.split("=");
    				databaseName = uArr[uArr.length - 1];
    			}
    		}
    	} else {
    		String[] urlArr = p.getUrl().split("\\?")[0].split("/");
    		databaseName = urlArr[urlArr.length - 1];
    	}
    	
    	return databaseName;
    }
}
