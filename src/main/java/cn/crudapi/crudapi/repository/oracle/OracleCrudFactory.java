package cn.crudapi.crudapi.repository.oracle;

import org.springframework.beans.factory.annotation.Autowired;

import cn.crudapi.crudapi.repository.CrudAbstractFactory;
import cn.crudapi.crudapi.repository.CrudAbstractRepository;

public class OracleCrudFactory extends CrudAbstractFactory {
	@Autowired
	private OracleCrudRepository oracleCrudRepository;
	
	@Override
	public CrudAbstractRepository getCrudRepository() {
		return oracleCrudRepository;
	}
}
