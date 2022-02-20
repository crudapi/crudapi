package cn.crudapi.core.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.crudapi.core.constant.ApiErrorCode;
import cn.crudapi.core.dto.ColumnDTO;
import cn.crudapi.core.entity.ColumnEntity;
import cn.crudapi.core.enumeration.DataTypeEnum;
import cn.crudapi.core.enumeration.IndexTypeEnum;
import cn.crudapi.core.exception.BusinessException;
import cn.crudapi.core.model.ColumnSql;
import cn.crudapi.core.service.CrudService;
import cn.crudapi.core.util.DateTimeUtils;

@Service
public class ColumnMapper {
	private static final Logger log = LoggerFactory.getLogger(ColumnMapper.class);
	
	@Autowired
    private CrudService crudService;
	 
	public void check(ColumnEntity columnEntity) {
		//列名称不能为空
		if (StringUtils.isEmpty(columnEntity.getName()) ) {
			throw new BusinessException(ApiErrorCode.COLUMN_NAME_NOT_EMPTY, "字段名称不能为空");
	    }

		//列类型不能为空
		if (columnEntity.getDataType() == null) {
			throw new BusinessException(ApiErrorCode.COLUMN_TYPE_NOT_EMPTY, "字段" + columnEntity.getName() + "类型不能为空");
	    }

		//字符串长度不能为空且大于0
    	if ((columnEntity.getDataType() == DataTypeEnum.VARCHAR || columnEntity.getDataType() == DataTypeEnum.CHAR)
        		&& (columnEntity.getLength() == null || columnEntity.getLength() <= 0)) {
        		throw new BusinessException(ApiErrorCode.COLUMN_LENGTH_INVALID, "字符串字段" + columnEntity.getName() + "长度不能为空且大于0");
        }

    	//非主键索引名称不能为空
    	if (columnEntity.getIndexType() != null
    		&& columnEntity.getIndexType() != IndexTypeEnum.NONE
    		&& columnEntity.getIndexType() != IndexTypeEnum.PRIMARY
    		&& StringUtils.isBlank(columnEntity.getIndexName())) {
    		throw new BusinessException(ApiErrorCode.INDEX_NAME_NOT_EMPTY, "字段非主键索引名称不能为空");
    	}

    	//自增长必须为主键
    	if (Objects.equals(columnEntity.getAutoIncrement(), true)
    		&& columnEntity.getIndexType() != IndexTypeEnum.PRIMARY) {
        	throw new BusinessException(ApiErrorCode.COLUMN_AUTO_INC_MUST_PRIMARY, "自增长字段必须为主键");
        }

    	//主键必须非null
    	if (columnEntity.getIndexType() == IndexTypeEnum.PRIMARY
    		&& Objects.equals(columnEntity.getNullable(), true) ) {
        	throw new BusinessException(ApiErrorCode.PRIMARY_MUST_NOT_NULL, "主键字段不能为NULL");
        }

    	//主键index_name必须为null
    	if (columnEntity.getIndexType() == IndexTypeEnum.PRIMARY
    		&& columnEntity.getIndexName() != null
    		&& !columnEntity.getIndexName().equals("PRIMARY")) {
        	throw new BusinessException(ApiErrorCode.PRIMARY_INDEX_NAME_MUST_EMPTY, "主键索引名称必须为空或者PRIMARY！");
        }
    }

