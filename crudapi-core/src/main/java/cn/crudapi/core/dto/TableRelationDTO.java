package cn.crudapi.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import cn.crudapi.core.enumeration.TableRelationTypeEnum;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TableRelationDTO extends AuditDTO {
	private Long id;

    private TableRelationTypeEnum relationType;

	@JsonProperty("fromTable")
    private TableDTO fromTableDTO;

	@JsonProperty("toTable")
    private TableDTO toTableDTO;

	@JsonProperty("fromColumn")
 	private ColumnDTO fromColumnDTO;

	@JsonProperty("toColumn")
 	private ColumnDTO toColumnDTO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TableRelationTypeEnum getRelationType() {
		return relationType;
	}

	public void setRelationType(TableRelationTypeEnum relationType) {
		this.relationType = relationType;
	}

	public TableDTO getFromTableDTO() {
		return fromTableDTO;
	}

	public void setFromTableDTO(TableDTO fromTableDTO) {
		this.fromTableDTO = fromTableDTO;
	}

	public TableDTO getToTableDTO() {
		return toTableDTO;
	}

	public void setToTableDTO(TableDTO toTableDTO) {
		this.toTableDTO = toTableDTO;
	}

	public ColumnDTO getFromColumnDTO() {
		return fromColumnDTO;
	}

	public void setFromColumnDTO(ColumnDTO fromColumnDTO) {
		this.fromColumnDTO = fromColumnDTO;
	}

	public ColumnDTO getToColumnDTO() {
		return toColumnDTO;
	}

	public void setToColumnDTO(ColumnDTO toColumnDTO) {
		this.toColumnDTO = toColumnDTO;
	}

	@Override
	public String toString() {
		return "TableRelationDTO [id=" + id + ", relationType=" + relationType + ", fromTableDTO=" + fromTableDTO
				+ ", toTableDTO=" + toTableDTO + ", fromColumnDTO=" + fromColumnDTO + ", toColumnDTO=" + toColumnDTO
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TableRelationDTO other = (TableRelationDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
