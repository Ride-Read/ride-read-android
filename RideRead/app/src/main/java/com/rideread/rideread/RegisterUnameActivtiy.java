package com.rideread.rideread;

import android.os.Bundle;
import android.view.View;

/**
 * Created by Jackbing on 2017/1/22.
 */

public class RegisterUnameActivtiy extends RegisterBaseActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_set_username);
    }

    //完成注册
    public void onComplete(View v){

    }

    public void onBack(View v){
        finish();
    }
}
