package com.rideread.rideread.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Jackbing on 2017/2/3.
 */

public class TimelinePagerAdapter extends PagerAdapter{
    private String[] tabTitle;
    private ArrayList<View> mList;

    public TimelinePagerAdapter(String[] tabTitle,ArrayList<View> mList){
        this.tabTitle=tabTitle;
        this.mList=mList;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mList.remove(position));

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mList.get(position));
        return mList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view==object;
    }
}
