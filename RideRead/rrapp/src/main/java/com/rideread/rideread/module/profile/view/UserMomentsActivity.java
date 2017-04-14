package com.rideread.rideread.module.profile.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.rideread.rideread.R;
import com.rideread.rideread.common.adapter.UserMomentsAdapter;
import com.rideread.rideread.common.base.BaseActivity;
import com.rideread.rideread.common.util.ListUtils;
import com.rideread.rideread.common.util.NetworkUtils;
import com.rideread.rideread.common.util.TitleBuilder;
import com.rideread.rideread.common.util.ToastUtils;
import com.rideread.rideread.common.util.UserUtils;
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
    public   final static String SELECTED_UID = "selected_uid";
    public final static String SELECTED_USERNAME = "selected_username";


    @BindView(R.id.recycle_view) RecyclerView mRecycleView;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;

    private List<Moment> mMoments;
    private UserMomentsAdapter mUserMomentsAdapter;
    private View mMomentsHeaderView;


    private int mPages = 0;
    private LinearLayoutManager mLayoutManager;
    private boolean isLoadingMore;
    private int mSelectedUid;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_common_recycle;
    }

    @Override
    public void initView() {
        mSelectedUid = getIntent().getIntExtra(SELECTED_UID, UserUtils.getUid());
        String username = getIntent().getStringExtra(SELECTED_USERNAME);
        if (TextUtils.isEmpty(username)) username = "阅圈";
        new TitleBuilder(this).setTitleText(mSelectedUid == UserUtils.getUid() ? getString(R.string.my_circle) : username).IsBack(true).build();

        mMoments = new ArrayList<>();
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mPages = 0;
            loadUserMoments();
        });
        mRecycleView.setHasFixedSize(true);
        mUserMomentsAdapter = new UserMomentsAdapter(this, mMoments);
        LayoutInflater layoutInflater = getLayoutInflater();
        mMomentsHeaderView = layoutInflater.inflate(R.layout.view_msg_tips, null);
        //        mMomentsAdapter.addHeadView(mViewMsgTips);
        //        mViewMsgTips.setVisibility(View.GONE);
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


        loadUserMoments();

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

    @OnClick(R.id.img_top_bar_left)
    public void onViewClicked() {
        finish();
    }
}
