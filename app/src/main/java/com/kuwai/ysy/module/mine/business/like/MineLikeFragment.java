package com.kuwai.ysy.module.mine.business.like;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.FindFriendChildFragment;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;
import java.util.List;

public class MineLikeFragment extends BaseFragment implements View.OnClickListener {

    private ViewPager pager;
    private NavigationLayout navigationLayout;
    private List<Fragment> fragments;
    private RadioGroup radioGroup;

    private RadioButton radioButtonLikeMe, radioButtonIlike, radioButtonEach;

    public static MineLikeFragment newInstance() {
        Bundle args = new Bundle();
        MineLikeFragment fragment = new MineLikeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.mine_like_fragment;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        pager = mRootView.findViewById(R.id.vp_dy_detail);
        radioGroup = (RadioGroup) mRootView.findViewById(R.id.main_radiogroup);
        navigationLayout = mRootView.findViewById(R.id.navigation);
        navigationLayout.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        radioButtonLikeMe = mRootView.findViewById(R.id.radio_likeme);
        radioButtonIlike = mRootView.findViewById(R.id.radio_ilike);
        radioButtonEach = mRootView.findViewById(R.id.radio_each);
        radioButtonIlike.setOnClickListener(this);
        radioButtonEach.setOnClickListener(this);
        radioButtonLikeMe.setOnClickListener(this);

        fragments = new ArrayList<Fragment>();
        fragments.add(LikeMeFragment.newInstance());
        fragments.add(ILikeFragment.newInstance());
        fragments.add(LikeEachOtherFragment.newInstance());

        pager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return fragments.get(arg0);
            }
        });
        // 添加页面切换事件的监听器
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //pager.resetHeight(position);
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(position);
                radioButton.setChecked(true);
                switch (position) {
                    case 0:
                        radioButtonLikeMe.setTextSize(22);
                        radioButtonIlike.setTextSize(16);
                        radioButtonEach.setTextSize(16);
                        break;
                    case 1:
                        radioButtonLikeMe.setTextSize(16);
                        radioButtonIlike.setTextSize(22);
                        radioButtonEach.setTextSize(16);
                        break;
                    case 2:
                        radioButtonLikeMe.setTextSize(16);
                        radioButtonIlike.setTextSize(16);
                        radioButtonEach.setTextSize(22);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_likeme:
                        pager.setCurrentItem(0);
                        /*radioButtonLikeMe.setTextSize(22);
                        radioButtonIlike.setTextSize(16);
                        radioButtonEach.setTextSize(16);*/
                        break;
                    case R.id.radio_ilike:
                        pager.setCurrentItem(1);
                        /*radioButtonLikeMe.setTextSize(16);
                        radioButtonIlike.setTextSize(22);
                        radioButtonEach.setTextSize(16);*/
                        break;
                    case R.id.radio_each:
                        pager.setCurrentItem(2);
                        /*radioButtonLikeMe.setTextSize(16);
                        radioButtonIlike.setTextSize(16);
                        radioButtonEach.setTextSize(22);*/
                        break;
                }
            }
        });
    }

}
