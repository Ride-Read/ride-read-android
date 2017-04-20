package com.rideread.rideread.module.profile.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rideread.rideread.R;
import com.rideread.rideread.common.adapter.CollectAdapter;
import com.rideread.rideread.common.base.BaseActivity;
import com.rideread.rideread.common.util.ListUtils;
import com.rideread.rideread.common.util.NetworkUtils;
import com.rideread.rideread.common.util.TitleBuilder;
import com.rideread.rideread.common.util.ToastUtils;
import com.rideread.rideread.data.result.CollectInfo;
import com.rideread.rideread.function.net.retrofit.ApiUtils;
import com.rideread.rideread.function.net.retrofit.BaseCallback;
import com.rideread.rideread.function.net.retrofit.BaseModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by SkyXiao on 2017/4/6.
 */

public class CollectActivity extends BaseActivity {


    @BindView(R.id.recycle_view) RecyclerView mRecycleView;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;

    private CollectAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<CollectInfo> mCollectList;

    private int mPages = 0;
    private boolean isLoadingMore;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_common_recycle;
    }

    @Override
    public void initView() {
        new TitleBuilder(this).setTitleText("我的收藏").IsBack(true).setLeftOnClickListener(v -> finish()).build();


        mCollectList = new ArrayList<>();
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mPages = 0;
            loadCollects();
        });

        mRecycleView.setHasFixedSize(true);

        mAdapter = new CollectAdapter(this, mCollectList);
        mRecycleView.setAdapter(mAdapter);

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
                        loadCollects();
                    }
                }
            }
        });
        loadCollects();
    }

    private void loadCollects() {
        if (!NetworkUtils.isConnected()) {
            ToastUtils.show(R.string.network_error_fail);
        }
        isLoadingMore = true;
        if (0 == mPages) mSwipeRefreshLayout.setRefreshing(true);
        ApiUtils.loadCollectMoment(new BaseCallback<BaseModel<List<CollectInfo>>>() {
            @Override
            protected void onSuccess(BaseModel<List<CollectInfo>> model) throws Exception {
                List<CollectInfo> tUsers = model.getData();
                if (!ListUtils.isEmpty(tUsers)) {
                    if (0 == mPages) mCollectList.clear();
                    mCollectList.addAll(tUsers);
                    mAdapter.notifyDataSetChanged();
                    mPages++;
                }
            }

            @Override
            protected void onAfter() {
                isLoadingMore = false;
                if (mSwipeRefreshLayout.isRefreshing()) mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }


}
