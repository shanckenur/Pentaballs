package com.shanckenur.pentaballs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shanckenur.pentaballs.machine.roleArray;
import com.shanckenur.pentaballs.online.Connection;
import com.shanckenur.pentaballs.online.client;
import com.shanckenur.pentaballs.time.Time_CountDown;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mz on 2017/5/15.
 */

public class Index extends AppCompatActivity {

    public final static int CN=1;
    public final static int US=2;
    public final static int appslanguage=CN;

    private static ImageView chess_1_1_1;
    private static ImageView chess_1_1_2;
    private static ImageView chess_1_1_3;
    private static ImageView chess_1_2_1;
    private static ImageView chess_1_2_2;
    private static ImageView chess_1_2_3;
    private static ImageView chess_1_3_1;
    private static ImageView chess_1_3_2;
    private static ImageView chess_1_3_3;

    private static ImageView chess_2_1_1;
    private static ImageView chess_2_1_2;
    private static ImageView chess_2_1_3;
    private static ImageView chess_2_2_1;
    private static ImageView chess_2_2_2;
    private static ImageView chess_2_2_3;
    private static ImageView chess_2_3_1;
    private static ImageView chess_2_3_2;
    private static ImageView chess_2_3_3;

    private static ImageView chess_3_1_1;
    private static ImageView chess_3_1_2;
    private static ImageView chess_3_1_3;
    private static ImageView chess_3_2_1;
    private static ImageView chess_3_2_2;
    private static ImageView chess_3_2_3;
    private static ImageView chess_3_3_1;
    private static ImageView chess_3_3_2;
    private static ImageView chess_3_3_3;

    private static ImageView chess_4_1_1;
    private static ImageView chess_4_1_2;
    private static ImageView chess_4_1_3;
    private static ImageView chess_4_2_1;
    private static ImageView chess_4_2_2;
    private static ImageView chess_4_2_3;
    private static ImageView chess_4_3_1;
    private static ImageView chess_4_3_2;
    private static ImageView chess_4_3_3;

    private static Button bt_toPerson;
    private static Button bt_toComputer;
    public static Button bt_online;

    private static RelativeLayout board;

    public static Context context;

    public static AlertDialog dialog_question;
    private static ImageView bt_question;

    public final static Time_CountDown countDown=new Time_CountDown();

    //用于标识是否为第一次连接
    public static boolean SocketInit=true;

    public static final Timer timer=new Timer();
    public static TimerTask task=new TimerTask() {

        @Override
        public void run() {
            Message message=new Message();
            message.what=1;
            handler.sendMessage(message);
        }
    };
    public static Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            client.send(9);
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.index);

        if(appslanguage==US) {
            if (!MainActivity.setLanguage)
                setLanguage("en");
        }
