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

import cn.crudapi.crudapi.constant.ColumnConsts;
import cn.crudapi.crudapi.constant.DataSourceConsts;
import cn.crudapi.crudapi.constant.SqlConsts;
import cn.crudapi.crudapi.constant.Status;
import cn.crudapi.crudapi.constant.SystemConsts;

@Component
@EnableConfigurationProperties({ DataSourceProperties.class, DataSourceExtProperties.class })
@ConfigurationProperties(prefix = "spring.datasource.hikari")
public class DynamicDataSourceProvider implements DataSourceProvider {
	private static final Logger log = LoggerFactory.getLogger(DynamicDataSourceProvider.class);

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	private DynamicDataSource dynamicDataSource;
	
	private List<Map<String, DynamicDataSourceProperties>> dataSources;
    
	private List<Map<String, DynamicDataSourceProperties>> dynamicDataSourcePropertiesList;
	
	private Map<Object, Object> targetDataSourcesMap;
	
	private Map<String, String> targetDataSourceNameDataBaseTypesMap;
	
	@Resource
	private DataSourceProperties dataSourceProperties;
    
	@Resource
	private DataSourceExtProperties dataSourceExtProperties;
	
	private DynamicDataSourceProperties dynamicDataSourceProperties;
	
	public DynamicDataSourceProperties queryDynamicDataSourceProperties() {
		if (this.dynamicDataSourceProperties != null) {
			return this.dynamicDataSourceProperties;
		}
		DynamicDataSourceProperties dynamicDataSourceProperties = new DynamicDataSourceProperties();
		dynamicDataSourceProperties.setBusinessDatabaseNaming(this.dataSourceExtProperties.getBusinessDatabaseNaming());
		dynamicDataSourceProperties.setBusinessTablePrefix(this.dataSourceExtProperties.getBusinessTablePrefix());
		dynamicDataSourceProperties.setMetadataDatabaseNaming(this.dataSourceExtProperties.getMetadataDatabaseNaming());
		dynamicDataSourceProperties.setMetadataTablePrefix(this.dataSourceExtProperties.getMetadataTablePrefix());
		dynamicDataSourceProperties.setCaption(this.dataSourceExtProperties.getCaption());
		dynamicDataSourceProperties.setDatabaseType(this.dataSourceExtProperties.getDatabaseType());
		dynamicDataSourceProperties.setName(DataSourceConsts.DEFAULT);
		
		this.dynamicDataSourceProperties = dynamicDataSourceProperties;
		
		return dynamicDataSourceProperties;
	}

	private DataSource buildDataSource(DataSourceProperties prop) {
        DataSourceBuilder<?> builder = DataSourceBuilder.create();
        builder.driverClassName(prop.getDriverClassName());
        builder.username(prop.getUsername());
        builder.password(prop.getPassword());
        builder.url(prop.getUrl());
        return builder.build();
    }
	
	public List<Map<String, Object>> listDataSourceFromDatabase() {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put(ColumnConsts.IS_DELETED, false);
		paramsMap.put(ColumnConsts.STATUS, Status.ACTIVE);
		
		StringBuilder sb = new StringBuilder();
		sb.append(SqlConsts.SELECT)
			.append(" * ")
			.append(SqlConsts.FROM)
			.append(" ")
			.append(SystemConsts.TABLE_DATA_SOURCE)
			.append(" ")
			.append(SqlConsts.WHERE)
			.append(" ")
			.append(ColumnConsts.IS_DELETED)
			.append(" = :")
			.append(ColumnConsts.IS_DELETED)
			.append(" ")
			.append(SqlConsts.AND)
			.append(" ")
			.append(ColumnConsts.STATUS)
			.append(" = :")
			.append(ColumnConsts.STATUS)
			.append(" ")
			.append(SqlConsts.ORDER)
			.append(" ")
			.append(SqlConsts.BY)
			.append(" ")
			.append(ColumnConsts.DISPLAY_ORDER)
			.append(" ")
			.append(SqlConsts.ASC);

		
		String sql = sb.toString();
		
		log.info(sql);
		
    	List<Map<String, Object>> dataSourceMapList = namedParameterJdbcTemplate.queryForList(sql, paramsMap);
    	
    	return dataSourceMapList;
	}
    
