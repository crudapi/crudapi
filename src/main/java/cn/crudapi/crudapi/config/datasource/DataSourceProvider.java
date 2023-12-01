package cn.crudapi.crudapi.config.datasource;

import java.util.List;
import javax.sql.DataSource;

public interface DataSourceProvider {
	List<DataSource> provide();
}
