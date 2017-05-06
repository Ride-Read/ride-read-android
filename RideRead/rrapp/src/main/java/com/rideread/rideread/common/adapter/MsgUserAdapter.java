package com.rideread.rideread.common.adapter;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseActivity;
import com.rideread.rideread.common.util.ImgLoader;
import com.rideread.rideread.common.util.TimeUtils;
import com.rideread.rideread.common.util.Utils;
import com.rideread.rideread.data.been.MsgInfo;
import com.rideread.rideread.module.profile.view.ChatSingleActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SkyXiao on 2017/4/9.
 */

public class MsgUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<MsgInfo> mList;
    private BaseActivity mActivity;

    public MsgUserAdapter(BaseActivity baseActivity, List<MsgInfo> msgInfoList) {
        this.mActivity = baseActivity;
        this.mList = msgInfoList;
        mLayoutInflater = LayoutInflater.from(Utils.getAppContext());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.view_msg_item, parent, false);
        return new MsgUserHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder tHolder, int position) {
        MsgUserHolder holder = (MsgUserHolder) tHolder;

        MsgInfo info = mList.get(position);
        ImgLoader.getInstance().displayImage(info.getFaceUrl(), holder.mImgAvatar);
        holder.mTvName.setText(info.getUsername());
        holder.mTvTime.setText(TimeUtils.getFriendlyTimeSpanByNow(info.getTime()));
        holder.mTvContent.setText(info.getMsg());
        holder.mClMsgUserView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable(ChatSingleActivity.CHAT_USER, info);
            mActivity.gotoActivity(ChatSingleActivity.class, bundle);
        });
        //        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class MsgUserHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cl_msg_user_view) ConstraintLayout mClMsgUserView;
        @BindView(R.id.img_avatar) SimpleDraweeView mImgAvatar;
        @BindView(R.id.tv_name) TextView mTvName;
        @BindView(R.id.tv_content) TextView mTvContent;
        @BindView(R.id.tv_time) TextView mTvTime;

        public MsgUserHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
