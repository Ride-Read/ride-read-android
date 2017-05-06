package com.rideread.rideread.data.been;

import com.rideread.rideread.data.result.UserBaseInfo;

/**
 * Created by SkyXiao on 2017/4/21.
 */

public class MsgInfo extends UserBaseInfo {
    private long time;
    private String msg;

    public MsgInfo() {}

    public MsgInfo(UserBaseInfo user, String msg, long time) {
        this.uid = user.getUid();
        this.username = user.getUsername();
        this.faceUrl = user.getFaceUrl();
        this.msg = msg;
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
