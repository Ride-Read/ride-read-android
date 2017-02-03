package com.rideread.rideread.adapter;

import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackbing on 2016/12/24.
 */

public  abstract  class OrderTabAdapter {

    public String[] orderTitle=null;

    public int getCount(){
        return orderTitle.length;
    }


    public abstract View getView(int position);

    /**
     * 返回获取屏幕大小的DisplayMetrics实例
     * @return
     */
    public abstract DisplayMetrics getDm();


}
