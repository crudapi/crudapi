package cn.crudapi.core.query;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY,property = "name")
@JsonSubTypes(value = {
    @JsonSubTypes.Type(value = CompositeCondition.class, name = "C"),
    @JsonSubTypes.Type(value = LeafCondition.class, name = "L")
})

public interface Condition {
	String toQuerySql();

	List<Object> toQueryValues();
}
