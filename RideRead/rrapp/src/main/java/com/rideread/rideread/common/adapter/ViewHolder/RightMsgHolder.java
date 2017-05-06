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
import com.rideread.rideread.common.event.ImTypeMsgResendEvent;
import com.rideread.rideread.common.util.ImgLoader;
import com.rideread.rideread.common.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SkyXiao on 2017/4/21.
 */

public class RightMsgHolder extends AVCommonViewHolder {

    @BindView(R.id.tv_chat_time) TextView mTvChatTime;
    @BindView(R.id.pb_chat_progressbar_right) ProgressBar mPbChatProgressbarRight;
    @BindView(R.id.img_chat_status_right) ImageView mImgChatStatusRight;
    @BindView(R.id.fl_chat_status_right) FrameLayout mFlChatStatusRight;
    @BindView(R.id.tv_chat_txt_right) TextView mTvChatTxtRight;
    @BindView(R.id.sdv_chat_avatar_right) SimpleDraweeView mSdvChatAvatarRight;

    private AVIMMessage message;

    public RightMsgHolder(Context context, ViewGroup root) {
        super(context, root, R.layout.view_char_right);
    }

    @OnClick(R.id.img_chat_status_right)
    public void onErrorClick(View view) {
        ImTypeMsgResendEvent event = new ImTypeMsgResendEvent();
        event.message = message;
        EventBus.getDefault().post(event);
        ToastUtils.show("重新发送");
    }

    @Override
    public void bindData(Object msg) {
        message = (AVIMMessage) msg;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = dateFormat.format(message.getTimestamp());

        String content = getContext().getString(R.string.unsupport_message_type);
        ;
        if (message instanceof AVIMTextMessage) {
            content = ((AVIMTextMessage) message).getText();
        }

        mTvChatTxtRight.setText(content);
        mTvChatTime.setText(time);

        if (AVIMMessage.AVIMMessageStatus.AVIMMessageStatusFailed == message.getMessageStatus()) {
            mImgChatStatusRight.setVisibility(View.VISIBLE);
            mPbChatProgressbarRight.setVisibility(View.GONE);
            mFlChatStatusRight.setVisibility(View.VISIBLE);
        } else if (AVIMMessage.AVIMMessageStatus.AVIMMessageStatusSending == message.getMessageStatus()) {
            mImgChatStatusRight.setVisibility(View.GONE);
            mPbChatProgressbarRight.setVisibility(View.VISIBLE);
            mFlChatStatusRight.setVisibility(View.VISIBLE);
        } else {
            mFlChatStatusRight.setVisibility(View.GONE);
        }
    }

    public void showTimeView(boolean isShow) {
        mTvChatTime.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @OnClick(R.id.img_chat_status_right)
    public void onViewClicked() {}

    public void setFaceUrl(String faceUrl) {
        ImgLoader.getInstance().displayImage(faceUrl, mSdvChatAvatarRight);
    }
}

