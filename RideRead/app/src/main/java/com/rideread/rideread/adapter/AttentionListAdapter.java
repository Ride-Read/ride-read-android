package com.rideread.rideread.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.rideread.rideread.R;
import com.rideread.rideread.bean.TimeLine;

import java.util.List;

/**
 * Created by Jackbing on 2017/2/4.
 */

public class AttentionListAdapter extends BaseAdapter {
    private List<TimeLine> timelines;
    private LayoutInflater inflate;
    private Context context;
    private int resId;

    public AttentionListAdapter(List<TimeLine> timelines,int resId,Context context){
        this.inflate=LayoutInflater.from(context);
        this.timelines=timelines;
        this.resId=resId;
        this.context=context;
    }
    @Override
    public int getCount() {
        return timelines.size();
    }

    @Override
    public Object getItem(int position) {
        return timelines.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TimeLine timeline=(TimeLine) getItem(position);
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=inflate.inflate(resId,null);
            viewHolder.text=(TextView)convertView.findViewById(R.id.timeline_text);
            viewHolder.gridView=(GridView) convertView.findViewById(R.id.timeline_imgs);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }


        if(timeline.isHasText()){
            viewHolder.text.setText(timeline.getText());
            viewHolder.text.setVisibility(View.VISIBLE);
        }

        if(timeline.isHasImg()){
            GridViewAdapter adapter=new GridViewAdapter(timeline.getImgs(),context,R.layout.timeline_grid_item);
            viewHolder.gridView.setAdapter(adapter);
            viewHolder.gridView.setVisibility(View.VISIBLE);
        }



        return convertView;
    }

    class ViewHolder {
        public TextView text;
        public GridView gridView;
    }


}
