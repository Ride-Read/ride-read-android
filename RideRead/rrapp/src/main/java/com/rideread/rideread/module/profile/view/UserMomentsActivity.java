package com.rideread.rideread.module.profile.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.facebook.drawee.view.SimpleDraweeView;
import com.rideread.rideread.R;
import com.rideread.rideread.common.adapter.UserMomentsAdapter;
import com.rideread.rideread.common.base.BaseActivity;
import com.rideread.rideread.common.dialog.ConfirmDialogFragment;
import com.rideread.rideread.common.util.ImgLoader;
import com.rideread.rideread.common.util.ListUtils;
import com.rideread.rideread.common.util.NetworkUtils;
import com.rideread.rideread.common.util.TitleBuilder;
import com.rideread.rideread.common.util.ToastUtils;
import com.rideread.rideread.common.util.UserUtils;
import com.rideread.rideread.common.widget.RecyclerViewHeader;
import com.rideread.rideread.data.result.DefJsonResult;
import com.rideread.rideread.data.result.DetailUserInfo;
import com.rideread.rideread.data.result.Moment;
import com.rideread.rideread.function.net.retrofit.ApiUtils;
import com.rideread.rideread.function.net.retrofit.BaseCallback;
import com.rideread.rideread.function.net.retrofit.BaseModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by SkyXiao on 2017/4/6.
 */

public class UserMomentsActivity extends BaseActivity {
    public final static String SELECTED_UID = "selected_uid";
    public final static String SELECTED_USERNAME = "selected_username";


