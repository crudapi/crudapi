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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.crudapi.core.constant.ApiErrorCode;
import cn.crudapi.core.dto.SequenceDTO;
import cn.crudapi.core.exception.BusinessException;
import cn.crudapi.core.query.Condition;
import cn.crudapi.core.service.SequenceMetadataService;
import cn.crudapi.core.util.ConditionUtils;
import cn.crudapi.core.util.RequestUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags ="序列号管理")
@RestController
@RequestMapping("/api/metadata/sequences")
public class SequenceMetadataController {
    @Autowired
    private SequenceMetadataService sequenceMetadataService;

	@ApiOperation(value="创建序列号")
    @PostMapping()
    public ResponseEntity<Long> create(@RequestBody SequenceDTO sequenceDTO) {
		Long sequenceId = sequenceMetadataService.create(sequenceDTO);

        return new ResponseEntity<Long>(sequenceId, HttpStatus.CREATED);
    }

	@ApiOperation(value="修改序列号")
    @PatchMapping(value = "/{sequenceId}")
    public ResponseEntity<Void> update(@PathVariable("sequenceId") Long sequenceId, @RequestBody SequenceDTO sequenceDTO) {
        sequenceMetadataService.update(sequenceId, sequenceDTO);

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

	@ApiOperation(value="获取序列号")
    @GetMapping(value = "/{sequenceId}")
    public ResponseEntity<SequenceDTO> get(@PathVariable("sequenceId") Long sequenceId) {
        SequenceDTO sequenceDTO = sequenceMetadataService.get(sequenceId);
        if (sequenceDTO == null) {
            throw new BusinessException(ApiErrorCode.API_RESOURCE_NOT_FOUND, sequenceId);
        }

        return new ResponseEntity<SequenceDTO>(sequenceDTO, HttpStatus.OK);
    }
	
	@ApiOperation(value="获取序列号")
    @GetMapping(value = "/name/{sequenceName}")
    public ResponseEntity<SequenceDTO> getByName(@PathVariable("sequenceName") String sequenceName) {
        SequenceDTO sequenceDTO = sequenceMetadataService.get(sequenceName);
        if (sequenceDTO == null) {
            throw new BusinessException(ApiErrorCode.API_RESOURCE_NOT_FOUND, sequenceName);
        }

        return new ResponseEntity<SequenceDTO>(sequenceDTO, HttpStatus.OK);
    }

    @ApiOperation(value="查询个数")
	@GetMapping(value = "/count")
	public ResponseEntity<Long> count(@RequestParam(value = "filter", required = false) String filter,
			@RequestParam(value = "search", required = false) String search,
			HttpServletRequest request) {
    	Condition condition = ConditionUtils.toCondition(RequestUtils.getParams(request));
    	
    	Long count  = sequenceMetadataService.count(filter, search, condition);

		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}
    
	@ApiOperation(value="查询序列号")
    @GetMapping()
    public ResponseEntity<List<SequenceDTO>> list(@RequestParam(value = "filter", required = false) String filter,
			@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "offset", required = false) Integer offset,
			@RequestParam(value = "limit", required = false) Integer limit,
			@RequestParam(value = "orderby", required = false) String orderby,
			HttpServletRequest request) {
		Condition condition = ConditionUtils.toCondition(RequestUtils.getParams(request));
		
        List<SequenceDTO> sequenceDTOList = sequenceMetadataService.list(filter, search, condition, offset, limit, orderby);

        return new ResponseEntity<List<SequenceDTO>>(sequenceDTOList, HttpStatus.OK);
    }
	
	@ApiOperation(value="导出序列号")
	@GetMapping("/export")
	public ResponseEntity<List<SequenceDTO>> export() {
		    HttpHeaders headers = new HttpHeaders();
		    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		    headers.add("Content-Disposition", "attachment; filename=metadataSequence.json");
		    headers.add("Pragma", "no-cache");
		    headers.add("Expires", "0");
		    headers.add("Last-Modified", new Date().toString());
		    headers.add("ETag", String.valueOf(System.currentTimeMillis()));
		 
			List<SequenceDTO> sequenceDTOList = sequenceMetadataService.listAll();

		    return ResponseEntity
		            .ok()
		            .headers(headers)
		            .contentType(MediaType.APPLICATION_JSON)
		            .body(sequenceDTOList);
	}

	@ApiOperation(value="删除序列号")
	@DeleteMapping(value = "/{sequenceId}")
	public ResponseEntity<Void> delete(@PathVariable("sequenceId") Long sequenceId) {
		sequenceMetadataService.delete(sequenceId);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value="批量删除序列号")
	@DeleteMapping()
	public ResponseEntity<Void> batchDelete(@RequestBody(required = false) List<Long> idList) {
		if (idList == null) {
			sequenceMetadataService.deleteAll();
		} else if (idList.size() == 0) {
			throw new BusinessException(ApiErrorCode.API_RESOURCE_NOT_FOUND, "至少选择一个");
		} else {
			sequenceMetadataService.delete(idList);
		}
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
