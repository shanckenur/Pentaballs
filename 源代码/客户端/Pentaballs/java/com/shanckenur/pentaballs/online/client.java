package com.shanckenur.pentaballs.online;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import com.shanckenur.pentaballs.Checkerboard;
import com.shanckenur.pentaballs.Index;
import com.shanckenur.pentaballs.MainActivity;
import com.shanckenur.pentaballs.R;
import com.shanckenur.pentaballs.json.JavaBeanToString;
import com.shanckenur.pentaballs.json.StringToJavaBean;
import com.shanckenur.pentaballs.json.jsonObject;
import com.shanckenur.pentaballs.time.Time_CountDown;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mz on 2017/6/1.
 */

public class client implements Runnable{

    public static BufferedReader in = null;
    public static PrintWriter out = null;
    public Socket socket = null;
    public String jsonString = null;
    public Integer pkey = null;
    public String host = null;
    public int port = 0;

    //用于记录发送和接受的棋子
    private static int Chess_send=0;
    private static int Chess_receive=0;

    //用于标志是否要发送销毁房间申请
    public static boolean cancelRoom=false;



/*
    //定时主动回应心跳检测防止超时
    public static Handler handler=new Handler();
    public static Runnable sendHeard=new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                //send(9);
            } catch (Exception e){}

            //handler.postDelayed(this, 1000);
        }
    };
*/
    // 初始化函数
    public client(String host, int port) throws Exception {
        this.host = host;
        this.port = port;
        //this.f = f;

        socket = new Socket(host, port);
        // 获取输入流
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // 获取输出流
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8")), true);
/*
        task=new TimerTask() {

            @Override
            public void run() {
                Message message=new Message();
                message.what=1;
                handler.sendMessage(message);
            }
        };*/
    }

    public static void send(int ask)
    {
        jsonObject obj=new jsonObject();
        if(ask==701) {
            //创建房间请求
            obj.setState(701);
            if(MainActivity.hasPasswordConnection) {
                obj.setSetPsw(1);
            } else {
                obj.setSetPsw(0);
            }
            System.out.println("客户端消息:" +ask+"---over");
        } else if(ask==9) {
            //心跳检测回应
            obj.setState(9);
            System.out.println("客户端消息:" +ask+"---over");
        } else if(ask==403) {
            //释放房间申请
            obj.setState(403);
            System.out.println("客户端消息:" +ask+"---over");
        } else if(ask==705) {
            //重置棋盘申请
            obj.setState(705);
            System.out.println("客户端消息:" +ask+"---over");

            new Handler(Index.context.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {

                    //倒计时开始
                    Time_CountDown.start();
                }
            });
        }

        String jsonString = JavaBeanToString.JBTS(obj);
        out.write(jsonString + "\r\n");
        out.flush();
    }

    public static void send(int ask,int room)
    {
        jsonObject obj=new jsonObject();
        if(ask==702) {
            //加入房间请求
            obj.setState(702);
            obj.setRoomNumber(room);
            System.out.println("客户端消息:" +ask);
            //MainActivity.dialog_OnLine.dismiss();
        }

        String jsonString = JavaBeanToString.JBTS(obj);
        out.write(jsonString + "\r\n");
        out.flush();
    }

    public static void send(int ask,int room,int psw)
    {
        jsonObject obj=new jsonObject();
        if(ask==702) {
            //加入房间请求
            obj.setState(702);
            obj.setRoomNumber(room);
            obj.setPsw(psw);
            obj.setSetPsw(-1);
            System.out.println("客户端消息:" +ask);
            //MainActivity.dialog_OnLine.dismiss();
        }

        String jsonString = JavaBeanToString.JBTS(obj);
        out.write(jsonString + "\r\n");
        out.flush();
    }

    public static void sendChess(int chess)
    {
        Chess_send=chess;
    }

    public static void sendRotate(int rotate)
    {
        jsonObject obj=new jsonObject();
        obj.setRoate(rotate);
        if(Chess_send>0)
            obj.setState(802);
        else if(Chess_send<0)
            obj.setState(801);
        obj.setCoordinate(Chess_send);

        String jsonString = JavaBeanToString.JBTS(obj);
        System.out.println("发送:" +jsonString);
        out.write(jsonString + "\r\n");
        out.flush();
/*
        new Handler(MainActivity.context.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                MainActivity.LockChess();
            }
        });*/
    }

