/**
 * 
 */
package com.softisland.bean.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

import com.softisland.bean.domian.SoftCookie;
import com.softisland.bean.domian.SoftHeader;
import com.softisland.bean.domian.SoftHttpResponse;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.FormBody.Builder;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Administrator
 *
 */
@Slf4j
public class OKHttpClientUtil {
	
	    /**
		 * 默认：请求获取数据的超时时间，单位秒。
		 */
		private static final int defaultSocketTimeout = 30;
		/**
		 * 默认：设置连接超时时间，单位秒。
		 */
		private static final int defaultConnectTimeout = 5;
		
		/**
		 * 初始化
		 * @param host
		 * @param port
		 * @param softCookies
		 * @return
		 */
		public static OkHttpClient initOkHttpClient(String host,Integer port,SoftCookie[] softCookies,boolean redirects){
			
			OkHttpClient.Builder builder= new OkHttpClient.Builder()
									.connectTimeout(defaultConnectTimeout, TimeUnit.SECONDS)
									.readTimeout(defaultSocketTimeout, TimeUnit.SECONDS)
									.writeTimeout(defaultSocketTimeout,TimeUnit.SECONDS) 
									.retryOnConnectionFailure(true)
									.followRedirects(redirects)
									.followSslRedirects(redirects)
									;
			
			if(StringUtils.isNoneBlank(host) && !host.equals("127.0.0.1")){
				builder.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port)));
				/**
				 * Authenticator proxyAuthenticator = null;
				 * 需要代理认证
				 * proxyAuthenticator = new Authenticator() {
					  @Override public Request authenticate(Route route, Response response) throws IOException {
					       String credential = Credentials.basic(username, password);
					       return response.request().newBuilder()
					           .header("Proxy-Authorization", credential)
					           .build();
					  }
					};
				 */
			}
			
			if(softCookies!=null && softCookies.length>0){
				builder.cookieJar(new CookieJar() {
					@Override
					public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
						// TODO Auto-generated method stub
						
					}
					@Override
					public List<Cookie> loadForRequest(HttpUrl url) {
						List<Cookie> cookies = new ArrayList<Cookie>();
						for(SoftCookie softCookie : softCookies){
							cookies.add(new Cookie.Builder()
									.domain(softCookie.getDomain())
									.name(softCookie.getName())
									.path(StringUtils.isNoneBlank(softCookie.getPath())?softCookie.getPath():"/")
									.value(softCookie.getValue())
									.build());
						}
						return cookies;
					}
				});
			}
			
			return builder.build();
		}
		/**
		 * 不使用代理GET
		 * @param url
		 * @return
		 * @throws Exception
		 */
		public static SoftHttpResponse getJson(String url)throws Exception{
			return getJson(url, null, null, null, null);
		}
		
		/**
		 * GET方法
		 * @param url
		 * @param host
		 * @param port
		 * @return
		 * @throws Exception
		 */
		public static SoftHttpResponse getJson(String url,String host,Integer port)throws Exception{
			return getJson(url,null,host,port,null);
		}
		
		/**
		 * GET方法
		 * @param url
		 * @param softCookies
		 * @param host
		 * @param port
		 * @param headers
		 * @return
		 * @throws Exception
		 */
		public static SoftHttpResponse getJson(String url,SoftCookie[] softCookies,String host,Integer port, SoftHeader[] headers)throws Exception{
			OkHttpClient client = initOkHttpClient(host,port,softCookies,false);
			Request.Builder requestBuilder = new Request.Builder()
					.url(url)
					.get();
			if(headers != null && headers.length > 0){
				for(SoftHeader header : headers){
					requestBuilder.addHeader(header.getName(), header.getValue());
				}
			}
			Request request = requestBuilder.build();
			Response response = null;
			
			SoftHttpResponse softHttpResponse = null;
			
			try {
				response = client.newCall(request).execute();
//				if(response.code() == 200){
					softHttpResponse = getSoftHttpResponse(response);
//				}else{
//					log.info("地址:({})网络请求出现异常({})",url,JSON.toJSONString(response));
//				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return softHttpResponse;
		}
		
//		public static SoftHttpResponse getJsonRedirect(String url,SoftCookie[] softCookies,String host,Integer port, SoftHeader[] headers){
//			OkHttpClient client = initOkHttpClient(host,port,softCookies);
//			Request request = new Request.Builder()
//					.url(url)
//					.get()
//					.build();
//		}
		
		
		/**
		 * POSTJSON 不使用代理
		 * @param url
		 * @param json
		 * @return
		 * @throws Exception
		 */
		public static SoftHttpResponse postJsonDataToUrl(String url, String json)throws Exception{
			return postJsonDataToUrl(url, json, null, null);
		}
		
		
		/**
		 * POSTJSON 代理
		 * @param url
		 * @param json
		 * @param host
		 * @param port
		 * @return
		 * @throws Exception
		 */
		public static SoftHttpResponse postJsonDataToUrl(String url, String json,String host,Integer port)throws Exception{
			return postJsonDataToUrl(url, json, null, null, host, port);
		}
		
		/**
		 * POSTJSON
		 * @param url
		 * @param json
		 * @param softCookies
		 * @param headers
		 * @param host
		 * @param port
		 * @return
		 * @throws Exception
		 */
		public static SoftHttpResponse postJsonDataToUrl(String url, String json, SoftCookie[] softCookies, SoftHeader[] headers,String host,Integer port)throws Exception{
			OkHttpClient client = initOkHttpClient(host,port,softCookies,false);
			
			RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
			
			Request request = new Request.Builder().url(url).post(body).build();
			Response response = null;
			SoftHttpResponse softHttpResponse = null;
			try {
				response = client.newCall(request).execute();
				if(response.code() == 200){
					softHttpResponse = getSoftHttpResponse(response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return softHttpResponse;
		}
		
		/**
		 * POST 不使用代理
		 * @param url
		 * @param paraMap
		 * @return
		 * @throws Exception
		 */
		public static SoftHttpResponse postParamsToUrl(String url,Map<String,String> paraMap)throws Exception{
			return postParamsToUrl(url, paraMap,null,null);
		}
		
		/**
		 * POST PARAM 代理
		 * @param url
		 * @param paraMap
		 * @param host
		 * @param port
		 * @return
		 * @throws Exception
		 */
		public static SoftHttpResponse postParamsToUrl(String url,Map<String,String> paraMap,String host,Integer port)throws Exception{
			return postParamsToUrl(url,paraMap,null,null,host,port);
		}
		
		/**
		 * POST
		 * @param url
		 * @param paraMap
		 * @param softCookies
		 * @param host
		 * @param port
		 * @return
		 * @throws Exception
		 */
		public static SoftHttpResponse postParamsToUrl(String url,Map<String,String> paraMap,SoftCookie[] softCookies,String host,Integer port)throws Exception{
			
			OkHttpClient client = initOkHttpClient(host,port,softCookies,false);
			
			Builder bodyBuilder = new FormBody.Builder();
			if(paraMap!=null && paraMap.size()>0){
				paraMap.forEach((k,v)->{
					bodyBuilder.add(k, v);
				});
			}
			
			Request request = new Request.Builder().url(url).post(bodyBuilder.build()).build();
			
			Response response = null;
			SoftHttpResponse softHttpResponse = null;
			try {
				response = client.newCall(request).execute();
				if(response.code() == 200){
					softHttpResponse = getSoftHttpResponse(response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return softHttpResponse;
		}
		
		/**
		 * POST
		 * @param url
		 * @param paraMap
		 * @param softCookies
		 * @param softHeaders
		 * @param host
		 * @param port
		 * @return
		 * @throws Exception
		 */
		public static SoftHttpResponse postParamsToUrl(String url,Map<String,String> paraMap,SoftCookie[] softCookies,SoftHeader[] softHeaders,String host,Integer port)throws Exception{
			
			OkHttpClient client = initOkHttpClient(host,port,softCookies,false);
			
			Builder bodyBuilder = new FormBody.Builder();
			if(paraMap!=null && paraMap.size()>0){
				paraMap.forEach((k,v)->{
					bodyBuilder.add(k, v);
				});
			}
			
			Request.Builder builder = new Request.Builder().url(url).post(bodyBuilder.build());
			
			if(softHeaders!=null && softHeaders.length>0){
				for(SoftHeader h : softHeaders){
					builder.addHeader(h.getName(), h.getValue());
				}
			}
			
			
			Request request = builder.build();
			
			Response response = null;
			SoftHttpResponse softHttpResponse = null;
			try {
				response = client.newCall(request).execute();
				if(response.code() == 200){
					softHttpResponse = getSoftHttpResponse(response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return softHttpResponse;
		}
		
		
		/**
		 * POST 数组表单
		 * @param url
		 * @param paraMap
		 * @param softCookies
		 * @param softHeaders
		 * @param host
		 * @param port
		 * @return
		 * @throws Exception
		 */
		public static SoftHttpResponse postArrayParamsToUrl(String url,List<Map<String,String>> paraMap,SoftCookie[] softCookies,SoftHeader[] softHeaders,String host,Integer port)throws Exception{
			
			OkHttpClient client = initOkHttpClient(host,port,softCookies,false);
			
			Builder bodyBuilder = new FormBody.Builder();
			
			if(paraMap!=null && paraMap.size()>0){
				paraMap.forEach(l->{
					l.forEach((k,v)->{
						bodyBuilder.add(k, v);
					});
				});
			}
			
			Request.Builder builder = new Request.Builder().url(url).post(bodyBuilder.build());
			
			if(softHeaders!=null && softHeaders.length>0){
				for(SoftHeader h : softHeaders){
					builder.addHeader(h.getName(), h.getValue());
				}
			}
			
			
			Request request = builder.build();
			
			Response response = null;
			SoftHttpResponse softHttpResponse = null;
			try {
				response = client.newCall(request).execute();
				if(response.code() == 200){
					softHttpResponse = getSoftHttpResponse(response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return softHttpResponse;
		}
		
		
		/**
		 * 组装softHttpResponse
		 * @param response
		 * @return
		 * @throws IOException
		 */
		private static SoftHttpResponse getSoftHttpResponse(Response response) throws IOException{
			SoftHttpResponse soft = new SoftHttpResponse();
			soft.setStatus(response.code());
			soft.setContent(response.body().string());
			
			Headers headers = response.headers();
			Map<String,String> headersMap = new HashMap<String,String>();
			
			for(int i = 0; i <headers.size(); i++){
				if(headersMap.containsKey(headers.name(i))){
					headersMap.put(headers.name(i), headersMap.get(headers.name(i))+";"+headers.value(i));
				}else{
					headersMap.put(headers.name(i), headers.value(i));
				}
			}
			soft.setHeaders(headersMap);
			
			Map<String,String> cookies = new HashMap<String,String>();
			String cookiesStr = null;
			
			if(headersMap.containsKey("Set-Cookie")){
				cookiesStr = headersMap.get("Set-Cookie");
			}
			
			if(StringUtils.isBlank(cookiesStr)){
				return soft;
			}
			
			for(String str : cookiesStr.split(";")){
				String[] strs = str.split("=");
				if(strs.length<2){
					continue;
				}
				cookies.put(strs[0], strs[1]);
			}
			
			soft.setCookies(cookies);
			
			return soft;
		}
		
		/**
		 * 得到请求数据文件
		 * @param url
		 * @param softCookies
		 * @param host
		 * @param port
		 * @param headers
		 * @return
		 */
		public static InputStream getData(String url,SoftCookie[] softCookies,String host,Integer port, SoftHeader[] headers){
			
			OkHttpClient client = initOkHttpClient(host,port,softCookies,false);
			
			Request.Builder requestBuilder = new Request.Builder()
					.url(url)
					.get();
			if(headers != null && headers.length > 0){
				for(SoftHeader header : headers){
					requestBuilder.addHeader(header.getName(), header.getValue());
				}
			}
			Request request = requestBuilder.build();
			
			Response response = null;
			
			InputStream inputStream = null;
			try {
				response = client.newCall(request).execute();
				
				inputStream = response.body().byteStream();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return inputStream;
		}
		
		
		
		/**
		 * Multipart-Form POST
		 * @param url
		 * @param paraMap
		 * @param softCookies
		 * @param softHeaders
		 * @param host
		 * @param port
		 * @return
		 */
		public static SoftHttpResponse postMultipartParam(String url,Map<String,String> paraMap,SoftCookie[] softCookies,SoftHeader[] softHeaders,String host,Integer port){
			OkHttpClient client = initOkHttpClient(host,port,softCookies,false);
			MultipartBody.Builder bodyBuilder = new MultipartBody.Builder()
					.setType(MultipartBody.FORM);
			paraMap.forEach((k,v)->{
				bodyBuilder.addFormDataPart(k, v);
			});
			Request.Builder builder = new Request.Builder().url(url).post(bodyBuilder.build());
			
			if(softHeaders!=null && softHeaders.length>0){
				for(SoftHeader h : softHeaders){
					builder.addHeader(h.getName(), h.getValue());
				}
			}
			
			Request request = builder.build();
			
			Response response = null;
			SoftHttpResponse softHttpResponse = null;
			try {
				response = client.newCall(request).execute();
				if(response.code() == 200){
					softHttpResponse = getSoftHttpResponse(response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return softHttpResponse;
		}
		
		/**
		 * POST STRING 
		 * @param url
		 * @param paraMap
		 * @param softCookies
		 * @param softHeaders
		 * @param host
		 * @param port
		 * @return
		 * @throws Exception
		 */
		public static SoftHttpResponse postStringToUrl(String url,String params,SoftCookie[] softCookies,SoftHeader[] softHeaders,String host,Integer port)throws Exception{
			
			OkHttpClient client = initOkHttpClient(host,port,softCookies,false);
			
			
			RequestBody body = null;
			if(StringUtils.isNoneBlank(params)){
				body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8"), params);
			}
			
			Request.Builder builder = new Request.Builder().url(url).post(body);
			
			if(softHeaders!=null && softHeaders.length>0){
				for(SoftHeader h : softHeaders){
					builder.addHeader(h.getName(), h.getValue());
				}
			}
			
			Request request = builder.build();
			
			Response response = null;
			SoftHttpResponse softHttpResponse = null;
			try {
				response = client.newCall(request).execute();
//				if(response.code() == 200){
					softHttpResponse = getSoftHttpResponse(response);
//				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return softHttpResponse;
		}
}
