package com.kuwai.ysy.module.mine.business.homepage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.CircleImageView;
import com.allen.library.SuperButton;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.FindFriendChildFragment;
import com.kuwai.ysy.module.circle.adapter.MyPagerAdapter;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.circle.business.dongtai.DongtaiMainFragment;
import com.kuwai.ysy.module.mine.adapter.PicAdapter;
import com.kuwai.ysy.module.mine.adapter.vip.HuangjinVipFeeAdapter;
import com.kuwai.ysy.module.mine.bean.TabEntity;
import com.kuwai.ysy.module.mine.bean.vip.VipBannerBean;
import com.kuwai.ysy.module.mine.bean.vip.VipPayBean;
import com.kuwai.ysy.utils.Utils;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;
import java.util.List;

public class MemHomepageFragment extends BaseFragment implements View.OnClickListener {

    private ImageView mLeft;
    private TextView mTitle;
    private TextView mSubTitle;
    private ImageView mRight;
    private CircleImageView mImgHead;
    private TextView mTvNick;
    private ImageView mImgVip;
    private TextView mTvLevel;
    private TextView mTvSign;
    private ImageView mImgRight;
    private TextView mTvXinyong;
    private ImageView mImgXinyong;
    private View mLine1;
    private TextView mTvShangxian;
    private ImageView mImgShangxian;
    private SuperButton mBtnLike;
    private SuperButton mBtnChat;
    private SuperButton mBtnSendGift;
    private RecyclerView mRlPic;
    private PicAdapter mDateAdapter;

    private List<CategoryBean> mDataList = new ArrayList<>();
    private ViewPager viewPager;
    private final String[] mTitles = {"资料信息", "动态"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private CommonTabLayout slidingTabLayout;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;

    public static MemHomepageFragment newInstance() {
        Bundle args = new Bundle();
        MemHomepageFragment fragment = new MemHomepageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_mem_homepage;
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

        mLeft = mRootView.findViewById(R.id.left);
        mTitle = mRootView.findViewById(R.id.title);
        mSubTitle = mRootView.findViewById(R.id.sub_title);
        mRight = mRootView.findViewById(R.id.right);
        mImgHead = mRootView.findViewById(R.id.img_head);
        mTvNick = mRootView.findViewById(R.id.tv_nick);
        mImgVip = mRootView.findViewById(R.id.img_vip);
        mTvLevel = mRootView.findViewById(R.id.tv_level);
        mTvSign = mRootView.findViewById(R.id.tv_sign);
        mImgRight = mRootView.findViewById(R.id.img_right);
        mTvXinyong = mRootView.findViewById(R.id.tv_xinyong);
        mImgXinyong = mRootView.findViewById(R.id.img_xinyong);
        mLine1 = mRootView.findViewById(R.id.line1);
        mTvShangxian = mRootView.findViewById(R.id.tv_shangxian);
        mImgShangxian = mRootView.findViewById(R.id.img_shangxian);
        mBtnLike = mRootView.findViewById(R.id.btn_like);
        mBtnChat = mRootView.findViewById(R.id.btn_chat);
        mBtnSendGift = mRootView.findViewById(R.id.btn_send_gift);
        mRlPic = mRootView.findViewById(R.id.rl_pic);

        mDataList.add(new CategoryBean());
        mDataList.add(new CategoryBean());
        mDataList.add(new CategoryBean());
        mDataList.add(new CategoryBean());
        mDataList.add(new CategoryBean());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRlPic.setLayoutManager(linearLayoutManager);

        mDateAdapter = new PicAdapter(mDataList);
        mRlPic.setAdapter(mDateAdapter);

        viewPager = mRootView.findViewById(R.id.vp);
        slidingTabLayout = mRootView.findViewById(R.id.tl_9);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }
        mFragments.add(PageDetailFragment.newInstance());
        mFragments.add(DongtaiMainFragment.newInstance());
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
        //slidingTabLayout.setTabWidth(Utils.getScreenWidth() / 2);
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
