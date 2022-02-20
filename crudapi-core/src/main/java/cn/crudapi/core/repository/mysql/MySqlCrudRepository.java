package cn.crudapi.core.repository.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.crudapi.core.repository.CrudAbstractRepository;

@Component
public class MySqlCrudRepository extends CrudAbstractRepository {
	private static final Logger log = LoggerFactory.getLogger(MySqlCrudRepository.class);

	@Override
	public Long create(String tableName, Object obj) {
		log.info("MySqlCrudRepository->create");

		return super.create(tableName, obj);
	}
}
