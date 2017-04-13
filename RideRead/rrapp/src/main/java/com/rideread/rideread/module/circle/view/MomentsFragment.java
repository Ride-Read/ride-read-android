package com.rideread.rideread.module.circle.view;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.rideread.rideread.R;
import com.rideread.rideread.common.adapter.MomentsAdapter;
import com.rideread.rideread.common.base.BaseFragment;
import com.rideread.rideread.common.util.ListUtils;
import com.rideread.rideread.data.Logger;
import com.rideread.rideread.data.result.Moment;
import com.rideread.rideread.function.net.retrofit.ApiUtils;
import com.rideread.rideread.function.net.retrofit.BaseCallback;
import com.rideread.rideread.function.net.retrofit.BaseModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class MomentsFragment extends BaseFragment {
    public static String MOMENTS_TYPE = "moments_type";
    public static int MOMENTS_TYPE_NEARBY = 1;
    public static int MOMENTS_TYPE_ATTENTION = 0;
    private int mMomentsType;
    private List<Moment> mMoments;
    private MomentsAdapter mMomentsAdapter;


    @BindView(R.id.recycle_view) RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;
    private int mPages = 0;
    private LinearLayoutManager mLayoutManager;
    private boolean isLoadingMore;

    @Override
    public int getLayoutRes() {
        return R.layout.common_recycle_view;
    }

    @Override
    public void initView() {
        mMomentsType = getArguments().getInt(MOMENTS_TYPE);
        mMoments = new ArrayList<>();
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mPages = 0;
            loadMoments();
        });
        mRecyclerView.setHasFixedSize(true);
        mMomentsAdapter = new MomentsAdapter(getBaseActivity(), mMoments);
        LayoutInflater layoutInflater = getBaseActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.view_msg_tips, null);
        mMomentsAdapter.addHeadView(view);
        mRecyclerView.setAdapter(mMomentsAdapter);

        mLayoutManager = new LinearLayoutManager(getBaseActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = mLayoutManager.getItemCount();
                //lastVisibleItem >= totalItemCount - 4 表示剩下4个item自动加载，各位自由选择
                // dy>0 表示向下滑动
                if (lastVisibleItem >= totalItemCount - 2 && dy > 0) {
                    if (isLoadingMore) {
                        isLoadingMore = false;
                        Logger.d(TAG, "ignore manually update!");
                    } else {
                        loadMoments();//这里多线程也要手动控制isLoadingMore
                        isLoadingMore = true;
                    }
                }
            }
        });


        loadMoments();
    }


    private void loadMoments() {
        isLoadingMore = true;
        mSwipeRefreshLayout.setRefreshing(true);
        ApiUtils.loadMoments(mPages, mMomentsType, new BaseCallback<BaseModel<List<Moment>>>() {
            @Override
            protected void onSuccess(BaseModel<List<Moment>> model) throws Exception {

                List<Moment> tMoments = model.getData();
                if (!ListUtils.isEmpty(tMoments)) {
                    if (0 == mPages) mMoments.clear();

                    mMoments.addAll(tMoments);
                    mMomentsAdapter.notifyDataSetChanged();

                    mPages++;
                }

            }

            @Override
            protected void onAfter() {
                super.onAfter();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public static MomentsFragment newInstance(int type) {
        MomentsFragment fragment = new MomentsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(MOMENTS_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

}
