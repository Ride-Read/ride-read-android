package com.rideread.rideread.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rideread.rideread.R;
import com.rideread.rideread.bean.SelfTimelineList;
import com.rideread.rideread.bean.SelfTimlineDetail;

import java.util.List;

/**
 * Created by Jackbing on 2017/2/9.
 */

public class SelfTimelineDetailAdapter extends BaseAdapter {

    List<SelfTimelineList> lists;
    private LayoutInflater inflater;
    private int resId;
    public SelfTimelineDetailAdapter(Context context, List<SelfTimelineList> lists, int resId){
        this.lists=lists;
        this.resId=resId;
        this.inflater=LayoutInflater.from(context);
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
        SelfTimelineList timelineList=(SelfTimelineList) getItem(position);
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=inflater.inflate(resId,null);
            viewHolder.text=(TextView) convertView.findViewById(R.id.self_timeline_detail_tv);
            viewHolder.iv=(ImageView)convertView.findViewById(R.id.self_timeline_detail_iv);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        viewHolder.text.setText(timelineList.getText());
        viewHolder.iv.setImageResource(R.mipmap.me);
        return convertView;
    }

    class ViewHolder{
        TextView text;
        ImageView iv;
    }
}
