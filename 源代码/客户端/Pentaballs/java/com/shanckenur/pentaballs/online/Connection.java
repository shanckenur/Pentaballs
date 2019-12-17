package com.shanckenur.pentaballs.online;

import android.os.Handler;
import android.widget.Toast;

import com.shanckenur.pentaballs.Index;

import java.net.Socket;

/**
 * Created by mz on 2017/6/1.
 */

public class Connection {

    public static String host = "112.74.106.59";// 服务端ip
    public static int port = 4711;// 服务端端口号
    public static client c = null;

    public final static int ConnectFromIndex=1;
    public final static int ConnectFromButton=2;

    public static int getConnection(final int from) {

        try {
            if(c==null) {
                new Thread() {
                    public void run() {
                        try {
                            c = new client(host, port);
                            new Thread(c).start();// 监听端口
                        } catch (Exception e) {
                            e.printStackTrace();

                            c = null;

                            if (from == ConnectFromButton) {
                                new Handler(Index.context.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Index.context,
                                                "Can not connect to server,please check network configuration",
                                                Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                            System.out.println("客户端消息:服务端未响应");
                        }
                    }
                }.start();
            }

            //new Thread(c).start();// 监听端口
            return 1;
        } catch (Exception e) {
            System.out.println("客户端消息:服务端未响应");
        } // 创建客户端对象
        return 0;
    }

}
