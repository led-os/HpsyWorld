package com.kuwai.ysy.module.mine.business.homepage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.CircleImageView;
import com.allen.library.SuperButton;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.mine.MinePicAdapter;
import com.kuwai.ysy.module.mine.adapter.PicAdapter;
import com.kuwai.ysy.module.mine.bean.PersolHomePageBean;
import com.kuwai.ysy.module.mine.bean.TabEntity;
import com.kuwai.ysy.module.mine.bean.vip.GallaryBean;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MineHomepageFragment extends BaseFragment<MineHomepagePresenter> implements MineHomepageContract.IHomeView, View.OnClickListener {

    private ImageView mLeft;
    private TextView mTitle;
    private TextView mSubTitle;
    private RecyclerView mRlPic;
    private MinePicAdapter mDateAdapter;

    private ImageView mRight;
    private CircleImageView mImgHead;
    private TextView mTvNick;
    private ImageView mImgVip;
    private TextView mTvLevel;
    private TextView mTvSign;
    private ImageView mImgRight;

    private ViewPager viewPager;
    private final String[] mTitles = {"资料信息", "动态", "树洞"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private CommonTabLayout slidingTabLayout;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;

    public static MineHomepageFragment newInstance() {
        Bundle args = new Bundle();
        MineHomepageFragment fragment = new MineHomepageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_page_mine;
    }

    @Override
    protected MineHomepagePresenter getPresenter() {
        return new MineHomepagePresenter(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mLeft = mRootView.findViewById(R.id.left);
        mTitle = mRootView.findViewById(R.id.title);
        mSubTitle = mRootView.findViewById(R.id.sub_title);
        mRlPic = mRootView.findViewById(R.id.rl_pic);

        mRight = mRootView.findViewById(R.id.right);
        mImgHead = mRootView.findViewById(R.id.img_head);
        mTvNick = mRootView.findViewById(R.id.tv_nick);
        mImgVip = mRootView.findViewById(R.id.img_vip);
        mTvLevel = mRootView.findViewById(R.id.tv_level);
        mTvSign = mRootView.findViewById(R.id.tv_sign);
        mImgRight = mRootView.findViewById(R.id.img_right);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRlPic.setLayoutManager(linearLayoutManager);
        mDateAdapter = new MinePicAdapter();
        mRlPic.setAdapter(mDateAdapter);

        viewPager = mRootView.findViewById(R.id.vp);
        slidingTabLayout = mRootView.findViewById(R.id.tl_9);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }
        mFragments.add(PageDetailMineFragment.newInstance());
        mFragments.add(PageDyMineFragment.newInstance());
        mFragments.add(TreeHoleMineFragment.newInstance());
        mAdapter = new MyPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(mAdapter);
        //slidingTabLayout.setViewPager(viewPager);\
        slidingTabLayout.setTabData(mTabEntities);

        slidingTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {
                }
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                slidingTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestHomeData(SPManager.getStringValue("uid", "1"));

        //slidingTabLayout.setTabWidth(Utils.getScreenWidth() / 2);
    }

    @Override
    public void setHomeData(PersolHomePageBean persolHomePageBean) {


        mTitle.setText(persolHomePageBean.getData().getInfo().getNickname());
        List<String> subtitle = new ArrayList<>();
        if (!TextUtils.isEmpty(persolHomePageBean.getData().getInfo().getAge())) {
            subtitle.add(persolHomePageBean.getData().getInfo().getAge() + "岁");
        }
        if (!TextUtils.isEmpty(String.valueOf(persolHomePageBean.getData().getInfo().getHeight()))) {
            subtitle.add(String.valueOf(persolHomePageBean.getData().getInfo().getHeight()) + "cm");
        }
        if (!TextUtils.isEmpty(persolHomePageBean.getData().getInfo().getEducation())) {
            subtitle.add(persolHomePageBean.getData().getInfo().getEducation());
        }
        if (!TextUtils.isEmpty(persolHomePageBean.getData().getInfo().getAnnual_income())) {
            subtitle.add(persolHomePageBean.getData().getInfo().getAnnual_income());
        }
        mSubTitle.setText(StringUtils.join(subtitle.toArray(), "|"));

        GlideUtil.load(mContext, persolHomePageBean.getData().getInfo().getAvatar(), mImgHead);
        mTvNick.setText(persolHomePageBean.getData().getInfo().getNickname());

        switch (persolHomePageBean.getData().getInfo().getIs_vip()) {
            case 0:
                mImgVip.setVisibility(View.GONE);
                break;
            case 1:
                mImgVip.setVisibility(View.VISIBLE);
                break;
        }

        mTvLevel.setText(String.valueOf(persolHomePageBean.getData().getInfo().getGrade()));

        List<String> info = new ArrayList<>();
        if (!TextUtils.isEmpty(String.valueOf(persolHomePageBean.getData().getInfo().getUid()))) {
            info.add("ID:" + String.valueOf(persolHomePageBean.getData().getInfo().getUid()));
        }
        if (!TextUtils.isEmpty(persolHomePageBean.getData().getInfo().getCity())) {
            info.add(persolHomePageBean.getData().getInfo().getCity());
        }
        mTvSign.setText(StringUtils.join(info.toArray(), "|"));

        mDateAdapter.addData(persolHomePageBean.getData().getInfo().getAttach());
    }

    @Override
    public void showError(int errorCode, String msg) {

    }

    @Override
    public void showViewLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showViewError(Throwable t) {

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
