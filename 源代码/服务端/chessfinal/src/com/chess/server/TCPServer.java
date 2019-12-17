package com.chess.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.chess.json.JavaBeanToString;
import com.chess.json.StringToJavaBean;
import com.chess.json.jsonObject;
import com.chess.judge.chessBoardMethod;
import com.chess.playAndChessBoard.createPassword;
import com.chess.playAndChessBoard.playerUnlogin;
import com.clock.Timers;
import com.clock.clocker;
/**
 * 服务端
 * 
 * @author zhh
 * 
 */

public class TCPServer {
	private static final int PORT = 4711;// 端口号
	private List<Socket> mList = new ArrayList<Socket>();// 套接字数组
	private ServerSocket server = null;// 服务端套接字
	private ExecutorService myExecutorService = null;// 线程池
	public static void main(String[] args) {
		new TCPServer();// 创建主线程
	}

	public TCPServer() {
		try {
			// 创建服务端套接字
			server = new ServerSocket(PORT);
			// 创建线程池
			myExecutorService = Executors.newCachedThreadPool();
			System.out.println(
					clocker.getTime()+"服务端消息:服务端启动成功,IP:" + server.getInetAddress().getHostAddress() + ":" + server.getLocalPort());
			Socket client = null;
			while (true) {
				// 获取客户端 连接
				client = server.accept();
				System.out.println(clocker.getTime()+"服务端消息:" + client.getInetAddress() + ":" + client.getPort() + "已连接.");
				// 将客户端连接添加到套接字数组
				mList.add(client);
				// 给客户端连接建立相应的线程
				myExecutorService.execute(new Service(client));
			}

		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println(clocker.getTime()+"服务端消息:服务器错误!");
		} // try
	}// TCPServer
		// 内部类



	class Service implements Runnable {
		private Socket socket;
		private BufferedReader in = null;
		private PrintWriter out = null;
		private String jsonString = "";
		private Integer key = null;
		private Timers timers=null;
		

		public Service(Socket socket) {
			this.socket = socket;
			// 获取输入流
			try {

				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")),
						true);
			} catch (IOException e) {
				//e.printStackTrace();
				System.out.println(clocker.getTime()+"服务器消息:获取输入输出流失败");
			}
			// 获取输出流
			initInformation.init(this.socket);// 初始化信息
			timers = new Timers(this.socket);
			new Thread(timers).start();
		}// service

		@Override
		public void run() {
			try {
				while (true) {
					if ((jsonString = in.readLine()) != null) {
						//timers.restartTimer(this.socket);
					//	System.out.println("====接收到的客户端信息====");
						// 将接受的json字符串转换成JavaBean对象
						jsonObject obj = new jsonObject();
						obj = StringToJavaBean.STJB(jsonString);
						int state = obj.getState();
						int setPsw=obj.getSetPsw();//为1创建房间密码，为0默认密码,为-1加入房间 
						int password=obj.getPsw();
						//心跳检测
						if(state==9){
							if(timers!=null){
								timers.restartTimer(this.socket);
								//System.out.println(clocker.getTime()+"服务端消息:心跳检测");
							}
						}
						//403
						if(state==403){
							if(key!=null&&key!=0){
								playerUnlogin.unlogin(key, this.socket);
								//key=null;
							}
						}
						//601
						if(state==601){
							listenPortByNum60x.LPBN6(socket, jsonString, key);
						}
						//701 702 703
						if (state == 701 || state == 702 || state == 703) {
							if(key!=null&&key!=0){
								playerUnlogin.unlogin(key, socket);
							}
							int psw=0;
							if(setPsw==1){
								psw=createPassword.CP(1);
							}else if(setPsw==0){
								psw=0;
							}else if(setPsw==-1){
								psw=password;
							}
							key = listenPortByNum70x.LPBN7(socket, state, obj.getRoomNumber(),psw);
							if(key!=null&&key!=0){
								if(timers!=null){
									timers.setSocketAndKey(this.socket, key);
								}
							}
						}
						
						//重新开始
						if(state==705){
							if(key!=null&&key!=0){
								chessBoardMethod.initChessBoard(key);
								jsonObject objresatrt = new jsonObject();
								objresatrt.setState(705);
								String jsonStringRestart=JavaBeanToString.JBTS(objresatrt);
								sendInformationToOp.sendInfo(this.socket, jsonStringRestart, key);
							}
						}
						//801 802
						if (state == 801 || state == 802) {
							if (key != null && key != 0) {
								int coordinate = obj.getCoordinate();
								int roate = obj.getRoate();
								listenPortBNum80x.LPBN8(socket, coordinate, roate, key);
							}
						}
					}
				} // while
			} catch (Exception e) {
				// e.printStackTrace();
				//System.out.println("====客户端退出信息===");
				// 打印退出信息
				System.out.println(clocker.getTime()+"服务端消息:" + this.socket.getInetAddress() + "棋盘标记:" + key + "已经退出游戏");
				mList.remove(socket);// 移除退出客户端的套接字
				if (key != null && key != 0) {
					playerUnlogin.unlogin(key, socket);
				}
				Thread.interrupted();// 中断当前线程
			} // try
		}// run
	}
}
