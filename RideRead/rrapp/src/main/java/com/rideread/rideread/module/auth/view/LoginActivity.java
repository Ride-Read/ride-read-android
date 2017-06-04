package com.rideread.rideread.module.auth.view;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.WindowManager;
import android.widget.ImageView;

import com.rideread.rideread.R;
import com.rideread.rideread.common.adapter.LoginTabAdapter;
import com.rideread.rideread.common.base.BaseMVPActivity;
import com.rideread.rideread.common.util.PermissionUtils;
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
//		getSupportActionBar().hide();//隐藏标题栏
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

    //权限申请结果回掉
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //数组grantResults是请求结果的集合（值为0（获得权限）或1（未获得权限））
        if (requestCode == PermissionUtils.CODE) {
            boolean agreement = true;
            //用for循环判断权限是否全部获得：
            for (int i : grantResults) {
                //一旦出现失败就把agreement变成false
                if (i == PackageManager.PERMISSION_DENIED) {
                    agreement = false;
                    break;
                }
            }

            if (agreement) {
                //获取全部权限才能进行相应的操作
            } else { //否则提示申请权限
                String htmlString = "<body>\n" +
                        "APP需要必要权限，如果已经拒绝权限，可以重新在\n" +
                        "<strong><font color=\"#2baf2b\">“设置>应用程序>应用名称>权限”</font></strong>中重新获取权限\n" +
                        "</body>";
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("温馨提示");
                builder.setCancelable(false);
                builder.setMessage(Html.fromHtml(htmlString));
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        }
    }




}
