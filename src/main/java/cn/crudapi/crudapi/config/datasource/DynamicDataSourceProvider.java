package cn.crudapi.crudapi.config.datasource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(DataSourceProperties.class)
@ConfigurationProperties(prefix = "spring.datasource.hikari")
public class DynamicDataSourceProvider implements DataSourceProvider {
	private static final Logger log = LoggerFactory.getLogger(DynamicDataSourceProvider.class);

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private DynamicDataSource dynamicDataSource;
	
	private List<Map<String, DynamicDataSourceProperties>> dataSources;
    
	private List<Map<String, DynamicDataSourceProperties>> dynamicDataSourcePropertiesList;
	
	private Map<Object,Object> targetDataSourcesMap;
	
	private Map<String, String> targetDataSourceNameDataBaseTypesMap;
	
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
    
    public List<Map<String, DynamicDataSourceProperties>> getDynamicDataSourcePropertiesList() {
    	if (this.dynamicDataSourcePropertiesList != null) {
    		return this.dynamicDataSourcePropertiesList;
    	}
    	List<Map<String, DynamicDataSourceProperties>> dynamicDataSourcePropertiesList = new ArrayList<Map<String, DynamicDataSourceProperties>>();
    	if (dataSources != null) {
            dataSources.forEach(map -> {
                Set<String> keys = map.keySet();
                keys.forEach(key -> {
                	DynamicDataSourceProperties t = map.get(key);
                	
                	String name = key;
                	String caption = t.getCaption();
	           		String databaseType = t.getDatabaseType();
	           		String driverClassName = t.getDriverClassName();
	           		String url = t.getUrl();
	           		Boolean deleted = t.getDeleted();
	           		String status = t.getStatus();
	           		if (Boolean.TRUE.equals(deleted)) {
		           		log.warn("[config skip dataSoure]name = {}, caption = {}, databaseType = {}, driverClassName = {}, deleted = {}, url = {}", name, caption, databaseType, driverClassName, deleted, url);
	           		} else {
	           			if ("ACTIVE".equals(status)) {
	           				log.info("[config add dataSoure]name = {}, caption = {}, databaseType = {}, driverClassName = {}, status = {}, url = {}", name, caption, databaseType, driverClassName, status, url);
			           		dynamicDataSourcePropertiesList.add(map);
	           			} else {
	           				log.warn("[config skip dataSoure]name = {}, caption = {}, databaseType = {}, driverClassName = {}, status = {}, url = {}", name, caption, databaseType, driverClassName, status, url);
	           			}
	           		}
                });
            });
    	}
    	
    	List<Map<String, Object>> dataSourceMapList = namedParameterJdbcTemplate.queryForList("SELECT * FROM `ca_system_data_source`  WHERE `is_deleted` = false AND `status` = 'ACTIVE' ORDER BY `display_order` ASC", new HashMap<String, Object>());
    	for (Map<String, Object> t : dataSourceMapList) {
    		 DynamicDataSourceProperties properties = new DynamicDataSourceProperties();
    		 String name = t.get("name").toString();
    		 String caption = t.get("caption").toString();
    		 String databaseType = t.get("database_type").toString();
    		 String driverClassName = t.get("driver_class_name").toString();
    		 String url = t.get("url").toString();
    		 String username = t.get("username").toString();
    		 String password = t.get("password").toString();
    		 String metadataTablePrefix = t.get("metadata_table_prefix").toString();
    		 String metadataDatabaseNaming = t.get("metadata_database_naming").toString();
    		 String businessTablePrefix = t.get("business_table_prefix").toString();
    		 String businessDatabaseNaming = t.get("business_database_naming").toString();
    		 
    		 log.info("[database add dataSoure]name = {}, caption = {}, databaseType = {}, metadataTablePrefix = {}, metadataDatabaseNaming = {}, businessTablePrefix = {}, businessDatabaseNaming = {}, driverClassName = {}, url = {}",
    				 name, caption, databaseType, metadataTablePrefix, metadataDatabaseNaming, businessTablePrefix, businessDatabaseNaming, driverClassName, url);
    		 
    		 properties.setCaption(caption);
    		 properties.setDriverClassName(driverClassName);
    		 properties.setUrl(url);
    		 properties.setUsername(username);
    		 properties.setPassword(password);
    		 properties.setDatabaseType(databaseType);
    		 properties.setMetadataTablePrefix(metadataTablePrefix);
    		 properties.setMetadataDatabaseNaming(metadataDatabaseNaming);
    		 properties.setBusinessTablePrefix(businessTablePrefix);
    		 properties.setBusinessDatabaseNaming(businessDatabaseNaming);
    		 properties.setStatus("ACTIVE");
    		 properties.setDeleted(false);
    		 
    		 Map<String, DynamicDataSourceProperties> propertiesMap = new HashMap<String, DynamicDataSourceProperties>();
    		 propertiesMap.put(name, properties);
    		 dynamicDataSourcePropertiesList.add(propertiesMap);
    	}
    	
    	this.dynamicDataSourcePropertiesList = dynamicDataSourcePropertiesList;
        return dynamicDataSourcePropertiesList;
    }

