package cn.crudapi.core.model;

import java.util.List;

import cn.crudapi.core.dto.TableDTO;
import cn.crudapi.core.query.Condition;

public class QueryModel {
	private TableDTO tableDTO;
	private List<String> selectList;
	private Condition condition;
	private Integer offset;
	private Integer limit; 
	private String orderby;
	
	public TableDTO getTableDTO() {
		return tableDTO;
	}
	public void setTableDTO(TableDTO tableDTO) {
		this.tableDTO = tableDTO;
	}
	
	public Condition getCondition() {
		return condition;
	}
	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public String getOrderby() {
		return orderby;
	}
	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
	public List<String> getSelectList() {
		return selectList;
	}
	public void setSelectList(List<String> selectList) {
		this.selectList = selectList;
	}
}
