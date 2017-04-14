package com.rideread.rideread.common.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseActivity;
import com.rideread.rideread.common.util.ImgLoader;
import com.rideread.rideread.common.util.TimeUtils;
import com.rideread.rideread.data.result.Comment;
import com.rideread.rideread.module.profile.view.UserMomentsActivity;

/**
 * Created by SkyXiao on 2017/4/13.
 */

public class CommentsAdapter extends BaseListAdapter<Comment> {


    public CommentsAdapter(Context context) {
        super(context);
    }

    @Override
    public Long getItemId(Comment comment) {
        return (long) comment.getCommentId();
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder viewHolder, ViewGroup viewGroup) {
        SimpleDraweeView sdv_thumb_avatar = viewHolder.getView(R.id.sdv_thumb_avatar);
        TextView tv_thumb_username = viewHolder.getView(R.id.tv_thumb_username);
        TextView tv_thumb_time = viewHolder.getView(R.id.tv_thumb_time);
        TextView tv_thumb_content = viewHolder.getView(R.id.tv_thumb_content);

        Comment comment = getItem(position);
        ImgLoader.getInstance().displayImage(comment.getFaceUrl(), sdv_thumb_avatar);
        tv_thumb_username.setText(comment.getUsername());
        tv_thumb_time.setText(TimeUtils.getFriendlyTimeSpanByNow(comment.getCreatedAt()));
        tv_thumb_content.setText(comment.getMsg());

        sdv_thumb_avatar.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putInt(UserMomentsActivity.SELECTED_UID, comment.getUid());
            bundle.putString(UserMomentsActivity.SELECTED_USERNAME, comment.getUsername());
            ((BaseActivity) mContext).gotoActivity(UserMomentsActivity.class, bundle);
        });

        return convertView;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.view_comment;
    }
}


