package com.rideread.rideread.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rideread.rideread.R;

/**
 * Created by Jackbing on 2017/2/13.
 */

public class LoginFragment  extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView=inflater.inflate(R.layout.test,container,false);
        RelativeLayout rl=(RelativeLayout)mView.findViewById(R.id.nav_map);
        final View loginView=mView.findViewById(R.id.login_include_layout);
        final View findPwdView=mView.findViewById(R.id.findpwd_include_layout);
        TextView tv_findPwd=(TextView)mView.findViewById(R.id.login_tv_forgetpwd);
        tv_findPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginView.setVisibility(View.GONE);
                findPwdView.setVisibility(View.VISIBLE);
            }
        });
        return mView;
    }
}
