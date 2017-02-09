package com.rideread.rideread.bean;

import java.util.List;

/**
 * Created by Jackbing on 2017/2/9.
 */

public class SelfTimlineDetail {
    private String pushTime;//发布时间
    private List<SelfTimelineList> lists;

    public SelfTimlineDetail(List<SelfTimelineList> lists, String pushTime) {
        this.lists = lists;
        this.pushTime = pushTime;
    }

    public List<SelfTimelineList> getLists() {
        return lists;
    }

    public void setLists(List<SelfTimelineList> lists) {
        this.lists = lists;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }
}
