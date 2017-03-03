package com.rideread.rideread.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.rideread.rideread.R;
import com.rideread.rideread.adapter.CommentListAdapter;
import com.rideread.rideread.bean.Comment;
import com.rideread.rideread.bean.TimeLine;
import com.rideread.rideread.common.ImageShower;
import com.rideread.rideread.widget.DeleteDialogFragment;
import com.rideread.rideread.widget.LikeLinearLayout;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Jackbing on 2017/3/2.
 */

public class CommentListFragment extends Fragment {

    private DeleteDialogFragment deleteDialogFragment;

    private View mView;
    private TimeLine timeline;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.comment_list_fragment,container,false);
        return mView;
    }


    public void setTimeLine(TimeLine timeLine){
        this.timeline=timeLine;
        initView();
    }

    private void initView() {

        List<Comment> comments=new ArrayList<Comment>();
        comments.add(new Comment("12:29","拍的真好","","陈晓村"));
        comments.add(new Comment("11:20","世界那么大，我想去看看","","镇一间"));
        comments.add(new Comment("10:29","我也想去","","黄花话"));

        List<String> face_urls=new ArrayList<String>();
        for(int i=88;i>0;i--){
            face_urls.add(""+i);
        }
        ListView listView=(ListView) mView.findViewById(R.id.timeline_detail_commentlist);

        View v= LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.timeline_detail_headerview,null);
        LikeLinearLayout linearLayout=(LikeLinearLayout)v.findViewById(R.id.likes_linearlayout);
        for(int i=0;i<face_urls.size();i++){
            if(linearLayout.canAddView()){
                CircleImageView circleImageView=new CircleImageView(getContext());
                circleImageView.setImageResource(R.mipmap.me);
                circleImageView.setPadding(0,0,8,0);
                linearLayout.addView(circleImageView);
            }
            if(!linearLayout.canAddView()){
                break;
            }
        }
        TextView like_nums=(TextView)v.findViewById(R.id.like_nums);
        like_nums.setText(face_urls.size()+"");

        TextView text=(TextView)v.findViewById(R.id.timeline_text);
        NineGridImageView gridView=(NineGridImageView) v.findViewById(R.id.timeline_imgs);
        //final VideoTextureView videoTextureView=(VideoTextureView)v.findViewById(R.id.timeline_videotextureview);
        //ImageView iv_play=(ImageView)v.findViewById(R.id.iv_play);
        TextView author=(TextView)v.findViewById(R.id.timeline_nickname);
        TextView pushTime=(TextView)v.findViewById(R.id.timeline_time);
        ImageView timeline_head=(ImageView)v.findViewById(R.id.timeline_head);


        listView.addHeaderView(v);

        if(timeline.isHasText()==true){
            text.setText(timeline.getText());
            text.setVisibility(View.VISIBLE);
        }else{
            text.setVisibility(View.GONE);
        }

        NineGridImageViewAdapter<Integer> adapter=new NineGridImageViewAdapter<Integer>() {
            @Override
            protected void onDisplayImage(Context context, ImageView imageView, Integer resId) {
                imageView.setImageResource(resId);
            }

            @Override
            protected void onItemImageClick(Context context, ImageView imageView, int index, List<Integer> list) {

                Intent intent=new Intent(getActivity(), ImageShower.class);
                intent.putExtra("content","图像url");
                startActivity(intent);

            }
        };

        if(timeline.isHasImg()==true){
            gridView.setAdapter(adapter);
            gridView.setImagesData(timeline.getImgs());
            gridView.setVisibility(View.VISIBLE);
        }else{
            gridView.setVisibility(View.GONE);
        }


//        if(timeline.isHasVideo()==true){
//
//            videoTextureView.setVisibility(View.VISIBLE);
//            iv_play.setVisibility(View.VISIBLE);
//            videoTextureView.setIvTip(iv_play);
//            videoTextureView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(!videoTextureView.getPlayStatus())
//                        videoTextureView.startMediaPlayer();
//                    else{
//                        videoTextureView.stopMediaPlayer();
//                    }
//                }
//            });
//        }else{
//            videoTextureView.setVisibility(View.GONE);
//            iv_play.setVisibility(View.GONE);
//        }

        author.setText(timeline.getAuthor());
        pushTime.setText(timeline.getPushTime());
        timeline_head.setImageResource(R.mipmap.me);

        listView.setAdapter(new CommentListAdapter(comments,getContext(),R.layout.timline_detail_comment_listitem));
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                if(deleteDialogFragment==null){
                    deleteDialogFragment=new DeleteDialogFragment();
                }
                deleteDialogFragment.show(CommentListFragment.this.getActivity().getSupportFragmentManager(),"deleteDialogFragment");

                return true;
            }
        });

    }
}
