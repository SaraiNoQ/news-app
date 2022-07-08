package com.dysopia.dysopiaassistproject.backend.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 输出json格式
 * @author: Abean
 * @date: 2022/6/30 16:05
 * @param:
 * @return:
 **/
public class PrintJson {
	
	/**
	 * @description: 将boolean值解析为json串
	 * @author: Abean
	 * @date: 2022/6/30 16:05
	 * @param: [response, flag]
	 * @return: void
	 **/
	public static void printJsonFlag(HttpServletResponse response, boolean flag){
		
		Map<String, Boolean> map = new HashMap<>();
		map.put("result", flag);
		
		ObjectMapper om = new ObjectMapper();
		try {
			//{"success":true}
			String json = om.writeValueAsString(map);
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
	
	/**
	 * @description: 将对象解析为json串
	 * @author: Abean
	 * @date: 2022/6/30 16:05
	 * @param: [response, obj]
	 * @return: void
	 **/
	public static void printJsonObj(HttpServletResponse response, Object obj){
		ObjectMapper om = new ObjectMapper();

		try {
			String json = om.writeValueAsString(obj);
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}























