package cn.crudapi.core.repository.mssql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.crudapi.core.repository.CrudAbstractFactory;
import cn.crudapi.core.repository.CrudAbstractRepository;

public class MsSqlCrudFactory extends CrudAbstractFactory {
	private static final Logger log = LoggerFactory.getLogger(MsSqlCrudFactory.class);
	
	@Autowired
	private MsSqlCrudRepository msSqlCrudRepository;
	
	@Override
	public CrudAbstractRepository getCrudRepository() {
		return msSqlCrudRepository;
	}
}
