package com.rideread.rideread.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.rideread.rideread.R;
import com.rideread.rideread.adapter.OrderFragmentAdapter;
import com.rideread.rideread.adapter.OrderTabAdapterImp;
import com.rideread.rideread.widget.ScrollTabView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackbing on 2017/1/26.
 */

public class TimeLineFragment extends Fragment {

    private View mView;
    private ImageView imageViewLine;
    private ScrollTabView scrolltabView;
    private OrderTabAdapterImp orderTabAdapterImp;
    private ViewPager viewPager;
    private String[] tabTitle=null;

    private ArrayList<View> mList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView=inflater.inflate(R.layout.timeline_fragment_layout, container,false);
        initDatas();
        initView(mView);

        //initMagicIndicator();
        return mView;
    }

    private void initView(View v) {
        final TabLayout tablayout=(TabLayout) v.findViewById(R.id.main_timeline_tablayout);
        tablayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(),R.color.login_textcolor_gray));
       // LinearLayout linearLayout=(LinearLayout)tablayout.getChildAt(0);
        //linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        //linearLayout.setDividerDrawable(ContextCompat.getDrawable(getContext(),R.drawable.timleline_tablayout_divider));
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

//    private void initMagicIndicator() {
//        final MagicIndicator magicIndicator=(MagicIndicator)mView.findViewById(R.id.main_timeline_magicindicator);
//        CommonNavigator comnav=new CommonNavigator(getContext());
//        comnav.setAdapter(new CommonNavigatorAdapter() {
//            @Override
//            public int getCount() {
//                return tabTitle==null?0:tabTitle.length;
//            }
//
//            @Override
//            public IPagerTitleView getTitleView(Context context, final int i) {
//                ColorTransitionPagerTitleView ctv=new ColorTransitionPagerTitleView(getContext());
//                ctv.setNormalColor(Color.GRAY);
//                ctv.setSelectedColor(Color.BLACK);
//                ctv.setText(tabTitle[i]);
//                ctv.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        viewPager.setCurrentItem(i);
//                    }
//                });
//                return ctv;
//            }
//
//            @Override
//            public IPagerIndicator getIndicator(Context context) {
//                LinePagerIndicator indicator=new LinePagerIndicator(getContext());
//                indicator.setMode(LinePagerIndicator.MODE_MATCH_EDGE);
//                return indicator;
//            }
//        });
//
//        magicIndicator.setNavigator(comnav);
//        ViewPagerHelper.bind(magicIndicator,viewPager);
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                magicIndicator.onPageScrolled(position,positionOffset,positionOffsetPixels);
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                magicIndicator.onPageSelected(position);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                magicIndicator.onPageScrollStateChanged(state);
//            }
//        });
//        viewPager.setCurrentItem(0);
//
//
//    }


    public void initDatas(){
        tabTitle=getResources().getStringArray(R.array.timeline_tab_title);
    }
//
//    public void initTab(View mView){
//        imageViewLine = (ImageView) mView.findViewById(R.id.imageview_bottom_line);
//        scrolltabView = (ScrollTabView) mView.findViewById(R.id.main_timeline_tabs);
//        scrolltabView.setImageViewLine(imageViewLine);
//        orderTabAdapterImp = new OrderTabAdapterImp(this);
//        orderTabAdapterImp.orderTitle = tabTitle;
//        scrolltabView.setOrderTabAdapter(orderTabAdapterImp);
//    }
//
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
