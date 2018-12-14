package com.kuwai.ysy.module.circle.business.HoleDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.bean.HoleDetailBean;
import com.kuwai.ysy.module.circle.business.DyDashang.DyDashangListFragment;
import com.kuwai.ysy.module.circle.business.dycomment.DySecFragment;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyViewPager;
import com.kuwai.ysy.widget.ResetHeightViewpager;
import com.kuwai.ysy.widget.popwindow.YsyPopWindow;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.base.StatusBarUtil;
import com.rayhahah.rbase.utils.useful.SPManager;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class HoleDetailMainFragment extends BaseFragment<HoleDetailPresenter> implements HoleDetailContract.IHomeView, View.OnClickListener {

    private MyViewPager pager;
    private List<Fragment> fragments;
    private RadioGroup radioGroup;
    private ImageView mRightImg;

    private SuperButton mDshangBtn;
    private LinearLayout mBottomLay;
    private YsyPopWindow mListPopWindow;
    private String tid;

    private ImageView mLeft;
    /**
     * 这件事想说很久了
     */
    private ImageView mRightTxt;
    private RelativeLayout mTopLay;
    private TextView mTime;
    private SuperButton mChat;
    /**
     * 这件事想说很久了
     */
    private TextView mTvTile;
    private View mLine1;
    /**
     * 我们欠了很多钱，房子车子甚至家里的金器全卖光了，现在还欠外面600多万。老公已经是黑户，我自己信用卡逾期20多万，三个月没还了。但是这已经是...我们欠了很多钱，房子车子甚至家里的金器全卖光了，现在还欠外面600多万。老公已经是黑户，我自己信用卡逾期20多万，三个月没还了。但是这已经是...我们欠了很多钱，房子车子甚至家里的金器全卖光了，现在还欠外面600多万。老公已经是黑户，我自己信用卡逾期20多万，三个月没还了。但是这已经是...
     */
    private TextView mContainer;
    private LinearLayout mHeadLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    /**
     * 打赏(20)
     */
    private RadioButton mRadioDashang;
    /**
     * 评论(10)
     */
    private RadioButton mRadioReward;
    private RadioGroup mMainRadiogroup;
    private AppBarLayout mAppBarLayout;
    private ResetHeightViewpager mMainVpContainer;
    private NestedScrollView mNsv;
    private CoordinatorLayout mRootLayout;
    /**
     * 提交
     */
    private SuperButton mDialogCommentBt;
    /**
     * 说点什么吧...
     */
    private TextView mDetailPageDoComment;
    private List<String> info = new ArrayList<>();
    private HoleDetailBean mHoleDetailBean;

    public static HoleDetailMainFragment newInstance(Bundle bundle) {
        HoleDetailMainFragment fragment = new HoleDetailMainFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_hole_detail;
    }

    @Override
    protected HoleDetailPresenter getPresenter() {
        return new HoleDetailPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_txt:
                showPopListView();
                break;
            case R.id.sb_chat:

                break;
        }
    }

    private void showPopListView() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_hole_oprate, null);
        //处理popWindow 显示内容
        //handleListView(contentView);
        //创建并显示popWindow
        mListPopWindow = new YsyPopWindow.PopupWindowBuilder(getActivity())
                .setView(contentView)
                .enableBackgroundDark(true)
                .size(Utils.dp2px(120), ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create()
                .showAsDropDown(mRightImg, -280, 0);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tid = getArguments().getString("tid");

        mLeft = (ImageView) mRootView.findViewById(R.id.left);
        mTime = mRootView.findViewById(R.id.tv_time);
        mChat = mRootView.findViewById(R.id.sb_chat);
        mTopLay = (RelativeLayout) mRootView.findViewById(R.id.top_lay);
        mTvTile = (TextView) mRootView.findViewById(R.id.tv_title);
        mLine1 = (View) mRootView.findViewById(R.id.line1);
        mContainer = (TextView) mRootView.findViewById(R.id.container);
        mHeadLayout = (LinearLayout) mRootView.findViewById(R.id.head_layout);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) mRootView.findViewById(R.id.collapsing_toolbar_layout);
        mRadioDashang = (RadioButton) mRootView.findViewById(R.id.radio_dashang);
        mRadioReward = (RadioButton) mRootView.findViewById(R.id.radio_reward);
        mAppBarLayout = (AppBarLayout) mRootView.findViewById(R.id.app_bar_layout);
        mNsv = (NestedScrollView) mRootView.findViewById(R.id.nsv);
        mRootLayout = (CoordinatorLayout) mRootView.findViewById(R.id.root_layout);
        mDetailPageDoComment = (TextView) mRootView.findViewById(R.id.detail_page_do_comment);

        pager = mRootView.findViewById(R.id.main_vp_container);
        pager.setOffscreenPageLimit(2);
        radioGroup = (RadioGroup) mRootView.findViewById(R.id.main_radiogroup);
        mBottomLay = mRootView.findViewById(R.id.bottom_lay);
        mRightImg = mRootView.findViewById(R.id.right_txt);
        mRightImg.setOnClickListener(this);
        mDshangBtn = mRootView.findViewById(R.id.dialog_comment_bt);
        mRootView.findViewById(R.id.radio_dashang).setOnClickListener(this);
        mRootView.findViewById(R.id.radio_reward).setOnClickListener(this);
        mRootView.findViewById(R.id.sb_chat).setOnClickListener(this);

        Bundle bundle = new Bundle();
        bundle.putString("did", tid);
        bundle.putString("type","2");

        fragments = new ArrayList<Fragment>();
        fragments.add(DyDashangListFragment.newInstance(bundle));
        fragments.add(DySecFragment.newInstance(bundle));

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
                        mDshangBtn.setVisibility(View.VISIBLE);
                        mBottomLay.setVisibility(View.GONE);
                        break;
                    case 1:
                        mDshangBtn.setVisibility(View.GONE);
                        mBottomLay.setVisibility(View.VISIBLE);
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
                    case R.id.radio_dashang:
                        pager.setCurrentItem(0);
                        break;
                    case R.id.radio_reward:
                        pager.setCurrentItem(1);
                        break;
                }
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        StatusBarUtil.setDarkMode(getActivity());

        mPresenter.requestHomeData(tid, SPManager.get().getStringValue("uid"));
    }

    @Override
    public void setHomeData(HoleDetailBean holeDetailBean) {
        mHoleDetailBean = holeDetailBean;

        if (!TextUtils.isEmpty(holeDetailBean.getData().getCreate_time())) {
            info.add("发布于" + DateTimeUitl.getTime(holeDetailBean.getData().getCreate_time()));
        }
        if (!TextUtils.isEmpty(String.valueOf(holeDetailBean.getData().getViews()))) {
            info.add(String.valueOf(holeDetailBean.getData().getViews()) + "人看过");
        }
        mTime.setText(StringUtils.join(info.toArray(), "|"));

        mTvTile.setText(holeDetailBean.getData().getTitle());
        mContainer.setText(holeDetailBean.getData().getText());

        mRadioDashang.setText("打赏（" + String.valueOf(holeDetailBean.getData().getReward()) + ")");
        mRadioReward.setText("评论（" + String.valueOf(holeDetailBean.getData().getComment()) + ")");

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
}
