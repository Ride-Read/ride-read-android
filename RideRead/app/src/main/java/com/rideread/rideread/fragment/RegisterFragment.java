package com.rideread.rideread.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.rideread.rideread.R;

/**
 * Created by Jackbing on 2017/2/13.
 */

public class RegisterFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView=inflater.inflate(R.layout.test,container,false);
        RelativeLayout rl=(RelativeLayout)mView.findViewById(R.id.nav_map);
        rl.setBackgroundColor(Color.RED);

        return mView;
    }
}
