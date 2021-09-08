package cn.crudapi.core.service;

import java.util.List;

import cn.crudapi.core.dto.SequenceDTO;
import cn.crudapi.core.query.Condition;

public interface SequenceMetadataService {
    Long create(SequenceDTO sequenceDTO);

    void update(Long id, SequenceDTO sequenceDTO);

    List<SequenceDTO> list(String filter, String search, Condition condition, Integer offset, Integer limit, String orderby);
    
    Long count(String filter, String search, Condition condition);

    SequenceDTO get(Long id);

    SequenceDTO get(String name);

    void deleteAll();

	void delete(Long id);
	
	void delete(List<Long> ids);

	List<SequenceDTO> listAll();
	
	List<SequenceDTO> list(List<Long> ids);
}
