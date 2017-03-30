package com.rideread.rideread.function.net.retrofit;

/**
 * Created by SkyXiao on 2017/1/22.
 */

import java.io.Serializable;

/**
 * 公共的响应体，可以根据自身项目接口的json结构自己定义
 */
public class BaseModel<T> implements Serializable {

    private int status;
    private String msg;
    private long timestamp;
    private T data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
