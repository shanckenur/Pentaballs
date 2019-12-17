package com.chess.playAndChessBoard;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.chess.judge.chessBoardMethod;
/**
 * 棋盘分配函数
 * @author zhh
 *
 */
public class playAndChessBoard {
	static List<playerJavaBean> list=new ArrayList<>();//客户端-棋盘映射对象数组
	static Integer key=null;
	static playerJavaBean pjb=null;
	
	public static synchronized Integer selectRoom(Socket socket){
		Integer key=0;
		for(int i=0;i<list.size();i++){
			pjb=list.get(i);
			if(pjb.getLength()==1&&pjb.getPsw()==0){//是否有空闲房间 
				if(pjb.flag==1){//获取正key
					key=pjb.getKey();
				}else{
					key=-pjb.getKey();//获取负key
				}
				pjb.pushSocket(socket);//将套接字加入客户端-棋盘映射对象
				break;
			}
		}
		return key;
	}
	
	public static synchronized Integer joinRoom(Socket socket,int roomNum,int psw){
		Integer key=0;
		for(int i=0;i<list.size();i++){
			pjb=list.get(i);
			if(pjb.getKey()==roomNum&&pjb.getPsw()==psw){
				if(pjb.getLength()==1){//是否有人退出
					if(pjb.flag==1){//获取正key
						key=pjb.getKey();
					}else{
						key=-pjb.getKey();//获取负key
					}
					pjb.pushSocket(socket);//将套接字加入客户端-棋盘映射对象
					break;
				}
			}
		}
		return key;
	}
	public static synchronized Integer createRoom(Socket socket,int psw){
		Integer key=null;
		for(int i=0;i<list.size();i++){
			pjb=list.get(i);
			if(pjb.getLength()==0){//是否有空闲房间 
				if(pjb.flag==1){//获取正key
					key=pjb.getKey();
				}else{
					key=-pjb.getKey();//获取负key
				}
//				pjb.setPsw(psw);
//				pjb.pushSocket(socket);//将套接字加入客户端-棋盘映射对象
				break;
			}
		}
		if(key==null){
			key= chessBoardMethod.getNewChessBoard();//获取棋盘
		}
		playerJavaBean player = new playerJavaBean();
		player.SetKey(key);
		player.pushSocket(socket);
		player.setPsw(psw);
		for(int i=0;i<list.size();i++){
			if(list.get(i).getKey().intValue()==key.intValue()){
				list.remove(i);
			}
		}
		list.add(player);//将客户端-棋盘映射对象加入到数组
		return key;
	}
	public static synchronized Integer pS(Socket socket){
		key=null;
		pjb=null;
		for(int i=0;i<list.size();i++){
			pjb=list.get(i);
			if(pjb.getLength()<2){//是否有人退出
				if(pjb.flag==1){//获取正key
					key=pjb.getKey();
				}else{
					key=-pjb.getKey();//获取负key
				}
				pjb.pushSocket(socket);//将套接字加入客户端-棋盘映射对象
				break;
			}
		}
		if(key==null){//若无人退出则获得新的棋盘
			key= chessBoardMethod.getNewChessBoard();//获取棋盘
			playerJavaBean player = new playerJavaBean();
			player.SetKey(key);
			player.pushSocket(socket);
			list.add(player);//将客户端-棋盘映射对象加入到数组
		}
		return key;
	}
	//获取对手套接字
	public static Socket getOpSocket(Socket s,Integer key){
		key=key>0?key:-key;
		for(int i=0;i<list.size();i++){
			playerJavaBean PJB=list.get(i);
			if(PJB.getKey().intValue()==key.intValue()){
				return PJB.getSocket(s);
			}//if
		}//for
		return null;
	}
	
	//获取双方套接字 
		public static List<Socket> getOpSocketTwo(Integer key){
			key=key>0?key:-key;
			for(int i=0;i<list.size();i++){
				playerJavaBean PJB=list.get(i);
				if(PJB.getKey().intValue()==key.intValue()){
					return PJB.getSocketTwo();
				}//if
			}//for
			return null;
		}
	//删除客户端-棋盘映射对象中的客户端
	public static  synchronized void delSocket(Integer key,Socket socket){
		Integer pkey=null;
		int flag=0;
		if(key<0){//删除负key
			pkey=-key;
			flag=-1;
		}else//删除正 key
		{
			pkey=key;
			flag=1;
		}
		playerJavaBean playerJavaBean=null;
		//获取key值相对应的客户端
		for(int i=0;i<list.size();i++){
			playerJavaBean=list.get(i);
			if(playerJavaBean.getKey().intValue()==pkey.intValue()){
				//System.out.println("removeSocket");
				playerJavaBean.removeSocket(flag,socket);
				break;
			}
		}//for
	}//delSocket
}
