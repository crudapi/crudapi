package cn.crudapi.crudapi.config.execute;

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;

import cn.crudapi.crudapi.config.datasource.DataSourceContextHolder;

public class DynamicCrudSqlExecute implements InitializingBean {
	@Nullable
	private CrudSqlExecute defaultCrudSqlExecute;
	
	@Nullable
	private Map<String, String> targetDriverClassNames;

	@Nullable
	private Map<String, CrudSqlExecute> resolvedCrudSqlExecutes;

	
	public String determineCurrentLookupKey() {
		return DataSourceContextHolder.getDataSource();
	}

	public void setDefaultCrudSqlExecute(CrudSqlExecute defaultCrudSqlExecute) {
		this.defaultCrudSqlExecute = defaultCrudSqlExecute;
	}

	public void setTargetDriverClassNames(Map<String, String> targetDriverClassNames) {
		this.targetDriverClassNames = targetDriverClassNames;
	}

	public void setResolvedCrudSqlExecutes(Map<String, CrudSqlExecute> resolvedCrudSqlExecutes) {
		this.resolvedCrudSqlExecutes = resolvedCrudSqlExecutes;
	}

	protected CrudSqlExecute determineTargetDataSource() {
		String lookupKey = determineCurrentLookupKey();
		CrudSqlExecute crudSqlExecute = this.resolvedCrudSqlExecutes.get(lookupKey);
		if (crudSqlExecute == null) {
			crudSqlExecute = this.defaultCrudSqlExecute;
		}
		if (crudSqlExecute == null) {
			throw new IllegalStateException("Cannot determine target crudSqlExecute for lookup key [" + lookupKey + "]");
		}
		return crudSqlExecute;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
	}
}
