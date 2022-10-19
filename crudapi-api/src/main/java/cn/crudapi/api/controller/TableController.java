package cn.crudapi.api.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.crudapi.core.annotation.CurrentUser;
import cn.crudapi.core.constant.ApiErrorCode;
import cn.crudapi.core.datasource.config.DataSourceContextHolder;
import cn.crudapi.core.dto.ColumnDTO;
import cn.crudapi.core.dto.TableDTO;
import cn.crudapi.core.dto.UserDTO;
import cn.crudapi.core.enumeration.OperatorTypeEnum;
import cn.crudapi.core.exception.BusinessException;
import cn.crudapi.core.query.Condition;
import cn.crudapi.core.service.FileService;
import cn.crudapi.core.service.TableMetadataService;
import cn.crudapi.core.service.TableService;
import cn.crudapi.core.util.ConditionUtils;
import cn.crudapi.core.util.RequestUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags ="表数据管理")
@RestController
@RequestMapping("/api/business")
public class TableController {
    @Autowired
    private TableMetadataService tableMetadataService;

    @Autowired
    private TableService tableService;

    @Autowired
    private FileService fileService;

    @ApiOperation(value="添加数据")
    @PostMapping(value = "/{name}")
    public ResponseEntity<String> create(@PathVariable("name") String name, 
    		@RequestBody Map<String, Object> map,
    		@CurrentUser UserDTO userDTO) {
    	String id = tableService.create(name, map, userDTO.getId());

        return new ResponseEntity<String>(id, HttpStatus.CREATED);
    }
    
