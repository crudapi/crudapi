package cn.crudapi.core.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.lang.Nullable;

import cn.crudapi.core.datasource.config.DataSourceContextHolder;
import cn.crudapi.core.repository.CrudAbstractFactory;

public class DynamicCrudSqlExecute  implements InitializingBean {
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
//		if (this.targetDriverClassNames == null) {
//			throw new IllegalArgumentException("Property 'targetDriverClassNames' is required");
//		}
//		this.resolvedCrudSqlExecutes = new HashMap<>(this.targetDriverClassNames.size());
//		this.targetDriverClassNames.forEach((key, value) -> {
//			CrudAbstractFactory crudAbstractFactory = CrudTemplateUtils.createCrudAbstractFactory(value);
//            CrudSqlExecute crudSqlExecute = new CrudSqlExecute(crudAbstractFactory);
//
//			this.resolvedCrudSqlExecutes.put(key, crudSqlExecute);
//		});
	}
}
