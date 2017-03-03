package com.rideread.rideread.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.rideread.rideread.R;
import com.rideread.rideread.activity.TimelineDetailsActivity;
import com.rideread.rideread.adapter.AttentionListAdapter;
import com.rideread.rideread.bean.TimeLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackbing on 2017/2/3.
 */

public class AttentionFragment extends Fragment {

    private List<TimeLine> lists;
    private ListView listview;
    private View mView;
    private int[] imgs={R.mipmap.me,R.mipmap.me,R.mipmap.me,
            R.mipmap.me,R.mipmap.me,R.mipmap.me,R.mipmap.me,R.mipmap.me,R.mipmap.me};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.timeline_attention_listview, container, false);
        initData();
        initView(mView);

        return mView;
    }

    private void initData() {
        lists=new ArrayList<TimeLine>();
        List<Integer> imgsL=new ArrayList<Integer>();
        for(int i=0;i<imgs.length;i++){
            imgsL.add(imgs[i]);
        }

        lists.add(new TimeLine("涨小明","56","10km",true,false,false,imgsL,"广州","","6分钟前",null,"10"));
        lists.add(new TimeLine("涨小明","56","10km",false,true,true,null,"广州","","6分钟前","今天风景很美，我先去看看","10"));
        lists.add(new TimeLine("涨小明","56","10km",true,true,false,imgsL,"广州","","6分钟前","哈哈，我到这里了，过来看看","10"));
        lists.add(new TimeLine("涨小明","56","10km",false,true,true,null,"广州","","6分钟前","不知道说什么，随便写写","10"));
        lists.add(new TimeLine("涨小明","56","10km",false,true,false,null,"广州","","6分钟前","今天风景很美，我先去看看","10"));
        lists.add(new TimeLine("涨小明","56","10km",true,true,false,imgsL,"广州","","6分钟前","哈哈，我到这里了，过来看看","10"));

    }

    private void initView(View mView) {
        listview=(ListView) mView.findViewById(R.id.timeline_attention_listview);

        View v=LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.timeline_newmsg_headview,null);
        listview.addHeaderView(v);
        AttentionListAdapter adapter=new AttentionListAdapter(lists,R.layout.timeline_attention_listitem,getContext());
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    Toast.makeText(getContext(),"点击了消息提示",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent=new Intent(AttentionFragment.this.getActivity(), TimelineDetailsActivity.class);
                    intent.putExtra("timeline",(TimeLine)parent.getAdapter().getItem(position));
                    startActivity(intent);
                }

            }
        });
    }

//    public List<Map<String,Object>> getDataList(){
//        List<Map<String,Object>> lists=new ArrayList<Map<String,Object>>();
//        for(int i=0;i<imgs.length;i++){
//            Map<String,Object> map=new HashMap<String,Object>();
//            map.put("image",imgs[i]);
//            lists.add(map);
//        }
//        return lists;
//    }
}
