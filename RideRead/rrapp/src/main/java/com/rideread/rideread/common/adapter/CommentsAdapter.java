package com.rideread.rideread.common.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rideread.rideread.R;
import com.rideread.rideread.common.util.TimeUtils;
import com.rideread.rideread.data.result.Comment;

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
        TextView tv_thumb_username = viewHolder.getView(R.id.tv_thumb_username);
        TextView tv_thumb_time = viewHolder.getView(R.id.tv_thumb_time);
        TextView tv_thumb_content = viewHolder.getView(R.id.tv_thumb_content);

        Comment comment = getItem(position);
        tv_thumb_username.setText(comment.getUsername());
        tv_thumb_time.setText(TimeUtils.getFriendlyTimeSpanByNow(comment.getCreatedAt()));
        tv_thumb_content.setText(comment.getMsg());

        return convertView;
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.view_comment;
    }
}


