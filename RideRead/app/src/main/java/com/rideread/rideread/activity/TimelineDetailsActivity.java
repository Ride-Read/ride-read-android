package com.rideread.rideread.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.rideread.rideread.R;
import com.rideread.rideread.bean.TimeLine;
import com.rideread.rideread.fragment.CommentListFragment;
import com.rideread.rideread.widget.MoreDialogFragment;

/**
 * Created by Jackbing on 2017/2/10.
 */

public class TimelineDetailsActivity extends BaseActivity implements View.OnClickListener{

    private CommentListFragment commentListFragment;
    private MoreDialogFragment moreDialogFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.timeline_details_layout);
        ImageView back=(ImageView) findViewById(R.id.left_setting_icon);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ImageView more=(ImageView)findViewById(R.id.right_search_icon);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(moreDialogFragment==null){
                    moreDialogFragment=new MoreDialogFragment();
                }
                moreDialogFragment.show(getSupportFragmentManager(),"moreDialogFragment");
            }
        });
        commentListFragment=(CommentListFragment)getSupportFragmentManager().findFragmentById(R.id.timline_commentlist);
        commentListFragment.setTimeLine(initData());
        //initView(initData());
    }



//    private void initView(TimeLine timeline) {
//
//        String commentlist="陈小春，张学友，陈小春，张学友，陈小春，张学友，陈小春，张学友";
//
//        List<Comment> comments=new ArrayList<Comment>();
//        comments.add(new Comment("12:29","拍的真好","","陈晓村"));
//        comments.add(new Comment("11:20","世界那么大，我想去看看","","镇一间"));
//        comments.add(new Comment("10:29","我也想去","","黄花话"));
//
//        ListView listView=(ListView) findViewById(R.id.timeline_detail_commentlist);
//
//        View v= LayoutInflater.from(getApplicationContext()).inflate(R.layout.timeline_detail_headerview,null);
//        TextView text=(TextView)v.findViewById(R.id.timeline_text);
//        NineGridImageView gridView=(NineGridImageView) v.findViewById(R.id.timeline_imgs);
//        final  VideoTextureView videoTextureView=(VideoTextureView)v.findViewById(R.id.timeline_videotextureview);
//        ImageView iv_play=(ImageView)v.findViewById(R.id.iv_play);
//        TextView author=(TextView)v.findViewById(R.id.timeline_nickname);
//        TextView commentList=(TextView)v.findViewById(R.id.timline_detail_comment_list);
//        TextView diasance=(TextView)v.findViewById(R.id.timeline_distance);
//        TextView location=(TextView)v.findViewById(R.id.timeline_destinnation);
//        TextView pushTime=(TextView)v.findViewById(R.id.timeline_time);
//        ImageView timeline_head=(ImageView)v.findViewById(R.id.timeline_head);
//
//        ImageView back=(ImageView) findViewById(R.id.left_setting_icon);
//
//        listView.addHeaderView(v);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//
//
//
//        if(timeline.isHasText()==true){
//            text.setText(timeline.getText());
//            text.setVisibility(View.VISIBLE);
//        }else{
//            text.setVisibility(View.GONE);
//        }
//
//        NineGridImageViewAdapter<Integer> adapter=new NineGridImageViewAdapter<Integer>() {
//            @Override
//            protected void onDisplayImage(Context context, ImageView imageView, Integer resId) {
//                imageView.setImageResource(resId);
//            }
//
//            @Override
//            protected void onItemImageClick(Context context, ImageView imageView, int index, List<Integer> list) {
//
//                Intent intent=new Intent(TimelineDetailsActivity.this, ImageShower.class);
//                intent.putExtra("content","图像url");
//                startActivity(intent);
//
//            }
//        };
//
//        if(timeline.isHasImg()==true){
//            gridView.setAdapter(adapter);
//            gridView.setImagesData(timeline.getImgs());
//            gridView.setVisibility(View.VISIBLE);
//        }else{
//            gridView.setVisibility(View.GONE);
//        }
//
//
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
//
//        author.setText(timeline.getAuthor());
//        pushTime.setText(timeline.getPushTime());
//        diasance.setText("距离我"+timeline.getDistance());
//        location.setText(timeline.getLocation());
//        timeline_head.setImageResource(R.mipmap.me);
//        commentList.setText(commentlist);
//
//        listView.setAdapter(new CommentListAdapter(comments,this,R.layout.timline_detail_comment_listitem));
//
//
//    }



    private TimeLine initData() {
       return (TimeLine) getIntent().getSerializableExtra("timeline");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.collection_textview:
                moreDialogFragment.dismiss();
                break;
            case R.id.share_textview:
                moreDialogFragment.dismiss();
                break;
        }


    }
}
