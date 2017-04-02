package com.rideread.rideread.data.result;

import java.io.Serializable;

/**
 * Created by Jackbing on 2017/2/27.
 * 登录返回的用户数据
 */

public class UserBaseInfo implements Serializable {
    private int tid;
    private String faceUrl;
    private String signature;
    private String nickname;

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
