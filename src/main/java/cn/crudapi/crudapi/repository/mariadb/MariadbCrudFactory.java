package cn.crudapi.crudapi.repository.mariadb;

import org.springframework.beans.factory.annotation.Autowired;

import cn.crudapi.crudapi.repository.CrudAbstractFactory;
import cn.crudapi.crudapi.repository.CrudAbstractRepository;

public class MariadbCrudFactory extends CrudAbstractFactory {
	@Autowired
	private MariadbCrudRepository mariadbCrudRepository;
	
	@Override
	public CrudAbstractRepository getCrudRepository() {
		return mariadbCrudRepository;
	}
}
