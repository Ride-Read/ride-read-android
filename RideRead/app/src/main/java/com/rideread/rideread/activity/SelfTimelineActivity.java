package com.rideread.rideread.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.rideread.rideread.R;
import com.rideread.rideread.adapter.SelfTimelineListAdapter;
import com.rideread.rideread.bean.SelfTimeline;
import com.rideread.rideread.bean.SelfTimelineList;
import com.rideread.rideread.bean.SelfTimlineDetail;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Jackbing on 2017/2/8.
 * 我的阅圈页面
 */

public class SelfTimelineActivity extends BaseActivity {

    private SelfTimeline selftimeline;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.self_timeline_layout);
        String userName=getIntent().getStringExtra("userName");
        //根据用户名拉取我的阅圈页面所需要的数据
        initData();
        initView();
    }

    private void initView() {

        ImageView back=(ImageView)findViewById(R.id.left_setting_icon);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        View v= LayoutInflater.from(getApplicationContext()).inflate(R.layout.self_timeline_listview_headerview,null);
        ListView listView=(ListView)findViewById(R.id.self_timeline_list);
        listView.addHeaderView(v);
        TextView self_timeline_name=(TextView)v.findViewById(R.id.self_timeline_name);
        CircleImageView circleiv=(CircleImageView)v.findViewById(R.id.self_timeline_civ);
        ImageView ivsex=(ImageView)v.findViewById(R.id.self_timeline_sex);
        TextView signture=(TextView)v.findViewById(R.id.self_timeline_signture);
        TextView self_timeline_attention=(TextView)v.findViewById(R.id.self_timeline_attention);
        TextView self_timeline_fans=(TextView)v.findViewById(R.id.self_timeline_fans);
        TextView self_timeline_location=(TextView)v.findViewById(R.id.self_timeline_location);
        TextView self_timeline_school=(TextView)v.findViewById(R.id.self_timeline_school);
        TextView self_timeline_xizuo=(TextView)v.findViewById(R.id.self_timeline_xizuo);
        TextView self_timeline_job=(TextView)v.findViewById(R.id.self_timeline_job);


        self_timeline_attention.setText(selftimeline.getAttention());
        self_timeline_fans.setText(selftimeline.getFans());
        self_timeline_job.setText(selftimeline.getJob());
        self_timeline_location.setText(selftimeline.getLocation());
        self_timeline_school.setText(selftimeline.getSchool());
        self_timeline_name.setText(selftimeline.getName());
        self_timeline_xizuo.setText(selftimeline.getXizuo());
        signture.setText(selftimeline.getSignture());
        ivsex.setImageResource(R.mipmap.female);
        circleiv.setImageResource(R.mipmap.me);
        listView.setAdapter(new SelfTimelineListAdapter(this,selftimeline.getSelftimelinelist(),R.layout.self_timeline_listitem));

    }

    private void initData() {


        List<SelfTimelineList> lists1=new ArrayList<SelfTimelineList>();
        lists1.add(new SelfTimelineList("这棵树好漂亮",""));

        List<SelfTimelineList> lists2=new ArrayList<SelfTimelineList>();
        lists2.add(new SelfTimelineList("狼了一圈",""));
        lists2.add(new SelfTimelineList("好开心",""));

        List<SelfTimelineList> lists3=new ArrayList<SelfTimelineList>();
        lists3.add(new SelfTimelineList("狼了一圈",""));
        lists3.add(new SelfTimelineList("好开心",""));

        List<SelfTimelineList> lists4=new ArrayList<SelfTimelineList>();
        lists4.add(new SelfTimelineList("狼了一圈",""));
        lists4.add(new SelfTimelineList("好开心",""));

        ArrayList<SelfTimlineDetail> selftimelinedeatil=new ArrayList<SelfTimlineDetail>();
        selftimelinedeatil.add(new SelfTimlineDetail(lists1,"今天"));
        selftimelinedeatil.add(new SelfTimlineDetail(lists2,"1月14号"));
        selftimelinedeatil.add(new SelfTimlineDetail(lists3,"6月12号"));
        selftimelinedeatil.add(new SelfTimlineDetail(lists4,"7月12号"));

        selftimeline=new SelfTimeline("关注593", "阅粉5210万", "广州", "演员", "张小熊","", "广州大学",selftimelinedeatil, 1, "摩羯座", "个性签名");
    }



}
