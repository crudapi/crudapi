package cn.crudapi.weixin.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.crudapi.weixin.service.WeixinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags ="微信")
@RestController
@RequestMapping("/api/weixin")
public class WeixinController {
	@Autowired
	private WeixinService weixinService;
	
	@ApiOperation(value="获取access-token")
    @GetMapping(value = "/accessToken")
    public ResponseEntity<Map<String, Object>> getAccessToken() {
    	Map<String, Object> data = weixinService.getAccessToken("1");
    	
        return new ResponseEntity<Map<String, Object>>(data, HttpStatus.OK);
    }
	
	@ApiOperation(value="刷新access-token")
    @PostMapping(value = "/accessToken")
    public ResponseEntity<Map<String, Object>> refreshAccessToken() {
    	Map<String, Object> data = weixinService.getWeixinAccessToken();
    	
        return new ResponseEntity<Map<String, Object>>(data, HttpStatus.OK);
    }
	
	@ApiOperation(value="获取appId")
    @GetMapping(value = "/appId")
    public ResponseEntity<String> getAppId() {
		String data = weixinService.getAppId("1");
    	
        return new ResponseEntity<String>(data, HttpStatus.OK);
    }
	
	@ApiOperation(value="长链接转成短链接")
    @PostMapping(value = "/shortUrl")
    public ResponseEntity<String> convertLongUrlToShortUrl(
    		@ApiParam(name = "url")
    		@RequestBody Map<String, Object> map) {
		String data = weixinService.convertLongUrlToShortUrl(map.get("url").toString());
    	
        return new ResponseEntity<String>(data, HttpStatus.OK);
    }
	
	@ApiOperation(value="获取签名")
    @PostMapping(value = "/sign")
    public ResponseEntity<Map<String, String>> weixinSign(
    		@ApiParam(name = "url")
    		@RequestBody Map<String, Object> map) {
    	Map<String, String> data = weixinService.weixinSign(map.get("url").toString());
    	
        return new ResponseEntity<Map<String, String>>(data, HttpStatus.OK);
    }
	
    
//    @GetMapping(value = "/openId")
//    public ResponseEntity<String> getOpenId(@RequestParam(value = "code") String code) {
//    	String data = weixinService.getOpenId(code);
//    	
//        return new ResponseEntity<String>(data, HttpStatus.OK);
//    }
}