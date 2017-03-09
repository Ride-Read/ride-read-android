package com.rideread.rideread.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Switch;

/**
 * Created by Jackbing on 2017/3/9.
 */

public class SexSwitch extends Switch {
    public SexSwitch(Context context) {
        this(context,null);
    }

    public SexSwitch(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SexSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
