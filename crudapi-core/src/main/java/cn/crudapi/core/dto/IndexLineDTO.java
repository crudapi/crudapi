package cn.crudapi.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder(alphabetic = true)
public class IndexLineDTO {
	private Long id;

	@JsonProperty("column")
	private ColumnDTO columnDTO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ColumnDTO getColumnDTO() {
		return columnDTO;
	}

	public void setColumnDTO(ColumnDTO columnDTO) {
		this.columnDTO = columnDTO;
	}

	@Override
	public String toString() {
		return "IndexLineDTO [id=" + id + ", columnDTO=" + columnDTO + "]";
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
		IndexLineDTO other = (IndexLineDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
