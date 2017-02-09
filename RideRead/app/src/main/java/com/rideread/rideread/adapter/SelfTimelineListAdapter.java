package com.rideread.rideread.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.rideread.rideread.R;
import com.rideread.rideread.bean.SelfTimlineDetail;

import java.util.List;

/**
 * Created by Jackbing on 2017/2/9.
 */

public class SelfTimelineListAdapter extends BaseAdapter{

    List<SelfTimlineDetail> lists;
    private LayoutInflater inflater;
    private Context context;
    private int resId;
    public SelfTimelineListAdapter(Context context,List<SelfTimlineDetail> lists,int resId){
        this.lists=lists;
        this.resId=resId;
        this.inflater=LayoutInflater.from(context);
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
        SelfTimlineDetail detail=(SelfTimlineDetail) getItem(position);
        ViewHolder viewHolder=null;

        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=inflater.inflate(resId,null);
            viewHolder.pushtime=(TextView)convertView.findViewById(R.id.self_timelinelist_pushtime);
            viewHolder.listdetail=(ListView)convertView.findViewById(R.id.self_timeline_lv_detail);
            convertView.setTag(viewHolder);

        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }

        viewHolder.pushtime.setText(detail.getPushTime());
        viewHolder.listdetail.setAdapter(new SelfTimelineDetailAdapter(context,detail.getLists(),R.layout.self_timelinelist_detail_item));

        return convertView;
    }

    class ViewHolder {
        TextView pushtime;
        ListView listdetail;
    }


}
