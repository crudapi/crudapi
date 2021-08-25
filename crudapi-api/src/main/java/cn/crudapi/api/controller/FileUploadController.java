package cn.crudapi.api.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import cn.crudapi.core.service.FileService;

@Api(tags ="文件管理")
@RestController
@RequestMapping("/api/file")
public class FileUploadController {
	@Autowired
	private FileService fileService;
	
	@ApiOperation(value="上传")
	@PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Map<String, Object>> upload(@RequestPart MultipartFile file) {
        return new ResponseEntity<Map<String, Object>>(fileService.upload(file), HttpStatus.CREATED);
    }
	
	@ApiOperation(value="大文件上传")
	@PostMapping(value="/big", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<Map<String, Object>> upload(String name,
            String md5,
            Long size,
            Integer chunks,
            Integer chunk,
            MultipartFile file) {
		return new ResponseEntity<Map<String, Object>>(fileService.uploadWithBlock(name, md5,size,chunks,chunk,file), HttpStatus.CREATED);
	}
	
	@ApiOperation(value="删除")
	@DeleteMapping(value = "/{fileName}")
	public ResponseEntity<Void> delete(@PathVariable("fileName") String fileName) {
		fileService.delete(fileName);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
