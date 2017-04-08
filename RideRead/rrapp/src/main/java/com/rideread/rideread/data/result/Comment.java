package com.rideread.rideread.data.result;

import java.io.Serializable;

/**
 * Created by SkyXiao on 2017/3/31.
 */

public class Comment implements Serializable {
    private int uid;
    private String msg;
    private String reply_nickname;
    private int reply_uid;
    private int comment_id;
    private long createdAt;
    private String nickname;
    private String faceUrl;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getReply_nickname() {
        return reply_nickname;
    }

    public void setReply_nickname(String reply_nickname) {
        this.reply_nickname = reply_nickname;
    }

    public int getReply_uid() {
        return reply_uid;
    }

    public void setReply_uid(int reply_uid) {
        this.reply_uid = reply_uid;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFaceUrl() {
        return faceUrl;
    }

    public void setFaceUrl(String faceUrl) {
        this.faceUrl = faceUrl;
    }
}
