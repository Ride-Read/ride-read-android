package com.rideread.rideread.data.result;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by Jackbing on 2017/2/27.
 * 登录返回的用户数据
 */

public class FollowUser implements Serializable {
    private int fid;
    private int tid;
    private String followedFaceUrl;
    private String followedUsername;
    private String followedSignature;
    private String followerFaceUrl;
    private String followerUsername;
    private String followerSignature;


    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getFollowedFaceUrl() {
        return followedFaceUrl;
    }

    public void setFollowedFaceUrl(String followedFaceUrl) {
        this.followedFaceUrl = followedFaceUrl;
    }

    public String getFollowedUsername() {
        return followedUsername;
    }

    public void setFollowedUsername(String followedUsername) {
        this.followedUsername = followedUsername;
    }

    public String getFollowedSignature() {
        if (TextUtils.isEmpty(followedSignature)) {
            return "暂无个性签名";
        }
        return followedSignature;
    }

    public void setFollowedSignature(String followedSignature) {
        this.followedSignature = followedSignature;
    }

    public String getFollowerFaceUrl() {
        return followerFaceUrl;
    }

    public void setFollowerFaceUrl(String followerFaceUrl) {
        this.followerFaceUrl = followerFaceUrl;
    }

    public String getFollowerUsername() {
        return followerUsername;
    }

    public void setFollowerUsername(String followerUsername) {
        this.followerUsername = followerUsername;
    }

    public String getFollowerSignature() {
        return followerSignature;
    }

    public void setFollowerSignature(String followerSignature) {
        this.followerSignature = followerSignature;
    }
}
