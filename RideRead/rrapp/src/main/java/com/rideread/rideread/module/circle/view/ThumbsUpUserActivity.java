package com.rideread.rideread.module.circle.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rideread.rideread.R;
import com.rideread.rideread.common.adapter.ThumbsUpUserAdapter;
import com.rideread.rideread.common.base.BaseActivity;
import com.rideread.rideread.common.util.ListUtils;
import com.rideread.rideread.common.util.NetworkUtils;
import com.rideread.rideread.common.util.TitleBuilder;
import com.rideread.rideread.common.util.ToastUtils;
import com.rideread.rideread.data.result.ThumbsUpUser;
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

public class ThumbsUpUserActivity extends BaseActivity {

    public static final String MOMENT_ID = "moment_id";

    @BindView(R.id.recycle_view) RecyclerView mRecycleView;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;

    private ThumbsUpUserAdapter mUserAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<ThumbsUpUser> mUserList;

    private int mPages = 0;

    private boolean isLoadingMore;
    private int mMid;


    @Override
    public int getLayoutRes() {
        return R.layout.activity_common_recycle;
    }

    @Override
    public void initView() {

        mMid = getIntent().getIntExtra(MOMENT_ID, 0);

        new TitleBuilder(this).setTitleText(R.string.thumbs_up_user).IsBack(true).setRightImage(R.drawable.icon_search).build();

        mUserList = new ArrayList<>();
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mPages = 0;
            loadUsers();
        });

        mRecycleView.setHasFixedSize(true);
        mUserAdapter = new ThumbsUpUserAdapter(this, mUserList);
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


    private void loadUsers() {
        if (!NetworkUtils.isConnected()) {
            ToastUtils.show(R.string.network_error_fail);
        }
        if (0 == mMid) {
            ToastUtils.show("加载失败");
            return;
        }
        isLoadingMore = true;
        if (0 == mPages) mSwipeRefreshLayout.setRefreshing(true);
        ApiUtils.loadThumbsUpUser(mMid, mPages, new BaseCallback<BaseModel<List<ThumbsUpUser>>>() {
            @Override
            protected void onSuccess(BaseModel<List<ThumbsUpUser>> model) throws Exception {

                List<ThumbsUpUser> tUsers = model.getData();
                if (!ListUtils.isEmpty(tUsers)) {
                    if (0 == mPages) mUserList.clear();
                    mUserList.addAll(tUsers);
                    mUserAdapter.notifyDataSetChanged();
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

    @OnClick({R.id.img_top_bar_left, R.id.img_top_bar_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_top_bar_left:
                finish();
                break;
            case R.id.img_top_bar_right:
                ToastUtils.show("搜索");
                break;
        }
    }

}
