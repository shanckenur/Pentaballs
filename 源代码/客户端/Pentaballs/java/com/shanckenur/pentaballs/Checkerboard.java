package com.shanckenur.pentaballs;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.shanckenur.pentaballs.rotate.getTempArray;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mz on 2017/5/10.
 */

public class Checkerboard {

    private static int line=6;
    private static int row=6;
    public static int[][]board;
    private static Map<Integer,int[][]> map = new HashMap<Integer,int[][]>();

    private static Context context;

    //AI等级选择
    public static int AiLevel=0;

    //是否結束
    public static boolean isFinish=false;

    public Checkerboard(Context con)
    {
        context=con;

        board=new int [6][6];

        for(int i=0;i<line;i++)
            for(int j=0;j<row;j++)
                board[i][j]=0;

        MainActivity.ShowResult(0);

        MainActivity.Start_Game();

        MainActivity.hasSetChess=false;

        AiLevel=0;
        isFinish=false;
    }

    //获取整个棋盘
    public static int[][] getWholeBoard() {
        return board;
    }

    //落子
    public static void DoaChess(int line,int row,int kindOfChess){
        DoaChess(line,row,kindOfChess,board);
    }

    //落子
    public static void DoaChess(int line,int row,int kindOfChess,int[][] board){
        //Toast.makeText(context,board[line][row]+"",Toast.LENGTH_SHORT).show();
        board[line-1][row-1]=kindOfChess;
        //Toast.makeText(context,board[line][row]+"",Toast.LENGTH_SHORT).show();
        /*if(kindOfChess==1)
            MainActivity.GoBlack(line,row);
        else if(kindOfChess==-1)
            MainActivity.GoWhite(line,row);*/
    }
/*
    public void setChess(int line,int row,int kindOfChess)
    {
        board[line-1][row-1]=kindOfChess;
    }
    */

    public static int getItem(int i,int j)
    {
        return board[i-1][j-1];
    }

    //得到部分棋盘
    public static int [][] getBoard(int block)
    {//Log.i("my",board.length+" -getBoard");
        int[][] temp=new int[3][3];

        if(block==1){
            int line=0,row=0;
            for(int i=0;i<3;i++){
                for(int j=0;j<3;j++){
                    temp[line][row]=board[i][j];
                    row++;
                }
                row=0;
                line++;
            }
        }

        //第二块
        if(block==2){
            int line=0,row=0;
            for(int i=0;i<3;i++){//Log.i("my","111");
                for(int j=3;j<6;j++){//Log.i("my","222");
                    temp[line][row]=board[i][j];
                    row++;
                }
                row=0;
                line++;
            }//Log.i("my","301");
        }

        //第三块
        if(block==3){
            int line=0,row=0;
            for(int i=3;i<6;i++){
                for(int j=0;j<3;j++){
                    temp[line][row]=board[i][j];
                    row++;
                }
                row=0;
                line++;
            }
        }

        //第四块
        if(block==4){
            int line=0,row=0;
            for(int i=3;i<6;i++){
                for(int j=3;j<6;j++){
                    temp[line][row]=board[i][j];
                    row++;
                }
                row=0;
                line++;
            }
        }
        return temp;
    }

    //旋转棋盘数组
    public void rotate_board(int rotate_state)
    {
        board= getTempArray.GTA(board,rotate_state);
    }

    public void rotate_board(int [][]board,int rotate_state)
    {
        board= getTempArray.GTA(board,rotate_state);
    }

}
