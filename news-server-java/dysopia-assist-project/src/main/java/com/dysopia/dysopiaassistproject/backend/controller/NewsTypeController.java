package com.dysopia.dysopiaassistproject.backend.controller;

import com.dysopia.dysopiaassistproject.backend.pojo.NewsType;
import com.dysopia.dysopiaassistproject.backend.service.NewsTypeService;
import com.dysopia.dysopiaassistproject.backend.utils.HTMLParseUtil;

import com.dysopia.dysopiaassistproject.backend.utils.PrintJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：Abean
 * @date ：2022/6/26 0:16
 * @description ：TODO
 */

@Controller
public class NewsTypeController {

    @Autowired
    private NewsTypeService newsTypeService;

//    @Scheduled(fixedDelay = 100 * 1000)
    public void saveCrawlerNewsType() throws Exception {
        Map<String, String> category = HTMLParseUtil.getNewsTypeCategory();

        // 新建对象存储
        for (String title : category.keySet()) {
            NewsType newsType = new NewsType();
            newsType.setType(title);
            newsType.setUri(category.get(title));

            // 保存数据库
            newsTypeService.addNewsType(newsType);
        }

        System.out.println("新闻目录数据抓取完成！");
    }

    /**
     * @description: 新闻种类接口
     * @author: Abean
     * @date: 2022/6/30 10:27
     * @param:
     * @return: NewsType
     **/
    @GetMapping("/backend/showNewsType")
    @ResponseBody
    @CrossOrigin
    public void showNewsType(HttpServletRequest request, HttpServletResponse response) {
        List<NewsType> newsTypeList = newsTypeService.getAllTypes();

        Map<String, Object> res = new HashMap<>();
        res.put("code", 0);
        res.put("msg", "");
        res.put("data", newsTypeList);
        response.setContentType("text/html;charset=utf-8");
        PrintJson.printJsonObj(response, res);
    }

    /**
     * @description: 新增新闻，成功返回true，失败返回false
     * @author: Abean
     * @date: 2022/7/1 13:45
     * @param: [request, response]
     * @return: void
     **/
    @GetMapping("/web/addNewsType")
    @ResponseBody
    @CrossOrigin
    public void addNewsType(HttpServletRequest request, HttpServletResponse response) {
        String type = request.getParameter("type");
        NewsType newsType = new NewsType();
        newsType.setType(type);

        Map<String, Object> res = new HashMap<>();
        Boolean flag = newsTypeService.addNewsType(newsType) == 1;
        res.put("success", flag);
        PrintJson.printJsonObj(response, res);
    }

    @GetMapping("/web/deleteNewsType")
    @ResponseBody
    @CrossOrigin
    public void deleteNewsType(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");

        Map<String, Object> res = new HashMap<>();
        Boolean flag = newsTypeService.deleteNewsType(Integer.parseInt(id)) == 1;
        res.put("success", flag);
        PrintJson.printJsonObj(response, res);
    }
}
