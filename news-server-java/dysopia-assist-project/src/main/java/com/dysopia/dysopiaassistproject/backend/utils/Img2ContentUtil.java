package com.dysopia.dysopiaassistproject.backend.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinamobile.cmss.sdk.ocr.ECloudDefaultClient;
import com.chinamobile.cmss.sdk.ocr.http.constant.Region;
import com.chinamobile.cmss.sdk.ocr.http.signature.Credential;
import com.chinamobile.cmss.sdk.ocr.request.IECloudRequest;
import com.chinamobile.cmss.sdk.ocr.request.ocr.OcrRequestFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author ：Abean
 * @date ：2022/7/6 20:11
 * @description ：实现图像转文字
 */

@Component
public class Img2ContentUtil {
    public static String user_ak;
    private static String user_sk;
    private static ECloudDefaultClient client;


    static {

        user_ak = "097cdf78bae242408602de66355d4649";
        user_sk = "710430b52aee4d1a8b3e2ad8698865df";
        Credential credential = new Credential(user_ak, user_sk);
        client = new ECloudDefaultClient(credential, Region.POOL_SZ);
    }

    /**
     * @description: 通用文字识别-通用印刷体识别
     * @author: Abean
     * @date: 2022/7/6 12:33
     * @param: []
     * @return: void
     **/
    public static String textGeneral(String imgBase64) {
        HashMap<String, Object> generalParams = new HashMap<>();
        JSONObject generalOptions = new JSONObject();
        generalOptions.put("rotate_180", true);
        generalOptions.put("language", "zh");
        generalParams.put("options", generalOptions);

//        // 参数为图片路径
//        IECloudRequest generalRequest = OcrRequestFactory.getOcrRequest("/api/ocr/v1/general", imgPath, generalParams);

        // 参数为图片的base64编码
        IECloudRequest generalRequestBase64 = OcrRequestFactory.getOcrBase64Request("/api/ocr/v1/general", imgBase64, generalParams);

        try {
            JSONObject response = (JSONObject) client.call(generalRequestBase64);
            // 获取包含文字区域的array
            JSONArray items = (JSONArray) response.get("items");
            // 保存每一个文字json键值对
            List<JSONArray> wordsList = new ArrayList<>();
            for (int i = 0; i < items.size(); i++) {
                JSONObject jsonObject = items.getJSONObject(i);
                wordsList.add((JSONArray) jsonObject.get("words"));
            }

            // 通过键值对获取每一个文字
            StringBuilder content = new StringBuilder();
            for (JSONArray arr : wordsList) {
                for (int i = 0; i < arr.size(); i++) {
                    JSONObject characters = arr.getJSONObject(i);
                    content.append(characters.get("character"));
                }
            }

            return content.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
//        System.out.println(textGeneral(null));
    }
}
