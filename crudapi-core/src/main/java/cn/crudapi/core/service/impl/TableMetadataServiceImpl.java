package cn.crudapi.core.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.crudapi.core.constant.ApiErrorCode;
import cn.crudapi.core.constant.MetaDataConfig;
import cn.crudapi.core.dto.TableDTO;
import cn.crudapi.core.entity.ColumnEntity;
import cn.crudapi.core.entity.IndexEntity;
import cn.crudapi.core.entity.IndexLineEntity;
import cn.crudapi.core.entity.TableEntity;
import cn.crudapi.core.enumeration.ConditionTypeEnum;
import cn.crudapi.core.enumeration.OperatorTypeEnum;
import cn.crudapi.core.exception.BusinessException;
import cn.crudapi.core.mapper.TableMapper;
import cn.crudapi.core.model.TableSql;
import cn.crudapi.core.query.CompositeCondition;
import cn.crudapi.core.query.Condition;
import cn.crudapi.core.query.LeafCondition;
import cn.crudapi.core.service.CrudService;
import cn.crudapi.core.service.TableMetadataService;
import cn.crudapi.core.util.ConditionUtils;
import cn.crudapi.core.util.DateTimeUtils;

@Service
public class TableMetadataServiceImpl implements TableMetadataService {
	private static final Logger log = LoggerFactory.getLogger(TableMetadataServiceImpl.class);
   
	private static final String TABLE_TABLE_NAME = "ca_meta_table";
	private static final String COLUMN_TABLE_NAME = "ca_meta_column";
	private static final String INDEX_TABLE_NAME = "ca_meta_index";
	private static final String INDEX_LINE_TABLE_NAME = "ca_meta_index_line";
	private static final String RELATION_TABLE_NAME = "ca_meta_table_relation";
	
	@Autowired
    private CrudService crudService;
	    
    @Autowired
    private TableMapper tableMapper;

	@Override
	public List<Map<String, Object>> getMetaDatas() {
		return crudService.getMetaDatas();
	}
	
	@Override
	public Map<String, Object> getMetaData(String tableName) {
		return crudService.getMetaData(tableName);
	}
	
	@Override
	public void checkTable() {
    	Long tableCount = count(null, null, null);
    	
    	if (tableCount >= MetaDataConfig.MAX_TABLE_COUNT) {
    		throw new BusinessException(ApiErrorCode.TABLE_COUNT_ERROR, "表个数不能大于" + MetaDataConfig.MAX_TABLE_COUNT);
    	}
	}
    
    @Transactional
    @Override
    public Long create(TableDTO tableDTO) {
    	checkTable();
    	
    	if (!Boolean.TRUE.equals(tableDTO.getReverse()) && isExist(tableDTO.getTableName())) {
    		throw new BusinessException(ApiErrorCode.DUPLICATE_TABLE_NAME, tableDTO.getTableName() + "表已经存在");
    	}
        TableEntity tableEntity = tableMapper.toEntity(tableDTO);
        
        tableEntity.setCreatedDate(DateTimeUtils.sqlTimestamp());
        tableEntity.setLastModifiedDate(DateTimeUtils.sqlTimestamp());
        
        tableMapper.check(tableEntity);

        return insert(tableEntity);
    }

    @Override
    //@CacheEvict(value = "tableMetadata", key="#id")
    public void update(Long id, TableDTO tableDTO) {
    	tableDTO.setId(id);
    	TableEntity tableEntity = getTableEntityIncludeChildren(id);
       
        if (tableEntity.getCreatePhysicalTable()) {
        	if (tableDTO.getColumnDTOList() != null 
        		&& tableDTO.getColumnDTOList().size() == 0) {
        		throw new BusinessException(ApiErrorCode.COLUMN_NOT_EMPTY, "表至少包括一个字段！");
        	}
        	
            TableSql tableSql = tableMapper.toEntityIgnoreNull(tableEntity, tableDTO);
            tableEntity.setLastModifiedDate(DateTimeUtils.sqlTimestamp());
            tableMapper.check(tableEntity);
            patch(tableEntity, tableSql.getColumnSql().getDeleteColumnIdList(), tableSql.getIndexSql().getDeleteIndexIdList());
            
            for (String sql : tableSql.getSqlList()) {
                execute(sql);
            }

            for (String sql : tableSql.getColumnSql().getDeleteSqlList()) {
                execute(sql);
            }

            for (String sql : tableSql.getColumnSql().getUpdateSqlList()) {
                execute(sql);
            }

            for (String sql : tableSql.getColumnSql().getAddSqlList()) {
                execute(sql);
            }

            for (String sql : tableSql.getIndexSql().getDeleteSqlList()) {
                execute(sql);
            }

            for (String sql : tableSql.getIndexSql().getUpdateSqlList()) {
                execute(sql);
            }

            for (String sql : tableSql.getIndexSql().getAddSqlList()) {
                execute(sql);
            }
        } else {
        	TableEntity newTableEntity = tableMapper.toEntity(tableDTO);
        	newTableEntity.setId(id);
        	newTableEntity.setLastModifiedDate(DateTimeUtils.sqlTimestamp());
            
            tableMapper.check(newTableEntity);
            put(newTableEntity);
        }
    }

