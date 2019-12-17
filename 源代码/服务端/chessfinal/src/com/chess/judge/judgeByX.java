package com.chess.judge;
/**
 * X判断胜负函数
 * @author zhh
 *
 */
public class judgeByX {
	static int chessColor=0;
	public static int JBX(int[][] chessBoard) {
		int numOfChess = 0;
		chessColor=0;
		numOfChess = JBXA(chessBoard);

		if (numOfChess >= 5) {
			if (chessColor== 1)
				return 201;// 黑子胜利
			else
				return 202;// 白子胜利
		}
		return 203;// 无胜负
	}

	public static int JBXA( int[][] chessBoard) {
		int numOfChess=0;
		for (int l = 0; l < 6; l++) {
			for (int r = 0; r < 6; r++) {
				if(chessBoard[l][r]==0)
					continue;
				else
					chessColor=chessBoard[l][r];
				numOfChess = 0;
				// 判断左上到右下对角线是否有五子连珠
				for (int i = l, j = r; i <= 5 && j <= 5; i++, j++) {
					if (chessBoard[i][j] == chessColor)
						numOfChess++;
				}
				
				for (int i = l - 1, j = r - 1; i >= 0 && j >= 0; --i, --j) {
					if (chessBoard[i][j] == chessColor)
						numOfChess++;
				}
				
				if (numOfChess >= 5)
					return numOfChess;
				else
					numOfChess = 0;
				// 判断右上到左下对对角线是否有五子连珠
				for (int i = l, j = r; j<= 5 && i  >= 0; i--, j++) {
					if (chessBoard[i][j] == chessColor)
						numOfChess++;
				}
				
				for (int i = l +1, j = r - 1; i <=5 && j>=0; ++i, --j) {
					if (chessBoard[i][j] == chessColor)
						numOfChess++;
				}
				
				if (numOfChess >= 5)
					return numOfChess;
				else
					numOfChess = 0;
			}
		}
		return numOfChess;
	}
}