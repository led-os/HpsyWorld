package com.kuwai.ysy.module.home.business;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.controller.NavigationController;
import com.kuwai.ysy.module.chat.ChatFragment;
import com.kuwai.ysy.module.chat.ChatMainFragment;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.chat.bean.StsBean;
import com.kuwai.ysy.module.chat.bean.UserInfoBean;
import com.kuwai.ysy.module.circle.business.DongtaiFragment;
import com.kuwai.ysy.module.find.business.FoundHome.FoundFragment;
import com.kuwai.ysy.module.home.VideohomeActivity;
import com.kuwai.ysy.module.mine.business.mine.MineLoginFragment;
import com.kuwai.ysy.widget.PageNavigationView;
import com.rayhahah.rbase.base.RBaseFragment;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import io.reactivex.functions.Consumer;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

public class HomeActivity extends BaseActivity implements AMapLocationListener {

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FORTH = 3;
    public static final int FIFTH = 4;

    public static NavigationController mNavigationController;
    private RBaseFragment[] mFragments = new RBaseFragment[5];
    private AMapLocation mAmapLocation;

    private String user1 = "bBp48xhvTH1txJ8TJcYxZLmIC5Mv+fpWZ4zmDkh2pTLGVZEU6ZNOS4PwHGMF7gUw95eSiYc7cZpNLaX6kxdHOA==";
    private String user2 = "r15Y4G6BcSpmSgXbJrf/ClUbwhMPS+kf5yBTiVt919N9HJEQV3wopiEsnyZK5KbUzIcg7rRdn8ZWr7Sv9AIj0A==";
    private UserInfo userInfo;
    private AMapLocationClient mlocationClient;

    private boolean needImmersive() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("type", Build.MANUFACTURER);
        Log.e("type", Build.HARDWARE);
        Log.e("type", Build.DEVICE);
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFragments[FIRST] = VideohomeActivity.newInstance();
        mFragments[SECOND] = DongtaiFragment.newInstance();
        mFragments[THIRD] = ChatMainFragment.newInstance();
        mFragments[FORTH] = new FoundFragment();
        mFragments[FIFTH] = MineLoginFragment.newInstance();

        PageNavigationView pageBottomTabLayout = (PageNavigationView) findViewById(R.id.tab);

        mNavigationController = pageBottomTabLayout.material()
                .addItem(R.drawable.btn_tab_home_default, R.drawable.btn_tab_home_pressed, getResources().getString(R.string.tab_home))
                .addItem(R.drawable.btn_tab_dongtai_default, R.drawable.btn_tab_dongtai_pressed, getResources().getString(R.string.tab_action), getResources().getColor(R.color.theme))
                .addItem(R.drawable.btn_tab_chat_default, R.drawable.btn_tab_chat_pressed, getResources().getString(R.string.tab_chat), getResources().getColor(R.color.theme))
                .addItem(R.drawable.btn_tab_faxian_default, R.drawable.btn_tab_faxian_pressed, getResources().getString(R.string.tab_near), getResources().getColor(R.color.theme))
                .addItem(R.drawable.btn_tab_wode_default, R.drawable.btn_tab_wode_pressed, getResources().getString(R.string.tab_mine), getResources().getColor(R.color.theme))
                .build();

//        loadMultipleRootFragment(R.id.fl_tab_container,FIRST,mFragments[FIRST],mFragments[SECOND],mFragments[THIRD]);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new TestViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(5);
        mNavigationController.setupWithViewPager(viewPager);
        getLocation();
        // connectRongYun(user1);
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

            @Override
            public UserInfo getUserInfo(String userId) {
                findUserById(userId);
                return null;//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。SDK
            }

        }, true);
    }

    private void getLocation() {

        mlocationClient = new AMapLocationClient(this);
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置为高精度定位模式
        mLocationOption.setOnceLocation(true);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        mlocationClient.startLocation();
    }

    private void findUserById(String userId) {
        addSubscription(ChatApiFactory.getUserInfo(userId).subscribe(new Consumer<UserInfoBean>() {
            @Override
            public void accept(UserInfoBean userInfoBean) throws Exception {
                if (userInfoBean.getCode() == 200) {
                    userInfo = new UserInfo(String.valueOf(userInfoBean.getData().getUid()), userInfoBean.getData().getNickname(), Uri.parse(userInfoBean.getData().getAvatar()));
                    RongIM.getInstance().refreshUserInfoCache(userInfo);
                } else {
                    //ToastUtils.showShort(myBlindBean.getMsg());
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtils.showShort("网络错误");
            }
        }));
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

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mAmapLocation = amapLocation;
                if (mAmapLocation != null) {
                    SPManager.get().putString("longitude", String.valueOf(mAmapLocation.getLongitude()));
                    SPManager.get().putString("latitude", String.valueOf(mAmapLocation.getLatitude()));
                }

            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    private AMapLocationClientOption mLocationOption;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static class ScroolListener implements View.OnScrollChangeListener {

        @Override
        public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            if (scrollY > oldScrollY) {//列表向上滑动
                mNavigationController.hideBottomLayout();
            } else if (scrollY < oldScrollY) {//列表向下滑动
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
