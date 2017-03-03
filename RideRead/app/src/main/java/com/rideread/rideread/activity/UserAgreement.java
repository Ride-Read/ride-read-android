package com.rideread.rideread.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.rideread.rideread.R;
import com.rideread.rideread.widget.RegisterToolBar;

/**
 * Created by Jackbing on 2017/1/22.
 */

public class UserAgreement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.useragreement_main);
        Intent intent=getIntent();
        String title=intent.getStringExtra("title");

        RegisterToolBar toolbar=(RegisterToolBar)findViewById(R.id.register_toolbar);
        if(title!=null&&!title.isEmpty()){
            toolbar.setTitle(title);
        }
        ImageView back=(ImageView)findViewById(R.id.left_arrow_icon);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
