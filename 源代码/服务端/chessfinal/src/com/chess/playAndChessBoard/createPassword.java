package com.chess.playAndChessBoard;


public class createPassword {
	public static int CP(int mark) {
			int test=0;
			if(mark==1){
				test=(int) (Math.random() * 1000);
				if(test/100==0){
					test=0;
				}
				if(test==0){
					test+=123+(int)(Math.random()*10);
				}
			}else{
				test=0;
			}
			
			return test;
	}

}
