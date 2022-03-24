package cn.crudapi.core.repository.oracle;

import org.springframework.beans.factory.annotation.Autowired;

import cn.crudapi.core.repository.CrudAbstractFactory;
import cn.crudapi.core.repository.CrudAbstractRepository;

public class OracleCrudFactory extends CrudAbstractFactory {
	@Autowired
	private OracleCrudRepository oracleCrudRepository;
	
	@Override
	public CrudAbstractRepository getCrudRepository() {
		return oracleCrudRepository;
	}
}
