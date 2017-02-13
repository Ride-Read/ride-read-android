package com.rideread.rideread.widget;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.rideread.rideread.adapter.AbstractTabAdapter;

/**
 * Created by Jackbing on 2017/2/13.
 */

public class ScrollTabView extends HorizontalScrollView implements ViewPager.OnPageChangeListener {


    private LinearLayout container;
    private ViewPager viewPager;
    private ImageView indicatorView;
    private DisplayMetrics display = null;
    private LinearLayout.LayoutParams lp;
    private int defalutLeftMargin;
    private AbstractTabAdapter adapter;


    public ScrollTabView(Context context) {
        this(context, null);
    }

    public ScrollTabView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        container = new LinearLayout(context);
        container.setGravity(LinearLayout.HORIZONTAL);
        addView(container);

    }


    public void setTabAdapter(AbstractTabAdapter adapter){
        this.adapter=adapter;
        initTabs();
    }

    public void initViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        viewPager.setOnPageChangeListener(this);
    }


    private void initIndicator() {

        display = adapter.getDisplayMetrics();
        //width = display.widthPixels / getOrderTabAdapter().getCount();
        lp = (LinearLayout.LayoutParams) indicatorView.getLayoutParams();
//        lp.width = width;
//        line.setLayoutParams(lp);
        //indicatorView.setLayoutParams(lp);
        defalutLeftMargin=lp.leftMargin;

    }

    public void setIndicatorView(ImageView indicatorView){
        this.indicatorView=indicatorView;
        initIndicator();
    }


    private void initTabs() {

        for (int i = 0; i < adapter.getCount(); i++) {
            final int position = i;
            View v = adapter.getView(position);
            container.addView(v);

            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectTab(position);
                }
            });
        }
        selectTab(0);
    }

    public void selectTab(int position) {


        for (int i = 0; i < container.getChildCount(); i++) {
            container.getChildAt(i).setSelected(position == i);
        }
        int l = container.getChildAt(0).getWidth();
        smoothScrollTo(l * ((position - 1)), 0);
        if (viewPager != null) {
            viewPager.setCurrentItem(position);
        }

    }


    /**
     * 通过设置滑动线条的leftMargin，实现位置变化
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        Log.e("------onPageScrolled","position="+position+","+"positionOffset"+positionOffset+","+"positionOffsetPixels"+positionOffsetPixels);
        int length = adapter.getCount();
        if (positionOffset != 0 && positionOffsetPixels != 0) {
            lp.leftMargin =defalutLeftMargin+ positionOffsetPixels / length + lp.width * position;
            indicatorView.setLayoutParams(lp);
        }


    }

    @Override
    public void onPageSelected(int position) {

        selectTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


}
