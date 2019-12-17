package com.clock;

import java.text.SimpleDateFormat;
import java.util.Date;


public class clocker {
	public static String  getTime(){
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return "["+format.format(date)+"] ";
	}
}
