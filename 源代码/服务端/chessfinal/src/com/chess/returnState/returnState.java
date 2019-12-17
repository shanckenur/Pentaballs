package com.chess.returnState;

import com.chess.json.StringToJavaBean;
import com.chess.json.jsonObject;

public class returnState {
	public static int getState(String jsonString){
		jsonObject obj=StringToJavaBean.STJB(jsonString);
		return obj.getState();
	}
}
