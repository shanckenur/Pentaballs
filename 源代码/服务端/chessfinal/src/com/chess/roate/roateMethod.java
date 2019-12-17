package com.chess.roate;

import com.chess.judge.chessBoardMethod;

/**
 * 棋盘旋转类
 * 
 * @author zhh
 *
 */
public class roateMethod {
	public static void RM(Integer key, int roteValue) {
		int[][] chessBoard = chessBoardMethod.getChessBoard(key);
		getTempArray.GTA(chessBoard, roteValue);
	}
}
