package cn.crudapi.weixin.service.impl;


import java.util.HashMap;
import java.util.Map;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cn.crudapi.core.constant.ApiErrorCode;
import cn.crudapi.core.exception.BusinessException;
import cn.crudapi.core.service.TableService;
import cn.crudapi.weixin.service.WeixinService;
import cn.crudapi.weixin.util.WeixinUtil;
import cn.crudapi.core.util.DateTimeUtils;
import cn.crudapi.core.util.JsonUtils;

@Service
public class WeixinServiceImpl implements WeixinService {
	private static final Logger log = LoggerFactory.getLogger(WeixinServiceImpl.class);
	
	private static final String TABLE_NAME = "weixinConfig";
	
	private static final String API_BASE_URL = "https://api.weixin.qq.com";
	
	@Autowired
	private TableService tableService;

	@Autowired
	private RestTemplate restClient;

	@Override
	public Map<String, String> weixinSign(String url) { 
		log.info("weixin sign = " + url);
		Map<String, Object> weixinConfig = tableService.get(TABLE_NAME, "1", "appId,jsapiTicket", null);
		String jsapiTicket = weixinConfig.get("jsapiTicket").toString();
		
		Map<String, String> signMap = WeixinUtil.sign(jsapiTicket, url);
		signMap.put("appId",  weixinConfig.get("appId").toString());
		
		return signMap;
	}
	
	@Override
	public String convertLongUrlToShortUrl(String longUrl) { 
		log.info("longUrl = " + longUrl);
		Map<String, Object> weixinConfig = tableService.get(TABLE_NAME, "1", null, null);
		String accessToken = weixinConfig.get("accessToken").toString();
		String shortUrl = getShortUrl(accessToken, longUrl);
		
		return shortUrl;
	}
	
	@Override
	@Cacheable(value = "weixinAccessToken", key="#id")
	public Map<String, Object> getAccessToken(String id) { 
		log.info("Cacheable weixinAccessToken = " + id);
		Map<String, Object> weixinConfig = tableService.get(TABLE_NAME, id, null, null);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("accessToken", weixinConfig.get("accessToken"));
		data.put("expiresIn", weixinConfig.get("expiresIn"));
		
		return data;
	}
	
	@Override
	@CacheEvict(value = "weixinAccessToken", allEntries= true)
	public synchronized Map<String, Object> getWeixinAccessToken() {
		Map<String, Object> weixinConfig = tableService.get(TABLE_NAME, "1", null, null);
		String appId = weixinConfig.get("appId").toString();
		String appSecret = weixinConfig.get("appSecret").toString();
		
		String url = API_BASE_URL + "/cgi-bin/token?grant_type={grantType}&appid={appId}&secret={appSecret}";
			
		Map<String, String> params = new HashMap<String, String>();
		params.put("grantType", "client_credential");
		params.put("appId", appId);
		params.put("appSecret", appSecret);
		
		log.info(url);
		@SuppressWarnings("unchecked")
		Map<String, Object> data = restClient.getForObject(url, Map.class, params);
		log.info(data.toString());
		
		if (data.get("errcode") != null) {
			if (data.get("errmsg").toString().contains("whitelist")) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("enable", false);
				map.put("log", data.toString());
				
				tableService.update(TABLE_NAME, "1", map);
			}
			throw new BusinessException(ApiErrorCode.REST_ERROR, data.toString());
		}
		
		int expiresInSec = Integer.parseInt(data.get("expires_in").toString());
		DateTime expiresIn = DateTime.now().plusSeconds(expiresInSec);
		String accessToken = data.get("access_token").toString();
		String jsapiTicket = getWeixinJsapiTicket(accessToken);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accessToken", accessToken);
		map.put("expiresIn", DateTimeUtils.sqlTimestamp(expiresIn));
		map.put("jsapiTicket", jsapiTicket);
		map.put("log", "success");
		
		tableService.update(TABLE_NAME, "1", map);
		
