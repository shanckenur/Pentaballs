package com.shanckenur.pentaballs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.shanckenur.pentaballs.machine.Analysis;
import com.shanckenur.pentaballs.machine.roleArray;
import com.shanckenur.pentaballs.online.Connection;
import com.shanckenur.pentaballs.online.client;
import com.shanckenur.pentaballs.time.DisMessage;
import com.shanckenur.pentaballs.time.Time_CountDown;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static GridLayout board;
    public static GridLayout board_upandleft;
    public static GridLayout board_upandright;
    public static GridLayout board_downandleft;
    public static GridLayout board_downandright;
    //定位子棋盘
    private static int local_board=0;
    //当前视图
    public static View contextView;
    //当前上下文
    public static Context context;

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

    public static ImageView arrow_upandleft;
    public static ImageView arrow_upandright;
    public static ImageView arrow_leftandup;
    public static ImageView arrow_leftanddown;
    public static ImageView arrow_rightandup;
    public static ImageView arrow_rightanddown;
    public static ImageView arrow_downandleft;
    public static ImageView arrow_downandright;

    private final static int rotate_upandleft=-1;
    private final static int rotate_upandright=2;
    private final static int rotate_leftandup=1;
    private final static int rotate_leftanddown=-3;
    private final static int rotate_rightandup=-2;
    private final static int rotate_rightanddown=4;
    private final static int rotate_downandleft=3;
    private final static int rotate_downandright=-4;

    //是否落子
    public static Boolean hasSetChess=false;
    //旋转方向
    private Boolean rotate_state;

    private int line;
    private int row;

    public static Checkerboard checkerboard;

    public final static int CHESS_BLACK=1;
    public final static int CHESS_WHITE=-1;
    public final static int CHESS_NONE=0;

    //游戏模式
    public final static int Game_OffLine=1;
    public final static int Game_P2C=2;
    public final static int Game_OnLine=3;
    public static int Game_state=0;

    //黑白执子判断
    public static int TurnTo=0;
    //人机模式、线上对战选子
    public static int Chess_choose=0;

    private ImageView menu_up;

    private static AlertDialog dialog_menu;
    private static AlertDialog dialog_chooseChess;
    public static AlertDialog dialog_OnLine;
    public static AlertDialog dialog_exit;
    public static AlertDialog dialog_course;
    public static AlertDialog dialog_chat;

    //public static TextView result;

    //AI单次结果
    public static int[] computerAI;

    //标记判断人机模式是否选好AI难度及棋子类型
    private Boolean chesschoose=false;
    private Boolean levelchoose=false;

    //AI等级
    public static final int Level_hard=1;
    public static final int Level_middle=2;
    public static final int Level_simple=3;

    //创建房间号码
    public static TextView text_createKey;
    public static TextView text_createPsw;
    public static LinearLayout Layout_createPsw;
    public static LinearLayout Layout_inputPsw;

    //标记是否为线上模式本机落子完毕便于锁定棋子处理
    public static boolean LockChess_after_done=false;

    //标记是否为bt_online发送连接请求(辅助)
    public static boolean socketFromIndex=true;

    private static ImageView bt_course;

    //public static boolean backable=true;

    //是否设置系统语言
    public static Boolean setLanguage=false;

    //显示倒计时
    public static TextView showTimer;

    //是否加密连接
    public static Boolean hasPasswordConnection=true;

    //聊天显示
    public static TextView receiveMessage;
    public static TextView sendMessage;
    public static ImageView message;
    public static DisMessage DisMessage_receive;
    public static DisMessage DisMessage_send;

    //消息暂存
    public static String SaveMessage;
    //是否为聊天消息，紧急执行
    public static Boolean isCloudMessage=false;
    //是否为对战结果
    public static Boolean isCloudResult=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        context=this;

        contextView=View.inflate(this,R.layout.activity_main,null);

        //result= (TextView) findViewById(R.id.result_temp);

        showTimer= (TextView) findViewById(R.id.time_count);

        //接收游戏类型信息
        Intent intent=getIntent();
        String Game=intent.getStringExtra("Game");
        if(Game.equals("Game_OffLine"))//线下双人游戏
        {
            Game_state=Game_OffLine;
            TurnTo=CHESS_BLACK;
            //Time_CountDown.start();
        }else if(Game.equals("Game_P2C"))//人机模式
        {
            Game_state=Game_P2C;
            showChooseChess();
            TurnTo=CHESS_BLACK;
        }else if(Game.equals("Game_OnLine"))//线上对战模式
        {
            Game_state=Game_OnLine;
            showOnLine();
            TurnTo=CHESS_BLACK;
        }

        InitBoard();
        InitChess();
        InitArrow();
        InitMenu();
        InitMessage();

        //新建一轮游戏
        checkerboard=new Checkerboard(this);

        showQuestion();