    @BindView(R.id.view_header) RecyclerViewHeader mViewHeader;
    @BindView(R.id.recycle_view) RecyclerView mRecycleView;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.map_view_bg) TextureMapView mMapViewBg;
    @BindView(R.id.img_avatar) SimpleDraweeView mImgAvatar;
    @BindView(R.id.tv_name) TextView mTvName;
    @BindView(R.id.tv_tag_job) TextView mTvTagJob;
    @BindView(R.id.tv_tag_loc) TextView mTvTagLoc;
    @BindView(R.id.tv_tag_label) TextView mTvTagLabel;
    @BindView(R.id.tv_signature) TextView mTvSignature;
    @BindView(R.id.tv_attention) TextView mTvAttention;
    @BindView(R.id.tv_msg) TextView mTvMsg;
    @BindView(R.id.tv_fans) TextView mTvFans;
    @BindView(R.id.btn_attention) Button mBtnAttention;
    @BindView(R.id.btn_message) Button mBtnMessage;
    @BindView(R.id.ll_follow_action) LinearLayout mLlFollowAction;

    private AMap mAMap;
    private UiSettings mUiSettings;//定义一个UiSettings对象

    private List<Moment> mMoments;
    private UserMomentsAdapter mUserMomentsAdapter;
    private View mMomentsHeaderView;


    private int mPages = 0;
    private LinearLayoutManager mLayoutManager;
    private boolean isLoadingMore;
    private int mSelectedUid;
    private boolean isMyself;
    private DetailUserInfo mUserInfo;
    private boolean isFollow;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_user_moments;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapViewBg.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {

        mAMap = mMapViewBg.getMap();
        mUiSettings = mAMap.getUiSettings();//实例化UiSettings类对象
        mUiSettings.setZoomControlsEnabled(false);
        mAMap.moveCamera(CameraUpdateFactory.zoomTo(1F));

        mSelectedUid = getIntent().getIntExtra(SELECTED_UID, UserUtils.getUid());
        String username = getIntent().getStringExtra(SELECTED_USERNAME);
        if (TextUtils.isEmpty(username)) {
            username = "阅圈";
        } else {
            username = username + "的阅圈";
        }
        isMyself = mSelectedUid == UserUtils.getUid();
        new TitleBuilder(this).setTitleText(isMyself ? getString(R.string.my_circle) : username).IsBack(true).setLeftOnClickListener(v -> finish()).build();

        mMoments = new ArrayList<>();
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mPages = 0;
            loadUserMoments();
        });
        mRecycleView.setHasFixedSize(true);
        mUserMomentsAdapter = new UserMomentsAdapter(this, mMoments);
        //        LayoutInflater layoutInflater = getLayoutInflater();
        //        if (!isMyself) {
        //            mMomentsHeaderView = layoutInflater.inflate(R.layout.view_user_info, null);
        //            mUserMomentsAdapter.addHeadView(mMomentsHeaderView);
        //        }
        mRecycleView.setAdapter(mUserMomentsAdapter);

        mLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLayoutManager);


        mRecycleView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = mLayoutManager.getItemCount();
                // dy>0 表示向下滑动
                if (lastVisibleItem >= totalItemCount - 1 && dy > 0) {
                    if (!isLoadingMore) {
                        loadUserMoments();//这里多线程也要手动控制isLoadingMore
                    }
                }
            }
        });
        if (isMyself) {
            mViewHeader.setVisibility(View.GONE);
            mLlFollowAction.setVisibility(View.GONE);
        } else {
            mViewHeader.attachTo(mRecycleView);
            getUserInfo(mSelectedUid);
        }


        loadUserMoments();

    }

    private void getUserInfo(int selectedUid) {
        ApiUtils.showUserInfo(selectedUid, new BaseCallback<BaseModel<DetailUserInfo>>() {
            @Override
            protected void onSuccess(BaseModel<DetailUserInfo> model) throws Exception {
                mUserInfo = model.getData();
                if (null != mUserInfo) {
                    initHeaderView();
                }
            }
        });
    }

    private void initHeaderView() {
        ImgLoader.getInstance().displayImage(mUserInfo.getFaceUrl(), mImgAvatar);
        mTvName.setText(mUserInfo.getUsername());
        mTvName.setCompoundDrawablesWithIntrinsicBounds(0, 0, mUserInfo.getSex() == 1 ? R.drawable.ic_sex_man : R.drawable.ic_sex_female, 0);

        String signature = mUserInfo.getSignature();
        if (TextUtils.isEmpty(signature)) signature = "人生需要留白";
        mTvSignature.setText(signature);

        String job = mUserInfo.getCareer();
        if (TextUtils.isEmpty(job)) {
            mTvTagJob.setVisibility(View.GONE);
        } else {
            mTvTagJob.setVisibility(View.VISIBLE);
            mTvTagJob.setText(job);
        }
        String location = mUserInfo.getLocation();
        if (TextUtils.isEmpty(location)) {
            mTvTagLoc.setVisibility(View.GONE);
        } else {
            mTvTagLoc.setVisibility(View.VISIBLE);
            mTvTagLoc.setText(location);
        }
        List<String> tags = mUserInfo.getTags();
        if (ListUtils.isEmpty(tags)) {
            mTvTagLabel.setVisibility(View.GONE);
        } else {
            mTvTagLabel.setVisibility(View.VISIBLE);
            mTvTagLabel.setText(tags.get(0));
        }

        mTvMsg.setText("消息 0");//TODO 获取消息数
        mTvAttention.setText("关注 " + mUserInfo.getFollowing());
        mTvFans.setText("阅粉 " + mUserInfo.getFollower());

        isFollow = mUserInfo.getIsFollow() <= 1;
        refreshFollowAction(isFollow);
    }

    private void refreshFollowAction(boolean isFollow) {
        if (isFollow) {
            mBtnAttention.setBackgroundResource(R.drawable.btn_gray_sel);
            mBtnAttention.setTextColor(getResources().getColor(R.color.gray_text_55));
            mBtnAttention.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tick_mini, 0, 0, 0);
        } else {
            mBtnAttention.setBackgroundResource(R.drawable.btn_green_sel);
            mBtnAttention.setTextColor(getResources().getColor(R.color.white_normal));
            mBtnAttention.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add_vector, 0, 0, 0);
        }
    }

    private void loadUserMoments() {
        if (!NetworkUtils.isConnected()) {
            ToastUtils.show(R.string.network_error_fail);
        }
        isLoadingMore = true;
        if (0 == mPages) mSwipeRefreshLayout.setRefreshing(true);
        ApiUtils.showUserMoments(mSelectedUid, mPages, new BaseCallback<BaseModel<List<Moment>>>() {
            @Override
            protected void onSuccess(BaseModel<List<Moment>> model) throws Exception {

                List<Moment> tMoments = model.getData();
                if (!ListUtils.isEmpty(tMoments)) {
                    if (0 == mPages) mMoments.clear();

                    mMoments.addAll(tMoments);
                    mUserMomentsAdapter.notifyDataSetChanged();

                    mPages++;
                }
            }

            @Override
            protected void onAfter() {
                super.onAfter();
                isLoadingMore = false;
                if (mSwipeRefreshLayout.isRefreshing()) mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    @OnClick({R.id.btn_personality_map, R.id.btn_attention, R.id.btn_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_personality_map:
                gotoActivity(PersonalityMapActivity.class);
                break;
            case R.id.btn_attention:
                followUser();
                break;
            case R.id.btn_message:
                ToastUtils.show("发消息");
                break;
        }
    }

    private void followUser() {
        if (isFollow) {
            ConfirmDialogFragment unFollowDialog = ConfirmDialogFragment.newInstance(R.string.sure2unfollow);
            unFollowDialog.show(getSupportFragmentManager(), "dialog");

        } else {
            ApiUtils.follow(mSelectedUid, new BaseCallback<BaseModel<DefJsonResult>>() {
                @Override
                protected void onSuccess(BaseModel<DefJsonResult> model) throws Exception {
                    isFollow = true;
                    refreshFollowAction(isFollow);
                }
            });
        }

    }

    @Override
    public void doPositiveClick() {
        super.doPositiveClick();
        ApiUtils.unfollow(mSelectedUid, new BaseCallback<BaseModel<DefJsonResult>>() {
            @Override
            protected void onSuccess(BaseModel<DefJsonResult> model) throws Exception {
                isFollow = false;
                refreshFollowAction(isFollow);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapViewBg.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        mMapViewBg.onSaveInstanceState(bundle);
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapViewBg.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapViewBg.onDestroy();
    }
}
