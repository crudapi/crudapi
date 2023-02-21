package cn.crudapi.crudapi.repository.sqlite;

import org.springframework.beans.factory.annotation.Autowired;

import cn.crudapi.crudapi.repository.CrudAbstractFactory;
import cn.crudapi.crudapi.repository.CrudAbstractRepository;

public class SqliteCrudFactory extends CrudAbstractFactory {
	@Autowired
	private SqliteCrudRepository sqliteCrudRepository;
	
	@Override
	public CrudAbstractRepository getCrudRepository() {
		return sqliteCrudRepository;
	}
}
