package com.rideread.rideread.data.result;

/**
 * Created by Jackbing on 2017/2/27.
 * 登录返回的用户数据
 */

public class ThumbsUpUser extends UserBaseInfo {
    private int thumbsUpId;
    private int mid;
    private String createdAt;
    private String updatedAt;

    public int getThumbsUpId() {
        return thumbsUpId;
    }

    public void setThumbsUpId(int thumbsUpId) {
        this.thumbsUpId = thumbsUpId;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
