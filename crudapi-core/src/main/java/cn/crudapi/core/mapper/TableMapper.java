package cn.crudapi.core.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.crudapi.core.constant.ApiErrorCode;
import cn.crudapi.core.dto.ColumnDTO;
import cn.crudapi.core.dto.IndexDTO;
import cn.crudapi.core.dto.TableDTO;
import cn.crudapi.core.entity.ColumnEntity;
import cn.crudapi.core.entity.IndexEntity;
import cn.crudapi.core.entity.TableEntity;
import cn.crudapi.core.enumeration.IndexTypeEnum;
import cn.crudapi.core.exception.BusinessException;
import cn.crudapi.core.model.ColumnSql;
import cn.crudapi.core.model.IndexSql;
import cn.crudapi.core.model.TableSql;
import cn.crudapi.core.service.CrudService;
import cn.crudapi.core.util.DateTimeUtils;

@Service
public class TableMapper {
    @Autowired
    private ColumnMapper columnMapper;

    @Autowired
    private IndexMapper indexMapper;

	@Autowired
    private CrudService crudService;
	 
    public void check(TableEntity tableEntity) {
		if (StringUtils.isEmpty(tableEntity.getName())) {
			throw new BusinessException(ApiErrorCode.TABLE_NAME_NOT_EMPTY, "表名不能为空！");
	    }

		if (tableEntity.getEngine() == null) {
			throw new BusinessException(ApiErrorCode.TABLE_ENGINE_NOT_EMPTY,  "存储引擎不能为空！");
	    }

		List<String> columnIndexNameList = new ArrayList<String>();
		List<String> indexNameList = new ArrayList<String>();
		int primaryCount = 0;

		List<String> columnNames = new ArrayList<String>();
		List<String> columnCaptions = new ArrayList<String>();
    	if (CollectionUtils.isNotEmpty(tableEntity.getColumnEntityList())) {
			for (ColumnEntity columnEntity : tableEntity.getColumnEntityList()) {
				columnMapper.check(columnEntity);
				if (columnEntity.getIndexType() == IndexTypeEnum.PRIMARY)  {
					primaryCount++;
				} else {
					columnIndexNameList.add(columnEntity.getIndexName());
				}
				
				if (!columnNames.contains(columnEntity.getName())) {
					columnNames.add(columnEntity.getName());
				} else {
					throw new BusinessException(ApiErrorCode.DUPLICATE_COLUMN_NAME, "列英文名称：" + columnEntity.getName() + "不能重复！");
				}
				
				if (!columnCaptions.contains(columnEntity.getCaption())) {
					columnCaptions.add(columnEntity.getCaption());
				} else {
					throw new BusinessException(ApiErrorCode.DUPLICATE_COLUMN_CAPTION, "列中文名称：" + columnEntity.getCaption() + "不能重复！");
				}
			}
		}

    	if (CollectionUtils.isNotEmpty(tableEntity.getIndexEntityList())) {
			for (IndexEntity indexEntity : tableEntity.getIndexEntityList()) {
				indexMapper.check(indexEntity);
				if (indexEntity.getIndexType() == IndexTypeEnum.PRIMARY)  {
					primaryCount++;
				} else {
					indexNameList.add(indexEntity.getName());
				}
			}
		}

    	//主键个数不能大于1
    	if (primaryCount > 1) {
    		throw new BusinessException(ApiErrorCode.PRIMARY_COUNT_GT_ONE, "主键个数不能超过一个！");
    	}

    	//列的indexname和index的name不能重复，各自的通过表UQ检查
    	for (String indexName : indexNameList) {
			if (columnIndexNameList.stream().anyMatch(t -> Objects.equals(t, indexName))) {
				throw new BusinessException(ApiErrorCode.DUPLICATE_INDEX_NAME, "索引名称不能重复！");
			}
		}
    }

