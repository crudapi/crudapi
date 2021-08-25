package cn.crudapi.core.service;

import java.util.List;

public interface SequenceService {
    Object getNextValue(String sequenceName);

	Object getNextValue(Long sequenceId);

	List<Object> getNextValues(Long sequenceId, int size);
}
