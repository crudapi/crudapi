package cn.crudapi.core.service;

import java.util.List;
import java.util.Map;

import cn.crudapi.core.dto.TableDTO;
import cn.crudapi.core.query.Condition;

public interface TableMetadataService {
    Long create(TableDTO tableDTO);

    void update(Long tableId, TableDTO tableDTO);

    TableDTO get(Long tableId);

    TableDTO get(String tableName);

    void delete(Long tableId, Boolean isDropPhysicalTable);
    
    void delete(List<Long> ids, Boolean isDropPhysicalTable);

    void deleteAll(Boolean isDropPhysicalTable);

    Long count(String filter, String search, Condition condition);
    
    List<TableDTO> list(String filter, String search, Condition condition, Integer offset, Integer limit, String orderby);

	List<TableDTO> listAll(List<Long> idList);

	Map<String, Object> getMetaData(String tableName);

	List<Map<String, Object>> getMetaDatas();
	
	List<Long> batchReverseMetaData(List<String> tableNames);

	Long reverseMetaData(String tableName);
	
	void checkTable();

	Boolean isExist(String tableName);

}
