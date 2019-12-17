package com.chess.json;


import com.google.gson.Gson;
/**
 * 将JavaBean对象转变成json字符串
 * @author zhh
 *
 */
public class JavaBeanToString {
	public static String JBTS(jsonObject obj){
		Gson gson = new Gson();//创建GSON对象
		String result =gson.toJson(obj);
		return result;//返回json字符串 
	}
}
