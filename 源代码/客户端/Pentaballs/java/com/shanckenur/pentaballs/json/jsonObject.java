package com.shanckenur.pentaballs.json;

/**
 * Created by mz on 2017/6/1.
 */

public class jsonObject {

    private Integer pkey;//棋盘标记值
    private int state;//状态码
    private int coordinate;//棋子坐标数值
    private int roate;//棋盘旋转方式
    private String message;//消息
    private int setPsw;//是否设置密码
    private int roomNumber;//房间号
    private int psw;//房间密码

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber=roomNumber;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Integer getPkey() {
        return pkey;
    }
    public void setPkey(Integer pkey) {
        this.pkey = pkey;
    }
    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public int getCoordinate() {
        return coordinate;
    }
    public void setCoordinate(int coordinate) {
        this.coordinate = coordinate;
    }
    public int getRoate() {
        return roate;
    }
    public void setRoate(int roate) {
        this.roate = roate;
    }

    public int getSetPsw() {
        return setPsw;
    }

    public void setSetPsw(int setPsw) {
        this.setPsw = setPsw;
    }

    public int getPsw() {
        return psw;
    }

    public void setPsw(int psw) {
        this.psw = psw;
    }
}
