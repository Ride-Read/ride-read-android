package com.rideread.rideread.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rideread.rideread.R;

/**
 * Created by Jackbing on 2017/2/13.
 */

public class TabAdapterImp extends AbstractTabAdapter {

    private DisplayMetrics displayMetrics;
    private Activity activity;

    public TabAdapterImp(Activity activity){
        this.activity=activity;
        displayMetrics=new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    }

    @Override
    public DisplayMetrics getDisplayMetrics() {
        return displayMetrics;
    }

    public void setDisplayMetrics(DisplayMetrics displayMetrics) {
        this.displayMetrics = displayMetrics;
    }

    @Override
    public View getView(int position) {

        Log.e("widthPixels","widthPixels------>>>>>>"+displayMetrics.widthPixels);
        LayoutInflater inflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView=inflater.inflate(R.layout.scrollview_tab_layout,null);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(displayMetrics.widthPixels/2, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout ly=(LinearLayout) mView.findViewById(R.id.tab_layout);
        ly.setLayoutParams(lp);
        Log.e("ly","ly------->>>"+ly.getLayoutParams().width);
        TextView title=(TextView)mView.findViewById(R.id.login_scrollview_title);
        title.setText(titles[position]);
        return mView;
    }
}
