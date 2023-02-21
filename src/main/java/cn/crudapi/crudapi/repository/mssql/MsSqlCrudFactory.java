package cn.crudapi.crudapi.repository.mssql;

import org.springframework.beans.factory.annotation.Autowired;

import cn.crudapi.crudapi.repository.CrudAbstractFactory;
import cn.crudapi.crudapi.repository.CrudAbstractRepository;

public class MsSqlCrudFactory extends CrudAbstractFactory {
	@Autowired
	private MsSqlCrudRepository msSqlCrudRepository;
	
	@Override
	public CrudAbstractRepository getCrudRepository() {
		return msSqlCrudRepository;
	}
}
