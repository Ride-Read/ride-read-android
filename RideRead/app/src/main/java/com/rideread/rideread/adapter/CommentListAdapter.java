package com.rideread.rideread.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rideread.rideread.R;
import com.rideread.rideread.bean.Comment;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Jackbing on 2017/2/10.
 */

public class CommentListAdapter extends BaseAdapter {

    List<Comment> lists;
    LayoutInflater inflater;
    int resId;
    Context context;

    public CommentListAdapter(List<Comment> lists,Context context,int resId){
        inflater=LayoutInflater.from(context);
        this.context=context;
        this.resId=resId;
        this.lists=lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comment comment=(Comment) getItem(position);
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=inflater.inflate(resId,null);
            viewHolder.content=(TextView)convertView.findViewById(R.id.timeline_detail_commentcontent);
            viewHolder.head=(CircleImageView)convertView.findViewById(R.id.timeline_detail_comment_head);
            viewHolder.name=(TextView)convertView.findViewById(R.id.timeline_detail_commentname);
            viewHolder.time=(TextView)convertView.findViewById(R.id.timeline_detail_commenttime);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        viewHolder.head.setImageResource(R.mipmap.me);
        viewHolder.time.setText(comment.getCommentTime());
        viewHolder.name.setText(comment.getName());
        viewHolder.content.setText(comment.getContent());
        return convertView;
    }

    class ViewHolder{
        CircleImageView head;
        TextView name,content,time;
    }
}
