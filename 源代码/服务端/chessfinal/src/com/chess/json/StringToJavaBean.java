package com.chess.json;

import com.google.gson.Gson;
/**
 * 将json字符串转变成JavaBean对象
 * @author zhh
 *
 */
public class StringToJavaBean {
	
	public static jsonObject STJB(String jsonString){
		jsonObject obj= new jsonObject();//创建JavaBean对象
		Gson gson = new Gson();
		obj=gson.fromJson(jsonString, obj.getClass());
		return obj;//返回JavaBean对象
	}
}
