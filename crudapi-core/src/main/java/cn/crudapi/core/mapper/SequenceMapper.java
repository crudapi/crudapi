package cn.crudapi.core.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cn.crudapi.core.dto.SequenceDTO;
import cn.crudapi.core.entity.SequenceEntity;
import cn.crudapi.core.util.DateTimeUtils;

@Service
public class SequenceMapper {
    public SequenceEntity toEntity(SequenceDTO sequenceDTO) {
        SequenceEntity sequenceEntity = new SequenceEntity();

        sequenceEntity.setId(sequenceDTO.getId());
        sequenceEntity.setName(sequenceDTO.getName());
        sequenceEntity.setCaption(sequenceDTO.getCaption());
        sequenceEntity.setDescription(sequenceDTO.getDescription());
        sequenceEntity.setMinValue(sequenceDTO.getMinValue());
        sequenceEntity.setMaxValue(sequenceDTO.getMaxValue());
        sequenceEntity.setIncrementBy(sequenceDTO.getIncrementBy());
        sequenceEntity.setNextValue(sequenceDTO.getNextValue());
        sequenceEntity.setCycle(sequenceDTO.getCycle());
        sequenceEntity.setCurrentTime(sequenceDTO.getCurrentTime());
        sequenceEntity.setSequenceType(sequenceDTO.getSequenceType());
        sequenceEntity.setFormat(sequenceDTO.getFormat());

        return sequenceEntity;
    }

    public void toEntityIgnoreNull(SequenceEntity sequenceEntity, SequenceDTO sequenceDTO) {
        if (sequenceDTO.getName() != null) {
            sequenceEntity.setName(sequenceDTO.getName());
        }

        if (sequenceDTO.getCaption() != null) {
            sequenceEntity.setCaption(sequenceDTO.getCaption());
        }

        if (sequenceDTO.getDescription() != null) {
            sequenceEntity.setDescription(sequenceDTO.getDescription());
        }

        if (sequenceDTO.getMinValue() != null) {
            sequenceEntity.setMinValue(sequenceDTO.getMinValue());
        }

        if (sequenceDTO.getMaxValue() != null) {
            sequenceEntity.setMaxValue(sequenceDTO.getMaxValue());
        }

        if (sequenceDTO.getIncrementBy() != null) {
            sequenceEntity.setIncrementBy(sequenceDTO.getIncrementBy());
        }

        if (sequenceDTO.getNextValue() != null) {
            sequenceEntity.setNextValue(sequenceDTO.getNextValue());
        }

        if (sequenceDTO.getCycle() != null) {
            sequenceEntity.setCycle(sequenceDTO.getCycle());
        }

        if (sequenceDTO.getCurrentTime() != null) {
            sequenceEntity.setCurrentTime(sequenceDTO.getCurrentTime());
        }

        if (sequenceDTO.getSequenceType() != null) {
            sequenceEntity.setSequenceType(sequenceDTO.getSequenceType());
        }

        if (sequenceDTO.getFormat() != null) {
            sequenceEntity.setFormat(sequenceDTO.getFormat());
        }
    }

    public SequenceDTO toDTO(SequenceEntity sequenceEntity) {
        if (sequenceEntity == null) {
            return null;
        }

        SequenceDTO sequenceDTO = new SequenceDTO();

        sequenceDTO.setId(sequenceEntity.getId());
        sequenceDTO.setName(sequenceEntity.getName());
        sequenceDTO.setCaption(sequenceEntity.getCaption());
        sequenceDTO.setDescription(sequenceEntity.getDescription());
        sequenceDTO.setMinValue(sequenceEntity.getMinValue());
        sequenceDTO.setMaxValue(sequenceEntity.getMaxValue());
        sequenceDTO.setIncrementBy(sequenceEntity.getIncrementBy());
        sequenceDTO.setNextValue(sequenceEntity.getNextValue());
        sequenceDTO.setCycle(sequenceEntity.getCycle());
        sequenceDTO.setCurrentTime(sequenceEntity.getCurrentTime());
        sequenceDTO.setSequenceType(sequenceEntity.getSequenceType());
        sequenceDTO.setFormat(sequenceEntity.getFormat());
        sequenceDTO.setCreatedDate(DateTimeUtils.toDateTime(sequenceEntity.getCreatedDate()));
        sequenceDTO.setLastModifiedDate(DateTimeUtils.toDateTime(sequenceEntity.getLastModifiedDate()));

        return sequenceDTO;
    }

    public List<SequenceEntity> toEntity(List<SequenceDTO> sequenceDTOList) {
        if (sequenceDTOList == null) {
            return null;
        } else {
            return sequenceDTOList.stream().filter(Objects::nonNull).map(this::toEntity).collect(Collectors.toList());
        }
    }

    public List<SequenceDTO> toDTO(List<SequenceEntity> sequenceEntityList) {
        if (sequenceEntityList == null) {
            return null;
        } else {
            return sequenceEntityList.stream().filter(Objects::nonNull).map(this::toDTO).collect(Collectors.toList());
        }
    }
}
