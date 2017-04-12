package com.rideread.rideread.module.profile.view;

import android.view.View;

import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseActivity;
import com.rideread.rideread.common.util.TitleBuilder;

import butterknife.OnClick;

/**
 * Created by SkyXiao on 2017/4/6.
 */

public class InviteActivity extends BaseActivity {
    @Override
    public int getLayoutRes() {
        return R.layout.activity_invite;
    }

    @Override
    public void initView() {
        new TitleBuilder(this).setTitleText("邀请好友").IsBack(true).setLeftOnClickListener(v -> onBackPressed()).build();
    }


    @OnClick({R.id.tv_share_weixin_friend, R.id.tv_share_weixin_circle, R.id.tv_share_qq, R.id.tv_share_contact, R.id.tv_share_weibo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_share_weixin_friend:
                break;
            case R.id.tv_share_weixin_circle:
                break;
            case R.id.tv_share_qq:
                break;
            case R.id.tv_share_contact:
                break;
            case R.id.tv_share_weibo:
                break;
        }
    }
}
