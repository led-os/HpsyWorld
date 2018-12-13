package com.kuwai.ysy.module.mine.business.vip;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.adapter.vip.BannerAdapter;
import com.kuwai.ysy.utils.ZoomOutPageTransformer;
import com.kuwai.ysy.widget.ViewPagerIndicator;
import com.rayhahah.rbase.base.RBasePresenter;

public class VipDetailFragment extends BaseFragment implements View.OnClickListener {

    private BannerAdapter mBannerAdapter;
    ViewPager mViewpager;
    ViewPagerIndicator mIndicatorLine;

    public static VipDetailFragment newInstance() {
        Bundle args = new Bundle();
        VipDetailFragment fragment = new VipDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_vip_detail;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mViewpager = mRootView.findViewById(R.id.viewpager);
        mIndicatorLine = mRootView.findViewById(R.id.indicator_line);
        initFirstBanner();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    private void initFirstBanner() {
        mBannerAdapter = new BannerAdapter(getActivity(), mViewpager);
        mViewpager.setAdapter(mBannerAdapter);
        mViewpager.setOffscreenPageLimit(2);//预加载2个
        mViewpager.setPageMargin(20);//设置viewpage之间的间距
        mViewpager.setClipChildren(false);
        mViewpager.setPageTransformer(true, new ZoomOutPageTransformer());
        mIndicatorLine.setViewPager(mViewpager, 6);
        mBannerAdapter.setItemClickListener(new BannerAdapter.ItemClickListener() {

            @Override
            public void onItemClick(int index) {
//                ToastUtils.showToast("点击了图片" + index);
            }
        });
        mViewpager.setCurrentItem(0);
    }
}
