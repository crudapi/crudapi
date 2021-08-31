package cn.crudapi.core.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import cn.crudapi.core.constant.ApiErrorCode;
import cn.crudapi.core.enumeration.ConditionTypeEnum;
import cn.crudapi.core.enumeration.OperatorTypeEnum;
import cn.crudapi.core.exception.BusinessException;
import cn.crudapi.core.query.CompositeCondition;
import cn.crudapi.core.query.Condition;
import cn.crudapi.core.query.LeafCondition;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URLDecoder;

public final class ConditionUtils {
	public static Condition toCondition(Map<String, Object> parameterMap) {
		return toCondition(parameterMap, ConditionTypeEnum.AND, OperatorTypeEnum.EQ);
	}
	
	public static Condition toCondition(Map<String, Object> parameterMap, 
			ConditionTypeEnum conditionType, 
			OperatorTypeEnum operatorType) {
		if (parameterMap == null || parameterMap.size() == 0) {
			return null;
		}
		
		CompositeCondition compositeCondition = new CompositeCondition();
		compositeCondition.setConditionType(conditionType);
		
		for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
		  String key = entry.getKey();
		  Object value = entry.getValue();
		  
		  if (value != null && !StringUtils.isEmpty(value.toString())) {
			  LeafCondition condition = new LeafCondition();
			  condition.setColumnName(key);
			  condition.setOperatorType(operatorType);
			  condition.addValue(value);
			  
			  compositeCondition.add(condition);
		  }
		}
		
		if (compositeCondition.getConditionList().size() > 0) { 
			return compositeCondition;
		} else {
			return null;
		}
	}
	
	public static Condition toCondition(String filter) {
		Condition filterCond = null;
		try {
			if (!StringUtils.isEmpty(filter)) {
				ObjectMapper objectMapper = new ObjectMapper();
				filterCond = objectMapper.readValue(URLDecoder.decode(filter, "UTF-8"), Condition.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(ApiErrorCode.JSON_ERROR, e.getMessage());
		}
		
		return filterCond;
	}
	
	public static Condition toCondition(String name, Object value) {
		LeafCondition condition = new LeafCondition();
		condition.setColumnName(name);
		condition.setOperatorType(OperatorTypeEnum.EQ);
		condition.addValue(value);
		
		return condition;
	}
	
	public static Condition toCondition(String name, List<Object> values) {
		if (values == null || values.size() == 0) {
			return null;
		}
		
		LeafCondition condition = new LeafCondition();
		condition.setColumnName(name);
		condition.setOperatorType(OperatorTypeEnum.IN);
		condition.setValueList(values);
		
		return condition;
	}
	
	public static Condition toCondition(String funcName, String name, Object value) {
		LeafCondition condition = new LeafCondition();
		condition.setFuncName(funcName);
		condition.setColumnName(name);
		condition.setOperatorType(OperatorTypeEnum.EQ);
		condition.addValue(value);
		
		return condition;
	}
	
	public static Condition toCondition(Condition condition1, Condition condition2) {
		Condition condition = null;
		
		if (condition1 == null && condition2 == null) {
			condition = null;
		} else if (condition1 == null && condition2 != null){
			condition = condition2;
		} else if (condition1 != null && condition2 == null){
			condition = condition1;
		} else {
			CompositeCondition compositeCondition = new CompositeCondition();
			compositeCondition.add(condition1);
			compositeCondition.add(condition2);
			condition = compositeCondition;
		}
		
		return condition;
	}
}

