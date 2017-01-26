package com.rideread.rideread.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.rideread.rideread.R;

/**
 * Created by Jackbing on 2016/12/23.
 */

public class RegisterToolBar extends Toolbar {


    private View mView;
    private LayoutInflater inflater;
    private TextView mTtitle;
    private ImageView leftArrowIcon;

    public RegisterToolBar(Context context) {
        this(context, null);
    }

    public RegisterToolBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RegisterToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        setContentInsetsRelative(10, 10);

        if (attrs != null) {
            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.RegisterToolBar, defStyleAttr, 0);

            boolean isShowTitle = a.getBoolean(R.styleable.RegisterToolBar_isShowTitle, false);

            int resId=a.getResourceId(R.styleable.RegisterToolBar_leftArrowIcon,R.mipmap.left_arrow);
            leftArrowIcon.setImageResource(resId);

            if (isShowTitle) {
                showTitle();
            }

            if(!isShowTitle){
                hideTitle();
            }

            a.recycle();
        }

    }

    private void showTitle() {
        if(mTtitle!=null){
            mTtitle.setVisibility(View.VISIBLE);
        }

    }

    private void hideTitle(){

        if (mTtitle!=null){
            mTtitle.setVisibility(View.GONE);
        }
    }

    /**
     * 加载自定义的toolbar布局
     */
    private void initView() {
        if (mView == null) {
            inflater = LayoutInflater.from(getContext());
            mView = (View) inflater.inflate(R.layout.register_toolbar, null);

            mTtitle = (TextView) mView.findViewById(R.id.register_toolbar_title);
            leftArrowIcon = (ImageView) mView.findViewById(R.id.left_arrow_icon);

            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);

            addView(mView, lp);
        }

    }


    @Override
    public void setTitle(@StringRes int resId) {
        setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title) {
        initView();
        if (mTtitle != null) {
            mTtitle.setText(title);
        }
    }




}
