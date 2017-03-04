package com.rideread.rideread.common;

import java.util.Date;

/**
 * Created by Jackbing on 2017/3/4.
 */

public class TimeStamp {

    public static long getTimeStamp(){
        return new Date().getTime();//生成时间戳
    }
}
