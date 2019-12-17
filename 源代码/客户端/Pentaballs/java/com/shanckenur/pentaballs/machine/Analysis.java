package com.shanckenur.pentaballs.machine;

import android.util.Log;

import com.shanckenur.pentaballs.Checkerboard;
import com.shanckenur.pentaballs.MainActivity;
import com.shanckenur.pentaballs.rotate.getTempArray;

/**
 * Created by mz on 2017/5/20.
 */

public class Analysis {

    private static int[][] board;
    private static int[][] board_upandleft;
    private static int[][] board_upandright;
    private static int[][] board_leftandup;
    private static int[][] board_leftanddown;
    private static int[][] board_rightandup;
    private static int[][] board_rightanddown;
    private static int[][] board_downandleft;
    private static int[][] board_downandright;

    private final static int rotate_upandleft=-1;
    private final static int rotate_upandright=2;
    private final static int rotate_leftandup=1;
    private final static int rotate_leftanddown=-3;
    private final static int rotate_rightandup=-2;
    private final static int rotate_rightanddown=4;
    private final static int rotate_downandleft=3;
    private final static int rotate_downandright=-4;

    private static int[] result;
    private static int[] result_upandleft;
    private static int[] result_upandright;
    private static int[] result_leftandup;
    private static int[] result_leftanddown;
    private static int[] result_rightandup;
    private static int[] result_rightanddown;
    private static int[] result_downandleft;
    private static int[] result_downandright;

    private final static int CHESS_BLACK=1;
    private final static int CHESS_WHITE=-1;
    private final static int CHESS_NONE=0;

    public static int[] Analyze(int[][] originalBoard, int ChessType)
    {
        //System.out.println("1  "+originalBoard[4][1]);

        board=new int[6][6];

        for(int i=0;i<6;i++)
            for(int j=0;j<6;j++)
                board[i][j]=originalBoard[i][j];

        //System.out.println("2  "+board[4][1]);

        result=new int[4];
        for(int i=0;i<4;i++)
            result[i]=0;
        showLog(board);
        //得到8种棋盘变化
        board_upandleft= getTempArray.GTA(board,rotate_upandleft);//showLog(board_upandleft);
        board_upandright= getTempArray.GTA(board,rotate_upandright);//showLog(board_upandright);
        board_leftandup= getTempArray.GTA(board,rotate_leftandup);//showLog(board_leftandup);
        board_leftanddown= getTempArray.GTA(board,rotate_leftanddown);//showLog(board_leftanddown);
        board_rightandup= getTempArray.GTA(board,rotate_rightandup);//showLog(board_rightandup);
        board_rightanddown= getTempArray.GTA(board,rotate_rightanddown);//showLog(board_rightanddown);
        board_downandleft= getTempArray.GTA(board,rotate_downandleft);//showLog(board_downandleft);
        board_downandright= getTempArray.GTA(board,rotate_downandright);//showLog(board_downandright);

        //返回某个旋转后的{ChessType,max,u,v}
        result_upandleft=ArtificialChess.AddPower(board_upandleft,ChessType);
        //取各个旋转后的最大权值，将{ChessType,max,u,v}赋值{max,u,v,旋转方式}
        if(result_upandleft[1]>result[0]) {
            for (int i = 1; i < 4; i++)
                result[i - 1] = result_upandleft[i];
            result[3]=rotate_upandleft;
        }

        result_upandright=ArtificialChess.AddPower(board_upandright,ChessType);

        if(result_upandright[1]>result[0]) {
            for (int i = 1; i < 4; i++)
                result[i - 1] = result_upandright[i];
            result[3]=rotate_upandright;
        }

        result_leftandup=ArtificialChess.AddPower(board_leftandup,ChessType);

        if(result_leftandup[1]>result[0]) {
            for (int i = 1; i < 4; i++)
                result[i - 1] = result_leftandup[i];
            result[3]=rotate_leftandup;
        }

        result_leftanddown=ArtificialChess.AddPower(board_leftanddown,ChessType);

        if(result_leftanddown[1]>result[0]) {
            for (int i = 1; i < 4; i++)
                result[i - 1] = result_leftanddown[i];
            result[3]=rotate_leftanddown;
        }

        result_rightandup=ArtificialChess.AddPower(board_rightandup,ChessType);

        if(result_rightandup[1]>result[0]) {
            for (int i = 1; i < 4; i++)
                result[i - 1] = result_rightandup[i];
            result[3]=rotate_rightandup;
        }

        result_rightanddown=ArtificialChess.AddPower(board_rightanddown,ChessType);

        if(result_rightanddown[1]>result[0]) {
            for (int i = 1; i < 4; i++)
                result[i - 1] = result_rightanddown[i];
            result[3]=rotate_rightanddown;
        }

        result_downandleft=ArtificialChess.AddPower(board_downandleft,ChessType);

        if(result_downandleft[1]>result[0]) {
            for (int i = 1; i < 4; i++)
                result[i - 1] = result_downandleft[i];
            result[3]=rotate_downandleft;
        }

        result_downandright=ArtificialChess.AddPower(board_downandright,ChessType);

        if(result_downandright[1]>result[0]) {
            for (int i = 1; i < 4; i++)
                result[i - 1] = result_downandright[i];
            result[3]=rotate_downandright;
        }

        //如果目标棋子在旋转棋盘上，进行棋子旋转修正
        if(BestChessinRotate(result[1]+1,result[2]+1,result[3])) {
            int correct[] = Change_before_Rotate(result[1] + 1, result[2] + 1, result[3]);
            result[1] = correct[0] - 1;
            result[2] = correct[1] - 1;
        }
        Log.i("hard_mode",result[3]+"result"+result[0]);
        //返回{MAX,Line,Row,Rotate}
        return result;
    }

