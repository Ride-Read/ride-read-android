package com.rideread.rideread;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

        ListView listView=(ListView)findViewById(R.id.self_timeline_list);
        TextView self_timeline_name=(TextView)findViewById(R.id.self_timeline_name);
        CircleImageView circleiv=(CircleImageView)findViewById(R.id.self_timeline_civ);
        ImageView ivsex=(ImageView)findViewById(R.id.self_timeline_sex);
        TextView signture=(TextView)findViewById(R.id.self_timeline_signture);
        TextView self_timeline_attention=(TextView)findViewById(R.id.self_timeline_attention);
        TextView self_timeline_fans=(TextView)findViewById(R.id.self_timeline_fans);
        TextView self_timeline_location=(TextView)findViewById(R.id.self_timeline_location);
        TextView self_timeline_school=(TextView)findViewById(R.id.self_timeline_school);
        TextView self_timeline_xizuo=(TextView)findViewById(R.id.self_timeline_xizuo);
        TextView self_timeline_job=(TextView)findViewById(R.id.self_timeline_job);


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

        setListViewHeightBaseAdapter(listView);



    }

    private void initData() {


        List<SelfTimelineList> lists1=new ArrayList<SelfTimelineList>();
        lists1.add(new SelfTimelineList("这棵树好漂亮",""));

        List<SelfTimelineList> lists2=new ArrayList<SelfTimelineList>();
        lists2.add(new SelfTimelineList("狼了一圈",""));
        lists2.add(new SelfTimelineList("好开心",""));

        ArrayList<SelfTimlineDetail> selftimelinedeatil=new ArrayList<SelfTimlineDetail>();
        selftimelinedeatil.add(new SelfTimlineDetail(lists1,"今天"));
        selftimelinedeatil.add(new SelfTimlineDetail(lists2,"1月14号"));

        selftimeline=new SelfTimeline("关注593", "阅粉5210万", "广州", "演员", "张小熊","", "广州大学",selftimelinedeatil, 1, "摩羯座", "个性签名");
    }


    public void setListViewHeightBaseAdapter(ListView listViewHeightBaseAdapter) {

        ListAdapter adapter=listViewHeightBaseAdapter.getAdapter();
        if(adapter==null){
            return;
        }
        int totalHeight=0;
        for(int i=0,len=adapter.getCount();i<len;i++){
            View listItem=adapter.getView(i,null,listViewHeightBaseAdapter);
            listItem.measure(0,0);
            totalHeight+=listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params=listViewHeightBaseAdapter.getLayoutParams();
        params.height=totalHeight+(listViewHeightBaseAdapter.getDividerHeight()*(adapter.getCount()-1));
        listViewHeightBaseAdapter.setLayoutParams(params);

    }
}
