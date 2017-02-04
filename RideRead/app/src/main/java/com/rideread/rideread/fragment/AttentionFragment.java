package com.rideread.rideread.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.rideread.rideread.R;
import com.rideread.rideread.adapter.AttentionListAdapter;
import com.rideread.rideread.bean.TimeLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jackbing on 2017/2/3.
 */

public class AttentionFragment extends Fragment {

    private List<TimeLine> lists;
    private ListView listview;
    private GridView gridView;
    private String text="hjvghajvghdvgahjdvgahj";
    private View mView;
    private int[] imgs={R.mipmap.me,R.mipmap.me,R.mipmap.me,
            R.mipmap.me,R.mipmap.me,R.mipmap.me,R.mipmap.me,R.mipmap.me,R.mipmap.me};
    private String[] from={"image"};
    private int[] to={R.id.timeline_grid_img};
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
        lists.add(new TimeLine(false,true,null,text,null,null,null,0));
        lists.add(new TimeLine(true,false,imgs,null,from,to,getDataList(),R.layout.timeline_grid_item));
        lists.add(new TimeLine(false,true,null,text,null,null,null,0));
        lists.add(new TimeLine(true,false,imgs,null,from,to,getDataList(),R.layout.timeline_grid_item));
        lists.add(new TimeLine(false,true,null,text,null,null,null,0));
        lists.add(new TimeLine(true,false,imgs,null,from,to,getDataList(),R.layout.timeline_grid_item));
        lists.add(new TimeLine(false,true,null,text,null,null,null,0));
        lists.add(new TimeLine(true,false,imgs,null,from,to,getDataList(),R.layout.timeline_grid_item));
        lists.add(new TimeLine(false,true,null,text,null,null,null,0));
        lists.add(new TimeLine(true,false,imgs,null,from,to,getDataList(),R.layout.timeline_grid_item));
        lists.add(new TimeLine(false,true,null,text,null,null,null,0));
        lists.add(new TimeLine(true,false,imgs,null,from,to,getDataList(),R.layout.timeline_grid_item));

    }

    private void initView(View mView) {
        listview=(ListView) mView.findViewById(R.id.timeline_attention_listview);
        AttentionListAdapter adapter=new AttentionListAdapter(lists,R.layout.timeline_attention_listitem,getContext());
        listview.setAdapter(adapter);
    }

    public List<Map<String,Object>> getDataList(){
        List<Map<String,Object>> lists=new ArrayList<Map<String,Object>>();
        for(int i=0;i<imgs.length;i++){
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("image",imgs[i]);
            lists.add(map);
        }
        return lists;
    }
}
