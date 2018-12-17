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
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.circle.business.dongtai.DongtaiMainFragment;
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

public class OtherHomepageFragment extends BaseFragment<OtherHomepagePresenter> implements OtherHomepageContract.IHomeView, View.OnClickListener {

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

    private ViewPager viewPager;
    private final String[] mTitles = {"资料信息", "动态"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private CommonTabLayout slidingTabLayout;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;
    private String otherid;
    private PersolHomePageBean mPersolHomePageBean;

    private List<GallaryBean> gallaryBeanList = new ArrayList<>();

    public static OtherHomepageFragment newInstance(Bundle bundle) {
        OtherHomepageFragment fragment = new OtherHomepageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_mem_homepage;
    }

    @Override
    protected OtherHomepagePresenter getPresenter() {
        return new OtherHomepagePresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_chat:
                //私聊
                break;
            case R.id.tv_xinyong:
                //信用度
                break;
            case R.id.btn_like:
                //喜欢
                break;
            case R.id.btn_send_gift:
                //弹出打赏
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        otherid = getArguments().getString("id");

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

        mBtnChat.setOnClickListener(this);
        mTvXinyong.setOnClickListener(this);
        mBtnLike.setOnClickListener(this);
        mBtnSendGift.setOnClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRlPic.setLayoutManager(linearLayoutManager);

        mDateAdapter = new PicAdapter();
        mRlPic.setAdapter(mDateAdapter);

        viewPager = mRootView.findViewById(R.id.vp);
        slidingTabLayout = mRootView.findViewById(R.id.tl_9);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }

        Bundle bundle = new Bundle();
        bundle.putString("id",otherid);

        mFragments.add(PageDetailFragment.newInstance(bundle));
        mFragments.add(DongtaiMainFragment.newInstance(C.TYPE_DY_ALL));
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

        mPresenter.requestHomeData(SPManager.getStringValue("uid", "1"), otherid);
        //slidingTabLayout.setTabWidth(Utils.getScreenWidth() / 2);
    }

    @Override
    public void setHomeData(PersolHomePageBean persolHomePageBean) {
        mPersolHomePageBean = persolHomePageBean;

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


        //mDateAdapter.addData(persolHomePageBean.getData().getInfo().getAttach());
        for (int i=0;i<persolHomePageBean.getData().getInfo().getAttach().size();i++) {
            gallaryBeanList.add(new GallaryBean(persolHomePageBean.getData().getInfo().getAttach().get(i),
                    persolHomePageBean.getData().getInfo().getIs_vip()==1?true:false,
                    persolHomePageBean.getData().getView_face()==1?true:false));
        }
        mDateAdapter.addData(gallaryBeanList);
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
