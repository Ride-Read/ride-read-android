package com.rideread.rideread.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rideread.rideread.R;

/**
 * Created by Jackbing on 2017/2/13.
 */

public class LoginFragment  extends Fragment implements View.OnClickListener{

    private View loginView,findPwdView,reSetPwdView;
    private String tagLogin="loginView",tagFindPwd="findPwdView",tagSetPwd="reSetPwdView";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView=inflater.inflate(R.layout.login_fragment_layout,container,false);
        loginView=mView.findViewById(R.id.login_include_layout);
        intiView(loginView.findViewById(R.id.login_btn),tagLogin);
        intiView(loginView.findViewById(R.id.login_tv_forgetpwd),null);
        findPwdView=mView.findViewById(R.id.findpwd_include_layout);
        intiView(findPwdView.findViewById(R.id.register_btn_next),tagFindPwd);
        reSetPwdView=mView.findViewById(R.id.resetpwd_include_layout);
        Button reSetPwdBtn=(Button)reSetPwdView.findViewById(R.id.register_btn_next);
        reSetPwdBtn.setText(getString(R.string.register_text_complete));
        TextView setPwdTitle=(TextView)reSetPwdView.findViewById(R.id.setpwd_title_tv);
        setPwdTitle.setText(getString(R.string.forgetpwd_text_resetpwd));
        intiView(reSetPwdBtn,tagSetPwd);
        return mView;
    }

    private void changeView(View hideView,View showView){

        hideView.setVisibility(View.GONE);
        showView.setVisibility(View.VISIBLE);
    }

    private void intiView(View v,String tag){
        v.setOnClickListener(this);
        v.setTag(tag);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_btn_next:
                String tag=(String)v.getTag();
                if(tag.equals(tagLogin)){
                    Toast.makeText(getActivity(),"登录",Toast.LENGTH_SHORT).show();
                }else if(tag.equals(tagFindPwd)){
                    changeView(findPwdView,reSetPwdView);
                }else if(tag.equals(tagSetPwd)){

                    Toast.makeText(getActivity(),"设置密码成功",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.login_tv_forgetpwd:
                changeView(loginView,findPwdView);
                break;


        }

    }
}
