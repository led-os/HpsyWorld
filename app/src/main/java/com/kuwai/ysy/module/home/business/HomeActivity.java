package com.kuwai.ysy.module.home.business;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.controller.NavigationController;
import com.kuwai.ysy.module.chat.ChatFragment;
import com.kuwai.ysy.module.circle.business.DongtaiFragment;
import com.kuwai.ysy.module.find.business.FoundHome.FoundFragment;
import com.kuwai.ysy.module.mine.business.mine.MineLoginFragment;
import com.kuwai.ysy.widget.PageNavigationView;
import com.rayhahah.rbase.base.RBaseFragment;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;

import io.rong.imlib.RongIMClient;

public class HomeActivity extends BaseActivity {

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FORTH = 3;
    public static final int FIFTH = 4;

    static NavigationController mNavigationController;
    private RBaseFragment[] mFragments = new RBaseFragment[5];

    private String user1 = "bBp48xhvTH1txJ8TJcYxZLmIC5Mv+fpWZ4zmDkh2pTLGVZEU6ZNOS4PwHGMF7gUw95eSiYc7cZpNLaX6kxdHOA==";
    private String user2 = "r15Y4G6BcSpmSgXbJrf/ClUbwhMPS+kf5yBTiVt919N9HJEQV3wopiEsnyZK5KbUzIcg7rRdn8ZWr7Sv9AIj0A==";

    private boolean needImmersive() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFragments[FIRST] = HomeFragment.newInstance();
        mFragments[SECOND] = DongtaiFragment.newInstance();
        mFragments[THIRD] = ChatFragment.newInstance();
        mFragments[FORTH] = new FoundFragment();
        mFragments[FIFTH] = MineLoginFragment.newInstance();

        PageNavigationView pageBottomTabLayout = (PageNavigationView) findViewById(R.id.tab);

        mNavigationController = pageBottomTabLayout.material()
                .addItem(R.drawable.btn_tab_home_default, R.drawable.btn_tab_home_pressed, getResources().getString(R.string.tab_home))
                .addItem(R.drawable.btn_tab_dongtai_default, R.drawable.btn_tab_dongtai_pressed, getResources().getString(R.string.tab_action),getResources().getColor(R.color.theme))
                .addItem(R.drawable.btn_tab_chat_default, R.drawable.btn_tab_chat_pressed, getResources().getString(R.string.tab_chat),getResources().getColor(R.color.theme))
                .addItem(R.drawable.btn_tab_faxian_default, R.drawable.btn_tab_faxian_pressed, getResources().getString(R.string.tab_near),getResources().getColor(R.color.theme))
                .addItem(R.drawable.btn_tab_wode_default, R.drawable.btn_tab_wode_pressed, getResources().getString(R.string.tab_mine),getResources().getColor(R.color.theme))
                .build();

//        loadMultipleRootFragment(R.id.fl_tab_container,FIRST,mFragments[FIRST],mFragments[SECOND],mFragments[THIRD]);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new TestViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(5);
        mNavigationController.setupWithViewPager(viewPager);

        connectRongYun(user1);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_home;
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
        Intent intent = new Intent(context, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private void connectRongYun(String token) {

        RongIMClient.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                Log.i("xxx", "onTokenIncorrect: ");
            }

            @Override
            public void onSuccess(String s) {
                Log.i("xxx", "onTokenIncorrect: ");
                //RongIM.getInstance().startConversation(getActivity(), Conversation.ConversationType.PRIVATE, "237", "测试");
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.i("xxx", "onTokenIncorrect: ");
            }
        });
    }

    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (getSupportFragmentManager().getBackStackEntryCount() <= 0) {
                if (System.currentTimeMillis() - firstTime > 2000) {
                    ToastUtils.showShort("再次按返回退出");
                    firstTime = System.currentTimeMillis();
                } else {
                    finish();
                    //System.exit(0);
                }
            } else {
                getSupportFragmentManager().popBackStack();
                //取出我们返回栈保存的Fragment,这里会从栈顶开始弹栈
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
