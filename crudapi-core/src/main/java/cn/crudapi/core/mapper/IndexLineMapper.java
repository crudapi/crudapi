package cn.crudapi.core.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.crudapi.core.dto.IndexLineDTO;
import cn.crudapi.core.entity.IndexLineEntity;

@Service
public class IndexLineMapper {
    @Autowired
    private ColumnMapper columnMapper;

    public IndexLineEntity toEntity(IndexLineDTO indexLineDTO) {
        IndexLineEntity indexLineEntity = new IndexLineEntity();

        indexLineEntity.setId(indexLineDTO.getId());
        indexLineEntity.setColumnEntity(columnMapper.toEntity(indexLineDTO.getColumnDTO()));
        indexLineEntity.setColumnId(indexLineEntity.getColumnEntity().getId());
        
        return indexLineEntity;
    }

    public List<IndexLineEntity> toEntity(List<IndexLineDTO> indexLineDTOList) {
        if (indexLineDTOList == null) {
            return null;
        } else {
            return indexLineDTOList.stream().filter(Objects::nonNull).map(this::toEntity).collect(Collectors.toList());
        }
    }

    public IndexLineDTO toDTO(IndexLineEntity indexLineEntity) {
        if (indexLineEntity == null) {
            return null;
        }

        IndexLineDTO indexLineDTO = new IndexLineDTO();

        indexLineDTO.setId(indexLineEntity.getId());
        indexLineDTO.setColumnDTO(columnMapper.toDTO(indexLineEntity.getColumnEntity()));

        return indexLineDTO;
    }

    public List<IndexLineDTO> toDTO(List<IndexLineEntity> indexLineEntityList) {
        if (indexLineEntityList == null) {
            return null;
        } else {
            return indexLineEntityList.stream().filter(Objects::nonNull).map(this::toDTO).collect(Collectors.toList());
        }
    }
}
