package com.rideread.rideread.module.profile.view;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseActivity;
import com.rideread.rideread.common.util.DateUtils;
import com.rideread.rideread.common.util.ImgLoader;
import com.rideread.rideread.common.util.TitleBuilder;
import com.rideread.rideread.common.util.ToastUtils;
import com.rideread.rideread.common.util.UserUtils;
import com.rideread.rideread.data.result.UserInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SkyXiao on 2017/4/6.
 */

public class UserInfoActivity extends BaseActivity {


    @BindView(R.id.img_avatar) SimpleDraweeView mImgAvatar;
    @BindView(R.id.tv_nickname) TextView mTvNickName;
    @BindView(R.id.tv_sex) TextView mTvSex;
    @BindView(R.id.tv_label) TextView mTvLabel;
    @BindView(R.id.tv_birthday) TextView mTvBirthday;
    @BindView(R.id.tv_phone) TextView mTvPhone;
    @BindView(R.id.tv_school) TextView mTvSchool;
    @BindView(R.id.tv_locale) TextView mTvLocale;
    @BindView(R.id.tv_hometown) TextView mTvHometown;
    @BindView(R.id.tv_job) TextView mTvJob;
    private UserInfo mUserInfo;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_userinfo;
    }

    @Override
    public void initView() {
        new TitleBuilder(this).setTitleText(R.string.edit_user_info).IsBack(true).setRightText("保存").build();

        mUserInfo = UserUtils.getCurUser();
        ImgLoader.getInstance().displayImage(mUserInfo.getFaceUrl(), mImgAvatar);
        mTvNickName.setText(mUserInfo.getUsername());

        String signature = mUserInfo.getSignature();
        //        mTvSignture.setText(TextUtils.isEmpty(signature) ? signature : "还没有个性签名");
        String sex = null;
        if (mUserInfo.getSex() == 0) {
            sex = "未知";
        } else if (mUserInfo.getSex() == 1) {
            sex = "男";
        } else {
            sex = "女";
        }
        //        mTvSex.setText(sex);
        StringBuilder sb = new StringBuilder();
        List<String> labels = mUserInfo.getTags();
        if (labels != null) {
            for (int i = 0; i < labels.size(); i++) {
                if (i == labels.size() - 1) {
                    sb.append(labels.get(i));
                } else {
                    sb.append(labels.get(i));
                }
            }
        }
        if (!TextUtils.isEmpty(sb.toString())) mTvLabel.setText(sb.toString());


        long birthday = mUserInfo.getBirthday();
        if (0!=birthday) mTvBirthday.setText(DateUtils.getDateDayFormat(birthday));

        String phoneNumber = mUserInfo.getPhonenumber();
        if (!TextUtils.isEmpty(phoneNumber)) mTvPhone.setText(phoneNumber);

        String school = mUserInfo.getSchool();
        if (!TextUtils.isEmpty(school)) mTvSchool.setText(school);

        String locale = mUserInfo.getLocation();
        if (!TextUtils.isEmpty(locale)) mTvLocale.setText(locale);

        String hometown = mUserInfo.getHometown();
        if (!TextUtils.isEmpty(hometown)) mTvHometown.setText(hometown);

        String job = mUserInfo.getCareer();
        if (!TextUtils.isEmpty(job)) mTvJob.setText(job);


    }


    @OnClick({R.id.img_top_bar_left, R.id.tv_top_bar_right, R.id.fl_avatar, R.id.fl_nick, R.id.fl_sex, R.id.fl_label, R.id.tv_signature, R.id.fl_birthday, R.id.fl_phone, R.id.tv_school, R.id.fl_locale, R.id.fl_hometown, R.id.fl_job})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_top_bar_left:
                finish();
                break;
            case R.id.tv_top_bar_right:
                ToastUtils.show("保存");
                break;
            case R.id.fl_avatar:
                ToastUtils.show("更换头像");
                break;
            case R.id.fl_nick:
                ToastUtils.show("更换昵称");
                break;
            case R.id.fl_sex:
                ToastUtils.show("更换性别");
                break;
            case R.id.fl_label:
                ToastUtils.show("更换标签");
                break;
            case R.id.tv_signature:
                ToastUtils.show("更换个性签名");
                break;
            case R.id.fl_birthday:
                ToastUtils.show("更换生日");
                break;
            case R.id.fl_phone:
                ToastUtils.show("更换手机号");
                break;
            case R.id.tv_school:
                ToastUtils.show("更换学习");
                break;
            case R.id.fl_locale:
                ToastUtils.show("更换所在地");
                break;
            case R.id.fl_hometown:
                ToastUtils.show("更换家乡");
                break;
            case R.id.fl_job:
                ToastUtils.show("更换职业");
                break;
        }
    }

}
