package com.shanckenur.pentaballs.machine;

import android.util.Log;

/**
 * Created by mz on 2017/5/22.
 */

public class roleArray {

    public static int wins[][][];
    private static int count;

    //规则数组初始化
    public static void InitRoleArray()
    {
        wins=new int[6][6][32];

        count = 0;
        //横向赢法统计    0-11
        for(int i=0;i<6;i++){
            for(int j=0;j<2;j++){
                for(int k=0;k<5;k++){
                    wins[i][j+k][count]=1;
                }
                count++;
            }
        }
        //纵向赢法统计    12-23
        for(int i=0;i<6;i++){
            for(int j=0;j<2;j++){
                for(int k=0;k<5;k++){
                    wins[j+k][i][count]=1;
                }
                count++;
            }
        }

        //左上到右下斜线赢法统计   24-27
        for(int i=0;i<2;i++){
            for(int j=0;j<2;j++){
                for(int k=0;k<5;k++){
                    wins[i+k][j+k][count]=1;
                }
                count++;
            }
        }

        //右上到左下斜线赢法统计   28-31
        for(int i=0;i<2;i++){
            for(int j=5;j>3;j--){
                for(int k=0;k<5;k++){
                    wins[i+k][j-k][count]=1;
                }
                count++;
            }
        }

    }
}
