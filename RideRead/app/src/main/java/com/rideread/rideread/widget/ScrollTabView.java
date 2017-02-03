package com.rideread.rideread.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rideread.rideread.adapter.OrderTabAdapter;


/**
 * 由于使用TabLayout实现不了想要的效果，所以使用自定义的HorizontalScrollView，来实现二级Tab
 * Created by Jackbing on 2016/12/24.
 */

public class ScrollTabView extends HorizontalScrollView implements ViewPager.OnPageChangeListener {


    private LinearLayout container;
    private OrderTabAdapter tabAdapter;
    private ViewPager viewPager;
    private ImageView line;
    private DisplayMetrics display = null;
    private LinearLayout.LayoutParams lp;
    private int width;


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


    public OrderTabAdapter getOrderTabAdapter() {

        return tabAdapter;

    }

    public void setOrderTabAdapter(OrderTabAdapter tabAdapter) {
        this.tabAdapter = tabAdapter;
        initTabs();
    }

    public void setViewPager(ViewPager viewPager) {
        initLine();
        this.viewPager = viewPager;
        viewPager.setOnPageChangeListener(this);
    }

    /**
     * width来设置滑动线条的宽度
     */
    private void initLine() {

        display = getOrderTabAdapter().getDm();
        width = display.widthPixels / getOrderTabAdapter().getCount();
        lp = (LinearLayout.LayoutParams) line.getLayoutParams();
        lp.width = width;
        line.setLayoutParams(lp);

    }

    private void initTabs() {

        for (int i = 0; i < tabAdapter.getCount(); i++) {
            final int position = i;
            View v = tabAdapter.getView(position);
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


    public void setImageViewLine(ImageView imageViewLine) {
        this.line = imageViewLine;
    }

    /**
     * 通过设置滑动线条的leftMargin，实现位置变化
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        int length = getOrderTabAdapter().getCount();
        if (positionOffset != 0 && positionOffsetPixels != 0) {
            lp.leftMargin = positionOffsetPixels / length + width * position;
            line.setLayoutParams(lp);
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
