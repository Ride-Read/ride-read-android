package com.rideread.rideread.common.event;

import java.util.List;

/**
 * Created by Jackbing on 2017/2/8.
 */

public class SelectPicEvent {
    public final List<String> picList;

    public SelectPicEvent(final List<String> value) {
        this.picList = value;
    }
}