    //create
    public TableEntity toEntity(TableDTO tableDTO) {
        TableEntity tableEntity = new TableEntity();
        tableEntity.setId(tableDTO.getId());
        tableEntity.setName(tableDTO.getName());
        tableEntity.setTableName(tableDTO.getTableName());
        tableEntity.setPluralName(tableDTO.getPluralName());
        tableEntity.setEngine(tableDTO.getEngine());
        tableEntity.setCaption(tableDTO.getCaption());
        tableEntity.setDescription(tableDTO.getDescription());
        tableEntity.setReverse(tableDTO.getReverse());
        tableEntity.setSystemable(tableDTO.getSystemable());
        tableEntity.setReadOnly(tableDTO.getReadOnly());

        List<ColumnEntity> columnEntityList = columnMapper.toEntity(tableDTO.getColumnDTOList());
        tableEntity.setColumnEntityList(columnEntityList);

        List<IndexEntity> indexEntityList = indexMapper.toEntity(tableDTO.getIndexDTOList());
        tableEntity.setIndexEntityList(indexEntityList);

        return tableEntity;
    }

    //create
    public void toEntity(TableEntity tableEntity, TableDTO tableDTO) {
        if (tableDTO.getName() != null) {
            tableEntity.setName(tableDTO.getName());
        }

        if (tableDTO.getPluralName() != null) {
            tableEntity.setPluralName(tableDTO.getPluralName());
        }
        
        if (tableDTO.getTableName() != null) {
            tableEntity.setTableName(tableDTO.getTableName());
        }
     
        if (tableDTO.getEngine() != null) {
            tableEntity.setEngine(tableDTO.getEngine());
        }

        if (tableDTO.getCaption() != null) {
            tableEntity.setCaption(tableDTO.getCaption());
        }

        if (tableDTO.getDescription() != null) {
            tableEntity.setDescription(tableDTO.getDescription());
        }
        
        if (tableDTO.getSystemable() != null) {
        	tableEntity.setSystemable(tableDTO.getSystemable());
        }
        
        if (tableDTO.getReadOnly() != null) {
        	tableEntity.setReadOnly(tableDTO.getReadOnly());
        }
        
        List<ColumnEntity> columnEntityList = columnMapper.toEntity(tableDTO.getColumnDTOList());
        tableEntity.setColumnEntityList(columnEntityList);
        
        List<IndexEntity> indexEntityList = indexMapper.toEntity(tableDTO.getIndexDTOList());
        tableEntity.setIndexEntityList(indexEntityList);
    }

    public TableSql toEntityIgnoreNull(TableEntity tableEntity, TableDTO tableDTO) {
        TableSql tableSql = new TableSql();
        List<String> sqlList = tableSql.getSqlList();

        if (tableDTO.getTableName() != null && !StringUtils.equals(tableEntity.getTableName(), tableDTO.getTableName())) {
            sqlList.add(crudService.toRenameTableSql(tableEntity.getTableName(), tableDTO.getTableName()));
            tableEntity.setTableName(tableDTO.getTableName());
        }

        if (tableDTO.getEngine() != null && !Objects.equals(tableEntity.getEngine(), tableDTO.getEngine())) {
        	String setTableEngineSql = crudService.toSetTableEngineSql(tableEntity.getTableName(), tableDTO.getEngine());
        	if (setTableEngineSql != null) {
            	sqlList.add(setTableEngineSql);
            }
            tableEntity.setEngine(tableDTO.getEngine());
        }

        if (tableDTO.getName() != null) {
            tableEntity.setName(tableDTO.getName());
        }
        
        if (tableDTO.getPluralName() != null) {
            tableEntity.setPluralName(tableDTO.getPluralName());
        }

        if (tableDTO.getCaption() != null) {
            tableEntity.setCaption(tableDTO.getCaption());
        }

        if (tableDTO.getDescription() != null) {
            tableEntity.setDescription(tableDTO.getDescription());
        }
        
        if (tableDTO.getSystemable() != null) {
        	tableEntity.setSystemable(tableDTO.getSystemable());
        }
        
        if (tableDTO.getReadOnly() != null) {
        	tableEntity.setReadOnly(tableDTO.getReadOnly());
        }

        if (tableDTO.getColumnDTOList() != null) {
            ColumnSql columnSql = columnMapper.toEntityIgnoreNull(tableEntity, tableEntity.getColumnEntityList(), tableDTO.getColumnDTOList());
            tableSql.setColumnSql(columnSql);
        }

        if (tableDTO.getIndexDTOList() != null) {
            IndexSql indexSql = indexMapper.toEntityIgnoreNull(tableEntity.getTableName(), tableEntity.getIndexEntityList(), tableDTO.getIndexDTOList());
            tableSql.setIndexSql(indexSql);
        }

        return tableSql;
    }


