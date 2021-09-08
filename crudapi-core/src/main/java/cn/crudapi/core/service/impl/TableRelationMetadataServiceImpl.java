package cn.crudapi.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.crudapi.core.constant.ApiErrorCode;
import cn.crudapi.core.dto.ColumnDTO;
import cn.crudapi.core.dto.SequenceDTO;
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
import cn.crudapi.core.repository.ColumnMetadataRepository;
import cn.crudapi.core.repository.TableMetadataRepository;
import cn.crudapi.core.repository.TableRelationMetadataRepository;
import cn.crudapi.core.service.TableMetadataService;
import cn.crudapi.core.service.TableRelationMetadataService;
import cn.crudapi.core.util.ConditionUtils;
import cn.crudapi.core.util.DateTimeUtils;


@Service
public class TableRelationMetadataServiceImpl implements TableRelationMetadataService {
	private static final Logger log = LoggerFactory.getLogger(TableRelationMetadataServiceImpl.class);
	
	@Autowired
    private TableRelationMapper tableRelationMapper;

    @Autowired
    private TableRelationMetadataRepository tableRelationMetadataRepository;

    @Autowired
    private TableMetadataRepository tableMetadataRepository;

    @Autowired
    private ColumnMetadataRepository columnMetadataRepository;

    @Autowired
    private TableMetadataService tableMetadataService;
    
    @Transactional
	@Override
	public void set(List<TableRelationDTO> tableRelationDTOList) {
		List<TableRelationEntity> oldTableRelationEntityList = tableRelationMetadataRepository.findAll(TableRelationEntity.class);
		lazyFetch(oldTableRelationEntityList);
		 
		List<TableRelationEntity> deleteTableRelationEntityList = new ArrayList<TableRelationEntity>();

		// 1. delete
		for (TableRelationEntity tableRelationEntity : oldTableRelationEntityList) {
			if (!tableRelationDTOList.stream().anyMatch(t -> Objects.equals(t.getId(), tableRelationEntity.getId()))) {
				deleteTableRelationEntityList.add(tableRelationEntity);
			}
		}
		tableRelationMetadataRepository.deleteAll(TableRelationEntity.class);

		//2. update and add
		List<TableRelationEntity> tableRelationEntityList = tableRelationMapper.toEntity(tableRelationDTOList);
		tableRelationMetadataRepository.batchInsert(tableRelationEntityList);
	}
    
    @Override
	public Long create(TableRelationDTO tableRelationDTO) {
    	TableRelationEntity tableRelationEntity = tableRelationMapper.toEntity(tableRelationDTO);
    	
    	tableRelationEntity.setCreatedDate(DateTimeUtils.sqlTimestamp());
    	tableRelationEntity.setLastModifiedDate(DateTimeUtils.sqlTimestamp());
         
		Long id = tableRelationMetadataRepository.insert(tableRelationEntity);

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
         
    	
		Long id = tableRelationMetadataRepository.insert(tableRelationEntity);
		
		return id;
	}
	
    @Override
    @CacheEvict(value = "tableRelationMetadata", key="#id")
    public void update(Long id, TableRelationDTO tableRelationDTO) {
    	TableRelationEntity tableRelationEntity = tableRelationMapper.toEntity(tableRelationDTO);
    	tableRelationEntity.setId(id);
    	tableRelationEntity.setLastModifiedDate(DateTimeUtils.sqlTimestamp());

    	tableRelationMetadataRepository.patch(tableRelationEntity);
    }
	
	@Override
	@Cacheable(value = "tableRelationMetadata", key="#id")
	public TableRelationDTO get(Long id) {
		TableRelationEntity tableRelationEntity = getTableRelationEntity(id);
		
        return tableRelationMapper.toDTO(tableRelationEntity);
	}
	
	@Override
	//@Cacheable(value = "tableRelationMetadata", key="#fromTableId")
	public List<TableRelationDTO> getFromTable(Long fromTableId) {
		List<TableRelationEntity> tableRelationEntityList = getTableRelationEntityList(fromTableId);
		
        return tableRelationMapper.toDTO(tableRelationEntityList);
	}
	

