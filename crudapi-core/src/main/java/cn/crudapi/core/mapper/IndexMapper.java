package cn.crudapi.core.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.crudapi.core.constant.ApiErrorCode;
import cn.crudapi.core.dto.IndexDTO;
import cn.crudapi.core.dto.IndexLineDTO;
import cn.crudapi.core.entity.IndexEntity;
import cn.crudapi.core.entity.IndexLineEntity;
import cn.crudapi.core.enumeration.IndexTypeEnum;
import cn.crudapi.core.exception.BusinessException;
import cn.crudapi.core.model.IndexSql;
import cn.crudapi.core.util.DateTimeUtils;
import cn.crudapi.core.util.DbUtils;

@Service
public class IndexMapper {
	private static final Logger log = LoggerFactory.getLogger(IndexMapper.class);
	
	@Autowired
	private IndexLineMapper indexLineMapper;

	public void check(IndexEntity indexEntity) {
		//类型不能为空
		if (indexEntity.getIndexType() == null
			|| indexEntity.getIndexType() == IndexTypeEnum.NONE) {
			throw new BusinessException(ApiErrorCode.INDEX_TYPE_NOT_EMPTY, "索引类型不能为空！");
	    }

    	//非主键索引名称不能为空
    	if (indexEntity.getIndexType() != IndexTypeEnum.PRIMARY
    		&& StringUtils.isBlank(indexEntity.getName())) {
    		throw new BusinessException(ApiErrorCode.INDEX_NAME_NOT_EMPTY, "非主键索引名称不能为空！");
    	}

     	//主键index_name必须为null
    	if (indexEntity.getIndexType() == IndexTypeEnum.PRIMARY
    		&& indexEntity.getName() != null
    		&& !indexEntity.getName().equals("PRIMARY")) {
        	throw new BusinessException(ApiErrorCode.PRIMARY_INDEX_NAME_MUST_EMPTY, "主键索引名称必须为空或者PRIMARY！");
        }

    	if (CollectionUtils.isEmpty(indexEntity.getIndexLineEntityList())) {
    		throw new BusinessException(ApiErrorCode.INDEX_LINE_NOT_EMPTY, "索引至少选择一个字段！");
    	}
	}

	public IndexEntity toEntity(IndexDTO indexDTO) {
		IndexEntity indexEntity = new IndexEntity();
		indexEntity.setId(indexDTO.getId());
		indexEntity.setName(indexDTO.getName());
		indexEntity.setCaption(indexDTO.getCaption());
		indexEntity.setDescription(indexDTO.getDescription());
		indexEntity.setIndexType(indexDTO.getIndexType());
		indexEntity.setIndexStorage(indexDTO.getIndexStorage());
		
		List<IndexLineEntity> indexLineEntityList = indexLineMapper.toEntity(indexDTO.getIndexLineDTOList());
		indexEntity.setIndexLineEntityList(indexLineEntityList);

		return indexEntity;
	}

	public List<IndexEntity> toEntity(List<IndexDTO> indexDTOList) {
		if (indexDTOList == null) {
			return null;
		} else {
			return indexDTOList.stream().filter(Objects::nonNull).map(this::toEntity).collect(Collectors.toList());
		}
	}

