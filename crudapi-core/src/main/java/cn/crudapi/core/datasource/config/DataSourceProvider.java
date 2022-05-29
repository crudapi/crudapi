package cn.crudapi.core.datasource.config;

import java.util.List;
import javax.sql.DataSource;

public interface DataSourceProvider {
	List<DataSource> provide();
}
