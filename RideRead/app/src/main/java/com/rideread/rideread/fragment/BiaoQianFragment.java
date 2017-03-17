package com.rideread.rideread.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.rideread.rideread.R;
import com.rideread.rideread.adapter.LabelAdapter;
import com.rideread.rideread.bean.Label;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackbing on 2017/3/17.
 */

public class BiaoQianFragment extends DialogFragment {

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView=inflater.inflate(R.layout.biaoqian_fragment_layout,container,false);
        initView();
        return mView;
    }

    private void initView() {

        List<Label> labels=new ArrayList<Label>();
        labels.add(new Label(false,"随性"));
        labels.add(new Label(false,"喜欢简单"));
        labels.add(new Label(false,"理想主义"));
        labels.add(new Label(false,"热血"));
        labels.add(new Label(false,"安静"));
        labels.add(new Label(false,"萌萌哒"));
        labels.add(new Label(false,"女汉子"));
        labels.add(new Label(false,"强迫症"));
        ListView listView=(ListView)mView.findViewById(R.id.lable_listview);
        listView.setAdapter(new LabelAdapter(getContext(),labels,R.layout.label_listview_item));

    }


}