    @Override
    //@Cacheable(value = "tableMetadata", key="#id")
    public TableDTO get(Long id) {
        TableEntity tableEntity = getTableEntityIncludeChildren(id);
    	
        return tableMapper.toDTO(tableEntity);
    }

    @Override
    //@Cacheable(value = "tableMetadata", key="#name")
    public TableDTO get(String name) {
    	TableEntity tableEntity = getTableEntityIncludeChildren(name);
	    
        return tableMapper.toDTO(tableEntity);
    }

    @Override
    @CacheEvict(value = "tableMetadata", allEntries= true)
    public void delete(Long id, Boolean isDropPhysicalTable) {
    	TableEntity tableEntity = crudService.get(TABLE_TABLE_NAME, id, TableEntity.class);
        if (tableEntity == null) {
  			throw new BusinessException(ApiErrorCode.API_RESOURCE_NOT_FOUND, id);
  		}
        
    	deleteTableCascade(id, tableEntity.getTableName(), isDropPhysicalTable);
    }
    
	@Override
	@CacheEvict(value = "tableMetadata", allEntries= true)
	public void delete(List<Long> ids, Boolean isDropPhysicalTable) {
		List<Object> valueList = new ArrayList<Object>();
		ids.stream().forEach(t -> {
			valueList.add(t);
		});
		
		Condition cond = ConditionUtils.toCondition("id", valueList);
		List<TableEntity> tableEntityList = crudService.list(TABLE_TABLE_NAME, cond, null, null, null, TableEntity.class);
				
    	for (TableEntity tableEntity : tableEntityList) {
    		deleteTableCascade(tableEntity.getId(), tableEntity.getTableName(), isDropPhysicalTable);
        }
	}

    @Override
    @CacheEvict(value = "tableMetadata", allEntries= true)
    public void deleteAll(Boolean isDropPhysicalTable) {
    	List<TableEntity> tableEntityList = crudService.list(TABLE_TABLE_NAME, TableEntity.class);
    	
    	for (TableEntity tableEntity : tableEntityList) {
    		deleteTableCascade(tableEntity.getId(), tableEntity.getTableName(), isDropPhysicalTable);
        }
    }

    @Override
    public List<TableDTO> list(String filter, String search, Condition condition, Integer offset, Integer limit, String orderby) {
    	Condition newCond = convertConditon(filter, search, condition);

    	List<TableEntity> tableEntityList =  crudService.list(TABLE_TABLE_NAME, newCond, orderby == null ? "id DESC": orderby, offset, limit, TableEntity.class);
    			
        for (TableEntity tableEntity : tableEntityList) {
        	List<ColumnEntity> columnEntityList = getColumnEntityList(tableEntity.getId());
     	    tableEntity.setColumnEntityList(columnEntityList);
     	    
     	    List<IndexEntity> indexEntityList = getIndexEntityList(tableEntity.getId(), columnEntityList);
     	    tableEntity.setIndexEntityList(indexEntityList);
        }
        
        return tableMapper.toDTO(tableEntityList);
    }
    
    @Override
    public List<TableDTO> listAll(List<Long> idList) {
		List<TableDTO> tableDTOs = new ArrayList<TableDTO>();
    	if (idList != null) {
        	idList.forEach(t -> {
        		tableDTOs.add(get(t));
        	});
    	}
    	
        return tableDTOs;
    }
    
	@Override
    public Boolean isExist(String tableName) {
    	return crudService.isExistTable(tableName);
    }

    @Override
	public Long count(String filter, String search, Condition condition) {
		Condition newCond = convertConditon(filter, search, condition);

		return crudService.count(TABLE_TABLE_NAME, newCond);
	}
	
    private void execute(String sql) {
        log.info(sql);
        crudService.execute(sql);
    }

