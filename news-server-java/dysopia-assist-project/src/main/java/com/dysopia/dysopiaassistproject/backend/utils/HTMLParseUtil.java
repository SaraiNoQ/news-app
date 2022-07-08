package com.dysopia.dysopiaassistproject.backend.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：Abean
 * @date ：2022/6/24 20:30
 * @description ：TODO
 */

@Component
public class HTMLParseUtil {

    /**
     * @description: 获取新浪新闻（"https://news.sina.com.cn/"）网页新闻目录
     * @author: Abean
     * @date: 2022/6/26 14:51
     * @param: []
     * @return: java.util.Map<java.lang.String,java.lang.String>
     **/
    public static Map<String, String> getNewsTypeCategory() throws Exception {
        String uri = "https://news.sina.com.cn/";
        // jsoup解析网页
        Document doc = Jsoup.parse(HttpGetHTMLUtil.getHtml(uri));

        System.out.println("开始抓取新闻目录数据！");

        // 获取新闻种类名称及uri
        Element category = doc.getElementById("blk_cNav2_01");
        Elements types = category.getElementsByTag("a");
        // 存入map
        Map<String, String> newsTypeCate = new HashMap<>();
        for (Element el : types) {
            String corUri = el.attr("href");
            String title = el.text();
            // 排除非需要种类
            if (title.equals("滚动") || title.equals("排行") || title.equals("黑猫投诉") || title.equals("图片")
                    || title.equals("视频") || title.equals("专题")) continue;

            newsTypeCate.put(title, corUri);
        }

        return newsTypeCate;
    }

