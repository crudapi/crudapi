package cn.crudapi.core.repository.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.crudapi.core.repository.CrudAbstractFactory;
import cn.crudapi.core.repository.CrudAbstractRepository;

public class MySqlCrudFactory extends CrudAbstractFactory {
	private static final Logger log = LoggerFactory.getLogger(MySqlCrudFactory.class);
	
	@Autowired
	private MySqlCrudRepository mySqlCrudRepository;
	
	@Override
	public CrudAbstractRepository getCrudRepository() {
		return mySqlCrudRepository;
	}
}
