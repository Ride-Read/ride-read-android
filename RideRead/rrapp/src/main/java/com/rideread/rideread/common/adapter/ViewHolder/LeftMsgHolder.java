package com.rideread.rideread.common.adapter.ViewHolder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.facebook.drawee.view.SimpleDraweeView;
import com.rideread.rideread.R;
import com.rideread.rideread.common.util.ImgLoader;

import java.text.SimpleDateFormat;

import butterknife.BindView;

import static com.rideread.rideread.R.id.tv_chat_time;

/**
 * Created by SkyXiao on 2017/4/21.
 */

public class LeftMsgHolder extends AVCommonViewHolder {

    @BindView(tv_chat_time) TextView mTvChatTime;
    @BindView(R.id.sdv_chat_avatar_left) SimpleDraweeView mSdvChatAvatarLeft;
    @BindView(R.id.tv_chat_txt_left) TextView mTvChatTxtLeft;
    @BindView(R.id.pb_chat_progressbar_left) ProgressBar mPbChatProgressbarLeft;
    @BindView(R.id.img_chat_status_left) ImageView mImgChatStatusLeft;
    @BindView(R.id.fl_chat_status_left) FrameLayout mFlChatStatusLeft;

    public LeftMsgHolder(Context context, ViewGroup root) {
        super(context, root, R.layout.view_char_left);
    }

    @Override
    public void bindData(Object o) {
        AVIMMessage message = (AVIMMessage) o;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String time = dateFormat.format(message.getTimestamp());

        String content = getContext().getString(R.string.unsupport_message_type);
        if (message instanceof AVIMTextMessage) {
            content = ((AVIMTextMessage) message).getText();
        }

        mTvChatTxtLeft.setText(content);
        mTvChatTime.setText(time);
        //        nameView.setText(message.getFrom());
    }

    public void showTimeView(boolean isShow) {
        mTvChatTime.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    public void setFaceUrl(String faceUrl) {
        ImgLoader.getInstance().displayImage(faceUrl,mSdvChatAvatarLeft);
    }
}