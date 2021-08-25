package cn.crudapi.core.util;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlExceptionUtil {
    public static final HashMap<String, String> UQ_MAP = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        {
            put("uq_bsm_sequence_name", "序列号名称不能重复");
            put("uq_bsm_table_name", "表名称不能重复");
            put("uq_bsm_table_plural_name", "表复数名称不能重复");
            put("uq_bsm_table_table_name", "表物理名称不能重复");
            put("uq_bsm_column_name", "列名称不能重复");
            put("uq_bsm_column_index_name", "列索引名称不能重复");
            put("uq_bsm_index_name", "索引名称不能重复");
        }
    };
    
    public static final HashMap<String, String> FK_MAP = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;
        {
            put("fk_bsm_index_line_column_id", "列已经建立索引，不能删除");
        }
    };

    
    //Column 'code' cannot be null;
    //PreparedStatementCallback; Column 'code' cannot be null; nested exception is java.sql.SQLIntegrityConstraintViolationException: Column 'code' cannot be null
    public static String parseNullError(String rawMsg) {
        String errorMsg = null;
        Pattern p = Pattern.compile("Column\\s'(\\S+)'\\scannot\\sbe\\snull", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(rawMsg);
        if (m.find()) {
            String key = m.group(1);
            errorMsg = key + " 不能为空";
        } 

        return errorMsg;
    }
    
	//    SQL [DELETE FROM `bs_meta_column` WHERE (`id` IN (?,?))]; 
	//    Cannot delete or update a parent row: a foreign key constraint fails (`crudapi`.`bs_meta_index_line`, CONSTRAINT `fk_bsm_index_line_column_id` FOREIGN KEY (`columnId`) REFERENCES `bs_meta_column` (`id`)); 
	//    nested exception is java.sql.SQLIntegrityConstraintViolationException: 
	//    Cannot delete or update a parent row: a foreign key constraint fails (`crudapi`.`bs_meta_index_line`, CONSTRAINT `fk_bsm_index_line_column_id` FOREIGN KEY (`columnId`) REFERENCES `bs_meta_column` (`id`))
    public static String parseFkError(String rawMsg) {
        String errorMsg = null;
        
        Pattern p = Pattern.compile("CONSTRAINT\\s`(\\S+)`\\sFOREIGN KEY", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(rawMsg);
        if (m.find()) {
        	String key = m.group(1);
            if (FK_MAP.containsKey(key)) {
                errorMsg = FK_MAP.get(key).toString();
            } else {
                errorMsg = key + "存在外键引用";
            }
        }

        return errorMsg;
    }
    
    // "Duplicate entry 'xxxxxx' for key 'uq_recruit_plan_phone'"
    public static String parseUQ1Error(String rawMsg) {
        String errorMsg = null;
        
        Pattern p = Pattern.compile("Duplicate.*for\\skey\\s'(\\S+)'", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(rawMsg);
        if (m.find()) {
        	String[] keys = m.group(1).split("\\.");
            if (keys.length > 1) {
	           	String key = keys[1];
	            if (UQ_MAP.containsKey(key)) {
	                errorMsg = UQ_MAP.get(key).toString();
	            } else {
	                errorMsg = key + "不能重复";
	            }
            }
        }

        return errorMsg;
    }
    
    //"message": "could not execute statement; SQL [n/a]; constraint [recruit_plan.uq_recruit_plan_phone]; nested exception is org.hibernate.exception.ConstraintViolationException: could not execute statement"
    public static String parseUQ2Error(String rawMsg) {
        String errorMsg = null;
        
        Pattern p = Pattern.compile("constraint\\s+\\[(.*)\\]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(rawMsg);
        if (m.find()) {
        	String[] keys = m.group(1).split("\\.");
            if (keys.length > 1) {
	           	String key = keys[1];
	            if (UQ_MAP.containsKey(key)) {
	                errorMsg = UQ_MAP.get(key).toString();
	            } else {
	                errorMsg = key + "不能重复";
	            }
            }
        }

        return errorMsg;
    }
    
    
    public static String parse(String rawMsg) {
    	if (rawMsg == null) {
    		return "null错误";
    	}
    	
        String errorMsg = rawMsg;
        
        errorMsg = parseNullError(rawMsg);
        if (errorMsg != null) {
        	return errorMsg;
        }
        
        errorMsg = parseUQ1Error(rawMsg);
        if (errorMsg != null) {
        	return errorMsg;
        }
        
        errorMsg = parseUQ2Error(rawMsg);
        if (errorMsg != null) {
        	return errorMsg;
        }
        
        errorMsg = parseFkError(rawMsg);
        if (errorMsg != null) {
        	return errorMsg;
        }

        return rawMsg;
    }
}
