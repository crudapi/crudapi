package cn.crudapi.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public final class RequestUtils {
	public static Map<String, Object> getParams(HttpServletRequest request, List<String> blackList) {
	   Enumeration<String> parameterNameEnumeration = request.getParameterNames();  
       Map<String, Object> parameterMap = new HashMap<String,Object>();
       while (parameterNameEnumeration.hasMoreElements()){
           String parameterName = parameterNameEnumeration.nextElement();  
           if (!blackList.contains(parameterName)) {
               parameterMap.put(parameterName, request.getParameter(parameterName));  
           }
       }
       return parameterMap;  
	}
	

	public static Map<String, Object> getParams(HttpServletRequest request) {
	   List<String> blackList = new ArrayList<>(Arrays.asList("select", "expand", "filter", "search", "offset", "limit", "orderby"));
	      
       return getParams(request, blackList);  
	}
}