    @Override
    public List<DataSource> provide() {
    	Map<Object, Object> targetDataSourcesMap = new HashMap<>();
    	Map<String, String> targetDataSourceNameDataBaseTypesMap = new HashMap<>();
    	List<DataSource> res = new ArrayList<>();
    	
    	List<Map<String, DynamicDataSourceProperties>> dynamicDataSourcePropertiesList = this.getDynamicDataSourcePropertiesList();
    	
		dynamicDataSourcePropertiesList.forEach(map -> {
            Set<String> keys = map.keySet();
            keys.forEach(key -> {
            	DynamicDataSourceProperties t = map.get(key);
            	
            	String name = key;
            	String caption = t.getCaption();
           		String databaseType = t.getDatabaseType();
           		String driverClassName = t.getDriverClassName();
           		String url = t.getUrl();
           		String username = t.getUsername();
           		String password = t.getPassword();
           		Boolean deleted = t.getDeleted();

           		log.info("[build dataSource]name = {}, caption = {}, databaseType = {}, driverClassName = {}, deleted = {}, url = {}", 
           				name, caption, databaseType, driverClassName, deleted, url);
       			DataSourceProperties properties = new DataSourceProperties();
       			properties.setDriverClassName(driverClassName);
           		properties.setUrl(url);
           		properties.setUsername(username);
           		properties.setPassword(password);
           		
                DataSource dataSource = buildDataSource(properties);
                targetDataSourcesMap.put(name, dataSource);
                targetDataSourceNameDataBaseTypesMap.put(name, databaseType);
            });
        });
    	
    	
        //更新dynamicDataSource
        this.targetDataSourcesMap = targetDataSourcesMap;
        this.targetDataSourceNameDataBaseTypesMap = targetDataSourceNameDataBaseTypesMap;
        dynamicDataSource.setTargetDataSources(targetDataSourcesMap);
        dynamicDataSource.afterPropertiesSet();
        
        return res;
    }

    @PostConstruct
    public void init() {
        provide();
    }
    
    public List<Map<String, DynamicDataSourceProperties>> getDataSources() {
        return dataSources;
    }
    
    public void setDataSources(List<Map<String, DynamicDataSourceProperties>> dataSources) {
        this.dataSources = dataSources;
    }
    
    public List<Map<String, String>> getDataSourceNames() {
    	List<Map<String, String>> dataSourceNames = new ArrayList<Map<String, String>>();
    	Map<String, String> dataSourceNameMap = new HashMap<String, String>();
    	dataSourceNameMap.put("name", "primary");
    	dataSourceNameMap.put("caption", "主数据源");
    	dataSourceNameMap.put("database", parseDatabaseName(dataSourceProperties));
    	dataSourceNames.add(dataSourceNameMap);
    	
    	if (dataSources != null) {
    		dataSources.forEach(map -> {
    			Set<Map.Entry<String, DynamicDataSourceProperties>> entrySet = map.entrySet();
    	        for (Map.Entry<String, DynamicDataSourceProperties> entry : entrySet) {
    	        	Map<String, String> t = new HashMap<String, String>();
    	        	t.put("name", entry.getKey());
    	        	DynamicDataSourceProperties p = entry.getValue();
    	        	t.put("caption", p.getCaption());
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
    	
    	Optional<Map<String, String>> op = dataSourceNames.stream()
    	.filter(t -> t.get("name").toString().equals(dataSource))
    	.findFirst();
    	if (op.isPresent()) {
    		return op.get().get("database");
    	} else {
	    	return dataSourceNames.stream()
	    	.filter(t -> t.get("name").toString().equals("primary"))
	    	.findFirst().get().get("database");
    	}
    }
    
    private String parseDatabaseName(DataSourceProperties p) {
    	String url = p.getUrl();
    	String databaseName = "";
    	
    	if (url.startsWith("jdbc:sqlite")) {
    		//jdbc:sqlite:crudapi-sqlite.db
    		String[] urlArr = url.split(":");
    		databaseName = urlArr[urlArr.length - 1];
    		databaseName = databaseName.split("\\.")[0];
    	} else if (url.toLowerCase().indexOf("databasename") >= 0) {
    		String[] urlArr = url.split(";");
    		for (String u : urlArr) {
    			if (u.toLowerCase().indexOf("databasename") >= 0) {
    				String[] uArr = u.split("=");
    				databaseName = uArr[uArr.length - 1];
    			}
    		}
    	} else {
    		String[] urlArr = url.split("\\?")[0].split("/");
    		databaseName = urlArr[urlArr.length - 1];
    	}
    	
    	return databaseName;
    }

	public Map<Object, Object> getTargetDataSourcesMap() {
		return targetDataSourcesMap;
	}
	
	public Map<String, String> getTargetDataSourceNameDataBaseTypesMap() {
		return targetDataSourceNameDataBaseTypesMap;
	}
}
