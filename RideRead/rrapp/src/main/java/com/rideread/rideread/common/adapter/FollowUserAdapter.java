package com.rideread.rideread.common.adapter;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseActivity;
import com.rideread.rideread.common.util.ImgLoader;
import com.rideread.rideread.common.util.Utils;
import com.rideread.rideread.data.result.DefJsonResult;
import com.rideread.rideread.data.result.FollowUser;
import com.rideread.rideread.function.net.retrofit.ApiUtils;
import com.rideread.rideread.function.net.retrofit.BaseCallback;
import com.rideread.rideread.function.net.retrofit.BaseModel;
import com.rideread.rideread.module.profile.view.UserMomentsActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SkyXiao on 2017/4/9.
 */

public class FollowUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<FollowUser> mUserList;
    private BaseActivity mActivity;
    private boolean isFans;

    public FollowUserAdapter(BaseActivity baseActivity, boolean isFans, List<FollowUser> users) {
        this.mActivity = baseActivity;
        this.mUserList = users;
        this.isFans = isFans;
        mLayoutInflater = LayoutInflater.from(Utils.getAppContext());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.view_item_user, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder tHolder, int position) {
        UserViewHolder holder = (UserViewHolder) tHolder;

        FollowUser user = mUserList.get(position);

        if (isFans) {

            ImgLoader.getInstance().displayImage(user.getFollowerFaceUrl(), holder.mImgAvatar);

            holder.mTvName.setText(user.getFollowerUsername());
            holder.mTvSignature.setText(user.getFollowerSignature());

            holder.mBtnAttention.setVisibility(View.VISIBLE);
            holder.mTvTime.setVisibility(View.GONE);
            //            holder.mTvTime.setText(getFriendlyTimeSpanByNow(((UserInfo) user).getCreatedAt()));
            holder.mBtnAttention.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ApiUtils.follow(user.getTid(), new BaseCallback<BaseModel<DefJsonResult>>() {
                        @Override
                        protected void onSuccess(BaseModel<DefJsonResult> model) throws Exception {
                            holder.mBtnAttention.setVisibility(View.GONE);
                        }
                    });

                }
            });

        } else {
            ImgLoader.getInstance().displayImage(user.getFollowedFaceUrl(), holder.mImgAvatar);

            holder.mTvName.setText(user.getFollowedUsername());
            holder.mTvSignature.setText(user.getFollowedSignature());

            holder.mBtnAttention.setVisibility(View.GONE);
            holder.mTvTime.setVisibility(View.VISIBLE);
            //            holder.mTvTime.setText(getFriendlyTimeSpanByNow(((UserInfo) user).getCreatedAt()));
        }

        holder.mImgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(UserMomentsActivity.SELECTED_UID, isFans ? user.getFid() : user.getTid());
                bundle.putString(UserMomentsActivity.SELECTED_USERNAME, isFans ? user.getFollowerUsername() : user.getFollowedUsername());
                mActivity.gotoActivity(UserMomentsActivity.class, bundle);
            }
        });
        //        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }


    class UserViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_avatar) SimpleDraweeView mImgAvatar;
        @BindView(R.id.tv_name) TextView mTvName;
        @BindView(R.id.tv_signature) TextView mTvSignature;
        @BindView(R.id.btn_attention) ImageButton mBtnAttention;
        @BindView(R.id.tv_time) TextView mTvTime;

        public UserViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
