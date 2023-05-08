package cn.crudapi.crudapi.config.execute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import cn.crudapi.crudapi.config.datasource.DynamicDataSourceProperties;
import cn.crudapi.crudapi.config.datasource.DynamicDataSourceProvider;
import cn.crudapi.crudapi.repository.CrudAbstractFactory;
import cn.crudapi.crudapi.repository.mssql.MsSqlCrudFactory;
import cn.crudapi.crudapi.repository.mysql.MySqlCrudFactory;
import cn.crudapi.crudapi.repository.oracle.OracleCrudFactory;
import cn.crudapi.crudapi.repository.postsql.PostSqlCrudFactory;
import cn.crudapi.crudapi.repository.sqlite.SqliteCrudFactory;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource.hikari")
public class CrudTemplateConfig {
	@Autowired
	private DataSourceProperties dataSourceProperties;
	
	
	@Autowired
	private DynamicDataSourceProvider dynamicDataSourceProvider;
	
	
    
    @Bean(name = "postSqlCrudFactory")
  	public PostSqlCrudFactory getPostSqlCrudFactory() {
  		return new PostSqlCrudFactory();
  	}
    
    @Bean(name = "mySqlCrudFactory")
	public MySqlCrudFactory getMySqlCrudFactory() {
		return new MySqlCrudFactory();
	}
    
    @Bean(name = "msSqlCrudFactory")
	public MsSqlCrudFactory getMsSqlCrudFactory() {
		return new MsSqlCrudFactory();
	}
    
    @Bean(name = "oracleCrudFactory")
	public OracleCrudFactory getOracleCrudFactory() {
		return new OracleCrudFactory();
	}
    
    @Bean(name = "sqliteCrudFactory")
  	public SqliteCrudFactory getSqliteCrudFactory() {
  		return new SqliteCrudFactory();
  	}
    
    @Bean(name = "crudAbstractFactory")
	public CrudAbstractFactory getDefaultCrudAbstractFactory() {
		String driverClassName = dataSourceProperties.getDriverClassName();
		return CrudTemplateUtils.createCrudAbstractFactory(driverClassName);
	}
	
    public CrudSqlExecute getCrudSqlExecute(CrudAbstractFactory factory) {
    	CrudSqlExecute crudSqlExecute = new CrudSqlExecute(factory);
    	return crudSqlExecute;
    }
    
	@Primary
	@Bean(name = "dynamicCrudSqlExecute")
    public DynamicCrudSqlExecute dynamicCrudSqlExecute() {
		Map<String, String> targetDriverClassNames = new HashMap<String, String>();
		
		List<Map<String, DynamicDataSourceProperties>> dynamicDataSourcePropertiesList = dynamicDataSourceProvider.queryDynamicDataSourcePropertiesList();
		
		dynamicDataSourcePropertiesList.forEach(map -> {
            Set<String> keys = map.keySet();
            keys.forEach(key -> {
            	DynamicDataSourceProperties properties = map.get(key); 
            	String driverClassName = properties.getDriverClassName();
                targetDriverClassNames.put(key, driverClassName);
            });
        });
        
        Map<String, CrudSqlExecute> resolvedCrudSqlExecutes = new HashMap<>(targetDriverClassNames.size());
		targetDriverClassNames.forEach((key, value) -> {
			CrudAbstractFactory crudAbstractFactory = null;
			String beanClassName = CrudTemplateUtils.getBeanClassName(value);
			if (beanClassName.equals("PostSqlCrudFactory")) {
				crudAbstractFactory = getPostSqlCrudFactory();
			} else if (beanClassName.equals("MsSqlCrudFactory")) {
				crudAbstractFactory = getMsSqlCrudFactory();
			} else if (beanClassName.equals("OracleCrudFactory")) {
				crudAbstractFactory = getOracleCrudFactory();
			} else if (beanClassName.equals("MySqlCrudFactory")) {
				crudAbstractFactory = getMySqlCrudFactory();
			} else if (beanClassName.equals("SqliteCrudFactory")) {
				crudAbstractFactory = getSqliteCrudFactory();
			}
			resolvedCrudSqlExecutes.put(key, getCrudSqlExecute(crudAbstractFactory));
		});
        
        DynamicCrudSqlExecute dynamicCrudSqlExecute = new DynamicCrudSqlExecute();
        dynamicCrudSqlExecute.setDefaultCrudSqlExecute(getCrudSqlExecute(getDefaultCrudAbstractFactory()));
        dynamicCrudSqlExecute.setResolvedCrudSqlExecutes(resolvedCrudSqlExecutes);
        
        return dynamicCrudSqlExecute;
    }
	
    @Bean(name = "crudTemplate")
    public CrudTemplate getCrudTemplate(){
        return new CrudTemplate(dynamicCrudSqlExecute());
    }
}