    public List<Map<String, DynamicDataSourceProperties>> queryDynamicDataSourcePropertiesList() {
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
	           			if (Status.ACTIVE.equals(status)) {
	           				log.info("[config add dataSoure]name = {}, caption = {}, databaseType = {}, driverClassName = {}, status = {}, url = {}", name, caption, databaseType, driverClassName, status, url);
			           		dynamicDataSourcePropertiesList.add(map);
	           			} else {
	           				log.warn("[config skip dataSoure]name = {}, caption = {}, databaseType = {}, driverClassName = {}, status = {}, url = {}", name, caption, databaseType, driverClassName, status, url);
	           			}
	           		}
                });
            });
    	}
    	
    	List<Map<String, Object>> dataSourceMapList = this.listDataSourceFromDatabase();
    	
    	for (Map<String, Object> t : dataSourceMapList) {
    		 DynamicDataSourceProperties properties = new DynamicDataSourceProperties();
    		 String name = t.get(ColumnConsts.NAME).toString();
    		 String caption = t.get(ColumnConsts.CAPTION).toString();
    		 String databaseType = t.get(ColumnConsts.DATABASE_TYPE).toString();
    		 String driverClassName = t.get(ColumnConsts.DRIVER_CLASS_NAME).toString();
    		 String url = t.get(ColumnConsts.URL).toString();
    		 String username = t.get(ColumnConsts.USERNAME).toString();
    		 String password = t.get(ColumnConsts.PASSWORD).toString();
    		 String metadataTablePrefix = t.get(ColumnConsts.METADATA_TABLE_PREFIX).toString();
    		 String metadataDatabaseNaming = t.get(ColumnConsts.METADATA_DATABASE_NAMING).toString();
    		 String businessTablePrefix = t.get(ColumnConsts.BUSINESS_TABLE_PREFIX).toString();
    		 String businessDatabaseNaming = t.get(ColumnConsts.BUSINESS_DATABASE_NAMING).toString();
    		 
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
    		 properties.setStatus(Status.ACTIVE);
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
    	
    	List<Map<String, DynamicDataSourceProperties>> dynamicDataSourcePropertiesList = this.queryDynamicDataSourcePropertiesList();
    	
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
    	DynamicDataSourceProperties dynamicDataSourceProperties = this.queryDynamicDataSourceProperties();
    	
    	List<Map<String, String>> dataSourceNames = new ArrayList<Map<String, String>>();
    	Map<String, String> dataSourceNameMap = new HashMap<String, String>();
    	dataSourceNameMap.put("name", DataSourceConsts.PRIMARY);
    	dataSourceNameMap.put("databaseType", dynamicDataSourceProperties.getDatabaseType());
    	dataSourceNameMap.put("caption", dynamicDataSourceProperties.getCaption());
    	dataSourceNameMap.put("database", parseDatabaseName(dataSourceProperties));
    	dataSourceNames.add(dataSourceNameMap);
    	
    	if (dataSources != null) {
    		dataSources.forEach(map -> {
    			Set<Map.Entry<String, DynamicDataSourceProperties>> entrySet = map.entrySet();
    	        for (Map.Entry<String, DynamicDataSourceProperties> entry : entrySet) {
    	        	Map<String, String> t = new HashMap<String, String>();
    	        	t.put("name", entry.getKey());
    	        	DynamicDataSourceProperties p = entry.getValue();
    	        	dataSourceNameMap.put("databaseType", p.getDatabaseType());
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
	    	.filter(t -> t.get("name").toString().equals(DataSourceConsts.PRIMARY))
	    	.findFirst().get().get("database");
    	}
    }
    
    public String getDatabaseType() {
    	List<Map<String, String>> dataSourceNames = this.getDataSourceNames();
    	String dataSource = DataSourceContextHolder.getDataSource();
    	
    	Optional<Map<String, String>> op = dataSourceNames.stream()
    	.filter(t -> t.get("name").toString().equals(dataSource))
    	.findFirst();
    	if (op.isPresent()) {
    		return op.get().get("databaseType");
    	} else {
	    	return dataSourceNames.stream()
	    	.filter(t -> t.get("name").toString().equals(DataSourceConsts.PRIMARY))
	    	.findFirst().get().get("databaseType");
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
