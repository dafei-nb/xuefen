package cn.cdqf.dmsjportal.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpClientUtils {

    /**
     * http请求工具类，post请求
     *
     * @param url    url
     * @param params json字符串的参数
     * @return
     * @throws Exception
     */
    public static String httpPost(String url, String params) throws Exception {
        // 创建httpClient对象
        DefaultHttpClient defaultHttpClient = null;
        try {
            defaultHttpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/json;charset=ut-8");
            if (params != null) {
                System.out.println("请求参数：" + params);
                // 设置请求参数
                HttpEntity httpEntity = new StringEntity(params, "utf-8");
                httpPost.setEntity(httpEntity);
            }
            // 执行post请求，并得到相应结果
            HttpResponse httpResponse = defaultHttpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                String errorLog = "请求失败，errorCode:" + httpResponse.getStatusLine().getStatusCode();
                throw new Exception(url + errorLog);
            }
            // 解析结果
            HttpEntity responseEntity = httpResponse.getEntity();
            String responseStr = EntityUtils.toString(responseEntity, "utf-8");
            System.out.println("请求结果：" + responseStr);
            return responseStr;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (defaultHttpClient != null)
                defaultHttpClient.getConnectionManager().shutdown();
        }
    }

    /**
     * http请求工具类，get请求
     *
     * @param url    请求地址：可以已经带参数(?)，也可以没有带参数，在params中传过来
     * @param params 参数：值支持字符串和list
     * @return
     * @throws Exception
     */
    public static String httpGet(String url, Map<String, Object> params) throws Exception {
        DefaultHttpClient defaultHttpClient = null;
        try {
            defaultHttpClient = new DefaultHttpClient();
            if (params != null) {
                // 参数的拼接
                StringBuilder stringBuilder = new StringBuilder();
                Iterator<String> iterator = params.keySet().iterator();
                String key;
                while (iterator.hasNext()) {
                    key = iterator.next();
                    Object val = params.get(key);
                    if (val instanceof List) {
                        // 如果是list，则遍历拼接
                        List v = (List) val;
                        for (Object o : v) {
                            stringBuilder.append(key).append("=").append(o.toString()).append("&");
                        }
                    } else {
                        // 字符串：直接拼接
                        stringBuilder.append(key).append("=").append(val.toString()).append("&");
                    }
                }
                // 删除最后一个&
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                if (url.indexOf("?") > 0) {
                    // url地址本身包含？
                    url = url + "&" + stringBuilder.toString();
                } else {
                    url = url + "?" + stringBuilder.toString();
                }
            }
            System.out.println("请求地址：" + url);
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Content-Type", "application/json;charset=utf-8");
            // 执行
            HttpResponse httpResponse = defaultHttpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                String errorLog = "请求失败，errorCode:" + httpResponse.getStatusLine().getStatusCode();
                throw new Exception(url + errorLog);
            }
            // 解析结果
            HttpEntity responseEntity = httpResponse.getEntity();
            String responseStr = EntityUtils.toString(responseEntity, "utf-8");
            System.out.println("请求结果：" + responseStr);
            return responseStr;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (defaultHttpClient != null)
                defaultHttpClient.getConnectionManager().shutdown();
        }
    }
}
