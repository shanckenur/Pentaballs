package com.chess.server;

import java.net.Socket;

import com.chess.json.JavaBeanToString;
import com.chess.json.jsonObject;
import com.chess.playAndChessBoard.playAndChessBoard;

public class listenPortByNum70x {
	public static Integer LPBN7(Socket s,int st,int r,int psw){
		int state=st;
		int roomNum=r;
		int password=psw;
		Socket socket=s;
		Integer key=0;
		//701创建游戏房间
		if(state==701){
			//System.out.println(clocker.getTime()+"服务端消息:创建房间 ");
			key=playAndChessBoard.createRoom(socket,password);//获取创建的棋盘
			jsonObject obj = new jsonObject();
			obj.setPkey(key);//设置棋盘编号
			obj.setState(902);//创建房间者为黑子
			obj.setPsw(password);//设置的房间密码
			String jsonString=JavaBeanToString.JBTS(obj);
			sendInformation.sendInfo(jsonString, socket);
		}
		//702加入游戏房间
		if(state==702){
			//System.out.println(clocker.getTime()+"服务端消息:加入房间");
			key=playAndChessBoard.joinRoom(socket, roomNum,password);
			jsonObject obj = new jsonObject();

			if(key.intValue()==0){
				obj.setState(704);//加入房间失败
			//	System.out.println("加入房间失败");
			}
			jsonObject obj2 = new jsonObject();
			obj2.setState(401);
			if(key.intValue()<0){
				obj.setState(901);//白子
				sendInformationToOp.sendInfo(socket, JavaBeanToString.JBTS(obj2), key);
			}else if(key.intValue()>0){
				obj.setState(902);//黑子
				sendInformationToOp.sendInfo(socket, JavaBeanToString.JBTS(obj2), key);
			}
			String jsonString=JavaBeanToString.JBTS(obj);
			sendInformation.sendInfo(jsonString, socket);
		//	System.out.println(jsonString);
		}
		
		if(state==703){
	//		System.out.println(clocker.getTime()+"服务端消息 :随机搜索房间");
			key=playAndChessBoard.selectRoom(socket);
			jsonObject obj = new jsonObject();

			if(key.intValue()==0){
				obj.setState(704);//随机搜索房间失败 
			}
			
			jsonObject obj2 = new jsonObject();//建立给对手发送消息的对象
			obj2.setState(401);//有对手加入
			if(key.intValue()<0){
				obj.setState(901);//白子
				sendInformationToOp.sendInfo(socket, JavaBeanToString.JBTS(obj2), key);
			}else if(key.intValue()>0){
				obj.setState(902);//黑子
				sendInformationToOp.sendInfo(socket, JavaBeanToString.JBTS(obj2), key);
			}
			String jsonString=JavaBeanToString.JBTS(obj);
			sendInformation.sendInfo(jsonString, socket);
		//	System.out.println(jsonString);
		}
		return key;
	}
}
