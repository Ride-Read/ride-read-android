package com.rideread.rideread.common.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.rideread.rideread.R;
import com.rideread.rideread.common.dialog.ProgressDialogFragment;
import com.rideread.rideread.common.event.HideLoadingEvent;
import com.rideread.rideread.common.event.ShowLoadingEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

import static org.greenrobot.eventbus.ThreadMode.MAIN;


public abstract class BaseActivity extends AppCompatActivity {

    final static String ACTIVITY_FROM = "ACTIVITY_FROM";
    private BackPressedListener mBackPressedListener;


    public void setBackPressedListener(BackPressedListener backPressedListener) {
        this.mBackPressedListener = backPressedListener;
    }

    public interface BackPressedListener {
        boolean onBackPress();
    }

    public abstract int getLayoutRes();

    public abstract void initView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        ButterKnife.bind(this);
        initView();
    }


    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 用于显示progress的dialog
     */
    private static final String TAG_DIALOG_FRAGMENT = "tag_dialog_fragment";

    public void hideProgressDialog() {
        dismissProgressDialog();
    }

    protected void showProgressDialog(String message) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getExistingDialogFragment();
        if (prev == null) {
            ProgressDialogFragment fragment = ProgressDialogFragment.newInstance(message);
            fragment.show(ft, TAG_DIALOG_FRAGMENT);
        }
    }

    protected void dismissProgressDialog() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getExistingDialogFragment();
        if (prev != null) {
            ft.remove(prev).commit();
        }
    }

    private Fragment getExistingDialogFragment() {
        return getSupportFragmentManager().findFragmentByTag(TAG_DIALOG_FRAGMENT);
    }

    /**
     * Show message dialog.
     *
     * @param title   title.
     * @param message message.
     */
    public void showMessageDialog(int title, int message) {
        showMessageDialog(getText(title), getText(message));
    }

    /**
     * Show message dialog.
     *
     * @param title   title.
     * @param message message.
     */
    public void showMessageDialog(int title, CharSequence message) {
        showMessageDialog(getText(title), message);
    }

    /**
     * Show message dialog.
     *
     * @param title   title.
     * @param message message.
     */
    public void showMessageDialog(CharSequence title, int message) {
        showMessageDialog(title, getText(message));
    }

    /**
     * Show message dialog.
     *
     * @param title   title.
     * @param message message.
     */
    public void showMessageDialog(CharSequence title, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.knew, (dialog, which) -> dialog.dismiss());
        builder.show();
    }


    public void gotoActivity(Class<? extends Activity> TargetActivity) {
        gotoActivity(TargetActivity, false);
    }

    public void gotoActivity(Class<? extends Activity> TargetActivity, boolean isFinish) {
        gotoActivity(TargetActivity, null, isFinish);
    }

    public void gotoActivity(Class<? extends Activity> TargetActivity, Bundle bundle) {
        gotoActivity(TargetActivity, bundle, false);
    }

    public void gotoActivity(Class<? extends Activity> TargetActivity, Bundle bundle, boolean isFinish) {

        Intent intent = new Intent();
        intent.putExtra(ACTIVITY_FROM, getClass().getName());
        intent.setClass(this, TargetActivity);

        if (bundle != null && bundle.size() > 0) intent.putExtras(bundle);

        startActivity(intent);

        if (isFinish) {
            finish();
        }

    }

    @Subscribe(threadMode = MAIN)
    public void onLoading(final ShowLoadingEvent event) {
        showProgressDialog("正在加载，请等候...");
    }

    @Subscribe(threadMode = MAIN)
    public void onLoadCancel(final HideLoadingEvent event) {
        hideProgressDialog();
    }

}
