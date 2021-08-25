package cn.crudapi.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cn.crudapi.core.enumeration.SequenceTypeEnum;

@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SequenceDTO extends AuditDTO {
	private Long id;

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
		return "SequenceDTO [id=" + id + ", minValue=" + minValue + ", maxValue=" + maxValue + ", nextValue="
				+ nextValue + ", incrementBy=" + incrementBy + ", cycle=" + cycle + ", currentTime=" + currentTime
				+ ", sequenceType=" + sequenceType + ", format=" + format + "]";
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
		SequenceDTO other = (SequenceDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
