package com.rideread.rideread.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jackbing on 2017/3/4.
 */

public class PersonalInfoFollower implements Serializable{
    private int status;
    private List<Follower> follower;

    public PersonalInfoFollower(int status, List<Follower> follower) {
        this.status = status;
        this.follower = follower;
    }

    public List<Follower> getFollower() {
        return follower;
    }

    public void setFollowing(List<Follower> follower) {
        this.follower = follower;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
