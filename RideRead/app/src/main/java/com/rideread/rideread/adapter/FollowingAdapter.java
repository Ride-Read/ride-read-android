package com.rideread.rideread.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rideread.rideread.R;
import com.rideread.rideread.bean.Follower;
import com.rideread.rideread.bean.Following;

import java.util.List;

/**
 * Created by Jackbing on 2017/3/7.
 */

public class FollowingAdapter extends BaseAdapter{
    private List<Following> lists=null;
    private LayoutInflater inflater;
    private int ResId;
    private Context context;

    public FollowingAdapter(Context context,int resId,List<Following> lists){
        inflater=LayoutInflater.from(context);
        this.lists=lists;
        this.ResId=resId;
        this.context=context;
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
        Following follower=lists.get(position);
        ViewHolder viewHolder=null;
        if(convertView==null){
            convertView=inflater.inflate(ResId,null);
            viewHolder=new ViewHolder();
            viewHolder.iv=(ImageView)convertView.findViewById(R.id.mine_attention_listiv_head);
            viewHolder.tv_name=(TextView)convertView.findViewById(R.id.mine_attention_listiv_name);
            viewHolder.tv_signture=(TextView)convertView.findViewById(R.id.mine_attention_listiv_signture);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }

        //viewHolder.iv.setImageResource(attention.getHeadResId());
        Glide.with(context).load(follower.getFace_url()).into(viewHolder.iv);
        viewHolder.tv_name.setText(follower.getNickname());
        viewHolder.tv_signture.setText(follower.getSignature());
        return convertView;
    }

    class ViewHolder {
        ImageView iv;
        TextView tv_name,tv_signture;
    }
}
