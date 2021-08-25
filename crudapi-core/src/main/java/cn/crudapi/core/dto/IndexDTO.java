package cn.crudapi.core.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cn.crudapi.core.enumeration.IndexStorageEnum;
import cn.crudapi.core.enumeration.IndexTypeEnum;

@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)

public class IndexDTO extends AuditDTO {
	private Long id;

	private IndexTypeEnum indexType;

	private IndexStorageEnum indexStorage;

	@JsonProperty("indexLines")
	private List<IndexLineDTO> indexLineDTOList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public IndexTypeEnum getIndexType() {
		return indexType;
	}

	public void setIndexType(IndexTypeEnum indexType) {
		this.indexType = indexType;
	}

	public IndexStorageEnum getIndexStorage() {
		return indexStorage;
	}

	public void setIndexStorage(IndexStorageEnum indexStorage) {
		this.indexStorage = indexStorage;
	}

	public List<IndexLineDTO> getIndexLineDTOList() {
		return indexLineDTOList;
	}

	public void setIndexLineDTOList(List<IndexLineDTO> indexLineDTOList) {
		this.indexLineDTOList = indexLineDTOList;
	}

	@Override
	public String toString() {
		return "IndexDTO [id=" + id + ", indexType=" + indexType + ", indexStorage=" + indexStorage
				+ ", indexLineDTOList=" + indexLineDTOList + "]";
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
		IndexDTO other = (IndexDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
