package com.shanckenur.pentaballs.judge;

/**
 * 数字转换成坐标
 * Created by mz on 2017/5/15.
 */

public class numTurnCoordinate {
    public static int[] NTC(int coordinate){
        int[] co = new int[3];
        int kindOfChess=0;
        //判断是黑子还是白子
        if(coordinate>0)
            kindOfChess=1;//1代表黑子
        else
            kindOfChess=-1;//-1代表白子
        coordinate=coordinate<0?-coordinate:coordinate;//取绝对值
        coordinate--;
        co[0]=coordinate/6;//获取横坐标
        co[1]=coordinate%6;//获取 纵坐标
        co[2]=kindOfChess;
        return co;
    }
}
