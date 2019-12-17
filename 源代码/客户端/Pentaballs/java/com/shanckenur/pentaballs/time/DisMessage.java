package com.shanckenur.pentaballs.time;

import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;

import com.shanckenur.pentaballs.MainActivity;
import com.shanckenur.pentaballs.R;

/**
 * Created by mz on 2017/6/9.
 */

public class DisMessage {

    private static long millis;

    private static TextView receiveMessage=MainActivity.receiveMessage;
    private static TextView sendMessage=MainActivity.sendMessage;

    //清除接收消息 true   清除发送消息 false
    private static Boolean type;

    public DisMessage(Boolean type)
    {
        this.type=type;
    }

    //取消倒计时
    public static void cancel() {
        new Handler(MainActivity.context.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if(type) {
                    //清除接收
                    MainActivity.LoadoppsiteMessage("");
                } else {
                    //清除发送
                    MainActivity.LoadownerMessage("");
                }

                if(!MainActivity.getSaveMessage().equals("")) {
                    MainActivity.LoadMessage(MainActivity.getSaveMessage());
                    MainActivity.setSaveMessage("");
                }
            }
        });

        timer.cancel();
    }

    public static void stop() {
        timer.cancel();
    }

    //开始倒计时
    public static void start() {

        receiveMessage= (TextView) MainActivity.contextView.findViewById(R.id.receiveMessage);
        sendMessage= (TextView) MainActivity.contextView.findViewById(R.id.sendMessage);

        timer.start();
    }

    private static CountDownTimer timer = new CountDownTimer(5000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            new Handler(MainActivity.context.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {

                    DisMessage.cancel();
                }
            });
        }
    };
}
