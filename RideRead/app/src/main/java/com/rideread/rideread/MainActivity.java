package com.rideread.rideread;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.rideread.rideread.bean.Tab;
import com.rideread.rideread.fragment.MapFragment;
import com.rideread.rideread.fragment.MineFragment;
import com.rideread.rideread.fragment.TimeLineFragment;
import com.rideread.rideread.widget.MainFragmentTabHost;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jackbing on 2017/1/26.
 */

public class MainActivity extends BaseActivity {

    private LayoutInflater mInflater;
    private MainFragmentTabHost mTabhost;
    private List<Tab> mTabs = new ArrayList<>(3);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.main_layout);
        initTab();
    }

    private void initTab() {
        Tab tab_map = new Tab(MapFragment.class,R.string.main_text_map,R.drawable.selector_icon_home);
        Tab tab_timeline = new Tab(TimeLineFragment.class,R.string.main_text_timeline,R.drawable.selector_icon_work);
        Tab tab_mine = new Tab(MineFragment.class,R.string.main_text_mine,R.drawable.selector_icon_mine);

        mTabs.add(tab_map);
        mTabs.add(tab_timeline);
        mTabs.add(tab_mine);

        mInflater = LayoutInflater.from(this);
        mTabhost = (MainFragmentTabHost) this.findViewById(android.R.id.tabhost);
        mTabhost.setup(this,getSupportFragmentManager(),R.id.realtabcontent);

        for (Tab tab : mTabs){

            TabHost.TabSpec tabSpec = mTabhost.newTabSpec(getString(tab.getTitle()));

            tabSpec.setIndicator(buildIndicator(tab));

            mTabhost.addTab(tabSpec,tab.getFragment(),null);

        }

        mTabhost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mTabhost.setCurrentTab(1);
    }

    private View buildIndicator(Tab tab){

        View view =mInflater.inflate(R.layout.main_tab_indicator,null);
        ImageView img = (ImageView) view.findViewById(R.id.icon_tab);
        TextView text = (TextView) view.findViewById(R.id.txt_indicator);

        img.setBackgroundResource(tab.getIcon());
        text.setText(tab.getTitle());

        return  view;
    }
}
