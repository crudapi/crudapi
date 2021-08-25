package cn.crudapi.core.query;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.crudapi.core.enumeration.OperatorTypeEnum;
import cn.crudapi.core.util.DbUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(alphabetic = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeafCondition implements Condition {
	private String name = "L";

	private String columnName;
	
	private String funcName;
	
	//INSELECT
	private String inTableName;
	
	//INSELECT
	private Condition inCondition;

	@JsonProperty("operatorType")
	private OperatorTypeEnum operatorType;

	@JsonProperty("values")
	private List<Object> valueList = new ArrayList<Object>();

	
	
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
		if (OperatorTypeEnum.LIKE.equals(operatorType)) {
			List<Object> newValueList = new ArrayList<Object>();
			valueList.stream().forEach( t-> {
				newValueList.add("%" +  t + "%");
			});
			
			return newValueList;
		} else if (OperatorTypeEnum.INSELECT.equals(operatorType)) {
			return inCondition.toQueryValues();
		}
		return valueList == null ? new ArrayList<Object>() : valueList;
	}

	@Override
	public String toQuerySql() {
		String querySql = "";

        switch (operatorType)
        {
            case EQ:
            	querySql = toQuerySqlForEQ();
                break;
            case NE:
            	querySql = toQuerySqlForNE();
                break;
            case GE:
            	querySql = toQuerySqlForGE();
                break;
            case GT:
            	querySql = toQuerySqlForGT();
                break;
            case LE:
            	querySql = toQuerySqlForLE();
                break;
            case LT:
            	querySql = toQuerySqlForLT();
                break;    
            case LIKE:
            	querySql = toQuerySqlForLIKE();
                break;
            case BETWEEN:
            	querySql = toQuerySqlForBETWEEN();
                break;
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
            	querySql = toQuerySqlForINSELECT();
                break;
            case SEARCH:
            	querySql = toQuerySqlForSEARCH();
                break;
            default:
                break;
        }

        return  "(" + querySql + ")";
	}

    private String toQuerySqlForEQ() {
    	String querySql = "";
    	
    	if (StringUtils.isAllBlank(funcName)) {
    		querySql = DbUtils.toNameSql(columnName) + " = ?";
    	} else {
    		querySql = funcName + "(" + DbUtils.toNameSql(columnName) + ")" + " = ?";
    	}
       
        return querySql;
    }
    
    private String toQuerySqlForNE() {
    	String querySql = "";

        querySql = DbUtils.toNameSql(columnName) + " != ?";

        return querySql;
    }
    
    private String toQuerySqlForGE() {
    	String querySql = "";

        querySql = DbUtils.toNameSql(columnName) + " >= ?";

        return querySql;
    }
    
    private String toQuerySqlForGT() {
    	String querySql = "";

        querySql = DbUtils.toNameSql(columnName) + " > ?";

        return querySql;
    }
    
    private String toQuerySqlForLE() {
    	String querySql = "";

        querySql = DbUtils.toNameSql(columnName) + " <= ?";

        return querySql;
    }
    
    private String toQuerySqlForLT() {
    	String querySql = "";

        querySql = DbUtils.toNameSql(columnName) + " < ?";

        return querySql;
    }

    private String toQuerySqlForBETWEEN() {
    	String querySql = "";

        querySql = DbUtils.toNameSql(columnName) + " BETWEEN ? AND ?";

        return querySql;
    }
    
    private String toQuerySqlForSEARCH() {
    	StringBuilder sb = new StringBuilder();
        sb.append("MATCH({0}) AGAINST(? IN BOOLEAN MODE)");
        String pattern = sb.toString();
        Object[] arguments = { DbUtils.toNameSql(columnName) };

        String querySql = MessageFormat.format(pattern, arguments);

        return querySql;
    }

    private String toQuerySqlForLIKE() {
    	String querySql = "";

        querySql = DbUtils.toNameSql(columnName) + " LIKE ?";

        return querySql;
    }

    private String toQuerySqlForIN() {
    	String querySql = "";

	   List<String> whereValueList = new ArrayList<String>();
	   valueList.stream().forEach(t -> {
		   whereValueList.add("?");
       });

	   String whereValues = String.join(",", whereValueList);

       querySql = DbUtils.toNameSql(columnName) + " IN (" + whereValues + ")";

       return querySql;
    }
    
    private String toQuerySqlForISNULL() {
    	String querySql = "";

        querySql = DbUtils.toNameSql(columnName) + " IS NULL";

        return querySql;
    }
    
    private String toQuerySqlForISNOTNULL() {
    	String querySql = "";

        querySql = DbUtils.toNameSql(columnName) + " IS NOT NULL";

        return querySql;
    }
    
    
    //candidateId in (SELECT id FROM `bs_candidate` where `type` = 'chef') 
    private String toQuerySqlForINSELECT() {
       String querySql = "";

       String selectSql = "SELECT `id` FROM " + DbUtils.toNameSql(inTableName);
       selectSql += "where ";
       selectSql += inCondition.toQuerySql();
       
       querySql = DbUtils.toNameSql(columnName) + " IN (" + selectSql + ")";

       return querySql;
    }
}
