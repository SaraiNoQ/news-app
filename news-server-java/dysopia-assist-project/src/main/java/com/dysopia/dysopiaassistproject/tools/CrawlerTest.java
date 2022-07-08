package com.dysopia.dysopiaassistproject.tools;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author ：Abean
 * @date ：2022/6/24 16:16
 * @description ：测试爬虫
 */
public class CrawlerTest {
    public static void main(String[] args) throws Exception {
        // 打开浏览器，创建对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 请求地址
        // 新浪新闻：https://news.sina.com.cn/
        URIBuilder uri = new URIBuilder("https://news.sina.com.cn/");
        // 设置参数
        uri.setParameter("keys", "财经");
        // 发起请求
        HttpGet httpGet = new HttpGet(uri.build());

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            // 解析响应，获取数据
            if(response.getStatusLine().getStatusCode() ==  200){
                String content = EntityUtils.toString(response.getEntity(), "utf8");
                System.out.println(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭响应
            try {
                response.close();
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
    }
}
