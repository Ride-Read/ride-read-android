package com.rideread.rideread;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.rideread.rideread.adapter.RegionAdapter;
import com.rideread.rideread.bean.RegionModel;
import com.rideread.rideread.db.RegionDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackbing on 2017/2/1.
 * 选择地区
 */

public class MineEdtMsgDistrictActivity extends BaseActivity implements BaseQuickAdapter.OnRecyclerViewItemClickListener {

    public static final String REGION_PROVINCE = "region_province";
    public static final String REGION_CITY = "region_city";



    private RecyclerView mRecyclerView;
    private RegionAdapter mAdapter;
    private RegionDao mRegionDao;

    private List<RegionModel> mList;

    private List<RegionModel> mProvinceList;
    private List<RegionModel> mCityList;

    private int state = 0;

    private String mProvince;
    private String mCity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.mine_setting_selectdistrict_layout);
        mRegionDao = new RegionDao(this);

        mList = new ArrayList<RegionModel>();
        mAdapter = new RegionAdapter(mList);
        mAdapter.setOnRecyclerViewItemClickListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setAdapter(mAdapter);
        mProvinceList = mRegionDao.loadProvinceList();
        mAdapter.addData(mProvinceList);
    }

    @Override
    public void onItemClick(View view, int position) {

        RegionModel region = mAdapter.getItem(position);

        if (state == 0) {
            mCityList = mRegionDao.loadCityList(region.getId());

            mList.clear();
            mAdapter.addData(mCityList);

            mProvince = region.getName();
            state++;
        } else if (state == 1) {
            //mAreaList = mRegionDao.loadCountyList(region.getId());
            mCity = region.getName();
            finishSelect();
//            if (mAreaList.size() == 0) {
//                //防止有的城市没有县级
//                finishSelect();
//
//            } else {
//                mList.clear();
//                mAdapter.addData(mAreaList);
//
//                state++;
//            }
        }

//        } else if (state == 2) {
//            mArea = region.getName();
//            state++;
//
//            finishSelect();
//        }
    }

    /**
     * 完成
     */
    private void finishSelect() {
        Intent data = new Intent();
        data.putExtra(REGION_PROVINCE, mProvince);
        data.putExtra(REGION_CITY, mCity);

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (state == 0) {
            super.onBackPressed();
        }

        if (state == 1) {
            mList.clear();
            mAdapter.addData(mProvinceList);
            state--;
        }
//        } else if (state == 2) {
//            mList.clear();
//            mAdapter.addData(mCityList);
//            state--;
//        }
    }
}
