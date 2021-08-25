package cn.crudapi.core.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.crudapi.core.dto.TableRelationDTO;
import cn.crudapi.core.entity.TableRelationEntity;
import cn.crudapi.core.util.DateTimeUtils;

@Service
public class TableRelationMapper {
    @Autowired
    private ColumnMapper columnMapper;

    @Autowired
    private TableMapper tableMapper;

    public TableRelationEntity toEntity(TableRelationDTO tableRelationDTO) {
        TableRelationEntity tableRelationEntity = new TableRelationEntity();

        tableRelationEntity.setId(tableRelationDTO.getId());
        tableRelationEntity.setName(tableRelationDTO.getName());
        tableRelationEntity.setCaption(tableRelationDTO.getCaption());
        tableRelationEntity.setDescription(tableRelationDTO.getDescription());
        tableRelationEntity.setRelationType(tableRelationDTO.getRelationType());
        tableRelationEntity.setFromTableId(tableRelationDTO.getFromTableDTO().getId());
        tableRelationEntity.setToTableId(tableRelationDTO.getToTableDTO().getId());
        tableRelationEntity.setFromColumnId(tableRelationDTO.getFromColumnDTO().getId());
        tableRelationEntity.setToColumnId(tableRelationDTO.getToColumnDTO().getId());

        return tableRelationEntity;
    }

    public void toEntityIgnoreNull(TableRelationEntity tableRelationEntity, TableRelationDTO tableRelationDTO) {
        if (tableRelationDTO.getName() != null) {
            tableRelationEntity.setName(tableRelationDTO.getName());
        }

        if (tableRelationDTO.getCaption() != null) {
            tableRelationEntity.setCaption(tableRelationDTO.getCaption());
        }

        if (tableRelationDTO.getDescription() != null) {
            tableRelationEntity.setDescription(tableRelationDTO.getDescription());
        }

        if (tableRelationDTO.getRelationType() != null) {
        	tableRelationEntity.setRelationType(tableRelationDTO.getRelationType());
        }

        if (tableRelationDTO.getFromTableDTO() != null) {
        	tableRelationEntity.setFromTableId(tableRelationDTO.getFromTableDTO().getId());
        }

        if (tableRelationDTO.getToTableDTO() != null) {
        	 tableRelationEntity.setToTableId(tableRelationDTO.getToTableDTO().getId());
        }

        if (tableRelationDTO.getFromColumnDTO() != null) {
        	 tableRelationEntity.setFromColumnId(tableRelationDTO.getFromColumnDTO().getId());
        }

        if (tableRelationDTO.getToColumnDTO() != null) {
        	tableRelationEntity.setToColumnId(tableRelationDTO.getToColumnDTO().getId());
        }

    }

    public TableRelationDTO toDTO(TableRelationEntity tableRelationEntity) {
        if (tableRelationEntity == null) {
            return null;
        }

        TableRelationDTO tableRelationDTO = new TableRelationDTO();

        tableRelationDTO.setId(tableRelationEntity.getId());
        tableRelationDTO.setName(tableRelationEntity.getName());
        tableRelationDTO.setCaption(tableRelationEntity.getCaption());
        tableRelationDTO.setDescription(tableRelationEntity.getDescription());
        tableRelationDTO.setRelationType(tableRelationEntity.getRelationType());
        tableRelationDTO.setCreatedDate(DateTimeUtils.toDateTime(tableRelationEntity.getCreatedDate()));
        tableRelationDTO.setLastModifiedDate(DateTimeUtils.toDateTime(tableRelationEntity.getLastModifiedDate()));
        tableRelationDTO.setFromTableDTO(tableMapper.toBasicDTO(tableRelationEntity.getFromTableEntity()));
        tableRelationDTO.setToTableDTO(tableMapper.toBasicDTO(tableRelationEntity.getToTableEntity()));
        tableRelationDTO.setFromColumnDTO(columnMapper.toBasicDTO(tableRelationEntity.getFromColumnEntity()));
        tableRelationDTO.setToColumnDTO(columnMapper.toBasicDTO(tableRelationEntity.getToColumnEntity()));

        return tableRelationDTO;
    }

    public List<TableRelationEntity> toEntity(List<TableRelationDTO> tableRelationDTOList) {
        if (tableRelationDTOList == null) {
            return null;
        } else {
            return tableRelationDTOList.stream().filter(Objects::nonNull).map(this::toEntity).collect(Collectors.toList());
        }
    }

    public List<TableRelationDTO> toDTO(List<TableRelationEntity> tableRelationEntityList) {
        if (tableRelationEntityList == null) {
            return null;
        } else {
            return tableRelationEntityList.stream().filter(Objects::nonNull).map(this::toDTO).collect(Collectors.toList());
        }
    }
}
