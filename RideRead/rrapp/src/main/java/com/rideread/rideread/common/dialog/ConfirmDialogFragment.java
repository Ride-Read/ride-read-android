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
import com.rideread.rideread.common.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by SkyXiao on 2017/4/6.
 */

public class ConfirmDialogFragment extends DialogFragment {

    private static final String KEY_TITLE = "key_title";
    private static final String KEY_CONFIRM_TXT = "btn_confirm_txt";

    @BindView(R.id.tv_title) TextView mTvTitle;
    @BindView(R.id.tv_confirm) TextView mTvConfirm;
    Unbinder unbinder;

    public static ConfirmDialogFragment newInstance(int titleId) {
        return newInstance(titleId, R.string.confirm);
    }

    public static ConfirmDialogFragment newInstance(int titleId, int btnConfirmTxtId) {

        ConfirmDialogFragment frag = new ConfirmDialogFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_TITLE, titleId);
        args.putInt(KEY_CONFIRM_TXT, btnConfirmTxtId);
        frag.setArguments(args);

        return frag;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View mView = inflater.inflate(R.layout.dialog_confirm, container, false);
        unbinder = ButterKnife.bind(this, mView);
        initView(mView);
        return mView;
    }

    //添加监听
    private void initView(View v) {
        mTvTitle.setText(getArguments().getInt(KEY_TITLE));
        mTvConfirm.setText(getArguments().getInt(KEY_CONFIRM_TXT));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_cancel, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_confirm:
                ((BaseActivity) getActivity()).doPositiveClick();
                break;
        }
    }
}