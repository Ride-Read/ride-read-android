package com.rideread.rideread.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rideread.rideread.R;
import com.rideread.rideread.bean.ChatMessage;

import java.util.List;

/**
 * Created by Jackbing on 2017/2/6.
 */

public class ChatMessageAdapter extends BaseAdapter {

    private List<ChatMessage> datas;
    private LayoutInflater inflater;
    private int typeCount;//item的布局种数

    public ChatMessageAdapter(Context context,List<ChatMessage> datas,int typeCount){
        this.datas=datas;
        this.inflater=LayoutInflater.from(context);
        this.typeCount=typeCount;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    public void addMessage(String msg){
        if(datas!=null){
            datas.add(new ChatMessage(null,msg,R.mipmap.me,null,0));
        }
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage msg=(ChatMessage) getItem(position);
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            if(getItemViewType(position)==0){
                convertView=inflater.inflate(R.layout.im_chat_to_listitem,null);
            }else{
                convertView=inflater.inflate(R.layout.im_chat_from_listitem,null);
            }
            viewHolder.chatPortait=(ImageView) convertView.findViewById(R.id.im_chat_portait);
            viewHolder.chatContent=(TextView)convertView.findViewById(R.id.im_chat_content);
            viewHolder.chatTime=(TextView)convertView.findViewById(R.id.im_chat_time);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }

        if(!TextUtils.isEmpty(msg.getPubDate())){
            viewHolder.chatTime.setText(msg.getPubDate());
        }
        if(!TextUtils.isEmpty(msg.getContent())){
            viewHolder.chatContent.setText(msg.getContent());
        }

        viewHolder.chatPortait.setImageResource(msg.getPortait());


        return convertView;
    }

    @Override
    public int getViewTypeCount() {

        return typeCount;
    }

    @Override
    public int getItemViewType(int position) {
        return datas.get(position).getType();
    }


    class ViewHolder{
        TextView chatContent,chatTime;
        ImageView chatPortait;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
