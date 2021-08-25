package cn.crudapi.weixin.service;

import java.util.Map;

public interface WeixinService {
	Map<String, Object> getWeixinAccessToken();

	Map<String, Object> getAccessToken(String id);
	
	String getAppId(String id);
	
	String getOpenId(String code);

	Boolean isExpires();

	Map<String, String> weixinSign(String url);

	String convertLongUrlToShortUrl(String longUrl);

	Boolean checkSubscribe(String openId);
}
