package com.rideread.rideread.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Jackbing on 2017/2/13.
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private FragmentManager fm;
    public FragmentAdapter(FragmentManager fm, List<Fragment> fragments){
        super(fm);
        this.fragments=fragments;
        this.fm=fm;
    }

    @Override
    public Fragment getItem(int position) {
        if(fragments==null){
            return null;
        }
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        if(fragments==null){
            return 0;
        }
        return fragments.size();
    }
}
