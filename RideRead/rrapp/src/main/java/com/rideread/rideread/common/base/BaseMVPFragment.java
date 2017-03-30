package com.rideread.rideread.common.base;

import android.support.annotation.Nullable;

import com.rideread.rideread.common.util.ToastUtils;


public abstract class BaseMVPFragment<T extends IPresenter> extends BaseFragment implements IMVPView {

    private T mPresenter;

    public T getPresenter() {
        return mPresenter;
    }

    public void setPresenter(T presenter) {
        this.mPresenter = presenter;
    }


    @Override
    public void showToast(final String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (message == null) {
                    ToastUtils.show("null");
                } else {
                    ToastUtils.show(message);
                }
            }
        });
    }

    @Override
    public void showLoadProgress(@Nullable String content) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((BaseActivity) getActivity()).showProgressDialog(content);
            }
        });

    }

    @Override
    public void hideLoadProgress() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((BaseActivity) getActivity()).hideProgressDialog();
            }
        });
    }


    //	@Override
    //	public void showError(Throwable e) {
    //		e.printStackTrace();
    //	}

}
