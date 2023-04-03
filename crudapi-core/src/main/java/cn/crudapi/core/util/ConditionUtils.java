package cn.crudapi.core.util;

import java.util.ArrayList;
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
		return toCondition(parameterMap, ConditionTypeEnum.AND, OperatorTypeEnum.EQ, null);
	}

	public static Condition toCondition(Map<String, Object> parameterMap, Map<String, OperatorTypeEnum> operatorTypeMap) {
		return toCondition(parameterMap, ConditionTypeEnum.AND, OperatorTypeEnum.EQ, operatorTypeMap);
	}
	
	public static Condition toCondition(Map<String, Object> parameterMap, 
			ConditionTypeEnum conditionType, 
			OperatorTypeEnum operatorType,
			Map<String, OperatorTypeEnum> operatorTypeMap) {
		if (parameterMap == null || parameterMap.size() == 0) {
			return null;
		}
		
		CompositeCondition compositeCondition = new CompositeCondition();
		compositeCondition.setConditionType(conditionType);
		
		for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
		  String key = entry.getKey();
		  
		  OperatorTypeEnum newOperatorType = operatorType;
		  if (operatorTypeMap != null && operatorTypeMap.get(key) != null) {
			  newOperatorType = operatorTypeMap.get(key);
		  }
		  
		  Object value = entry.getValue();
		  
		  if (value != null && !StringUtils.isEmpty(value.toString().trim())) {
			  String valueStr = value.toString().trim();
			  
			  LeafCondition condition = new LeafCondition();
			  condition.setColumnName(key);
			  if (newOperatorType.equals(OperatorTypeEnum.MLIKE)) {
       			String[] valueArr = valueStr.split(",");
      			for (String v : valueArr) { 
      				if (!v.isEmpty()) {
      					condition.addValue(v);
      				}
      			}
			  } else {
				  //name=LIKE crudapi&productCount=LE 2&quantity=BETWEEN%20100,200
				  List<String> opValueList = new ArrayList<String>();
				  String[] opValueArr = valueStr.replaceFirst(" +", ";").split(";");
				  for (String ov : opValueArr) { 
					if (!ov.isEmpty()) {
						opValueList.add(ov);
					}
	    		  }
				  
				  if (opValueList.size() > 1) {
					  OperatorTypeEnum opt = null;
					  try {
						  opt = OperatorTypeEnum.valueOf(opValueList.get(0));
					  } catch (Exception e) {
						  
					  }
					  
					  String newValueStr = valueStr;
					  if (opt != null) {
						  newOperatorType = opt;
						  newValueStr = valueStr.replaceFirst(opValueList.get(0), "").trim();
					  }
					  
					  String[] newValueArr = newValueStr.split(",");
					  
					  if (newOperatorType.equals(OperatorTypeEnum.BETWEEN)) {
						  if (newValueArr.length > 1) {
							  String beginValue = newValueArr[0];
							  String endValue = newValueArr[1];
							  if (!beginValue.isEmpty() && !endValue.isEmpty()) { 
								  condition.addValue(beginValue);
								  condition.addValue(endValue);
							  } else if (beginValue.isEmpty() && !endValue.isEmpty()) {
								  newOperatorType = OperatorTypeEnum.LE;
								  condition.addValue(endValue);
							  } else if (!beginValue.isEmpty() && endValue.isEmpty()) {
								  newOperatorType = OperatorTypeEnum.GE;
								  condition.addValue(beginValue);
							  } else if (beginValue.isEmpty() && endValue.isEmpty()) { 
								  condition = null;
							  }
						  } else if (newValueArr.length == 1) {
							  newOperatorType = OperatorTypeEnum.GE;
							  condition.addValue(newValueArr[0]);
						  } else {
							  condition = null;
						  }
					  } else {
						  for (String v : newValueArr) { 
		      				if (!v.isEmpty()) {
		      					condition.addValue(v);
		      				}
		      			  }
					  }
					  
					  //change = to in
					  if (newOperatorType.equals(OperatorTypeEnum.EQ)  
						&& condition.getValueList().size() > 0 ) {
						  newOperatorType = OperatorTypeEnum.IN;
					  }
					  
				  } else {
					  if (valueStr.equalsIgnoreCase("ISNULL")
						|| valueStr.equalsIgnoreCase("ISNOTNULL")) {
						  OperatorTypeEnum opt = OperatorTypeEnum.valueOf(valueStr);
						  newOperatorType = opt;
						  condition.addValue(valueStr);
					  } else {
						  condition.addValue(value);
					  }
				  }
			  }
			  
			  if (condition != null) {
				  condition.setOperatorType(newOperatorType);
				  compositeCondition.add(condition);
			  }
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

