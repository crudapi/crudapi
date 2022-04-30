package cn.crudapi.core.model;

import java.util.List;
import java.util.Map;

import cn.crudapi.core.dto.TableDTO;

public class QueryData {
	private TableDTO tableDTO;
	
	private List<String> selectList;
	
	private Map<String, List<String>> subSelectMap;
	
	private List<Map<String, Object>> tableDataMapList;
	
	public TableDTO getTableDTO() {
		return tableDTO;
	}

	public void setTableDTO(TableDTO tableDTO) {
		this.tableDTO = tableDTO;
	}

	public List<Map<String, Object>> getTableDataMapList() {
		return tableDataMapList;
	}

	public void setTableDataMapList(List<Map<String, Object>> tableDataMapList) {
		this.tableDataMapList = tableDataMapList;
	}
	
	public Map<String, List<String>> getSubSelectMap() {
		return subSelectMap;
	}

	public void setSubSelectMap(Map<String, List<String>> subSelectMap) {
		this.subSelectMap = subSelectMap;
	}

	public List<String> getSelectList() {
		return selectList;
	}

	public void setSelectList(List<String> selectList) {
		this.selectList = selectList;
	}
}
