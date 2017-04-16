package com.rideread.rideread.module.circle.view;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.rideread.rideread.R;
import com.rideread.rideread.common.base.BaseFragment;
import com.rideread.rideread.module.profile.view.SettingActivity;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;


public class CircleFragment extends BaseFragment {

    @BindView(R.id.tl_circle_top) TabLayout mTlCircleTop;
    @BindView(R.id.viewpager) ViewPager mViewpager;
    Unbinder unbinder;

    private Fragment mNearbyFragment;
    private Fragment mAttentionFragment;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_main_circle;
    }

    @Override
    public void initView() {
        mTlCircleTop.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));

        LinearLayout linearLayout = (LinearLayout) mTlCircleTop.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerPadding(20);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(getContext(), R.drawable.tab_middle_divider));

        mNearbyFragment = MomentsFragment.newInstance(MomentsFragment.MOMENTS_TYPE_NEARBY);
        mAttentionFragment = MomentsFragment.newInstance(MomentsFragment.MOMENTS_TYPE_ATTENTION);

        FragmentAdapter adapter = new FragmentAdapter();
        mViewpager.setAdapter(adapter);

        mTlCircleTop.setupWithViewPager(mViewpager);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btn_search)
    public void onViewClicked() {
        getBaseActivity().gotoActivity(SearchActivity.class);
    }


    private class FragmentAdapter extends FragmentPagerAdapter {
        private final String[] tabTitle = getResources().getStringArray(R.array.circle_tab_title);

        public FragmentAdapter() {
            super(getChildFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 1:
                    return mAttentionFragment;
                default:
                    return mNearbyFragment;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitle[position];
        }
    }


}
