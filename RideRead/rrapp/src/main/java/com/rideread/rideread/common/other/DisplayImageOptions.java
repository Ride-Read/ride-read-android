package com.rideread.rideread.common.other;

import com.facebook.drawee.generic.RoundingParams;

/**
 * Created by SkyXiao on 2017/4/9.
 */

public class DisplayImageOptions {
    int            defaultPlaceHolder;
    RoundingParams roundingParams;

    public DisplayImageOptions() {

    }

    public DisplayImageOptions(int defaultPlaceHolder, RoundingParams roundingParams) {
        this.defaultPlaceHolder = defaultPlaceHolder;
        this.roundingParams = roundingParams;
    }

    public int getDefaultPlaceHolder() {
        return defaultPlaceHolder;
    }

    public void setDefaultPlaceHolder(int defaultPlaceHolder) {
        this.defaultPlaceHolder = defaultPlaceHolder;
    }

    public RoundingParams getRoundingParams() {
        return roundingParams;
    }

    public void setRoundingParams(RoundingParams roundingParams) {
        this.roundingParams = roundingParams;
    }
}