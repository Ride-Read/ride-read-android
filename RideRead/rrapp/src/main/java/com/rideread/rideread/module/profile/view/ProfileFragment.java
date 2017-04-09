package com.rideread.rideread.module.profile.view;


import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseFragment;
import com.rideread.rideread.common.util.ImgLoader;
import com.rideread.rideread.common.util.UserUtils;
import com.rideread.rideread.data.result.UserInfo;

import butterknife.BindView;
import butterknife.OnClick;

import static com.rideread.rideread.R.id.tv_name;


public class ProfileFragment extends BaseFragment {


    @BindView(R.id.img_map_bg) ImageView mImgMapBg;
    @BindView(R.id.img_avatar) SimpleDraweeView mImgAvatar;
    @BindView(tv_name) TextView mTvName;
    @BindView(R.id.tv_signature) TextView mTvSignature;
    @BindView(R.id.tv_msg) TextView mTvMsg;
    @BindView(R.id.tv_attention) TextView mTvAttention;
    @BindView(R.id.tv_fans) TextView mTvFans;

    private UserInfo mUserInfo;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_main_profile;
    }

    @Override
    public void initView() {
        mUserInfo = UserUtils.getCurUser();
        ImgLoader.getInstance().displayImage(mUserInfo.getFaceUrl(), mImgAvatar);
        mTvName.setText(mUserInfo.getUsername());
        mTvName.setCompoundDrawablesWithIntrinsicBounds(0, 0, mUserInfo.getSex() == 1 ? R.drawable.ic_sex_man : R.drawable.ic_sex_female, 0);

        String signature = mUserInfo.getSignature();
        if (TextUtils.isEmpty(signature)) signature = "人生需要留白";
        mTvSignature.setText(signature);

        mTvMsg.setText("消息 10");//TODO 获取消息数
        mTvAttention.setText("关注 " + mUserInfo.getFollowing());
        mTvFans.setText("阅粉 " + mUserInfo.getFollower());
    }


    @OnClick({R.id.btn_personality_map, R.id.tv_msg, R.id.tv_attention, R.id.tv_fans, R.id.btn_post_date, R.id.btn_my_circle, R.id.btn_my_collect, R.id.btn_invite_friend, R.id.btn_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_personality_map:
                break;
            case R.id.tv_msg:
                break;
            case R.id.tv_attention:
                break;
            case R.id.tv_fans:
                break;
            case R.id.btn_post_date:
                break;
            case R.id.btn_my_circle:
                break;
            case R.id.btn_my_collect:
                break;
            case R.id.btn_invite_friend:
                break;
            case R.id.btn_setting:
                getBaseActivity().gotoActivity(SettingActivity.class);
                break;
        }
    }
}
