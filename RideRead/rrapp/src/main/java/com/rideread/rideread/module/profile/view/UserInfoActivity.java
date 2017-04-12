package com.rideread.rideread.module.profile.view;

import android.text.TextUtils;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseActivity;
import com.rideread.rideread.common.util.ImgLoader;
import com.rideread.rideread.common.util.UserUtils;
import com.rideread.rideread.data.result.UserInfo;

import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by SkyXiao on 2017/4/6.
 */

public class UserInfoActivity extends BaseActivity {

    @BindView(R.id.tablerow_avatar) TableRow mTrAvatar;
    @BindView(R.id.tablerow_nickname) TableRow mTrNickname;
    @BindView(R.id.tablerow_sex) TableRow mTrSex;
    @BindView(R.id.tablerow_label) TableRow mTrLabel;
    @BindView(R.id.tablerow_signture) TableRow mTrSignture;
    @BindView(R.id.tablerow_birthday) TableRow mTrBirthDay;
    @BindView(R.id.tablerow_telphone) TableRow mTrTelPhone;
    @BindView(R.id.tablerow_school) TableRow mTrSchool;
    @BindView(R.id.tablerow_locale) TableRow mTrLocale;
    @BindView(R.id.tablerow_hometown) TableRow mTrHomeTown;
    @BindView(R.id.tablerow_job) TableRow mTrJob;

    @BindView(R.id.img_avatar) SimpleDraweeView mImgAvatar;
    @BindView(R.id.tv_nickname) TextView mTvNickName;
    @BindView(R.id.tv_sex) TextView mTvSex;
    @BindView(R.id.tv_label) TextView mTvlabel;
    @BindView(R.id.tv_signture) TextView mTvSignture;
    @BindView(R.id.tv_birthdate) TextView mTvBirthDay;
    @BindView(R.id.tv_telphone) TextView mTvTelPhone;
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
        mUserInfo = UserUtils.getCurUser();
        ImgLoader.getInstance().displayImage(mUserInfo.getFaceUrl(), mImgAvatar);
        mTvNickName.setText(mUserInfo.getUsername());

       String signture=mUserInfo.getSignature();
        mTvSignture.setText(TextUtils.isEmpty(signture)?signture:"还没有个性签名");
        String sex=null;
       if(mUserInfo.getSex()==0){
           sex="未知";
       }else if(mUserInfo.getSex()==1){
           sex="男";
       }else{
           sex="女";
       }
        mTvSex.setText(sex);
       StringBuilder sb=new StringBuilder();
       List<String> labels=mUserInfo.getTags();
       if(labels!=null){
           for(int i=0;i<labels.size();i++){
               if(i==labels.size()-1){
                   sb.append(labels.get(i));
               }else{
                   sb.append(labels.get(i));
               }
           }
       }
      if(!TextUtils.isEmpty(sb.toString())){
          mTvlabel.setText(sb.toString());
      }
       String birthday=mUserInfo.getBirthday();
        if(!TextUtils.isEmpty(birthday)){
            mTvBirthDay.setText(birthday);
        }
       String phoneNumber=mUserInfo.getPhonenumber();
        if(!TextUtils.isEmpty(phoneNumber)){
            mTvTelPhone.setText(phoneNumber);
        }
       String school=mUserInfo.getSchool();
        if(!TextUtils.isEmpty(school)){
            mTvSchool.setText(school);
        }
       String locale=mUserInfo.getLocation();
        if(!TextUtils.isEmpty(locale)){
            mTvLocale.setText(locale);
        }
       String hometown=mUserInfo.getHometown();
        if(!TextUtils.isEmpty(hometown)){
            mTvHometown.setText(hometown);
        }
       String job=mUserInfo.getCareer();
        if(!TextUtils.isEmpty(job)){
            mTvJob.setText(job);
        }


    }

    @OnClick({R.id.tablerow_avatar, R.id.tablerow_nickname, R.id.tablerow_sex,R.id.tablerow_label,R.id.tablerow_signture,R.id.tablerow_birthday,
            R.id.tablerow_telphone,R.id.tablerow_school,R.id.tablerow_locale,R.id.tablerow_hometown,R.id.tablerow_job})
    public void onViewClicked(View v) {

        switch (v.getId()){

        }

    }

//    @OnClick(R.id.btn_logout)
//    public void onClick() {
//        UserUtils.logout();
//        finish();
//    }
}
