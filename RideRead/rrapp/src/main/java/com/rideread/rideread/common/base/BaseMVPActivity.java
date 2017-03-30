package com.rideread.rideread.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.rideread.rideread.common.util.ToastUtils;

public abstract class BaseMVPActivity<T extends IPresenter> extends BaseActivity implements IMVPView {
    private T mPresenter;

    public T getPresenter() {
        return mPresenter;
    }

    public void setPresenter(T presenter) {
        this.mPresenter = presenter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getPresenter() != null) {
            getPresenter().resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (getPresenter() != null) {
            getPresenter().pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getPresenter() != null) {
            getPresenter().destroy();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (getPresenter() != null) {
            getPresenter().saveInstanceState(outState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (getPresenter() != null) {
            getPresenter().restoreInstanceState(savedInstanceState);
        }
    }


    @Override
    public void showToast(final String message) {
        runOnUiThread(() -> {
            if (message == null) {
                ToastUtils.show("null");
            } else {
                ToastUtils.show(message);
            }
        });
    }

    @Override
    public void showLoadProgress(@Nullable String content) {
        runOnUiThread(() -> showProgressDialog(content));
    }

    @Override
    public void hideLoadProgress() {
        runOnUiThread(() -> hideProgressDialog());
    }
}
