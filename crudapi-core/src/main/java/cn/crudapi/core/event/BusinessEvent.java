package cn.crudapi.core.event;

import org.springframework.context.ApplicationEvent;


public class BusinessEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;

	private String tableName;

	public BusinessEvent(Object source, String tableName) {
		super(source);
		this.tableName = tableName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
