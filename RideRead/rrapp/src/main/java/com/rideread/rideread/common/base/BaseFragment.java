package com.rideread.rideread.common.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    protected View mRootView;

    /**
     * log标识
     */
    public final String TAG = this.getClass().getSimpleName();
    private String mPageName;

    //    public Activity mActivity;
    //
    //    @Override
    //    public void onAttach(Context context) {
    //        super.onAttach(context);
    //        mActivity = getActivity();
    //    }
    //
    //    public void showSnackbar(View view, CharSequence text) {
    //        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
    //    }
    //
    //    public void showToast(CharSequence text) {
    //        Toast.makeText(mActivity, text, Toast.LENGTH_SHORT).show();
    //    }
    public abstract int getLayoutRes();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        XLog.d(TAG);
        //        Object clazz = this.getClass();
        //        mPageName = Analytics.page.get(clazz);
        if (TextUtils.isEmpty(mPageName)) {
            mPageName = this.getClass().getName();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //        XLog.d(TAG);

        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutRes(), container, false);
        }
        ButterKnife.bind(this, mRootView);
        initView();

        return mRootView;
    }


    public abstract void initView();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (null != view) {
            view.setClickable(true);
        }
    }

    public View getRootView() {
        return mRootView;
    }


    @Override
    public void onResume() {
        //        XLog.d(TAG);
        super.onResume();
        //        MobclickAgent.onPageStart(mPageName);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getBaseActivity() != null)
            getBaseActivity().setBackPressedListener(() -> onBackPressed());
    }

    @Override
    public void onPause() {
        //        XLog.d(TAG);
        super.onPause();
        //        MobclickAgent.onPageEnd(mPageName);
    }

    public void onTabTouch() {
    }

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    protected boolean onBackPressed() {
        return false;
    }

    @Override
    public void onDestroyView() {
        //        XLog.d(TAG);
        super.onDestroyView();
    }

}
