package cn.crudapi.crudapi.config.execute;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.crudapi.crudapi.repository.CrudAbstractFactory;

public class CrudTemplateUtils {
	private static final Logger log = LoggerFactory.getLogger(CrudTemplateUtils.class);
	
	private static final String MYSQL_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	 
	private static Map<String, String> driverClassNameMap = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("com.mysql.cj.jdbc.Driver", "cn.crudapi.crudapi.repository.mysql.MySqlCrudFactory");
			put("org.postgresql.Driver", "cn.crudapi.crudapi.repository.postsql.PostSqlCrudFactory");
			put("com.microsoft.sqlserver.jdbc.SQLServerDriver", "cn.crudapi.crudapi.repository.mssql.MsSqlCrudFactory");
			put("oracle.jdbc.OracleDriver", "cn.crudapi.crudapi.repository.oracle.OracleCrudFactory");
			put("org.sqlite.JDBC", "cn.crudapi.crudapi.repository.sqlite.SqliteCrudFactory");
		}
	};
	
	public static String getBeanClassName(String driverClassName) {
		String beanClassName = null;
		log.debug("CrudTemplateUtils->getBeanName driverClassName = {}", driverClassName);
		
		try {
			String factoryClassName = driverClassNameMap.get(driverClassName);
			if (factoryClassName == null) {
				factoryClassName = driverClassNameMap.get(MYSQL_DRIVER_NAME);
			}
			log.debug("CrudTemplateUtils->factoryClassName = {} ", factoryClassName);
			
			String[] factoryClassNameArr = factoryClassName.split("\\.");
			beanClassName = factoryClassNameArr[factoryClassNameArr.length - 1];
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return beanClassName;
	}

	public static CrudAbstractFactory createCrudAbstractFactory(String driverClassName) {
		CrudAbstractFactory crudAbstractFactory = null;
		log.debug("CrudTemplateUtils->driverClassName = {}", driverClassName);
		
		try {
			String factoryClassName = driverClassNameMap.get(driverClassName);
			if (factoryClassName == null) {
				factoryClassName = driverClassNameMap.get(MYSQL_DRIVER_NAME);
			}
			log.debug("CrudTemplateUtils->factoryClassName = {}", factoryClassName);
			
			Class<?> cls = Class.forName(factoryClassName);
			Object obj = cls.getDeclaredConstructor().newInstance();
			
			crudAbstractFactory = (CrudAbstractFactory)obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return crudAbstractFactory;
	}
}
