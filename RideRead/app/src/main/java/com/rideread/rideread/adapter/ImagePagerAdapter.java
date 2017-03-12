package com.rideread.rideread.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rideread.rideread.event.ImageIndexEvent;
import com.rideread.rideread.fragment.ImageFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Jackbing on 2017/3/12.
 */

public class ImagePagerAdapter extends FragmentStatePagerAdapter {

    private List<String> mDatas;
    public ImagePagerAdapter(FragmentManager fm, List<String> data) {
        super(fm);
        mDatas = data;
    }
    @Override
    public Fragment getItem(int position) {
        String url = mDatas.get(position);
        Fragment fragment = ImageFragment.newInstance(url);
        EventBus.getDefault().post(new ImageIndexEvent(position));
        return fragment;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }
}
