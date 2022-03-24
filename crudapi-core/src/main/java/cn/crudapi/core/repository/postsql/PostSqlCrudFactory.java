package cn.crudapi.core.repository.postsql;

import org.springframework.beans.factory.annotation.Autowired;

import cn.crudapi.core.repository.CrudAbstractFactory;
import cn.crudapi.core.repository.CrudAbstractRepository;

public class PostSqlCrudFactory extends CrudAbstractFactory {
	@Autowired
	private PostSqlCrudRepository postSqlCrudRepository;
	
	@Override
	public CrudAbstractRepository getCrudRepository() {
		return postSqlCrudRepository;
	}
}
