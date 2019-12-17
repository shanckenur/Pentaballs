package com.clock;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.chess.json.JavaBeanToString;
import com.chess.json.jsonObject;
import com.chess.playAndChessBoard.playerUnlogin;
import com.chess.server.sendInformation;


public class Timers implements Runnable{
	Socket socket=null;
	public static Map<Socket,Long> map=new HashMap<>();
	public static Map<Socket,Integer> keySocket= new HashMap<>();
	public static long t=12000;
	public Timers(Socket s) {
		socket=s;
		long time = System.currentTimeMillis();
		map.put(socket, time);
		System.out.println(clocker.getTime()+"服务端消息:当前在线人数 "+map.size());
	}
	public void restartTimer(Socket s){
		map.put(s, System.currentTimeMillis());
	}
	public void setTimer(Socket s){
		map.put(s, System.currentTimeMillis()-t-1000);
	}
	public void setSocketAndKey(Socket s,Integer k){
		keySocket.put(s,k);
	}
	public void run() {
		Socket selfSocket=socket;
		long nowTime =0;
		jsonObject timeOut = new jsonObject();
		Integer key=null;
		long tempTime=0;
		while (true) {
			nowTime= System.currentTimeMillis();
			tempTime=nowTime - map.get(selfSocket);
			//(nowTime - map.get(selfSocket))==10000||(nowTime - map.get(selfSocket))==8000||(nowTime - map.get(selfSocket))==4000||(nowTime - map.get(selfSocket))==5000||(nowTime - map.get(selfSocket))==6000||(nowTime - map.get(selfSocket))==7000
			if(tempTime>3990&&tempTime!=t&&tempTime%1000==0||tempTime>t-1000&&tempTime<t-990){

				timeOut.setState(9);
				sendInformation.sendInfo(JavaBeanToString.JBTS(timeOut),socket);// 发送退出消息
				//System.out.println("H");
//				try {
//					Thread.sleep(1);
//				} catch (InterruptedException e) {
//					System.out.println(clocker.getTime()+"服务端消息:休眠失败");
//					//
//				}
			}
	
			if ((nowTime - map.get(selfSocket))>= t) {
				System.out.println("超时");
				try {
				//	jsonObject timeOut = new jsonObject();
					timeOut.setState(500);
					sendInformation.sendInfo(JavaBeanToString.JBTS(timeOut),socket);// 发送超时消息
					map.remove(selfSocket);
					System.out.println(clocker.getTime()+"服务端消息:当前在线人数 "+map.size());
					key=keySocket.get(selfSocket);
				//	System.out.println("selfSocket"+key);
					if(key!=null){
						playerUnlogin.unlogin(key, selfSocket);
					}
					keySocket.remove(selfSocket);
					selfSocket.close();
					System.out.println("关闭套接字成功");
				//	Thread.interrupted();// 中断当前线程
					break;
				} catch (IOException e) {
					System.out.println(clocker.getTime()+"服务端消息:客户端关闭失败");
				}
			}
		
		}
		
	}
}