package com.kuwai.ysy.module.circletwo;

import android.os.Bundle;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.adapter.vip.BannerAdapter;
import com.kuwai.ysy.utils.glide.GlideRoundLoader;
import com.kuwai.ysy.widget.BannerRound;
import com.kuwai.ysy.widget.ViewPagerIndicator;
import com.rayhahah.rbase.base.RBasePresenter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.view.BannerViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HoleListTwoFragment extends BaseFragment {

    private BannerAdapter mBannerAdapter;
    private BannerRound bannerRound;
    BannerViewPager mViewpager;
    private List<String> mBannerList = new ArrayList<>();
    private String[] data = new String[]{"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555060234382&di=2893f5c84494aef66b4537f07d4199e7&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F018c985837fb53a801219c77022553.jpg%402o.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555060234382&di=2893f5c84494aef66b4537f07d4199e7&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F018c985837fb53a801219c77022553.jpg%402o.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1555060234382&di=2893f5c84494aef66b4537f07d4199e7&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F018c985837fb53a801219c77022553.jpg%402o.jpg"
    };
    ViewPagerIndicator mIndicatorLine;

    @Override
    public void initView(Bundle savedInstanceState) {
        mIndicatorLine = mRootView.findViewById(R.id.indicator_line);
        bannerRound = mRootView.findViewById(R.id.banner);
        mViewpager = mRootView.findViewById(R.id.bannerViewPager);
        bannerRound.setImageLoader(new GlideRoundLoader());
        bannerRound.setImages(Arrays.asList(data));
        bannerRound.setDelayTime(3000);
        bannerRound.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        mIndicatorLine.setViewPager(mViewpager, 3);
        bannerRound.start();
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_hole_two_list;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }
}
