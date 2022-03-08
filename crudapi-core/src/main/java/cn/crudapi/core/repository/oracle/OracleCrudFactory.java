package cn.crudapi.core.repository.oracle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.crudapi.core.repository.CrudAbstractFactory;
import cn.crudapi.core.repository.CrudAbstractRepository;

public class OracleCrudFactory extends CrudAbstractFactory {
	private static final Logger log = LoggerFactory.getLogger(OracleCrudFactory.class);
	
	@Autowired
	private OracleCrudRepository oracleCrudRepository;
	
	@Override
	public CrudAbstractRepository getCrudRepository() {
		return oracleCrudRepository;
	}
}
