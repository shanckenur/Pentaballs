package com.chess.server;

import java.net.Socket;

import com.chess.json.JavaBeanToString;
import com.chess.json.jsonObject;
import com.chess.judge.chessBoardMethod;
import com.chess.judge.judge;

public class listenPortBNum80x {
	public static void LPBN8(Socket s,int coordinate,int roate,Integer key){
		Socket socket=s;
		int state = judge.chessJudge(key, coordinate, roate);
		jsonObject obj = new jsonObject();
		obj.setPkey(key);//设置棋盘编号
		obj.setState(state);
		obj.setCoordinate(coordinate);//设置棋子坐标
		obj.setRoate(roate);//设置旋转状态 
		String jsonString=JavaBeanToString.JBTS(obj);
		sendInformationToOp.sendInfo(socket,jsonString,key);
		//发送状态到落子棋盘
		jsonObject obj2 = new jsonObject();
		obj2.setState(state);
		obj.setCoordinate(0);//设置棋子坐标
		String jsonString2=JavaBeanToString.JBTS(obj2);
		sendInformation.sendInfo(jsonString2, socket);

		if(state==201||state==202||state==204){
			chessBoardMethod.initChessBoard(key);// 初始化棋盘
		}
		
	}
}
