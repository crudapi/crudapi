package cn.crudapi.crudapi.repository.postsql;

import org.springframework.beans.factory.annotation.Autowired;

import cn.crudapi.crudapi.repository.CrudAbstractFactory;
import cn.crudapi.crudapi.repository.CrudAbstractRepository;

public class PostSqlCrudFactory extends CrudAbstractFactory {
	@Autowired
	private PostSqlCrudRepository postSqlCrudRepository;
	
	@Override
	public CrudAbstractRepository getCrudRepository() {
		return postSqlCrudRepository;
	}
}
