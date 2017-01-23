package com.rideread.rideread;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


/**
 * Created by Jackbing on 2017/1/22.
 * 填写手机号码
 */

public class RegisterPhoneActivity extends RegisterBaseActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_phonenum);

    }

    //下一步
    public void onNext(View v){
        startActivity(new Intent(this,RegisterSetPwdActivity.class));

    }

    //发送验证码
    public void onSendIdentCode(View v){

    }

    public void onBack(View v){
        finish();
    }

    public void readAgreement(View v){
        startActivity(new Intent(this,UserAgreement.class));
    }
}
