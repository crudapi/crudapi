package cn.crudapi.crudapi.util;

public class CrudapiUtils {
	public static String camelToUnderline(String param) {
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

	public static String underlineToCamel(String param) {
	    if (param == null || "".equals(param.trim())) {
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
}
