package com.rideread.rideread;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Jackbing on 2017/1/22.
 */

public class ResetPassword extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.forgetpwd_resetpwd);

    }

    public void onComplete(View v){

    }

    public void onBack(View b){
        finish();
    }
}
