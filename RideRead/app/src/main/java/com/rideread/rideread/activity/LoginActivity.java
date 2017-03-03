package com.rideread.rideread.activity;


import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;


import com.rideread.rideread.R;
import com.rideread.rideread.adapter.FragmentAdapter;
import com.rideread.rideread.adapter.TabAdapterImp;
import com.rideread.rideread.fragment.LoginFragment;
import com.rideread.rideread.fragment.RegisterFragment;
import com.rideread.rideread.widget.ScrollTabView;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends AppCompatActivity {


    private ViewPager viewPager;
    private String[] titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        getSupportActionBar().hide();//隐藏标题栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        initData();
        initViewPager();
        initTabs();

    }


    private void initViewPager() {
        viewPager=(ViewPager) findViewById(R.id.login_tabview_viewpager);
        FragmentAdapter fa=new FragmentAdapter(getSupportFragmentManager(),buildFragments());
        viewPager.setAdapter(fa);
    }

    private List<Fragment> buildFragments() {

        List<Fragment> fragments = new ArrayList<Fragment>(2);
        fragments.add(new LoginFragment());
        fragments.add(new RegisterFragment());
        return fragments;
    }

    private void initTabs() {
        ImageView indicatorView=(ImageView)findViewById(R.id.login_tabview_square);
        ScrollTabView scrollTabView=(ScrollTabView)findViewById(R.id.login_scrollview_tab);
        scrollTabView.initViewPager(viewPager);

        TabAdapterImp tai=new TabAdapterImp(this);
        tai.titles=titles;
        scrollTabView.setTabAdapter(tai);
        scrollTabView.setIndicatorView(indicatorView);
    }

    private void initData() {
        titles=getResources().getStringArray(R.array.scrollview_tab_title);

    }

}
