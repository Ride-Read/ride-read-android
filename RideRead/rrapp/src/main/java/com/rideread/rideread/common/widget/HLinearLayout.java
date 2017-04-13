package com.rideread.rideread.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by SkyXiao on 2017/4/12.
 */

public class HLinearLayout extends LinearLayout

{

    private boolean canAddView = true;

    public HLinearLayout(Context context) {
        this(context, null);
    }

    public HLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 每次调用addView,都会调用onMeasure
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //测量自己的宽和高，获取测量模式和测量值
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        //如果是wrap_content
        int width = 0;

        //行的宽
        int lineWidth = 0;

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            //获得某个子view
            View child = getChildAt(i);
            //测量子view的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) childView.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            if (childWidth + lineWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                canAddView = false;
            } else {
                lineWidth += childWidth;
            }
        }
        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(), sizeHeight);
    }

    /**
     * 判断剩余的控件是否足够放置控件
     *
     * @return
     */
    public boolean canAddView() {
        return canAddView;
    }

}
