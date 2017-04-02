package com.rideread.rideread.common.adapter;

import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by Jackbing on 2017/2/13.
 */

public abstract class AbstractTabAdapter {


    public String[] titles;

    public int getCount(){
        return titles.length;
    }


    public abstract View getView(int position);

    /**
     * 返回获取屏幕大小的DisplayMetrics实例
     * @return
     */
    public abstract DisplayMetrics getDisplayMetrics();
}
