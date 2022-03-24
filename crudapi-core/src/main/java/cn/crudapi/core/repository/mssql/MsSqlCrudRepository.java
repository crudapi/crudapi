package cn.crudapi.core.repository.mssql;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.crudapi.core.enumeration.OperatorTypeEnum;
import cn.crudapi.core.query.CompositeCondition;
import cn.crudapi.core.query.LeafCondition;
import cn.crudapi.core.repository.CrudAbstractRepository;

@Component
public class MsSqlCrudRepository extends CrudAbstractRepository {
	private static final Logger log = LoggerFactory.getLogger(MsSqlCrudRepository.class);
	
	@Override
	public String getDateBaseName() {
		return "mssql";
	}
	
	@Override
	public String getSqlQuotation() {
		return "\"";
	}
	
	@Override
	public String getLimitOffsetSql() {
		//offset X rows fetch next Y rows only
		return "OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY";
	}

	
	@Override
	public boolean isExistTable(String tableName) {
		//select   * from sysObjects where Id=OBJECT_ID(N'ca_file') and xtype='U'
		LeafCondition condition1 = new LeafCondition();
		condition1.setColumnName("name");
		condition1.setValue(tableName);
		condition1.setOperatorType(OperatorTypeEnum.EQ);
		
		LeafCondition condition2 = new LeafCondition();
		condition2.setColumnName("type");
		condition2.setValue("U");
		condition2.setOperatorType(OperatorTypeEnum.EQ);
		
		CompositeCondition condition = new CompositeCondition();
		condition.add(condition1);
		condition.add(condition2);
		
		Long count = this.count("sysObjects", condition);
		
        return count > 0;
	}
	
	@Override
	public Long create(String tableName, Map<String, Object> map) {
		log.info("MsSqlCrudRepository->create {}", tableName);
	
		return super.create(tableName, map);
	}
}
