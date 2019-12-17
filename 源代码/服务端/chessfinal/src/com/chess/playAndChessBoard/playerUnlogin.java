package com.chess.playAndChessBoard;

import java.net.Socket;

import com.chess.json.JavaBeanToString;
import com.chess.json.jsonObject;
import com.chess.judge.chessBoardMethod;
import com.chess.server.sendInformationToOp;

public class playerUnlogin {
	public static void unlogin(Integer key, Socket socket){
		//System.out.println("====退出信息===");
		playAndChessBoard.delSocket(key, socket);// 删除客户棋盘映射关系
		// 创建退出信息json字符串
		jsonObject unlogin = new jsonObject();
		unlogin.setState(402);
		unlogin.setPkey(key);
		sendInformationToOp.sendInfo(socket, JavaBeanToString.JBTS(unlogin), key);// 发送退出消息

		chessBoardMethod.initChessBoard(key);// 初始化棋盘
	//	System.out.println(clocker.getTime()+"服务端消息:已初始化棋盘");
	//	System.out.println("====退出信息打印完成===");
	}

}
