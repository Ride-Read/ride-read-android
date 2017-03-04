package com.rideread.rideread.bean;

import java.io.Serializable;

/**
 * Created by Jackbing on 2017/3/4.
 */

public class QiNiuTokenResp implements Serializable{

    private int status;
    private String qiniu_token;

    public String getQiniu_token() {
        return qiniu_token;
    }

    public QiNiuTokenResp(String qiniu_token, int status) {
        this.qiniu_token = qiniu_token;
        this.status = status;
    }

    public void setQiniu_token(String qiniu_token) {
        this.qiniu_token = qiniu_token;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
