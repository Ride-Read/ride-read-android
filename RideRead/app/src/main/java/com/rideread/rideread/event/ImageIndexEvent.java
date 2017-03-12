package com.rideread.rideread.event;

/**
 * Created by Jackbing on 2017/3/12.
 */

public class ImageIndexEvent {

    int index;

    public ImageIndexEvent(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
