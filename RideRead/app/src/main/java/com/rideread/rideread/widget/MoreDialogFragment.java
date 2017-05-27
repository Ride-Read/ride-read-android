package com.rideread.rideread.widget;

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
 * Created by Jackbing on 2017/3/3.
 */

public class MoreDialogFragment extends DialogFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View mView=inflater.inflate(R.layout.more_dialog_fragment,container,false);
        initView(mView);
        return mView;
    }

    //添加监听
    private void initView(View v) {
        View.OnClickListener listener=(View.OnClickListener) getActivity();
        ((TextView)v.findViewById(R.id.collection_textview)).setOnClickListener(listener);
        ((TextView)v.findViewById(R.id.share_textview)).setOnClickListener(listener);
    }

}
