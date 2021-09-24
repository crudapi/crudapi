package cn.crudapi.core.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetadataDTO {
	private List<SequenceDTO> sequenceDTOList;
	
	private List<TableDTO> tableDTOList;

	private List<TableRelationDTO> tableRelationDTOList;

	@JsonProperty("sequences")
	public List<SequenceDTO> getSequenceDTOList() {
		return sequenceDTOList;
	}

	public void setSequenceDTOList(List<SequenceDTO> sequenceDTOList) {
		this.sequenceDTOList = sequenceDTOList;
	}

	@JsonProperty("tables")
	public List<TableDTO> getTableDTOList() {
		return tableDTOList;
	}

	public void setTableDTOList(List<TableDTO> tableDTOList) {
		this.tableDTOList = tableDTOList;
	}

	@JsonProperty("tableRelations")
	public List<TableRelationDTO> getTableRelationDTOList() {
		return tableRelationDTOList;
	}

	public void setTableRelationDTOList(List<TableRelationDTO> tableRelationDTOList) {
		this.tableRelationDTOList = tableRelationDTOList;
	}
}