    private Long insert(TableEntity tableEntity) {
    	Long tableId = null;
    	 
    	List<ColumnEntity> columnEntityList = tableEntity.getColumnEntityList();
    	
        if (CollectionUtils.isNotEmpty(columnEntityList)) {
            tableEntity.setCreatePhysicalTable(true);
            
            //插入表
            tableId = crudService.create(TABLE_TABLE_NAME, tableEntity);
            for (ColumnEntity columnEntity : columnEntityList) {
         	   columnEntity.setTableId(tableId);
         	   columnEntity.setCreatedDate(DateTimeUtils.sqlTimestamp());
         	   columnEntity.setLastModifiedDate(DateTimeUtils.sqlTimestamp());
            }
            
            //批量插入列
            List<Object> columnObjList = new ArrayList<Object>();
            columnEntityList.stream().forEach(t -> columnObjList.add(t));
            int[] ret = crudService.batchCreateObj(COLUMN_TABLE_NAME, columnObjList);
            
            log.info(ret.toString());
           
            TableEntity newTableEntity = getTableEntityIncludeChildren(tableId);
            
            //批量插入index
            batchInsertIndex(tableEntity.getIndexEntityList(), newTableEntity);
            
            if (!Boolean.TRUE.equals(tableEntity.getReverse())) {
            	execute(crudService.toCreateTableSql(tableEntity));
            	
            	List<String> sqlList = crudService.toCreateIndexSqlList(tableEntity);
            	for (String sql: sqlList) {
            		execute(sql);
        		}
            }
        } else {
        	//仅插入表
            tableEntity.setCreatePhysicalTable(false);
            tableId = crudService.create(TABLE_TABLE_NAME, tableEntity);
        }

        return tableId;
    }
    
    private Long put(TableEntity tableEntity) {
    	Long tableId = tableEntity.getId();
    	List<ColumnEntity> columnEntityList = tableEntity.getColumnEntityList();
        if (CollectionUtils.isNotEmpty(columnEntityList)) {
            tableEntity.setCreatePhysicalTable(true);
            
            //编辑表
            crudService.patch(TABLE_TABLE_NAME, tableId, tableEntity);
            for (ColumnEntity columnEntity : columnEntityList) {
         	   columnEntity.setTableId(tableId);
         	   columnEntity.setCreatedDate(DateTimeUtils.sqlTimestamp());
         	   columnEntity.setLastModifiedDate(DateTimeUtils.sqlTimestamp());
            }
            
            //批量插入列
            List<Object> columnObjList = new ArrayList<Object>();
            columnEntityList.stream().forEach(t -> columnObjList.add(t));
            int[] ret = crudService.batchCreateObj(COLUMN_TABLE_NAME, columnObjList);
            
            log.info(ret.toString());

            if (CollectionUtils.isNotEmpty(tableEntity.getColumnEntityList())) {
                execute(crudService.toCreateTableSql(tableEntity));
            }
        } else {
        	//仅编辑表
            tableEntity.setCreatePhysicalTable(false);
            crudService.patch(TABLE_TABLE_NAME, tableId, tableEntity);
        }

        return tableId;
    }
    
    private void patch(TableEntity tableEntity, List<Long> deleteColumnIdList, List<Long>  deleteIndexIdList) {
    	Long tableId = tableEntity.getId();
    	
    	crudService.patch(TABLE_TABLE_NAME, tableId, tableEntity);
    	
        patchColumn(tableId, tableEntity.getColumnEntityList(), deleteColumnIdList);
        
        patchIndex(tableId, tableEntity.getIndexEntityList(), deleteIndexIdList);
    }
    
