package cn.crudapi.core.repository.postsql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.crudapi.core.repository.CrudAbstractFactory;
import cn.crudapi.core.repository.CrudAbstractRepository;

public class PostSqlCrudFactory extends CrudAbstractFactory {
	private static final Logger log = LoggerFactory.getLogger(PostSqlCrudFactory.class);
	
	@Autowired
	private PostSqlCrudRepository postSqlCrudRepository;
	
	@Override
	public CrudAbstractRepository getCrudRepository() {
		return postSqlCrudRepository;
	}
}
