package com.rideread.rideread.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.rideread.rideread.R;

/**
 * Created by Jackbing on 2017/3/3.
 */

public class DeleteDialogFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View mView=inflater.inflate(R.layout.delete_dialog_frgament,container,false);
        return mView;

    }
}