    public List<TableEntity> toEntity(List<TableDTO> tableDTOList) {
        return tableDTOList.stream().filter(Objects::nonNull).map(this::toEntity).collect(Collectors.toList());
    }

    public TableEntity toBasicEntity(TableDTO tableDTO) {
        TableEntity tableEntity = new TableEntity();
        tableEntity.setId(tableDTO.getId());
        tableEntity.setName(tableDTO.getName());
        tableEntity.setTableName(tableDTO.getTableName());
        tableEntity.setPluralName(tableDTO.getPluralName());
        tableEntity.setCaption(tableDTO.getCaption());
        tableEntity.setSystemable(tableDTO.getSystemable());
        tableEntity.setReadOnly(tableDTO.getReadOnly());
        
        return tableEntity;
    }

    public TableDTO toDTO(TableEntity tableEntity) {
        if (tableEntity == null) {
            return null;
        }

        TableDTO tableDTO = new TableDTO();
        tableDTO.setId(tableEntity.getId());
        tableDTO.setEngine(tableEntity.getEngine());
        tableDTO.setName(tableEntity.getName());
        tableDTO.setPluralName(tableEntity.getPluralName());
        tableDTO.setTableName(tableEntity.getTableName());
        tableDTO.setCaption(tableEntity.getCaption());
        tableDTO.setDescription(tableEntity.getDescription());
        tableDTO.setSystemable(tableEntity.getSystemable());
        tableDTO.setReadOnly(tableEntity.getReadOnly());
        tableDTO.setCreatedDate(DateTimeUtils.toDateTime(tableEntity.getCreatedDate()));
        tableDTO.setLastModifiedDate(DateTimeUtils.toDateTime(tableEntity.getLastModifiedDate()));

        List<ColumnDTO> columnDTOList = columnMapper.toDTO(tableEntity.getColumnEntityList());
        tableDTO.setColumnDTOList(columnDTOList);

        List<IndexDTO> indexDTOList = indexMapper.toDTO(tableEntity.getIndexEntityList());
        tableDTO.setIndexDTOList(indexDTOList);
        
        tableDTO.calculatePrimaryNameList();

        return tableDTO;
    }

    public List<TableDTO> toDTO(List<TableEntity> tableEntityList) {
        return tableEntityList.stream().filter(Objects::nonNull).map(this::toDTO).collect(Collectors.toList());
    }

    public TableDTO toBasicDTO(TableEntity tableEntity) {
        if (tableEntity == null) {
            return null;
        }

        TableDTO tableDTO = new TableDTO();
        tableDTO.setId(tableEntity.getId());
        tableDTO.setName(tableEntity.getName());
        tableDTO.setPluralName(tableEntity.getPluralName());
        tableDTO.setTableName(tableEntity.getTableName());
        tableDTO.setCaption(tableEntity.getCaption());

        return tableDTO;
    }
    
    public TableDTO toBasicDTO(Long id) {
        TableDTO tableDTO = new TableDTO();
        tableDTO.setId(id);

        return tableDTO;
    }
}
