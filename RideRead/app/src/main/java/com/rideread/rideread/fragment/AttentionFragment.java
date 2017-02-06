package com.rideread.rideread.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.rideread.rideread.R;
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

        lists.add(new TimeLine(true,false,false,imgsL,null));
        lists.add(new TimeLine(false,true,true,null,"今天风景很美，我先去看看"));
        lists.add(new TimeLine(true,true,false,imgsL,"哈哈，我到这里了，过来看看"));
        lists.add(new TimeLine(false,true,true,null,"不知道说什么，随便写写"));
        lists.add(new TimeLine(false,true,false,null,"今天风景很美，我先去看看"));
        lists.add(new TimeLine(true,true,false,imgsL,"哈哈，我到这里了，过来看看"));

    }

    private void initView(View mView) {
        listview=(ListView) mView.findViewById(R.id.timeline_attention_listview);
        AttentionListAdapter adapter=new AttentionListAdapter(lists,R.layout.timeline_attention_listitem,getContext());
        listview.setAdapter(adapter);
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
