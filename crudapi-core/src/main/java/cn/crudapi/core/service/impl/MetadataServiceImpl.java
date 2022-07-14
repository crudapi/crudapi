package cn.crudapi.core.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;

import cn.crudapi.core.constant.ApiErrorCode;
import cn.crudapi.core.dto.ColumnDTO;
import cn.crudapi.core.dto.MetadataDTO;
import cn.crudapi.core.dto.SequenceDTO;
import cn.crudapi.core.dto.TableDTO;
import cn.crudapi.core.dto.TableRelationDTO;
import cn.crudapi.core.exception.BusinessException;
import cn.crudapi.core.query.Condition;
import cn.crudapi.core.service.FileService;
import cn.crudapi.core.service.MetadataService;
import cn.crudapi.core.service.SequenceMetadataService;
import cn.crudapi.core.service.TableMetadataService;
import cn.crudapi.core.service.TableRelationMetadataService;
import cn.crudapi.core.service.TableService;
import cn.crudapi.core.util.ConditionUtils;
import cn.crudapi.core.util.JsonUtils;

@Service
public class MetadataServiceImpl implements MetadataService {
	private static final Logger log = LoggerFactory.getLogger(MetadataServiceImpl.class);
	
	private static final String TABLLE_FROM_BUILDER = "tableFormBuilder";
	
	@Autowired
	private FileService fileService;

	@Autowired
	private SequenceMetadataService sequenceMetadataService;

	@Autowired
	private TableMetadataService tableMetadataService;

	@Autowired
	private TableRelationMetadataService tableRelationMetadataService;

	@Autowired
	private TableService tableService;
	
