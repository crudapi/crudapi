package cn.crudapi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.crudapi.core.datasource.config.DataSourceContextHolder;
import cn.crudapi.core.service.SequenceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags ="序列号数据管理")
@RestController
@RequestMapping("/api/business/sequence")
public class SequenceController {
    @Autowired
    private SequenceService sequenceService;

	@ApiOperation(value="获取序列号的值")
    @GetMapping(value = "/{sequenceName}/nextval")
    public ResponseEntity<Object> getNextValue(@PathVariable("sequenceName") String sequenceName) {
        Object value = sequenceService.getNextValue(sequenceName);

        return new ResponseEntity<Object>(value, HttpStatus.OK);
    }
}
