package com.demeter.cloud.core.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * http请求封装
 * 
 * @author mrliu
 *
 */
public class HttpRequestUtil {

	/**
	 * 发送get请求
	 * 
	 * @param url
	 *            请求地址
	 * @return String
	 * @throws Exception
	 */
	public String get(String scheme, String host, String path, Integer port) throws Exception {
		return get(scheme, host, path, port, null);
	}

	/**
	 * 
	 * @param scheme
	 * @param host
	 * @param path
	 * @param port
	 * @return
	 * @throws Exception
	 */
	public String post(String scheme, String host, String path, Integer port) throws Exception {
		Map<String, String> parameter = new HashMap<String, String>();
		return post(scheme, host, path, port, parameter, null);
	}

	/**
	 * 
	 * @param scheme
	 * @param host
	 * @param path
	 * @param port
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public String post(String scheme, String host, String path, Integer port, String entity) throws Exception {
		return post(scheme, host, path, port, null, entity);
	}

	/**
	 * 发送get请求
	 * 
	 * @param url
	 *            请求地址
	 * @param parameter
	 *            参数
	 * @return string
	 * @throws Exception
	 * @throws URISyntaxException
	 */
	public String get(String scheme, String host, String path, Integer port, Map<String, String> parameter)
			throws Exception {
		URI uri = createURI(scheme, host, path, port, parameter);
		HttpGet httpget = new HttpGet(uri);
		return execute(httpget);
	}

	/**
	 * post请求
	 * 
	 * @param scheme
	 * @param host
	 * @param path
	 * @param port
	 * @param parameter
	 * @param entityString
	 *            json/xml/text/html
	 * @return
	 * @throws Exception
	 */
	public String post(String scheme, String host, String path, Integer port, Map<String, String> parameter,
			String entityString) throws Exception {
		URI uri = createURI(scheme, host, path, port, parameter);
		HttpPost httpPost = new HttpPost(uri);
		if (StringUtils.hasText(entityString)) {
			StringEntity requestEntity = new StringEntity(entityString, "utf-8");
			requestEntity.setContentEncoding("UTF-8");
			requestEntity.setContentType("application/json");
			httpPost.setEntity(requestEntity);
		}
		return execute(httpPost);
	}

	/**
	 * 执行请求
	 * 
	 * @param httpRequestBase
	 * @return
	 * @throws Exception
	 */
	private String execute(HttpRequestBase httpRequestBase) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = httpclient.execute(httpRequestBase);
		Integer status = response.getStatusLine().getStatusCode();
		if (status == HttpStatus.SC_OK) {
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity, "UTF-8");
		}
		response.close();
		httpclient.close();
		return null;
	}

	/**
	 * 创建uri
	 * 
	 * @param scheme
	 * @param host
	 * @param path
	 * @param port
	 * @param parameter
	 * @return
	 */
	private URI createURI(String scheme, String host, String path, Integer port, Map<String, String> parameter) {
		URIBuilder uriBuilder;
		try {
			uriBuilder = new URIBuilder().setScheme(scheme).setHost(host).setPath(path)
					.setPort(port == null ? 80 : port);
			setParameter(uriBuilder, parameter);
			return uriBuilder.build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 设置请求参数
	 * 
	 * @param uriBuilder
	 * @param parameter
	 */
	private void setParameter(URIBuilder uriBuilder, Map<String, String> parameter) {
		if (parameter == null) {
			return;
		}
		for (Map.Entry<String, String> entry : parameter.entrySet()) {
			uriBuilder.setParameter(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * 绕过验证
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sc = SSLContext.getInstance("SSLv3");

		// 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
		X509TrustManager trustManager = new X509TrustManager() {
			@Override
			public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) {
			}

			@Override
			public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) {
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};

		sc.init(null, new TrustManager[] { trustManager }, null);
		return sc;
	}

}
