package com.rideread.rideread.module.auth.view;

import android.widget.TextView;

import com.rideread.rideread.common.base.BaseMVPActivity;
import com.rideread.rideread.module.auth.presenter.LoginPresenterImpl;

import butterknife.BindView;

/**
 * Created by SkyXiao on 2017/3/30.
 */

public class LoginActivity extends BaseMVPActivity<LoginPresenterImpl> implements com.rideread.rideread.module.auth.view.ILoginView {
    @BindView(R.id.text) TextView mText;


    @Override
    public int getLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        setPresenter(new LoginPresenterImpl(this));
        mText.setText("OK");
    }

    @Override
    public void showData() {

    }

}
