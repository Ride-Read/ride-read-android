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

    public LoginResponse(UserData data, int status) {
        this.data = data;
        this.status = status;
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
}
