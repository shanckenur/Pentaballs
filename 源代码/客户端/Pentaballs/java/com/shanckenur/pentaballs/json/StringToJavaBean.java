package com.shanckenur.pentaballs.json;

import com.google.gson.Gson;

/**
 * Created by mz on 2017/6/1.
 */

public class StringToJavaBean {

    public static jsonObject STJB(String jsonString){
        jsonObject obj= new jsonObject();//创建JavaBean对象
        Gson gson = new Gson();
        obj=gson.fromJson(jsonString, obj.getClass());
        return obj;//返回JavaBean对象
    }
}
