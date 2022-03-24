package cn.crudapi.core.repository.mysql;

import org.springframework.beans.factory.annotation.Autowired;

import cn.crudapi.core.repository.CrudAbstractFactory;
import cn.crudapi.core.repository.CrudAbstractRepository;

public class MySqlCrudFactory extends CrudAbstractFactory {
	@Autowired
	private MySqlCrudRepository mySqlCrudRepository;
	
	@Override
	public CrudAbstractRepository getCrudRepository() {
		return mySqlCrudRepository;
	}
}
