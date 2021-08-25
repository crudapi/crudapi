package cn.crudapi.core.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import cn.crudapi.core.constant.ApiErrorCode;
import cn.crudapi.core.entity.BaseEntity;
import cn.crudapi.core.exception.BusinessException;
import cn.crudapi.core.query.Condition;
import cn.crudapi.core.util.ConditionUtils;
import cn.crudapi.core.util.DbUtils;


public class CaMetadataRepository<T extends BaseEntity> implements CaRepository<T> {
	private static final Logger log = LoggerFactory.getLogger(CaMetadataRepository.class);
	
	public static final String COLUMN_ID = "id";
	 
	@Autowired
    private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Override
	public Long insert(T entity) {
		List<String> columnNameList = entity.getColumnNames(false);
		List<Object> valueList = entity.getColumnValues(false);
		
		String sql = DbUtils.toInserSql(entity.getDataBaseTableName(), columnNameList, false);
        log.info(sql);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int row = jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                for (int index = 0; index < valueList.size(); ++index) {
                    ps.setObject(index + 1, valueList.get(index));
                }

                return ps;
            }

        }, keyHolder);

        if (row > 0) {
            return keyHolder.getKey().longValue();
        } else {
            return null;
        }
	}
	
	@Override
	public int[] batchInsert(List<T> entityList) {
		if (CollectionUtils.isEmpty(entityList)) {
			return new int[]{};
		}
		
    	T entity = entityList.get(0);
    	
    	String tableName = entity.getDataBaseTableName();
    	List<String> columnNameList = entity.getColumnNames(false);
    	
    	
    	List<List<Object>> valueListList = new ArrayList<List<Object>>();

        for (T item : entityList) {
        	List<Object> valueList = item.getColumnValues(false);
        	valueListList.add(valueList);
        }

        String sql = DbUtils.toInserSql(tableName, columnNameList, false);
        log.info(sql);
        
        
        int[] ret = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                List<Object> valueList = valueListList.get(i);
                
                log.info(valueList.toString());
                for (int index = 0; index < valueList.size(); ++index) {
                    ps.setObject(index + 1, valueList.get(index));
                }
            }

            @Override
            public int getBatchSize() {
                return valueListList.size();
            }
        });

        return ret;
    }
	
	@Override
	public int[] batchUpdate(List<T> entityList) {
		if (CollectionUtils.isEmpty(entityList)) {
			return new int[]{};
		}
		
    	T entity = entityList.get(0);
    	
    	String tableName = entity.getDataBaseTableName();
    	List<String> columnNameList = entity.getColumnNames(true);
    	
    	
    	List<List<Object>> valueListList = new ArrayList<List<Object>>();

        for (T item : entityList) {
        	List<Object> valueList = item.getColumnValues(true);
        	valueList.add(item.getRecId());
        	valueListList.add(valueList);
        }

        String sql = DbUtils.toUpdateSql(tableName, columnNameList);
        log.info(sql);
        
        sql += " WHERE ID = ?";
        
        
        int[] ret = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                List<Object> valueList = valueListList.get(i);
                
                log.info(valueList.toString());
                for (int index = 0; index < valueList.size(); ++index) {
                    ps.setObject(index + 1, valueList.get(index));
                }
            }

            @Override
            public int getBatchSize() {
                return valueListList.size();
            }
        });

        return ret;
    }


	@Override
	public void put(T entity) {
		List<String> columnNameList = entity.getColumnNames(true);
		List<Object> valueList = entity.getColumnValues(true);
		Long id = entity.getRecId();
		String sql = DbUtils.toUpdateSql(entity.getDataBaseTableName(), columnNameList);
        log.info(sql);
        
        Condition cond = ConditionUtils.toCondition(COLUMN_ID, id);
        sql += " WHERE " + cond.toQuerySql();
        valueList.addAll(cond.toQueryValues());
        
        
        int row = jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                int index = 0;
                for (index = 0; index < valueList.size(); ++index) {
                    ps.setObject(index + 1, valueList.get(index));
                }
            }
        });

        log.info("row = " + row);

        if (row == 0) {
            throw new BusinessException(ApiErrorCode.API_RESOURCE_NOT_FOUND, id);
        }
	}

	@Override
	public void patch(T entity) {
		List<String> columnNameList = entity.getColumnNamesIgnoreNull(true);
		List<Object> valueList = entity.getColumnValuesIgnoreNull(true);
		Long id = entity.getRecId();
		String sql = DbUtils.toUpdateSql(entity.getDataBaseTableName(), columnNameList);
        log.info(sql);
        
        Condition cond = ConditionUtils.toCondition(COLUMN_ID, id);
        sql += " WHERE " + cond.toQuerySql();
        valueList.addAll(cond.toQueryValues());
        
        int row = jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                int index = 0;
                for (index = 0; index < valueList.size(); ++index) {
                    ps.setObject(index + 1, valueList.get(index));
                }
            }
        });

        log.info("row = " + row);

        if (row == 0) {
            throw new BusinessException(ApiErrorCode.API_RESOURCE_NOT_FOUND, id);
        }
	}

	@Override
	public List<T> findAll(Class<T> mappedClass) {
		String tableName = getDataBaseTableName(mappedClass);
		String sql = "select * from `" + tableName + "`";
		log.info(sql);
		List<T> entityList = jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<T>(mappedClass));
		return entityList;
	}
	
	@Override
	public Long count(Condition cond, Class<T> mappedClass) {
		String tableName = getDataBaseTableName(mappedClass);
		
		List<Object> values = new ArrayList<Object>();
		String sql = "select count(*) from `" + tableName + "`";
		if (cond != null) {
        	sql += " WHERE " + cond.toQuerySql();
        	values = cond.toQueryValues();
        }

		log.info(sql);
		Long count =  jdbcTemplate.queryForObject(sql, values.toArray(), Long.class);
		return count;
	}
	
	@Override
	public List<T> find(Condition cond, Class<T> mappedClass) {
		String tableName = getDataBaseTableName(mappedClass);
		
		List<Object> values = new ArrayList<Object>();
		String sql = "select * from `" + tableName + "`";
		if (cond != null) {
        	sql += " WHERE " + cond.toQuerySql();
        	values = cond.toQueryValues();
        }

		log.info(sql);
		List<T> entityList = jdbcTemplate.query(sql, values.toArray(), new BeanPropertyRowMapper<T>(mappedClass));
		return entityList;
	}
	
	@Override
	public List<T> find(Condition cond, String orderby, Class<T> mappedClass) {
		String tableName = getDataBaseTableName(mappedClass);
		
		List<Object> values = new ArrayList<Object>();
		String sql = "select * from `" + tableName + "`";
		if (cond != null) {
        	sql += " WHERE " + cond.toQuerySql();
        	values = cond.toQueryValues();
        }
		
		if (StringUtils.isEmpty(orderby)) {
	    	sql += " ORDER BY id DESC";
	    } else {
	    	sql += " ORDER BY " + orderby;
	    }

		log.info(sql);
		List<T> entityList = jdbcTemplate.query(sql, values.toArray(), new BeanPropertyRowMapper<T>(mappedClass));
		return entityList;
	}
	

	@Override
	public List<T> find(Condition cond, Integer offset, Integer limit, String orderby, Class<T> mappedClass) {
		String tableName = getDataBaseTableName(mappedClass);
		
		List<Object> values = new ArrayList<Object>();
		String sql = "select * from `" + tableName + "`";
		if (cond != null) {
        	sql += " WHERE " + cond.toQuerySql();
        	values = cond.toQueryValues();
        }
		
		if (StringUtils.isEmpty(orderby)) {
        	sql += " ORDER BY id DESC LIMIT ?, ?";
        } else {
        	sql += " ORDER BY " + orderby + " LIMIT ?, ?";
        }

        if (offset == null) {
        	offset = 0;
        }

        if (limit == null) {
        	limit = 10;
        }

        values.add(offset);
        values.add(limit);

		log.info(sql);
		List<T> entityList = jdbcTemplate.query(sql, values.toArray(), new BeanPropertyRowMapper<T>(mappedClass));
		return entityList;
	}
	
	@Override
	public T getOne(Long id, Class<T> mappedClass) {
		return getOne( ConditionUtils.toCondition(COLUMN_ID, id), mappedClass);
	}
	
	@Override
	public T getBasicOne(Long id, Class<T> mappedClass) {
		String tableName = getDataBaseTableName(mappedClass);
		
		List<String> selectColumnNameList = new ArrayList<>();
		selectColumnNameList.add(COLUMN_ID);
		selectColumnNameList.add("name");
		selectColumnNameList.add("caption");
		
		String sql = DbUtils.toSelectSql(tableName, selectColumnNameList);
        log.info(sql);
        
        Condition cond = ConditionUtils.toCondition(COLUMN_ID, id);
        sql += " WHERE " + cond.toQuerySql();
    	log.info(sql);

    	List<Object> values = cond.toQueryValues();
		
		List<T> entityList = jdbcTemplate.query(sql, values.toArray(), new BeanPropertyRowMapper<T>(mappedClass));
		if (CollectionUtils.isEmpty(entityList)) {
			return null;
		}
		return entityList.get(0);
	}

	@Override
	public T getOne(String name, Class<T> mappedClass) {
		String tableName = getDataBaseTableName(mappedClass);
		String sql = "select * from `" + tableName + "` WHERE lower(`name`) = ?";
        log.info(sql);
        
		List<Object> values = new ArrayList<Object>();
		values.add(name.toLowerCase());
		
		List<T> entityList = jdbcTemplate.query(sql, values.toArray(), new BeanPropertyRowMapper<T>(mappedClass));
		if (CollectionUtils.isEmpty(entityList)) {
			return null;
		}
		return entityList.get(0);
	}
	
	@Override
	public T getOne(Condition cond, Class<T> mappedClass) {
		String tableName = getDataBaseTableName(mappedClass);
		
		List<Object> values = new ArrayList<Object>();
		String sql = "select * from `" + tableName + "`";
		if (cond != null) {
        	sql += " WHERE " + cond.toQuerySql();
        	values = cond.toQueryValues();
        }

        log.info(sql);
		
        List<T> entityList = jdbcTemplate.query(sql, values.toArray(), new BeanPropertyRowMapper<T>(mappedClass));
		if (CollectionUtils.isEmpty(entityList)) {
			return null;
		}
		return entityList.get(0);
	}

	@Override
	public void deleteById(Long id, Class<T> mappedClass) {
        deleteByCondition(ConditionUtils.toCondition(COLUMN_ID, id), mappedClass);
	}
	
	@Override
	public void deleteByCondition(Condition cond, Class<T> mappedClass) {
		String tableName = getDataBaseTableName(mappedClass);
		
		List<Object> values = new ArrayList<Object>();
		String sql = "DELETE FROM `" + tableName  +"`";
		if (cond != null) {
        	sql += " WHERE " + cond.toQuerySql();
        	values = cond.toQueryValues();
        }

        log.info(sql);

        jdbcTemplate.update(sql, values.toArray());
	}
	
	@Override
	public void deleteAll(Class<T> mappedClass) {
		deleteByCondition(null, mappedClass);
	}
	
	private String getDataBaseTableName(Class<T> mappedClass) {
		T entity = newInstance(mappedClass);
		
		return entity.getDataBaseTableName();
	}
	
	private T newInstance(Class<T> mappedClass) {
		T entity = null;
		
		try {
			entity = mappedClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return entity;
	}
}
