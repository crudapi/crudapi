package cn.crudapi.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.crudapi.core.constant.ApiErrorCode;
import cn.crudapi.core.dto.ColumnDTO;
import cn.crudapi.core.dto.TableDTO;
import cn.crudapi.core.dto.TableRelationDTO;
import cn.crudapi.core.entity.ColumnEntity;
import cn.crudapi.core.entity.TableEntity;
import cn.crudapi.core.entity.TableRelationEntity;
import cn.crudapi.core.enumeration.OperatorTypeEnum;
import cn.crudapi.core.enumeration.TableRelationTypeEnum;
import cn.crudapi.core.exception.BusinessException;
import cn.crudapi.core.mapper.TableRelationMapper;
import cn.crudapi.core.query.CompositeCondition;
import cn.crudapi.core.query.Condition;
import cn.crudapi.core.query.LeafCondition;
import cn.crudapi.core.service.CrudService;
import cn.crudapi.core.service.TableMetadataService;
import cn.crudapi.core.service.TableRelationMetadataService;
import cn.crudapi.core.util.ConditionUtils;
import cn.crudapi.core.util.DateTimeUtils;

@Service
public class TableRelationMetadataServiceImpl implements TableRelationMetadataService {
	private static final Logger log = LoggerFactory.getLogger(TableRelationMetadataServiceImpl.class);
	
	private static final String TABLE_TABLE_NAME = "ca_meta_table";
	
	private static final String COLUMN_TABLE_NAME = "ca_meta_column";
	
	private static final String RELATION_TABLE_NAME = "ca_meta_table_relation";
	
	private static final String USER_TABLE_NAME = "user";
	private static final String COLUMN_ID = "id";
	private static final String COLUMN_CRAEAE_BY_ID = "createById";
	private static final String COLUMN_UPDATE_BY_ID = "updateById";
	private static final String COLUMN_OWNER_ID = "ownerId";
	
