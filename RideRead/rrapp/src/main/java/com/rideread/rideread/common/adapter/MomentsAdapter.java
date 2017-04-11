package com.rideread.rideread.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.rideread.rideread.R;
import com.rideread.rideread.common.util.ImgLoader;
import com.rideread.rideread.common.util.ListUtils;
import com.rideread.rideread.common.util.PicassoImgLoader;
import com.rideread.rideread.common.util.ToastUtils;
import com.rideread.rideread.common.util.Utils;
import com.rideread.rideread.data.result.Comment;
import com.rideread.rideread.data.result.DefJsonResult;
import com.rideread.rideread.data.result.Moment;
import com.rideread.rideread.data.result.MomentUser;
import com.rideread.rideread.data.result.ThumbsUpUser;
import com.rideread.rideread.function.net.retrofit.ApiUtils;
import com.rideread.rideread.function.net.retrofit.BaseCallback;
import com.rideread.rideread.function.net.retrofit.BaseModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SkyXiao on 2017/4/9.
 */

public class MomentsAdapter extends RecyclerView.Adapter<MomentsAdapter.ViewHolder> {


    private LayoutInflater mLayoutInflater;
    private List<Moment> mMomentList;


    public MomentsAdapter(List<Moment> moments) {
        this.mMomentList = moments;
        mLayoutInflater = LayoutInflater.from(Utils.getAppContext());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.view_moment, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Moment moment = mMomentList.get(position);

        MomentUser user = moment.getUser();

        ImgLoader.getInstance().displayImage(user.getFaceUrl(), holder.mImgAvatar);
        holder.mTvName.setText(user.getUsername());

        String msg = moment.getMsg();
        if (!TextUtils.isEmpty(msg)) {
            holder.mTvMomentText.setText(msg);
            holder.mTvMomentText.setVisibility(View.VISIBLE);
        } else {
            holder.mTvMomentText.setVisibility(View.GONE);
        }

        List<String> pictures = moment.getPictures();
        if (!ListUtils.isEmpty(pictures)) {
            holder.mNineGridImgView.setAdapter(adapter);
            holder.mNineGridImgView.setImagesData(pictures);
            holder.mNineGridImgView.setVisibility(View.VISIBLE);
        } else {
            holder.mNineGridImgView.setVisibility(View.GONE);
        }

        boolean isAttent = 0 == user.getIsFollowed();
        holder.mBtnAttention.setBackgroundResource(isAttent ? R.drawable.icon_attented : R.drawable.icon_attention);
        holder.mBtnAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAttent) {
                    ApiUtils.unfollow(user.getUid(), new BaseCallback<BaseModel<DefJsonResult>>() {
                        @Override
                        protected void onSuccess(BaseModel<DefJsonResult> model) throws Exception {
                            holder.mBtnAttention.setBackgroundResource( R.drawable.icon_attention);
                            user.setIsFollowed(-1);
                        }
                    });
                } else {
                    ApiUtils.follow(user.getUid(), new BaseCallback<BaseModel<DefJsonResult>>() {
                        @Override
                        protected void onSuccess(BaseModel<DefJsonResult> model) throws Exception {
                            holder.mBtnAttention.setBackgroundResource( R.drawable.icon_attented );
                            user.setIsFollowed(1);
                        }
                    });
                }
            }
        });

        holder.mBtnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiUtils.addThumbsUp(moment.getMid(), new BaseCallback<BaseModel<DefJsonResult>>() {
                    @Override
                    protected void onSuccess(BaseModel<DefJsonResult> model) throws Exception {
                        ToastUtils.show("点赞成功");
                    }
                });
            }
        });
        holder.mTvTime.setText(moment.getCreatedAt() + "");
        List<ThumbsUpUser> thumbsUp = moment.getThumbsUp();
        int likeCount = ListUtils.isEmpty(thumbsUp) ? 0 : thumbsUp.size();
        holder.mTvLikeCount.setText(likeCount + "");
        List<Comment> comments = moment.getComment();
        int commentSize = ListUtils.isEmpty(comments) ? 0 : comments.size();
        holder.mTvCommentCount.setText(commentSize + "");
        holder.mTvLocInfo.setText(moment.getLatitude() + "-" + moment.getLongitude() + " 距离我" + moment.getDistanceString());//TODO
        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return mMomentList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_avatar) SimpleDraweeView mImgAvatar;
        @BindView(R.id.tv_name) TextView mTvName;
        @BindView(R.id.btn_attention) ImageButton mBtnAttention;
        @BindView(R.id.tv_time) TextView mTvTime;
        @BindView(R.id.tv_moment_text) TextView mTvMomentText;
        @BindView(R.id.nine_grid_img_view) NineGridImageView mNineGridImgView;
        @BindView(R.id.tv_loc_info) TextView mTvLocInfo;
        @BindView(R.id.btn_like) ImageButton mBtnLike;
        @BindView(R.id.tv_like_count) TextView mTvLikeCount;
        @BindView(R.id.btn_comment) ImageButton mBtnComment;

        @BindView(R.id.tv_comment_count) TextView mTvCommentCount;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    NineGridImageViewAdapter<String> adapter = new NineGridImageViewAdapter<String>() {
        @Override
        protected void onDisplayImage(Context context, ImageView imageView, String url) {
            PicassoImgLoader.displayImage(imageView, url);
        }
    };

}
