package cn.crudapi.core.query;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import cn.crudapi.core.constant.ApiErrorCode;
import cn.crudapi.core.enumeration.DataTypeEnum;
import cn.crudapi.core.enumeration.OperatorTypeEnum;
import cn.crudapi.core.exception.BusinessException;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeafCondition implements Condition {
	private String name = "L";

	private Map<String, DataTypeEnum> dataTypeMap;
	
	private String columnName;
	
	private String funcName;
	
	//INSELECT
	private String inColumnName;
	
	//INSELECT
	private String inTableName;
	
	//INSELECT
	private Condition inCondition;

	@JsonProperty("operatorType")
	private OperatorTypeEnum operatorType;

	@JsonProperty("values")
	private List<Object> valueList = new ArrayList<Object>();

	private List<String> valueParamNameList = null;
	
	private boolean namedParameter = false;
	
	private String sqlQuotation = "`";
	
	@Override
	public int build(String sqlQuotation, int seq, Map<String, DataTypeEnum> dataTypeMap) {
		this.namedParameter = true;
		this.sqlQuotation = sqlQuotation;
		this.dataTypeMap = dataTypeMap;
		this.valueParamNameList = new ArrayList<String>();
		for (int i = 0; i < valueList.size(); ++i) {
			this.valueParamNameList.add("LeafCondition" + seq++);
		}
		
		if (inCondition != null) {
			seq = inCondition.build(sqlQuotation, seq, dataTypeMap);
		}
		
		return seq;
	}

	public String toSqlName(String colunmName) {
		if (colunmName.equals("*")) {
			return colunmName;
		} else {
			String[] colunmNameArray = colunmName.split("\\.");
			List<String> colunmNameList = new ArrayList<String>();
			for (String t :colunmNameArray) {
				StringBuilder sb = new StringBuilder();
				sb.append(sqlQuotation);
				sb.append(t);
				sb.append(sqlQuotation);
				colunmNameList.add(sb.toString());
			}
			return String.join(".", colunmNameList);
		}
	}

	
	public String toSqlValue(String colunmName) {
		if (!namedParameter) {
			return "?";
		}
        StringBuilder sb = new StringBuilder();
	    sb.append(":");
        sb.append(colunmName);
        return sb.toString();
    }
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getInColumnName() {
		return inColumnName;
	}
	public void setInColumnName(String inColumnName) {
		this.inColumnName = inColumnName;
	}
	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getInTableName() {
		return inTableName;
	}

	public void setInTableName(String inTableName) {
		this.inTableName = inTableName;
	}

	public Condition getInCondition() {
		return inCondition;
	}

	public void setInCondition(Condition inCondition) {
		this.inCondition = inCondition;
	}

	public OperatorTypeEnum getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(OperatorTypeEnum operatorType) {
		this.operatorType = operatorType;
	}

	public List<Object> getValueList() {
		return valueList;
	}

	public void setValueList(List<Object> valueList) {
		this.valueList = valueList;
	}

	
	@Override
	public String toString() {
		return "LeafCondition [name=" + name + ", columnName=" + columnName + ", funcName=" + funcName
				+ ", inTableName=" + inTableName + ", inCondition=" + inCondition + ", operatorType=" + operatorType
				+ ", valueList=" + valueList + "]";
	}

	public void setValue(Object value) {
		valueList = new ArrayList<Object>();
		valueList.add(value);
	}

	public void addValue(Object value) {
		if (valueList == null) {
			valueList = new ArrayList<Object>();
		}

		valueList.add(value);
	}

	@Override
	public List<Object> toQueryValues() {
		if (OperatorTypeEnum.LIKE.equals(operatorType)
			|| OperatorTypeEnum.MLIKE.equals(operatorType)) {
			List<Object> newValueList = new ArrayList<Object>();
			valueList.stream().forEach( t-> {
				newValueList.add("%" +  t + "%");
			});
			
			return newValueList;
		} else if (OperatorTypeEnum.INSELECT.equals(operatorType)
			|| OperatorTypeEnum.NOTINSELECT.equals(operatorType)) {
			return inCondition == null ? new ArrayList<Object>() : inCondition.toQueryValues();
		}
		return valueList == null ? new ArrayList<Object>() : valueList;
	}
	
	@Override
	public Map<String, Object> toQueryValueMap() {
		if (namedParameter && valueParamNameList == null) {
			throw new BusinessException(ApiErrorCode.VALIDATED_ERROR, "condition is not init");
		}
		
		if (OperatorTypeEnum.INSELECT.equals(operatorType)
			|| OperatorTypeEnum.NOTINSELECT.equals(operatorType)) {
			return inCondition == null ? new HashMap<String, Object>() : inCondition.toQueryValueMap();
		}
		
		List<Object> newValueList = valueList;
		if (OperatorTypeEnum.LIKE.equals(operatorType)
			|| OperatorTypeEnum.MLIKE.equals(operatorType)
			|| OperatorTypeEnum.SEARCH.equals(operatorType)) {
			newValueList = new ArrayList<Object>();
			for (Object t : valueList) {
				newValueList.add("%" +  t + "%");
			}
		}
		
		Map<String, Object> queryValueMap = new HashMap<String, Object>();
		for (int i = 0; i < newValueList.size(); ++i) {
			Object obj = newValueList.get(i);
			
			DataTypeEnum dataType = (dataTypeMap != null) ? dataTypeMap.get(columnName): null;
			
			Object newObj = obj;
			if (dataType != null) {
				String objStr = obj.toString();
				if (dataType.equals(DataTypeEnum.BIGINT)) {
					newObj = Long.parseLong(objStr);
				} else if (dataType.equals(DataTypeEnum.INT)) {
        			newObj = Integer.parseInt(objStr);
        		}  else if (dataType.equals(DataTypeEnum.TINYINT)) {
        			newObj = Integer.parseInt(objStr);
        		} else if (dataType.equals(DataTypeEnum.DOUBLE)) {
        			newObj = Double.parseDouble(objStr);
        		}  else if (dataType.equals(DataTypeEnum.FLOAT)) {
        			newObj = Float.parseFloat(objStr);
        		}  else if (dataType.equals(DataTypeEnum.DECIMAL)) {
        			newObj = new BigDecimal(objStr);
        		} else if (dataType.equals(DataTypeEnum.BOOL)) {
        			if (objStr.equals("1") || objStr.toUpperCase().equals("TRUE")) {
        				newObj = true;
        			} else {
            			newObj = Boolean.parseBoolean(objStr);
        			}
        		}
			} 
			queryValueMap.put(valueParamNameList.get(i), newObj);
		}
		
		return queryValueMap;
	}

	@Override
	public String toQuerySql() {
		if (namedParameter && valueParamNameList == null) {
			throw new BusinessException(ApiErrorCode.VALIDATED_ERROR, "condition is not init");
		}
		String querySql = "";

        switch (operatorType)
        {
            case EQ:
            case NE:
            case GE:
            case GT:
            case LE:
            case LT:   
            case LIKE:
            case SEARCH:
            	querySql = toBasicQuerySql();
                break;
            case MLIKE:
            	querySql = toQuerySqlForMLIKE();
                break;
            case BETWEEN:
            	querySql = toQuerySqlForBETWEEN();
                break;
            case NOTIN: 
            case IN: 
            	querySql = toQuerySqlForIN();
            	break;
            case ISNULL:
            	querySql = toQuerySqlForISNULL();
                break;
            case ISNOTNULL:
            	querySql = toQuerySqlForISNOTNULL();
                break; 
            case INSELECT:
            case NOTINSELECT:
            	querySql = toQuerySqlForINSELECT();
                break;
            default:
                break;
        }

        return  "(" + querySql + ")";
	}

	private String toBasicQuerySql() {
		StringBuilder sb = new StringBuilder();
		    
		String operatorTypeStr = "";
		
        switch (operatorType)
        {
            case EQ:
            	operatorTypeStr = "=";
                break;
            case NE:
            	operatorTypeStr = "!=";
                break;
            case GE:
            	operatorTypeStr = ">=";
                break;
            case GT:
            	operatorTypeStr = ">";
                break;
            case LE:
            	operatorTypeStr = "<=";
                break;
            case LT:
            	operatorTypeStr = "<";
                break;    
            case LIKE:
            case SEARCH:
            	operatorTypeStr = "LIKE";
                break;
            default:
                break;
        }
       
        if (!StringUtils.isAllBlank(funcName)) {
        	sb.append(funcName);
        	sb.append("(");
    	}
        
        sb.append(toSqlName(columnName));
        
        if (!StringUtils.isAllBlank(funcName)) {
        	sb.append(")");
    	}
        
        sb.append(" ");
        sb.append(operatorTypeStr);
        sb.append(" ");
        sb.append(namedParameter ? toSqlValue(valueParamNameList.get(0)) : "?");
        
        return sb.toString();
    }
	
    private String toQuerySqlForMLIKE() {
        List<String> likeSqlList = new ArrayList<String>();
        for (int i = 0; i <  valueList.size(); ++i) {
        	StringBuilder sb = new StringBuilder();
        	sb.append(toSqlName(columnName));
 	    	sb.append(" LIKE ");
 	        sb.append(namedParameter ? toSqlValue(valueParamNameList.get(i)) : "?");
 	    	likeSqlList.add("(" + sb.toString() + ")");
        }
        
        return String.join(" OR ", likeSqlList);
    }
   
    private String toQuerySqlForBETWEEN() {
    	StringBuilder sb = new StringBuilder();
    	sb.append(toSqlName(columnName));
        sb.append(" BETWEEN ");
        sb.append(namedParameter ? toSqlValue(valueParamNameList.get(0)) : "?");
        sb.append(" AND ");
        sb.append(namedParameter ? toSqlValue(valueParamNameList.get(1)) : "?");
        
        return sb.toString();
    }
    
    private String toQuerySqlForSEARCH() {
    	StringBuilder sb = new StringBuilder();
        sb.append("MATCH({0}) AGAINST({1} IN BOOLEAN MODE)");
        String pattern = sb.toString();
        Object[] arguments = { toSqlName(columnName), namedParameter ? toSqlValue(valueParamNameList.get(0)) : "?"};

        String querySql = MessageFormat.format(pattern, arguments);

        return querySql;
    }

    private String toQuerySqlForIN() {
	   List<String> whereValueList = new ArrayList<String>();
	   
	   if (namedParameter) {
		   for (String valueParamName : valueParamNameList) {
			   whereValueList.add(toSqlValue(valueParamName));
		   }
	   } else {
		   for (Object value : valueList) {
			   whereValueList.add("?");
		   }
	   }
	   
	   String whereValues = String.join(",", whereValueList);

       StringBuilder sb = new StringBuilder();
   	   sb.append(toSqlName(columnName));
   	   if (operatorType.equals(OperatorTypeEnum.IN)) {
   		   sb.append(" IN (");
   	   } else {
   		   sb.append(" NOT IN ("); 
   	   }
       
       sb.append(whereValues);
       sb.append(")");
       
       return sb.toString();
    }
    
    private String toQuerySqlForISNULL() {
    	String querySql = "";

        querySql = toSqlName(columnName) + " IS NULL";

        return querySql;
    }
    
    private String toQuerySqlForISNOTNULL() {
    	String querySql = "";

        querySql = toSqlName(columnName) + " IS NOT NULL";

        return querySql;
    }
    
    
    //candidateId in (SELECT id FROM `bs_candidate` where `type` = 'chef') 
    private String toQuerySqlForINSELECT() {
       String querySql = "";
       
       String inTableNameSql = inTableName.contains(" ") ? inTableName  : toSqlName(inTableName);
       
       String selectSql = "SELECT " + toSqlName(inColumnName) + " FROM " + inTableNameSql;
       if (inCondition != null) {
    	   selectSql += " WHERE ";
           selectSql += inCondition.toQuerySql();
       }
       
   
       if (operatorType.equals(OperatorTypeEnum.INSELECT)) {
    	   querySql = toSqlName(columnName) + " IN (" + selectSql + ")";
   	   } else {
   		   querySql = toSqlName(columnName) + " NOT IN (" + selectSql + ")";
   	   }
       
       return querySql;
    }
}
