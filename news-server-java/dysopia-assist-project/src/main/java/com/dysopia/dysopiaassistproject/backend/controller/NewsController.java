package com.dysopia.dysopiaassistproject.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dysopia.dysopiaassistproject.backend.pojo.News;
import com.dysopia.dysopiaassistproject.backend.pojo.NewsType;
import com.dysopia.dysopiaassistproject.backend.service.NewsService;
import com.dysopia.dysopiaassistproject.backend.service.NewsTypeService;
import com.dysopia.dysopiaassistproject.backend.utils.HTMLParseUtil;
import com.dysopia.dysopiaassistproject.backend.utils.PrintJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：Abean
 * @date ：2022/6/28 15:01
 * @description ：TODO
 */


@Controller
public class NewsController {

    @Autowired
    private NewsTypeService newsTypeService;
    @Autowired
    private NewsService newsService;

    /**
     * @description: 根据新闻种类表中信息定时抓取新闻存入数据库
     * @author: Abean
     * @date: 2022/6/28 15:03
     * @param:          ClassName                            TagName
     *         govCN = "today", "direction";               govTN = "dt";
     *         cnCN = "feed-card-item";                      cnTn = "a";
     *         worldCN = "news-item  img-news-item";     worldTN = "h2";
     *         techCN = "seo_data_list";                  techTN = "li";
     *         finaCN = "m-list";                          finaTN = "a";
     *         milCN = "part1 arcticle-list";              milTN = "li";
     *         culCN = "blk12";                             culTN = "a";
     *         sportsCN = "ty-card-type10-r";           sportsTN = "li";
     *         entCN = "seo_data_list";                    entTN = "li";
     *
     * @return:
     **/
//    @Scheduled(fixedDelay = 24 * 60 * 60 * 1000)
    public void crawlerNews() throws Exception {
        // 先清理news表中所有信息，然后重新爬取
        newsService.deleteAll();
        System.out.println("删除成功！");

        // 获取新闻种类及其uri
        List<NewsType> types = newsTypeService.getAllTypes();
        // 根据种类及其uri爬取该种类下所有新闻title和uri，并存入总新闻map
        Map<String, Map<String, String>> news = new HashMap<>();
        for (NewsType item : types) {
            String type = item.getType();
            String typeUri = item.getUri();
            System.out.println(type + ":" + typeUri);
            if (typeUri == null) continue;

            // 爬取科技新闻title和uri
            if (type.equals("科技")) {
                news.put(type, HTMLParseUtil.getNewsInfo(type, typeUri, "seo_data_list", "li"));
            }
            // 爬取国际新闻title和uri
            if (type.equals("国际")) {
                news.put(type, HTMLParseUtil.getNewsInfo(type, typeUri, "news-item  img-news-item", "h2"));
            }
            // 爬取娱乐新闻title和uri
            if (type.equals("娱乐")) {
                news.put(type, HTMLParseUtil.getNewsInfo(type, typeUri, "seo_data_list", "li"));
            }
            // 爬取体育新闻title和uri
            if (type.equals("体育")) {
                news.put(type, HTMLParseUtil.getNewsInfo(type, typeUri, "ty-card-type10-r", "li"));
            }
            // 爬取财经新闻title和uri
            if (type.equals("财经")) {
                news.put(type, HTMLParseUtil.getNewsInfo(type, typeUri, "m-list", "a"));
            }
            // 爬取军事新闻title和uri
            if (type.equals("军事")) {
                news.put(type, HTMLParseUtil.getNewsInfo(type, typeUri, "part1 arcticle-list", "li"));
            }
            // 爬取政务新闻title和uri
            if (type.equals("政务")) {
                news.put(type, HTMLParseUtil.getNewsInfo(type, typeUri, "today", "dt"));
            }
            // 爬取文化新闻title和uri
            if (type.equals("文化")) {
                news.put(type, HTMLParseUtil.getNewsInfo(type, typeUri, "blk12", "a"));
            }
        }
        System.out.println("新闻名称和对应链接抓取成功！");

        // 按新闻种类爬取
        for (String type : news.keySet()) {
            // 根据map中新闻title和uri，爬取新闻具体内容
            Map<String, String> cur = news.get(type);
            for (String title : cur.keySet()) {
                String uri = cur.get(title);
//                System.out.println(title + ":" + uri);
                String content = HTMLParseUtil.getNewsContent(uri);
                if (content == null || content.equals("")) continue;

                // 存入数据库
                News item = new News();
                item.setTitle(title);
                item.setType(type);
                item.setContent(content);
                newsService.addNews(item);
            }
        }
    }

