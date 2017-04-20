package com.rideread.rideread.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseActivity;
import com.rideread.rideread.common.util.DateUtils;
import com.rideread.rideread.common.util.ImgLoader;
import com.rideread.rideread.common.util.Utils;
import com.rideread.rideread.data.result.CollectInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by SkyXiao on 2017/4/9.
 */

public class CollectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int TYPE_RELATION_ATTENTION = 1;
    private final int TYPE_RELATION_FANS = 2;
    private final int TYPE_RELATION_NEARBY = 3;

    private LayoutInflater mLayoutInflater;
    private List<CollectInfo> mList;
    private BaseActivity mActivity;
    private boolean isFans;

    public CollectAdapter(BaseActivity baseActivity, List<CollectInfo> collectInfoList) {
        this.mActivity = baseActivity;
        this.mList = collectInfoList;
        mLayoutInflater = LayoutInflater.from(Utils.getAppContext());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.view_collect_moment, parent, false);
        return new CollectHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder tHolder, int position) {
        CollectHolder holder = (CollectHolder) tHolder;

        CollectInfo info = mList.get(position);
        ImgLoader.getInstance().displayImage(info.getFirstPicture(), holder.mImgCover);
        ImgLoader.getInstance().displayImage(info.getFaceUrl(), holder.mImgAvatar);
        holder.mTvName.setText(info.getUsername());
//        holder.mTvContent.setText(info.get);
        holder.mTvTime.setText(DateUtils.getDateDayFormat(info.getCreateAt()));
        String relationType = "附近";
        switch (info.getType()) {
            case TYPE_RELATION_ATTENTION:
                relationType = "关注";
                break;
            case TYPE_RELATION_FANS:
                relationType = "附近";
                break;
            default:
                break;
        }
        holder.mTvRelation.setText(relationType);

        //        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class CollectHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_cover) SimpleDraweeView mImgCover;
        @BindView(R.id.img_avatar) SimpleDraweeView mImgAvatar;
        @BindView(R.id.tv_name) TextView mTvName;
        @BindView(R.id.tv_time) TextView mTvTime;
        @BindView(R.id.tv_relation) TextView mTvRelation;
        @BindView(R.id.tv_content) TextView mTvContent;

        public CollectHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