/*
        Resources resources = context.getResources();
        //DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        // 应用用户选择语言
        config.locale = Locale.ENGLISH;
        //resources.updateConfiguration(config, dm);
*/
        if(MainActivity.Game_state==MainActivity.Game_OnLine) {
            Intent intent = getIntent();
            String error = intent.getStringExtra("ERROR");
            System.out.println("客户端消息:返回:" + error);
            if (error.equals("Game_OnLine")) {
                if(appslanguage==US) {
                    Toast.makeText(context, "Net state is highly unstable", Toast.LENGTH_SHORT).show();
                } else if(appslanguage==CN) {
                    Toast.makeText(context, "网络不稳定", Toast.LENGTH_SHORT).show();
                }
            } else if (error.equals("OppoentExit")) {
                if(appslanguage==US) {
                    Toast.makeText(context, "Opponent exit", Toast.LENGTH_SHORT).show();
                } else if(appslanguage==CN) {
                    Toast.makeText(context, "对手退出", Toast.LENGTH_SHORT).show();
                }
            }
        }
        //} catch (Exception e){}

        Connection.getConnection(Connection.ConnectFromIndex);
        //初始化界面到首页，防止直接跳转到对战模式
        SocketInit=true;

        bt_toPerson= (Button) findViewById(R.id.index_toPerson);
        bt_toComputer= (Button) findViewById(R.id.index_toComputer);
        bt_online= (Button) findViewById(R.id.index_onLine);

        board= (RelativeLayout) findViewById(R.id.index_board);

        this.context=this;

        InitChess();
        bt_listener();
        showQuestion();
    }

    private void showQuestion() {

        bt_question= (ImageView) findViewById(R.id.bt_question);
        bt_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                final View view = inflater.inflate(R.layout.question, null);

                Button bt_question_ok= (Button) view.findViewById(R.id.bt_question_ok);

                bt_question_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_question.cancel();
                    }
                });

                builder.setView(view);

                dialog_question = builder.create();

                dialog_question.setCanceledOnTouchOutside(false);

                dialog_question.setCancelable(false);

                dialog_question.show();
            }
        });
    }

    private void setLanguage(String language) {
        //String  str = Locale.getDefault().getLanguage();
        //if(str.equals("zh")) str = "en";
        //else str = "zh";
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, null);
        updateActivity();
    }
    public void updateActivity() {
        Intent intent = new Intent();
        intent.setClass(this,Index.class);//当前Activity重新打开
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        finish();
        startActivity(intent);

        MainActivity.setLanguage=true;
    }

    @Override
    protected void onRestart() {

        myAnimation.restart_index_board(board);
        myAnimation.restart_index_menu(bt_toPerson,bt_toComputer,bt_online);
        myAnimation.restart_index_chess(chess_1_1_1,chess_1_2_1,chess_1_3_1,
                chess_1_1_2,chess_1_2_2,chess_1_3_2,
                chess_1_1_3,chess_1_2_3,chess_1_3_3,
                chess_2_1_1,chess_2_2_1,chess_2_3_1,
                chess_2_1_2,chess_2_2_2,chess_2_3_2,
                chess_2_1_3,chess_2_2_3,chess_2_3_3,
                chess_3_1_1,chess_3_2_1,chess_3_3_1,
                chess_3_1_2,chess_3_2_2,chess_3_3_2,
                chess_3_1_3,chess_3_2_3,chess_3_3_3,
                chess_4_1_1,chess_4_2_1,chess_4_3_1,
                chess_4_1_2,chess_4_2_2,chess_4_3_2,
                chess_4_1_3,chess_4_2_3,chess_4_3_3);

        //销毁房间
        if(client.cancelRoom) {
            client.send(403);
            client.cancelRoom=false;
        }

        super.onRestart();
    }

    private void bt_listener() {
        bt_toPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                Intent intent=new Intent(Index.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("Game","Game_OffLine");
*/
                myAnimation.translate_board(board,context,"Game_OffLine");
                myAnimation.alpha_menu(bt_toPerson,bt_toComputer,bt_online);
                myAnimation.alpha_chess(chess_1_1_1,chess_1_2_1,chess_1_3_1,
                        chess_1_1_2,chess_1_2_2,chess_1_3_2,
                        chess_1_1_3,chess_1_2_3,chess_1_3_3,
                        chess_2_1_1,chess_2_2_1,chess_2_3_1,
                        chess_2_1_2,chess_2_2_2,chess_2_3_2,
                        chess_2_1_3,chess_2_2_3,chess_2_3_3,
                        chess_3_1_1,chess_3_2_1,chess_3_3_1,
                        chess_3_1_2,chess_3_2_2,chess_3_3_2,
                        chess_3_1_3,chess_3_2_3,chess_3_3_3,
                        chess_4_1_1,chess_4_2_1,chess_4_3_1,
                        chess_4_1_2,chess_4_2_2,chess_4_3_2,
                        chess_4_1_3,chess_4_2_3,chess_4_3_3);

            }
        });

        bt_toComputer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                roleArray.InitRoleArray();

                myAnimation.translate_board(board,context,"Game_P2C");
                myAnimation.alpha_menu(bt_toPerson,bt_toComputer,bt_online);
                myAnimation.alpha_chess(chess_1_1_1,chess_1_2_1,chess_1_3_1,
                        chess_1_1_2,chess_1_2_2,chess_1_3_2,
                        chess_1_1_3,chess_1_2_3,chess_1_3_3,
                        chess_2_1_1,chess_2_2_1,chess_2_3_1,
                        chess_2_1_2,chess_2_2_2,chess_2_3_2,
                        chess_2_1_3,chess_2_2_3,chess_2_3_3,
                        chess_3_1_1,chess_3_2_1,chess_3_3_1,
                        chess_3_1_2,chess_3_2_2,chess_3_3_2,
                        chess_3_1_3,chess_3_2_3,chess_3_3_3,
                        chess_4_1_1,chess_4_2_1,chess_4_3_1,
                        chess_4_1_2,chess_4_2_2,chess_4_3_2,
                        chess_4_1_3,chess_4_2_3,chess_4_3_3);
            }
        });

        bt_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //连接服务器
                if(Connection.c==null) {
                    Connection.getConnection(Connection.ConnectFromButton);
                    SocketInit=false;
                } else {
                    initOnline();
                }

                //initOnline();
                /*} else {
                    Toast.makeText(Index.this,"Can not connect to server,please check network configuration",Toast.LENGTH_LONG).show();
                }*/

            }
        });
    }

    public static void initOnline() {
        //System.out.println("客户端消息:11");
        roleArray.InitRoleArray();
        //System.out.println("客户端消息:12");
        myAnimation.translate_board(board,context,"Game_OnLine");//System.out.println("客户端消息:13");
        myAnimation.alpha_menu(bt_toPerson,bt_toComputer,bt_online);//System.out.println("客户端消息:14");
        myAnimation.alpha_chess(chess_1_1_1,chess_1_2_1,chess_1_3_1,
                chess_1_1_2,chess_1_2_2,chess_1_3_2,
                chess_1_1_3,chess_1_2_3,chess_1_3_3,
                chess_2_1_1,chess_2_2_1,chess_2_3_1,
                chess_2_1_2,chess_2_2_2,chess_2_3_2,
                chess_2_1_3,chess_2_2_3,chess_2_3_3,
                chess_3_1_1,chess_3_2_1,chess_3_3_1,
                chess_3_1_2,chess_3_2_2,chess_3_3_2,
                chess_3_1_3,chess_3_2_3,chess_3_3_3,
                chess_4_1_1,chess_4_2_1,chess_4_3_1,
                chess_4_1_2,chess_4_2_2,chess_4_3_2,
                chess_4_1_3,chess_4_2_3,chess_4_3_3);
        //System.out.println("客户端消息:15");
    }

    private void InitChess() {

        chess_1_1_1= (ImageView) findViewById(R.id.index_chess_1_1_1);
        chess_1_1_2= (ImageView) findViewById(R.id.index_chess_1_1_2);
        chess_1_1_3= (ImageView) findViewById(R.id.index_chess_1_1_3);
        chess_1_2_1= (ImageView) findViewById(R.id.index_chess_1_2_1);
        chess_1_2_2= (ImageView) findViewById(R.id.index_chess_1_2_2);
        chess_1_2_3= (ImageView) findViewById(R.id.index_chess_1_2_3);
        chess_1_3_1= (ImageView) findViewById(R.id.index_chess_1_3_1);
        chess_1_3_2= (ImageView) findViewById(R.id.index_chess_1_3_2);
        chess_1_3_3= (ImageView) findViewById(R.id.index_chess_1_3_3);

        chess_2_1_1= (ImageView) findViewById(R.id.index_chess_2_1_1);
        chess_2_1_2= (ImageView) findViewById(R.id.index_chess_2_1_2);
        chess_2_1_3= (ImageView) findViewById(R.id.index_chess_2_1_3);
        chess_2_2_1= (ImageView) findViewById(R.id.index_chess_2_2_1);
        chess_2_2_2= (ImageView) findViewById(R.id.index_chess_2_2_2);
        chess_2_2_3= (ImageView) findViewById(R.id.index_chess_2_2_3);
        chess_2_3_1= (ImageView) findViewById(R.id.index_chess_2_3_1);
        chess_2_3_2= (ImageView) findViewById(R.id.index_chess_2_3_2);
        chess_2_3_3= (ImageView) findViewById(R.id.index_chess_2_3_3);

        chess_3_1_1= (ImageView) findViewById(R.id.index_chess_3_1_1);
        chess_3_1_2= (ImageView) findViewById(R.id.index_chess_3_1_2);
        chess_3_1_3= (ImageView) findViewById(R.id.index_chess_3_1_3);
        chess_3_2_1= (ImageView) findViewById(R.id.index_chess_3_2_1);
        chess_3_2_2= (ImageView) findViewById(R.id.index_chess_3_2_2);
        chess_3_2_3= (ImageView) findViewById(R.id.index_chess_3_2_3);
        chess_3_3_1= (ImageView) findViewById(R.id.index_chess_3_3_1);
        chess_3_3_2= (ImageView) findViewById(R.id.index_chess_3_3_2);
        chess_3_3_3= (ImageView) findViewById(R.id.index_chess_3_3_3);

        chess_4_1_1= (ImageView) findViewById(R.id.index_chess_4_1_1);
        chess_4_1_2= (ImageView) findViewById(R.id.index_chess_4_1_2);
        chess_4_1_3= (ImageView) findViewById(R.id.index_chess_4_1_3);
        chess_4_2_1= (ImageView) findViewById(R.id.index_chess_4_2_1);
        chess_4_2_2= (ImageView) findViewById(R.id.index_chess_4_2_2);
        chess_4_2_3= (ImageView) findViewById(R.id.index_chess_4_2_3);
        chess_4_3_1= (ImageView) findViewById(R.id.index_chess_4_3_1);
        chess_4_3_2= (ImageView) findViewById(R.id.index_chess_4_3_2);
        chess_4_3_3= (ImageView) findViewById(R.id.index_chess_4_3_3);

    }
}
