package cn.crudapi.core.dto;

import org.joda.time.DateTime;


public abstract class AuditDTO extends BaseDTO {
	private DateTime createdDate;

	private DateTime lastModifiedDate;

	public DateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(DateTime createdDate) {
		this.createdDate = createdDate;
	}

	public DateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(DateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@Override
	public String toString() {
		return "AuditDTO [createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate + "]";
	}
}