	private static Map<String, String> relationNameMap = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put(COLUMN_CRAEAE_BY_ID, "createBy");
			put(COLUMN_UPDATE_BY_ID, "updateBy");
			put(COLUMN_OWNER_ID, "owner");
		}
	};
	
	private static Map<String, String> relationCaptionMap = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put(COLUMN_CRAEAE_BY_ID, "创建者");
			put(COLUMN_UPDATE_BY_ID, "修改者");
			put(COLUMN_OWNER_ID, "所有者");
		}
	};
    
	@Autowired
    private CrudService crudService;
	  
	@Autowired
    private TableRelationMapper tableRelationMapper;

    @Autowired
    private TableMetadataService tableMetadataService;
    
    @Transactional
	@Override
	public void set(List<TableRelationDTO> tableRelationDTOList) {
		List<TableRelationEntity> oldTableRelationEntityList = crudService.list(RELATION_TABLE_NAME, TableRelationEntity.class);
		
		lazyFetch(oldTableRelationEntityList);
		 
		List<TableRelationEntity> deleteTableRelationEntityList = new ArrayList<TableRelationEntity>();

		// 1. delete
		for (TableRelationEntity tableRelationEntity : oldTableRelationEntityList) {
			if (!tableRelationDTOList.stream().anyMatch(t -> Objects.equals(t.getId(), tableRelationEntity.getId()))) {
				deleteTableRelationEntityList.add(tableRelationEntity);
			}
		}
		
		crudService.delete(RELATION_TABLE_NAME);
	
	    //2. update and add
	    List<TableRelationEntity> tableRelationEntityList = tableRelationMapper.toEntity(tableRelationDTOList);
		
	    List<Object> tableRelationObjList = new ArrayList<Object>();
	    tableRelationEntityList.stream().forEach(t -> tableRelationObjList.add(t));
        crudService.batchCreateObj(RELATION_TABLE_NAME, tableRelationObjList); 
	}
    
    @Override
	public Long create(TableRelationDTO tableRelationDTO) {
    	TableRelationEntity tableRelationEntity = tableRelationMapper.toEntity(tableRelationDTO);
    	
    	tableRelationEntity.setCreatedDate(DateTimeUtils.sqlTimestamp());
    	tableRelationEntity.setLastModifiedDate(DateTimeUtils.sqlTimestamp());
         
		Long id = crudService.create(RELATION_TABLE_NAME, tableRelationEntity);

        return id;
	}

	@Override
	public Long create(TableRelationTypeEnum relationType, 
						String name, 
						String caption,
						String fromTableName, 
						String fromColumnName, 
						String toTableName, 
						String toColumnName) {
		TableDTO oldFromTableDTO = tableMetadataService.get(fromTableName);
		
		ColumnDTO oldFromColumnDTO = oldFromTableDTO.getColumnDTOList().stream()
	        		.filter(t -> fromColumnName.equals(t.getName()))
	        		.findFirst().get();
		
		TableDTO oldToTableDTO = tableMetadataService.get(toTableName);
		ColumnDTO oldToColumnDTO = oldToTableDTO.getColumnDTOList().stream()
	        		.filter(t -> toColumnName.equals(t.getName()))
	        		.findFirst().get();
		
		TableRelationDTO tableRelationDTO = new TableRelationDTO();
		tableRelationDTO.setRelationType(relationType);
		tableRelationDTO.setName(name);
		tableRelationDTO.setCaption(caption);
		
		
		TableDTO fromTableDTO = new TableDTO();
		fromTableDTO.setId(oldFromTableDTO.getId());
		
		ColumnDTO fromColumnDTO = new ColumnDTO();
		fromColumnDTO.setId(oldFromColumnDTO.getId());
		
		TableDTO toTableDTO = new TableDTO();
		toTableDTO.setId(oldToTableDTO.getId());
		
		ColumnDTO toColumnDTO = new ColumnDTO();
		toColumnDTO.setId(oldToColumnDTO.getId());
		
		tableRelationDTO.setFromTableDTO(fromTableDTO);
		tableRelationDTO.setFromColumnDTO(fromColumnDTO);
		tableRelationDTO.setToTableDTO(toTableDTO);
		tableRelationDTO.setToColumnDTO(toColumnDTO);
		
		TableRelationEntity tableRelationEntity = tableRelationMapper.toEntity(tableRelationDTO);
		tableRelationEntity.setCreatedDate(DateTimeUtils.sqlTimestamp());
    	tableRelationEntity.setLastModifiedDate(DateTimeUtils.sqlTimestamp());
         
    	
		Long id = crudService.create(RELATION_TABLE_NAME, tableRelationEntity);
		
		return id;
	}
	
    @Override
    @CacheEvict(value = "tableRelationMetadata", allEntries= true)
    public void update(Long id, TableRelationDTO tableRelationDTO) {
    	TableRelationEntity tableRelationEntity = tableRelationMapper.toEntity(tableRelationDTO);
    	tableRelationEntity.setId(id);
    	tableRelationEntity.setLastModifiedDate(DateTimeUtils.sqlTimestamp());

    	crudService.patch(RELATION_TABLE_NAME, id, tableRelationEntity);
    }
	
	@Override
	@Cacheable(value = "tableRelationMetadata", key="#id")
	public TableRelationDTO get(Long id) {
		TableRelationEntity tableRelationEntity = getTableRelationEntity(id);
		
        return tableRelationMapper.toDTO(tableRelationEntity);
	}
	
	@Override
	@Cacheable(value = "tableRelationMetadata", key="#fromTableId")
	public List<TableRelationDTO> getFromTable(Long fromTableId) {
		List<TableRelationEntity> tableRelationEntityList = getTableRelationEntityList(fromTableId);
		
        return tableRelationMapper.toDTO(tableRelationEntityList);
	}
	

	@Override
	@Cacheable(value = "tableRelationMetadata", key="#fromTableName")
	public List<TableRelationDTO> getFromTable(String fromTableName) {
		TableDTO tableDTO = tableMetadataService.get(fromTableName);
 	    
 	    List<TableRelationEntity> tableRelationEntityList = getTableRelationEntityList(tableDTO.getId());
	    
 	 
 	    return tableRelationMapper.toDTO(tableRelationEntityList);
	}


	@Override
	@CacheEvict(value = "tableRelationMetadata", allEntries= true)
	public void deleteByFromTable(Long fromTableId) {
		LeafCondition cond = new LeafCondition();
 	    cond.setColumnName("fromTableId");
 	    cond.setValue(fromTableId);
 	    cond.setOperatorType(OperatorTypeEnum.EQ);
 	    
    	crudService.delete(RELATION_TABLE_NAME, cond);
	}
	
	@Override
	@CacheEvict(value = "tableRelationMetadata", allEntries= true)
	public void deleteByToTable(Long toTableId) {
		LeafCondition cond = new LeafCondition();
 	    cond.setColumnName("toTableId");
 	    cond.setValue(toTableId);
 	    cond.setOperatorType(OperatorTypeEnum.EQ);
 	    
 	    crudService.delete(RELATION_TABLE_NAME, cond);
	}

    @Override
    @CacheEvict(value = "tableRelationMetadata", allEntries= true)
    public void delete(Long id) {
    	crudService.delete(RELATION_TABLE_NAME, id);
    }
    
    @Override
	@CacheEvict(value = "tableRelationMetadata", allEntries= true)
	public void delete(List<Long> ids) {
		List<Object> valueList = new ArrayList<Object>();
		ids.stream().forEach(t -> {
			valueList.add(t);
		});
		
		Condition cond = ConditionUtils.toCondition("id", valueList);
		
		crudService.delete(RELATION_TABLE_NAME, cond);
	}

    @Override
    @CacheEvict(value = "tableRelationMetadata", allEntries= true)
    public void deleteAll() {
    	crudService.delete(RELATION_TABLE_NAME);
    }
    
	@Override
	public Long count(String filter, String search, Condition condition) {
		Condition newCond = convertConditon(filter, search, condition);

		return crudService.count(RELATION_TABLE_NAME, newCond);
	}
	
	@Override
	public List<TableRelationDTO> list(String filter, String search, Condition condition, Integer offset, Integer limit,
			String orderby) {
		Condition newCond = convertConditon(filter, search, condition);
		
		return this.list(newCond, null, offset, limit);
	}
	
	@Override
	public List<TableRelationDTO> listAll() {
	    return this.list(null, null, null, null);
	}
	
	@Override
	public List<TableRelationDTO> list(List<Long> tableIds) {
		if (tableIds == null || tableIds.size() == 0) {
			return new ArrayList<TableRelationDTO>();
		}
		
		List<Object> valueList = new ArrayList<Object>();
		tableIds.stream().forEach(t -> {
			valueList.add(t);
		});
		
		Condition fromTableCond = ConditionUtils.toCondition("fromTableId", valueList);
		Condition toTableCond = ConditionUtils.toCondition("toTableId", valueList);
		
		Condition cond = ConditionUtils.toCondition(fromTableCond, toTableCond);
		
	    return this.list(cond, null, null, null);
	}

	private List<TableRelationDTO> list(Condition condition, String orderby, Integer offset, Integer limit) {
		String sqlQuotation = crudService.getSqlQuotation();
    String idOrderby = sqlQuotation + "id" + sqlQuotation +  " DESC";
        
		List<TableRelationEntity> tableRelationEntityList = 
				crudService.list(RELATION_TABLE_NAME, condition, orderby == null ? idOrderby: orderby, offset, limit, TableRelationEntity.class);
 	    
		lazyFetch(tableRelationEntityList);
	 	    
	    return tableRelationMapper.toDTO(tableRelationEntityList);
	}
	
    private TableRelationEntity getTableRelationEntity(Long id) {
   	    TableRelationEntity tableRelationEntity = crudService.get(RELATION_TABLE_NAME, id, TableRelationEntity.class);
   	    
   	    lazyFetch(tableRelationEntity);
   	    
   	    return tableRelationEntity;
  	}
      
    
    private List<TableRelationEntity> getTableRelationEntityList(Long fromTableId) {
		LeafCondition cond = new LeafCondition();
 	    cond.setColumnName("fromTableId");
 	    cond.setValue(fromTableId);
 	    cond.setOperatorType(OperatorTypeEnum.EQ);
 	    
 	    List<TableRelationEntity> tableRelationEntityList = 
 	    		crudService.list(RELATION_TABLE_NAME, cond, null, null, null, TableRelationEntity.class);
 	    
 	    //内置用户字段关联
    	TableDTO userTableDTO = tableMetadataService.get(USER_TABLE_NAME);
    	TableDTO fromTableDTO = tableMetadataService.get(fromTableId);
    	
    	ColumnDTO userIdColumnDTO = userTableDTO.getColumn(COLUMN_ID);
    	for (ColumnDTO columnDTO : fromTableDTO.getColumnDTOList()) {
    		String columnName = columnDTO.getName();
    		String relationName = relationNameMap.get(columnName);
    		String relationCaption = relationCaptionMap.get(columnName);
    		
    		if (columnName.equals(COLUMN_CRAEAE_BY_ID)
    			|| columnName.equals(COLUMN_UPDATE_BY_ID)
    			|| columnName.equals(COLUMN_OWNER_ID)) {
    			TableRelationEntity tableRelationEntity = new TableRelationEntity();
    			tableRelationEntity.setName(relationName);
    			tableRelationEntity.setCaption(relationCaption);
    			tableRelationEntity.setFromTableId(fromTableId);
    			tableRelationEntity.setFromColumnId(columnDTO.getId());
    			tableRelationEntity.setToTableId(userTableDTO.getId());
    			tableRelationEntity.setToColumnId(userIdColumnDTO.getId());
    			tableRelationEntity.setRelationType(TableRelationTypeEnum.ManyToOne);
    			
    			tableRelationEntityList.add(tableRelationEntity);
    		}
    	}
    	
    	
 	    lazyFetch(tableRelationEntityList);
 	    
 	    return tableRelationEntityList;
	}
    
    private void lazyFetch(List<TableRelationEntity> tableRelationEntityList) {
		for (TableRelationEntity tableRelationEntity : tableRelationEntityList) {
			lazyFetch(tableRelationEntity);
 	    }
	}
    
    private void lazyFetch(TableRelationEntity tableRelationEntity) {
    	TableEntity fromTableEntity = crudService.get(TABLE_TABLE_NAME, tableRelationEntity.getFromTableId(),  TableEntity.class);
    	TableEntity toTableEntity = crudService.get(TABLE_TABLE_NAME, tableRelationEntity.getToTableId(), TableEntity.class);
    	ColumnEntity fromColumnEntity = crudService.get(COLUMN_TABLE_NAME, tableRelationEntity.getFromColumnId(), ColumnEntity.class);
    	ColumnEntity toColumnEntity = crudService.get(COLUMN_TABLE_NAME, tableRelationEntity.getToColumnId(), ColumnEntity.class);
    	
    	tableRelationEntity.setFromTableEntity(fromTableEntity);
    	tableRelationEntity.setFromColumnEntity(fromColumnEntity);
    	tableRelationEntity.setToTableEntity(toTableEntity);
    	tableRelationEntity.setToColumnEntity(toColumnEntity);
  	}

    private Condition convertConditon(String filter, String search, Condition condition) {
    	Condition newCond = null;

    	try {
    		//1. filter
	    	Condition filterCond = ConditionUtils.toCondition(filter);

	    	//2. search
	    	LeafCondition searchCond = null;
	    	if (!StringUtils.isEmpty(search)) {
	    		searchCond = new LeafCondition();
	        	searchCond.setColumnName("name");
	        	searchCond.setOperatorType(OperatorTypeEnum.LIKE);
	        	searchCond.addValue(search);
			}
	    	
	    	//3. other condition
	    	if (filterCond == null && searchCond == null && condition == null) {
	    		newCond = null;
	    	} else {
	    		CompositeCondition compositeCondition = new CompositeCondition();
	    		compositeCondition.add(filterCond);
	    		compositeCondition.add(condition);
	    		compositeCondition.add(searchCond);
	    		
	    		newCond = compositeCondition;

	    		log.info(newCond.toString());
	    		log.info(newCond.toQuerySql());
	    	}
    	} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, e.getMessage());
		}

    	return newCond;
    }
}
