package com.shanckenur.pentaballs.json;

import com.google.gson.Gson;

/**
 * Created by mz on 2017/6/1.
 */

public class JavaBeanToString {

    public static String JBTS(jsonObject obj){
        Gson gson = new Gson();//创建GSON对象
        String result =gson.toJson(obj);
        return result;//返回json字符串
    }
}
