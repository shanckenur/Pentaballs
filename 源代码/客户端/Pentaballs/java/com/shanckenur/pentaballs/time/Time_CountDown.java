package com.shanckenur.pentaballs.time;

import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;

import com.shanckenur.pentaballs.Checkerboard;
import com.shanckenur.pentaballs.MainActivity;
import com.shanckenur.pentaballs.R;

/**
 * Created by mz on 2017/6/9.
 */

public class Time_CountDown {

    private static long millis;

    private static TextView showTimer=MainActivity.showTimer;

    //取消倒计时
    public static void cancel() {
        new Handler(MainActivity.context.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                //timer.cancel();
                showTimer.setText("");
            }
        });

        timer.cancel();
    }

    //开始倒计时
    public static void start() {
        /*new Handler(MainActivity.context.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

            }
        });*/
        showTimer= (TextView) MainActivity.contextView.findViewById(R.id.time_count);
        timer.start();
    }

    private static CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {

            millis=millisUntilFinished;

            new Handler(MainActivity.context.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    /*if((millis / 1000)<10) {
                        showTimer.setText("0:0" + (millis / 1000)+"");
                    } else {
                        showTimer.setText("0:" + (millis / 1000)+"");
                    }*/
                    //showTimer.setText("" + (millis / 1000)+"s");
                    MainActivity.setTimerCount((int)(millis / 1000));
                }
            });

        }

        @Override
        public void onFinish() {
            new Handler(MainActivity.context.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {

                    MainActivity.setTimerCount(0);
                    //showTimer.setText("0");

/*                    Boolean hasChess=false;
                    for(int i=2;i<5;i++) {
                        for(int j=2;j<5;j++) {
                            if(Checkerboard.getItem(i,j)!=0) {
                                MainActivity.UnLockChess();
                                MainActivity.AIgo(MainActivity.findChess(i,j),MainActivity.findArrow(1));

                                hasChess=true;
                            }

                        }
                    }

                    if(!hasChess) {
                        for(int i=1;i<6;i++) {
                            for(int j=1;j<6;j++) {
                                if(Checkerboard.getItem(i,j)!=0) {
                                    MainActivity.UnLockChess();
                                    MainActivity.findChess(i,j).callOnClick();
                                    MainActivity.findArrow(1).callOnClick();

                                }

                            }
                        }
                    }*/
                }
            });
        }
    };
}
