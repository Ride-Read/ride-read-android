package com.rideread.rideread.data.result;

import java.io.Serializable;

/**
 * Created by SkyXiao on 2017/3/31.
 */

public class QiniuToken implements Serializable {
    private String upToken;

    public String getUpToken() {
        return upToken;
    }

    public void setUpToken(String upToken) {
        this.upToken = upToken;
    }
}
