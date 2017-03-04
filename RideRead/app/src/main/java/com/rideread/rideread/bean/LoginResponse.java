package com.rideread.rideread.bean;

import android.provider.ContactsContract;

import java.io.Serializable;

/**
 * Created by Jackbing on 2017/2/27.
 * 登录返回的数据
 */

public class LoginResponse implements Serializable{
    private int status;
    private UserData data;
    private long timestamp;

    public LoginResponse(UserData data, int status,long timestamp) {
        this.data = data;
        this.status = status;
        this.timestamp=timestamp;
    }

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
