package com.rideread.rideread.data.result;

import java.util.List;

/**
 * Created by SkyXiao on 2017/4/18.
 */

public class DetailUserInfo extends UserInfo {
    public DetailUserInfo(String faceUrl, String nick, int sex, List<String> labels, String signature, String school, String location, String homeTown, String career) {
        super(faceUrl, nick, sex, labels, signature, school, location, homeTown, career);
    }

    private int  isFollow;

    public int getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(int isFollow) {
        this.isFollow = isFollow;
    }
}
