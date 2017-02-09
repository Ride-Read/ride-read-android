package com.rideread.rideread.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.rideread.rideread.R;
import com.rideread.rideread.SelfTimelineActivity;
import com.rideread.rideread.bean.TimeLine;
import com.rideread.rideread.fragment.AttentionFragment;
import com.rideread.rideread.widget.VideoTextureView;

import java.util.List;

/**
 * Created by Jackbing on 2017/2/4.
 */

public class AttentionListAdapter extends BaseAdapter{
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

        final TimeLine timeline=(TimeLine) getItem(position);
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=inflate.inflate(resId,null);
            viewHolder.text=(TextView)convertView.findViewById(R.id.timeline_text);
            viewHolder.gridView=(NineGridImageView) convertView.findViewById(R.id.timeline_imgs);
            viewHolder.videoTextureView=(VideoTextureView)convertView.findViewById(R.id.timeline_videotextureview);
            viewHolder.iv_play=(ImageView)convertView.findViewById(R.id.iv_play);
            viewHolder.author=(TextView)convertView.findViewById(R.id.timeline_nickname);
            viewHolder.commentnum=(TextView)convertView.findViewById(R.id.timeline_comment_num);
            viewHolder.zannum=(TextView)convertView.findViewById(R.id.timeline_like_num);
            viewHolder.diasance=(TextView)convertView.findViewById(R.id.timeline_distance);
            viewHolder.location=(TextView)convertView.findViewById(R.id.timeline_destinnation);
            viewHolder.pushTime=(TextView)convertView.findViewById(R.id.timeline_time);
            viewHolder.timeline_head=(ImageView)convertView.findViewById(R.id.timeline_head);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }

        viewHolder.timeline_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, SelfTimelineActivity.class);
                intent.putExtra("userName",timeline.getAuthor());
                context.startActivity(intent);
            }
        });

        if(timeline.isHasText()==true){
            viewHolder.text.setText(timeline.getText());
            viewHolder.text.setVisibility(View.VISIBLE);
        }else{
            viewHolder.text.setVisibility(View.GONE);
        }

        if(timeline.isHasImg()==true){
            viewHolder.gridView.setAdapter(adapter);
            viewHolder.gridView.setImagesData(timeline.getImgs());
            viewHolder.gridView.setVisibility(View.VISIBLE);
        }else{
            viewHolder.gridView.setVisibility(View.GONE);
        }


        if(timeline.isHasVideo()==true){
            Log.e("lllllll","hhh");
            viewHolder.videoTextureView.setVisibility(View.VISIBLE);
            viewHolder.iv_play.setVisibility(View.VISIBLE);
            viewHolder.videoTextureView.setIvTip(viewHolder.iv_play);
            setOnClickListener(viewHolder);
        }else{
            viewHolder.videoTextureView.setVisibility(View.GONE);
            viewHolder.iv_play.setVisibility(View.GONE);
        }

        viewHolder.author.setText(timeline.getAuthor());
        viewHolder.pushTime.setText(timeline.getPushTime());
        viewHolder.commentnum.setText(timeline.getCommentnum());
        viewHolder.zannum.setText(timeline.getZanNum());
        viewHolder.diasance.setText("距离我"+timeline.getDistance());
        viewHolder.location.setText(timeline.getLocation());

        return convertView;
    }



    class ViewHolder {
        public TextView text,author,commentnum,zannum,pushTime,diasance,location;
        public NineGridImageView gridView;
        public VideoTextureView videoTextureView;
        public ImageView iv_play;
        public ImageView timeline_head;
    }


    NineGridImageViewAdapter<Integer> adapter=new NineGridImageViewAdapter<Integer>() {
        @Override
        protected void onDisplayImage(Context context, ImageView imageView, Integer resId) {
            imageView.setImageResource(resId);
        }
    };

    public void setOnClickListener(final ViewHolder viewHolder){
        viewHolder.videoTextureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!viewHolder.videoTextureView.getPlayStatus())
                    viewHolder.videoTextureView.startMediaPlayer();
                else{
                    viewHolder.videoTextureView.stopMediaPlayer();
                }
            }
        });
    }



}