    /**
     * @description: 所有新闻接口
     * @author: Abean
     * @date: 2022/7/7 8:55
     * @param: [request, response]
     * @return: void
     **/
    @PostMapping("/backend/showAllNews")
    @ResponseBody
    @CrossOrigin
    public void showAllNews(HttpServletRequest request, HttpServletResponse response) {
        List<News> newsList = newsService.getAll();

        Map<String, Object> res = new HashMap<>();
        res.put("code", 0);
        res.put("msg", "");
        res.put("data", newsList);
        response.setContentType("text/html;charset=utf-8");
        PrintJson.printJsonObj(response, res);
    }

    /**
     * @description: 科技新闻接口
     * @author: Abean
     * @date: 2022/6/30 18:48
     * @param: [request, response]
     * @return: void
     **/
    @GetMapping("/backend/showTechNews")
    @ResponseBody
    @CrossOrigin
    public void showTechNews(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> query = new HashMap<>();
        query.put("type", "科技");
        List<News> newsList = newsService.getNewsByType(query);

        Map<String, Object> res = new HashMap<>();
        res.put("code", 0);
        res.put("msg", "");
        res.put("data", newsList);
        response.setContentType("text/html;charset=utf-8");
        PrintJson.printJsonObj(response, res);
    }

    /**
     * @description: 国际新闻接口
     * @author: Abean
     * @date: 2022/6/30 18:48
     * @param: [request, response]
     * @return: void
     **/
    @GetMapping("/backend/showWorldNews")
    @ResponseBody
    @CrossOrigin
    public void showWorldNews(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> query = new HashMap<>();
        query.put("type", "国际");
        List<News> newsList = newsService.getNewsByType(query);

        Map<String, Object> res = new HashMap<>();
        res.put("code", 0);
        res.put("msg", "");
        res.put("data", newsList);
        response.setContentType("text/html;charset=utf-8");
        PrintJson.printJsonObj(response, res);
    }

    /**
     * @description: 娱乐新闻接口
     * @author: Abean
     * @date: 2022/6/30 18:48
     * @param: [request, response]
     * @return: void
     **/
    @GetMapping("/backend/showEntNews")
    @ResponseBody
    @CrossOrigin
    public void showEntNews(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> query = new HashMap<>();
        query.put("type", "娱乐");
        List<News> newsList = newsService.getNewsByType(query);

        Map<String, Object> res = new HashMap<>();
        res.put("code", 0);
        res.put("msg", "");
        res.put("data", newsList);
        response.setContentType("text/html;charset=utf-8");
        PrintJson.printJsonObj(response, res);
    }

    /**
     * @description: 体育新闻接口
     * @author: Abean
     * @date: 2022/6/30 18:48
     * @param: [request, response]
     * @return: void
     **/
    @GetMapping("/backend/showSportsNews")
    @ResponseBody
    @CrossOrigin
    public void showSportsNews(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> query = new HashMap<>();
        query.put("type", "体育");
        List<News> newsList = newsService.getNewsByType(query);

        Map<String, Object> res = new HashMap<>();
        res.put("code", 0);
        res.put("msg", "");
        res.put("data", newsList);
        response.setContentType("text/html;charset=utf-8");
        PrintJson.printJsonObj(response, res);
    }

    /**
     * @description: 财经新闻接口
     * @author: Abean
     * @date: 2022/6/30 18:48
     * @param: [request, response]
     * @return: void
     **/
    @GetMapping("/backend/showFinaNews")
    @ResponseBody
    @CrossOrigin
    public void showFinaNews(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> query = new HashMap<>();
        query.put("type", "财经");
        List<News> newsList = newsService.getNewsByType(query);

        Map<String, Object> res = new HashMap<>();
        res.put("code", 0);
        res.put("msg", "");
        res.put("data", newsList);
        response.setContentType("text/html;charset=utf-8");
        PrintJson.printJsonObj(response, res);
    }

