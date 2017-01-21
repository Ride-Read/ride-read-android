package com.rideread.rideread;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        getSupportActionBar().hide();//隐藏标题栏

    }


    public void onRegister(View v){
        startActivity(new Intent(this,RegisterActivity.class));
    }

    public void onForgetPwd(View v){

    }
}
