package cn.crudapi.api.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.crudapi.core.annotation.CurrentUser;
import cn.crudapi.core.dto.UserDTO;
import cn.crudapi.core.service.TableService;
import cn.crudapi.core.util.RequestUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags ="数据库SQL接口管理")
@RestController
@RequestMapping("/api/sqlapi")
public class SqlApiController {
    @Autowired
    private TableService tableService;

    @ApiOperation(value="查询数据")
	@GetMapping(value = "/{group}/{name}")
	public ResponseEntity<List<Map<String, Object>>> list(
			@PathVariable("group") String group,
			@PathVariable("name") String name,
			@RequestParam(value = "offset", required = false) Integer offset,
			@RequestParam(value = "limit", required = false) Integer limit,
			@CurrentUser UserDTO userDTO,
			HttpServletRequest request) {
    	Map<String, Object> paramMap = RequestUtils.getParams(request, null);
    	paramMap.put("offset", offset);
    	paramMap.put("limit", limit);
    	
		List<Map<String, Object>> mapList = tableService.list(group, name, paramMap, userDTO);

		return new ResponseEntity<List<Map<String, Object>>>(mapList, HttpStatus.OK);
	}
    
    @ApiOperation(value="查询个数")
	@GetMapping(value = "/{group}/{name}/count")
	public ResponseEntity<Long> count(
			@PathVariable("group") String group,
			@PathVariable("name") String name,
			@CurrentUser UserDTO userDTO,
			HttpServletRequest request) {
    	Map<String, Object> paramMap = RequestUtils.getParams(request, null);
    	
    	Long count  = tableService.count(group, name, paramMap, userDTO);

		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}
}
