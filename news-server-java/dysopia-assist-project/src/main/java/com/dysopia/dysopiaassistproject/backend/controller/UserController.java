package com.dysopia.dysopiaassistproject.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dysopia.dysopiaassistproject.backend.pojo.News;
import com.dysopia.dysopiaassistproject.backend.pojo.User;
import com.dysopia.dysopiaassistproject.backend.service.UserService;
import com.dysopia.dysopiaassistproject.backend.utils.PrintJson;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @date ：2022/7/5 13:41
 * @description ：TODO
 */

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    /**
     * @description: 客户端用户登录
     * @author: Abean
     * @date: 2022/7/5 17:42
     * @param:
     * @return:
     **/
    @PostMapping("/backend/login")
    @ResponseBody
    @CrossOrigin
    public void login(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String psd = request.getParameter("password");

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User item = userService.isExist(queryWrapper);

        Map<String, Object> res = new HashMap<>();
        // 用户名不存在
        if (item == null) {
            res.put("msg", "用户名不存在！");
            res.put("success", false);
        } else {
            // 用户名存在，判断密码是否正确
            boolean flag = psd.equals(item.getPassword());
            if (flag) {
                res.put("success", true);
            } else {
                res.put("msg", "密码不正确！");
                res.put("success", false);
            }
        }
        response.setContentType("text/html;charset=utf-8");
        PrintJson.printJsonObj(response, res);
    }

    /**
     * @description: 客户端用户注册
     * @author: Abean
     * @date: 2022/7/5 18:37
     * @param: [request, response]
     * @return: void
     **/
    @PostMapping("/backend/register")
    @ResponseBody
    @CrossOrigin
    public void register(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String psd = request.getParameter("password");
        String email = request.getParameter("email");

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User item = userService.isExist(queryWrapper);

        Map<String, Object> res = new HashMap<>();
        // 判定用户名是否存在，存在则不能注册
        if (item != null) {
            res.put("msg", "用户名已存在！");
            res.put("success", false);
        } else {
            // 用户名不存在，新增用户
            User user = new User();
            user.setUsername(username);
            user.setPassword(psd);
            if (email != null) user.setEmail(email);

            userService.addUser(user);
            res.put("success", true);
        }
        response.setContentType("text/html;charset=utf-8");
        PrintJson.printJsonObj(response, res);
    }

    /**
     * @description: 新增用户，id自增
     * @author: Abean
     * @date: 2022/7/5 13:55
     * @param: [request, response]
     * @return: void
     **/
    @GetMapping("/web/addUser")
    @ResponseBody
    @CrossOrigin
    public void addUser(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String psd = request.getParameter("password");
        String email = request.getParameter("email");

        User item = new User();
        item.setUsername(username);
        item.setPassword(psd);
        if (email != null) item.setEmail(email);

        Map<String, Object> res = new HashMap<>();
        boolean flag = userService.addUser(item) == 1;
        res.put("success", flag);
        PrintJson.printJsonObj(response, res);
    }


    /**
     * @description: 服务端管理员根据id修改用户信息，仅支持修改密码和邮箱
     * @author: Abean
     * @date: 2022/7/5 16:14
     * @param: [request, response]
     * @return: void
     **/
    @GetMapping("/web/editUser")
    @ResponseBody
    @CrossOrigin
    public void editUser(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String psd = request.getParameter("password");
        String email = request.getParameter("email");

        User item = new User();
        item.setId(Integer.parseInt(id));
        item.setPassword(psd);
        if (email != null) item.setEmail(email);

        Map<String, Object> res = new HashMap<>();
        boolean flag = userService.editUser(item) == 1;
        res.put("success", flag);
        PrintJson.printJsonObj(response, res);
    }

    /**
     * @description: 服务端管理员根据id查询用户信息
     * @author: Abean
     * @date: 2022/7/5 16:25
     * @param: [request, response]
     * @return: void
     **/
    @GetMapping("/web/queryUser")
    @ResponseBody
    @CrossOrigin
    public void queryUser(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");

        User item = userService.selectUser(Integer.parseInt(id));
        Map<String, Object> res = new HashMap<>();
        res.put("code", 0);
        res.put("msg", "");
        res.put("data", item);
        response.setContentType("text/html;charset=utf-8");
        PrintJson.printJsonObj(response, res);
    }

    /**
     * @description: 所有用户接口
     * @author: SaraiNoQ
     * @date: 2022/7/7 8:55
     * @param: [request, response]
     * @return: void
     **/
    @PostMapping("/backend/showAllUsers")
    @ResponseBody
    @CrossOrigin
    public void showAllNews(HttpServletRequest request, HttpServletResponse response) {
        List<User> newsList = userService.getAll();

        Map<String, Object> res = new HashMap<>();
        res.put("code", 0);
        res.put("msg", "");
        res.put("data", newsList);
        response.setContentType("text/html;charset=utf-8");
        PrintJson.printJsonObj(response, res);
    }

    /**
     * @description: 服务端管理员根据id删除用户
     * @author: Abean
     * @date: 2022/7/5 16:29
     * @param:
     * @return:
     **/
    @GetMapping("/web/deleteUser")
    @ResponseBody
    @CrossOrigin
    public void deleteUser(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");

        Map<String, Object> res = new HashMap<>();
        Boolean flag = userService.deleteUser(Integer.parseInt(id)) == 1;
        res.put("success", flag);
        PrintJson.printJsonObj(response, res);
    }
}