/*
    public int receiveState(BufferedReader in)
    {
        try {
            while (true) {
                if ((jsonString = in.readLine()) != null) {
                    jsonObject obj = new jsonObject();
                    obj = StringToJavaBean.STJB(jsonString);
                    System.out.println("客户端消息:连接成功,服务器响应值:" + obj.getState());

                    return obj.getState();
                }
            }
        }catch (Exception e) {
            System.out.println("客户端消息:获取失败");
        }

        return 0;
    }
    */
/*
    // 棋子坐标 信息发送函数
    public void send(int coordinate, int roate) {
        jsonObject obj3 = new jsonObject();
        obj3.setPkey(key);
        // System.out.println(key);
        obj3.setState(211);
        obj3.setRoate(roate);
        obj3.setCoordinate(coordinate);

        String jsonString = JavaBeanToString.JBTS(obj3);
        out.write(jsonString + "\r\n");
        out.flush();

        co = numTurnCoordinate.NTC(coordinate);
        // 在棋盘中记录棋子
        chessBoardMethod.changeChess(co[0], co[1], co[2], chessBoard);
        clientRoateMethod.RM(chessBoard, roate);
        pritfChessBoard.PCB(chessBoard);

    }
*/
    // 发送消息
    public static void sendMessage(String message) {
        jsonObject obj3 = new jsonObject();
        obj3.setState(601);
        obj3.setMessage(message);

        String jsonString = JavaBeanToString.JBTS(obj3);
        out.write(jsonString + "\r\n");
        out.flush();
    }

    // 获取服务端信息
    @Override
    public void run() {
        try {
            // 发起连接，获得棋盘标记
            System.out.println("客户端消息:客户端发起连接，等待服务器响应...");
            while (true) {
                if ((jsonString = in.readLine()) != null) {
                    jsonObject obj = new jsonObject();
                    obj = StringToJavaBean.STJB(jsonString);
                    int state = obj.getState();

                    if(state==301) {
                        //连接成功
                        System.out.println("客户端消息:连接成功");

                        if(!Index.SocketInit) {
                            if (MainActivity.socketFromIndex) {
                                new Handler(Index.context.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {

                                        Index.initOnline();
                                    }
                                });
                            } else {

                                Intent intent = new Intent(MainActivity.context, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);//无动画
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//重绘Activity

                                intent.putExtra("Game", "Game_OnLine");

                                MainActivity.context.startActivity(intent);

                                //Index.bt_online.callOnClick();
                            }
                        }

                        if(Connection.c!=null) {
                            //心跳检测
                            Index.timer.schedule(Index.task, 2000, 2000);
                        }

                    }

                    System.out.println("客户端消息:连接成功,服务器响应值:" + obj.getState());

                    break;
                }
            }

            // 持续监听服务端信息
            System.out.println("客户端消息:正在接收消息:" + jsonString);
            while (true) {
                if ((jsonString = in.readLine()) != null) {
                    jsonObject obj = new jsonObject();
                    obj = StringToJavaBean.STJB(jsonString);
                    pkey = obj.getPkey();
                    int state = obj.getState();
                    int chess=obj.getCoordinate();
                    final int rotate=obj.getRoate();
                    final int setPsw=obj.getSetPsw();
                    final int psw=obj.getPsw();
                    final String cloudmessage=obj.getMessage();

                    System.out.println("客户端消息:正在接收消息:" + jsonString);
/*
                    if(state!=9)
                        Toast.makeText(MainActivity.context,"state="+state,Toast.LENGTH_SHORT).show();
*/
                    if(state==9) {
                        //心跳检测
                        send(9);
                        send(9);
                        /*
                        try {
                            handler.removeCallbacks(sendHeard);
                            //主动发送心跳检测
                            handler.postDelayed(sendHeard, 1000);
                        } catch (Exception e){}*/

                    } else if(state==902) {
                        //房间创建成功，执黑
                        System.out.println("room：" + pkey);
                        MainActivity.Chess_choose=MainActivity.CHESS_BLACK;

                        new Handler(MainActivity.dialog_OnLine.getContext().getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                MainActivity.text_createKey.setText(""+pkey);

                                if(psw!=0) {
                                    MainActivity.Layout_createPsw.setVisibility(View.VISIBLE);
                                    MainActivity.text_createPsw.setText(""+psw);
                                }
                            }
                        });

                        cancelRoom=true;

                        //MainActivity.setRoom(pkey);
                        //System.out.println("客户端消息:正在接收消息:" + jsonString);
                    } else if(state==901) {
                        //房间加入成功，执白
                        //System.out.println("客户端消息:正在接收消息:" + jsonString);
                        MainActivity.Chess_choose=MainActivity.CHESS_WHITE;
                        MainActivity.dialog_OnLine.dismiss();

                        new Handler(MainActivity.dialog_OnLine.getContext().getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                //黑子先行，白子锁定
                                MainActivity.LockChess();
                            }
                        });

                        cancelRoom=true;

                    } else if(state==203||state==201||state==202) {System.out.println("客户端消息:chess" + chess);
                        if(chess>0) {//黑子
                            if(MainActivity.Chess_choose==MainActivity.CHESS_WHITE) {
                                //System.out.println("客户端消息:" + jsonString);
                                Chess_receive=chess;
                                new Handler(MainActivity.context.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        int oppoent[]=MainActivity.numToChess(Chess_receive);

                                        MainActivity.UnLockChess();

                                        MainActivity.findChess(oppoent[0],oppoent[1]).callOnClick();
                                        MainActivity.findArrow(rotate).callOnClick();

                                        //倒计时开始
                                        Time_CountDown.start();

                                        if(!MainActivity.receiveMessage.getText().toString().equals("你赢了")&&
                                                !MainActivity.receiveMessage.getText().toString().equals("你输了")) {
                                            MainActivity.isCloudMessage=false;
                                            MainActivity.isCloudResult=false;
                                            MainActivity.LoadMessage("该你了");
                                        }
                                    }
                                });
                            } else if(MainActivity.Chess_choose==MainActivity.CHESS_BLACK) {
                                //错误
                            }
                        } else if(chess<0) {//白子
                            if(MainActivity.Chess_choose==MainActivity.CHESS_WHITE) {
                                //错误
                            } else if(MainActivity.Chess_choose==MainActivity.CHESS_BLACK) {
                                //System.out.println("客户端消息:" + jsonString);
                                Chess_receive=chess;
                                new Handler(MainActivity.context.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        int oppoent[]=MainActivity.numToChess(Chess_receive);
                                        //System.out.println("客户端消息:oppoent[2]:" + oppoent[2]);
                                        MainActivity.UnLockChess();

                                        MainActivity.findChess(oppoent[0],oppoent[1]).callOnClick();
                                        MainActivity.findArrow(rotate).callOnClick();

                                        //倒计时开始
                                        Time_CountDown.start();

                                        if(!MainActivity.receiveMessage.getText().toString().equals("你赢了")&&
                                                !MainActivity.receiveMessage.getText().toString().equals("你输了")) {
                                            MainActivity.isCloudMessage=false;
                                            MainActivity.isCloudResult=false;
                                            MainActivity.LoadMessage("该你了");
                                        }
                                    }
                                });
                            }

                        }

                        if(state==201) {
                            //黑子胜
                            if(MainActivity.Chess_choose==MainActivity.CHESS_BLACK) {
                                System.out.println("you win");
                                new Handler(MainActivity.context.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        MainActivity.isCloudMessage=false;
                                        MainActivity.isCloudResult=true;
                                        MainActivity.LoadMessage("你赢了");
                                        Checkerboard.isFinish=true;
                                    }
                                });

                            } else if(MainActivity.Chess_choose==MainActivity.CHESS_WHITE) {
                                System.out.println("you lose");
                                new Handler(MainActivity.context.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        MainActivity.isCloudMessage=false;
                                        MainActivity.isCloudResult=true;
                                        MainActivity.LoadMessage("你输了");
                                        Checkerboard.isFinish=true;
                                    }
                                });

                            }
                        } else if(state==202) {
                            //白子胜
                            if(MainActivity.Chess_choose==MainActivity.CHESS_BLACK) {
                                System.out.println("you lose");
                                new Handler(MainActivity.context.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        MainActivity.isCloudMessage=false;
                                        MainActivity.isCloudResult=true;
                                        MainActivity.LoadMessage("你输了");
                                        Checkerboard.isFinish=true;
                                    }
                                });

                            } else if(MainActivity.Chess_choose==MainActivity.CHESS_WHITE) {
                                System.out.println("you win");
                                new Handler(MainActivity.context.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        MainActivity.isCloudMessage=false;
                                        MainActivity.isCloudResult=true;
                                        MainActivity.LoadMessage("你赢了");
                                        Checkerboard.isFinish=true;
                                    }
                                });

                            }
                        }

                    } else if(state==401) {
                        //对方加入
                        MainActivity.dialog_OnLine.dismiss();

                        new Handler(MainActivity.dialog_OnLine.getContext().getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                //开始倒计时
                                Time_CountDown.start();
                                Log.i("countd","00");
                                //Index.countDown.start();
                            }
                        });

                        //Toast.makeText(MainActivity.context,"A player get in your room",Toast.LENGTH_SHORT).show();

                    } else if(state==402) {
                        //对方退出
                        //MainActivity.dialog_OnLine.dismiss();

                        //Toast.makeText(MainActivity.context,"Opponent exit",Toast.LENGTH_SHORT).show();

                        if(MainActivity.Game_state==MainActivity.Game_OnLine) {

                            Intent intent = new Intent(MainActivity.context, Index.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);//无动画
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//重绘Activity

                            intent.putExtra("ERROR","OppoentExit");

                            MainActivity.context.startActivity(intent);

                            //Toast.makeText(MainActivity.context,"time out",Toast.LENGTH_SHORT).show();
                        }

                    } else if(state==500) {

                        Connection.c=null;

                        if(MainActivity.Game_state==MainActivity.Game_OnLine) {

                            Intent intent = new Intent(MainActivity.context, Index.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);//无动画
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//重绘Activity

                            intent.putExtra("ERROR","Game_OnLine");

                            MainActivity.context.startActivity(intent);

                            //Toast.makeText(MainActivity.context,"time out",Toast.LENGTH_SHORT).show();
                        }
                    } else if(state==704) {
                        //Connection.c=null;
                    } else if(state==705) {

                        new Handler(MainActivity.context.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                //重新开始
                                MainActivity.reInitGame();
                                MainActivity.Chess_choose=MainActivity.CHESS_WHITE;
                                MainActivity.TurnTo=MainActivity.CHESS_BLACK;
                                //黑子先行，白子锁定
                                MainActivity.LockChess();

                            }
                        });

                    } else if(state==601) {

                        new Handler(MainActivity.context.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {

                                MainActivity.isCloudMessage=true;
                                MainActivity.isCloudResult=false;
                                MainActivity.LoadoppsiteMessage(cloudmessage);
                            }
                        });

                    }
                }
            }


        } catch (Exception e) {
            //e.printStackTrace();
            Connection.c=null;
            System.out.println("客户端消息:与服务器断开连接");
        }
    }

}