	public ColumnEntity toEntity(ColumnDTO columnDTO) {
		ColumnEntity columnEntity = new ColumnEntity();
		columnEntity.setId(columnDTO.getId());
		columnEntity.setName(columnDTO.getName());
		columnEntity.setCaption(columnDTO.getCaption());
		columnEntity.setDescription(columnDTO.getDescription());
		columnEntity.setDisplayOrder(columnDTO.getDisplayOrder());
		columnEntity.setDataType(columnDTO.getDataType());
		columnEntity.setUnsigned(columnDTO.getUnsigned());
		columnEntity.setIndexType(columnDTO.getIndexType());
		columnEntity.setIndexStorage(columnDTO.getIndexStorage());
		columnEntity.setIndexName(columnDTO.getIndexName());
		columnEntity.setLength(columnDTO.getLength());
		columnEntity.setPrecision(columnDTO.getPrecision());
		columnEntity.setScale(columnDTO.getScale());
		columnEntity.setAutoIncrement(columnDTO.getAutoIncrement());
		columnEntity.setSeqId(columnDTO.getSeqId());
		columnEntity.setNullable(columnDTO.getNullable());
		columnEntity.setDefaultValue(columnDTO.getDefaultValue());
		columnEntity.setInsertable(columnDTO.getInsertable());
		columnEntity.setUpdatable(columnDTO.getUpdatable());
		columnEntity.setQueryable(columnDTO.getQueryable());
		columnEntity.setDisplayable(columnDTO.getDisplayable());
		columnEntity.setSystemable(columnDTO.getSystemable());
		return columnEntity;
	}

	public List<ColumnEntity> toEntity(List<ColumnDTO> columnDTOList) {
		if (columnDTOList == null) {
			return null;
		} else {
			return columnDTOList.stream().filter(Objects::nonNull).map(this::toEntity).collect(Collectors.toList());
		}
	}

	public List<String> toEntityIgnoreNull(String tableName, ColumnEntity columnEntity, ColumnDTO columnDTO) {
		List<String> sqlList = new ArrayList<String>();
		String sql = null;

		String oldColumnName = columnEntity.getName();
		String oldIndexName = columnEntity.getIndexName();
		if (columnEntity.getIndexType() == null
			|| IndexTypeEnum.NONE.equals(columnEntity.getIndexType())) {
			oldIndexName = null;
		} else if (IndexTypeEnum.PRIMARY.equals(columnEntity.getIndexType())) {
			oldIndexName = "PRIMARY";
		}

		Boolean isChanged = false;
		Boolean isIndexChanged = false;

		if ((columnDTO.getName() != null && !StringUtils.equals(columnEntity.getName(), columnDTO.getName()))
				|| (columnDTO.getDataType() != null
						&& !Objects.equals(columnEntity.getDataType(), columnDTO.getDataType()))
				|| (columnDTO.getUnsigned() != null
						&& !Objects.equals(columnEntity.getUnsigned(), columnDTO.getUnsigned()))
				|| (columnDTO.getLength() != null && !Objects.equals(columnEntity.getLength(), columnDTO.getLength()))
				|| (columnDTO.getPrecision() != null
						&& !Objects.equals(columnEntity.getPrecision(), columnDTO.getPrecision()))
				|| (columnDTO.getScale() != null && !Objects.equals(columnEntity.getScale(), columnDTO.getScale()))
				|| (columnDTO.getAutoIncrement() != null
						&& !Objects.equals(columnEntity.getAutoIncrement(), columnDTO.getAutoIncrement()))
				|| (columnDTO.getNullable() != null
						&& !Objects.equals(columnEntity.getNullable(), columnDTO.getNullable()))
				|| (columnDTO.getDefaultValue() != null
						&& !Objects.equals(columnEntity.getDefaultValue(), columnDTO.getDefaultValue()))) {
			isChanged = true;
			log.info("isChanged true");
		}

		if ((columnDTO.getIndexType() != null && !Objects.equals(columnEntity.getIndexType(), columnDTO.getIndexType()))
				|| (columnDTO.getIndexStorage() != null
						&& !Objects.equals(columnEntity.getIndexStorage(), columnDTO.getIndexStorage()))
				|| (columnDTO.getIndexName() != null
						&& !Objects.equals(columnEntity.getIndexName(), columnDTO.getIndexName()))) {
			isIndexChanged = true;
			log.info("isIndexChanged true");
		}

		if (columnDTO.getName() != null) {
			columnEntity.setName(columnDTO.getName());
		}

		if (columnDTO.getCaption() != null) {
			columnEntity.setCaption(columnDTO.getCaption());
		}

		if (columnDTO.getDescription() != null) {
			columnEntity.setDescription(columnDTO.getDescription());
		}

		if (columnDTO.getDisplayOrder() != null) {
			columnEntity.setDisplayOrder(columnDTO.getDisplayOrder());
		}

		if (columnDTO.getDataType() != null) {
			columnEntity.setDataType(columnDTO.getDataType());
		}

		if (columnDTO.getUnsigned() != null) {
			columnEntity.setUnsigned(columnDTO.getUnsigned());
		}

		if (columnDTO.getIndexType() != null) {
			columnEntity.setIndexType(columnDTO.getIndexType());
		}

		if (columnDTO.getIndexStorage() != null) {
			columnEntity.setIndexStorage(columnDTO.getIndexStorage());
		}

		if (columnDTO.getIndexName() != null) {
			columnEntity.setIndexName(columnDTO.getIndexName());
		}

		Integer newColumnLength = columnDTO.getLength();
		if (newColumnLength != null) {
			columnEntity.setLength(newColumnLength < 0 ? null: newColumnLength);
		}

		Integer newColumnPrecision = columnDTO.getPrecision();
		if (newColumnPrecision != null) {
			columnEntity.setPrecision(newColumnPrecision < 0 ? null: newColumnPrecision);
		}
		
		Integer newColumnScale = columnDTO.getScale();
		if (newColumnScale != null) {
			columnEntity.setScale(newColumnScale < 0 ? null: newColumnScale);
		}

		if (columnDTO.getAutoIncrement() != null) {
			columnEntity.setAutoIncrement(columnDTO.getAutoIncrement());
		}

		Long newColumnSeqId = columnDTO.getSeqId();
		if (newColumnSeqId != null) {
			columnEntity.setSeqId(newColumnSeqId < 0 ? null : newColumnSeqId);
		}

		if (columnDTO.getNullable() != null) {
			columnEntity.setNullable(columnDTO.getNullable());
		}

		if (columnDTO.getDefaultValue() != null) {
			columnEntity.setDefaultValue(columnDTO.getDefaultValue());
		}

		if (columnDTO.getInsertable() != null) {
			columnEntity.setInsertable(columnDTO.getInsertable());
		}

		if (columnDTO.getUpdatable() != null) {
			columnEntity.setUpdatable(columnDTO.getUpdatable());
		}

		if (columnDTO.getQueryable() != null) {
			columnEntity.setQueryable(columnDTO.getQueryable());
		}
		if (columnDTO.getDisplayable() != null) {
			columnEntity.setDisplayable(columnDTO.getDisplayable());
		}
		
		if (columnDTO.getSystemable() != null) {
			columnEntity.setSystemable(columnDTO.getSystemable());
		}
		
		if (isChanged) {
			sql = crudService.toUpdateColumnSql(tableName, oldColumnName, columnEntity);
			if (StringUtils.isNotBlank(sql)) {
				sqlList.add(sql);
			}
		}

		if (isIndexChanged) {
			sql = crudService.toUpdateColumnIndexSql(tableName, oldIndexName, columnEntity);
			if (StringUtils.isNotBlank(sql)) {
				sqlList.add(sql);
			}
		}

		return sqlList;
	}

