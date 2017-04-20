package com.rideread.rideread.module.main;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseActivity;
import com.rideread.rideread.common.util.UserUtils;
import com.rideread.rideread.common.widget.MainFragmentTabHost;
import com.rideread.rideread.data.Logger;
import com.rideread.rideread.data.been.Tab;
import com.rideread.rideread.function.net.im.AVImClientManager;
import com.rideread.rideread.module.auth.view.LoginActivity;
import com.rideread.rideread.module.circle.view.CircleFragment;
import com.rideread.rideread.module.map.view.MapFragment;
import com.rideread.rideread.module.profile.view.ProfileFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    public final static String INVITED_RIDE_READ_ID = "invited_ride_read_id";

    @BindView(R.id.tab_content) FrameLayout mTabContent;
    @BindView(android.R.id.tabhost) MainFragmentTabHost mTabHost;


    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        //        getSupportActionBar().hide();
        initTab();

        //        AVObject testObject = new AVObject("TestObject");
        //        testObject.put("words","Hello World!");
        //        testObject.saveInBackground(new SaveCallback() {
        //            @Override
        //            public void done(AVException e) {
        //                if(e == null){
        //                    Logger.d("saved","success!");
        //                }
        //            }
        //        });

        openClient();
    }

    private void openClient() {
        AVImClientManager.getInstance().open(UserUtils.getUid() + "", new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (e == null) {
                    Logger.d("LeanCloud", "登录成功!");
                } else {
                    Logger.d("LeanCloud", "登录失败!");
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (0 == UserUtils.getUid()) {
            gotoActivity(LoginActivity.class, true);
        }
    }

    private void initTab() {
        Tab tab_map = new Tab(MapFragment.class, R.string.main_text_map, R.drawable.tab_map_sel);
        Tab tab_timeline = new Tab(CircleFragment.class, R.string.main_text_circle, R.drawable.tab_circle_sel);
        Tab tab_mine = new Tab(ProfileFragment.class, R.string.main_text_mine, R.drawable.tab_profile_sel);

        List<Tab> mTabs = new ArrayList<>(3);
        mTabs.add(tab_map);
        mTabs.add(tab_timeline);
        mTabs.add(tab_mine);

        mTabHost.setup(this, getSupportFragmentManager(), R.id.tab_content);

        for (Tab tab : mTabs) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(getString(tab.getTitle()));
            tabSpec.setIndicator(buildIndicator(tab));
            mTabHost.addTab(tabSpec, tab.getFragment(), null);
        }

        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        mTabHost.setCurrentTab(0);
    }

    private View buildIndicator(Tab tab) {
        LayoutInflater mInflater = LayoutInflater.from(this);
        View view = mInflater.inflate(R.layout.main_tab_indicator, null);
        ImageView img = (ImageView) view.findViewById(R.id.img_tab);
        TextView text = (TextView) view.findViewById(R.id.tv_tab);

        img.setBackgroundResource(tab.getIcon());
        text.setText(tab.getTitle());

        return view;
    }
}

