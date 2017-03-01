package com.rideread.rideread.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rideread.rideread.R;
import com.rideread.rideread.adapter.OrderFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackbing on 2017/1/26.
 */

public class TimeLineFragment extends Fragment {

    private View mView;
    private ViewPager viewPager;
    private String[] tabTitle=null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView=inflater.inflate(R.layout.timeline_fragment_layout, container,false);
        initDatas();
        initView(mView);
        return mView;
    }

    private void initView(View v) {
        final TabLayout tablayout=(TabLayout) v.findViewById(R.id.main_timeline_tablayout);
        tablayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
        LinearLayout linearLayout=(LinearLayout)tablayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerPadding(20);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(getContext(),R.drawable.timleline_tablayout_divider));
        initViewPager(v);
        tablayout.setupWithViewPager(viewPager);

        for(int i=0;i<tablayout.getTabCount();i++){
            TabLayout.Tab tab=tablayout.getTabAt(i);
            tab.setText(tabTitle[i]);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tablayout.setScrollPosition(position,positionOffset,true);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    public void initDatas(){
        tabTitle=getResources().getStringArray(R.array.timeline_tab_title);
    }

    private List<Fragment> buildFragments() {
        List<Fragment> fragments = new ArrayList<Fragment>(2);
        fragments.add(new NearbyFragment());
        fragments.add(new AttentionFragment());
        return fragments;
    }

    private void initViewPager(View mView) {

        viewPager = (ViewPager) mView.findViewById(R.id.main_timeline_viewpager);
        OrderFragmentAdapter t = new OrderFragmentAdapter(getFragmentManager(), buildFragments());
        viewPager.setAdapter(t);
    }

}
