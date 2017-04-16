package com.rideread.rideread.data.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jackbing on 2017/2/27.
 * 登录返回的用户数据
 */

public class SearchUsers implements Serializable {

    private List<FollowUser> followers;
    private List<FollowUser> followeds;

    public List<FollowUser> getFollowers() {
        return followers;
    }

    public void setFollowers(List<FollowUser> followers) {
        this.followers = followers;
    }

    public List<FollowUser> getFolloweds() {
        return followeds;
    }

    public void setFolloweds(List<FollowUser> followeds) {
        this.followeds = followeds;
    }
}
