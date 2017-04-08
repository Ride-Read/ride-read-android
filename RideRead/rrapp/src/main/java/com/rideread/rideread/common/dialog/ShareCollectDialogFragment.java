package com.rideread.rideread.common.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.rideread.rideread.R;

/**
 * Created by SkyXiao on 2017/4/6.
 */

public class ShareCollectDialogFragment extends DialogFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View mView=inflater.inflate(R.layout.dialog_share_collect,container,false);
        initView(mView);
        return mView;
    }

    //添加监听
    private void initView(View v) {
        View.OnClickListener listener=(View.OnClickListener) getActivity();
        ((TextView)v.findViewById(R.id.tv_share)).setOnClickListener(listener);
        ((TextView)v.findViewById(R.id.tv_collect)).setOnClickListener(listener);
    }

}