	@Override
	public void importData(File file) {
		try {
			String body = FileUtils.readFileToString(file, "utf-8");
			
			MetadataDTO metadataDTO = JsonUtils.toObject(body, new TypeReference<MetadataDTO>(){});
			
			this.importData(metadataDTO);
		} catch (Exception e) {
			throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, "导入失败！" + e.getMessage()); 
		}
	}
	
	
	@Override
	@Transactional
	public void importData(MetadataDTO metadataDTO) {
		try {
			//seq
			List<SequenceDTO> sequenceDTOs = metadataDTO.getSequenceDTOList();
			Map<Long, Long> seqMap = new HashMap<Long, Long>();
			for (SequenceDTO sequenceDTO : sequenceDTOs) { 
				log.info(sequenceDTO.getName() + " oldSeqId:" + sequenceDTO.getId());
				Condition condition = ConditionUtils.toCondition("name", sequenceDTO.getName());
				if (sequenceMetadataService.count(null, null, condition) > 0) {
					log.info("skip isExist seq:" + sequenceDTO.getName());
					seqMap.put(sequenceDTO.getId(), sequenceDTO.getId());
					continue;
				}
				Long newSeqId = sequenceMetadataService.create(sequenceDTO);
				log.info(sequenceDTO.getName() + " newSeqId:" + newSeqId);
				seqMap.put(sequenceDTO.getId(), newSeqId);
			}
			
			//替换SeqId
			List<TableDTO> tableDTOs = metadataDTO.getTableDTOList();
			for (TableDTO tableDTO : tableDTOs) {
				for (ColumnDTO columnDTO : tableDTO.getColumnDTOList()) {
					Long seqId = columnDTO.getSeqId();
					if (seqId != null) {
						columnDTO.setSeqId(seqMap.get(seqId));
					}
				}
			}
			
			//table
			Map<Long, TableDTO> tableMap = new HashMap<Long, TableDTO>();
			List<Long> tableIds = new ArrayList<Long>();
			for (TableDTO tableDTO : tableDTOs) { 
				log.info(tableDTO.getName() + " oldTableId:" + tableDTO.getId());
				
				if (!Boolean.TRUE.equals(tableDTO.getReverse()) 
					&& tableMetadataService.isExist(tableDTO.getTableName())) {
					log.info("skip isExist table:" + tableDTO.getTableName());
					tableMap.put(tableDTO.getId(), tableDTO);
					continue;
				}
				
				Long newTableId = tableMetadataService.create(tableDTO);
				TableDTO newTableDTO = tableMetadataService.get(newTableId);
				
				log.info(tableDTO.getName() + " newTableId:" + newTableId +  ", newTableName:" + newTableDTO.getName());
				tableMap.put(tableDTO.getId(), newTableDTO);
				tableIds.add(newTableId);
			}
			
			//relation
			List<TableRelationDTO> tableRelationDTOs = metadataDTO.getTableRelationDTOList();
			for (TableRelationDTO tableRelationDTO : tableRelationDTOs) {
				Long fromTableId = tableRelationDTO.getFromTableDTO().getId();
				Long toTableId = tableRelationDTO.getToTableDTO().getId();
				
				TableDTO newFromTable = tableMap.get(fromTableId);
				TableDTO newToTable = tableMap.get(toTableId);
				
				if (newFromTable != null && newToTable != null) {
					if (newFromTable.getId().equals(fromTableId)
					&& newToTable.getId().equals(toTableId)) {
						log.info(newFromTable.getName() + "," + newToTable.getName() + "relation is exist, skip!");
						continue;
					}
				}
				
				tableRelationMetadataService.create(tableRelationDTO.getRelationType(), 
						tableRelationDTO.getName(), 
						tableRelationDTO.getCaption(), 
						newFromTable == null ? tableRelationDTO.getFromTableDTO().getName() : newFromTable.getName(),
						tableRelationDTO.getFromColumnDTO().getName(),
						newToTable == null ? tableRelationDTO.getToTableDTO().getName() : newToTable.getName(),
						tableRelationDTO.getToColumnDTO().getName());
			}
			
			List<Map<String, Object>> tableBuilderList = metadataDTO.getTableBuilderList();
			for (Map<String, Object> tableBuilder : tableBuilderList) {
				Long oldTableId = Long.parseLong(tableBuilder.get("tableId").toString());
				
				Long newTableId = oldTableId;
				TableDTO newTable = tableMap.get(oldTableId);
				if (newTable != null) {
					if (!newTable.getId().equals(oldTableId)) {
						newTableId = newTable.getId();
						tableBuilder.put("tableId", newTableId);
						if (tableBuilder.get("id") != null) {
							tableBuilder.remove("id");
						}
						
						Optional<TableDTO> oldTableOp = tableDTOs.stream().filter(t -> t.getId().equals(oldTableId)).findFirst();
						if (!oldTableOp.isPresent()) {
							throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, oldTableId + " is not present"); 
						}
						TableDTO oldTable = oldTableOp.get();
						
						String body = tableBuilder.get("body").toString();
						
						List<Map<String, Object>> tableBuilderColumnList = JsonUtils.toObject(body, new TypeReference<List<Map<String, Object>>>(){});
						for (Map<String, Object> tableBuilderColumn : tableBuilderColumnList) {
							Long oldColumnId = Long.parseLong(tableBuilderColumn.get("columnId").toString());
							ColumnDTO oldColumnDTO = oldTable.getColumnDTOList().stream().filter(t -> t.getId().equals(oldColumnId)).findFirst().get();
							String oldColumnName = oldColumnDTO.getName();
							
							ColumnDTO newColumnDTO = newTable.getColumnDTOList().stream().filter(t -> t.getName().equals(oldColumnName)).findFirst().get();
							
							tableBuilderColumn.put("columnId", newColumnDTO.getId());
							log.info(tableBuilderColumn.toString());
						}
						
						tableBuilder.put("body", JsonUtils.toJson(tableBuilderColumnList));
						
						tableService.create(TABLLE_FROM_BUILDER, tableBuilder);
					}
				}
			}

		} catch (Exception e) {
			throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, "导入失败！" + e.getMessage()); 
		}
	}
	
   
    @Override
    public String getExportFile(String name, List<Long> ids) {
		String fileName = null;
		try {
			fileName = fileService.getRandomFileName(name + ".json");
			File file = fileService.getFile(fileName);

			log.info(file.getAbsolutePath());
			
			//table
			List<TableDTO> tableDTOs = tableMetadataService.listAll(ids);
			
			//sequence
			List<Long> seqIds = new ArrayList<Long>();
			for (TableDTO tableDTO : tableDTOs) {
				for (ColumnDTO columnDTO : tableDTO.getColumnDTOList()) {
					Long seqId = columnDTO.getSeqId();
					if (seqId != null && seqIds.indexOf(seqId) < 0) {
						seqIds.add(seqId);
					}
				}
			}
			List<SequenceDTO> sequenceDTOs = sequenceMetadataService.list(seqIds);

			//relation
			List<TableRelationDTO> tableRelationDTOs = tableRelationMetadataService.list(ids);
			
			List<Object> values = new ArrayList<Object>();
			for (Long id : ids) {
				values.add(id);
			}
			
			Condition condition = ConditionUtils.toCondition("tableId", values);
			List<Map<String, Object>> tableBuilderList = tableService.list(TABLLE_FROM_BUILDER, null, null, null, null, condition, null, null, null);
			
			MetadataDTO metadataDTO = new MetadataDTO();
			metadataDTO.setSequenceDTOList(sequenceDTOs);
			metadataDTO.setTableDTOList(tableDTOs);
			metadataDTO.setTableRelationDTOList(tableRelationDTOs);
			metadataDTO.setTableBuilderList(tableBuilderList);
			String body = JsonUtils.toJson(metadataDTO);
			log.info(body);
			FileUtils.writeStringToFile(file, body, "utf-8");
			
 			return fileName;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, e.getMessage());
		}
	}
}
