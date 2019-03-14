package com.kuwai.ysy.module.home.business.main;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.MyApp;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.callback.GiftClickCallback;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.find.api.AppointApiFactory;
import com.kuwai.ysy.module.find.bean.GiftPopBean;
import com.kuwai.ysy.module.home.business.main.NearPerFragment;
import com.kuwai.ysy.module.mine.MyWalletActivity;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.PersolHome2PageBean;
import com.kuwai.ysy.module.mine.bean.TabEntity;
import com.kuwai.ysy.module.mine.business.homepage.DongtaiOtherFragment;
import com.kuwai.ysy.module.mine.business.homepage.OtherHomepageFragment;
import com.kuwai.ysy.module.mine.business.homepage.otherpage.PageDetail2Fragment;
import com.kuwai.ysy.module.mine.business.mine.MineLoginFragment;
import com.kuwai.ysy.module.mine.business.visitor.MineVisitorFragment;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.GiftPannelView;
import com.kuwai.ysy.widget.NavigationLayout;
import com.kuwai.ysy.widget.shadow.FlowLayout;
import com.kuwai.ysy.widget.shadow.TagAdapter;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;
import io.rong.callkit.RongCallKit;
import io.rong.imkit.RongIM;

public class CardDetailActivity extends BaseActivity implements View.OnClickListener, GiftClickCallback {

    private ImageView iv_cover;
    private CommonTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private RelativeLayout mBottomLay;
    private final String[] mTitles = {"照片", "动态", "资料"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;
    private NavigationLayout navigationLayout;

    private PersolHome2PageBean persolHome2PageBean;
    TextView nameView;
    TextView cityView;
    SuperButton eduView;
    TextView sexTv;
    TextView signTv;
    SuperButton starTv;
    SuperButton heightTv;

    private String otherId = "";
    private int cardWidth;
    private String TAG = "";
    private ImageView btn_chat,btn_gift,btn_like;

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

        nameView = findViewById(R.id.tv_name);
        cityView = findViewById(R.id.tv_location);
        eduView = findViewById(R.id.tv_edu);
        starTv = findViewById(R.id.tv_star);
        heightTv = findViewById(R.id.tv_height);
        sexTv = findViewById(R.id.tv_sex);
        signTv = findViewById(R.id.tv_sign);

        iv_cover.getLayoutParams().height = cardWidth;
        iv_cover.getLayoutParams().width = cardWidth;
        mBottomLay = findViewById(R.id.bottom_lay);
        btn_chat = findViewById(R.id.btn_chat);
        btn_gift = findViewById(R.id.btn_send_gift);
        btn_like = findViewById(R.id.btn_like);
        btn_like.setOnClickListener(this);
        btn_gift.setOnClickListener(this);
        btn_chat.setOnClickListener(this);
        iv_cover.setOnClickListener(this);
        viewPager = findViewById(R.id.vp);
        navigationLayout = findViewById(R.id.navigation);
        navigationLayout.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        slidingTabLayout = findViewById(R.id.tl_9);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }

        mFragments.add(PicFragment.newInstance(otherId));
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
                RongCallKit.startSingleCall(CardDetailActivity.this, "10005", RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO);
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
            case R.id.btn_chat:
                //RongIM.getInstance().setMessageAttachedUserInfo(true);
                RongIM.getInstance().startPrivateChat(this, otherId, persolHome2PageBean.getData().getInfo().getNickname());
                //私聊
                break;
            case R.id.btn_like:
                if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                    /*if ("喜欢".equals(mBtnLike.getText().toString())) {
                        mPresenter.like(SPManager.get().getStringValue("uid"), otherid, 1);
                    } else {
                        mPresenter.like(SPManager.get().getStringValue("uid"), otherid, 2);
                    }*/
                    like(SPManager.get().getStringValue("uid"), otherId, 1);
                } else {
                    ToastUtils.showShort(R.string.login_error);
                }
                //喜欢
                break;
            case R.id.btn_send_gift:
                createDialog(CardDetailActivity.this);
                //弹出打赏
                break;
           /* case R.id.btn_update:
                mPresenter.invite(SPManager.get().getStringValue("uid"), otherid);
                break;*/
        }
    }

    private CustomDialog customDialog;

    private void createDialog(final Context context) {
        addSubscription(AppointApiFactory.getAllGifts()
                .subscribe(new Consumer<GiftPopBean>() {
                    @Override
                    public void accept(@NonNull GiftPopBean dateTheme) throws Exception {
                        GiftPannelView pannelView = new GiftPannelView(context);
                        pannelView.setData(dateTheme.getData(), context);
                        pannelView.setGiftClickCallBack(CardDetailActivity.this);
                        customDialog = new CustomDialog.Builder(context)
                                .setView(pannelView)
                                .setTouchOutside(true)
                                .setDialogGravity(Gravity.BOTTOM)
                                .build();
                        customDialog.show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        //mView.showViewError(throwable);
                    }
                }));
        //View root = View.inflate(context, R.layout.dialog_gift, null);
    }

    @Override
    public void giftClick(GiftPopBean.DataBean giftBean) {
        customDialog.dismiss();
        giftReward(giftBean);
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
        persolHome2PageBean = persolHomePageBean;
        PersolHome2PageBean.DataBean.InfoBean infoBean = persolHomePageBean.getData().getInfo();
        GlideUtil.loadwithNobg(CardDetailActivity.this, infoBean.getImage().get(0).getImg(), iv_cover);
        nameView.setText(infoBean.getNickname());
        signTv.setText(infoBean.getSig());
        sexTv.setText(infoBean.getAge());
        heightTv.setText(infoBean.getHeight() + "cm");
        eduView.setText(infoBean.getEducation());
        starTv.setText(Utils.getStar(infoBean.getBirthday()) + "座");
        /*if (!Utils.isNullString(String.valueOf(infoBean.getDistance())) && talent.getDistance() > 1) {
            if (talent.getDistance() < 99) {
                holder.cityView.setText(talent.getLastcity() + Utils.format1Number(talent.getDistance()) + "km");
            } else {
                holder.cityView.setText(talent.getLastcity() + ">99km");
            }
        } else {
            holder.cityView.setText(talent.getLastcity() + "<1km");
        }*/
        if (infoBean.getGender() == 1) {
            Drawable drawableLeft = getResources().getDrawable(
                    R.drawable.home_icon_male);
            sexTv.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                    null, null, null);
        } else {
            Drawable drawableLeft = getResources().getDrawable(
                    R.drawable.home_icon_female);
            sexTv.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                    null, null, null);
        }
    }

    public void like(String uid, String otherId, int type) {
        addSubscription(MineApiFactory.getUserLike(uid, otherId, type).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                //mView.likeResult(response);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

    private void giftReward(final GiftPopBean.DataBean gift) {
        Map<String, Object> param = new HashMap<>();
        param.put("uid", SPManager.get().getStringValue("uid"));
        param.put("other_uid", otherId);
        param.put("g_id", gift.getG_id());
        param.put("g_nums", gift.num);
        param.put("message", "");
        ChatApiFactory.rewardPe(param)
                .subscribe(new Consumer<SimpleResponse>() {
                    @Override
                    public void accept(@NonNull SimpleResponse dateTheme) throws Exception {
                        if (dateTheme.code == 200) {
                            //ToastUtils.showShort("赠送成功");
                        } else if (dateTheme.code == 202) {
                            //余额不足跳转充值
                            startActivity(new Intent(CardDetailActivity.this, MyWalletActivity.class));
                        }
                        ToastUtils.showShort(dateTheme.msg);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        //mView.showViewError(throwable);
                    }
                });
    }
}
