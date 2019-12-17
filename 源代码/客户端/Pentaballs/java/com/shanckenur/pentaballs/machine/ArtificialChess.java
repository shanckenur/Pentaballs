package com.shanckenur.pentaballs.machine;

import android.util.Log;

import com.shanckenur.pentaballs.Checkerboard;
import com.shanckenur.pentaballs.MainActivity;

/**
 * Created by mz on 2017/5/20.
 */

public class ArtificialChess {

    private static int[][] board;

    private final static int CHESS_BLACK=1;
    private final static int CHESS_WHITE=-1;
    private final static int CHESS_NONE=0;

    //赢法统计数组
    private static int[] myWin = new int[32];
    private static int[] computerWin = new int[32];
    private final static int count=32;

    //保存最高得分的i，j值
    private static int u=0;
    private static int v=0;

    //AI等级
    public final int Level_hard=1;
    public final int Level_middle=2;
    public final int Level_simple=3;

    //ChessType为人执棋子颜色
    public static int[] AddPower(int[][] checkerboard,int ChessType)
    {
        board=new int[6][6];

        for(int i=0;i<6;i++)
            for (int j=0;j<6;j++)
                board[i][j]=checkerboard[i][j];

        //赢法数组初始化
        Wininit();
        Init(ChessType);

        int resute[]=computerAI();
        int resute_type[]=new int[4];
        resute_type[0]=ChessType;
        resute_type[1]=resute[0];
        resute_type[2]=resute[1];
        resute_type[3]=resute[2];

        return resute_type;
    }

    //统计赢法数组
    private static void Init(int ChessType) {

        for(int m=0;m<6;m++) {
            for (int n = 0; n < 6; n++) {
                if (board[m][n] != CHESS_NONE) {
                    for (int k = 0; k < count; k++) {
                        if (roleArray.wins[m][n][k] == 1) {
                            if (board[m][n] == ChessType) {
                                myWin[k]++;
                                computerWin[k] = 6;
                            } else if (board[m][n] + ChessType == 0) {
                                computerWin[k]++;
                                myWin[k] = 6;
                            }
                        }
                    }
                }
            }
        }
    }

    //赢法数组初始化
    public static void Wininit(){

        for(int i = 0 ;i<32;i++){
            myWin[i] = 0;
            computerWin[i] = 0;
        }
    }

    //AI计算当前棋盘最佳位置
    private static int[] computerAI() {
        // TODO Auto-generated method stub

        //保存最高得分
        int max = 0;

        int[][] myScore = new int[10][10];
        int[][] computerScore = new int[10][10];
        //初始化分数值
        for(int i=0;i<6;i++){
            for(int j=0;j<6;j++){
                myScore[i][j] = 0;
                computerScore[i][j]=0;
            }
        }

        for(int i=0;i<6;i++){
            for(int j=0;j<6;j++){
                if(board[i][j] == CHESS_NONE){
                    for(int k=0;k<count;k++){
                        if(roleArray.wins[i][j][k]==1){

                            if(Checkerboard.AiLevel== MainActivity.Level_simple) {

                                //我方得分，计算机拦截
                                if(myWin[k]==1){
                                    myScore[i][j]+=200;
                                }else if(myWin[k]==2){
                                    myScore[i][j]+=400;
                                }else if(myWin[k] == 3){
                                    myScore[i][j]+=2000;
                                }else if(myWin[k] == 4){
                                    myScore[i][j] += 10000;
                                }

                                //计算机走法 得分
                                if(computerWin[k]==1){
                                    computerScore[i][j]+=220;
                                }else if(computerWin[k]==2){
                                    computerScore[i][j]+=420;
                                }else if(computerWin[k] == 3){
                                    computerScore[i][j]+=2100;
                                }else if(computerWin[k] == 4){
                                    computerScore[i][j] += 20000;
                                }

                            } else if(Checkerboard.AiLevel==MainActivity.Level_middle) {

                                //我方得分，计算机拦截
                                if(myWin[k]==1){
                                    myScore[i][j]+=200;
                                }else if(myWin[k]==2){
                                    myScore[i][j]+=400;
                                }else if(myWin[k] == 3){
                                    myScore[i][j]+=2000;
                                }else if(myWin[k] == 4){
                                    myScore[i][j] += 10000;
                                }

                                //计算机走法 得分
                                if(computerWin[k]==1){
                                    computerScore[i][j]+=420;
                                }else if(computerWin[k]==2){
                                    computerScore[i][j]+=500;
                                }else if(computerWin[k] == 3){
                                    computerScore[i][j]+=2100;
                                }else if(computerWin[k] == 4){
                                    computerScore[i][j] += 20000;
                                }

                            } else if(Checkerboard.AiLevel==MainActivity.Level_hard) {

                                //我方得分，计算机拦截
                                if(myWin[k]==1){
                                    myScore[i][j]+=200;
                                }else if(myWin[k]==2){
                                    myScore[i][j]+=400;
                                }else if(myWin[k] == 3){
                                    myScore[i][j]+=2000;
                                }else if(myWin[k] == 4){
                                    myScore[i][j] += 10000;
                                }

                                //计算机走法 得分
                                if(computerWin[k]==1){
                                    computerScore[i][j]+=420;
                                }else if(computerWin[k]==2){
                                    computerScore[i][j]+=500;
                                }else if(computerWin[k] == 3){
                                    computerScore[i][j]+=2100;
                                }else if(computerWin[k] == 4){
                                    computerScore[i][j] += 20000;
                                }

                            }
                        }
                    }
                    //System.out.println(i+" "+j+" "+max);
                    //判断我方最高得分，将最高分数的点获取出来, u，v为计算机要落下的子的坐标
                    if(myScore[i][j]>max){
                        max = myScore[i][j];
                        u = i;
                        v = j;
                    }else if(myScore[i][j] == max ){
                        if(computerScore[i][j]>computerScore[u][v]){
                            //认为i，j点比u，v点好
                            u = i;
                            v = j;
                        }/* else if(Checkerboard.AiLevel==MainActivity.Level_middle&&) {

                        }*/
                    }

                    //判断电脑方最高得分，将最高分数的点获取出来
                    if(computerScore[i][j]>max){
                        max = computerScore[i][j];
                        u = i;
                        v = j;
                    }else if(computerScore[i][j] == max ){
                        if(myScore[i][j]>myScore[u][v]){
                            //认为i，j点比u，v点好
                            u = i;
                            v = j;
                        }
                    }

                }
            }
        }
        //chessBoard[u][v] = 2;

        //for(int i=0;i<count;i++)
        //    System.out.println(myWin[i]);

        int a[]={max,u,v};
        //Log.i("my",max+"+"+u+"+"+v);
        Log.i("temp","u"+u);
        return a;

    }

}
