package com.chess.judge;
/**
 * 十字判断胜负函数
 * @author zhh
 *
 */
public class judgeByLine {
	static int chessColor=0;
	public static int JBL(int[][] chessBoard){
		int numOfChess=0;//相连棋子个数标记
		chessColor=0;
		numOfChess=JBLA(chessBoard);

		if(numOfChess>=5)
		{
			if(chessColor==1)
				return 201;//黑子胜利
			else
				return 202;//白子胜利
		}
			return 203;//无胜负
	}
	public static int JBLA(int[][] chessBoard){
		int numOfChess=0;
		for (int l = 0; l < 6; l++) {
			for (int r = 0; r < 6; r++) {
				if(chessBoard[l][r]==0)
					continue;
				else
					chessColor=chessBoard[l][r];
		numOfChess = 0;
		//判断纵向是否五子连珠
		for(int i=l;i<=5;i++){
			if(chessBoard[i][r]==chessColor)
				numOfChess++;
			else
				break;
		}
		for(int i=l-1;i>=0;--i){
			if(chessBoard[i][r]==chessColor)
				numOfChess++;
			else
				break;
		}
		if(numOfChess>=5)
			return numOfChess;
		else
			numOfChess=0;
		//判断横向是否有五子连珠
		for(int i=r;i<=5;i++){
			if(chessBoard[l][i]==chessColor)
				numOfChess++;
			else
				break;
		}
		for(int i=r-1;i>=0;--i){
			if(chessBoard[l][i]==chessColor)
				numOfChess++;
			else
				break;
		}
		if(numOfChess>=5)
			return numOfChess;
		else
			numOfChess=0;
			}
		}
		return numOfChess;
	}
}
