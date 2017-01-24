package com.rideread.rideread.bean;

/**
 * Created by Jackbing on 2017/1/22.
 */

public class LoginMessageEntity {

    private int resultCode;
    private String msg;

    public LoginMessageEntity(String msg, int resultCode) {
        this.msg = msg;
        this.resultCode = resultCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
}
