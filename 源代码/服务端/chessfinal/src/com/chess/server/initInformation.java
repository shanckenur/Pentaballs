package com.chess.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import com.chess.json.JavaBeanToString;
import com.chess.json.jsonObject;
import com.clock.clocker;

public class initInformation {
	public static void init(Socket s){
		Socket socket=s;
		PrintWriter out = null;
		try {
		//	System.out.println("====客户端初始化信息====");
			// 创建JavaBean对象
			jsonObject obj = new jsonObject();
			obj.setState(301);//连接成功
			// 将JavaBean对象转换成json字符串
			String jsonString = JavaBeanToString.JBTS(obj);
			// 获取输出流
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")),
					true);
			// 在字符串末尾加上换行回车，防止接收端挂起
			out.write(jsonString + "\r\n");
			// 刷新缓存区
			out.flush();
			sendInformation.sendInfo(jsonString,socket);//发送连接成功的消息到客户端
		//	System.out
		//			.println(clocker.getTime()+"服务端消息:数据" + jsonString + "已经发送至" + socket.getInetAddress() + ":" + socket.getPort());
		//	System.out.println("====客户端信息初始化完成====");
		} catch (IOException e) {
		//	e.printStackTrace();
			System.out.println(clocker.getTime()+"服务端消息:初始化失败");
		} // try
	}
}
