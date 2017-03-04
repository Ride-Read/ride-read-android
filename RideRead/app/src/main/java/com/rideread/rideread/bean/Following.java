package com.rideread.rideread.bean;

import java.io.Serializable;

/**
 * Created by Jackbing on 2017/3/4.
 * 关注
 */

public class Following implements Serializable{


    private int uid;
    private String face_url;
    private String signature;
    private String nickname;

    public Following(String face_url, String nickname, String signature, int uid) {
        this.face_url = face_url;
        this.nickname = nickname;
        this.signature = signature;
        this.uid = uid;
    }

    public String getFace_url() {
        return face_url;
    }

    public void setFace_url(String face_url) {
        this.face_url = face_url;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
