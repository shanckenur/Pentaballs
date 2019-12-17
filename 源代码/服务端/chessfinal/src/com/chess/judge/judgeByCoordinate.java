package com.chess.judge;
/**
 * 
 * @author zhh
 *
 */
public class judgeByCoordinate {
	//棋盘大小
	static int lineNum=6;
	static int rowNum=6;

	public static int jBC(int line,int row,int[][] chessBoard){
		//调用十字判断函数，获取对局结果信息
		int jbl=judgeByLine.JBL(chessBoard);
		//调用x字判断函数，获取对局结果信息
		int jbx=judgeByX.JBX(chessBoard);
		//若有一方获胜则返回相应的信息201或者202
		if(jbl==201||jbx==201){
			return 201;
		}else if(jbl==202||jbx==202){
			return 202;
		}
		//若无人获胜则返回203
		return 203;
	}
}
