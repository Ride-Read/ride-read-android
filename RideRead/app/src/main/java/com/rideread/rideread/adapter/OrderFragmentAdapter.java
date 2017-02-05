package com.rideread.rideread.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 订单fragment适配器
 * Created by Jackbing on 2016/12/24.
 */

public class OrderFragmentAdapter extends FragmentPagerAdapter {


    private List<Fragment> fragments;

    public OrderFragmentAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    /**
     * 返回fragment的总数，从而确定tab的个数
     * @return
     */
    @Override
    public int getCount() {
        return fragments.size();
    }


}
