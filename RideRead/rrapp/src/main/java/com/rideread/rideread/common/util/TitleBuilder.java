package com.rideread.rideread.common.util;

import android.app.Activity;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.rideread.rideread.R;

/**
 * Created by SkyXiao on 2017/4/10.
 */

public class TitleBuilder {
    private View mTopBar;
    private TextView tvTitle;
    private ImageView mImgLeft;
    private ImageView mImgRight;
    private TextView tvLeft;
    private TextView tvRight;

    public TitleBuilder(Activity context) {
        mTopBar = context.findViewById(R.id.rl_top_bar);
        tvTitle = (TextView) mTopBar.findViewById(R.id.tv_top_bar_title);
        mImgLeft = (ImageView) mTopBar.findViewById(R.id.img_top_bar_left);
        mImgRight = (ImageView) mTopBar.findViewById(R.id.img_top_bar_right);
        tvLeft = (TextView) mTopBar.findViewById(R.id.tv_top_bar_left);
        tvRight = (TextView) mTopBar.findViewById(R.id.tv_top_bar_right);
    }

    public TitleBuilder(View context) {
        mTopBar = context.findViewById(R.id.rl_top_bar);
        tvTitle = (TextView) mTopBar.findViewById(R.id.tv_top_bar_title);
        mImgLeft = (ImageView) mTopBar.findViewById(R.id.img_top_bar_left);
        mImgRight = (ImageView) mTopBar.findViewById(R.id.img_top_bar_right);
        tvLeft = (TextView) mTopBar.findViewById(R.id.tv_top_bar_left);
        tvRight = (TextView) mTopBar.findViewById(R.id.tv_top_bar_right);
    }

    // title

    public TitleBuilder setTitleBgRes(int resid) {
        mTopBar.setBackgroundResource(resid);
        return this;
    }

    public TitleBuilder setTitleText(String text) {
        tvTitle.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        tvTitle.setText(text);
        return this;
    }

    public TitleBuilder setTitleText(@StringRes int textResId) {
        tvTitle.setVisibility(0 != textResId ? View.GONE : View.VISIBLE);
        tvTitle.setText(textResId);
        return this;
    }

    // left

    public TitleBuilder setLeftImage(int resId) {
        mImgLeft.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        mImgLeft.setImageResource(resId);
        return this;
    }

    public TitleBuilder IsBack(boolean isBack) {
        mImgLeft.setVisibility(isBack ? View.VISIBLE : View.GONE);
        return this;
    }

    public TitleBuilder setLeftText(String text) {
        tvLeft.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        tvLeft.setText(text);
        return this;
    }

    public TitleBuilder setLeftOnClickListener(View.OnClickListener listener) {
        if (mImgLeft.getVisibility() == View.VISIBLE) {
            mImgLeft.setOnClickListener(listener);
        } else if (tvLeft.getVisibility() == View.VISIBLE) {
            tvLeft.setOnClickListener(listener);
        }
        return this;
    }

    // right

    public TitleBuilder setRightImage(int resId) {
        mImgRight.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        mImgRight.setImageResource(resId);
        return this;
    }

    public TitleBuilder setRightText(String text) {
        tvRight.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        tvRight.setText(text);
        return this;
    }

    public TitleBuilder setRightOnClickListener(View.OnClickListener listener) {
        if (mImgRight.getVisibility() == View.VISIBLE) {
            mImgRight.setOnClickListener(listener);
        } else if (tvRight.getVisibility() == View.VISIBLE) {
            tvRight.setOnClickListener(listener);
        }
        return this;
    }

    public View build() {
        return mTopBar;
    }

}