package cn.crudapi.core.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cn.crudapi.core.enumeration.EngineEnum;
import cn.crudapi.core.enumeration.IndexTypeEnum;

@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TableDTO extends AuditDTO {
	private Long id;

	private EngineEnum engine;

	private String pluralName;
	
	private String tableName;
	
	private Boolean reverse;
	
	private Boolean systemable;
	
    private Boolean readOnly;
	
	@JsonProperty("columns")
	private List<ColumnDTO> columnDTOList;

	@JsonProperty("indexs")
	private List<IndexDTO> indexDTOList;
	
	@JsonProperty("primaryNames")
	private List<String> primaryNameList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EngineEnum getEngine() {
		return engine;
	}

	public void setEngine(EngineEnum engine) {
		this.engine = engine;
	}

	public String getPluralName() {
		return pluralName;
	}

	public void setPluralName(String pluralName) {
		this.pluralName = pluralName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Boolean getReverse() {
		return reverse;
	}

	public void setReverse(Boolean reverse) {
		this.reverse = reverse;
	}

	public Boolean getSystemable() {
		return systemable;
	}

	public void setSystemable(Boolean systemable) {
		this.systemable = systemable;
	}

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public List<ColumnDTO> getColumnDTOList() {
		return columnDTOList;
	}

	public void setColumnDTOList(List<ColumnDTO> columnDTOList) {
		this.columnDTOList = columnDTOList;
	}

	public List<IndexDTO> getIndexDTOList() {
		return indexDTOList;
	}

	public void setIndexDTOList(List<IndexDTO> indexDTOList) {
		this.indexDTOList = indexDTOList;
	}
	
	
	public void setPrimaryNameList(List<String> primaryNameList) {
		this.primaryNameList = primaryNameList;
	}

	public List<String> getPrimaryNameList() {
		return primaryNameList;
	}
	
	private List<String> getPrimaryOrUniqueNameList(IndexTypeEnum indexTypeEnum) {
		Optional<ColumnDTO> opColumn = this.columnDTOList.stream().filter(t-> indexTypeEnum.equals(t.getIndexType())).findFirst();
		
		List<String> uniqueNameList = new ArrayList<String>();
		
		if (!opColumn.isPresent()) {
			Optional<IndexDTO> opIndex = this.indexDTOList.stream().filter(t -> indexTypeEnum.equals(t.getIndexType())).findFirst();
			if (opIndex.isPresent()) {
				List<IndexLineDTO> indexLineDTOList = opIndex.get().getIndexLineDTOList();
				for (IndexLineDTO indexLineDTO : indexLineDTOList) {
					ColumnDTO c = this.columnDTOList.stream().filter(t-> indexLineDTO.getColumnDTO().getId().equals(t.getId())).findFirst().get();
					uniqueNameList.add(c.getName());   	
				}
			}
		} else {
			uniqueNameList.add(opColumn.get().getName());   	
		}
		
		return uniqueNameList;
	}
	
	

	public void calculatePrimaryNameList() {
		List<String> primaryNameList = getPrimaryOrUniqueNameList(IndexTypeEnum.PRIMARY);
		
		if (primaryNameList.size() == 0) {
			primaryNameList = getPrimaryOrUniqueNameList(IndexTypeEnum.UNIQUE);
		}
		
		this.primaryNameList = primaryNameList;
	}
	
    @JsonIgnore
	public ColumnDTO getColumn(String name) {
		Optional<ColumnDTO> opColumn = this.columnDTOList.stream().filter(t-> name.equals(t.getName())).findFirst();
		
		return opColumn.get();
	}
	
	@JsonIgnore
	public boolean getAutoIncrement() {
		return this.columnDTOList.stream().filter(t-> Boolean.TRUE.equals(t.getAutoIncrement())).findFirst().isPresent();   	
	}
	 
	@Override
	public String toString() {
		return "TableDTO [id=" + id + ", engine=" + engine + ", pluralName=" + pluralName + ", tableName=" + tableName
				+ ", reverse=" + reverse + ", systemable=" + systemable + ", readOnly=" + readOnly + ", columnDTOList="
				+ columnDTOList + ", indexDTOList=" + indexDTOList + ", primaryNameList=" + primaryNameList + "]";
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
		TableDTO other = (TableDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
