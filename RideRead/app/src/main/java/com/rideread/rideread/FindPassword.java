package com.rideread.rideread;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Jackbing on 2017/1/22.
 */

public class FindPassword extends RegisterBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpwd_findpwd);

    }


    public void onNext(View v) {

        startActivity(new Intent(this,ResetPassword.class));
    }


    public void onSendIdentCode(View v) {

    }


}