	public ColumnSql toEntityIgnoreNull(String tableName, List<ColumnEntity> columnEntityList, List<ColumnDTO> columnDTOList) {
		ColumnSql columnSql = new ColumnSql();
		List<String> deleteSqlList = columnSql.getDeleteSqlList();
		List<String> updateSqlList = columnSql.getUpdateSqlList();
		List<String> addSqlList = columnSql.getAddSqlList();
		
		// 1. delete
		List<Long> deleteColumnIdList = new ArrayList<Long>();
		for (ColumnEntity columnEntity : columnEntityList) {
			if (!columnDTOList.stream().anyMatch(t -> Objects.equals(t.getId(), columnEntity.getId()))) {
				deleteColumnIdList.add(columnEntity.getId());

				deleteSqlList.add(crudService.toDeleteColumnSql(tableName, columnEntity.getName()));
			}
		}
		columnSql.setDeleteColumnIdList(deleteColumnIdList);

		columnEntityList.removeIf(t -> deleteColumnIdList.contains(t.getId()));
		
		// 2. add and update
		Optional<ColumnEntity> optional = null;
		ColumnEntity columnEntity = null;
		for (ColumnDTO columnDTO : columnDTOList) {
			optional = columnEntityList.stream()
					.filter(t ->  columnDTO.getId() != null && Objects.equals(t.getId(), columnDTO.getId())).findFirst();
			if (optional.isPresent()) {
				updateSqlList.addAll(toEntityIgnoreNull(tableName, optional.get(), columnDTO));
			} else {
				columnEntity = toEntity(columnDTO);
				columnEntityList.add(columnEntity);

				addSqlList.add(crudService.toAddColumnSql(tableName, columnEntity));
			}
		}

		return columnSql;
	}

