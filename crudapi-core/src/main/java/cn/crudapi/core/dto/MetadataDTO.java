package cn.crudapi.core.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetadataDTO {
	@JsonProperty("sequences")
	private List<SequenceDTO> sequenceDTOList;
	
	@JsonProperty("tables")
	private List<TableDTO> tableDTOList;

	@JsonProperty("tableRelations")
	private List<TableRelationDTO> tableRelationDTOList;

	public List<SequenceDTO> getSequenceDTOList() {
		return sequenceDTOList;
	}

	public void setSequenceDTOList(List<SequenceDTO> sequenceDTOList) {
		this.sequenceDTOList = sequenceDTOList;
	}


	public List<TableDTO> getTableDTOList() {
		return tableDTOList;
	}


	public void setTableDTOList(List<TableDTO> tableDTOList) {
		this.tableDTOList = tableDTOList;
	}


	public List<TableRelationDTO> getTableRelationDTOList() {
		return tableRelationDTOList;
	}


	public void setTableRelationDTOList(List<TableRelationDTO> tableRelationDTOList) {
		this.tableRelationDTOList = tableRelationDTOList;
	}
}
