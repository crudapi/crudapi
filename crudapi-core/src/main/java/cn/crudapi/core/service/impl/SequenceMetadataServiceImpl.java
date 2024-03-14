package cn.crudapi.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cn.crudapi.core.constant.ApiErrorCode;
import cn.crudapi.core.dto.SequenceDTO;
import cn.crudapi.core.entity.SequenceEntity;
import cn.crudapi.core.enumeration.OperatorTypeEnum;
import cn.crudapi.core.exception.BusinessException;
import cn.crudapi.core.mapper.SequenceMapper;
import cn.crudapi.core.query.CompositeCondition;
import cn.crudapi.core.query.Condition;
import cn.crudapi.core.query.LeafCondition;
import cn.crudapi.core.service.CrudService;
import cn.crudapi.core.service.SequenceMetadataService;
import cn.crudapi.core.util.ConditionUtils;
import cn.crudapi.core.util.DateTimeUtils;


@Service
public class SequenceMetadataServiceImpl implements SequenceMetadataService {
	private static final Logger log = LoggerFactory.getLogger(SequenceMetadataServiceImpl.class);
	
	private static final String SEQUENCE_TABLE_NAME = "ca_meta_sequence";
	
    @Autowired
    private CrudService crudService;
    
    @Autowired
    private SequenceMapper sequenceMapper;

    @Override
    public Long create(SequenceDTO sequenceDTO) {
        SequenceEntity sequenceEntity = sequenceMapper.toEntity(sequenceDTO);
        
        sequenceEntity.setCreatedDate(DateTimeUtils.sqlTimestamp());
        sequenceEntity.setLastModifiedDate(DateTimeUtils.sqlTimestamp());
        
        Long id = crudService.create(SEQUENCE_TABLE_NAME, sequenceEntity);

        return id;
    }

    @Override
    @CacheEvict(value = "sequenceMetadata", allEntries= true)
    public void update(Long sequenceId, SequenceDTO sequenceDTO) {
        SequenceEntity sequenceEntity = sequenceMapper.toEntity(sequenceDTO);
        sequenceEntity.setId(sequenceId);
        sequenceEntity.setLastModifiedDate(DateTimeUtils.sqlTimestamp());

        crudService.patch(SEQUENCE_TABLE_NAME, sequenceId, sequenceEntity);
    }

	@Override
	@Cacheable(value = "sequenceMetadata", key="#id")
	public SequenceDTO get(Long id) {
		SequenceEntity sequenceEntity = crudService.get(SEQUENCE_TABLE_NAME, id, SequenceEntity.class);
		return sequenceMapper.toDTO(sequenceEntity);
	}

	@Override
	@Cacheable(value = "sequenceMetadata", key="#name")
	public SequenceDTO get(String name) {
		Map<String, Object> keyMap = new HashMap<String, Object>();
		keyMap.put("name", name);
		SequenceEntity sequenceEntity = crudService.get(SEQUENCE_TABLE_NAME, keyMap, SequenceEntity.class);
		return sequenceMapper.toDTO(sequenceEntity);
	}

	@Override
    @CacheEvict(value = "sequenceMetadata", allEntries= true)
	public void deleteAll() {
		crudService.delete(SEQUENCE_TABLE_NAME);
	}

	@Override
    @CacheEvict(value = "sequenceMetadata", key="#id")
	public void delete(Long id) {
		crudService.delete(SEQUENCE_TABLE_NAME, id);
	}
	
	@Override
	@CacheEvict(value = "sequenceMetadata", allEntries= true)
	public void delete(List<Long> ids) {
		List<Object> valueList = new ArrayList<Object>();
		ids.stream().forEach(t -> {
			valueList.add(t);
		});
		
		Condition cond = ConditionUtils.toCondition("id", valueList);
		crudService.delete(SEQUENCE_TABLE_NAME, cond);
	}

	@Override
	public Long count(String filter, String search, Condition condition) {
		Condition newCond = convertConditon(filter, search, condition);

		return crudService.count(SEQUENCE_TABLE_NAME, newCond);
	}
	
	@Override
	public List<SequenceDTO> list(String filter, String search, Condition condition, Integer offset, Integer limit, String orderby){
		Condition newCond = convertConditon(filter, search, condition);

		return this.list(newCond, orderby, offset, limit);
	}
	
	@Override
	public List<SequenceDTO> listAll() {
		return this.list(null, null, null, null);
	}

	@Override
	public List<SequenceDTO> list(List<Long> ids) {
		if (ids == null || ids.size() == 0) {
			return new ArrayList<SequenceDTO>();
		}
		
		List<Object> valueList = new ArrayList<Object>();
		ids.stream().forEach(t -> {
			valueList.add(t);
		});
		
		Condition cond = ConditionUtils.toCondition("id", valueList);
		return this.list(cond, null, null, null);
	}
	
	private List<SequenceDTO> list(Condition condition, String orderby, Integer offset, Integer limit){
		String sqlQuotation = crudService.getSqlQuotation();
	    String idOrderby = sqlQuotation + "id" + sqlQuotation +  " DESC";
	    
		List<SequenceEntity> sequenceEntityList = crudService.list(SEQUENCE_TABLE_NAME, condition, orderby == null ? idOrderby : orderby, offset, limit, SequenceEntity.class);
		return sequenceMapper.toDTO(sequenceEntityList);
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
