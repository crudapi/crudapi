package cn.crudapi.crudapi.repository.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.crudapi.crudapi.repository.CrudAbstractRepository;

@Component
public class MySqlCrudRepository extends CrudAbstractRepository {
	private static final Logger log = LoggerFactory.getLogger(MySqlCrudRepository.class);

	@Override
	public String getSqlQuotation() {
		log.info("MySqlCrudRepository->getSqlQuotation");
		return super.getSqlQuotation();
	}
}