    private void patchColumn(Long tableId, List<ColumnEntity> columnEntityList, List<Long> deleteColumnIdList) {
        Timestamp timestamp = DateTimeUtils.sqlTimestamp();
        
        List<ColumnEntity> insertColumnEntityList = new ArrayList<ColumnEntity>();
        List<ColumnEntity> updateColumnEntityList = new ArrayList<ColumnEntity>();
     
        for (ColumnEntity columnEntity : columnEntityList) {
           columnEntity.setTableId(tableId);
           if (columnEntity.getId() != null) {
           	   columnEntity.setLastModifiedDate(timestamp);
           	   updateColumnEntityList.add(columnEntity);
           } else {
           	   columnEntity.setCreatedDate(timestamp);
           	   columnEntity.setLastModifiedDate(timestamp);
           	   insertColumnEntityList.add(columnEntity);
           }
         }
        
         //批量删除列
         if (CollectionUtils.isNotEmpty(deleteColumnIdList)) {
             List<Object> values = new ArrayList<Object>();
             deleteColumnIdList.stream().forEach(t -> {
            	 values.add(t);
             });
             
             Condition cond = ConditionUtils.toCondition("id", values);
             crudService.delete(COLUMN_TABLE_NAME, cond);
         }
         
         //批量编辑列
         List<Object> updateColumnObjList = new ArrayList<Object>();
         updateColumnEntityList.stream().forEach(t -> updateColumnObjList.add(t));
         int[] ret = crudService.batchPutObj(COLUMN_TABLE_NAME, updateColumnObjList);
          
         log.info(ret.toString());
         
         //批量插入列
         List<Object> insertColumnObjList = new ArrayList<Object>();
         insertColumnEntityList.stream().forEach(t -> insertColumnObjList.add(t));
         ret = crudService.batchCreateObj(COLUMN_TABLE_NAME, insertColumnObjList);
         
         log.info(ret.toString());
    }
    
    private void patchIndex(Long tableId, List<IndexEntity> indexEntityList, List<Long> deleteIndexIdList) {
        Timestamp timestamp = DateTimeUtils.sqlTimestamp();
        
        List<IndexEntity> insertIndexEntityList = new ArrayList<IndexEntity>();
        List<IndexEntity> updateIndexEntityList = new ArrayList<IndexEntity>();
     
        for (IndexEntity indexEntity : indexEntityList) {
           indexEntity.setTableId(tableId);
           if (indexEntity.getId() != null) {
               indexEntity.setLastModifiedDate(timestamp);
               updateIndexEntityList.add(indexEntity);
           } else {
               indexEntity.setCreatedDate(timestamp);
               indexEntity.setLastModifiedDate(timestamp);
               insertIndexEntityList.add(indexEntity);
           }
         }
        
         //批量删除索引
         if (CollectionUtils.isNotEmpty(deleteIndexIdList)) {
             List<Object> values = new ArrayList<Object>();
             deleteIndexIdList.stream().forEach(t -> {
                 values.add(t);
             });
             
             //先删除IndexLineEntity
             Condition cond = ConditionUtils.toCondition("indexId", values);
             crudService.delete(INDEX_LINE_TABLE_NAME, cond);
             
             //删除IndexEntity
             cond = ConditionUtils.toCondition("id", values);
             crudService.delete(INDEX_TABLE_NAME, cond);
         }
         
         //批量编辑索引
         batchUpdateIndex(updateIndexEntityList);
         
         //批量插入索引
         batchInsertIndex(insertIndexEntityList);
    }
    
    private void batchInsertIndex(List<IndexEntity> indexEntityList) {
    	if (CollectionUtils.isNotEmpty(indexEntityList)) { 
    		for (IndexEntity indexEntity : indexEntityList) {
    			insertIndex(indexEntity);
            }
    	}
    }
    
    private Long insertIndex(IndexEntity indexEntity) {
    	Timestamp now = DateTimeUtils.sqlTimestamp();
    	indexEntity.setCreatedDate(now);
    	indexEntity.setLastModifiedDate(now);
		
    	Long indexId = crudService.create(INDEX_TABLE_NAME, indexEntity);
    	
    	List<IndexLineEntity> indexLineEntityList = indexEntity.getIndexLineEntityList();
    	
    	if (CollectionUtils.isNotEmpty(indexLineEntityList)) { 
    		
    		for (IndexLineEntity indexLineEntity : indexLineEntityList) {
        		indexLineEntity.setIndexId(indexId);
            }
    		
    		List<Object> indexLineObjList = new ArrayList<Object>();
    		indexLineEntityList.stream().forEach(t -> indexLineObjList.add(t));
        	int[] ret = crudService.batchCreateObj(INDEX_LINE_TABLE_NAME, indexLineObjList);
        	
        	log.info(ret.toString());
        	
    	}
    	 
    	return indexId;
    }
    
    private void batchInsertIndex(List<IndexEntity> indexEntityList, TableEntity newTbleEntity) {
    	if (CollectionUtils.isNotEmpty(indexEntityList)) { 
    		for (IndexEntity indexEntity : indexEntityList) {
    			insertIndex(indexEntity, newTbleEntity);
            }
    	}
    }
    