/*
        int a[]=Analysis.Change_before_Rotate(6,2,rotate_leftanddown);
        Log.i("test",a[0]+"-"+a[1]);
*/
        //chess_1_1_1.callOnClick();
    }

    private void InitMessage() {

        receiveMessage= (TextView) findViewById(R.id.receiveMessage);
        sendMessage= (TextView) findViewById(R.id.sendMessage);

        message= (ImageView) findViewById(R.id.message);

        if(Game_state!=Game_OnLine)
            message.setVisibility(View.GONE);

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.chat, null);

                final TextView chat1= (TextView) view.findViewById(R.id.chat1);
                final TextView chat2= (TextView) view.findViewById(R.id.chat2);
                final TextView chat3= (TextView) view.findViewById(R.id.chat3);
                final TextView chat4= (TextView) view.findViewById(R.id.chat4);
                final TextView chat5= (TextView) view.findViewById(R.id.chat5);
                final TextView chat6= (TextView) view.findViewById(R.id.chat6);

                builder.setView(view);

                chat1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        client.sendMessage(chat1.getText().toString());
                        LoadownerMessage(chat1.getText().toString());
                        dialog_chat.cancel();
                    }
                });

                chat2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        client.sendMessage(chat2.getText().toString());
                        LoadownerMessage(chat2.getText().toString());
                        dialog_chat.cancel();
                    }
                });

                chat3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        client.sendMessage(chat3.getText().toString());
                        LoadownerMessage(chat3.getText().toString());
                        dialog_chat.cancel();
                    }
                });

                chat4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        client.sendMessage(chat4.getText().toString());
                        LoadownerMessage(chat4.getText().toString());
                        dialog_chat.cancel();
                    }
                });

                chat5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        client.sendMessage(chat5.getText().toString());
                        LoadownerMessage(chat5.getText().toString());
                        dialog_chat.cancel();
                    }
                });

                chat6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        client.sendMessage(chat6.getText().toString());
                        LoadownerMessage(chat6.getText().toString());
                        dialog_chat.cancel();
                    }
                });

                dialog_chat = builder.create();

                Window dialogWindow = dialog_chat.getWindow();
                dialogWindow.setGravity(Gravity.BOTTOM);
                //dialogWindow.setDimAmount(0);

                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                lp.y = 10;
                lp.dimAmount = 0.0f;
                //lp.alpha = 0.5f;

                dialogWindow.setAttributes(lp);

                dialog_chat.show();

            }
        });

        //client.sendMessage("来呀互相伤害啊");
        //LoadownerMessage("来呀互相伤害啊");
    }

    public static void LoadownerMessage(String message)
    {
        sendMessage.setText(message);
        DisMessage_send=new DisMessage(false);
        DisMessage_send.start();
    }

    public static void LoadoppsiteMessage(String message)
    {
        if(isCloudResult) {
            SaveMessage=receiveMessage.getText().toString();
        }

        receiveMessage.setText(message);
        if(!message.equals("")) {
            DisMessage_receive = new DisMessage(true);
            DisMessage_receive.start();
        }
    }

    public static void LoadMessage(String message)
    {
        if(!checkerboard.isFinish||Game_state!=Game_OnLine) {
            String exist = receiveMessage.getText().toString();

            if (message.equals("该你了") || message.equals("等待对方下棋") ||
                    message.equals("白子执棋") || message.equals("黑子执棋")) {
                if (exist.equals("该你了") || exist.equals("等待对方下棋") ||
                        exist.equals("白子执棋") || exist.equals("黑子执棋")) {
                    receiveMessage.setText(message);
                }
            }


            if (receiveMessage.getText().toString().equals("")) {
                isCloudMessage = false;
                receiveMessage.setText(message);
            } else if (isCloudResult) {
                isCloudMessage = false;
                receiveMessage.setText(message);
            } else {
                SaveMessage = message;
            }
        }
    }

    public static String getSaveMessage()
    {
        return SaveMessage;
    }

    public static void setSaveMessage(String message)
    {
        SaveMessage=message;
    }

    public static void reInitMessage()
    {
        isCloudMessage=false;
        isCloudResult=false;
        checkerboard.isFinish=false;
        receiveMessage.setText("");
        sendMessage.setText("");
    }

    public static void setTimerCount(int time)
    {
        showTimer.setText(time+"s");
    }

    private void InitBoard() {

        board_upandleft = (GridLayout) MainActivity.this.findViewById(R.id.board_upandleft);
        board_upandright= (GridLayout) MainActivity.this.findViewById(R.id.board_upandright);
        board_downandleft= (GridLayout) MainActivity.this.findViewById(R.id.board_downandleft);
        board_downandright= (GridLayout) MainActivity.this.findViewById(R.id.board_downandright);

    }

    private void showQuestion() {

        bt_course= (ImageView) findViewById(R.id.bt_course);
        bt_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                final View view = inflater.inflate(R.layout.course, null);

                Button bt_question_ok= (Button) view.findViewById(R.id.bt_question_ok);

                bt_question_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_course.cancel();
                    }
                });

                builder.setView(view);

                dialog_course = builder.create();

                dialog_course.setCanceledOnTouchOutside(false);

                dialog_course.setCancelable(false);

                dialog_course.show();
            }
        });
    }

    public void showOnLine() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.online_connect, null);

        Button bt_online_create= (Button) view.findViewById(R.id.bt_online_create);
        Button bt_online_joinin= (Button) view.findViewById(R.id.bt_online_joinin);
        Button bt_online_exit= (Button) view.findViewById(R.id.bt_online_exit);
        text_createKey= (TextView) view.findViewById(R.id.text_newKey);
        text_createPsw= (TextView) view.findViewById(R.id.text_createPsw);
        final EditText edit_joininKey= (EditText) view.findViewById(R.id.text_existKey);
        final EditText text_inputPsw= (EditText) view.findViewById(R.id.text_inputPsw);
        RadioGroup radioGroup= (RadioGroup) view.findViewById(R.id.radiogroup);
        RadioButton radio_private= (RadioButton) view.findViewById(R.id.radio_private);
        RadioButton radio_public= (RadioButton) view.findViewById(R.id.radio_public);

        Layout_createPsw= (LinearLayout) view.findViewById(R.id.Layout_createPsw);
        Layout_inputPsw= (LinearLayout) view.findViewById(R.id.Layout_inputPsw);

        //设置默认状态
        radioGroup.check(R.id.radio_private);
        Layout_createPsw.setVisibility(View.INVISIBLE);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                int radioButtonId = group.getCheckedRadioButtonId();

                switch (radioButtonId) {
                    case R.id.radio_private:
                        Layout_createPsw.setVisibility(View.INVISIBLE);
                        Layout_inputPsw.setVisibility(View.VISIBLE);
                        hasPasswordConnection=true;
                        break;
                    case R.id.radio_public:
                        Layout_createPsw.setVisibility(View.GONE);
                        Layout_inputPsw.setVisibility(View.GONE);
                        hasPasswordConnection=false;
                        break;
                }

            }
        });

        bt_online_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //向服务器发创建房间命令
                client.send(701);

                socketFromIndex=true;
            }
        });

        bt_online_joinin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //向服务器发加入房间命令

                String room=edit_joininKey.getText().toString();
                if(room.equals("")) {
                    if(Index.appslanguage==Index.US) {
                        Toast.makeText(view.getContext(), "please input a room", Toast.LENGTH_SHORT).show();
                    } else if(Index.appslanguage==Index.CN) {
                        Toast.makeText(view.getContext(), "请输入房间号", Toast.LENGTH_SHORT).show();
                    }
                } else if(room.length()!=5) {
                    if(Index.appslanguage==Index.US) {
                        Toast.makeText(view.getContext(),"please ensure the room",Toast.LENGTH_SHORT).show();
                    } else if(Index.appslanguage==Index.CN) {
                        Toast.makeText(view.getContext(), "请确认房间号", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if(hasPasswordConnection) {
                        String psw=text_inputPsw.getText().toString();
                        client.send(702,Integer.parseInt(room),Integer.parseInt(psw));
                    } else {
                        client.send(702, Integer.parseInt(room));
                    }
                }

                socketFromIndex=true;

            }
        });

        bt_online_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(socketFromIndex)
                    finish();
                else {
                    socketFromIndex=true;

                    Intent intent=new Intent(MainActivity.context,Index.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);//无动画
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//重绘Activity

                    MainActivity.context.startActivity(intent);
                }

            }
        });

        builder.setView(view);

        dialog_OnLine = builder.create();

        dialog_OnLine.setCanceledOnTouchOutside(false);

        dialog_OnLine.setCancelable(false);

        dialog_OnLine.show();

    }

    //AI选择难度模式与黑白棋
    private void showChooseChess() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.p2c_choose, null);

        final Button bt_chooseHard= (Button) view.findViewById(R.id.bt_Hardlevel);
        final Button bt_chooseMiddle= (Button) view.findViewById(R.id.bt_MiddleLevel);
        final Button bt_chooseSimple= (Button) view.findViewById(R.id.bt_SimpleLevel);

        final Button bt_chooseBlack= (Button) view.findViewById(R.id.bt_chooseBlack);
        final Button bt_chooseWhite= (Button) view.findViewById(R.id.bt_chooseWhite);

        bt_chooseHard.setTextSize(15);
        bt_chooseMiddle.setTextSize(15);
        bt_chooseSimple.setTextSize(15);
        bt_chooseBlack.setTextSize(15);
        bt_chooseWhite.setTextSize(15);

        builder.setView(view);

        final float text_size=15;

        bt_chooseHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_chooseHard.setTextColor(getResources().getColor(R.color.gold));
                bt_chooseMiddle.setTextColor(getResources().getColor(R.color.mytext));
                bt_chooseSimple.setTextColor(getResources().getColor(R.color.mytext));
                bt_chooseHard.setTextSize(20);
                bt_chooseMiddle.setTextSize(text_size);
                bt_chooseSimple.setTextSize(text_size);

                Checkerboard.AiLevel=Level_hard;

                levelchoose=true;
                if(levelchoose&&chesschoose) {
                    if(Chess_choose==CHESS_WHITE) {
                        //AI先下
                        TurnTo=CHESS_BLACK;
                        chess_1_2_2.callOnClick();
                        arrow_leftandup.callOnClick();
                    }

                    dialog_chooseChess.cancel();
                    levelchoose=false;
                    chesschoose=false;
                }
            }
        });

        bt_chooseMiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_chooseHard.setTextColor(getResources().getColor(R.color.mytext));
                bt_chooseMiddle.setTextColor(getResources().getColor(R.color.gold));
                bt_chooseSimple.setTextColor(getResources().getColor(R.color.mytext));
                bt_chooseHard.setTextSize(text_size);
                bt_chooseMiddle.setTextSize(20);
                bt_chooseSimple.setTextSize(text_size);

                Checkerboard.AiLevel=Level_middle;

                levelchoose=true;
                if(levelchoose&&chesschoose) {
                    if(Chess_choose==CHESS_WHITE) {
                        //AI先下
                        TurnTo=CHESS_BLACK;
                        chess_1_2_2.callOnClick();
                        arrow_leftandup.callOnClick();
                    }

                    dialog_chooseChess.cancel();
                    levelchoose=false;
                    chesschoose=false;
                }
            }
        });

        bt_chooseSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_chooseHard.setTextColor(getResources().getColor(R.color.mytext));
                bt_chooseMiddle.setTextColor(getResources().getColor(R.color.mytext));
                bt_chooseSimple.setTextColor(getResources().getColor(R.color.gold));
                bt_chooseHard.setTextSize(text_size);
                bt_chooseMiddle.setTextSize(text_size);
                bt_chooseSimple.setTextSize(20);

                Checkerboard.AiLevel=Level_simple;

                levelchoose=true;
                if(levelchoose&&chesschoose) {
                    if(Chess_choose==CHESS_WHITE) {
                        //AI先下
                        TurnTo=CHESS_BLACK;
                        chess_1_2_2.callOnClick();
                        arrow_leftandup.callOnClick();
                    }

                    dialog_chooseChess.cancel();
                    levelchoose=false;
                    chesschoose=false;
                }
            }
        });

        bt_chooseBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_chooseBlack.setTextColor(getResources().getColor(R.color.gold));
                bt_chooseWhite.setTextColor(getResources().getColor(R.color.mytext));
                bt_chooseBlack.setTextSize(20);
                bt_chooseWhite.setTextSize(text_size);

                Chess_choose=TurnTo=CHESS_BLACK;
                chesschoose=true;
                if(levelchoose&&chesschoose) {
                    dialog_chooseChess.cancel();
                    levelchoose=false;
                    chesschoose=false;
                }
            }
        });

        bt_chooseWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bt_chooseBlack.setTextColor(getResources().getColor(R.color.mytext));
                bt_chooseWhite.setTextColor(getResources().getColor(R.color.gold));
                bt_chooseBlack.setTextSize(text_size);
                bt_chooseWhite.setTextSize(20);

                Chess_choose=CHESS_WHITE;
                chesschoose=true;
                if(levelchoose&&chesschoose) {
                    //AI先下
                    TurnTo=CHESS_BLACK;
                    chess_1_2_2.callOnClick();
                    arrow_leftandup.callOnClick();

                    dialog_chooseChess.cancel();
                    levelchoose=false;
                    chesschoose=false;
                }
            }
        });

        dialog_chooseChess = builder.create();

        Window dialogWindow = dialog_chooseChess.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);

        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 10;

        dialogWindow.setAttributes(lp);

        dialog_chooseChess.setCanceledOnTouchOutside(false);

        dialog_chooseChess.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if(keyCode==KeyEvent.KEYCODE_BACK) {
                    levelchoose=false;
                    chesschoose=false;

                    finish();
                }

                return false;
            }
        });

        dialog_chooseChess.show();
    }

    //初始化菜单
    private void InitMenu() {

        menu_up= (ImageView) findViewById(R.id.menu_up);

        menu_up.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = LayoutInflater.from(context);
                View view = inflater.inflate(R.layout.menu, null);

                Button bt_newGame= (Button) view.findViewById(R.id.bt_newGame);
                Button bt_toPerson= (Button) view.findViewById(R.id.bt_toPerson);
                Button bt_toComputer= (Button) view.findViewById(R.id.bt_toComputer);
                Button bt_onLine= (Button) view.findViewById(R.id.bt_onLine);

                builder.setView(view);

                bt_newGame.setVisibility(View.VISIBLE);
                bt_toPerson.setVisibility(View.VISIBLE);
                bt_toComputer.setVisibility(View.VISIBLE);
                bt_onLine.setVisibility(View.VISIBLE);

                //隐藏当前模式
                if(Game_state==0)
                    bt_newGame.setVisibility(View.GONE);
                else if(Game_state==Game_OffLine)
                    bt_toPerson.setVisibility(View.GONE);
                else if(Game_state==Game_P2C)
                    bt_toComputer.setVisibility(View.GONE);
                else if(Game_state==Game_OnLine)
                    bt_onLine.setVisibility(View.GONE);

                bt_newGame.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        reInitGame();

                        TurnTo=CHESS_BLACK;
                        Chess_choose=CHESS_BLACK;

                        if(Game_state==Game_P2C)
                            showChooseChess();

                        //重置对方棋盘
                        if(Game_state==Game_OnLine) {
                            reInitMessage();
                            client.send(705);
                        }

                        dialog_menu.cancel();
                    }
                });

                bt_toPerson.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Game_state=Game_OffLine;

                        reInitGame();
                        reInitMessage();

                        TurnTo=CHESS_BLACK;

                        dialog_menu.cancel();
                    }
                });

                bt_toComputer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Game_state=Game_P2C;

                        reInitGame();
                        reInitMessage();

                        showChooseChess();

                        roleArray.InitRoleArray();
                        dialog_menu.cancel();
                    }
                });

                bt_onLine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Game_state=Game_OnLine;

                        reInitGame();
                        reInitMessage();

                        socketFromIndex=false;

                        if(Connection.c==null) {
                            //连接服务器
                            Connection.getConnection(Connection.ConnectFromButton);
                            Index.SocketInit=false;
                        } else {

                            Intent intent = new Intent(MainActivity.context, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);//无动画
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//重绘Activity

                            intent.putExtra("Game", "Game_OnLine");

                            MainActivity.context.startActivity(intent);
                        }

                        dialog_menu.cancel();
                    }
                });

                dialog_menu = builder.create();

                Window dialogWindow = dialog_menu.getWindow();
                dialogWindow.setGravity(Gravity.BOTTOM);

                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                lp.y = 10;

                dialogWindow.setAttributes(lp);

                dialog_menu.show();
            }
        });
    }

    public static void reInitGame()
    {
        //重新开始
        checkerboard=new Checkerboard(context);

        //恢复空棋盘
        reconsituation(board_upandleft,1);
        reconsituation(board_upandright,2);
        reconsituation(board_downandleft,3);
        reconsituation(board_downandright,4);

        local_board=0;
        hasSetChess=false;
    }

    public void onClick(View v) {

        //操作是否有改变
        Boolean somethingchange=false;

        String id_style=v.getTag().toString().substring(0,5);

        if(!hasSetChess&&id_style.equals("chess"))//在没有落子情况下落子
        {
            //落子后立即锁定棋子
            LockChess();

            ImageView chess=(ImageView)v;

            String chess_num=v.getTag().toString();

            //确定落子所在区域
            if(chess_num.contains("chess_1_"))
                local_board=1;
            else if(chess_num.contains("chess_2_"))
                local_board=2;
            else if(chess_num.contains("chess_3_"))
                local_board=3;
            else if(chess_num.contains("chess_4_"))
                local_board=4;

            //定位子棋盘
            if(local_board==1)
                board= (GridLayout) findViewById(R.id.board_upandleft);
            else if(local_board==2)
                board= (GridLayout) findViewById(R.id.board_upandright);
            else if(local_board==3)
                board= (GridLayout) findViewById(R.id.board_downandleft);
            else if(local_board==4)
                board= (GridLayout) findViewById(R.id.board_downandright);

            if(Game_state==Game_OffLine&&TurnTo==CHESS_BLACK)//线下对战落黑子
            {
                GoBlack(chess);
                TurnTo=CHESS_WHITE;

            }else if(Game_state==Game_OffLine&&TurnTo==CHESS_WHITE)//线下对战落白子
            {
                GoWhite(chess);
                TurnTo=CHESS_BLACK;

            }else if(Game_state==Game_P2C&&TurnTo==Chess_choose)//人机模式人落子
            {
                if(TurnTo==CHESS_WHITE) {
                    GoWhite(chess);
                    TurnTo = CHESS_BLACK;
                }else if(TurnTo==CHESS_BLACK) {
                    GoBlack(chess);
                    TurnTo = CHESS_WHITE;
                }
            }else if(Game_state==Game_P2C&&TurnTo+Chess_choose==0)//人机模式AI落子
            {
                if(TurnTo==CHESS_WHITE) {
                    GoWhite(chess);
                    TurnTo = CHESS_BLACK;
                }else if(TurnTo==CHESS_BLACK) {
                    GoBlack(chess);
                    TurnTo = CHESS_WHITE;
                }
            }else if(Game_state==Game_OnLine&&TurnTo==Chess_choose)//线上对战本机落子
            {
                if(TurnTo==CHESS_WHITE)
                    GoWhite(chess);
                else if(TurnTo==CHESS_BLACK)
                    GoBlack(chess);

                //发送棋子
                client.sendChess(chessToNum(chess,TurnTo));

            }else if(Game_state==Game_OnLine&&TurnTo+Chess_choose==0)//线上对战对方落子
            {
                if(TurnTo==CHESS_WHITE)
                    GoWhite(chess);
                else if(TurnTo==CHESS_BLACK)
                    GoBlack(chess);
            }

            
            //chess_1_1_1.callOnClick();

            //箭头出现
            Show_arrow();

            somethingchange=true;

            //落子处理完毕后解除旋转锁定
            UnLockArrow();

        }else{//在已经落子情况下旋转
            if(id_style.equals("arrow"))
            {
                //旋转后立即锁定旋转
                LockArrow();

                //UnLockArrow();

                String arrow_tag=v.getTag().toString();

                //确定旋转区域
                if(arrow_tag.contains("arrow_1_"))
                    local_board=1;
                else if(arrow_tag.contains("arrow_2_"))
                    local_board=2;
                else if(arrow_tag.contains("arrow_3_"))
                    local_board=3;
                else if(arrow_tag.contains("arrow_4_"))
                    local_board=4;

                //确定旋转子棋盘
                if(local_board==1)
                    board= (GridLayout) findViewById(R.id.board_upandleft);
                else if(local_board==2)
                    board= (GridLayout) findViewById(R.id.board_upandright);
                else if(local_board==3)
                    board= (GridLayout) findViewById(R.id.board_downandleft);
                else if(local_board==4)
                    board= (GridLayout) findViewById(R.id.board_downandright);

                //确定旋转方向
                if(arrow_tag.contains("_clock"))
                    rotate_state=true;
                else if (arrow_tag.contains("_anticlock"))
                    rotate_state=false;

                //棋盘旋转动画
                myAnimation.Rotate_board(board,local_board-1,rotate_state,1000);

                //棋子旋转动画
                rotate_chess(local_board,rotate_state);

                //异步处理棋盘旋转数组
                if(rotate_state)
                    checkerboard.rotate_board(local_board);
                else
                    checkerboard.rotate_board(local_board*-1);

                if(Game_state==Game_OnLine&&TurnTo==Chess_choose)//线上对战本机旋转
                {
                    //发送棋子
                    if(rotate_state)
                        client.sendRotate(local_board);
                    else
                        client.sendRotate(local_board*-1);

                    if(TurnTo==CHESS_WHITE)
                        TurnTo = CHESS_BLACK;
                    else if(TurnTo==CHESS_BLACK)
                        TurnTo = CHESS_WHITE;

                    LockChess_after_done=true;

                }else if(Game_state==Game_OnLine&&TurnTo+Chess_choose==0)//线上对战对方旋转
                {
                    if(TurnTo==CHESS_WHITE)
                        TurnTo = CHESS_BLACK;
                    else if(TurnTo==CHESS_BLACK)
                        TurnTo = CHESS_WHITE;
                }

                //异步分析人机下一步落子位置
                if(Game_state==Game_P2C&&TurnTo!=Chess_choose)
                {
                    //Timer timer=new Timer();

                    if(TurnTo==CHESS_BLACK) {
                        //以黑子视角分析
                        computerAI=Analysis.Analyze(checkerboard.getWholeBoard(),CHESS_WHITE);

                    }else if(TurnTo==CHESS_WHITE) {
                        //以白子视角分析
                        computerAI=Analysis.Analyze(checkerboard.getWholeBoard(),CHESS_BLACK);

                    }
                }

                //隐藏箭头
                Hide_arrow();

                somethingchange=true;

            }else{
                if(Index.appslanguage==Index.US) {
                    Toast.makeText(context,"please click a arrow to rotate the board",Toast.LENGTH_SHORT).show();
                } else if(Index.appslanguage==Index.CN) {
                    Toast.makeText(context, "请点击箭头旋转棋盘", Toast.LENGTH_SHORT).show();
                }
            }

        }

        //改变落子操作状态
        if(hasSetChess&&somethingchange)
            hasSetChess=false;
        else if (!hasSetChess&&somethingchange)
            hasSetChess=true;

    }
    //AI落子调用操作
    public static void AIgo(ImageView chess,ImageView arrow)
    {
        chess.callOnClick();
        arrow.callOnClick();

        Log.i("temp",chess.getTag().toString());
    }

    //旋转区域上所有棋子旋转
    private void rotate_chess(int local_board,boolean rotate_state) {

        if(rotate_state)
        {
            switch (local_board)
            {
                case 1:
                    myAnimation.rotate_chess(true,1000,
                            chess_1_1_1,chess_1_1_2,chess_1_1_3,
                            chess_1_2_1,chess_1_2_2,chess_1_2_3,
                            chess_1_3_1,chess_1_3_2,chess_1_3_3);
                    break;
                case 2:
                    myAnimation.rotate_chess(true,1000,
                            chess_2_1_1,chess_2_1_2,chess_2_1_3,
                            chess_2_2_1,chess_2_2_2,chess_2_2_3,
                            chess_2_3_1,chess_2_3_2,chess_2_3_3);
                    break;
                case 3:
                    myAnimation.rotate_chess(true,1000,
                            chess_3_1_1,chess_3_1_2,chess_3_1_3,
                            chess_3_2_1,chess_3_2_2,chess_3_2_3,
                            chess_3_3_1,chess_3_3_2,chess_3_3_3);
                    break;
                case 4:
                    myAnimation.rotate_chess(true,1000,
                            chess_4_1_1,chess_4_1_2,chess_4_1_3,
                            chess_4_2_1,chess_4_2_2,chess_4_2_3,
                            chess_4_3_1,chess_4_3_2,chess_4_3_3);
                    break;
            }

        }else{
            switch (local_board)
            {
                case 1:
                    myAnimation.rotate_chess(false,1000,
                            chess_1_1_1,chess_1_1_2,chess_1_1_3,
                            chess_1_2_1,chess_1_2_2,chess_1_2_3,
                            chess_1_3_1,chess_1_3_2,chess_1_3_3);
                    break;
                case 2:
                    myAnimation.rotate_chess(false,1000,
                            chess_2_1_1,chess_2_1_2,chess_2_1_3,
                            chess_2_2_1,chess_2_2_2,chess_2_2_3,
                            chess_2_3_1,chess_2_3_2,chess_2_3_3);
                    break;
                case 3:
                    myAnimation.rotate_chess(false,1000,
                            chess_3_1_1,chess_3_1_2,chess_3_1_3,
                            chess_3_2_1,chess_3_2_2,chess_3_2_3,
                            chess_3_3_1,chess_3_3_2,chess_3_3_3);
                    break;
                case 4:
                    myAnimation.rotate_chess(false,1000,
                            chess_4_1_1,chess_4_1_2,chess_4_1_3,
                            chess_4_2_1,chess_4_2_2,chess_4_2_3,
                            chess_4_3_1,chess_4_3_2,chess_4_3_3);
                    break;
            }
        }
    }

    //按照旋转后数组重新置子
    public static void reconsituation(GridLayout board,int block)
    {
        int [][]temp=checkerboard.getBoard(block);
        
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++) {
                if (temp[i][j]==CHESS_BLACK)
                    GoBlack(i+1,j+1,block);
                else if(temp[i][j]==CHESS_WHITE)
                    GoWhite(i+1,j+1,block);
                else
                    UnGo(i+1,j+1,block);
            }
    }

    //取特定棋子行数(从1始)
    private static int getLine(ImageView chess)
    {
        String tag=chess.getTag().toString().substring(6,7);
        String tag1=chess.getTag().toString().substring(8,9);

        if(tag.equals("3")||tag.equals("4"))
            return Integer.parseInt(tag1)+3;

        return Integer.parseInt(tag1);
    }

    //取特定棋子列数(从1始)
    private static int getRow(ImageView chess)
    {
        String tag=chess.getTag().toString().substring(6,7);
        String tag1=chess.getTag().toString().substring(10,11);

        if(tag.equals("2")||tag.equals("4"))
            return Integer.parseInt(tag1)+3;

        return Integer.parseInt(tag1);
    }

    public static int chessToNum(ImageView chess,int type)
    {
        int num=(getLine(chess)-1)*6+getRow(chess);
        if(type==CHESS_WHITE)
            num=num*(-1);

        return num;
    }

    public static int[] numToChess(int num)
    {
        int[] co = new int[3];
        int kindOfChess=0;
        //判断是黑子还是白子
        if(num>0)
            kindOfChess=1;//1代表黑子
        else
            kindOfChess=-1;//-1代表白子
        num=num<0?-num:num;//取绝对值
        num--;
        co[0]=num/6+1;//获取横坐标
        co[1]=num%6+1;//获取纵坐标
        co[2]=kindOfChess;

        return co;
    }
    /*
        private static int Block(int line, int row)
        {
            if(line<3&&row<3)
                return 1;
            else if(line<3&&row>2)
                return 2;
            else if(line>2&&row<3)
                return 3;
            else if(line>2&&row>2)
                return 4;
            return 0;
        }

        public static void GoBlack(int line, int row)
        {
            String tag="chess_"+Block(line, row)+"_"+(line+1)+"_"+(row+1);
            ImageView chess= (ImageView) contextView.findViewWithTag(tag);
            Toast.makeText(context,chess.getTag().toString(),Toast.LENGTH_SHORT).show();
            GoBlack(chess);
        }

        public static void GoWhite(int line, int row)
        {
            String tag="chess_"+Block(line, row)+"_"+(line+1)+"_"+(row+1);
            ImageView chess= (ImageView) contextView.findViewWithTag(tag);

            GoWhite(chess);
        }
    */
    public static void GoBlack(ImageView chess)
    {
        chess.setAlpha(1.0f);
        chess.setImageResource(R.drawable.blackchess);

        checkerboard.DoaChess(getLine(chess),getRow(chess),CHESS_BLACK);
    }

    public static void GoWhite(ImageView chess)
    {
        chess.setAlpha(1.0f);
        chess.setImageResource(R.drawable.whitechess);

        checkerboard.DoaChess(getLine(chess),getRow(chess),CHESS_WHITE);
    }

    public static void UnGo(ImageView chess)
    {
        chess.setAlpha(0.0f);

        checkerboard.DoaChess(getLine(chess),getRow(chess),CHESS_NONE);
    }

    //获取结果
    public static void ShowResult(int result_code)
    {
        if(result_code==202) {
            //result.setText("WHITE WON!");
            receiveMessage.setText("白子胜利");
            Checkerboard.isFinish=true;
            //Finish_Game();
            LockChess();
        }
        else if(result_code==201) {
            //result.setText("BLACK WON!");
            receiveMessage.setText("黑子胜利");
            Checkerboard.isFinish=true;
            //Finish_Game();
            LockChess();
        }
        else if(result_code==203)
        {
            if(TurnTo==CHESS_BLACK) {
                //result.setText("BLACK TURN");
                receiveMessage.setText("黑子执棋");
            } else if(TurnTo==CHESS_WHITE) {
                //result.setText("WHITE TURN");
                receiveMessage.setText("白子执棋");
            }
        }else {
            //result.setText("");
            receiveMessage.setText("");
        }
    }

    private void InitArrow() {

        arrow_upandleft= (ImageView) findViewById(R.id.arrow_upandleft);
        arrow_upandright= (ImageView) findViewById(R.id.arrow_upandright);
        arrow_leftandup= (ImageView) findViewById(R.id.arrow_leftandup);
        arrow_leftanddown= (ImageView) findViewById(R.id.arrow_leftanddown);
        arrow_rightandup= (ImageView) findViewById(R.id.arrow_rightandup);
        arrow_rightanddown= (ImageView) findViewById(R.id.arrow_rightanddown);
        arrow_downandleft= (ImageView) findViewById(R.id.arrow_downandleft);
        arrow_downandright= (ImageView) findViewById(R.id.arrow_downandright);

        ClearArrow();

        Arrow_listener();

    }

    private void Arrow_listener() {

        arrow_upandleft.setOnClickListener(this);
        arrow_upandright.setOnClickListener(this);
        arrow_leftandup.setOnClickListener(this);
        arrow_leftanddown.setOnClickListener(this);
        arrow_rightandup.setOnClickListener(this);
        arrow_rightanddown.setOnClickListener(this);
        arrow_downandleft.setOnClickListener(this);
        arrow_downandright.setOnClickListener(this);
    }

    private static void ClearArrow()
    {
        arrow_upandleft.setAlpha(0.0f);
        arrow_upandright.setAlpha(0.0f);
        arrow_leftandup.setAlpha(0.0f);
        arrow_leftanddown.setAlpha(0.0f);
        arrow_rightandup.setAlpha(0.0f);
        arrow_rightanddown.setAlpha(0.0f);
        arrow_downandleft.setAlpha(0.0f);
        arrow_downandright.setAlpha(0.0f);

        arrow_upandleft.setEnabled(false);
        arrow_leftandup.setEnabled(false);
        arrow_upandright.setEnabled(false);
        arrow_rightandup.setEnabled(false);
        arrow_leftanddown.setEnabled(false);
        arrow_downandleft.setEnabled(false);
        arrow_rightanddown.setEnabled(false);
        arrow_downandright.setEnabled(false);
    }

    private void Hide_arrow()
    {
        Animation alpha=new AlphaAnimation(1.0f,0.0f);
        alpha.setFillAfter(true);
        alpha.setDuration(500);

        arrow_upandleft.startAnimation(alpha);
        arrow_leftandup.startAnimation(alpha);

        arrow_upandright.startAnimation(alpha);
        arrow_rightandup.startAnimation(alpha);

        arrow_leftanddown.startAnimation(alpha);
        arrow_downandleft.startAnimation(alpha);

        arrow_rightanddown.startAnimation(alpha);
        arrow_downandright.startAnimation(alpha);

        arrow_upandleft.setEnabled(false);
        arrow_leftandup.setEnabled(false);
        arrow_upandright.setEnabled(false);
        arrow_rightandup.setEnabled(false);
        arrow_leftanddown.setEnabled(false);
        arrow_downandleft.setEnabled(false);
        arrow_rightanddown.setEnabled(false);
        arrow_downandright.setEnabled(false);

    }

    private void Show_arrow()
    {
        Animation alpha=new AlphaAnimation(0.0f,1.0f);
        alpha.setFillAfter(true);
        alpha.setDuration(500);

        arrow_upandleft.startAnimation(alpha);
        arrow_leftandup.startAnimation(alpha);
        arrow_upandleft.setAlpha(1.0f);
        arrow_leftandup.setAlpha(1.0f);

        arrow_upandright.startAnimation(alpha);
        arrow_rightandup.startAnimation(alpha);
        arrow_upandright.setAlpha(1.0f);
        arrow_rightandup.setAlpha(1.0f);

        arrow_leftanddown.startAnimation(alpha);
        arrow_downandleft.startAnimation(alpha);
        arrow_leftanddown.setAlpha(1.0f);
        arrow_downandleft.setAlpha(1.0f);

        arrow_rightanddown.startAnimation(alpha);
        arrow_downandright.startAnimation(alpha);
        arrow_rightanddown.setAlpha(1.0f);
        arrow_downandright.setAlpha(1.0f);

        arrow_upandleft.setEnabled(true);
        arrow_leftandup.setEnabled(true);
        arrow_upandright.setEnabled(true);
        arrow_rightandup.setEnabled(true);
        arrow_leftanddown.setEnabled(true);
        arrow_downandleft.setEnabled(true);
        arrow_rightanddown.setEnabled(true);
        arrow_downandright.setEnabled(true);

    }

    //胜负判定结束之后消除事件
    public static void Finish_Game()
    {
        chess_1_1_1.setEnabled(false);
        chess_1_1_2.setEnabled(false);
        chess_1_1_3.setEnabled(false);
        chess_1_2_1.setEnabled(false);
        chess_1_2_2.setEnabled(false);
        chess_1_2_3.setEnabled(false);
        chess_1_3_1.setEnabled(false);
        chess_1_3_2.setEnabled(false);
        chess_1_3_3.setEnabled(false);

        chess_2_1_1.setEnabled(false);
        chess_2_1_2.setEnabled(false);
        chess_2_1_3.setEnabled(false);
        chess_2_2_1.setEnabled(false);
        chess_2_2_2.setEnabled(false);
        chess_2_2_3.setEnabled(false);
        chess_2_3_1.setEnabled(false);
        chess_2_3_2.setEnabled(false);
        chess_2_3_3.setEnabled(false);

        chess_3_1_1.setEnabled(false);
        chess_3_1_2.setEnabled(false);
        chess_3_1_3.setEnabled(false);
        chess_3_2_1.setEnabled(false);
        chess_3_2_2.setEnabled(false);
        chess_3_2_3.setEnabled(false);
        chess_3_3_1.setEnabled(false);
        chess_3_3_2.setEnabled(false);
        chess_3_3_3.setEnabled(false);

        chess_4_1_1.setEnabled(false);
        chess_4_1_2.setEnabled(false);
        chess_4_1_3.setEnabled(false);
        chess_4_2_1.setEnabled(false);
        chess_4_2_2.setEnabled(false);
        chess_4_2_3.setEnabled(false);
        chess_4_3_1.setEnabled(false);
        chess_4_3_2.setEnabled(false);
        chess_4_3_3.setEnabled(false);
    }

    //新建游戏后启动事件
    public static void Start_Game()
    {
        chess_1_1_1.setEnabled(true);
        chess_1_1_2.setEnabled(true);
        chess_1_1_3.setEnabled(true);
        chess_1_2_1.setEnabled(true);
        chess_1_2_2.setEnabled(true);
        chess_1_2_3.setEnabled(true);
        chess_1_3_1.setEnabled(true);
        chess_1_3_2.setEnabled(true);
        chess_1_3_3.setEnabled(true);

        chess_2_1_1.setEnabled(true);
        chess_2_1_2.setEnabled(true);
        chess_2_1_3.setEnabled(true);
        chess_2_2_1.setEnabled(true);
        chess_2_2_2.setEnabled(true);
        chess_2_2_3.setEnabled(true);
        chess_2_3_1.setEnabled(true);
        chess_2_3_2.setEnabled(true);
        chess_2_3_3.setEnabled(true);

        chess_3_1_1.setEnabled(true);
        chess_3_1_2.setEnabled(true);
        chess_3_1_3.setEnabled(true);
        chess_3_2_1.setEnabled(true);
        chess_3_2_2.setEnabled(true);
        chess_3_2_3.setEnabled(true);
        chess_3_3_1.setEnabled(true);
        chess_3_3_2.setEnabled(true);
        chess_3_3_3.setEnabled(true);

        chess_4_1_1.setEnabled(true);
        chess_4_1_2.setEnabled(true);
        chess_4_1_3.setEnabled(true);
        chess_4_2_1.setEnabled(true);
        chess_4_2_2.setEnabled(true);
        chess_4_2_3.setEnabled(true);
        chess_4_3_1.setEnabled(true);
        chess_4_3_2.setEnabled(true);
        chess_4_3_3.setEnabled(true);
    }

    //初始化棋子
    private void InitChess() {

        chess_1_1_1= (ImageView) findViewById(R.id.chess_1_1_1);
        chess_1_1_2= (ImageView) findViewById(R.id.chess_1_1_2);
        chess_1_1_3= (ImageView) findViewById(R.id.chess_1_1_3);
        chess_1_2_1= (ImageView) findViewById(R.id.chess_1_2_1);
        chess_1_2_2= (ImageView) findViewById(R.id.chess_1_2_2);
        chess_1_2_3= (ImageView) findViewById(R.id.chess_1_2_3);
        chess_1_3_1= (ImageView) findViewById(R.id.chess_1_3_1);
        chess_1_3_2= (ImageView) findViewById(R.id.chess_1_3_2);
        chess_1_3_3= (ImageView) findViewById(R.id.chess_1_3_3);

        chess_2_1_1= (ImageView) findViewById(R.id.chess_2_1_1);
        chess_2_1_2= (ImageView) findViewById(R.id.chess_2_1_2);
        chess_2_1_3= (ImageView) findViewById(R.id.chess_2_1_3);
        chess_2_2_1= (ImageView) findViewById(R.id.chess_2_2_1);
        chess_2_2_2= (ImageView) findViewById(R.id.chess_2_2_2);
        chess_2_2_3= (ImageView) findViewById(R.id.chess_2_2_3);
        chess_2_3_1= (ImageView) findViewById(R.id.chess_2_3_1);
        chess_2_3_2= (ImageView) findViewById(R.id.chess_2_3_2);
        chess_2_3_3= (ImageView) findViewById(R.id.chess_2_3_3);

        chess_3_1_1= (ImageView) findViewById(R.id.chess_3_1_1);
        chess_3_1_2= (ImageView) findViewById(R.id.chess_3_1_2);
        chess_3_1_3= (ImageView) findViewById(R.id.chess_3_1_3);
        chess_3_2_1= (ImageView) findViewById(R.id.chess_3_2_1);
        chess_3_2_2= (ImageView) findViewById(R.id.chess_3_2_2);
        chess_3_2_3= (ImageView) findViewById(R.id.chess_3_2_3);
        chess_3_3_1= (ImageView) findViewById(R.id.chess_3_3_1);
        chess_3_3_2= (ImageView) findViewById(R.id.chess_3_3_2);
        chess_3_3_3= (ImageView) findViewById(R.id.chess_3_3_3);

        chess_4_1_1= (ImageView) findViewById(R.id.chess_4_1_1);
        chess_4_1_2= (ImageView) findViewById(R.id.chess_4_1_2);
        chess_4_1_3= (ImageView) findViewById(R.id.chess_4_1_3);
        chess_4_2_1= (ImageView) findViewById(R.id.chess_4_2_1);
        chess_4_2_2= (ImageView) findViewById(R.id.chess_4_2_2);
        chess_4_2_3= (ImageView) findViewById(R.id.chess_4_2_3);
        chess_4_3_1= (ImageView) findViewById(R.id.chess_4_3_1);
        chess_4_3_2= (ImageView) findViewById(R.id.chess_4_3_2);
        chess_4_3_3= (ImageView) findViewById(R.id.chess_4_3_3);

        ClearChess();
        chess_listener();
    }

    //设置棋子事件
    private void chess_listener() {

        chess_1_1_1.setOnClickListener(this);
        chess_1_1_2.setOnClickListener(this);
        chess_1_1_3.setOnClickListener(this);
        chess_1_2_1.setOnClickListener(this);
        chess_1_2_2.setOnClickListener(this);
        chess_1_2_3.setOnClickListener(this);
        chess_1_3_1.setOnClickListener(this);
        chess_1_3_2.setOnClickListener(this);
        chess_1_3_3.setOnClickListener(this);
        chess_2_1_1.setOnClickListener(this);
        chess_2_1_2.setOnClickListener(this);
        chess_2_1_3.setOnClickListener(this);
        chess_2_2_1.setOnClickListener(this);
        chess_2_2_2.setOnClickListener(this);
        chess_2_2_3.setOnClickListener(this);
        chess_2_3_1.setOnClickListener(this);
        chess_2_3_2.setOnClickListener(this);
        chess_2_3_3.setOnClickListener(this);
        chess_3_1_1.setOnClickListener(this);
        chess_3_1_2.setOnClickListener(this);
        chess_3_1_3.setOnClickListener(this);
        chess_3_2_1.setOnClickListener(this);
        chess_3_2_2.setOnClickListener(this);
        chess_3_2_3.setOnClickListener(this);
        chess_3_3_1.setOnClickListener(this);
        chess_3_3_2.setOnClickListener(this);
        chess_3_3_3.setOnClickListener(this);
        chess_4_1_1.setOnClickListener(this);
        chess_4_1_2.setOnClickListener(this);
        chess_4_1_3.setOnClickListener(this);
        chess_4_2_1.setOnClickListener(this);
        chess_4_2_2.setOnClickListener(this);
        chess_4_2_3.setOnClickListener(this);
        chess_4_3_1.setOnClickListener(this);
        chess_4_3_2.setOnClickListener(this);
        chess_4_3_3.setOnClickListener(this);
    }

    private void ClearChess()
    {
        chess_1_1_1.setAlpha(0.0f);
        chess_1_1_2.setAlpha(0.0f);
        chess_1_1_3.setAlpha(0.0f);
        chess_1_2_1.setAlpha(0.0f);
        chess_1_2_2.setAlpha(0.0f);
        chess_1_2_3.setAlpha(0.0f);
        chess_1_3_1.setAlpha(0.0f);
        chess_1_3_2.setAlpha(0.0f);
        chess_1_3_3.setAlpha(0.0f);
        chess_2_1_1.setAlpha(0.0f);
        chess_2_1_2.setAlpha(0.0f);
        chess_2_1_3.setAlpha(0.0f);
        chess_2_2_1.setAlpha(0.0f);
        chess_2_2_2.setAlpha(0.0f);
        chess_2_2_3.setAlpha(0.0f);
        chess_2_3_1.setAlpha(0.0f);
        chess_2_3_2.setAlpha(0.0f);
        chess_2_3_3.setAlpha(0.0f);
        chess_3_1_1.setAlpha(0.0f);
        chess_3_1_2.setAlpha(0.0f);
        chess_3_1_3.setAlpha(0.0f);
        chess_3_2_1.setAlpha(0.0f);
        chess_3_2_2.setAlpha(0.0f);
        chess_3_2_3.setAlpha(0.0f);
        chess_3_3_1.setAlpha(0.0f);
        chess_3_3_2.setAlpha(0.0f);
        chess_3_3_3.setAlpha(0.0f);
        chess_4_1_1.setAlpha(0.0f);
        chess_4_1_2.setAlpha(0.0f);
        chess_4_1_3.setAlpha(0.0f);
        chess_4_2_1.setAlpha(0.0f);
        chess_4_2_2.setAlpha(0.0f);
        chess_4_2_3.setAlpha(0.0f);
        chess_4_3_1.setAlpha(0.0f);
        chess_4_3_2.setAlpha(0.0f);
        chess_4_3_3.setAlpha(0.0f);

    }

    public static void LockaChess(ImageView chess)
    {
        chess.setEnabled(false);
    }

    public static void LockChess()
    {
        chess_1_1_1.setEnabled(false);
        chess_1_1_2.setEnabled(false);
        chess_1_1_3.setEnabled(false);
        chess_1_2_1.setEnabled(false);
        chess_1_2_2.setEnabled(false);
        chess_1_2_3.setEnabled(false);
        chess_1_3_1.setEnabled(false);
        chess_1_3_2.setEnabled(false);
        chess_1_3_3.setEnabled(false);

        chess_2_1_1.setEnabled(false);
        chess_2_1_2.setEnabled(false);
        chess_2_1_3.setEnabled(false);
        chess_2_2_1.setEnabled(false);
        chess_2_2_2.setEnabled(false);
        chess_2_2_3.setEnabled(false);
        chess_2_3_1.setEnabled(false);
        chess_2_3_2.setEnabled(false);
        chess_2_3_3.setEnabled(false);

        chess_3_1_1.setEnabled(false);
        chess_3_1_2.setEnabled(false);
        chess_3_1_3.setEnabled(false);
        chess_3_2_1.setEnabled(false);
        chess_3_2_2.setEnabled(false);
        chess_3_2_3.setEnabled(false);
        chess_3_3_1.setEnabled(false);
        chess_3_3_2.setEnabled(false);
        chess_3_3_3.setEnabled(false);

        chess_4_1_1.setEnabled(false);
        chess_4_1_2.setEnabled(false);
        chess_4_1_3.setEnabled(false);
        chess_4_2_1.setEnabled(false);
        chess_4_2_2.setEnabled(false);
        chess_4_2_3.setEnabled(false);
        chess_4_3_1.setEnabled(false);
        chess_4_3_2.setEnabled(false);
        chess_4_3_3.setEnabled(false);
    }

    public static void LockArrow()
    {
        arrow_upandleft.setEnabled(false);
        arrow_leftandup.setEnabled(false);
        arrow_upandright.setEnabled(false);
        arrow_rightandup.setEnabled(false);
        arrow_leftanddown.setEnabled(false);
        arrow_downandleft.setEnabled(false);
        arrow_rightanddown.setEnabled(false);
        arrow_downandright.setEnabled(false);
    }

    public static void UnLockaChess(ImageView chess)
    {
        chess.setEnabled(true);
    }

    public static void UnLockChess()
    {//System.out.println("UnLockChess");
        if(checkerboard.getItem(1,1)==0)
            chess_1_1_1.setEnabled(true);
        if(checkerboard.getItem(1,2)==0)
            chess_1_1_2.setEnabled(true);
        if(checkerboard.getItem(1,3)==0)
            chess_1_1_3.setEnabled(true);
        if(checkerboard.getItem(2,1)==0)
            chess_1_2_1.setEnabled(true);
        if(checkerboard.getItem(2,2)==0)
            chess_1_2_2.setEnabled(true);
        if(checkerboard.getItem(2,3)==0)
            chess_1_2_3.setEnabled(true);
        if(checkerboard.getItem(3,1)==0)
            chess_1_3_1.setEnabled(true);
        if(checkerboard.getItem(3,2)==0)
            chess_1_3_2.setEnabled(true);
        if(checkerboard.getItem(3,3)==0)
            chess_1_3_3.setEnabled(true);

        if(checkerboard.getItem(1,4)==0)
            chess_2_1_1.setEnabled(true);
        if(checkerboard.getItem(1,5)==0)
            chess_2_1_2.setEnabled(true);
        if(checkerboard.getItem(1,6)==0)
            chess_2_1_3.setEnabled(true);
        if(checkerboard.getItem(2,4)==0)
            chess_2_2_1.setEnabled(true);
        if(checkerboard.getItem(2,5)==0)
            chess_2_2_2.setEnabled(true);
        if(checkerboard.getItem(2,6)==0)
            chess_2_2_3.setEnabled(true);
        if(checkerboard.getItem(3,4)==0)
            chess_2_3_1.setEnabled(true);
        if(checkerboard.getItem(3,5)==0)
            chess_2_3_2.setEnabled(true);
        if(checkerboard.getItem(3,6)==0)
            chess_2_3_3.setEnabled(true);

        if(checkerboard.getItem(4,1)==0)
            chess_3_1_1.setEnabled(true);
        if(checkerboard.getItem(4,2)==0)
            chess_3_1_2.setEnabled(true);
        if(checkerboard.getItem(4,3)==0)
            chess_3_1_3.setEnabled(true);
        if(checkerboard.getItem(5,1)==0)
            chess_3_2_1.setEnabled(true);
        if(checkerboard.getItem(5,2)==0)
            chess_3_2_2.setEnabled(true);
        if(checkerboard.getItem(5,3)==0)
            chess_3_2_3.setEnabled(true);
        if(checkerboard.getItem(6,1)==0)
            chess_3_3_1.setEnabled(true);
        if(checkerboard.getItem(6,2)==0)
            chess_3_3_2.setEnabled(true);
        if(checkerboard.getItem(6,3)==0)
            chess_3_3_3.setEnabled(true);

        if(checkerboard.getItem(4,4)==0)
            chess_4_1_1.setEnabled(true);
        if(checkerboard.getItem(4,5)==0)
            chess_4_1_2.setEnabled(true);
        if(checkerboard.getItem(4,6)==0)
            chess_4_1_3.setEnabled(true);
        if(checkerboard.getItem(5,4)==0)
            chess_4_2_1.setEnabled(true);
        if(checkerboard.getItem(5,5)==0)
            chess_4_2_2.setEnabled(true);
        if(checkerboard.getItem(5,6)==0)
            chess_4_2_3.setEnabled(true);
        if(checkerboard.getItem(6,4)==0)
            chess_4_3_1.setEnabled(true);
        if(checkerboard.getItem(6,5)==0)
            chess_4_3_2.setEnabled(true);
        if(checkerboard.getItem(6,6)==0)
            chess_4_3_3.setEnabled(true);

    }

    public static void UnLockArrow()
    {
        arrow_upandleft.setEnabled(true);
        arrow_leftandup.setEnabled(true);
        arrow_upandright.setEnabled(true);
        arrow_rightandup.setEnabled(true);
        arrow_leftanddown.setEnabled(true);
        arrow_downandleft.setEnabled(true);
        arrow_rightanddown.setEnabled(true);
        arrow_downandright.setEnabled(true);
    }

    public static void GoBlack(int line, int row,int block)
    {
        switch (block) {
            case 1:
                switch (line) {
                    case 1:
                        switch (row) {
                            case 1:
                                GoBlack(chess_1_1_1);
                                LockaChess(chess_1_1_1);
                                break;
                            case 2:
                                GoBlack(chess_1_1_2);
                                LockaChess(chess_1_1_2);
                                break;
                            case 3:
                                GoBlack(chess_1_1_3);
                                LockaChess(chess_1_1_3);
                                break;
                        }
                        break;
                    case 2:
                        switch (row) {
                            case 1:
                                GoBlack(chess_1_2_1);
                                LockaChess(chess_1_2_1);
                                break;
                            case 2:
                                GoBlack(chess_1_2_2);
                                LockaChess(chess_1_2_2);
                                break;
                            case 3:
                                GoBlack(chess_1_2_3);
                                LockaChess(chess_1_2_3);
                                break;
                        }
                        break;
                    case 3:
                        switch (row) {
                            case 1:
                                GoBlack(chess_1_3_1);
                                LockaChess(chess_1_3_1);
                                break;
                            case 2:
                                GoBlack(chess_1_3_2);
                                LockaChess(chess_1_3_2);
                                break;
                            case 3:
                                GoBlack(chess_1_3_3);
                                LockaChess(chess_1_3_3);
                                break;
                        }
                        break;
                }
                break;
            case 2:
                switch (line) {
                    case 1:
                        switch (row) {
                            case 1:
                                GoBlack(chess_2_1_1);
                                LockaChess(chess_2_1_1);
                                break;
                            case 2:
                                GoBlack(chess_2_1_2);
                                LockaChess(chess_2_1_2);
                                break;
                            case 3:
                                GoBlack(chess_2_1_3);
                                LockaChess(chess_2_1_3);
                                break;
                        }
                        break;
                    case 2:
                        switch (row) {
                            case 1:
                                GoBlack(chess_2_2_1);
                                LockaChess(chess_2_2_1);
                                break;
                            case 2:
                                GoBlack(chess_2_2_2);
                                LockaChess(chess_2_2_2);
                                break;
                            case 3:
                                GoBlack(chess_2_2_3);
                                LockaChess(chess_2_2_3);
                                break;
                        }
                        break;
                    case 3:
                        switch (row) {
                            case 1:
                                GoBlack(chess_2_3_1);
                                LockaChess(chess_2_3_1);
                                break;
                            case 2:
                                GoBlack(chess_2_3_2);
                                LockaChess(chess_2_3_2);
                                break;
                            case 3:
                                GoBlack(chess_2_3_3);
                                LockaChess(chess_2_3_3);
                                break;
                        }
                        break;
                }
                break;
            case 3:
                switch (line) {
                    case 1:
                        switch (row) {
                            case 1:
                                GoBlack(chess_3_1_1);
                                LockaChess(chess_3_1_1);
                                break;
                            case 2:
                                GoBlack(chess_3_1_2);
                                LockaChess(chess_3_1_2);
                                break;
                            case 3:
                                GoBlack(chess_3_1_3);
                                LockaChess(chess_3_1_3);
                                break;
                        }
                        break;
                    case 2:
                        switch (row) {
                            case 1:
                                GoBlack(chess_3_2_1);
                                LockaChess(chess_3_2_1);
                                break;
                            case 2:
                                GoBlack(chess_3_2_2);
                                LockaChess(chess_3_2_2);
                                break;
                            case 3:
                                GoBlack(chess_3_2_3);
                                LockaChess(chess_3_2_3);
                                break;
                        }
                        break;
                    case 3:
                        switch (row) {
                            case 1:
                                GoBlack(chess_3_3_1);
                                LockaChess(chess_3_3_1);
                                break;
                            case 2:
                                GoBlack(chess_3_3_2);
                                LockaChess(chess_3_3_2);
                                break;
                            case 3:
                                GoBlack(chess_3_3_3);
                                LockaChess(chess_3_3_3);
                                break;
                        }
                        break;
                }
                break;
            case 4:
                switch (line) {
                    case 1:
                        switch (row) {
                            case 1:
                                GoBlack(chess_4_1_1);
                                LockaChess(chess_4_1_1);
                                break;
                            case 2:
                                GoBlack(chess_4_1_2);
                                LockaChess(chess_4_1_2);
                                break;
                            case 3:
                                GoBlack(chess_4_1_3);
                                LockaChess(chess_4_1_3);
                                break;
                        }
                        break;
                    case 2:
                        switch (row) {
                            case 1:
                                GoBlack(chess_4_2_1);
                                LockaChess(chess_4_2_1);
                                break;
                            case 2:
                                GoBlack(chess_4_2_2);
                                LockaChess(chess_4_2_2);
                                break;
                            case 3:
                                GoBlack(chess_4_2_3);
                                LockaChess(chess_4_2_3);
                                break;
                        }
                        break;
                    case 3:
                        switch (row) {
                            case 1:
                                GoBlack(chess_4_3_1);
                                LockaChess(chess_4_3_1);
                                break;
                            case 2:
                                GoBlack(chess_4_3_2);
                                LockaChess(chess_4_3_2);
                                break;
                            case 3:
                                GoBlack(chess_4_3_3);
                                LockaChess(chess_4_3_3);
                                break;
                        }
                        break;
                }
                break;

        }


    }

    public static void GoWhite(int line, int row,int block)
    {
        switch (block) {
            case 1:
                switch (line) {
                    case 1:
                        switch (row) {
                            case 1:
                                GoWhite(chess_1_1_1);
                                LockaChess(chess_1_1_1);
                                break;
                            case 2:
                                GoWhite(chess_1_1_2);
                                LockaChess(chess_1_1_2);
                                break;
                            case 3:
                                GoWhite(chess_1_1_3);
                                LockaChess(chess_1_1_3);
                                break;
                        }
                        break;
                    case 2:
                        switch (row) {
                            case 1:
                                GoWhite(chess_1_2_1);
                                LockaChess(chess_1_2_1);
                                break;
                            case 2:
                                GoWhite(chess_1_2_2);
                                LockaChess(chess_1_2_2);
                                break;
                            case 3:
                                GoWhite(chess_1_2_3);
                                LockaChess(chess_1_2_3);
                                break;
                        }
                        break;
                    case 3:
                        switch (row) {
                            case 1:
                                GoWhite(chess_1_3_1);
                                LockaChess(chess_1_3_1);
                                break;
                            case 2:
                                GoWhite(chess_1_3_2);
                                LockaChess(chess_1_3_2);
                                break;
                            case 3:
                                GoWhite(chess_1_3_3);
                                LockaChess(chess_1_3_3);
                                break;
                        }
                        break;
                }
                break;
            case 2:
                switch (line) {
                    case 1:
                        switch (row) {
                            case 1:
                                GoWhite(chess_2_1_1);
                                LockaChess(chess_2_1_1);
                                break;
                            case 2:
                                GoWhite(chess_2_1_2);
                                LockaChess(chess_2_1_2);
                                break;
                            case 3:
                                GoWhite(chess_2_1_3);
                                LockaChess(chess_2_1_3);
                                break;
                        }
                        break;
                    case 2:
                        switch (row) {
                            case 1:
                                GoWhite(chess_2_2_1);
                                LockaChess(chess_2_2_1);
                                break;
                            case 2:
                                GoWhite(chess_2_2_2);
                                LockaChess(chess_2_2_2);
                                break;
                            case 3:
                                GoWhite(chess_2_2_3);
                                LockaChess(chess_2_2_3);
                                break;
                        }
                        break;
                    case 3:
                        switch (row) {
                            case 1:
                                GoWhite(chess_2_3_1);
                                LockaChess(chess_2_3_1);
                                break;
                            case 2:
                                GoWhite(chess_2_3_2);
                                LockaChess(chess_2_3_2);
                                break;
                            case 3:
                                GoWhite(chess_2_3_3);
                                LockaChess(chess_2_3_3);
                                break;
                        }
                        break;
                }
                break;
            case 3:
                switch (line) {
                    case 1:
                        switch (row) {
                            case 1:
                                GoWhite(chess_3_1_1);
                                LockaChess(chess_3_1_1);
                                break;
                            case 2:
                                GoWhite(chess_3_1_2);
                                LockaChess(chess_3_1_2);
                                break;
                            case 3:
                                GoWhite(chess_3_1_3);
                                LockaChess(chess_3_1_3);
                                break;
                        }
                        break;
                    case 2:
                        switch (row) {
                            case 1:
                                GoWhite(chess_3_2_1);
                                LockaChess(chess_3_2_1);
                                break;
                            case 2:
                                GoWhite(chess_3_2_2);
                                LockaChess(chess_3_2_2);
                                break;
                            case 3:
                                GoWhite(chess_3_2_3);
                                LockaChess(chess_3_2_3);
                                break;
                        }
                        break;
                    case 3:
                        switch (row) {
                            case 1:
                                GoWhite(chess_3_3_1);
                                LockaChess(chess_3_3_1);
                                break;
                            case 2:
                                GoWhite(chess_3_3_2);
                                LockaChess(chess_3_3_2);
                                break;
                            case 3:
                                GoWhite(chess_3_3_3);
                                LockaChess(chess_3_3_3);
                                break;
                        }
                        break;
                }
                break;
            case 4:
                switch (line) {
                    case 1:
                        switch (row) {
                            case 1:
                                GoWhite(chess_4_1_1);
                                LockaChess(chess_4_1_1);
                                break;
                            case 2:
                                GoWhite(chess_4_1_2);
                                LockaChess(chess_4_1_2);
                                break;
                            case 3:
                                GoWhite(chess_4_1_3);
                                LockaChess(chess_4_1_3);
                                break;
                        }
                        break;
                    case 2:
                        switch (row) {
                            case 1:
                                GoWhite(chess_4_2_1);
                                LockaChess(chess_4_2_1);
                                break;
                            case 2:
                                GoWhite(chess_4_2_2);
                                LockaChess(chess_4_2_2);
                                break;
                            case 3:
                                GoWhite(chess_4_2_3);
                                LockaChess(chess_4_2_3);
                                break;
                        }
                        break;
                    case 3:
                        switch (row) {
                            case 1:
                                GoWhite(chess_4_3_1);
                                LockaChess(chess_4_3_1);
                                break;
                            case 2:
                                GoWhite(chess_4_3_2);
                                LockaChess(chess_4_3_2);
                                break;
                            case 3:
                                GoWhite(chess_4_3_3);
                                LockaChess(chess_4_3_3);
                                break;
                        }
                        break;
                }
                break;

        }
    }

    public static void UnGo(int line, int row, int block)
    {
        switch (block) {
            case 1:
                switch (line) {
                    case 1:
                        switch (row) {
                            case 1:
                                UnGo(chess_1_1_1);
                                UnLockaChess(chess_1_1_1);
                                break;
                            case 2:
                                UnGo(chess_1_1_2);
                                UnLockaChess(chess_1_1_2);
                                break;
                            case 3:
                                UnGo(chess_1_1_3);
                                UnLockaChess(chess_1_1_3);
                                break;
                        }
                        break;
                    case 2:
                        switch (row) {
                            case 1:
                                UnGo(chess_1_2_1);
                                UnLockaChess(chess_1_2_1);
                                break;
                            case 2:
                                UnGo(chess_1_2_2);
                                UnLockaChess(chess_1_2_2);
                                break;
                            case 3:
                                UnGo(chess_1_2_3);
                                UnLockaChess(chess_1_2_3);
                                break;
                        }
                        break;
                    case 3:
                        switch (row) {
                            case 1:
                                UnGo(chess_1_3_1);
                                UnLockaChess(chess_1_3_1);
                                break;
                            case 2:
                                UnGo(chess_1_3_2);
                                UnLockaChess(chess_1_3_2);
                                break;
                            case 3:
                                UnGo(chess_1_3_3);
                                UnLockaChess(chess_1_3_3);
                                break;
                        }
                        break;
                }
                break;
            case 2:
                switch (line) {
                    case 1:
                        switch (row) {
                            case 1:
                                UnGo(chess_2_1_1);
                                UnLockaChess(chess_2_1_1);
                                break;
                            case 2:
                                UnGo(chess_2_1_2);
                                UnLockaChess(chess_2_1_2);
                                break;
                            case 3:
                                UnGo(chess_2_1_3);
                                UnLockaChess(chess_2_1_3);
                                break;
                        }
                        break;
                    case 2:
                        switch (row) {
                            case 1:
                                UnGo(chess_2_2_1);
                                UnLockaChess(chess_2_2_1);
                                break;
                            case 2:
                                UnGo(chess_2_2_2);
                                UnLockaChess(chess_2_2_2);
                                break;
                            case 3:
                                UnGo(chess_2_2_3);
                                UnLockaChess(chess_2_2_3);
                                break;
                        }
                        break;
                    case 3:
                        switch (row) {
                            case 1:
                                UnGo(chess_2_3_1);
                                UnLockaChess(chess_2_3_1);
                                break;
                            case 2:
                                UnGo(chess_2_3_2);
                                UnLockaChess(chess_2_3_2);
                                break;
                            case 3:
                                UnGo(chess_2_3_3);
                                UnLockaChess(chess_2_3_3);
                                break;
                        }
                        break;
                }
                break;
            case 3:
                switch (line) {
                    case 1:
                        switch (row) {
                            case 1:
                                UnGo(chess_3_1_1);
                                UnLockaChess(chess_3_1_1);
                                break;
                            case 2:
                                UnGo(chess_3_1_2);
                                UnLockaChess(chess_3_1_2);
                                break;
                            case 3:
                                UnGo(chess_3_1_3);
                                UnLockaChess(chess_3_1_3);
                                break;
                        }
                        break;
                    case 2:
                        switch (row) {
                            case 1:
                                UnGo(chess_3_2_1);
                                UnLockaChess(chess_3_2_1);
                                break;
                            case 2:
                                UnGo(chess_3_2_2);
                                UnLockaChess(chess_3_2_2);
                                break;
                            case 3:
                                UnGo(chess_3_2_3);
                                UnLockaChess(chess_3_2_3);
                                break;
                        }
                        break;
                    case 3:
                        switch (row) {
                            case 1:
                                UnGo(chess_3_3_1);
                                UnLockaChess(chess_3_3_1);
                                break;
                            case 2:
                                UnGo(chess_3_3_2);
                                UnLockaChess(chess_3_3_2);
                                break;
                            case 3:
                                UnGo(chess_3_3_3);
                                UnLockaChess(chess_3_3_3);
                                break;
                        }
                        break;
                }
                break;
            case 4:
                switch (line) {
                    case 1:
                        switch (row) {
                            case 1:
                                UnGo(chess_4_1_1);
                                UnLockaChess(chess_4_1_1);
                                break;
                            case 2:
                                UnGo(chess_4_1_2);
                                UnLockaChess(chess_4_1_2);
                                break;
                            case 3:
                                UnGo(chess_4_1_3);
                                UnLockaChess(chess_4_1_3);
                                break;
                        }
                        break;
                    case 2:
                        switch (row) {
                            case 1:
                                UnGo(chess_4_2_1);
                                UnLockaChess(chess_4_2_1);
                                break;
                            case 2:
                                UnGo(chess_4_2_2);
                                UnLockaChess(chess_4_2_2);
                                break;
                            case 3:
                                UnGo(chess_4_2_3);
                                UnLockaChess(chess_4_2_3);
                                break;
                        }
                        break;
                    case 3:
                        switch (row) {
                            case 1:
                                UnGo(chess_4_3_1);
                                UnLockaChess(chess_4_3_1);
                                break;
                            case 2:
                                UnGo(chess_4_3_2);
                                UnLockaChess(chess_4_3_2);
                                break;
                            case 3:
                                UnGo(chess_4_3_3);
                                UnLockaChess(chess_4_3_3);
                                break;
                        }
                        break;
                }
                break;

        }
    }

    //AI棋子
    public static ImageView findChess(int line,int row)
    {Log.i("temp","line"+line);
        switch (line)
        {
            case 1:
                switch (row)
                {
                    case 1:return chess_1_1_1;
                    case 2:return chess_1_1_2;
                    case 3:return chess_1_1_3;
                    case 4:return chess_2_1_1;
                    case 5:return chess_2_1_2;
                    case 6:return chess_2_1_3;
                }
            case 2:
                switch (row)
                {
                    case 1:return chess_1_2_1;
                    case 2:return chess_1_2_2;
                    case 3:return chess_1_2_3;
                    case 4:return chess_2_2_1;
                    case 5:return chess_2_2_2;
                    case 6:return chess_2_2_3;
                }
            case 3:
                switch (row)
                {
                    case 1:return chess_1_3_1;
                    case 2:return chess_1_3_2;
                    case 3:return chess_1_3_3;
                    case 4:return chess_2_3_1;
                    case 5:return chess_2_3_2;
                    case 6:return chess_2_3_3;
                }
            case 4:
                switch (row)
                {
                    case 1:return chess_3_1_1;
                    case 2:return chess_3_1_2;
                    case 3:return chess_3_1_3;
                    case 4:return chess_4_1_1;
                    case 5:return chess_4_1_2;
                    case 6:return chess_4_1_3;
                }
            case 5:
                switch (row)
                {
                    case 1:return chess_3_2_1;
                    case 2:return chess_3_2_2;
                    case 3:return chess_3_2_3;
                    case 4:return chess_4_2_1;
                    case 5:return chess_4_2_2;
                    case 6:return chess_4_2_3;
                }
            case 6:
                switch (row)
                {
                    case 1:return chess_3_3_1;
                    case 2:return chess_3_3_2;
                    case 3:return chess_3_3_3;
                    case 4:return chess_4_3_1;
                    case 5:return chess_4_3_2;
                    case 6:return chess_4_3_3;
                }
        }
        return null;
    }

    //AI箭头
    public static ImageView findArrow(int i)
    {
        switch (i)
        {
            case rotate_upandleft:return arrow_upandleft;
            case rotate_upandright:return arrow_upandright;
            case rotate_leftandup:return arrow_leftandup;
            case rotate_leftanddown:return arrow_leftanddown;
            case rotate_rightandup:return arrow_rightandup;
            case rotate_rightanddown:return arrow_rightanddown;
            case rotate_downandleft:return arrow_downandleft;
            case rotate_downandright:return arrow_downandright;
        }
        return null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
/*
            // 创建退出对话框
            AlertDialog isExit = new AlertDialog.Builder(this).create();
            // 设置对话框消息
            isExit.setMessage("sure to exit?");
            // 添加选择按钮并注册监听
            isExit.setButton(DialogInterface.BUTTON_POSITIVE,"yes",listener);
            isExit.setButton(DialogInterface.BUTTON_NEGATIVE,"no",listener);
            // 显示对话框
            isExit.show();
            */
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.exit, null);

            Button bt_exit_yes= (Button) view.findViewById(R.id.bt_exit_yes);
            Button bt_exit_no= (Button) view.findViewById(R.id.bt_exit_no);


            bt_exit_no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog_exit.cancel();
                }
            });

            bt_exit_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //重置棋盘
                    checkerboard=new Checkerboard(context);

                    finish();
                }
            });


            builder.setView(view);

            dialog_exit = builder.create();

            dialog_exit.setCanceledOnTouchOutside(false);

            dialog_exit.setCancelable(false);

            dialog_exit.show();
        }

        return false;

    }

    /**监听对话框里面的button点击事件*/
  /*   DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
    {
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序

                    //重置棋盘
                    checkerboard=new Checkerboard(context);

                    finish();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };*/
}
