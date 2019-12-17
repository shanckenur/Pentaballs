package com.chess.test;
/**
 * 打印棋盘 
 * @author zhh
 *
 */
public class pritfChessBoard {
	public static void PCB(int[][] chessBoard){
		for(int i =0;i<6;i++){
			for(int j=0;j<6;j++){
				if(chessBoard[i][j]>=0)
					System.out.print(" "+chessBoard[i][j]+" ");
				else
					System.out.print(chessBoard[i][j]+" ");	
			}
			System.out.println();
		}
		System.out.println();
	}
}
