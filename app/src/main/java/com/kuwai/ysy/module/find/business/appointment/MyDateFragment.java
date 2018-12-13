package com.kuwai.ysy.module.find.business.appointment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.adapter.MyPagerAdapter;
import com.kuwai.ysy.module.find.business.mycommis.MyCommisMainFragment;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;
import java.util.List;

public class MyDateFragment extends BaseFragment implements View.OnClickListener {

    private TabLayout mTabLayout;
    private List<Fragment> fragmentList;
    private ArrayList<String> list_Title;
    private ViewPager viewpager;
    private NavigationLayout navigationLayout;

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
}
