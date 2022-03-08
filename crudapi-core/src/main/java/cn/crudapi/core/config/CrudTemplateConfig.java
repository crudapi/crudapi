package cn.crudapi.core.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.crudapi.core.repository.CrudAbstractFactory;

@Configuration
public class CrudTemplateConfig {
	private static final Logger log = LoggerFactory.getLogger(CrudTemplateConfig.class);
	
	public static final String MYSQL_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	 
	Map<String, String> driverClassNameMap = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("com.mysql.cj.jdbc.Driver", "cn.crudapi.core.repository.mysql.MySqlCrudFactory");
			put("org.postgresql.Driver", "cn.crudapi.core.repository.postsql.PostSqlCrudFactory");
			put("com.microsoft.sqlserver.jdbc.SQLServerDriver", "cn.crudapi.core.repository.mssql.MsSqlCrudFactory");
			put("oracle.jdbc.OracleDriver", "cn.crudapi.core.repository.oracle.OracleCrudFactory");
		}
	};

	
	@Autowired
	private CrudDatasourceProperties crudDatasourceProperties;
	
    @Bean
    public CrudTemplate crudTemplate(CrudAbstractFactory factory) {
    	CrudTemplate crudTemplate =  new CrudTemplate(factory);
    	return crudTemplate;
    }
    
	@Bean
	public CrudAbstractFactory crudAbstractFactory() {
		CrudAbstractFactory crudAbstractFactory = null;
		String driverClassName = crudDatasourceProperties.getDriverClassName();
		log.info("CrudTemplateConfig->driverClassName: " + driverClassName);
		
		try {
			String factoryClassName = driverClassNameMap.get(driverClassName);
			if (factoryClassName == null) {
				factoryClassName = driverClassNameMap.get(MYSQL_DRIVER_NAME);
			}
			log.info("CrudTemplateConfig->factoryClassName: " + factoryClassName);
			
			Class<?> cls = Class.forName(factoryClassName);
			Object obj = cls.newInstance();
			
			crudAbstractFactory = (CrudAbstractFactory)obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return crudAbstractFactory;
	}
}
