package cn.crudapi.core.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;

import cn.crudapi.core.dto.UserDTO;
import cn.crudapi.core.model.QueryModel;
import cn.crudapi.core.query.Condition;

public interface TableService {
    Map<String, Object> get(String name, String id, String select, String expand);

    List<Map<String, Object>> list(String name, String select, String expand, String filter, String search, Condition condition, Integer offset, Integer limit, String orderby);

    Long count(String name, String filter, String search, Condition condition);
    
    void delete(String name, String id);
    
    void delete(String name, List<String> idList);

    void delete(String name, String id, Boolean isSoftDelete, UserDTO userDTO);
    
    void delete(String name, List<String> idList, Boolean isSoftDelete,  UserDTO userDTO);
    
	void deleteAll(List<String> nameList);

	void importData(String name, String fileName);
	
	void importData(String name, File file);
	
	void importData(String name, List<Map<String, Object>> mapList);
	
	void importData(String name, String fileName, UserDTO userDTO);
	
	void importData(String name, File file, UserDTO userDTO);
	
	void importData(String name, List<Map<String, Object>> mapList, UserDTO userDTO);
	
	List<Map<String, Object>> convertExecelToData(String name, File file);

	List<Map<String, Object>> convertExecelSheetToData(String name, Sheet sheet);
	
	void importData(File jsonFile, UserDTO userDTO);

	Map<String, Object> convertJsonToData(File jsonFile);
	
	void update(String name, String id, Map<String, Object> newMap, UserDTO userDTO);
	
	void update(String name, String id, Map<String, Object> newMap);

	String create(String name, Map<String, Object> map, UserDTO userDTO);
	
	String create(String name, Map<String, Object> map);

	Condition convertConditon(String name, String filter, String search, Condition condition);

	String getImportTemplate(String name, String type);
	
	String exportData(String name, String type, String filter, String search, Condition condition);

	String exportData(String name, String type, String select, String filter, String search, Condition condition);
	
	List<Map<String, Object>> listAllByIds(String name, List<String> idList, String select, String expand);

	List<Map<String, Object>> listMain(String name, String select, String expand, String filter, String search,
			Condition condition, Integer offset, Integer limit, String orderby);

	List<Map<String, Object>> list(String group, String name, Map<String, Object> paramMap, UserDTO userDTO);

	Long count(String group, String name, Map<String, Object> paramMap, UserDTO userDTO);
	
	String exportJsonData(String name, List<Long> ids, UserDTO userDTO);

	void batchImportData(String name, List<Map<String, Object>> mapList, UserDTO userDTO);

	List<Map<String, Object>> convertExecelSheetToRawData(Sheet sheet);

	String exportToXmlData(String name, String select, String filter, String search, Condition condition, Boolean isDisplayCaption, UserDTO userDTO);

	List<Map<String, Object>> list(String name, String select, String expand, String filter, String search,
			Condition condition, Integer offset, Integer limit, String orderby, UserDTO userDTO);

	Map<String, Object> get(String name, String id, String select, String expand, UserDTO userDTO);

	List<Map<String, Object>> listAllByIds(String name, List<String> idList, String select, String expand,
			UserDTO userDTO);

	String exportData(String name, String type, String select, String filter, String search, Condition condition,
			UserDTO userDTO);

	Map<String, Map<String, Object>> getFullDicFromCache(String tableName, List<String> selectList, String pkName);

	void cleanFullDicFromCache();


}
