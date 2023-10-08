package cn.crudapi.crudapi.repository.mariadb;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.crudapi.crudapi.repository.CrudAbstractRepository;

@Component
public class MariadbCrudRepository extends CrudAbstractRepository {
	private static final Logger log = LoggerFactory.getLogger(MariadbCrudRepository.class);

	@Override
	public String getDateBaseName() {
		return "mariadb";
	}
	
	@Override
	public String getSqlQuotation() {
		log.info("MySqlCrudRepository->getSqlQuotation");
		return super.getSqlQuotation();
	}
}