    /**
     * @description: 获取各种类type新闻页面每一条新闻的title和uri
     * @author: Abean
     * @date: 2022/6/27 20:44
     * @param: type         新闻种类
     *         uri          该种类具体页面uri
     *         className    需要爬取的class
     *         tagName      目标class下的标签tag
     * @return: java.util.Map<java.lang.String,java.lang.String>
     **/
    public static Map<String, String> getNewsInfo(String type, String uri, String className, String tagName) {
        try {
            // jsoup解析网页
            String html = HttpGetHTMLUtil.getHtml(uri);
            Document doc = Jsoup.parse(html);

            System.out.println("开始抓取新闻名称和对应链接！");

            // 获取对应class内容
            Elements classes = doc.getElementsByClass(className);
            Map<String, String> newsInfo = new HashMap<>();
            for (Element el : classes) {
                // 获取对应tag内容
                Elements tags = el.getElementsByTag(tagName);
                // 每个class最多爬取40条新闻
                int sz = Math.min(tags.size(), 40);
                for (int i = 0; i < sz; i++) {
                    Element info = tags.get(i);
                    // 获取新闻名称及对应uri
                    String title = info.text();
                    String corUri = info.getElementsByTag("a").attr("href");

                    // 排除非新闻、排除组图、视频新闻、不相关链接
                    if (title.length() < 7 || title.contains("组图") || title.contains("视频")) continue;
                    if (type.equals("科技") && !corUri.contains("https://finance.sina.com.cn/tech/")) continue;
                    if (type.equals("财经") && !corUri.contains("https://finance.sina.com.cn/")) continue;
                    if (type.equals("军事") && corUri.contains("slide.mil")) continue;
                    if (type.equals("体育") && corUri.contains("lottery.sina")) continue;

                    System.out.println(title + ":" + corUri);
                    newsInfo.put(title, corUri);
                }
            }

            return newsInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
//        // jsoup解析网页
//        String html = HttpGetHTMLUtil.getHtml(uri);
//        if (html == null) return null;
//        Document doc = Jsoup.parse(html);
//
//        System.out.println("开始抓取新闻名称和对应链接！");
//
//        // 获取对应class内容
//        Elements classes = doc.getElementsByClass(className);
//        Map<String, String> newsInfo = new HashMap<>();
//        for (Element el : classes) {
//            // 获取对应tag内容
//            Elements tags = el.getElementsByTag(tagName);
//            // 每个type最多爬取50条新闻
//            int sz = Math.min(tags.size(), 50);
//            for (int i = 0; i < sz; i++) {
//                Element info = tags.get(i);
//                // 获取新闻名称及对应uri
//                String title = info.text();
//                String corUri = info.getElementsByTag("a").attr("href");
//
//                // 排除非新闻、排除组图、视频新闻、不相关链接
//                if (title.length() < 7 || title.contains("组图") || title.contains("视频")) continue;
//                if (type.equals("科技") && !corUri.contains("https://finance.sina.com.cn/tech/")) continue;
//                if (type.equals("财经") && !corUri.contains("https://finance.sina.com.cn/")) continue;
//                if (type.equals("军事") && corUri.contains("slide.mil")) continue;
//                if (type.equals("体育") && corUri.contains("lottery.sina")) continue;
//
//                System.out.println(title + ":" + corUri);
//                newsInfo.put(title, corUri);
//            }
//        }
    }

    /**
     * @description: 获取具体title新闻页面的内容
     * @author: Abean
     * @date: 2022/6/28 21:35
     * @param: uri  具体title新闻的uri
     * @return: java.lang.String
     **/
    public static String getNewsContent(String uri) {
        try {
            // jsoup解析网页
            String html = HttpGetHTMLUtil.getHtml(uri);
            Document doc = Jsoup.parse(html);

            System.out.println("开始抓取新闻正文内容！");

            StringBuilder content = new StringBuilder();
            Elements article = doc.getElementsByClass("article");
            for (Element el : article) {
                Elements tags = el.getElementsByTag("p");
                for (Element p : tags) {
                    String para = p.text();
                    // 换行
                    content.append(para).append("\r\n");
                }
            }

            return content.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
//        // jsoup解析网页
//        String html = HttpGetHTMLUtil.getHtml(uri);
//        if (html == null) return null;
//        Document doc = Jsoup.parse(html);
//
//        System.out.println("开始抓取新闻正文内容！");
//
//        StringBuilder content = new StringBuilder();
//        Elements article = doc.getElementsByClass("article");
//        // 排除异常
//        if (article.isEmpty()) return null;
//
//        for (Element el : article) {
//            Elements tags = el.getElementsByTag("p");
//            for (Element p : tags) {
//                String para = p.text();
//                content.append(para).append("\r\n");
//            }
//        }
//
//        return content.toString();
    }

    public static void main(String[] args) throws Exception {
        String govUri = "http://gov.sina.com.cn/";
        String milUri = "https://mil.news.sina.com.cn/";
        String cnUri = "https://news.sina.com.cn/china/";
        String worldUri = "https://news.sina.com.cn/world/";
        String techUri = "https://tech.sina.com.cn/";
        String culUri = "http://cul.news.sina.com.cn/";
        String entUri = "https://ent.sina.com.cn/";
        String sportsUri = "https://sports.sina.com.cn/";
        String finaUri = "https://finance.sina.com.cn/";

        String govCN = "today";  // 两个class分别是 "today" 和 "direction"
        String cnCN = "feed-card-item";
        String worldCN = "news-item  img-news-item";
        String techCN = "seo_data_list";
        String finaCN = "m-list";
        String milCN = "part1 arcticle-list";
        String culCN = "blk12";
        String sportsCN = "ty-card-type10-r";
        String entCN = "seo_data_list";

        String govTN = "dt";
        String cnTn = "a";
        String worldTN = "h2";
        String techTN = "li";
        String finaTN = "a";
        String milTN = "li";
        String culTN = "a";
        String sportsTN = "li";
        String entTN = "li";

//        System.out.println(getNewsInfo("财经", finaUri, finaCN, finaTN).size());

//        getNewsInfo("政务", govUri, govCN, govTN);  // 政务
//        getNewsInfo("国内", cnUri, cnCN, cnTn);  // 国内 未爬取
//        getNewsInfo("国际", worldUri, worldCN, worldTN);  // 国际
//        getNewsInfo("科技", techUri, techCN, techTN);  // 科技
//        getNewsInfo("财经", finaUri, finaCN, finaTN);  // 财经
//        getNewsInfo("军事", milUri, milCN, milTN);  // 军事
//        getNewsInfo("文化", culUri, culCN, culTN);  // 文化 未爬取
//        getNewsInfo("体育", sportsUri, sportsCN, sportsTN);  // 体育
//        getNewsInfo("娱乐", entUri, entCN, entTN);  // 娱乐

//        Map<String, String> newsInfo = getNewsInfo("科技", techUri, techCN, techTN);  // 政务
//
//        String title = "";
//        String uri = "";
//        for (String str : newsInfo.keySet()) {
//            title = str;
//            uri = newsInfo.get(title);
//        }
//        System.out.println(title + ":" + uri);
    }
}
