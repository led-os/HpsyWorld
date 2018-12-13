package com.kuwai.ysy.module.home.business.sading;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.controller.NavigationController;
import com.kuwai.ysy.module.circle.CircleFragment;
import com.kuwai.ysy.module.home.business.HomeFragment;
import com.kuwai.ysy.module.home.business.MyFragment;
import com.kuwai.ysy.widget.PageNavigationView;
import com.rayhahah.rbase.base.RBaseFragment;
import com.rayhahah.rbase.base.RBasePresenter;

public class Home2Fragment extends BaseFragment {

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;

    static NavigationController mNavigationController;
    private RBaseFragment[] mFragments = new RBaseFragment[3];

    public static Home2Fragment newInstance() {

        Bundle args = new Bundle();

        Home2Fragment fragment = new Home2Fragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.activity_home;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        Toolbar toolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        //getActivity().setSupportActionBar(toolbar);
        mFragments[FIRST] = HomeFragment.newInstance();
        mFragments[SECOND] = MyFragment.newInstance();
        mFragments[THIRD] = CircleFragment.newInstance();

        PageNavigationView pageBottomTabLayout = (PageNavigationView) mRootView.findViewById(R.id.tab);

        /*mNavigationController = pageBottomTabLayout.material()
                .addItem(R.drawable.ic_restore_teal_24dp, getResources().getString(R.string.tab_home))
                .addItem(R.drawable.ic_favorite_teal_24dp, getResources().getString(R.string.tab_collect))
                .addItem(R.drawable.ic_nearby_teal_24dp, getResources().getString(R.string.tab_circle))
                .build();*/

        ViewPager viewPager = (ViewPager) mRootView.findViewById(R.id.viewPager);
        viewPager.setAdapter(new TestViewPagerAdapter(getFragmentManager()));
        mNavigationController.setupWithViewPager(viewPager);
    }

    /**
     * 监听列表的滑动来控制底部导航栏的显示与隐藏
     */
    public static class ListScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 8) {//列表向上滑动
                mNavigationController.hideBottomLayout();
            } else if (dy < -8) {//列表向下滑动
                mNavigationController.showBottomLayout();
            }
        }
    }

    //下面几个类都是为了测试写的

    private class TestViewPagerAdapter extends FragmentPagerAdapter {

        TestViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public RBaseFragment getItem(int position) {
            return mFragments[position];
        }

        @Override
        public int getCount() {
            return mNavigationController.getItemCount();
        }
    }

    public static void reStart(Context context) {
        Intent intent = new Intent(context, Home2Fragment.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
