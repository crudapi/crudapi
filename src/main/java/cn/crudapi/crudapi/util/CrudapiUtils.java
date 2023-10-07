package cn.crudapi.crudapi.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import cn.crudapi.crudapi.constant.ApiErrorCode;
import cn.crudapi.crudapi.constant.Naming;
import cn.crudapi.crudapi.exception.BusinessException;

public class CrudapiUtils {
	public static String lowerCamelToLowerUnderscore(String param) {
	    if (param == null || "".equals(param.trim())) {
	        return "";
	    }
	    int len = param.length();
	    StringBuilder sb = new StringBuilder(len);
	    for (int i = 0; i < len; i++) {
	        char c = param.charAt(i);
	        if (Character.isUpperCase(c)) {
	            sb.append("_").append(Character.toLowerCase(c));
	        } else {
	            sb.append(c);
	        }
	    }
	    return sb.toString();
	}

	public static String lowerHyphenToLowerUnderscore(String param) {
	    if (param == null || "".equals(param.trim())) {
	        return "";
	    }
	    
	    return param.replace("-", "_");
	}
	
	public static String lowerUnderscoreToLowerCamel(String param) {
	    if (!StringUtils.hasLength(param)) {
	        return "";
	    }
	    
	    int len = param.length();
	    
	    StringBuilder sb = new StringBuilder(len);
	    for (int i = 0; i < len; i++) {
	        char c = param.charAt(i);
	        if (c == '_') {
	            if (++i < len) {
	                sb.append(Character.toUpperCase(param.charAt(i)));
	            }
	        } else {
	            sb.append(c);
	        }
	    }
	    return sb.toString();
	}
	
	public static String convert(String param, String fromNaming, String toNaming) {
		if (fromNaming.equals(Naming.LOWER_HYPHEN) && toNaming.equals(Naming.LOWER_UNDERSCORE)) {
			return CrudapiUtils.lowerHyphenToLowerUnderscore(param);
		} else if (fromNaming.equals(Naming.LOWER_UNDERSCORE) && toNaming.equals(Naming.LOWER_CAMEL)) {
			return CrudapiUtils.lowerUnderscoreToLowerCamel(param);
		} else {
			throw new BusinessException(ApiErrorCode.DEFAULT_ERROR, fromNaming + " to " + toNaming + "is not define!");
		}
	}
	
	public static List<Map<String, Object>> convert(List<Map<String, Object>> mapList, String fromNaming, String toNaming) {
		List<Map<String, Object>> newMapList = new ArrayList<>();
		for (Map<String, Object> t : mapList) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			for(Map.Entry<String, Object> entry : t.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (key.toLowerCase().startsWith("is_")) {
					key = key.substring(3);
					
					if (value != null ) {
						String valueStr = value.toString();
						if (valueStr.equals("1") || valueStr.toLowerCase().equals("true")) {
							value = true;
		    			} else {
		    				value = false;
		    			}
					}
				}
				
				String newKey = CrudapiUtils.convert(key, fromNaming, toNaming);
				
				map.put(newKey, value);
			}
			newMapList.add(map);
		}
		
		return newMapList;
	}
}