    private Long insertIndex(IndexEntity indexEntity, TableEntity newTbleEntity) {
    	Timestamp now = DateTimeUtils.sqlTimestamp();
    	indexEntity.setCreatedDate(now);
    	indexEntity.setLastModifiedDate(now);
    	indexEntity.setTableId(newTbleEntity.getId());
    	
    	Long indexId = crudService.create(INDEX_TABLE_NAME, indexEntity);
    	List<IndexLineEntity> indexLineEntityList = indexEntity.getIndexLineEntityList();
    	
    	if (CollectionUtils.isNotEmpty(indexLineEntityList)) { 
    		
    		for (IndexLineEntity indexLineEntity : indexLineEntityList) {
        		indexLineEntity.setIndexId(indexId);
        		
        		ColumnEntity columnEntity = newTbleEntity.getColumnEntityList().stream().filter(t -> t.getName().equals(indexLineEntity.getColumnEntity().getName())).findFirst().get();
        		indexLineEntity.setColumnId(columnEntity.getId());
        		indexLineEntity.setColumnEntity(columnEntity);
            }
        	
    	    List<Object> indexLineObjList = new ArrayList<Object>();
    	    indexLineEntityList.stream().forEach(t -> indexLineObjList.add(t));
        	int[] ret = crudService.batchCreateObj(INDEX_LINE_TABLE_NAME, indexLineObjList);
        	log.info(ret.toString());
        	
    	}
    	 
    	return indexId;
    }
    
    private void batchUpdateIndex(List<IndexEntity> indexEntityList) {
    	if (CollectionUtils.isNotEmpty(indexEntityList)) { 
    		for (IndexEntity indexEntity : indexEntityList) {
    			updateIndex(indexEntity);
            }
    	}
    }
    
    private Long updateIndex(IndexEntity indexEntity) {
    	Long indexId = indexEntity.getId();
    	Timestamp now = DateTimeUtils.sqlTimestamp();
    	indexEntity.setLastModifiedDate(now);
    	crudService.patch(INDEX_TABLE_NAME, indexId, indexEntity);
    	
    	List<IndexLineEntity> indexLineEntityList = indexEntity.getIndexLineEntityList();
    	
    	if (CollectionUtils.isNotEmpty(indexLineEntityList)) {
            //先删除IndexLineEntity
            Condition cond = ConditionUtils.toCondition("indexId", indexId);
            crudService.delete(INDEX_LINE_TABLE_NAME, cond);
             
    		for (IndexLineEntity indexLineEntity : indexLineEntityList) {
        		indexLineEntity.setIndexId(indexId);
            }
        	
    		List<Object> indexLineObjList = new ArrayList<Object>();
    		indexLineEntityList.stream().forEach(t -> indexLineObjList.add(t));
        	int[] ret = crudService.batchCreateObj(INDEX_LINE_TABLE_NAME, indexLineObjList);
        	
        	log.info(ret.toString());
    	}
    	 
    	return indexId;
    }

    private TableEntity getTableEntityIncludeChildren(Long id) {
        TableEntity tableEntity = crudService.get(TABLE_TABLE_NAME, id, TableEntity.class);
        if (tableEntity == null) {
        	return null;
		}

	    List<ColumnEntity> columnEntityList = getColumnEntityList(tableEntity.getId());
	    tableEntity.setColumnEntityList(columnEntityList);
	    
	    List<IndexEntity> indexEntityList = getIndexEntityList(tableEntity.getId(), columnEntityList);
	    tableEntity.setIndexEntityList(indexEntityList);
	    
        return tableEntity;
    }
    
    private TableEntity getTableEntityIncludeChildren(String name) {
    	Condition condition1 = ConditionUtils.toCondition("lower", "name", name.toLowerCase());
    	Condition condition2 = ConditionUtils.toCondition("lower", "pluralName", name.toLowerCase());
    	Condition condition3 = ConditionUtils.toCondition("lower", "tableName", name.toLowerCase());
    	CompositeCondition compositeCondition = new CompositeCondition();
    	compositeCondition.setConditionType(ConditionTypeEnum.OR);
    	compositeCondition.add(condition1);
    	compositeCondition.add(condition2);
    	compositeCondition.add(condition3);
    	
        List<TableEntity> tableEntityList = crudService.list(TABLE_TABLE_NAME, compositeCondition, null, null, null, TableEntity.class);
        if (tableEntityList.size() == 0) {
			return null;
		}
        
        TableEntity tableEntity = tableEntityList.get(0);
       
	    List<ColumnEntity> columnEntityList = getColumnEntityList(tableEntity.getId());
	    tableEntity.setColumnEntityList(columnEntityList);
	    
	    List<IndexEntity> indexEntityList = getIndexEntityList(tableEntity.getId(), columnEntityList);
	    tableEntity.setIndexEntityList(indexEntityList);
	    
        return tableEntity;
    }
   
