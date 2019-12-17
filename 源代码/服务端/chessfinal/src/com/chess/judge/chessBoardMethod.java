package com.chess.judge;

import java.util.HashMap;
import java.util.Map;

/**
 * 棋盘管理函数
 * @author zhh
 *
 */
public class chessBoardMethod {
	static int line=6;//棋盘行数
	static int row=6;//棋盘列数
	static Map<Integer,int[][]> map = new HashMap<Integer,int[][]>();//key-棋盘映射集合
	static int pkey=10001;//初始棋盘标记
	
	public static void changeChess(int line,int row,int kindOfChess,int[][] board){
		board[line][row]=kindOfChess;//落子
		//System.out.println("改变棋子:"+board[line][row]);
	}
	//新棋盘获取函数
	public static synchronized Integer getNewChessBoard(){
			int[][] board1 =new int[line][row];
			//初始化棋盘
			for(int i=0;i<line;i++){
				for(int j=0;j<row;j++){
					board1[i][j]=0;
				}
			}
			//将生成的棋盘添加到map中
			map.put(pkey,board1);
			return pkey++;
	}
	//获取已存在棋盘
	public static int[][] getChessBoard(Integer key){
		return map.get(key);
	}
	
	public static void initChessBoard(Integer initKey){//初始化棋盘 
		if(initKey<0)
			initKey=-initKey;
		int[][] initChessBoard=map.get(initKey);
		for(int i=0;i<line;i++){
			for(int j =0;j<row;j++){
				initChessBoard[i][j]=0;
			}
		}
	}
}
