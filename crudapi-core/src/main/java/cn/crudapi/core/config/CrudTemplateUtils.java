package cn.crudapi.core.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.crudapi.core.repository.CrudAbstractFactory;

public class CrudTemplateUtils {
	private static final Logger log = LoggerFactory.getLogger(CrudTemplateUtils.class);
	
	private static final String MYSQL_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	 
	private static Map<String, String> driverClassNameMap = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("com.mysql.cj.jdbc.Driver", "cn.crudapi.core.repository.mysql.MySqlCrudFactory");
			put("org.postgresql.Driver", "cn.crudapi.core.repository.postsql.PostSqlCrudFactory");
			put("com.microsoft.sqlserver.jdbc.SQLServerDriver", "cn.crudapi.core.repository.mssql.MsSqlCrudFactory");
			put("oracle.jdbc.OracleDriver", "cn.crudapi.core.repository.oracle.OracleCrudFactory");
		}
	};
	
	public static String getBeanClassName(String driverClassName) {
		String beanClassName = null;
		log.info("CrudTemplateUtils->getBeanName driverClassName: " + driverClassName);
		
		try {
			String factoryClassName = driverClassNameMap.get(driverClassName);
			if (factoryClassName == null) {
				factoryClassName = driverClassNameMap.get(MYSQL_DRIVER_NAME);
			}
			log.info("CrudTemplateConfig->factoryClassName: " + factoryClassName);
			
			String[] factoryClassNameArr = factoryClassName.split("\\.");
			beanClassName = factoryClassNameArr[factoryClassNameArr.length - 1];
			
//			char[] chars = beanName.toCharArray();
//	        //首字母小写方法，大写会变成小写，如果小写首字母会消失
//	        chars[0] +=32;
//	        beanName = String.valueOf(chars);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return beanClassName;
	}

	public static CrudAbstractFactory createCrudAbstractFactory(String driverClassName) {
		CrudAbstractFactory crudAbstractFactory = null;
		log.info("CrudTemplateUtils->driverClassName: " + driverClassName);
		
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
