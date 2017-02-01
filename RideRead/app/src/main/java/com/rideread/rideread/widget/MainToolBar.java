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
 * Created by Jackbing on 2017/1/26.
 */

public class MainToolBar extends Toolbar {
    private View mView;
    private LayoutInflater inflater;
    private TextView mTtitle;
    private ImageView rightSearchIcon,leftSettingIcon;

    public MainToolBar(Context context) {
        this(context, null);
    }

    public MainToolBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        setContentInsetsRelative(10, 10);

        if (attrs != null) {
            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.MainToolBar, defStyleAttr, 0);

            boolean isShowSearchIcon = a.getBoolean(R.styleable.MainToolBar_isShowRightIcon, false);
            boolean isShowSettingIcon=a.getBoolean(R.styleable.MainToolBar_isShowLeftIcon,false);

            int lefticonResId=a.getResourceId(R.styleable.MainToolBar_LeftIcon,R.mipmap.icon_setting);
            int righticonResId=a.getResourceId(R.styleable.MainToolBar_RightIcon,R.mipmap.icon_search);

            leftSettingIcon.setImageResource(lefticonResId);
            rightSearchIcon.setImageResource(righticonResId);


            if(isShowSettingIcon){
                showSettingIcon();
            }else{
                hideSettingIcon();
            }

            if (isShowSearchIcon) {
                showSearchIcon();
            }else{
                hideSearchIcon();
            }

            a.recycle();
        }

    }

    private void showSettingIcon(){
        if(leftSettingIcon!=null){
            leftSettingIcon.setVisibility(View.VISIBLE);
        }
    }

    private void hideSettingIcon(){
        if(leftSettingIcon!=null){
            leftSettingIcon.setVisibility(View.GONE);
        }
    }

    private void showSearchIcon() {

        if (rightSearchIcon != null) {
            rightSearchIcon.setVisibility(View.VISIBLE);
        }
    }

    private void hideSearchIcon() {
        if (rightSearchIcon != null) {
            rightSearchIcon.setVisibility(View.GONE);
        }
    }

    /**
     * 加载自定义的toolbar布局
     */
    private void initView() {
        if (mView == null) {
            inflater = LayoutInflater.from(getContext());
            mView = (View) inflater.inflate(R.layout.main_toolbar, null);

            mTtitle = (TextView) mView.findViewById(R.id.main_toolbar_title);
            rightSearchIcon = (ImageView) mView.findViewById(R.id.right_search_icon);
            leftSettingIcon=(ImageView)mView.findViewById(R.id.left_setting_icon);

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
