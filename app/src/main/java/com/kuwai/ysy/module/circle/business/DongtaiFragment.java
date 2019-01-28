package com.kuwai.ysy.module.circle.business;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.MessageActivity;
import com.kuwai.ysy.module.circle.adapter.MyPagerAdapter;
import com.kuwai.ysy.module.circle.api.CircleApiFactory;
import com.kuwai.ysy.module.circle.bean.UnreadBean;
import com.kuwai.ysy.module.circle.business.TreeHoleMain.TreeHoleMainFragment;
import com.kuwai.ysy.module.circle.business.dongtai.DongtaiMainFragment;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.pageitem.RoundMessageView;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.StatusBarUtil;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

import static com.kuwai.ysy.app.C.TYPE_DY_ALL;
import static com.kuwai.ysy.app.C.TYPE_DY_FRIEND;

public class DongtaiFragment extends BaseFragment implements View.OnClickListener {

    private TabLayout mTabLayout;
    private List<Fragment> fragmentList;
    private ArrayList<String> list_Title;
    private ViewPager viewpager;
    private RelativeLayout mBgLay;
    private ImageView mMessageImg;
    private RoundMessageView red_tv;

    public static DongtaiFragment newInstance() {
        Bundle args = new Bundle();
        DongtaiFragment fragment = new DongtaiFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_dongtai;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_message:
                if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                    startActivity(new Intent(getActivity(), MessageActivity.class));
                } else {
                    ToastUtils.showShort(R.string.login_error);
                }
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        EventBusUtil.register(this);
        mTabLayout = mRootView.findViewById(R.id.tabLayout);
        viewpager = mRootView.findViewById(R.id.viewPager);
        mBgLay = mRootView.findViewById(R.id.lay_bg);
        red_tv = mRootView.findViewById(R.id.red_tv);
        mMessageImg = mRootView.findViewById(R.id.img_message);
        mMessageImg.setOnClickListener(this);
        fragmentList = new ArrayList<>();
        list_Title = new ArrayList<>();
        fragmentList.add(DongtaiMainFragment.newInstance(TYPE_DY_ALL));
        fragmentList.add(DongtaiMainFragment.newInstance(TYPE_DY_FRIEND));
        fragmentList.add(TreeHoleMainFragment.newInstance());
        list_Title.add("动态");
        list_Title.add("好友");
        list_Title.add("树洞");
        viewpager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), getActivity(), fragmentList, list_Title));
        viewpager.setCurrentItem(1);
        viewpager.setOffscreenPageLimit(3);
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
                    ((TextView) view).setTextSize(20);
                    ((TextView) view).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    ((TextView) view).setTextColor(ContextCompat.getColor(mContext, R.color.theme));
                }

                switch (tab.getPosition()) {
                    case 0:
                        //设置状态栏为黑色
                        //StatusBarUtil.setColor(getActivity(), getResources().getColor(R.color.transparent), 0);
                        mTabLayout.setBackgroundColor(getResources().getColor(R.color.white));
                        mBgLay.setBackgroundColor(getResources().getColor(R.color.white));
                        StatusBarUtil.setLightMode(getActivity());
                        break;
                    case 1:
                        //设置状态栏为红色
                        //StatusBarUtil.setColor(getActivity(), getResources().getColor(R.color.transparent), 0);
                        mTabLayout.setBackgroundColor(getResources().getColor(R.color.white));
                        mBgLay.setBackgroundColor(getResources().getColor(R.color.white));
                        StatusBarUtil.setLightMode(getActivity());
                        break;
                    case 2:
                        //设置状态栏为蓝色
                        //StatusBarUtil.setColor(getActivity(), getResources().getColor(R.color.black), 0);
                        mTabLayout.setBackgroundColor(getResources().getColor(R.color.black));
                        mBgLay.setBackgroundColor(getResources().getColor(R.color.black));
                        StatusBarUtil.setDarkMode(getActivity());
                        break;
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (getActivity() != null) {
            if (!isVisibleToUser) {
                StatusBarUtil.setLightMode(getActivity());
            } else if (mTabLayout.getSelectedTabPosition() == 2) {
                StatusBarUtil.setDarkMode(getActivity());
            }
            if (isVisibleToUser) {
                if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                    getUnreadData();
                }
            }
        }
    }

    private void getUnreadData() {
        addSubscription(CircleApiFactory.getUnreadData(SPManager.get().getStringValue("uid")).subscribe(new Consumer<UnreadBean>() {
            @Override
            public void accept(UnreadBean myBlindBean) throws Exception {
                if (myBlindBean.getCode() == 200) {
                    if (myBlindBean.getData().getComment() + myBlindBean.getData().getGift() + myBlindBean.getData().getLikes() > 0) {
                        red_tv.setHasMessage(true);
                    } else {
                        red_tv.setHasMessage(false);
                    }
                } else {
                    //ToastUtils.showShort(myBlindBean.getMsg());
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtils.showShort(R.string.server_error);
            }
        }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusUtil.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isLogin(MessageEvent event) {
        if (event.getCode() == C.MSG_UNREAD_UPDATE) {
            getUnreadData();
        }
    }
}