		return data;
	}
	
	@Override
	public  Boolean isExpires() {
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");  
        
		DateTime now = DateTime.now();
		log.info("now = " + now.toString(format));
		
		long nowTime = now.getMillis();
		long expireTime = 0L;
		
		Map<String, Object> weixinConfig = tableService.get(TABLE_NAME, "1", null, null);
		Object expiresInObj = weixinConfig.get("expiresIn");
		Object accessTokenObj = weixinConfig.get("accessToken");
		
		int enableInt = Integer.parseInt(weixinConfig.get("enable").toString());
		
		Boolean enable = enableInt == 1 ? true : false;
		if (!enable) {
			log.info("weixinAccessToken enable = " + enable);
			return false;
		}
		
		if (accessTokenObj == null || expiresInObj == null) {
			return true;
		} else {

			log.info("expiresInObj = " + expiresInObj.toString());
			
			expireTime = DateTime.parse(expiresInObj.toString(), format).getMillis();
			
			//提前300S
			if ((nowTime + 300000) >= expireTime ) {
				log.info(now.toString(format) + " + 300000 >= " + expiresInObj.toString() + "过期了，重新获取...");
				return true;
			}
		}
		
		return false;
	}

	@Override
	public String getOpenId(String code) {
		// https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
		String url = API_BASE_URL + "/sns/oauth2/access_token?appid={appId}&secret={appSecret}&code={code}&grant_type={grantType}";
		
		Map<String, Object> weixinConfig = tableService.get(TABLE_NAME, "1", null, null);
		String appId = weixinConfig.get("appId").toString();
		String appSecret = weixinConfig.get("appSecret").toString();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("appId", appId);
		params.put("appSecret", appSecret);
		params.put("code", code);
		params.put("grantType", "authorization_code");
		
		log.info(url);
		log.info(params.toString());
		
		//{"access_token":"35_lvGXRXwDX0F_2eg8yP8InfrgKHiq26CIgqznIjitRLnOTIWbGNVXmgvQETZAoS7Bd-qQt4IgHwn1pPxf1RW3VfmvgTs09RpQLqerdg_5fSU","expires_in":7200,"refresh_token":"35_L-3cNHwQa0WJH2YN16o1TWc0ygg1Ma_opMdpUEAv1vL_253HNCAc8Qc-8isiqf2JizkstjQ-LNZyB0kwPnw5lOXOMvXNKczYvnhKkX5d8tw","openid":"orPWvs010GpNn76XfdxW99B3UdkY","scope":"snsapi_base"}
		String jsonStr = restClient.getForObject(url, String.class, params);
		log.info(jsonStr);
		
		@SuppressWarnings("unchecked")
		Map<String, Object> obj = JsonUtils.toObject(jsonStr, Map.class);
		if (obj.get("errcode") != null) {
			throw new BusinessException(ApiErrorCode.REST_ERROR, obj.toString());
		}
		
		return  obj.get("openid").toString();
	}
	
	@Override
	public Boolean checkSubscribe(String openId) {
		//https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
		String url = API_BASE_URL + "/cgi-bin/user/info?access_token={accessToken}&openid={openId}&lang={lang}";
		
		Map<String, Object> weixinConfig = tableService.get(TABLE_NAME, "1", null, null);
		String accessToken = weixinConfig.get("accessToken").toString();
		//String accessToken = "39_hqQfGzAxtFYW2L0f1402pgTBoBPqOc5ApvU3it4fiuQ9m9WAqrCuKfeIkq-8oXTMHcyfylbWpM2lc31cXOzPrOUnJr2KORBErPXQYmx08qLZr6uojje0s1N9hEuZ_haIfaO2ppih5M7M-BSSIDTaABAXSI";
		Map<String, String> params = new HashMap<String, String>();
		params.put("accessToken", accessToken);
		params.put("openId", openId);
		params.put("lang", "zh_CN");
		
		log.info(url);
		log.info(params.toString());
		
//		{
//		    "subscribe": 1, 
//		    "openid": "o6_bmjrPTlm6_2sgVt7hMZOPfL2M", 
//		    "nickname": "Band", 
//		    "sex": 1, 
//		    "language": "zh_CN", 
//		    "city": "广州", 
//		    "province": "广东", 
//		    "country": "中国", 
//		    "headimgurl":"http://thirdwx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
//		    "subscribe_time": 1382694957,
//		    "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
//		    "remark": "",
//		    "groupid": 0,
//		    "tagid_list":[128,2],
//		    "subscribe_scene": "ADD_SCENE_QR_CODE",
//		    "qr_scene": 98765,
//		    "qr_scene_str": ""
//		}
		String jsonStr = restClient.getForObject(url, String.class, params);
		log.info(jsonStr);
		
		@SuppressWarnings("unchecked")
		Map<String, Object> obj = JsonUtils.toObject(jsonStr, Map.class);
		if (obj.get("errcode") != null) {
			throw new BusinessException(ApiErrorCode.REST_ERROR, obj.toString());
		}
		
		return  obj.get("subscribe").toString().equals("1") ? true : false;
	}
	
	private String getWeixinJsapiTicket(String accessToken) {
		// api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi
		String url = API_BASE_URL + "/cgi-bin/ticket/getticket?access_token={accessToken}&type={type}";
		Map<String, String> params = new HashMap<String, String>();
		params.put("accessToken", accessToken);
		params.put("type", "jsapi");
		
		log.info(url);
		log.info(params.toString());
		
//		{
//			  "errcode":0,
//			  "errmsg":"ok",
//			  "ticket":"bxLdikRXVbTPdHSM05e5u5sUoXNKd8-41ZO3MhKoyN5OfkWITDGgnr2fwJ0m9E8NYzWKVZvdVtaUgWvsdshFKA",
//			  "expires_in":7200
//			}
		@SuppressWarnings("unchecked")
		Map<String, Object> data = restClient.getForObject(url, Map.class, params);
		log.info(data.toString());
		
		if (data.get("errcode") != null && !data.get("errcode").toString().equals("0")) {
			throw new BusinessException(ApiErrorCode.REST_ERROR, data.toString());
		}
		
		return  data.get("ticket").toString();
	}
	
	private String getShortUrl(String accessToken, String longUrl) {
		String url = API_BASE_URL + "/cgi-bin/shorturl?access_token=" + accessToken;
		log.info(url);
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
		
	    Map<String, Object> mapDTO = new HashMap<String, Object>();
	    mapDTO.put("action", "long2short");
	    mapDTO.put("long_url", longUrl);
	    
	    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(mapDTO, headers);

//		{"errcode":0,"errmsg":"ok","short_url":"http:\/\/w.url.cn\/s\/AvCo6Ih"}
		@SuppressWarnings("unchecked")
		Map<String, Object> data = restClient.exchange(url,  
				HttpMethod.POST, entity, Map.class).getBody();
		log.info(data.toString());
		
		if (data.get("errcode") != null && !data.get("errcode").toString().equals("0")) {
			throw new BusinessException(ApiErrorCode.REST_ERROR, data.toString());
		}
		
		return  data.get("short_url").toString();
	}

	@Override
	@Cacheable(value = "weixinAppId", key="#id")
	public String getAppId(String id) {
		log.info("Cacheable getAppId = " + id);
		Map<String, Object> weixinConfig = tableService.get(TABLE_NAME, id, null, null);
		
		return weixinConfig.get("appId").toString();
	}
}