package com.dysopia.dysopiaassistproject.backend.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

/**
 * @author ：Abean
 * @date ：2022/6/25 12:11
 * @description ：获取网页资源
 */

@Component
public class HttpGetHTMLUtil {
    private PoolingHttpClientConnectionManager cm;

    public HttpGetHTMLUtil() {
        cm = new PoolingHttpClientConnectionManager();
        //设置最大连接数
        cm.setMaxTotal(100);
        cm.setDefaultMaxPerRoute(10);
    }

    public static String getHtml(String uri) {
        // 打开浏览器，创建对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 发起请求
        HttpGet httpGet = new HttpGet(uri.trim());

        CloseableHttpResponse response = null;
        String html = null;
        try {
            response = httpClient.execute(httpGet);
            // 解析响应，获取数据
            if(response.getStatusLine().getStatusCode() ==  200){
                html = EntityUtils.toString(response.getEntity(), "utf8");
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭响应
            try {
                if (response != null) response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 关闭浏览器
            try {
                httpClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return html;
    }
}
