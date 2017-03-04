package com.rideread.rideread.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jackbing on 2017/3/4.
 */

public class PersonalInfoFollowing implements Serializable {
    private int status;
    private List<Following> following;

    public PersonalInfoFollowing(int status, List<Following> following) {
        this.status = status;
        this.following = following;
    }

    public List<Following> getFollowing() {
        return following;
    }

    public void setFollowing(List<Following> following) {
        this.following = following;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
