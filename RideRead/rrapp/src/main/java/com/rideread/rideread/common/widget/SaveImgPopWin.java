package com.rideread.rideread.common.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.rideread.rideread.R;

import butterknife.ButterKnife;

/**
 * Created by SkyXiao on 2017/4/14.
 */

public class SaveImgPopWin extends PopupWindow {


    private Button mBtnSaveImg, mBtnCancel;
    private View mMenuView;

    public SaveImgPopWin(Activity context, View.OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.pop_win_img_save, null);
        mBtnSaveImg= ButterKnife.findById(mMenuView,R.id.btn_save_img);
        mBtnCancel= ButterKnife.findById(mMenuView,R.id.btn_cancel);
        //取消按钮 -> 销毁弹出框
        mBtnCancel.setOnClickListener(v -> dismiss());
        //设置按钮监听  
        mBtnSaveImg.setOnClickListener(itemsOnClick);
        //设置SaveImgPopWin的View
        this.setContentView(mMenuView);
        //设置SaveImgPopWin弹出窗体的宽  
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SaveImgPopWin弹出窗体的高  
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SaveImgPopWin弹出窗体可点击  
        this.setFocusable(true);
        //设置SaveImgPopWin弹出窗体动画效果  
        this.setAnimationStyle(R.style.popup_anim_style);
        //实例化一个ColorDrawable颜色为全透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        //设置SaveImgPopWin弹出窗体的背景  
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框  
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mBtnSaveImg.getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

    }
}