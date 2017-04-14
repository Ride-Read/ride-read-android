package com.rideread.rideread.data.result;

import java.io.Serializable;

/**
 * Created by Jackbing on 2017/2/27.
 * 登录返回的用户数据
 */

public class UserBaseInfo implements Serializable {
    private int uid;
    private String faceUrl;
    private String username;
    private String signature;


    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
