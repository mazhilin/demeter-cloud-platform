package com.demeter.cloud.core.utils;

import com.demeter.cloud.core.constant.Constants;
import com.demeter.cloud.framework.utils.CheckEmptyUtil;
import com.demeter.cloud.model.exception.BusinessException;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 向指定 URL 发送POST方法的请求
 *
 * @return 远程资源的响应结果
 */
public class HttpUtil {
  /**
   * https连接头
   */
  public static final String HTTPS = "https://";
  /**
   * https的一种安全传输协议
   */
  public static final String TLS = "TLS";
  /**
   * http链接头，用于拼接url
   */
  public static final String HTTP = "http://";
  /**
   * httpClient建立连接超时时间
   */
  private static final String httpClientConnetionTimeOut;
  /**
   * 连接到某台目标机器的最大连接数
   */
  private static final String httpClientMaxPerRoute;
  /**
   * http client线程池最大链接数量
   */
  private static final String httpClientPoolMax;
  /**
   * httpClient建立反馈超时时间
   */
  private static final String httpClientSocketTimeOut;

  private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);
  private static PoolingHttpClientConnectionManager cm = null;
  private static HttpClientBuilder httpClientBuilder = null;
  private static RequestConfig requestConfig = null;

  /** 读取baseUtilConfig.properties配置文件设置配置 */
  static {
    httpClientPoolMax = "200";
    httpClientMaxPerRoute = "20";
    httpClientConnetionTimeOut = "2000";
    httpClientSocketTimeOut = "1800000";
    log.debug(
            String.format(
                    "HttpUtil properties: httpClientPoolMax[%s], httpClientMaxPerRoute[%s], httpClientConnetionTimeOut[%s], httpClientSocketTimeOut[%s]",
                    httpClientPoolMax,
                    httpClientMaxPerRoute,
                    httpClientConnetionTimeOut,
                    httpClientSocketTimeOut));
    if (CheckEmptyUtil.isEmpty(cm)) {
      cm = new PoolingHttpClientConnectionManager();
      cm.setMaxTotal(Integer.parseInt(httpClientPoolMax));
      cm.setDefaultMaxPerRoute(Integer.parseInt(httpClientMaxPerRoute));
    }
    requestConfig =
            RequestConfig.custom()
                    .setConnectTimeout(Integer.parseInt(httpClientConnetionTimeOut))
                    .setSocketTimeout(Integer.parseInt(httpClientSocketTimeOut))
                    .build();
    if (CheckEmptyUtil.isEmpty(httpClientBuilder)) {
      httpClientBuilder = HttpClients.custom().setConnectionManager(cm);
    }
  }

  /**
   * 向指定 URL 发送POST方法的请求
   *
   * @param url    发送请求的 URL
   * @param params 请求的参数集合
   * @return 远程资源的响应结果
   */
  @SuppressWarnings("unused")
  public static String sendPost(String url, Map<String, String> params) {
    OutputStreamWriter out = null;
    BufferedReader in = null;
    StringBuilder result = new StringBuilder();
    try {
      URL realUrl = new URL(url);
      HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
      // 发送POST请求必须设置如下两行
      conn.setDoOutput(true);
      conn.setDoInput(true);
      // POST方法
      conn.setRequestMethod("POST");
      // 设置通用的请求属性
      conn.setRequestProperty("accept", "*/*");
      conn.setRequestProperty("connection", "Keep-Alive");
      conn.setRequestProperty(
              "user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
      conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      conn.connect();
      // 获取URLConnection对象对应的输出流
      out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
      // 发送请求参数
      if (params != null) {
        StringBuilder param = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
          if (param.length() > 0) {
            param.append("&");
          }
          param.append(entry.getKey());
          param.append("=");
          param.append(entry.getValue());
        }
        out.write(param.toString());
      }
      // flush输出流的缓冲
      out.flush();
      // 定义BufferedReader输入流来读取URL的响应
      in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
      String line;
      while ((line = in.readLine()) != null) {
        result.append(line);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    // 使用finally块来关闭输出流、输入流
    finally {
      try {
        if (out != null) {
          out.close();
        }
        if (in != null) {
          in.close();
        }
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
    return result.toString();
  }

  /**
   * HTTP方式发送请求
   *
   * @param URL        URL地址
   * @param requestMsg 请求内容
   * @return 请求失败返回空串
   * @throws BusinessException
   */
  public static final String send(String URL, String requestMsg) throws BusinessException, IOException {
    if (CheckEmptyUtil.isAnyEmpty(URL, requestMsg)) {
      throw new BusinessException(
              String.format("HttpUtil.send args URL[%s], requestMsp[%s] is empty", URL, requestMsg));
    }
    String responseText = "";
    CloseableHttpResponse response = null;
    CloseableHttpClient client = null;
    try {
      client = getHttpClient(URL);
      HttpPost httpPost = new HttpPost(URL);
      httpPost.setConfig(requestConfig);

      String msg = CheckEmptyUtil.convertInvalidInvisibleToSpace(requestMsg);
      StringEntity se = new StringEntity(msg, Constants.UNIFIED_CODE);
      httpPost.setEntity(se);
      httpPost.setHeader("Content-Type", "application/soap+xml; charset=" + Constants.UNIFIED_CODE);
      response = client.execute(httpPost);
      HttpEntity entity = response.getEntity();
      int statusCode = response.getStatusLine().getStatusCode();
      if (200 == statusCode) {
        responseText = EntityUtils.toString(entity);
        responseText = new String(responseText.getBytes(), Constants.UNIFIED_CODE);
        log.debug(String.format("Response msg for URL %s :\n%s", URL, responseText));
      } else {
        log.error(String.format("Request URL %s fail, error code:%s", URL, statusCode));
      }
    } catch (HttpHostConnectException e) {
      throw new BusinessException(
              String.format(
                      "Buildding connection to url [%s] fail. it's over the max connection buildding time [%s]",
                      URL, httpClientConnetionTimeOut));
    } catch (Exception e) {
      throw new BusinessException(String.format("Send webservice for url:[%s], error:%s", URL, e));
    } finally {
      IOUtil.close(response);
    }
    return responseText;
  }

  /**
   * 根据url是否为https而构建不同的HttpClient客户端对象。
   *
   * @param url
   * @return
   * @throws NoSuchAlgorithmException
   * @throws KeyManagementException
   */
  @SuppressWarnings("deprecation")
  public static CloseableHttpClient getHttpClient(String url)
          throws NoSuchAlgorithmException, KeyManagementException {
    if (CheckEmptyUtil.trim(url).startsWith(HTTPS)) {
      X509TrustManager x509mgr =
              new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] xcs, String string) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] xcs, String string) {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                  return null;
                }
              };
      SSLContext sslContext = SSLContext.getInstance(TLS);
      sslContext.init(null, new TrustManager[]{x509mgr}, null);
      SSLConnectionSocketFactory ssl = new SSLConnectionSocketFactory(sslContext);

      return HttpClients.custom().setSSLSocketFactory(ssl).build();
    } else {
      return httpClientBuilder.build();
    }
  }

  /**
   * 用http GET方式请求
   *
   * @param URL http地址。
   * @return
   * @throws BusinessException
   */
  public static String httpGet(String URL) throws BusinessException, IOException {
    if (CheckEmptyUtil.isAnyEmpty(URL)) {
      throw new BusinessException(String.format("HttpUtil.send args URL[%s] is empty", URL));
    }
    String responseText = "";
    CloseableHttpResponse response = null;
    CloseableHttpClient client = null;
    try {
      client = getHttpClient(URL);
      HttpGet httpGet = new HttpGet(URL);
      httpGet.setConfig(requestConfig);
      response = client.execute(httpGet);
      HttpEntity entity = response.getEntity();
      int statusCode = response.getStatusLine().getStatusCode();
      if (200 == statusCode) {
        responseText = EntityUtils.toString(entity);
        responseText = new String(responseText.getBytes(), Constants.UNIFIED_CODE);
        log.debug(String.format("httpGet.Response msg for URL %s :\n%s", URL, responseText));
      } else {
        log.error(String.format("httpGet.Request URL %s fail, error code:%s", URL, statusCode));
        throw new BusinessException(
                String.format("httpGet.Request URL %s fail, error code:%s", URL, statusCode));
      }

    } catch (HttpHostConnectException e) {
      throw new BusinessException(
              String.format(
                      "Buildding connection to url [%s] fail. it's over the max connection buildding time [%s]",
                      URL, httpClientConnetionTimeOut));
    } catch (KeyManagementException e) {
      log.error(String.format("Send httpGet for url:[%s] catch a exception.", URL), e);
    } catch (NoSuchAlgorithmException e) {
      log.error(String.format("Send httpGet for url:[%s] catch a exception.", URL), e);
    } catch (ClientProtocolException e) {
      log.error(String.format("Send httpGet for url:[%s] catch a exception.", URL), e);
    } catch (IOException e) {
      log.error(String.format("Send httpGet for url:[%s] catch a exception.", URL), e);
    } finally {
      IOUtil.close(response);
    }
    return responseText;
  }

  /**
   * 用http POST方式请求
   *
   * @param url
   * @param params 参数，key-value形式
   * @return
   * @throws BusinessException
   */
  public static String httpPost(String url, Map<String, Object> params) throws BusinessException, IOException {
    if (CheckEmptyUtil.isAnyEmpty(url)) {
      throw new BusinessException(String.format("HttpUtil.httpPost args URL[%s] is empty", url));
    }
    String responseText = "";
    CloseableHttpResponse response = null;
    CloseableHttpClient client = null;
    try {
      client = getHttpClient(url);
      HttpPost httpPost = new HttpPost(url);
      httpPost.setConfig(requestConfig);
      List<BasicNameValuePair> nameValuePair = new ArrayList<>();
      if (CheckEmptyUtil.isNotEmpty(params)) {
        for (Map.Entry<String, Object> entry : params.entrySet()) {
          nameValuePair.add(
                  new BasicNameValuePair(entry.getKey(), (String) entry.getValue()));
        }
      }
      httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair, Constants.UNIFIED_CODE));
      response = client.execute(httpPost);
      HttpEntity entity = response.getEntity();
      int statusCode = response.getStatusLine().getStatusCode();
      if (200 == statusCode) {
        responseText = EntityUtils.toString(entity);
        responseText = new String(responseText.getBytes(), Constants.UNIFIED_CODE);
        log.debug(
                String.format(
                        "HttpUtil.httpPost's Response msg for URL %s with params %s :\n%s",
                        url, params, responseText));
      } else {
        log.error(
                String.format(
                        "HttpUtil.httpPost's Request URL %s fail, error code:%s", url, statusCode));
        throw new BusinessException(
                String.format("httpPost.Request URL %s fail, error code:%s", url, statusCode));
      }
    } catch (HttpHostConnectException e) {
      throw new BusinessException(
              String.format(
                      "Buildding connection to url [%s] fail. it's over the max connection buildding time [%s]",
                      url, httpClientConnetionTimeOut));

    } catch (KeyManagementException e) {
      log.error(String.format("Send httpPost for url:[%s] catch a exception.", url), e);
    } catch (NoSuchAlgorithmException e) {
      log.error(String.format("Send httpPost for url:[%s] catch a exception.", url), e);
    } catch (ClientProtocolException e) {
      log.error(String.format("Send httpPost for url:[%s] catch a exception.", url), e);
    } catch (IOException e) {
      log.error(String.format("Send httpPost for url:[%s] catch a exception.", url), e);
    } finally {
      IOUtil.close(response);
    }
    return responseText;
  }
}
