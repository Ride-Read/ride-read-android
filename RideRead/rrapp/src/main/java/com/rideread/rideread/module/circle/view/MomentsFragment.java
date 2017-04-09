package com.rideread.rideread.module.circle.view;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
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
import butterknife.ButterKnife;


public class MomentsFragment extends BaseFragment {
    public static String MOMENTS_TYPE = "moments_type";
    public static int MOMENTS_TYPE_NEARBY = 1;
    public static int MOMENTS_TYPE_ATTENTION = 0;
    private int mMomentsType;
    private List<Moment> mMoments;
    private MomentsAdapter mMomentsAdapter;


    @BindView(R.id.rv_moment_list) RecyclerView mRvMomentList;
    @BindView(R.id.srl_refresh) SwipeRefreshLayout mSrlRefresh;
    private int mPages = 0;
    private LinearLayoutManager mLayoutManager;
    private boolean isLoadingMore;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_nearby;
    }

    @Override
    public void initView() {
        mMomentsType = getArguments().getInt(MOMENTS_TYPE);
        mMoments = new ArrayList<>();
        mSrlRefresh.setOnRefreshListener(() -> {
            mPages = 0;
            loadMoments();
        });
        mRvMomentList.setHasFixedSize(true);
        mMomentsAdapter = new MomentsAdapter(mMoments);
        mRvMomentList.setAdapter(mMomentsAdapter);
        mLayoutManager = new LinearLayoutManager(getBaseActivity());
        mRvMomentList.setLayoutManager(mLayoutManager);
        mRvMomentList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (false) {
                    loadMoments();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = mLayoutManager.getItemCount();
                //lastVisibleItem >= totalItemCount - 4 表示剩下4个item自动加载，各位自由选择
                // dy>0 表示向下滑动
                if (lastVisibleItem >= totalItemCount - 4 && dy > 0) {
                    if (isLoadingMore) {
                        Logger.d(TAG, "ignore manually update!");
                    } else {
                        loadMoments();//这里多线程也要手动控制isLoadingMore
                        isLoadingMore = false;
                    }
                }
            }
        });


        loadMoments();
    }


    private void loadMoments() {
        isLoadingMore = true;
        mSrlRefresh.setRefreshing(true);
        ApiUtils.loadMoments(mPages, mMomentsType, new BaseCallback<BaseModel<List<Moment>>>() {
            @Override
            protected void onSuccess(BaseModel<List<Moment>> model) throws Exception {
                Logger.e("http", JSON.toJSONString(model));

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
                mSrlRefresh.setRefreshing(false);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