	@Override
	//@Cacheable(value = "tableRelationMetadata", key="#fromTableName")
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
    	tableRelationMetadataRepository.deleteByCondition(cond, TableRelationEntity.class);
		
	}
	
	@Override
	@CacheEvict(value = "tableRelationMetadata", allEntries= true)
	public void deleteByToTable(Long toTableId) {
		LeafCondition cond = new LeafCondition();
 	    cond.setColumnName("toTableId");
 	    cond.setValue(toTableId);
 	    cond.setOperatorType(OperatorTypeEnum.EQ);
    	tableRelationMetadataRepository.deleteByCondition(cond, TableRelationEntity.class);
	}

    @Override
    public void delete(Long id) {
    	tableRelationMetadataRepository.deleteById(id, TableRelationEntity.class);
    }
    
    @Override
	@CacheEvict(value = "tableRelationMetadata", allEntries= true)
	public void delete(List<Long> ids) {
		List<Object> valueList = new ArrayList<Object>();
		ids.stream().forEach(t -> {
			valueList.add(t);
		});
		
		Condition cond = ConditionUtils.toCondition("id", valueList);
		tableRelationMetadataRepository.deleteByCondition(cond, TableRelationEntity.class);
	}

    @Override
    @CacheEvict(value = "tableRelationMetadata", allEntries= true)
    public void deleteAll() {
    	tableRelationMetadataRepository.deleteAll(TableRelationEntity.class);
    }
    
    private TableRelationEntity getTableRelationEntity(Long id) {
   	    TableRelationEntity tableRelationEntity = tableRelationMetadataRepository.getOne(id, TableRelationEntity.class);
   	    lazyFetch(tableRelationEntity);
   	    
   	    return tableRelationEntity;
  	}
      
    
    private List<TableRelationEntity> getTableRelationEntityList(Long fromTableId) {
		LeafCondition cond = new LeafCondition();
 	    cond.setColumnName("fromTableId");
 	    cond.setValue(fromTableId);
 	    cond.setOperatorType(OperatorTypeEnum.EQ);
 	    
 	    List<TableRelationEntity> tableRelationEntityList = tableRelationMetadataRepository.find(cond, TableRelationEntity.class);
 	    lazyFetch(tableRelationEntityList);
 	    
 	    return tableRelationEntityList;
	}
    
    private void lazyFetch(List<TableRelationEntity> tableRelationEntityList) {
		for (TableRelationEntity tableRelationEntity : tableRelationEntityList) {
			lazyFetch(tableRelationEntity);
 	    }
	}
    
    private void lazyFetch(TableRelationEntity tableRelationEntity) {
    	TableEntity fromTableEntity = tableMetadataRepository.getBasicOne(tableRelationEntity.getFromTableId(),  TableEntity.class);
    	TableEntity toTableEntity = tableMetadataRepository.getBasicOne(tableRelationEntity.getToTableId(), TableEntity.class);
    	ColumnEntity fromColumnEntity = columnMetadataRepository.getBasicOne(tableRelationEntity.getFromColumnId(), ColumnEntity.class);
    	ColumnEntity toColumnEntity = columnMetadataRepository.getBasicOne(tableRelationEntity.getToColumnId(), ColumnEntity.class);
    	tableRelationEntity.setFromTableEntity(fromTableEntity);
    	tableRelationEntity.setFromColumnEntity(fromColumnEntity);
    	tableRelationEntity.setToTableEntity(toTableEntity);
    	tableRelationEntity.setToColumnEntity(toColumnEntity);
  	}


	@Override
	public List<TableRelationDTO> list(String filter, String search, Condition condition, Integer offset, Integer limit,
			String orderby) {
		Condition newCond = convertConditon(filter, search, condition);
		
		List<TableRelationEntity> tableRelationEntityList = tableRelationMetadataRepository.find(newCond, offset, limit, orderby, TableRelationEntity.class);
	    lazyFetch(tableRelationEntityList);
	 	    
	    return tableRelationMapper.toDTO(tableRelationEntityList);
	}
	
	@Override
	public List<TableRelationDTO> listAll() {
		List<TableRelationEntity> tableRelationEntityList = tableRelationMetadataRepository.findAll(TableRelationEntity.class);
	    lazyFetch(tableRelationEntityList);
	 	    
	    return tableRelationMapper.toDTO(tableRelationEntityList);
	}

	@Override
	public Long count(String filter, String search, Condition condition) {
		Condition newCond = convertConditon(filter, search, condition);

		return tableRelationMetadataRepository.count(newCond, TableRelationEntity.class);
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
		
		List<TableRelationEntity> tableRelationEntityList = tableRelationMetadataRepository.find(cond, TableRelationEntity.class);
	    lazyFetch(tableRelationEntityList);
	 	    
	    return tableRelationMapper.toDTO(tableRelationEntityList);
	}
}
