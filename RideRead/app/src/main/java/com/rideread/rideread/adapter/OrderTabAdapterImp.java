package com.rideread.rideread.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rideread.rideread.R;

/**
 * Created by Jackbing on 2016/12/24.
 */

public class OrderTabAdapterImp extends OrderTabAdapter{

    DisplayMetrics dm;
    private Fragment fm;

    public OrderTabAdapterImp(Fragment fr) {
        this.fm=fr;
        dm=new DisplayMetrics();
        fr.getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
    }

    @Override
    public View getView(int position) {

        LayoutInflater inflater=(LayoutInflater) fm.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView=inflater.inflate(R.layout.main_timeline_tab_layout,null);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(dm.widthPixels/2, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout ly=(LinearLayout) mView.findViewById(R.id.main_timeline_tab_linear);
        ly.setLayoutParams(lp);
        TextView tv1=(TextView)mView.findViewById(R.id.main_timeline_tab_name);
        tv1.setText(orderTitle[position]);

        return mView;
    }

    @Override
    public DisplayMetrics getDm() {
        return dm;
    }

    public void setDm(DisplayMetrics dm) {
        this.dm = dm;
    }
}
