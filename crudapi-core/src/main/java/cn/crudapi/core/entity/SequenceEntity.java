package cn.crudapi.core.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import cn.crudapi.core.enumeration.SequenceTypeEnum;

public class SequenceEntity implements BaseEntity {
	private static final String TABLE_NAME = "ca_meta_sequence";
	
	private Long id;

	private String name;

	private String caption;

	private String description;

	private Timestamp createdDate;

	private Timestamp lastModifiedDate;

	private Long minValue;

	private Long maxValue;

	private Long nextValue;

	private Long incrementBy;

    private Boolean cycle;

    private Boolean currentTime;

	private SequenceTypeEnum sequenceType;

	private String format;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Long getMinValue() {
		return minValue;
	}

	public void setMinValue(Long minValue) {
		this.minValue = minValue;
	}

	public Long getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Long maxValue) {
		this.maxValue = maxValue;
	}

	public Long getNextValue() {
		return nextValue;
	}

	public void setNextValue(Long nextValue) {
		this.nextValue = nextValue;
	}

	public Long getIncrementBy() {
		return incrementBy;
	}

	public void setIncrementBy(Long incrementBy) {
		this.incrementBy = incrementBy;
	}

	public Boolean getCycle() {
		return cycle;
	}

	public void setCycle(Boolean cycle) {
		this.cycle = cycle;
	}

	public Boolean getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Boolean currentTime) {
		this.currentTime = currentTime;
	}

	public SequenceTypeEnum getSequenceType() {
		return sequenceType;
	}

	public void setSequenceType(SequenceTypeEnum sequenceType) {
		this.sequenceType = sequenceType;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	
	@Override
	public String toString() {
		return "SequenceEntity [id=" + id + ", name=" + name + ", caption=" + caption + ", description=" + description
				+ ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate + ", minValue=" + minValue
				+ ", maxValue=" + maxValue + ", nextValue=" + nextValue + ", incrementBy=" + incrementBy + ", cycle="
				+ cycle + ", currentTime=" + currentTime + ", sequenceType=" + sequenceType + ", format=" + format
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
		SequenceEntity other = (SequenceEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public List<String> getColumnNamesIgnoreNull(Boolean isUpdate) {
		List<String> columnNames = new ArrayList<String>();
		if (name != null) { columnNames.add("name"); }
		if (caption != null) { columnNames.add("caption"); }
		if (description != null) { columnNames.add("description"); }
		if (createdDate != null && isUpdate == false) { columnNames.add("createdDate"); }
		if (lastModifiedDate != null) { columnNames.add("lastModifiedDate"); }
		if (minValue != null) { columnNames.add("minValue"); }
		if (maxValue != null) { columnNames.add("maxValue"); }
		if (nextValue != null) { columnNames.add("nextValue"); }
		if (incrementBy != null) { columnNames.add("incrementBy"); }
		if (cycle != null) { columnNames.add("cycle"); }
		if (currentTime != null) { columnNames.add("currentTime"); }
		if (sequenceType != null) { columnNames.add("sequenceType"); }
		if (format != null) { columnNames.add("format"); }
		return columnNames;
	}
	
	@Override
	public List<String> getColumnNames(Boolean isUpdate) {
		List<String> columnNames = new ArrayList<String>();
		columnNames.add("name");
		columnNames.add("caption"); 
		columnNames.add("description");
		if (isUpdate == false) { 
			columnNames.add("createdDate"); 
		}
		columnNames.add("lastModifiedDate");
		columnNames.add("minValue");
		columnNames.add("maxValue");
		columnNames.add("nextValue");
		columnNames.add("incrementBy");
		columnNames.add("cycle");
		columnNames.add("currentTime");
		columnNames.add("sequenceType");
		columnNames.add("format");
		return columnNames;
	}
	
	@Override
	public List<Object> getColumnValues(Boolean isUpdate) {
		List<Object> columnValues = new ArrayList<Object>();
		columnValues.add(name);
		columnValues.add(caption);
		columnValues.add(description);
		if (isUpdate == false) { 
			columnValues.add(createdDate); 
		}
		columnValues.add(lastModifiedDate);
		columnValues.add(minValue);
		columnValues.add(maxValue);
		columnValues.add(nextValue);
		columnValues.add(incrementBy);
		columnValues.add(cycle);
		columnValues.add(currentTime);
		columnValues.add(sequenceType != null ? sequenceType.toString() : null);
		columnValues.add(format);
		return columnValues;
	}
	
	@Override
	public List<Object> getColumnValuesIgnoreNull(Boolean isUpdate) {
		List<Object> columnValues = new ArrayList<Object>();
		if (name != null) { columnValues.add(name); }
		if (caption != null) { columnValues.add(caption); }
		if (description != null) { columnValues.add(description); }
		if (createdDate != null && isUpdate == false) { columnValues.add(createdDate); }
		if (lastModifiedDate != null) { columnValues.add(lastModifiedDate); }
		if (minValue != null) { columnValues.add(minValue); }
		if (maxValue != null) { columnValues.add(maxValue); }
		if (nextValue != null) { columnValues.add(nextValue); }
		if (incrementBy != null) { columnValues.add(incrementBy); }
		if (cycle != null) { columnValues.add(cycle); }
		if (currentTime != null) { columnValues.add(currentTime); }
		if (sequenceType != null) { columnValues.add(sequenceType.toString()); }
		if (format != null) { columnValues.add(format); }
		return columnValues;
	}

	@Override
	public String getDataBaseTableName() {
		return TABLE_NAME;
	}

	@Override
	public Long getRecId() {
		return id;
	}
}
