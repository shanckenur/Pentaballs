package com.shanckenur.pentaballs;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Handler;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.shanckenur.pentaballs.judge.judgeByCoordinate;
import com.shanckenur.pentaballs.time.Time_CountDown;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

import static com.shanckenur.pentaballs.MainActivity.Chess_choose;


/**
 * Created by mz on 2017/5/10.
 */

public class myAnimation {

    //记录四个子棋盘旋转情况
    private static float[] Rotate_board_record={0.0f,0.0f,0.0f,0.0f};

    //旋转棋盘
    public static void Rotate_board(final GridLayout board,final int local_board, final Boolean isClockwise, final int duration)
    {
        //Log.i("my",Rotate_board_record[local_board]+"");
        AnimationSet animationSet = new AnimationSet(true);

        Animation rotate;
        if(isClockwise) {
            rotate = new RotateAnimation(
                    Rotate_board_record[local_board], Rotate_board_record[local_board] + 90f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            //Rotate_board_record[local_board]+=90f;
        } else {
            rotate = new RotateAnimation(
                    Rotate_board_record[local_board], Rotate_board_record[local_board] - 90f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            //Rotate_board_record[local_board]-=90f;
        }

        //rotate.setFillAfter(true);
        rotate.setDuration(duration);
/*
        Animation scale = new ScaleAnimation(
                1.0f, 0.6f,1.0f, 0.6f,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);

        //scale.setFillAfter(false);
        scale.setDuration(duration/2);
        scale.setRepeatCount(1);
        scale.setRepeatMode(Animation.REVERSE);

        animationSet.addAnimation(scale);
        animationSet.addAnimation(rotate);

        animationSet.setFillAfter(true);

        board.startAnimation(animationSet);
*/
        board.startAnimation(rotate);

        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                if(MainActivity.Game_state==MainActivity.Game_OnLine)
                {
                    //对战模式开始倒计时
                    Time_CountDown.start();
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                board.clearAnimation();

                /*Animation rerotate;
                if(isClockwise)
                    rerotate= new RotateAnimation(0f,-90f,
                            Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                else
                    rerotate= new RotateAnimation(0f,90f,
                            Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);

                rerotate.setFillAfter(true);
                rerotate.setDuration(0);

                board.startAnimation(rerotate);*/

                //调用局部数组重新旋转后排子
                MainActivity.reconsituation(board,local_board+1);

                int[][] chessBoard=MainActivity.checkerboard.getWholeBoard();

                //数组重新赋值
                int chessNum=0;
                for(int line=0;line<6;line++){
                    for(int row=0;row<6;row++){
                        if(chessBoard[line][row]==1||chessBoard[line][row]==-1){
                            chessNum++;
                        }
                    }
                }

                //旋转处理完毕后解除棋子锁定
                MainActivity.UnLockChess();

                if(MainActivity.Game_state!=MainActivity.Game_OnLine) {

                    if (chessNum > 8)//8子后判断优化算法
                    {
                        int result = judgeByCoordinate.jBC(chessBoard);
                        MainActivity.ShowResult(result);
                    } else
                        MainActivity.ShowResult(203);
                }

                if(MainActivity.LockChess_after_done) {
                    //线上模式本机下棋完毕后锁定棋子
                    MainActivity.LockChess();
                    MainActivity.LockChess_after_done=false;

                    if(!MainActivity.receiveMessage.getText().toString().equals("你赢了")&&
                            !MainActivity.receiveMessage.getText().toString().equals("你输了")) {
                        MainActivity.isCloudMessage=false;
                        MainActivity.isCloudResult=false;
                        MainActivity.LoadMessage("等待对方下棋");
                    }
                }

                //线上游戏结束处理
                if(MainActivity.Game_state==MainActivity.Game_OnLine) {
                    if(Checkerboard.isFinish==true) {
                        MainActivity.LockChess();
                        MainActivity.LockArrow();
                    }
                }

                //动画结束后AI落子操作调用MainActivity中AIgo
                if(MainActivity.Game_state==MainActivity.Game_P2C&&
                        MainActivity.TurnTo!=Chess_choose&&!Checkerboard.isFinish)
                    MainActivity.AIgo(MainActivity.findChess(MainActivity.computerAI[1]+1,MainActivity.computerAI[2]+1),MainActivity.findArrow(MainActivity.computerAI[3]));


/*
                if(MainActivity.Game_state==MainActivity.Game_P2C) {
                    Log.i("temp", MainActivity.findChess(MainActivity.computerAI[1] + 1, MainActivity.computerAI[2] + 1).getTag().toString());
                    Log.i("temp", MainActivity.computerAI[1] + 1+"/" +MainActivity.computerAI[2] + 1);
                }
*/
                /*if(isClockwise)
                    board.setRotation(90f);
                else{
                    board.setRotation(-90f);
                    //setDisplayOrientation(board,90);
                }*/

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    //旋转棋子
    public static void rotate_chess(final Boolean isClockwise, final int duration,
                                    final ImageView chess_1, final ImageView chess_2, final ImageView chess_3,
                                    final ImageView chess_4, final ImageView chess_5, final ImageView chess_6,
                                    final ImageView chess_7, final ImageView chess_8, final ImageView chess_9)
    {
        Animation rotate;
        if(isClockwise)
            rotate=new RotateAnimation(0f,-90f,Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
        else
            rotate=new RotateAnimation(0f,90f,Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);

        rotate.setFillAfter(true);
        rotate.setDuration(duration);

        chess_1.startAnimation(rotate);
        chess_2.startAnimation(rotate);
        chess_3.startAnimation(rotate);
        chess_4.startAnimation(rotate);
        chess_5.startAnimation(rotate);
        chess_6.startAnimation(rotate);
        chess_7.startAnimation(rotate);
        chess_8.startAnimation(rotate);
        chess_9.startAnimation(rotate);

        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                chess_1.clearAnimation();
                chess_2.clearAnimation();
                chess_3.clearAnimation();
                chess_4.clearAnimation();
                chess_5.clearAnimation();
                chess_6.clearAnimation();
                chess_7.clearAnimation();
                chess_8.clearAnimation();
                chess_9.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    //首页棋盘平移
    public static void translate_board(final RelativeLayout board, final Context context, final String game)
    {//System.out.println("客户端消息:201");
        Animation translate_board=new TranslateAnimation(0.0f,0.0f,0.0f,120f);
        translate_board.setDuration(500);
        translate_board.setFillAfter(true);
        //System.out.println("客户端消息:202");
        try {
            board.startAnimation(translate_board);
        }catch (Exception e){}
        //System.out.println("客户端消息:203");
        translate_board.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {//System.out.println("客户端消息:21");
                Intent intent=new Intent(context,MainActivity.class);//System.out.println("客户端消息:22");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);//无动画
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//重绘Activity
                if(game.equals("Game_OffLine"))
                    intent.putExtra("Game","Game_OffLine");
                else if(game.equals("Game_P2C"))
                    intent.putExtra("Game","Game_P2C");
                else if(game.equals("Game_OnLine"))
                    intent.putExtra("Game","Game_OnLine");
                //System.out.println("客户端消息:25");
                context.startActivity(intent);//System.out.println("客户端消息:26");

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    //首页棋子消失
    public static void alpha_chess(final ImageView chess_1_1, final ImageView chess_1_2, final ImageView chess_1_3,
                                   final ImageView chess_1_4, final ImageView chess_1_5, final ImageView chess_1_6,
                                   final ImageView chess_1_7, final ImageView chess_1_8, final ImageView chess_1_9,
                                   final ImageView chess_2_1, final ImageView chess_2_2, final ImageView chess_2_3,
                                   final ImageView chess_2_4, final ImageView chess_2_5, final ImageView chess_2_6,
                                   final ImageView chess_2_7, final ImageView chess_2_8, final ImageView chess_2_9,
                                   final ImageView chess_3_1, final ImageView chess_3_2, final ImageView chess_3_3,
                                   final ImageView chess_3_4, final ImageView chess_3_5, final ImageView chess_3_6,
                                   final ImageView chess_3_7, final ImageView chess_3_8, final ImageView chess_3_9,
                                   final ImageView chess_4_1, final ImageView chess_4_2, final ImageView chess_4_3,
                                   final ImageView chess_4_4, final ImageView chess_4_5, final ImageView chess_4_6,
                                   final ImageView chess_4_7, final ImageView chess_4_8, final ImageView chess_4_9)
    {
        Animation alpha_chess=new AlphaAnimation(1.0f,0.0f);
        alpha_chess.setFillAfter(true);
        alpha_chess.setDuration(500);

        chess_1_1.startAnimation(alpha_chess);
        chess_1_2.startAnimation(alpha_chess);
        chess_1_3.startAnimation(alpha_chess);
        chess_1_4.startAnimation(alpha_chess);
        chess_1_5.startAnimation(alpha_chess);
        chess_1_6.startAnimation(alpha_chess);
        chess_1_7.startAnimation(alpha_chess);
        chess_1_8.startAnimation(alpha_chess);
        chess_1_9.startAnimation(alpha_chess);

        chess_2_1.startAnimation(alpha_chess);
        chess_2_2.startAnimation(alpha_chess);
        chess_2_3.startAnimation(alpha_chess);
        chess_2_4.startAnimation(alpha_chess);
        chess_2_5.startAnimation(alpha_chess);
        chess_2_6.startAnimation(alpha_chess);
        chess_2_7.startAnimation(alpha_chess);
        chess_2_8.startAnimation(alpha_chess);
        chess_2_9.startAnimation(alpha_chess);

        chess_3_1.startAnimation(alpha_chess);
        chess_3_2.startAnimation(alpha_chess);
        chess_3_3.startAnimation(alpha_chess);
        chess_3_4.startAnimation(alpha_chess);
        chess_3_5.startAnimation(alpha_chess);
        chess_3_6.startAnimation(alpha_chess);
        chess_3_7.startAnimation(alpha_chess);
        chess_3_8.startAnimation(alpha_chess);
        chess_3_9.startAnimation(alpha_chess);

        chess_4_1.startAnimation(alpha_chess);
        chess_4_2.startAnimation(alpha_chess);
        chess_4_3.startAnimation(alpha_chess);
        chess_4_4.startAnimation(alpha_chess);
        chess_4_5.startAnimation(alpha_chess);
        chess_4_6.startAnimation(alpha_chess);
        chess_4_7.startAnimation(alpha_chess);
        chess_4_8.startAnimation(alpha_chess);
        chess_4_9.startAnimation(alpha_chess);
    }

    public static void restart_index_board(RelativeLayout board)
    {
        Animation translate_board=new TranslateAnimation(0.0f,0.0f,0.0f,0.0f);
        translate_board.setDuration(0);
        translate_board.setFillAfter(true);

        board.startAnimation(translate_board);
    }

    public static void restart_index_chess(final ImageView chess_1_1, final ImageView chess_1_2, final ImageView chess_1_3,
                                           final ImageView chess_1_4, final ImageView chess_1_5, final ImageView chess_1_6,
                                           final ImageView chess_1_7, final ImageView chess_1_8, final ImageView chess_1_9,
                                           final ImageView chess_2_1, final ImageView chess_2_2, final ImageView chess_2_3,
                                           final ImageView chess_2_4, final ImageView chess_2_5, final ImageView chess_2_6,
                                           final ImageView chess_2_7, final ImageView chess_2_8, final ImageView chess_2_9,
                                           final ImageView chess_3_1, final ImageView chess_3_2, final ImageView chess_3_3,
                                           final ImageView chess_3_4, final ImageView chess_3_5, final ImageView chess_3_6,
                                           final ImageView chess_3_7, final ImageView chess_3_8, final ImageView chess_3_9,
                                           final ImageView chess_4_1, final ImageView chess_4_2, final ImageView chess_4_3,
                                           final ImageView chess_4_4, final ImageView chess_4_5, final ImageView chess_4_6,
                                           final ImageView chess_4_7, final ImageView chess_4_8, final ImageView chess_4_9)
    {
        Animation alpha_chess=new AlphaAnimation(1.0f,1.0f);
        alpha_chess.setFillAfter(true);
        alpha_chess.setDuration(0);

        chess_1_1.startAnimation(alpha_chess);
        chess_1_2.startAnimation(alpha_chess);
        chess_1_3.startAnimation(alpha_chess);
        chess_1_4.startAnimation(alpha_chess);
        chess_1_5.startAnimation(alpha_chess);
        chess_1_6.startAnimation(alpha_chess);
        chess_1_7.startAnimation(alpha_chess);
        chess_1_8.startAnimation(alpha_chess);
        chess_1_9.startAnimation(alpha_chess);

        chess_2_1.startAnimation(alpha_chess);
        chess_2_2.startAnimation(alpha_chess);
        chess_2_3.startAnimation(alpha_chess);
        chess_2_4.startAnimation(alpha_chess);
        chess_2_5.startAnimation(alpha_chess);
        chess_2_6.startAnimation(alpha_chess);
        chess_2_7.startAnimation(alpha_chess);
        chess_2_8.startAnimation(alpha_chess);
        chess_2_9.startAnimation(alpha_chess);

        chess_3_1.startAnimation(alpha_chess);
        chess_3_2.startAnimation(alpha_chess);
        chess_3_3.startAnimation(alpha_chess);
        chess_3_4.startAnimation(alpha_chess);
        chess_3_5.startAnimation(alpha_chess);
        chess_3_6.startAnimation(alpha_chess);
        chess_3_7.startAnimation(alpha_chess);
        chess_3_8.startAnimation(alpha_chess);
        chess_3_9.startAnimation(alpha_chess);

        chess_4_1.startAnimation(alpha_chess);
        chess_4_2.startAnimation(alpha_chess);
        chess_4_3.startAnimation(alpha_chess);
        chess_4_4.startAnimation(alpha_chess);
        chess_4_5.startAnimation(alpha_chess);
        chess_4_6.startAnimation(alpha_chess);
        chess_4_7.startAnimation(alpha_chess);
        chess_4_8.startAnimation(alpha_chess);
        chess_4_9.startAnimation(alpha_chess);
    }

    //首页菜单消失
    public static void alpha_menu(Button bt1,Button bt2,Button bt3)
    {
        Animation alpha_menu=new AlphaAnimation(1.0f,0.0f);
        alpha_menu.setFillAfter(true);
        alpha_menu.setDuration(500);

        bt1.startAnimation(alpha_menu);
        bt2.startAnimation(alpha_menu);
        bt3.startAnimation(alpha_menu);
    }

    public static void restart_index_menu(Button bt1,Button bt2,Button bt3)
    {
        Animation alpha_menu=new AlphaAnimation(1.0f,1.0f);
        alpha_menu.setFillAfter(true);
        alpha_menu.setDuration(0);

        bt1.startAnimation(alpha_menu);
        bt2.startAnimation(alpha_menu);
        bt3.startAnimation(alpha_menu);
    }


}
