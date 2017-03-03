package com.rideread.rideread.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.rideread.rideread.R;

/**
 * Created by Jackbing on 2017/1/31.
 */

public class AboutRideReadActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.mine_aboutrideread_layout);
        initView();
    }

    private void initView() {

        ImageView back=(ImageView)findViewById(R.id.left_setting_icon);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void onReadContract(View v){
        startActivity(new Intent(this,UserAgreement.class));
    }

    public void onContactRideRead(View v){
        startActivity(new Intent(this,ContactActivity.class));
    }


}
