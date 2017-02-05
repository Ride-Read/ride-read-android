package com.rideread.rideread.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.rideread.rideread.R;

/**
 * Created by Jackbing on 2017/2/4.
 */

public class GridViewAdapter extends BaseAdapter {

    private int[] imgs;
    private LayoutInflater inflater;
    private int resId;

    public GridViewAdapter(int[] imgs, Context context, int resId){
        inflater=LayoutInflater.from(context);
        this.imgs=imgs;
        this.resId=resId;
    }
    @Override
    public int getCount() {
        return imgs.length;
    }

    @Override
    public Object getItem(int position) {
        return imgs[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int drawable=(int)getItem(position);
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=inflater.inflate(resId,null);
            viewHolder.img=(ImageView) convertView.findViewById(R.id.timeline_grid_img);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.img.setImageResource(drawable);

        return convertView;
    }

    class ViewHolder {
        ImageView img;
    }
}
