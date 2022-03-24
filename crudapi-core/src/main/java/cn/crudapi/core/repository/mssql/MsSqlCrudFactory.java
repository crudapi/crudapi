package cn.crudapi.core.repository.mssql;

import org.springframework.beans.factory.annotation.Autowired;

import cn.crudapi.core.repository.CrudAbstractFactory;
import cn.crudapi.core.repository.CrudAbstractRepository;

public class MsSqlCrudFactory extends CrudAbstractFactory {
	@Autowired
	private MsSqlCrudRepository msSqlCrudRepository;
	
	@Override
	public CrudAbstractRepository getCrudRepository() {
		return msSqlCrudRepository;
	}
}
