package com.rideread.rideread.data.result;

/**
 * Created by Jackbing on 2017/2/27.
 * 登录返回的用户数据
 */

public class ThumbsUpUser extends UserBaseInfo {
    private int thumbsUpId;
    private String createAt;

    public int getThumbsUpId() {
        return thumbsUpId;
    }

    public void setThumbsUpId(int thumbsUpId) {
        this.thumbsUpId = thumbsUpId;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
