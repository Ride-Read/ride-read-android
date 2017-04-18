package com.rideread.rideread.module.profile.view;

import android.view.View;
import android.widget.TextView;

import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseActivity;
import com.rideread.rideread.common.util.AppUtils;
import com.rideread.rideread.common.util.ShareUtils;
import com.rideread.rideread.common.util.TitleBuilder;
import com.rideread.rideread.common.util.ToastUtils;
import com.rideread.rideread.common.util.UserUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SkyXiao on 2017/4/6.
 */

public class AboutUsActivity extends BaseActivity {
    @BindView(R.id.tv_version) TextView mTvVersion;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_about_us;
    }

    @Override
    public void initView() {
        new TitleBuilder(this).setTitleText("关于骑阅").IsBack(true).setLeftOnClickListener(v -> finish()).build();
        mTvVersion.setText("当前版本 V" + AppUtils.getVersionName());
    }


    @OnClick({R.id.tv_version_update, R.id.tv_contact_us, R.id.tv_protocol, R.id.tv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_version_update:
                ToastUtils.show("版本更新");
                break;
            case R.id.tv_contact_us:
                ToastUtils.show("联系我们");
                break;
            case R.id.tv_protocol:
                ToastUtils.show("用户协议");
                break;
            case R.id.tv_share:
                ShareUtils.share(this, getString(R.string.share_text, UserUtils.getCurUser().getRideReadId()));
                break;
        }
    }
}
