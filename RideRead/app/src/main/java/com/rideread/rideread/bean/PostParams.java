package com.rideread.rideread.bean;

/**
 * Created by Jackbing on 2017/3/7.
 * 每次访问都需要传递的参数
 */

public class PostParams {

    private long timeStmap;
    private String token;
    private int uid;

    public PostParams(){}
    public PostParams(long timeStmap, String token, int uid) {
        this.timeStmap = timeStmap;
        this.token = token;
        this.uid = uid;
    }

    public long getTimeStmap() {
        return timeStmap;
    }

    public void setTimeStmap(long timeStmap) {
        this.timeStmap = timeStmap;
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
}
