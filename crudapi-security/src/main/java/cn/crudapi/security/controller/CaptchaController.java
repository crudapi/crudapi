package cn.crudapi.security.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.code.kaptcha.Producer;

import cn.crudapi.security.service.CaptchaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags ="验证码管理")
@Controller
@RequestMapping("/api/captcha")
public class CaptchaController {
	private static final Logger log = LoggerFactory.getLogger(CaptchaController.class);
	
	@Autowired
	private Producer captchaProducer;
	
	@Autowired
	private CaptchaService captchaService;
	
	@ApiOperation(value="获取图片验证码")
    @GetMapping(value = "/img")
    public void captcha(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	resp.setContentType("image/jpeg");
    	
    	String capText = captchaProducer.createText();
    	log.info(capText);
    	
    	req.getSession().setAttribute("captcha", capText);
    	
    	BufferedImage bi = captchaProducer.createImage(capText);
    	
    	ServletOutputStream out = resp.getOutputStream();
    	
    	ImageIO.write(bi, "jpg", out);
    	
    	try {
    		out.flush();
    	} finally {
    		out.close();
    	}
    }
	
	@ApiOperation(value="获取短信验证码")
    @GetMapping(value = "/sms")
	public ResponseEntity<Void> sms(@RequestParam(value = "mobile", required = true) String mobile, @ApiIgnore HttpSession session) {
		captchaService.sendSmsCode(mobile, session);
	    
	    return new ResponseEntity<Void>(HttpStatus.OK);
	}
}