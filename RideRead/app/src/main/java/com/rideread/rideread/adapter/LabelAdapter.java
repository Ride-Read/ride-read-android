package com.rideread.rideread.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.rideread.rideread.R;
import com.rideread.rideread.bean.Label;

import java.util.List;

/**
 * Created by Jackbing on 2017/3/17.
 */

public class LabelAdapter extends BaseAdapter {


    private List<Label> labels;
    private Context context;
    private LayoutInflater inflater;
    private int resId;

    public LabelAdapter(Context context,  List<Label> labels, int resId) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.labels = labels;
        this.resId = resId;
    }

    @Override
    public int getCount() {
        return labels.size();
    }

    @Override
    public Object getItem(int position) {
        return labels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Label label=(Label) getItem(position);
        ViewHolder viewHolder=null;
        if(convertView==null){
            convertView=inflater.inflate(resId,null);
            viewHolder=new ViewHolder();
            viewHolder.checked=(CheckBox)convertView.findViewById(R.id.label_checked_box);
            viewHolder.label_tv=(TextView)convertView.findViewById(R.id.label_string);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        viewHolder.checked.setChecked(label.isSelected());
        viewHolder.label_tv.setText(label.getLable());

        return convertView;
    }

    class ViewHolder{
        CheckBox checked;
        TextView label_tv;
    }
}
