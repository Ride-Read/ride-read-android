package com.rideread.rideread.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rideread.rideread.R;
import com.rideread.rideread.bean.RegionModel;

import java.util.List;

/**
 * Created by Jackbing on 2017/2/1.
 */

public class RegionAdapter extends BaseQuickAdapter<RegionModel> {
    public RegionAdapter(List<RegionModel> data) {
        super(R.layout.mine_setting_district_item, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, RegionModel regionModel) {
        holder.setText(R.id.mine_tv_district_name, regionModel.getName());
    }
}
