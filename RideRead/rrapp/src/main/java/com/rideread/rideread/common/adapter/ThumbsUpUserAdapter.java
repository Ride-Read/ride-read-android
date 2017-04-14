package com.rideread.rideread.common.adapter;

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
import com.rideread.rideread.common.util.ToastUtils;
import com.rideread.rideread.common.util.Utils;
import com.rideread.rideread.data.result.DefJsonResult;
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

public class ThumbsUpUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<ThumbsUpUser> mUserList;
    private BaseActivity mActivity;

    public ThumbsUpUserAdapter(BaseActivity baseActivity, List<ThumbsUpUser> users) {
        this.mActivity = baseActivity;
        this.mUserList = users;
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

        ThumbsUpUser user = mUserList.get(position);

        ImgLoader.getInstance().displayImage(user.getFaceUrl(), holder.mImgAvatar);
        holder.mTvName.setText(user.getUsername());
        holder.mTvSignature.setText(user.getSignature());

        holder.mBtnAttention.setVisibility(View.VISIBLE);
        holder.mTvTime.setVisibility(View.GONE);
        boolean isAttent = 0 == user.getIsFollowed();
        holder.mBtnAttention.setBackgroundResource(isAttent ? R.drawable.icon_attented : R.drawable.icon_attention);
        holder.mBtnAttention.setOnClickListener(v -> {
            if (isAttent) {
                ApiUtils.unfollow(user.getUid(), new BaseCallback<BaseModel<DefJsonResult>>() {
                    @Override
                    protected void onSuccess(BaseModel<DefJsonResult> model) throws Exception {
                        //                        holder.mBtnAttention.setBackgroundResource(R.drawable.icon_attention);
                        //                        user.setIsFollowed(-1);
                        //                        notifyDataSetChanged();
                    }
                });
            } else {
                ApiUtils.follow(user.getUid(), new BaseCallback<BaseModel<DefJsonResult>>() {
                    @Override
                    protected void onSuccess(BaseModel<DefJsonResult> model) throws Exception {
                        holder.mBtnAttention.setVisibility(View.GONE);
                        //                        holder.mBtnAttention.setBackgroundResource(R.drawable.icon_attented);
                        //                        user.setIsFollowed(1);
                        //                        notifyDataSetChanged();
                    }
                });
            }
        });

        holder.mImgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show("进入个人页面");
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
