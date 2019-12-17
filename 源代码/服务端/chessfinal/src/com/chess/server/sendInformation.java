package com.chess.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class sendInformation {

	public static void sendInfo(String jsonString, Socket s) {
		//System.out.println(clocker.getTime()+"服务端消息:正在转发消息 " + jsonString);
		BufferedWriter pout = null;
		Socket mSocket = s;
		try {
			// 获取输出流
			pout = new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream(), "UTF-8"));
			// 字符串末尾添加回车换行
			pout.write(jsonString + "\r\n");
			// 刷新缓存区
			pout.flush();
		//	System.out
		//			.println(clocker.getTime()+"服务端消息:已成功转发消息" + jsonString + "至:" + mSocket.getInetAddress() + ":" + mSocket.getPort());
		} catch (IOException e) {
			//e.printStackTrace();
			//System.out.println(clocker.getTime()+"服务端消息:发送消息失败，客户端已失去客户端响应");
		} // try
	}
}
