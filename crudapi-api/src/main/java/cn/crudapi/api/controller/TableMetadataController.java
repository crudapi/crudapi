package cn.crudapi.api.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

import cn.crudapi.core.constant.ApiErrorCode;
import cn.crudapi.core.dto.TableDTO;
import cn.crudapi.core.exception.BusinessException;
import cn.crudapi.core.query.Condition;
import cn.crudapi.core.service.FileService;
import cn.crudapi.core.service.MetadataService;
import cn.crudapi.core.service.TableMetadataService;
import cn.crudapi.core.util.ConditionUtils;
import cn.crudapi.core.util.RequestUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags ="表管理")
@RestController
@RequestMapping("/api/metadata/tables")
public class TableMetadataController {
	@Autowired
	private TableMetadataService tableMetadataService;

	@Autowired
	private FileService fileService;

	@Autowired
	private MetadataService metadataService;

	@ApiOperation(value="创建表")
	@PostMapping()
	public ResponseEntity<Long> create(@RequestBody @Validated TableDTO tableDTO) {
		Long tableId = tableMetadataService.create(tableDTO);

		return new ResponseEntity<Long>(tableId, HttpStatus.CREATED);
	}
	
	@ApiOperation(value="批量创建表")
	@PostMapping("/batch")
	public ResponseEntity<List<Long>> batchCreate(@RequestBody @Validated List<TableDTO> tableDTOs) {
		List<Long> tableIds = new ArrayList<Long>();
		for (TableDTO tableDTO : tableDTOs) { 
			Long tableId = tableMetadataService.create(tableDTO);
			tableIds.add(tableId);
		}

		return new ResponseEntity<List<Long>>(tableIds, HttpStatus.CREATED);
	}
	
	@ApiOperation(value="修改表")
	@PatchMapping(value = "/{tableId}")
	public ResponseEntity<Void> update(@PathVariable("tableId") Long tableId, @RequestBody TableDTO tableDTO) {
		tableMetadataService.update(tableId, tableDTO);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@ApiOperation(value="获取表")
	@GetMapping(value = "/{tableId}")
	public ResponseEntity<TableDTO> get(@PathVariable("tableId") Long tableId) {
		TableDTO tableDTO = tableMetadataService.get(tableId);
		if (tableDTO == null) {
			throw new BusinessException(ApiErrorCode.API_RESOURCE_NOT_FOUND, tableId);
		}

		return new ResponseEntity<TableDTO>(tableDTO, HttpStatus.OK);
	}

	@ApiOperation(value="获取表")
	@GetMapping(value = "/name/{tableName}")
	public ResponseEntity<TableDTO> get(@PathVariable("tableName") String tableName) {
		TableDTO tableDTO = tableMetadataService.get(tableName);
		if (tableDTO == null) {
			throw new BusinessException(ApiErrorCode.API_RESOURCE_NOT_FOUND, tableName);
		}

		return new ResponseEntity<TableDTO>(tableDTO, HttpStatus.OK);
	}
	
	@ApiOperation(value="获取表元数据")
	@GetMapping(value = "/metadata/{tableName}")
	public ResponseEntity<Map<String, Object>> getMetaData(@PathVariable("tableName") String tableName) {
		Map<String, Object> map = tableMetadataService.getMetaData(tableName);
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	@ApiOperation(value="获取表元数据列表")
	@GetMapping(value = "/metadatas")
	public ResponseEntity<List<Map<String, Object>>> getMetaDatas() {
		List<Map<String, Object>> mapList = tableMetadataService.getMetaDatas();
		
		return new ResponseEntity<List<Map<String, Object>>>(mapList, HttpStatus.OK);
	}
	
    @ApiOperation(value="查询个数")
	@GetMapping(value = "/count")
	public ResponseEntity<Long> count(@RequestParam(value = "filter", required = false) String filter,
			@RequestParam(value = "search", required = false) String search,
			HttpServletRequest request) {
    	Condition condition = ConditionUtils.toCondition(RequestUtils.getParams(request));
    	
    	Long count  = tableMetadataService.count(filter, search, condition);

		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}
    
	@ApiOperation(value="查询表")
	@GetMapping()
	public ResponseEntity<List<TableDTO>> list(@RequestParam(value = "filter", required = false) String filter,
			@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "offset", required = false) Integer offset,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "orderby", required = false) String orderby,
			HttpServletRequest request) {
		Condition condition = ConditionUtils.toCondition(RequestUtils.getParams(request));
		
		List<TableDTO> tableDTOList = tableMetadataService.list(filter, search, condition, offset, limit, orderby);

		return new ResponseEntity<List<TableDTO>>(tableDTOList, HttpStatus.OK);
	}
	
    @ApiOperation(value="导入表")
	@PostMapping(value = "/import", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Void> importData(@RequestPart MultipartFile file) {
		Map<String, Object> map = fileService.upload(file);
		String fileName = map.get("name").toString();
		File tempFile = fileService.getFile(fileName);
		metadataService.importData(tempFile);
		fileService.delete(fileName);
		
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
    
	@ApiOperation(value="导出表")
	@PostMapping("/export")
    public ResponseEntity<String> getExportFile(@RequestBody(required = false) List<Long> idList) {
		String fileName = metadataService.getExportFile("crudapi", idList);
        return new ResponseEntity<String>(fileName, HttpStatus.CREATED);
    }
	
	@ApiOperation(value="删除表")
	@DeleteMapping(value = "/{tableId}")
	public ResponseEntity<Void> delete(@PathVariable("tableId") Long tableId,
			@RequestParam(value = "isDropPhysicalTable", required = false) Boolean isDropPhysicalTable) {
		tableMetadataService.delete(tableId, isDropPhysicalTable);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value="批量删除表")
	@DeleteMapping()
	public ResponseEntity<Void> batchDelete(@RequestBody(required = false) List<Long> idList,
			@RequestParam(value = "isDropPhysicalTable", required = false) Boolean isDropPhysicalTable) {
		if (idList == null) {
			tableMetadataService.deleteAll(isDropPhysicalTable);
		} else if (idList.size() == 0) {
			throw new BusinessException(ApiErrorCode.API_RESOURCE_NOT_FOUND, "至少选择一个");
		} else {
			tableMetadataService.delete(idList, isDropPhysicalTable);
		}
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
