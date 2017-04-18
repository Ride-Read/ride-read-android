package com.rideread.rideread.module.profile.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rideread.rideread.R;
import com.rideread.rideread.common.adapter.FollowUserAdapter;
import com.rideread.rideread.common.base.BaseActivity;
import com.rideread.rideread.common.util.ListUtils;
import com.rideread.rideread.common.util.NetworkUtils;
import com.rideread.rideread.common.util.TitleBuilder;
import com.rideread.rideread.common.util.ToastUtils;
import com.rideread.rideread.data.result.FollowUser;
import com.rideread.rideread.function.net.retrofit.ApiUtils;
import com.rideread.rideread.function.net.retrofit.BaseCallback;
import com.rideread.rideread.function.net.retrofit.BaseModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by SkyXiao on 2017/4/6.
 * 关注人||粉丝界面
 */

public class FollowUserActivity extends BaseActivity {
    public static final int USER_TYPE_ATTENTION = 0;
    public static final int USER_TYPE_FANS = 1;
    public static final String USER_TYPE = "user_type";

    @BindView(R.id.recycle_view) RecyclerView mRecycleView;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;

    private FollowUserAdapter mUserAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<FollowUser> mUserList;

    private int mPages = 0;
    private boolean isLoadingMore;
    private int mUserType;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_common_recycle;
    }

    @Override
    public void initView() {
        mUserType = getIntent().getIntExtra(USER_TYPE, USER_TYPE_ATTENTION);
        initTopBar();

        mUserList = new ArrayList<>();
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mPages = 0;
            loadUsers();
        });

        mRecycleView.setHasFixedSize(true);

        boolean isFans = USER_TYPE_FANS == mUserType;
        mUserAdapter = new FollowUserAdapter(this, isFans, mUserList);
        mRecycleView.setAdapter(mUserAdapter);

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
                if (lastVisibleItem >= totalItemCount - 1 && dy > 0) {
                    if (!isLoadingMore) {
                        loadUsers();
                    }
                }
            }
        });
        loadUsers();

    }

    private void initTopBar() {
        int titleRes = R.string.attention;
        if (USER_TYPE_FANS == mUserType) {
            titleRes = R.string.fans;
        }
        new TitleBuilder(this).setTitleText(titleRes).IsBack(true).setLeftOnClickListener(v -> finish()).build();
    }

    private void loadUsers() {
        if (!NetworkUtils.isConnected()) {
            ToastUtils.show(R.string.network_error_fail);
        }
        isLoadingMore = true;
        if (0 == mPages) mSwipeRefreshLayout.setRefreshing(true);
        if (USER_TYPE_FANS == mUserType) {
            ApiUtils.loadFollowers(new BaseCallback<BaseModel<List<FollowUser>>>() {
                @Override
                protected void onSuccess(BaseModel<List<FollowUser>> model) throws Exception {
                    updateUserList(model);
                }

                @Override
                protected void onAfter() {
                    loadFinish();
                }
            });
        } else {
            ApiUtils.loadFollowing(new BaseCallback<BaseModel<List<FollowUser>>>() {
                @Override
                protected void onSuccess(BaseModel<List<FollowUser>> model) throws Exception {
                    updateUserList(model);
                }

                @Override
                protected void onAfter() {
                    loadFinish();
                }
            });
        }
    }

    private void updateUserList(BaseModel<List<FollowUser>> model) {
        List<FollowUser> tUsers = model.getData();
        if (!ListUtils.isEmpty(tUsers)) {
            if (0 == mPages) mUserList.clear();
            mUserList.addAll(tUsers);
            mUserAdapter.notifyDataSetChanged();
            mPages++;
        }
    }

    private void loadFinish() {
        isLoadingMore = false;
        if (mSwipeRefreshLayout.isRefreshing()) mSwipeRefreshLayout.setRefreshing(false);
    }


}
