package com.dysopia.dysopiaassistproject.backend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dysopia.dysopiaassistproject.backend.utils.Img2ContentUtil;
import com.dysopia.dysopiaassistproject.backend.utils.PrintJson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

/**
 * @author ：Abean
 * @date ：2022/7/6 17:09
 * @description ：获取前端图片存入本地路径，并实现图像转文字
 */

@Controller
public class Img2ContentController {

    @Value("${SavePath.upload-folder}")
    private String UPLOAD_FOLDER;

    /**
     * @description: 接收前端传入图片，存入本地文件夹，然后从本地文件夹获取图片转文字
     * @author: Abean
     * @date: 2022/7/6 20:38
     * @param: [file, request, response]
     * @return: void
     **/
    @RequestMapping(value = "/backend/upload", produces = "text/plain;charset=UTF-8")
    @CrossOrigin
    public void upload(HttpServletResponse response, @RequestBody String base ) {
        String img = "data:image/jpeg;base64," + base.substring(20, base.length()-4);
        Map<String, Object> res = new HashMap<>();
        if (img == null) {
            res.put("msg", "请上传图片！");
            response.setContentType("text/html;charset=utf-8");
            PrintJson.printJsonObj(response, res);
            return;
        }

        /*
         * Base64格式：data:[][;charset=][;base64],Base64编码
         * 只获取编码部分
         **/
        String imgBase64;
        if (img.contains("data:")) {
            String[] decoders = img.split(",");
            imgBase64 = decoders[decoders.length - 1];
        } else {
            imgBase64 = img;
        }

        // 将存入图片转文字
        String content = Img2ContentUtil.textGeneral(imgBase64);
        res.put("success", content != null);
        res.put("data", content);
        response.setContentType("text/html;charset=utf-8");
        PrintJson.printJsonObj(response, res);
    }
}
