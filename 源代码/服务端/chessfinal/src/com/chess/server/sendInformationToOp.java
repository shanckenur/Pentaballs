package com.chess.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.chess.playAndChessBoard.playAndChessBoard;

public class sendInformationToOp {
	public static void sendInfo(Socket s,String jsonString, Integer key) {
		Socket socket=s;
	//	System.out.println(clocker.getTime()+"服务端消息:正在转发消息 " + jsonString);
			Socket mSocket =null;
			if ((mSocket=playAndChessBoard.getOpSocket(socket, key)) != null) {
				BufferedWriter pout = null;
				try {
					// 获取输出流
					pout = new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream(), "UTF-8"));
					// 字符串末尾添加回车换行
					pout.write(jsonString + "\r\n");
					// 刷新缓存区
					pout.flush();
		//			System.out.println(clocker.getTime()+"服务端消息:已成功转发消息" + jsonString + "至:" + mSocket.getInetAddress() + ":"
		//					+ mSocket.getPort());
				} catch (IOException e) {
					//e.printStackTrace();
				//	System.out.println(clocker.getTime()+"服务端消息:消息转发失败，客户端已断开连接");
				} // try
			} else {
				//System.out.println(clocker.getTime()+"服务端消息:只有一个玩家");
			} // if
	}// send
}
