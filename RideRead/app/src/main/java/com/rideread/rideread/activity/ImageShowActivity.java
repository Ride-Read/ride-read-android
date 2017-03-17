package com.rideread.rideread.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.rideread.rideread.R;
import com.rideread.rideread.adapter.ImagePagerAdapter;
import com.rideread.rideread.event.ImageIndexEvent;
import com.rideread.rideread.widget.ImageViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by Jackbing on 2017/3/12.
 */

public class ImageShowActivity extends BaseActivity implements View.OnLongClickListener{

    private List<String> list;
    private int index;
    private ImageViewPager imageViewPager;
    private TextView currentid;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.image_show);
        if(getIntent()!=null){
            list=getIntent().getStringArrayListExtra("imgurls");
            index=getIntent().getIntExtra("index",0);
        }
        initView();
        EventBus.getDefault().register(this);

    }
    public void initView(){

        imageViewPager=(ImageViewPager)findViewById(R.id.imageviewpager);
        currentid=(TextView)findViewById(R.id.current_id);
        TextView total=(TextView)findViewById(R.id.total_size);
        imageViewPager.setAdapter(new ImagePagerAdapter(getSupportFragmentManager(),list));
        imageViewPager.setCurrentItem(index);
        imageViewPager.setOnLongClickListener(this);
        currentid.setText(index+1+"");
        total.setText(list.size()+"");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ImageIndexEvent e){
        currentid.setText(e.getIndex()+1+"");
    }

    @Override
    public boolean onLongClick(View v) {

        PopupWindow popwindow=showSavePopWindow();
        if(popwindow!=null&&!popwindow.isShowing()){
            popwindow.showAtLocation(v, Gravity.BOTTOM,0,0);
        }
        return true;
    }

    public PopupWindow showSavePopWindow() {

        try{
            View v= getLayoutInflater().inflate(R.layout.savepic_layout, null);
            final PopupWindow popup=new PopupWindow(v, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ColorDrawable cd = new ColorDrawable(Color.WHITE);

            popup.setBackgroundDrawable(cd);
            popup.setAnimationStyle(R.style.popup_anim_style);

            popup.setOutsideTouchable(true);
            popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                }
            });
            popup.setFocusable(true);
            v.findViewById(R.id.save_tv).setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    ImageShowActivity.this.showToast("保存未实现");
                    popup.dismiss();
                }

            });
            v.findViewById(R.id.cancel_tv).setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    popup.dismiss();

                }

            });
            return popup;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