    //判断最佳落子是否在旋转棋盘上
    private static boolean BestChessinRotate(int line, int row, int rotate) {
        //Log.i("test","best-"+line+"-"+row+"-"+rotate);

        if(line<=3&&row<=3) {
            if (rotate == rotate_upandleft || rotate == rotate_leftandup)
                return true;
        } else if(line<=3&&row>3) {
            if (rotate == rotate_upandright || rotate == rotate_rightandup)
                return true;
        } else if(line>3&&row<=3) {
            if (rotate == rotate_leftanddown || rotate == rotate_downandleft)
                return true;
        } else if(line>3&&row>3) {
            if (rotate == rotate_rightanddown || rotate == rotate_downandright)
                return true;
        }

        return false;
    }

    //打印棋盘情况
    private static void showLog(int[][] temp) {
        Log.i("board",temp[0][0]+" "+temp[0][1]+" "+temp[0][2]+" "+
                temp[0][3]+" "+temp[0][4]+" "+temp[0][5]);
        Log.i("board",temp[1][0]+" "+temp[1][1]+" "+temp[1][2]+" "+
                temp[1][3]+" "+temp[1][4]+" "+temp[1][5]);
        Log.i("board",temp[2][0]+" "+temp[2][1]+" "+temp[2][2]+" "+
                temp[2][3]+" "+temp[2][4]+" "+temp[2][5]);
        Log.i("board",temp[3][0]+" "+temp[3][1]+" "+temp[3][2]+" "+
                temp[3][3]+" "+temp[3][4]+" "+temp[3][5]);
        Log.i("board",temp[4][0]+" "+temp[4][1]+" "+temp[4][2]+" "+
                temp[4][3]+" "+temp[4][4]+" "+temp[4][5]);
        Log.i("board",temp[5][0]+" "+temp[5][1]+" "+temp[5][2]+" "+
                temp[5][3]+" "+temp[5][4]+" "+temp[5][5]);
        Log.i("board","---------------");
    }

    //进行旋转修正
    public static int[] Change_before_Rotate(int line, int row, int rotate)
    {
        int newLine=line;
        int newRow=row;

        //修正旋转
        Boolean isclock=false;
        if(rotate==rotate_upandright||rotate==rotate_rightanddown||
                rotate==rotate_downandleft||rotate==rotate_leftandup)
            isclock=true;
        else isclock=false;

        int correct[]=new int[2];

        if(line%3==2&&row%3==2) {
            correct[0]=line;
            correct[1]=row;
            return correct;
        }else {
            if(isclock) {//逆时针修正
                if(row==2||row==5) {
                    if(line==1||line==4) {//chess_2
                        newLine++;
                        newRow--;
                    }else if(line==3||line==6) {//chess_8
                        newLine--;
                        newRow++;
                    }
                }else if(line==2||line==5) {
                    if(row==1||row==4)//chess_4
                    {
                        newLine++;
                        newRow++;
                    }else if(row==3||row==6) {//chess_6
                        newLine--;
                        newRow--;
                    }
                } else if(line%3==1&&row%3==1) {//chess_1
                    newLine=newLine+2;
                } else if(line%3==1&&row%3==0) {//chess_3
                    newRow=newRow-2;
                } else if(line%3==0&&row%3==1) {//chess_7
                    newRow=newRow+2;
                } else if(line%3==0&&row%3==0) {//chess_9
                    newLine=newLine-2;
                }
            }else {//顺时针修正
                if(row==2||row==5) {
                    if(line==1||line==4) {//chess_2
                        newLine++;
                        newRow++;
                    }else if(line==3||line==6) {//chess_8
                        newLine--;
                        newRow--;
                    }
                }else if(line==2||line==5) {
                    if(row==1||row==4)//chess_4
                    {
                        newLine--;
                        newRow++;
                    }else if(row==3||row==6) {//chess_6
                        newLine++;
                        newRow--;
                    }
                } else if(line%3==1&&row%3==1) {//chess_1
                    newRow=newRow+2;
                } else if(line%3==1&&row%3==0) {//chess_3
                    newLine=newLine+2;
                } else if(line%3==0&&row%3==1) {//chess_7
                    newLine=newLine-2;
                } else if(line%3==0&&row%3==0) {//chess_9
                    newRow=newRow-2;
                }
            }
        }
        correct[0]=newLine;
        correct[1]=newRow;
        return correct;
    }
}