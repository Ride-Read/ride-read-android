package com.rideread.rideread.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rideread.rideread.R;
import com.rideread.rideread.bean.MessageEntity;

import java.util.List;

/**
 * Created by Jackbing on 2017/2/6.
 */

public class MessageListAdapter extends BaseAdapter {

    private List<MessageEntity> entity;
    private LayoutInflater inflater;
    private int resId;
    public MessageListAdapter(List<MessageEntity> entity, Context context,int resId){
        this.resId=resId;
        this.inflater=LayoutInflater.from(context);
        this.entity=entity;
    }
    @Override
    public int getCount() {
        return entity.size();
    }

    @Override
    public Object getItem(int position) {
        return entity.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageEntity entity=(MessageEntity) getItem(position);
        ViewHolder viewHolder=null;

        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=inflater.inflate(resId,null);
            viewHolder.author=(TextView)convertView.findViewById(R.id.message_tv_author);
            viewHolder.content=(TextView)convertView.findViewById(R.id.message_tv_content);
            viewHolder.time=(TextView)convertView.findViewById(R.id.message_tv_time);
            viewHolder.Pic=(ImageView)convertView.findViewById(R.id.message_head_iv_pic);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }

        viewHolder.Pic.setImageResource(entity.getPoritart());
        viewHolder.time.setText(entity.getTime());
        viewHolder.content.setText(entity.getContent());
        viewHolder.author.setText(entity.getAuthor());

        return convertView;
    }

    class ViewHolder{
        ImageView Pic;
        TextView author,content,time;
    }
}
