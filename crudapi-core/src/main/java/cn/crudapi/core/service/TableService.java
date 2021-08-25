package cn.crudapi.core.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import cn.crudapi.core.query.Condition;
import com.fasterxml.jackson.databind.JsonNode;

public interface TableService {
    Map<String, Object> get(String name, String id, String select, String expand);

    List<Map<String, Object>> list(String name, String select, String expand, String filter, String search, Condition condition, Integer offset, Integer limit, String orderby);

    Long count(String name, String filter, String search, Condition condition);

    void delete(String name, String id);
    
    void delete(String name, List<String> idList);
    
	void deleteAll(List<String> nameList);

	void importData(String name, List<Map<String, Object>> mapList);
	
	void importData(String name, File file);
	
	void importData(String name, String fileName);

	void update(String name, String id, Map<String, Object> newMap);

	String create(String name, Map<String, Object> map);

	Condition convertConditon(String name, String filter, String search, Condition condition);

	String getImportTemplate(String name, String type);
	
	String exportData(String name, String type);

	List<Map<String, Object>> listAllByIds(String name, List<String> idList, String select, String expand);

}
