package com.rideread.rideread.widget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.rideread.rideread.R;
import com.rideread.rideread.selfinterface.MineEditListener;

/**
 * Created by Jackbing on 2017/3/9.
 */

public class MineEditDialogFragmen extends DialogFragment implements View.OnClickListener{

    private String tag;
    private String title;
    private String hint;
    private TextView tvComplete,tvTitle,edtEdit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View mView=inflater.inflate(R.layout.mine_edit_dialogfragment,container,false);
        edtEdit=(EditText)mView.findViewById(R.id.mine_edit_dialog);
        tvTitle=(TextView)mView.findViewById(R.id.mine_edit_dialog_title);
        tvComplete=(TextView)mView.findViewById(R.id.mine_edit_dialog_complete);
        tag=getArguments().getString("tag");
        tvTitle.setText(getArguments().getString("title"));
        edtEdit.setHint(getArguments().getString("hint"));
        tvComplete.setOnClickListener(this);
        return mView;
    }


    @Override
    public void onClick(View v) {

        String content=edtEdit.getText().toString().trim();
        edtEdit.setText("");
        MineEditListener listener=(MineEditListener) getActivity();
        listener.getResult(tag,content);
    }

}
