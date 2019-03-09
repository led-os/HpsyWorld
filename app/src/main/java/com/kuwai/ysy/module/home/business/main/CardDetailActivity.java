package com.kuwai.ysy.module.home.business.main;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.allen.library.SuperButton;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.MyApp;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.module.home.business.main.NearPerFragment;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.PersolHome2PageBean;
import com.kuwai.ysy.module.mine.bean.TabEntity;
import com.kuwai.ysy.module.mine.business.homepage.DongtaiOtherFragment;
import com.kuwai.ysy.module.mine.business.homepage.otherpage.PageDetail2Fragment;
import com.kuwai.ysy.module.mine.business.mine.MineLoginFragment;
import com.kuwai.ysy.module.mine.business.visitor.MineVisitorFragment;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.shadow.FlowLayout;
import com.kuwai.ysy.widget.shadow.TagAdapter;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;
import io.rong.callkit.RongCallKit;

public class CardDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_cover;
    private CommonTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private RelativeLayout mBottomLay;
    private final String[] mTitles = {"照片", "动态", "资料"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;

    private String otherId = "";
    private int cardWidth;

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutID() {
        return R.layout.act_card_detail;
    }

    @Override
    protected void initView() {
        otherId = getIntent().getStringExtra("id");
        iv_cover = findViewById(R.id.iv_cover);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        float density = dm.density;
        cardWidth = (int) (dm.widthPixels);
        iv_cover.getLayoutParams().height = cardWidth;
        iv_cover.getLayoutParams().width = cardWidth;
        mBottomLay = findViewById(R.id.bottom_lay);
        iv_cover.setOnClickListener(this);
        viewPager = findViewById(R.id.vp);
        slidingTabLayout = findViewById(R.id.tl_9);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }

        mFragments.add(PicFragment.newInstance(null));
        mFragments.add(DongtaiOtherFragment.newInstance(otherId));
        mFragments.add(PageDetail2Fragment.newInstance(otherId));
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);


        viewPager.setOffscreenPageLimit(3);
        //slidingTabLayout.setViewPager(viewPager);\
        slidingTabLayout.setTabData(mTabEntities);

        slidingTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {
                }
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                slidingTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ImageView iv = (ImageView) findViewById(R.id.btn_online);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongCallKit.startSingleCall(CardDetailActivity.this, "10029", RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO);
            }
        });
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(iv, "scaleX", 0f, 1.2f, 0.9f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(iv, "scaleY", 0f, 1.2f, 0.9f);
        ObjectAnimator animator = ObjectAnimator.ofFloat(mBottomLay, "translationX", Utils.getScreenWidth(), 0f);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(viewPager, "translationX", Utils.getScreenWidth(), 0f);
        AnimatorSet set = new AnimatorSet();
        set.play(anim1).with(anim2);
        set.playTogether(anim1, anim2, animator, animator1);
        set.setDuration(1400);
        set.start();

       /* ObjectAnimator animator = ObjectAnimator.ofFloat(mBottomLay, "translationX", Utils.getScreenWidth(), 0f);
        animator.setDuration(1200);
        animator.start();*/
        requestHomeData(otherId, otherId);
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_cover:
                supportFinishAfterTransition();
                break;
        }
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

    public void requestHomeData(String uid, String otherid) {
        addSubscription(MineApiFactory.getOtherHomepage2Info(uid, otherid).subscribe(new Consumer<PersolHome2PageBean>() {
            @Override
            public void accept(PersolHome2PageBean persolHomePageBean) throws Exception {
                setPersonData(persolHomePageBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i("", "accept: " + throwable);
            }
        }));
    }

    private void setPersonData(PersolHome2PageBean persolHomePageBean) {
        //高级资料

        PersolHome2PageBean.DataBean.InfoBean infoBean = persolHomePageBean.getData().getInfo();
        GlideUtil.loadwithNobg(CardDetailActivity.this, infoBean.getImage().get(0).getImg(), iv_cover);
    }
}
