package com.rideread.rideread.common.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseActivity;
import com.rideread.rideread.module.map.view.PostMomentActivity;

/**
 * Created by SkyXiao on 2017/4/6.
 */

public class SignInDialogFragment extends DialogFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View mView = inflater.inflate(R.layout.dialog_sign_in, container, false);
        initView(mView);
        return mView;
    }

    //添加监听
    private void initView(View v) {
        v.findViewById(R.id.tv_cancel).setOnClickListener(v1 -> dismiss());
        v.findViewById(R.id.tv_post).setOnClickListener(v12 -> {
            dismiss();
            ((BaseActivity) getActivity()).gotoActivity(PostMomentActivity.class);
        });
    }

}