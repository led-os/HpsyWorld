package com.kuwai.ysy.module.mine.business.vip;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.adapter.vip.VipPagerAdapter;
import com.kuwai.ysy.module.mine.bean.vip.VipBannerBean;
import com.kuwai.ysy.utils.BaseLinkPageChangeListener;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;
import java.util.List;

public class VipCenterFragment extends BaseFragment implements View.OnClickListener {

    private int[] mPics = new int[]{R.drawable.bg_huangjin, R.drawable.bg_bojin, R.drawable.bg_zaunshi, R.drawable.bg_super};
    private String[] mTitles = new String[]{"黄金会员VIP", "铂金会员VIP", "钻石会员VIP", "高级会员VIP"};
    private int[] mImgs = new int[]{R.drawable.center_vip_bg_pud, R.drawable.center_vip_bg_baijin, R.drawable.center_vip_bg_zuanshi, R.drawable.center_vip_bg_super};
    private List<VipBannerBean> mBannerList = new ArrayList<>();

    private ViewPager headerVp;
    private ViewPager bodyVp;
    private List<Fragment> fragments;

    public static VipCenterFragment newInstance() {
        Bundle args = new Bundle();
        VipCenterFragment fragment = new VipCenterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.my_member_center_fragment;
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
        headerVp = (ViewPager) mRootView.findViewById(R.id.viewPager);
        bodyVp = (ViewPager) mRootView.findViewById(R.id.viewPager1);
        //设置适配器
        for (int i = 0; i < mPics.length; i++) {
            mBannerList.add(new VipBannerBean(mTitles[i], mPics[i], mImgs[i]));
        }

        headerVp.setAdapter(new VipPagerAdapter(getActivity(), mBannerList));
        headerVp.setPageMargin(30);
        headerVp.setOffscreenPageLimit(mPics.length);
        bodyVp.setOffscreenPageLimit(mPics.length);
        fragments = new ArrayList<Fragment>();
        fragments.add(VipHuangjinFragment.newInstance());
        fragments.add(VipBaijinFragment.newInstance());
        fragments.add(VipZuanshiFragment.newInstance());
        fragments.add(VipSuperFragment.newInstance());

        bodyVp.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return fragments.get(arg0);
            }
        });

        bodyVp.addOnPageChangeListener(new BaseLinkPageChangeListener(bodyVp, headerVp) {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        });
        headerVp.addOnPageChangeListener(new BaseLinkPageChangeListener(headerVp, bodyVp) {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }


}