    private List<ColumnEntity> getColumnEntityList(Long tableId) {
        LeafCondition cond = new LeafCondition();
	    cond.setColumnName("tableId");
	    cond.setValue(tableId);
	    cond.setOperatorType(OperatorTypeEnum.EQ);
	    
	    String sqlQuotation = crudService.getSqlQuotation();
	    String orderby = sqlQuotation + "displayOrder" + sqlQuotation +  " ASC";
	    
	    List<ColumnEntity> columnEntityList = crudService.list(COLUMN_TABLE_NAME, cond, orderby, null, null, ColumnEntity.class);
	    
        return columnEntityList;
    }
    
    private List<IndexEntity> getIndexEntityList(Long tableId,  List<ColumnEntity> columnEntityList) {
        LeafCondition cond = new LeafCondition();
	    cond.setColumnName("tableId");
	    cond.setValue(tableId);
	    cond.setOperatorType(OperatorTypeEnum.EQ);
	    
	    List<IndexEntity> indexEntityList = crudService.list(INDEX_TABLE_NAME, cond, null, null, null, IndexEntity.class);
	    
	    for (IndexEntity indexEntity : indexEntityList) {
	    	LeafCondition subCond = new LeafCondition();
	    	subCond.setColumnName("indexId");
	    	subCond.setValue(indexEntity.getId());
	    	subCond.setOperatorType(OperatorTypeEnum.EQ);
		    
		    List<IndexLineEntity> indexLineEntityList = crudService.list(INDEX_LINE_TABLE_NAME, subCond, null, null, null, IndexLineEntity.class);		
		    for (IndexLineEntity indexLineEntity : indexLineEntityList) {
		    	ColumnEntity columnEntity = columnEntityList.stream().filter(t -> t.getId().equals(indexLineEntity.getColumnId())).findFirst().get();
		    	indexLineEntity.setColumnEntity(columnEntity);
		    }
		    
		    indexEntity.setIndexLineEntityList(indexLineEntityList);
        }
	    
        return indexEntityList;
    }
    
    private void deleteColumnEntity(Long tableId) {
        LeafCondition cond = new LeafCondition();
	    cond.setColumnName("tableId");
	    cond.setValue(tableId);
	    cond.setOperatorType(OperatorTypeEnum.EQ);
	    
	    crudService.delete(COLUMN_TABLE_NAME, cond);
    }
    
    private void deleteIndexEntity(Long tableId) {
        LeafCondition cond = new LeafCondition();
	    cond.setColumnName("tableId");
	    cond.setValue(tableId);
	    cond.setOperatorType(OperatorTypeEnum.EQ);
	    
	    crudService.delete(INDEX_TABLE_NAME, cond);
    }
    
	@CacheEvict(value = "tableRelationMetadata", allEntries= true)
	private void deleteRelationEntity(Long tableId) {
		LeafCondition cond1 = new LeafCondition();
 	    cond1.setColumnName("fromTableId");
 	    cond1.setValue(tableId);
 	    cond1.setOperatorType(OperatorTypeEnum.EQ);
    	LeafCondition cond2 = new LeafCondition();
 	    cond2.setColumnName("toTableId");
 	    cond2.setValue(tableId);
 	    cond2.setOperatorType(OperatorTypeEnum.EQ);
 	    
 	    CompositeCondition compositeCondition = new CompositeCondition();
 	    compositeCondition.setConditionType(ConditionTypeEnum.OR);
 	    compositeCondition.add(cond1);
 	    compositeCondition.add(cond2);
 	    
 	    crudService.delete(RELATION_TABLE_NAME, compositeCondition);
	}
	
   private void deleteTableCascade(Long id, String tableName, Boolean isDropPhysicalTable) {
        deleteRelationEntity(id);
        
        deleteIndexEntity(id);
        
    	deleteColumnEntity(id);
    	
    	crudService.delete(TABLE_TABLE_NAME, id);
    	
    	dropPhysicalTable(tableName, isDropPhysicalTable);
    }
   
    private void dropPhysicalTable(String tableName, Boolean isDropPhysicalTable) {
    	if (Boolean.TRUE.equals(isDropPhysicalTable) ) {
    		crudService.dropTable(tableName);
    	}
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
