package cn.crudapi.core.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import cn.crudapi.core.enumeration.ConditionTypeEnum;
import cn.crudapi.core.enumeration.DataTypeEnum;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompositeCondition implements Condition {
	private String name = "C";

	@JsonProperty("conditionType")
	private ConditionTypeEnum conditionType = ConditionTypeEnum.AND;

	@JsonProperty("conditions")
	private List<Condition> conditionList = new ArrayList<Condition>();

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ConditionTypeEnum getConditionType() {
		return conditionType;
	}

	public void setConditionType(ConditionTypeEnum conditionType) {
		this.conditionType = conditionType;
	}

	public List<Condition> getConditionList() {
		return conditionList;
	}

	public void setConditionList(List<Condition> conditionList) {
		this.conditionList = conditionList;
	}

	public void add(Condition condtion) {
		if (condtion != null) {
			conditionList.add(condtion);
		}
	}
	
	@Override
	public String toString() {
		return "CompositeCondition [name=" + name + ", conditionType=" + conditionType + ", conditionList="
				+ conditionList + "]";
	}

	@Override
	public String toQuerySql() {
		String querySql = "";

        if (CollectionUtils.isEmpty(conditionList)) {
            return querySql;
        }

        List<String> querySqlList = new ArrayList<String>();
        conditionList.stream().forEach(t -> {
        	querySqlList.add(t.toQuerySql());
        });

        String whereValues = String.join(" " + conditionType.toString() + " ", querySqlList);

        querySql = "(" + whereValues + ")";

        return querySql;
	}

	@Override
	public List<Object> toQueryValues() {
	    List<Object> queryValues = new ArrayList<Object>();
        conditionList.stream().forEach(t -> {
        	queryValues.addAll(t.toQueryValues());
        });

        return queryValues;
	}

	@Override
	public Map<String, Object> toQueryValueMap() {
		Map<String, Object> queryValueMap = new HashMap<String, Object>();
        conditionList.stream().forEach(t -> {
        	queryValueMap.putAll(t.toQueryValueMap());
        });

        return queryValueMap;
	}

	@Override
	public int build(String sqlQuotation, int seq, Map<String, DataTypeEnum> dataTypeMap) {
		for (Condition condition: conditionList) {
			seq = condition.build(sqlQuotation, seq, dataTypeMap);
		}
		
		return seq;
	}
}
