package com.rideread.rideread.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rideread.rideread.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Jackbing on 2017/3/2.
 */

public class LikeLinearLayout extends LinearLayout {

    private Context context;
    private ViewGroup.LayoutParams lp;
    private int width;//系统分配的宽度
    private List<String> face_urls;//头像连接

    private LinearLayout containter;

    public LikeLinearLayout(Context context) {
        this(context,null);
    }

    public LikeLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }

    public LikeLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        containter=new LinearLayout(context);
        containter.setOrientation(LinearLayout.HORIZONTAL);
        containter.setGravity(LinearLayout.HORIZONTAL);
        lp=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(containter);
    }



    //传入所有点赞人的头像url
    public void addLikesMember(List<String> face_urls){

        this.face_urls=face_urls;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width=getMeasuredWidth();
        Log.e("all width","all width="+width);
        for(int i=0;i<face_urls.size();i++){
            CircleImageView img=new CircleImageView(context);
            img.setLayoutParams(lp);
            img.setImageResource(R.mipmap.me);
            int img_widthw=MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY);
            int img_height=MeasureSpec.makeMeasureSpec(0, MeasureSpec.EXACTLY);
            img.measure(img_widthw,img_height);
            int img_width=img.getMeasuredWidth();
            Log.e("---","width2========"+",img_width="+img_width);
            if(width>img_width) {
                containter.addView(img);
                containter.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
                containter.setDividerPadding(8);

                width -= (img_width + 8);
                Log.e("---", "width1=" + width);
            }else{
                break;
            }
//            }else{
//                CircleImageView imgs=new CircleImageView(context);
//                imgs.setImageResource(R.mipmap.likebar_after);
//                containter.addView(imgs);
//                Log.e("---","width1========");
//                break;
//            }
        }
    }
}