    @ApiOperation(value="批量添加数据")
    @PostMapping(value = "/{name}/batch")
    public ResponseEntity<Void> batchCreate(@PathVariable("name") String name, 
    		@RequestBody List<Map<String, Object>> mapList,
    		@CurrentUser UserDTO userDTO) {
    	tableService.importData(name, mapList, userDTO.getId());

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
    
    @ApiOperation(value="导入EXCEL数据")
	@PostMapping(value = "/{name}/import", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Void> importData(@PathVariable("name") String name, 
    		@RequestPart MultipartFile file,
    		@CurrentUser UserDTO userDTO) {
    	Map<String, Object> map = fileService.upload(file);
		String fileName = map.get("name").toString();
		File tempFile = fileService.getFile(fileName);
		tableService.importData(name, tempFile, userDTO.getId());
		fileService.delete(fileName);
		
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
    

    @ApiOperation(value="导入JSON数据")
	@PostMapping(value = "/import", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Void> importJsonData(
    		@RequestPart MultipartFile file,
    		@CurrentUser UserDTO userDTO) {
    	Map<String, Object> map = fileService.upload(file);
		String fileName = map.get("name").toString();
		File tempFile = fileService.getFile(fileName);
		tableService.importData(tempFile, userDTO.getId());
		fileService.delete(fileName);
		
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
    
	@ApiOperation(value="导出JSON数据")
	@PostMapping("/export")
    public ResponseEntity<String> exportJsonData(@RequestBody(required = false) List<Long> idList) {
		String fileName = tableService.exportJsonData("crudapi", idList);
        return new ResponseEntity<String>(fileName, HttpStatus.CREATED);
    }    
    
    @ApiOperation(value="获取导入数据模板")
	@GetMapping(value = "/{name}/import/template")
    public ResponseEntity<String> getImportTemplate(@PathVariable("name") String name) {
    	String fileName = tableService.getImportTemplate(name, null);
		String url = fileService.getUrl(fileName);
        return new ResponseEntity<String>(url, HttpStatus.CREATED);
    }
    
    
    @ApiOperation(value="导出数据")
   	@PostMapping(value = "/{name}/export")
    public ResponseEntity<String> exportData(@PathVariable("name") String name,
    		@RequestParam(value = "select", required = false) String select,
			@RequestParam(value = "filter", required = false) String filter,
			@RequestParam(value = "search", required = false) String search,
			HttpServletRequest request) {
    	Condition condition = ConditionUtils.toCondition(RequestUtils.getParams(request));
    	
   		String fileName = tableService.exportData(name, null, select, filter, search, condition);
   		String url = fileService.getUrl(fileName);
        return new ResponseEntity<String>(url, HttpStatus.CREATED);
    }
    
    
    @ApiOperation(value="导出XML数据")
   	@PostMapping(value = "/{name}/export/xml")
    public ResponseEntity<String> exportToXmlData(@PathVariable("name") String name,
    		@RequestParam(value = "select", required = false) String select,
			@RequestParam(value = "filter", required = false) String filter,
			@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "isDisplayCaption", required = false) Boolean isDisplayCaption,
			HttpServletRequest request) {
    	List<String> blackList = new ArrayList<>(Arrays.asList("select", "expand", "filter", "search", "offset", "limit", "orderby", "dataSource", "isDisplayCaption"));
 	     
    	Condition condition = ConditionUtils.toCondition(RequestUtils.getParams(request, blackList));
    	
   		String fileName = tableService.exportToXmlData(name, select, filter, search, condition, isDisplayCaption);
   		String url = fileService.getUrl(fileName);
        return new ResponseEntity<String>(url, HttpStatus.CREATED);
    }
       
    @ApiOperation(value="修改数据")
    @PatchMapping(value = "/{name}/{id}")
    public ResponseEntity<Void> update(@PathVariable("name") String name,
    		@PathVariable("id") String id,
    		@RequestBody Map<String, Object> map,
    		@CurrentUser UserDTO userDTO) {
    	tableService.update(name, id, map, userDTO.getId());

    	return new ResponseEntity<Void>(HttpStatus.OK);
    }

    //@PreAuthorize("hasRole(#name+'READ') or hasRole('ROLE_ADMIN')")
    @ApiOperation(value="查询数据")
	@GetMapping(value = "/{name}")
	public ResponseEntity<List<Map<String, Object>>> list(@PathVariable("name") String name,
			@RequestParam(value = "select", required = false) String select,
			@RequestParam(value = "expand", required = false) String expand,
			@RequestParam(value = "filter", required = false) String filter,
			@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "offset", required = false) Integer offset,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "orderby", required = false) String orderby,
			HttpServletRequest request) {
    	Condition condition = convertNewCondition(name, request);
    	
		List<Map<String, Object>> mapList = tableService.list(name, select, expand, filter, search, condition, offset, limit, orderby);

		return new ResponseEntity<List<Map<String, Object>>>(mapList, HttpStatus.OK);
	}
    
    @ApiOperation(value="查询个数")
	@GetMapping(value = "/{name}/count")
	public ResponseEntity<Long> count(@PathVariable("name") String name,
			@RequestParam(value = "filter", required = false) String filter,
			@RequestParam(value = "search", required = false) String search,
			HttpServletRequest request) {
    	Condition condition =convertNewCondition(name, request);
    	
    	Long count  = tableService.count(name, filter, search, condition);

		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}
    

    @ApiOperation(value="获取数据")
	@GetMapping(value = "/{name}/{id}")
	public ResponseEntity<Map<String, Object>> get(@PathVariable("name") String name, @PathVariable("id") String id,
			@RequestParam(value = "select", required = false) String select,
			@RequestParam(value = "expand", required = false) String expand) {
    	Map<String, Object> map = tableService.get(name, id, select, expand);

		if (map == null) {
			throw new BusinessException(ApiErrorCode.API_RESOURCE_NOT_FOUND, id);
		}

		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
    
    @ApiOperation(value="获取指定编号所有数据")
 	@GetMapping(value = "/{name}/all")
 	public ResponseEntity<List<Map<String, Object>>> listAllByIds(@PathVariable("name") String name,
 			@RequestParam(value = "ids", required = true) String ids,
 			@RequestParam(value = "select", required = false) String select,
 			@RequestParam(value = "expand", required = false) String expand) {
    	List<String> idList = new ArrayList<String>();
    	if (!StringUtils.isBlank(ids)) {
    		String[] idArr = ids.split(",");
    		for (String id : idArr) {
    			if (!StringUtils.isBlank(id)) {
    				idList.add(id);
    			}
    		}
    	}
    	
    	List<Map<String, Object>> mapList = tableService.listAllByIds(name, idList, select, expand);

 		return new ResponseEntity<List<Map<String, Object>>>(mapList, HttpStatus.OK);
 	}


    @ApiOperation(value="删除数据")
	@DeleteMapping(value = "/{name}/{id}")
	public ResponseEntity<Void> delete(@PathVariable("name") String name,
			@PathVariable("id") String id,
			@RequestParam(value = "isSoftDelete", required = false) Boolean isSoftDelete,
			@CurrentUser UserDTO userDTO) {
    	tableService.delete(name, id, isSoftDelete, userDTO.getId());

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
    
    @ApiOperation(value="批量删除数据")
	@DeleteMapping(value = "/{name}")
	public ResponseEntity<Void> batchDelete(@PathVariable("name") String name, 
			@RequestBody List<String> idList,
			@RequestParam(value = "isSoftDelete", required = false) Boolean isSoftDelete,
			@CurrentUser UserDTO userDTO) {
    	tableService.delete(name, idList, isSoftDelete, userDTO.getId());

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
    
    private Condition convertNewCondition(String name, HttpServletRequest request) {
    	TableDTO mainTableDTO = tableMetadataService.get(name);
    	Map<String, OperatorTypeEnum> operatorTypeMap = new HashMap<String, OperatorTypeEnum>();
    	for (ColumnDTO columnDTO : mainTableDTO.getColumnDTOList()) {
    		if (Boolean.TRUE.equals(columnDTO.getMultipleValue())) {
    			operatorTypeMap.put(columnDTO.getName(), OperatorTypeEnum.MLIKE);
    		}
    	}
    	
    	Condition condition = ConditionUtils.toCondition(RequestUtils.getParams(request), operatorTypeMap);
    	
    	return condition;
    }

//	@ApiOperation(value="清空表")
//	@DeleteMapping(value = "")
//	public ResponseEntity<Void> deleteAll(@RequestBody List<String> nameList) {
//		tableService.deleteAll(nameList);
//
//		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
//	}
}
