package com.rideread.rideread.activity;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.rideread.rideread.R;

/**
 * Created by Jackbing on 2017/1/31.
 */

public class SettingActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.mine_setting_layout);
        intiView();
    }

    private void intiView() {

        ImageView back=(ImageView)findViewById(R.id.left_setting_icon);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    //关于骑阅
    public void onAboutRideRead(View V){
        startActivity(new Intent(this,AboutRideReadActivity.class));

    }

    public void onExit(View v){

    }

    public void onHowUse(View v){
        startActivity(new Intent(this,UserAgreement.class));

    }
}