	public ColumnEntity toBasicEntity(ColumnDTO columnDTO) {
		ColumnEntity columnEntity = new ColumnEntity();
		columnEntity.setId(columnDTO.getId());
		columnEntity.setName(columnDTO.getName());
		columnEntity.setCaption(columnDTO.getCaption());

		return columnEntity;
	}

	public ColumnDTO toDTO(ColumnEntity columnEntity) {
		ColumnDTO columnDTO = new ColumnDTO();

		columnDTO.setId(columnEntity.getId());
		columnDTO.setName(columnEntity.getName());
		columnDTO.setCaption(columnEntity.getCaption());
		columnDTO.setDescription(columnEntity.getDescription());
		columnDTO.setDisplayOrder(columnEntity.getDisplayOrder());
		columnDTO.setDataType(columnEntity.getDataType());
		columnDTO.setUnsigned(columnEntity.getUnsigned() != null ? columnEntity.getUnsigned() : false);
		columnDTO.setIndexType(columnEntity.getIndexType());
		columnDTO.setIndexStorage(columnEntity.getIndexStorage());
		columnDTO.setIndexName(columnEntity.getIndexName());
		columnDTO.setLength(columnEntity.getLength());
		columnDTO.setPrecision(columnEntity.getPrecision());
		columnDTO.setScale(columnEntity.getScale());
		columnDTO.setAutoIncrement(columnEntity.getAutoIncrement() != null ? columnEntity.getAutoIncrement() : false);
		columnDTO.setSeqId(columnEntity.getSeqId());
		columnDTO.setNullable(columnEntity.getNullable() != null ? columnEntity.getNullable() : false);
		columnDTO.setDefaultValue(columnEntity.getDefaultValue());
		columnDTO.setInsertable(columnEntity.getInsertable() != null ? columnEntity.getInsertable() : false);
		columnDTO.setUpdatable(columnEntity.getUpdatable() != null ? columnEntity.getUpdatable() : false);
		columnDTO.setQueryable(columnEntity.getQueryable() != null ? columnEntity.getQueryable() : false);
		columnDTO.setDisplayable(columnEntity.getDisplayable() != null ? columnEntity.getDisplayable() : false);
		columnDTO.setSystemable(columnEntity.getSystemable() != null ? columnEntity.getSystemable() : false);
		
		if (columnEntity.getCreatedDate() != null) {
			columnDTO.setCreatedDate(DateTimeUtils.toDateTime(columnEntity.getCreatedDate()));
		}
		
		if (columnEntity.getLastModifiedDate() != null) {
			columnDTO.setLastModifiedDate(DateTimeUtils.toDateTime(columnEntity.getLastModifiedDate()));
		}

		return columnDTO;
	}

	public List<ColumnDTO> toDTO(List<ColumnEntity> columnEntityList) {
		if (columnEntityList == null) {
			return null;
		} else {
			return columnEntityList.stream().filter(Objects::nonNull).map(this::toDTO).collect(Collectors.toList());
		}
	}

	public ColumnDTO toBasicDTO(ColumnEntity columnEntity) {
		ColumnDTO columnDTO = new ColumnDTO();

		columnDTO.setId(columnEntity.getId());
		columnDTO.setName(columnEntity.getName());
		columnDTO.setCaption(columnEntity.getCaption());

		return columnDTO;
	}
	
	public ColumnDTO toBasicDTO(Long id) {
		ColumnDTO columnDTO = new ColumnDTO();

		columnDTO.setId(id);

		return columnDTO;
	}
}
