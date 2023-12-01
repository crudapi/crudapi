package cn.crudapi.crudapi.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.crudapi.crudapi.config.datasource.DataSourceContextHolder;


@Aspect
public class DataSourceAspect {
	private static final Logger log = LoggerFactory.getLogger(DataSourceAspect.class);
	
	@Pointcut("within(cn.crudapi.crudapi.controller..*)")
	public void applicationPackagePointcut() {
	}

	@Around("applicationPackagePointcut()")
	public Object dataSourceAround(ProceedingJoinPoint joinPoint) throws Throwable {
		String dataSource = DataSourceContextHolder.getHeaderDataSource();
		if (log.isDebugEnabled()) {
			log.debug("DataSourceAspect DataSourceContextHolder setDataSource {}", dataSource);
		}
		DataSourceContextHolder.setDataSource(dataSource);
		try {
			return joinPoint.proceed();
		} finally {
			if (log.isDebugEnabled()) {
				log.debug("DataSourceContextHolder cleanDataSource");
			}
			DataSourceContextHolder.cleanDataSource();
		}
	}
}
