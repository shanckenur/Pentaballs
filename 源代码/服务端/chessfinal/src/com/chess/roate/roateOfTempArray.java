package com.chess.roate;
/**
 * 将临时数组写会棋盘 
 * @author zhh
 *
 */
public class roateOfTempArray {
	public static void ROTA(int[][] temp,int roateValue){
		int a[][]=new int[3][3];
		//正数右旋转
		if(roateValue>0){
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					a[i][j]=temp[j][i];
				}
			}
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					temp[i][j]=a[i][2-j];
				}
			}
		}else{//负数左旋转
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					a[i][j]=temp[j][i];
				}
			}
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++){
					temp[i][j]=a[2-i][j];
				}
			}
		}
	}
}
