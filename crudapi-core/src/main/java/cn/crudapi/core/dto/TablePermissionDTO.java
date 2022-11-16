package cn.crudapi.core.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TablePermissionDTO extends AuditDTO  implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long tableId;
	
	private String name;
	
	private List<Map<String, Object>> permissions;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTableId() {
		return tableId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Map<String, Object>> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Map<String, Object>> permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return "TablePermissionDTO [id=" + id + ", tableId=" + tableId + ", name=" + name + ", permissions="
				+ permissions + "]";
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
		TablePermissionDTO other = (TablePermissionDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
