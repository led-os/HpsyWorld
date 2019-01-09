package com.kuwai.ysy.module.mine.business.gift;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.business.like.LikeMeFragment;
import com.kuwai.ysy.module.mine.business.visitor.LookMeFragment;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;
import java.util.List;

public class MineMyGiftFragment extends BaseFragment implements View.OnClickListener {

    private ViewPager pager;
    private NavigationLayout navigationLayout;
    private List<Fragment> fragments;
    private RadioGroup radioGroup;

    private RadioButton radioButtonLookme, radioButtonMylook;

    public static MineMyGiftFragment newInstance() {
        Bundle args = new Bundle();
        MineMyGiftFragment fragment = new MineMyGiftFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.mine_mygift_fragment;
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
        navigationLayout.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(GiftAllWithdrawFragment.newInstance());
            }
        });
        radioButtonLookme = mRootView.findViewById(R.id.radio_lookme);
        radioButtonMylook = mRootView.findViewById(R.id.radio_mylook);
        radioButtonLookme.setOnClickListener(this);
        radioButtonMylook.setOnClickListener(this);

        fragments = new ArrayList<Fragment>();
        fragments.add(GiftMyAcceptFragment.newInstance());
        fragments.add(GiftMySendFragment.newInstance());

        /*SpannableString spanString = new SpannableString("我收到的");
        AbsoluteSizeSpan span = new AbsoluteSizeSpan(26);
        spanString.setSpan(span, 4, spanString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        radioButtonLookme.setText(spanString);
        SpannableString spanString1 = new SpannableString("我送出的");
        AbsoluteSizeSpan span1 = new AbsoluteSizeSpan(26);
        spanString1.setSpan(span1, 4, spanString1.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        radioButtonMylook.setText(spanString1);*/

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
                        radioButtonLookme.setTextSize(22);
                        radioButtonMylook.setTextSize(16);

                        break;
                    case 1:
                        radioButtonLookme.setTextSize(16);
                        radioButtonMylook.setTextSize(22);
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
                    case R.id.radio_lookme:
                        pager.setCurrentItem(0);
                        break;
                    case R.id.radio_mylook:
                        pager.setCurrentItem(1);
                        break;
                }
            }
        });
    }

}
