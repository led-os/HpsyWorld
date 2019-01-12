package com.kuwai.ysy.module.find.business;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.adapter.MyPagerAdapter;
import com.kuwai.ysy.module.find.adapter.CityAdapter;
import com.kuwai.ysy.module.find.bean.FoundHome.LocalNextBean;
import com.kuwai.ysy.module.find.business.Myinvited.MydateMainFragment;
import com.kuwai.ysy.module.find.business.MyPromise.MyCommisMainFragment;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.widget.NavigationLayout;
import com.kuwai.ysy.widget.popwindow.YsyPopWindow;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;
import java.util.List;

import static com.kuwai.ysy.app.C.MSG_FILTER_DATE;

public class MyDateFragment extends BaseFragment implements View.OnClickListener {

    private TabLayout mTabLayout;
    private List<Fragment> fragmentList;
    private ArrayList<String> list_Title;
    private ViewPager viewpager;
    private NavigationLayout navigationLayout;
    private YsyPopWindow mListPopWindow;

    public static MyDateFragment newInstance() {
        Bundle args = new Bundle();
        MyDateFragment fragment = new MyDateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_my_date;
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
        mTabLayout = mRootView.findViewById(R.id.tabLayout);
        viewpager = mRootView.findViewById(R.id.viewPager);
        navigationLayout = mRootView.findViewById(R.id.navigation);
        navigationLayout.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        navigationLayout.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopListView();
            }
        });
        fragmentList = new ArrayList<>();
        list_Title = new ArrayList<>();
        fragmentList.add(MydateMainFragment.newInstance());
        fragmentList.add(MyCommisMainFragment.newInstance());
        list_Title.add("我的邀约");
        list_Title.add("我的应约");
        viewpager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), getActivity(), fragmentList, list_Title));
        viewpager.setCurrentItem(1);
        viewpager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(viewpager);//此方法就是让tablayout和ViewPager联动

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(getTabView(i));
            }
        }

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
                View view = tab.getCustomView();
                if (null != view && view instanceof TextView) {
                    ((TextView) view).setTextSize(21);
                    ((TextView) view).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    ((TextView) view).setTextColor(ContextCompat.getColor(mContext, R.color.balck_28));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
                View view = tab.getCustomView();
                if (null != view && view instanceof TextView) {
                    ((TextView) view).setTextSize(16);
                    ((TextView) view).setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                    ((TextView) view).setTextColor(ContextCompat.getColor(mContext, R.color.gray_7b));
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mTabLayout.getTabAt(0).select();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mCityList.clear();
        mCityList.add(new LocalNextBean.DataBean(-1,"全部"));
        mCityList.add(new LocalNextBean.DataBean(0,"暂未成功"));
        mCityList.add(new LocalNextBean.DataBean(1,"已成功"));
        mCityList.add(new LocalNextBean.DataBean(2,"未成功"));
        //mCityList.add(new LocalNextBean.DataBean(3,"全部"));
        //mCityList.add(new LocalNextBean.DataBean(4,"已同意"));
    }

    /**
     * 自定义Tab的View
     *
     * @param currentPosition
     * @return
     */
    private View getTabView(int currentPosition) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_custom_tab, null);
        TextView textView = (TextView) view.findViewById(R.id.tab_item_textview);
        textView.setText(list_Title.get(currentPosition));
        return view;
    }

    private List<LocalNextBean.DataBean> mCityList = new ArrayList<>();

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
        CityAdapter adapter = new CityAdapter(mCityList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                for (LocalNextBean.DataBean bean : mCityList) {
                    bean.ischecked = false;
                }
                mCityList.get(position).ischecked = true;
                EventBusUtil.sendEvent(new MessageEvent(MSG_FILTER_DATE,mCityList.get(position).getRegion_id()));
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
