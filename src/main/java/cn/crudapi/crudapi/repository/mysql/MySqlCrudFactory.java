package cn.crudapi.crudapi.repository.mysql;

import org.springframework.beans.factory.annotation.Autowired;

import cn.crudapi.crudapi.repository.CrudAbstractFactory;
import cn.crudapi.crudapi.repository.CrudAbstractRepository;

public class MySqlCrudFactory extends CrudAbstractFactory {
	@Autowired
	private MySqlCrudRepository mySqlCrudRepository;
	
	@Override
	public CrudAbstractRepository getCrudRepository() {
		return mySqlCrudRepository;
	}
}
