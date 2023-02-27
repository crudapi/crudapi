package cn.crudapi.crudapi.config.datasource;

public class DataSourceContextHolder {
	//默认数据源primary=dataSource
    private static final String DEFAULT_DATASOURCE = "dataSource";

    //保存线程连接的数据源
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    private static final ThreadLocal<String> HEADER_HOLDER = new ThreadLocal<>();

    public static String getDataSource() {
    	String dataSoure = CONTEXT_HOLDER.get();
        if (dataSoure != null) {
        	return dataSoure;
        } else {
        	return DEFAULT_DATASOURCE;
        }
    }

    public static void setDataSource(String key) {
    	if ("primary".equals(key)) {
    		key = DEFAULT_DATASOURCE;
    	}
        CONTEXT_HOLDER.set(key);
    }

    public static void cleanDataSource() {
        CONTEXT_HOLDER.remove();
    }
    
    public static void setHeaderDataSource(String key) {
    	HEADER_HOLDER.set(key);
    }
    
    public static String getHeaderDataSource() {
    	String dataSoure = HEADER_HOLDER.get();
        if (dataSoure != null) {
        	return dataSoure;
        } else {
        	return DEFAULT_DATASOURCE;
        }
    }
}
