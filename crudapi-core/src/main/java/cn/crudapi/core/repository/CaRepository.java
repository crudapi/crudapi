package cn.crudapi.core.repository;

import java.util.List;

import cn.crudapi.core.entity.BaseEntity;
import cn.crudapi.core.query.Condition;

public interface CaRepository<T extends BaseEntity> {
	Long insert(T entity);
	
	int[] batchInsert(List<T> entityList);

	int[] batchUpdate(List<T> entityList);
	
	void put(T entity);
	
	void patch(T entity);
	
	List<T> find(Condition cond, Class<T> mappedClass);
	
	List<T> find(Condition cond, String orderby, Class<T> mappedClass);
	
	List<T> find(Condition cond, Integer offset, Integer limit, String orderby, Class<T> mappedClass);

	List<T> findAll(Class<T> mappedClass);

	Long count(Condition cond, Class<T> mappedClass);

	T getOne(Long id, Class<T> mappedClass);

	T getBasicOne(Long id, Class<T> mappedClass);
	
	T getOne(String name, Class<T> mappedClass);
	
	T getOne(Condition cond, Class<T> mappedClass);
	
	void deleteById(Long id, Class<T> mappedClass);

	void deleteByCondition(Condition cond, Class<T> mappedClass);
	
	void deleteAll(Class<T> mappedClass);
}
