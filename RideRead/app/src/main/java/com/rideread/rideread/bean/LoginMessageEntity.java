package com.rideread.rideread.bean;

/**
 * Created by Jackbing on 2017/1/22.
 */

public class LoginMessageEntity {

    private int status;
    private String token;
    private int uid;

    public LoginMessageEntity(int status, String token, int uid) {
        this.status = status;
        this.token = token;
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
