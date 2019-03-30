package com.kuwai.ysy.module.findtwo.business;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.FindFriendChildFragment;
import com.kuwai.ysy.module.chat.FindFriendNearFragment;
import com.kuwai.ysy.module.circle.adapter.MyPagerAdapter;
import com.kuwai.ysy.module.find.adapter.CityAdapter;
import com.kuwai.ysy.module.find.adapter.FilterAdapter;
import com.kuwai.ysy.module.find.bean.FoundHome.LocalNextBean;
import com.kuwai.ysy.module.findtwo.adapter.MapAdapter;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.widget.popwindow.YsyPopWindow;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;
import java.util.List;

import static com.kuwai.ysy.app.C.MSG_FILTER_DATE;

public class MyMeetFragment extends BaseFragment {

    SegmentTabLayout tabLayout_1;
    private String[] mTitles = {"我的邀约", "我的应约"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ViewPager viewPager;
    private MyPagerAdapter mAdapter;
    private ImageView imgLeft;
    private YsyPopWindow mListPopWindow;
    private RelativeLayout navigationLayout;
    private ImageView tv_filter;

    @Override
    public void initView(Bundle savedInstanceState) {
        tabLayout_1 = mRootView.findViewById(R.id.tl_1);
        imgLeft = mRootView.findViewById(R.id.img_left);
        navigationLayout = mRootView.findViewById(R.id.navigation);
        tv_filter = mRootView.findViewById(R.id.tv_filter);
        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        tabLayout_1.setTabData(mTitles);
        mFragments.add(new MyMeetYaoFragment());
        mFragments.add(new MyMeetYingFragment());
        mAdapter = new MyPagerAdapter(getChildFragmentManager());
        viewPager = mRootView.findViewById(R.id.vp);
        viewPager.setAdapter(mAdapter);
        tabLayout_1.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout_1.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tv_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopListView();
            }
        });
    }

    private List<LocalNextBean.DataBean> mCityList = new ArrayList<>();

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mCityList.clear();
        mCityList.add(new LocalNextBean.DataBean(-1, "全部"));
        mCityList.add(new LocalNextBean.DataBean(0, "等待中"));
        mCityList.add(new LocalNextBean.DataBean(2, "未完成"));
        mCityList.add(new LocalNextBean.DataBean(1, "已完成"));
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

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_mymeet;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    private void showPopListView() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_city_list, null);
        //处理popWindow 显示内容
        handleListView(contentView);
        //创建并显示popWindow
        mListPopWindow = new YsyPopWindow.PopupWindowBuilder(getActivity())
                .setView(contentView)
                .enableBackgroundDark(false)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create()
                .showAsDropDown(navigationLayout, 0, 0);
    }

    private void handleListView(View contentView) {
        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recyclerView);
        View dissmiss = contentView.findViewById(R.id.dissmiss);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        FilterAdapter adapter = new FilterAdapter(mCityList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                for (LocalNextBean.DataBean bean : mCityList) {
                    bean.ischecked = false;
                }
                mCityList.get(position).ischecked = true;
                EventBusUtil.sendEvent(new MessageEvent(MSG_FILTER_DATE, mCityList.get(position).getRegion_id()));
                adapter.notifyDataSetChanged();
                mListPopWindow.dissmiss();
            }
        });
        dissmiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListPopWindow != null) {
                    mListPopWindow.dissmiss();
                }
            }
        });
    }
}
