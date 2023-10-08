package cn.crudapi.crudapi.config.datasource;

import cn.crudapi.crudapi.constant.DataSourceConsts;

//默认数据源primary=dataSource
public class DataSourceContextHolder {
    //保存线程连接的数据源
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    private static final ThreadLocal<String> HEADER_HOLDER = new ThreadLocal<>();

    public static String getDataSource() {
    	String dataSoure = CONTEXT_HOLDER.get();
        if (dataSoure != null) {
        	return dataSoure;
        } else {
        	return DataSourceConsts.DEFAULT;
        }
    }

    public static void setDataSource(String key) {
    	if (DataSourceConsts.PRIMARY.equals(key)) {
    		key = DataSourceConsts.DEFAULT;
    	}
        CONTEXT_HOLDER.set(key);
    }

    public static void cleanDataSource() {
        CONTEXT_HOLDER.remove();
        HEADER_HOLDER.remove();
    }
    
    public static void setHeaderDataSource(String key) {
    	HEADER_HOLDER.set(key);
    }
    
    public static String getHeaderDataSource() {
    	String dataSoure = HEADER_HOLDER.get();
        if (dataSoure != null) {
        	return dataSoure;
        } else {
        	return DataSourceConsts.DEFAULT;
        }
    }
}
