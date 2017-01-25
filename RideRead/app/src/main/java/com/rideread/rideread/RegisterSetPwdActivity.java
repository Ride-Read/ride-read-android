package com.rideread.rideread;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Jackbing on 2017/1/22.
 * 设置密码
 */

public class RegisterSetPwdActivity extends RegisterBaseActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_setpassword);

    }

    public void onNext(View v){
        startActivity(new Intent(this,RegisterUnameActivtiy.class));

    }

    public void onBack(View v){
        finish();
    }
}
