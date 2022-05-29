package cn.crudapi.core.config;

import java.util.ArrayList;
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

import cn.crudapi.core.repository.CrudAbstractFactory;
import cn.crudapi.core.repository.mssql.MsSqlCrudFactory;
import cn.crudapi.core.repository.mysql.MySqlCrudFactory;
import cn.crudapi.core.repository.oracle.OracleCrudFactory;
import cn.crudapi.core.repository.postsql.PostSqlCrudFactory;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource.hikari")
public class CrudTemplateConfig {
	@Autowired
	private CrudDatasourceProperties crudDatasourceProperties;
	
	private List<Map<String, DataSourceProperties>> dataSources;
    
	public List<Map<String, DataSourceProperties>> getDataSources() {
        return dataSources;
    }
    
    public void setDataSources(List<Map<String, DataSourceProperties>> dataSources) {
        this.dataSources = dataSources;
    }
    
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
    
    @Bean(name = "crudAbstractFactory")
	public CrudAbstractFactory getCrudAbstractFactory() {
		String driverClassName = crudDatasourceProperties.getDriverClassName();
		return CrudTemplateUtils.createCrudAbstractFactory(driverClassName);
	}
	
    
    @Bean(name = "crudSqlExecute")
    public CrudSqlExecute getCrudSqlExecute(CrudAbstractFactory factory) {
    	CrudSqlExecute crudSqlExecute = new CrudSqlExecute(factory);
    	return crudSqlExecute;
    }
    
	@Primary
	@Bean(name = "dynamicCrudSqlExecute")
    public DynamicCrudSqlExecute dynamicCrudTemplate() {
		Map<String, String> targetDriverClassNames = new HashMap<String, String>();
		
		if (dataSources == null) {
			dataSources = new ArrayList<Map<String, DataSourceProperties>>();
		}
		
        dataSources.forEach(map -> {
            Set<String> keys = map.keySet();
            keys.forEach(key -> {
                DataSourceProperties properties = map.get(key); 
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
			}
			
            CrudSqlExecute crudSqlExecute = new CrudSqlExecute(crudAbstractFactory);

			resolvedCrudSqlExecutes.put(key, crudSqlExecute);
		});
        
        DynamicCrudSqlExecute dynamicCrudSqlExecute = new DynamicCrudSqlExecute();
        dynamicCrudSqlExecute.setDefaultCrudSqlExecute(getCrudSqlExecute(getCrudAbstractFactory()));
        dynamicCrudSqlExecute.setResolvedCrudSqlExecutes(resolvedCrudSqlExecutes);
        
        return dynamicCrudSqlExecute;
    }
	
    @Bean(name = "crudTemplate")
    public CrudTemplate getCrudTemplate(){
        return new CrudTemplate(dynamicCrudTemplate());
    }
}
