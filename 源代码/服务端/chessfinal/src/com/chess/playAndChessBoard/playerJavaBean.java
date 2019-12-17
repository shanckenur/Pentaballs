package com.chess.playAndChessBoard;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
/**
 * 客户端-棋盘映射对象
 * @author zhh
 *
 */
public class playerJavaBean {
	List<Socket> list =new ArrayList<>();//套接字数组
	Integer key =null;//棋盘标记
	int flag=0;//正负key值标记
	int password=0;
	public void pushSocket(Socket socket){
		if(list.size()==0)//第一个加入则缺少负key
			flag=-1;
		list.add(socket);

	}
	public void setPsw(int psw){
		password=psw;
	}
	public int getPsw(){
		return password;
	}
	//获取对手的套接字对象
	public Socket getSocket(Socket socket){
		Socket temp=null;
		for(int i=0;i<list.size();i++){
			if(list.get(i)!=socket){
				temp=list.get(i);
				break;
			}//if
		}
		return temp;
	}
	//获取双方套接字
	public List<Socket> getSocketTwo(){
		return list;
	}
	
	public Integer getKey(){
		return key;
	}
	public void SetKey(Integer key){
		this.key=key;
	}
	
	
	public void removeSocket(int f,Socket socket){
		list.remove(socket);
	//	System.out.println("套接字数组长度是:"+list.size());
		if(f<0)
			flag=-1;//删除负key缺少负key
		if(f>0)//删除正key缺少正key
			flag=1;
		if(list.size()==0)
			flag=1;
	}
	public int getLength(){
		return list.size();//获取套接字数组长度
	}
}
