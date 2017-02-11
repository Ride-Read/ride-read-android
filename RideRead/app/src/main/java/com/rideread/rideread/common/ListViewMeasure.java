package com.rideread.rideread.common;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Jackbing on 2017/2/10.
 */

public class ListViewMeasure {


    public static void setListViewHeightBaseAdapter(ListView listViewHeightBaseAdapter) {

        ListAdapter adapter=listViewHeightBaseAdapter.getAdapter();
        if(adapter==null){
            return;
        }
        int totalHeight=0;
        for(int i=0;i<adapter.getCount();i++){
            View listItem=adapter.getView(i,null,listViewHeightBaseAdapter);
            listItem.measure(0,0);
            totalHeight+=listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params=listViewHeightBaseAdapter.getLayoutParams();
        params.height=totalHeight+(listViewHeightBaseAdapter.getDividerHeight()*(adapter.getCount()-1));
        listViewHeightBaseAdapter.setLayoutParams(params);

    }
}
