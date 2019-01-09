package com.kuwai.ysy.module.mine.business.vip;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.find.bean.BlindBean;
import com.kuwai.ysy.module.mine.adapter.vip.VipPagerAdapter;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.vip.VipBannerBean;
import com.kuwai.ysy.module.mine.bean.vip.VipBean;
import com.kuwai.ysy.utils.BaseLinkPageChangeListener;
import com.kuwai.ysy.utils.DialogUtil;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class VipCenterFragment extends BaseFragment implements View.OnClickListener {

    private int[] mPics = new int[]{R.drawable.bg_huangjin, R.drawable.bg_bojin, R.drawable.bg_zaunshi, R.drawable.bg_super};
    private String[] mTitles = new String[]{"黄金会员VIP", "铂金会员VIP", "钻石会员VIP", "特级定制VIP"};
    private int[] mImgs = new int[]{R.drawable.center_vip_bg_pud, R.drawable.center_vip_bg_baijin, R.drawable.center_vip_bg_zuanshi, R.drawable.center_vip_bg_super};
    private List<VipBannerBean> mBannerList = new ArrayList<>();

    private ViewPager headerVp;
    private ViewPager bodyVp;
    private List<Fragment> fragments;
    private VipBean mVipBean;
    private VipPagerAdapter vipbannerAdapter = null;
    private Bundle mHbundle, mBbundle, mZbundle, mSbundle;

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
        ((NavigationLayout) mRootView.findViewById(R.id.navigation)).setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        mHbundle = new Bundle();
        mBbundle = new Bundle();
        mZbundle = new Bundle();
        mSbundle = new Bundle();

        vipbannerAdapter = new VipPagerAdapter(getActivity(), mBannerList);
        headerVp.setAdapter(vipbannerAdapter);
        headerVp.setPageMargin(30);
        headerVp.setOffscreenPageLimit(mPics.length);
        bodyVp.setOffscreenPageLimit(mPics.length);
        fragments = new ArrayList<Fragment>();

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
        getVipList();
    }

    private void getVipList() {
        DialogUtil.showLoadingDialog(getActivity(), "", getResources().getColor(R.color.theme));
        addSubscription(MineApiFactory.getVipList().subscribe(new Consumer<VipBean>() {
            @Override
            public void accept(VipBean vipBean) throws Exception {
                DialogUtil.dismissDialog(true);
                mVipBean = vipBean;
                if (mVipBean != null) {
                    mBannerList.clear();
                    //设置适配器
                    for (int i = 0; i < mPics.length; i++) {
                        VipBannerBean vipBannerBean = new VipBannerBean(mTitles[i], mPics[i], mImgs[i]);
                        int sum = 0;
                        for (int j = 0; j < mVipBean.getData().get(i).getPrivilege().size(); j++) {
                            sum += mVipBean.getData().get(i).getPrivilege().get(j).getArr().size();
                        }
                        vipBannerBean.tequan = sum;
                        mBannerList.add(vipBannerBean);
                    }
                    vipbannerAdapter.notifyDataSetChanged();

                    mHbundle.putSerializable("data", vipBean.getData().get(0));
                    mBbundle.putSerializable("data", vipBean.getData().get(1));
                    mZbundle.putSerializable("data", vipBean.getData().get(2));
                    mSbundle.putSerializable("data", vipBean.getData().get(3));
                    fragments.add(VipHuangjinFragment.newInstance(mHbundle));
                    fragments.add(VipBaijinFragment.newInstance(mBbundle));
                    fragments.add(VipZuanshiFragment.newInstance(mZbundle));
                    fragments.add(VipSuperFragment.newInstance(mSbundle));

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
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
                DialogUtil.dismissDialog(true);
            }
        }));
    }


}
