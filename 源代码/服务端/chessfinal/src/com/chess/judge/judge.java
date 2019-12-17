package com.chess.judge;

import com.chess.roate.roateMethod;
import com.chess.test.pritfChessBoard;
import com.clock.clocker;

/**
 * 判断函数
 * @author zhh
 *
 */
public class judge {

	public static int chessJudge(Integer key,int coordinate,int roate){
		 int lineNum=6;
		 int rowNum=6;
		 int chessNum=0;
		 int[] co=null;
		 if(key<0)
			 key=-key;
		 int[][] chessBoard = chessBoardMethod.getChessBoard(key);
		 //coordinate转换成坐标并改变值
		 co=numTurnCoordinate.NTC(coordinate);
		 //在棋盘中记录棋子
		 chessBoardMethod.changeChess(co[0],co[1],co[2],chessBoard);
		 //判断棋子个数，若棋子个数小于等于8则不可能有人获胜
		for(int line=0;line<lineNum;line++){
			for(int row=0;row<rowNum;row++){
				if(chessBoard[line][row]==1||chessBoard[line][row]==-1){
					chessNum++;
				}
			}
		}
		//旋转棋盘

		roateMethod.RM(key,roate);
	//	System.out.println(clocker.getTime()+"服务端消息:打印棋盘");
	//	pritfChessBoard.PCB(chessBoard);
		if(chessNum>8){
			//调用判断函数,判断是否有人获胜
			return judgeByCoordinate.jBC(co[0], co[1],chessBoard);
		}
		return 203;//无获胜方
	}
}