    /**
     * @description: 军事新闻接口
     * @author: Abean
     * @date: 2022/6/30 18:48
     * @param: [request, response]
     * @return: void
     **/
    @GetMapping("/backend/showMilNews")
    @ResponseBody
    @CrossOrigin
    public void showMilNews(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> query = new HashMap<>();
        query.put("type", "军事");
        List<News> newsList = newsService.getNewsByType(query);

        Map<String, Object> res = new HashMap<>();
        res.put("code", 0);
        res.put("msg", "");
        res.put("data", newsList);
        response.setContentType("text/html;charset=utf-8");
        PrintJson.printJsonObj(response, res);
    }

    /**
     * @description: 政务新闻接口
     * @author: Abean
     * @date: 2022/6/30 18:48
     * @param: [request, response]
     * @return: void
     **/
    @GetMapping("/backend/showGovNews")
    @ResponseBody
    @CrossOrigin
    public void showGovNews(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> query = new HashMap<>();
        query.put("type", "政务");
        List<News> newsList = newsService.getNewsByType(query);

        Map<String, Object> res = new HashMap<>();
        res.put("code", 0);
        res.put("msg", "");
        res.put("data", newsList);
        response.setContentType("text/html;charset=utf-8");
        PrintJson.printJsonObj(response, res);
    }

    /**
     * @description: 文化新闻接口
     * @author: Abean
     * @date: 2022/6/30 18:48
     * @param: [request, response]
     * @return: void
     **/
    @GetMapping("/backend/showCulNews")
    @ResponseBody
    @CrossOrigin
    public void showCulNews(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> query = new HashMap<>();
        query.put("type", "文化");
        List<News> newsList = newsService.getNewsByType(query);

        Map<String, Object> res = new HashMap<>();
        res.put("code", 0);
        res.put("msg", "");
        res.put("data", newsList);
        response.setContentType("text/html;charset=utf-8");
        PrintJson.printJsonObj(response, res);
    }

    /**
     * @description: 新闻具体内容展示接口
     * @author: Abean
     * @date: 2022/7/1 13:25
     * @param: [request, response]
     * @return: void
     **/
    @GetMapping("/backend/showNewsContent")
    @ResponseBody
    @CrossOrigin
    public void showNewsContent(HttpServletRequest request, HttpServletResponse response) {
        String title = request.getParameter("title");
        QueryWrapper<News> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);

        Map<String, Object> res = new HashMap<>();
        News news = newsService.getNewsByTitle(queryWrapper);
        res.put("code", 0);
        res.put("msg", "");
        res.put("data", news);
        response.setContentType("text/html;charset=utf-8");
        PrintJson.printJsonObj(response, res);
    }

    /**
     * @description: 添加新闻，id自增，成功返回true，失败返回false
     * @author: Abean
     * @date: 2022/6/30 23:17
     * @param: [request, response]
     * @return: void
     **/
    @GetMapping("/web/addNews")
    @ResponseBody
    @CrossOrigin
    public void addNews(HttpServletRequest request, HttpServletResponse response) {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String type = request.getParameter("type");
        String content = request.getParameter("content");

        News news = new News();
        news.setTitle(title);
        news.setAuthor(author);
        news.setType(type);
        news.setContent(content);

        Map<String, Object> res = new HashMap<>();
        Boolean flag = newsService.addNews(news) == 1;
        res.put("success", flag);
        PrintJson.printJsonObj(response, res);
    }

    /**
     * @description: 根据id编辑新闻，成功返回true，失败返回false
     * @author: Abean
     * @date: 2022/7/1 11:16
     * @param: [request, response]
     * @return: void
     **/
    @PostMapping("/web/editNews")
    @ResponseBody
    @CrossOrigin
    public void editNews(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String type = request.getParameter("type");
        String content = request.getParameter("content");

        News news = new News();
        news.setId(Integer.parseInt(id));
        news.setTitle(title);
        news.setAuthor(author);
        news.setType(type);
        news.setContent(content);

        Map<String, Object> res = new HashMap<>();
        Boolean flag = newsService.updateNews(news) == 1;
        res.put("success", flag);
        PrintJson.printJsonObj(response, res);
    }

    /**
     * @description: 根据id删除新闻，成功返回true，失败返回false
     * @author: Abean
     * @date: 2022/7/1 11:19
     * @param: [request, response]
     * @return: void
     **/
    @GetMapping("/web/deleteNews")
    @ResponseBody
    @CrossOrigin
    public void deleteNews(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");

        Map<String, Object> res = new HashMap<>();
        Boolean flag = newsService.deleteNews(Integer.parseInt(id)) == 1;
        res.put("success", flag);
        PrintJson.printJsonObj(response, res);
    }
}
