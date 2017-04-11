package com.rideread.rideread.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.rideread.rideread.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SkyXiao on 2017/4/11.
 */

public class PostPicAdapter extends RecyclerView.Adapter<PostPicAdapter.ViewHolder> {


    private LayoutInflater mInflater;
    private List<String> mSelectedPics;

    public PostPicAdapter(Context context, List<String> pictures) {
        mInflater = LayoutInflater.from(context);
        mSelectedPics = pictures;
    }


    @Override
    public int getItemCount() {
        return mSelectedPics.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.view_post_item_image, viewGroup, false);
        return new ViewHolder(view);
    }


    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {

        //        ImgLoader.getInstance().displayImage(mSelectedPics.get(i),viewHolder.mImgPicture);
        viewHolder.mImgPicture.setImageURI("file://" + mSelectedPics.get(i));
        viewHolder.mImgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedPics.remove(i);
                //                notifyItemRemoved(i);
                notifyDataSetChanged();
            }
        });
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_picture) SimpleDraweeView mImgPicture;
        @BindView(R.id.img_delete) ImageView mImgDelete;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}