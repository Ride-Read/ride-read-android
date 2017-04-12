package com.rideread.rideread.module.auth.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;
import android.widget.ImageView;

import com.rideread.rideread.R;
import com.rideread.rideread.common.adapter.LoginTabAdapter;
import com.rideread.rideread.common.base.BaseMVPActivity;
import com.rideread.rideread.common.widget.ScrollTabView;
import com.rideread.rideread.module.auth.presenter.LoginPresenterImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by SkyXiao on 2017/3/30.
 */

public class LoginActivity extends BaseMVPActivity<LoginPresenterImpl> implements ILoginView {
    @BindView(R.id.stv_arrow) ScrollTabView mStvArrow;
    @BindView(R.id.ic_indicator) ImageView ic_indicator;
    @BindView(R.id.viewpager_tab_view) ViewPager mViewpagerTabView;
    private List<Fragment> mFragments;


    @Override
    public int getLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        setPresenter(new LoginPresenterImpl(this));
        //getSupportActionBar().hide();//隐藏标题栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        initViewPager();
        initTabs();
    }

    @Override
    public void showData() {

    }


    private void initViewPager() {
        mFragments = new ArrayList<>(2);
        mFragments.add(new LoginFragment());
        mFragments.add(new RegisterFragment());
        FragmentPagerAdapter fpa = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
        mViewpagerTabView.setAdapter(fpa);
    }


    private void initTabs() {
        String[] titles = getResources().getStringArray(R.array.scrollview_tab_title);
        mStvArrow.initViewPager(mViewpagerTabView);

        LoginTabAdapter tai = new LoginTabAdapter(this);
        tai.titles = titles;
        mStvArrow.setTabAdapter(tai);
        mStvArrow.setIndicatorView(ic_indicator);
    }


}
