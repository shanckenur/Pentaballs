package com.chess.roate;
/**
 * 临时数组3*3生成函数 
 * @author zhh
 *
 */
public class getTempArray {
	public static int[][] GTA(int[][] chessBoard,int roteValue){
		int[][] temp=new int[3][3];

		//第一块
		if(roteValue==1||roteValue==-1){
			int line=0,row=0;
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					temp[line][row]=chessBoard[i][j];
					row++;
				}
				row=0;
				line++;
			}
		}
		
		//第二块
		if(roteValue==2||roteValue==-2){
			int line=0,row=0;
			for(int i=0;i<3;i++){
				for(int j=3;j<6;j++){
					temp[line][row]=chessBoard[i][j];
					row++;
				}
				row=0;
				line++;
			}
		}
		
		//第三块
		if(roteValue==3||roteValue==-3){
			int line=0,row=0;
			for(int i=3;i<6;i++){
				for(int j=0;j<3;j++){
					temp[line][row]=chessBoard[i][j];
					row++;
				}
				row=0;
				line++;
			}
		}
		
		//第四块
		if(roteValue==4||roteValue==-4){
			int line=0,row=0;
			for(int i=3;i<6;i++){
				for(int j=3;j<6;j++){
					temp[line][row]=chessBoard[i][j];
					row++;
				}
				row=0;
				line++;
			}
		}
		roateOfTempArray.ROTA(temp, roteValue);
		//第一块
		if(roteValue==1||roteValue==-1){
			int line=0,row=0;
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					chessBoard[i][j]=temp[line][row];
					row++;
				}
				row=0;
				line++;
			}
		}
		
		//第二块
		if(roteValue==2||roteValue==-2){
			int line=0,row=0;
			for(int i=0;i<3;i++){
				for(int j=3;j<6;j++){
					chessBoard[i][j]=temp[line][row];
					row++;
				}
				row=0;
				line++;
			}
		}
		
		//第三块
		if(roteValue==3||roteValue==-3){
			int line=0,row=0;
			for(int i=3;i<6;i++){
				for(int j=0;j<3;j++){
					chessBoard[i][j]=temp[line][row];
					row++;
				}
				row=0;
				line++;
			}
		}
		
		//第四块
		if(roteValue==4||roteValue==-4){
			int line=0,row=0;
			for(int i=3;i<6;i++){
				for(int j=3;j<6;j++){
					chessBoard[i][j]=temp[line][row];
					row++;
				}
				row=0;
				line++;
			}
		}
		
		return temp;
	}
}