	public List<String> toEntityIgnoreNull(String tableName, IndexEntity indexEntity, IndexDTO indexDTO) {
		List<String> sqlList = new ArrayList<String>();
		String sql = null;

		String oldIndexName = indexEntity.getName();
		if (indexEntity.getIndexType() == null
			|| IndexTypeEnum.NONE.equals(indexEntity.getIndexType())) {
			oldIndexName = null;
		} else if (IndexTypeEnum.PRIMARY.equals(indexEntity.getIndexType())) {
			oldIndexName = "PRIMARY";
		}

		List<Long> oldColumnIdLongList = new ArrayList<Long>();
		if (indexEntity.getIndexLineEntityList() != null) {
			indexEntity.getIndexLineEntityList().forEach(t -> oldColumnIdLongList.add(t.getColumnEntity().getId()));
		}

		List<Long> newColumnIdLongList = new ArrayList<Long>();
		if (indexDTO.getIndexLineDTOList() != null) {
			indexDTO.getIndexLineDTOList().forEach(t -> newColumnIdLongList.add(t.getColumnDTO().getId()));
		}

		Boolean isChanged = false;
		if ((indexDTO.getName() != null && !Objects.equals(indexEntity.getName(), indexDTO.getName()))
		        || (indexDTO.getIndexType() != null && !Objects.equals(indexEntity.getIndexType(), indexDTO.getIndexType()))
		        || (indexDTO.getIndexStorage() != null && !Objects.equals(indexEntity.getIndexStorage(), indexDTO.getIndexStorage()))
		        || (indexDTO.getIndexLineDTOList() != null && !Objects.equals(oldColumnIdLongList, newColumnIdLongList))) {
			isChanged = true;
			log.info("isChanged = true");
		}

		if (indexDTO.getName() != null) {
			indexEntity.setName(indexDTO.getName());
		}

		if (indexDTO.getCaption() != null) {
			indexEntity.setCaption(indexDTO.getCaption());
		}

		if (indexDTO.getDescription() != null) {
			indexEntity.setDescription(indexDTO.getDescription());
		}

		if (indexDTO.getIndexType() != null) {
			indexEntity.setIndexType(indexDTO.getIndexType());
		}

		if (indexDTO.getIndexStorage() != null) {
			indexEntity.setIndexStorage(indexDTO.getIndexStorage());
		}

		if (indexDTO.getIndexLineDTOList() != null) {
			List<IndexLineEntity> indexLineEntityList = indexLineMapper.toEntity(indexDTO.getIndexLineDTOList());
			indexEntity.setIndexLineEntityList(indexLineEntityList);
		}

		if (isChanged) {
			sql = DbUtils.toUpdateIndexSql(tableName, oldIndexName, indexEntity);
			if (StringUtils.isNotBlank(sql)) {
				sqlList.add(sql);
			}
		}

		return sqlList;
	}

	public IndexSql toEntityIgnoreNull(String tableName, List<IndexEntity> indexEntityList, List<IndexDTO> indexDTOList) {
		IndexSql indexSql = new IndexSql();
		List<String> deleteSqlList = indexSql.getDeleteSqlList();
		List<String> updateSqlList = indexSql.getUpdateSqlList();
		List<String> addSqlList = indexSql.getAddSqlList();
		String sql = null;

		// 1. delete
		List<Long> deleteIndexIdList = new ArrayList<Long>();
		for (IndexEntity indexEntity : indexEntityList) {
			if (!indexDTOList.stream().anyMatch(t -> Objects.equals(t.getId(), indexEntity.getId()))) {
				deleteIndexIdList.add(indexEntity.getId());

				deleteSqlList.add(DbUtils.toDeleteIndexSql(tableName, indexEntity.getName()));
			}
		}
		indexSql.setDeleteIndexIdList(deleteIndexIdList);

		indexEntityList.removeIf(t -> deleteIndexIdList.contains(t.getId()));

		// 2. add and update
		Optional<IndexEntity> optional = null;
		IndexEntity indexEntity = null;
		for (IndexDTO indexDTO : indexDTOList) {
			optional = indexEntityList.stream()
					.filter(t -> indexDTO.getId() != null && Objects.equals(t.getId(), indexDTO.getId())).findFirst();
			if (optional.isPresent()) {
				updateSqlList.addAll(toEntityIgnoreNull(tableName, optional.get(), indexDTO));
			} else {
				indexEntity = toEntity(indexDTO);
				indexEntityList.add(indexEntity);
				sql = DbUtils.toAddIndexSql(tableName, indexEntity);
				if (!StringUtils.isBlank(sql)) {
					addSqlList.add(sql);
				}
			}
		}
		return indexSql;
	}

	public IndexDTO toDTO(IndexEntity indexEntity) {
		IndexDTO indexDTO = new IndexDTO();

		indexDTO.setId(indexEntity.getId());
		indexDTO.setName(indexEntity.getName());
		indexDTO.setCaption(indexEntity.getCaption());
		indexDTO.setDescription(indexEntity.getDescription());
		indexDTO.setIndexType(indexEntity.getIndexType());
		indexDTO.setIndexStorage(indexEntity.getIndexStorage());
		indexDTO.setCreatedDate(DateTimeUtils.toDateTime(indexEntity.getCreatedDate()));
		indexDTO.setLastModifiedDate(DateTimeUtils.toDateTime(indexEntity.getLastModifiedDate()));

		List<IndexLineDTO> indexLineDTOList = indexLineMapper.toDTO(indexEntity.getIndexLineEntityList());
		indexDTO.setIndexLineDTOList(indexLineDTOList);

		return indexDTO;
	}

	public List<IndexDTO> toDTO(List<IndexEntity> indexEntityList) {
		if (indexEntityList == null) {
			return null;
		} else {
			return indexEntityList.stream().filter(Objects::nonNull).map(this::toDTO).collect(Collectors.toList());
		}
	}
}
