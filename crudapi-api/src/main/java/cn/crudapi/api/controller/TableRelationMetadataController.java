package cn.crudapi.api.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.crudapi.core.constant.ApiErrorCode;
import cn.crudapi.core.datasource.config.DataSourceContextHolder;
import cn.crudapi.core.dto.TableRelationDTO;
import cn.crudapi.core.exception.BusinessException;
import cn.crudapi.core.query.Condition;
import cn.crudapi.core.service.TableRelationMetadataService;
import cn.crudapi.core.util.ConditionUtils;
import cn.crudapi.core.util.RequestUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags ="表关系管理")
@RestController
@RequestMapping("/api/metadata/tablerelations")
public class TableRelationMetadataController {
	@Autowired
	private TableRelationMetadataService tableRelationMetadataService;

	@ApiOperation(value="设置表关系")
	@PutMapping()
	public ResponseEntity<Void> put(@RequestBody List<TableRelationDTO> tableRelationDTOList) {
		tableRelationMetadataService.set(tableRelationDTOList);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value="创建表关系")
	@PostMapping()
	public ResponseEntity<Void> create(@RequestBody TableRelationDTO tableRelationDTO) {
		tableRelationMetadataService.create(tableRelationDTO);

		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@ApiOperation(value="修改表关系")
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody TableRelationDTO tableRelationDTO) {
		tableRelationMetadataService.update(id, tableRelationDTO);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }
	
	@ApiOperation(value="获取表关系")
	@GetMapping(value = "/{id}")
	public ResponseEntity<TableRelationDTO> get(@PathVariable("id") Long id) {
		TableRelationDTO tableRelationDTO = tableRelationMetadataService.get(id);

		return new ResponseEntity<TableRelationDTO>(tableRelationDTO, HttpStatus.OK);
	}

	@ApiOperation(value="获取表关系")
	@GetMapping(value = "/fromTable/{tableId}")
	public ResponseEntity<List<TableRelationDTO>> getFromTable(@PathVariable("tableId") Long tableId) {
		List<TableRelationDTO> tableRelationDTOList = tableRelationMetadataService.getFromTable(tableId);

		return new ResponseEntity<List<TableRelationDTO>>(tableRelationDTOList, HttpStatus.OK);
	}
	
	@ApiOperation(value="获取表关系")
	@GetMapping(value = "/fromTable/name/{tableName}")
	public ResponseEntity<List<TableRelationDTO>> getFromTable(@PathVariable("tableName") String tableName) {
		List<TableRelationDTO> tableRelationDTOList = tableRelationMetadataService.getFromTable(tableName);

		return new ResponseEntity<List<TableRelationDTO>>(tableRelationDTOList, HttpStatus.OK);
	}

	@ApiOperation(value="查询个数")
	@GetMapping(value = "/count")
	public ResponseEntity<Long> count(@RequestParam(value = "filter", required = false) String filter,
			@RequestParam(value = "search", required = false) String search,
			HttpServletRequest request) {
		Condition condition = ConditionUtils.toCondition(RequestUtils.getParams(request));
    	
    	Long count  = tableRelationMetadataService.count(filter, search, condition);

		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}
    
	@ApiOperation(value="查询表关系")
    @GetMapping()
    public ResponseEntity<List<TableRelationDTO>> list(@RequestParam(value = "filter", required = false) String filter,
			@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "offset", required = false) Integer offset,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "orderby", required = false) String orderby,
			HttpServletRequest request) {
		Condition condition = ConditionUtils.toCondition(RequestUtils.getParams(request));
		
        List<TableRelationDTO> tableRelationDTOList = tableRelationMetadataService.list(filter, search, condition, offset, limit, orderby);

        return new ResponseEntity<List<TableRelationDTO>>(tableRelationDTOList, HttpStatus.OK);
    }
	
	@ApiOperation(value="导出表关系")
	@GetMapping("/export")
	public ResponseEntity<List<TableRelationDTO>> export() {
		    HttpHeaders headers = new HttpHeaders();
		    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		    headers.add("Content-Disposition", "attachment; filename=metadataRelation.json");
		    headers.add("Pragma", "no-cache");
		    headers.add("Expires", "0");
		    headers.add("Last-Modified", new Date().toString());
		    headers.add("ETag", String.valueOf(System.currentTimeMillis()));
		    List<TableRelationDTO> tableRelationDTOList = tableRelationMetadataService.listAll();

		    return ResponseEntity
		            .ok()
		            .headers(headers)
		            .contentType(MediaType.APPLICATION_JSON)
		            .body(tableRelationDTOList);
	}
	
	@ApiOperation(value="删除表关系")
	@DeleteMapping(value = "/fromTable/{tableId}")
	public ResponseEntity<Void> deleteByFromTableId(@PathVariable("tableId") Long tableId) {
		tableRelationMetadataService.deleteByFromTable(tableId);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value="删除表关系")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		tableRelationMetadataService.delete(id);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value="批量删除表关系")
	@DeleteMapping()
	public ResponseEntity<Void> batchDelete(@RequestBody(required = false) List<Long> idList) {
		if (idList == null) {
			tableRelationMetadataService.deleteAll();
		} else if (idList.size() == 0) {
			throw new BusinessException(ApiErrorCode.API_RESOURCE_NOT_FOUND, "至少选择一个");
		} else {
			tableRelationMetadataService.delete(idList);
		}
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
