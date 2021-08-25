package cn.crudapi.core.service;

import java.util.List;

import cn.crudapi.core.dto.TableRelationDTO;
import cn.crudapi.core.enumeration.TableRelationTypeEnum;
import cn.crudapi.core.query.Condition;

public interface TableRelationMetadataService {
	void set(List<TableRelationDTO> tableRelationDTOList);

	List<TableRelationDTO> list(String filter, String search, Condition condition, Integer offset, Integer limit, String orderby);
	    
	Long count(String filter, String search, Condition condition);

	TableRelationDTO get(Long tableId);
	
	void deleteAll();

	void deleteByFromTable(Long tableId);
	
	void deleteByToTable(Long toTableId);
	
	void delete(Long tableId);

	Long create(TableRelationTypeEnum relationType, String name, String caption, String fromTableName,
			String fromColumnName, String toTableName, String toColumnName);
	
	Long create(TableRelationDTO tableRelationDTO);
	
	void delete(List<Long> ids);

	List<TableRelationDTO> getFromTable(Long fromTableId);

	List<TableRelationDTO> getFromTable(String fromTableName);

	void update(Long id, TableRelationDTO tableRelationDTO);

	List<TableRelationDTO> listAll();

}
