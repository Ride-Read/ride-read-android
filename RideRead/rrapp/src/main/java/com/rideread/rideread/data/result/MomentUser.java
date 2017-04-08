package com.rideread.rideread.data.result;

/**
 * Created by Jackbing on 2017/2/27.
 * 登录返回的用户数据
 */

public class MomentUser extends UserBaseInfo {
    private int isFollowed;


    public int getIsFollowed() {
        return isFollowed;
    }

    public void setIsFollowed(int isFollowed) {
        this.isFollowed = isFollowed;
    }
}
