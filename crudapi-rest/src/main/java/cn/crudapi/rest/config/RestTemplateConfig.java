package cn.crudapi.rest.config;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import okhttp3.OkHttpClient;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
    	RestTemplate restTemplate =  new RestTemplate(factory);
//    	restTemplate.getMessageConverters();
//
//    	List<HttpMessageConverter<?>> list = restTemplate.getMessageConverters();
//    	for (HttpMessageConverter<?> httpMessageConverter : list) {
//	    	if(httpMessageConverter instanceof FormHttpMessageConverter) {
//	    		((FormHttpMessageConverter) httpMessageConverter).setCharset(Charset.forName(StandardCharsets.UTF_8.toString()));
//	    	}
//    	}
//    	restTemplate.setMessageConverters(list);
//
    	return restTemplate;
    }

//    @Bean
//    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
////        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory() { {
////            setProxy(new java.net.Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 6666)));
////        } };
//        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
//        factory.setReadTimeout(60000);//ms
//        factory.setConnectTimeout(15000);//ms
//        return factory;
//    }

  @Bean
  public ClientHttpRequestFactory okHttp3ClientHttpRequestFactory() {
//	  Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 6666));
//	  OkHttpClient okHttpClient = new OkHttpClient.Builder().proxy(proxy).build();

	  OkHttp3ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory();
      factory.setReadTimeout(60000);//ms
      factory.setConnectTimeout(15000);//ms
      return factory;
  }
}
