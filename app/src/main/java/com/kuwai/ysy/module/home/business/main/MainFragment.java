package com.kuwai.ysy.module.home.business.main;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.callback.CardCallback;
import com.kuwai.ysy.callback.GiftClickCallback;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.find.PerVideoActivity;
import com.kuwai.ysy.module.find.UploadActivity;
import com.kuwai.ysy.module.find.api.AppointApiFactory;
import com.kuwai.ysy.module.find.bean.GiftPopBean;
import com.kuwai.ysy.module.find.bean.VideoChatBean;
import com.kuwai.ysy.module.findtwo.api.Appoint2ApiFactory;
import com.kuwai.ysy.module.home.adapter.InnerAdapter;
import com.kuwai.ysy.module.home.api.HomeApiFactory;
import com.kuwai.ysy.module.home.bean.HomeCardBean;
import com.kuwai.ysy.module.home.bean.HomeVideoBean;
import com.kuwai.ysy.module.mine.MyWalletActivity;
import com.kuwai.ysy.module.mine.business.updatevideo.UpdateActivity;
import com.kuwai.ysy.utils.DialogUtil;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.GiftPannelView;
import com.kuwai.ysy.widget.home.SwipeFlingAdapterView;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cn.qqtheme.framework.util.LogUtils;
import io.reactivex.functions.Consumer;
import io.rong.callkit.RongCallKit;
import io.rong.imkit.RongIM;

public class MainFragment extends BaseFragment implements SwipeFlingAdapterView.onFlingListener,
        SwipeFlingAdapterView.OnItemClickListener, View.OnClickListener, GiftClickCallback {

    private int cardWidth;
    private int cardHeight;
    private LinearLayout animLay;

    private SwipeFlingAdapterView swipeView;
    private InnerAdapter adapter;
    private ImageView mLeftButton, mCenterButton, mRightButton;
    private CustomDialog customDialog;
    private HomeCardBean.DataBean mCurrentbean = null;

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getHomeData();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        //DisplayMetrics dm = getResources().getDisplayMetrics();
        //float density = dm.density;
        //cardWidth = (int) (dm.widthPixels - (2 * 18 * density));
        //cardHeight = (int) (dm.heightPixels - (338 * density));


        swipeView = (SwipeFlingAdapterView) mRootView.findViewById(R.id.swipe_view);
        if (swipeView != null) {
            swipeView.setIsNeedSwipe(true);
            swipeView.setFlingListener(this);
            swipeView.setOnItemClickListener(this);

            adapter = new InnerAdapter();
            swipeView.setAdapter(adapter);
        }

        mLeftButton = mRootView.findViewById(R.id.swipeLeft);
        mLeftButton.setOnClickListener(this);
        animLay = mRootView.findViewById(R.id.ll_anim);
        mRightButton = mRootView.findViewById(R.id.swipeRight);
        mRightButton.setOnClickListener(this);
        mCenterButton = mRootView.findViewById(R.id.swipeCenter);
        mCenterButton.setOnClickListener(this);

        //loadData();
    }


    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onItemClicked(MotionEvent event, View v, Object dataObject) {
        if (Utils.isNullString(SPManager.get().getStringValue("uid"))) {
            ToastUtils.showShort(R.string.login_error);
        } else if (Utils.isNullString(SPManager.get().getStringValue("photo")) || "0".equals(SPManager.get().getStringValue("photo"))) {
            showPop();
        } else {

            InnerAdapter.ViewHolder holder = (InnerAdapter.ViewHolder) v.getTag();
            HomeCardBean.DataBean talent = (HomeCardBean.DataBean) dataObject;
        /*Intent intent = new Intent(getActivity(),CardDetailActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(getActivity(),
                        holder.getImageView(),"TransitionName");*/
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Intent intent = new Intent(getActivity(), CardDetailActivity.class);
                intent.putExtra("id", String.valueOf(talent.getUid()));
                /*startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());*/
                getActivity().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                        Pair.create(((View) holder.getImageView()), "TransitionName"),
                        new Pair<View, String>(mLeftButton, "TransitionName1"),
                        new Pair<View, String>(mCenterButton, "TransitionName2"),
                        new Pair<View, String>(mRightButton, "TransitionName3"))
                        .toBundle());
                //getActivity().startActivity(intent,options.toBundle());
            } else {
                Intent intent = new Intent(getActivity(), CardDetailActivity.class);
                intent.putExtra("id", String.valueOf(talent.getUid()));
                startActivity(intent);
            }
        }

    }

    @Override
    public void removeFirstObjectInAdapter() {
        adapter.remove(0);
    }

    @Override
    public void onLeftCardExit(Object dataObject) {
        Log.e("", "");
    }

    @Override
    public void onRightCardExit(Object dataObject) {
        Log.e("", "");
        HomeCardBean.DataBean talent = (HomeCardBean.DataBean) dataObject;
       /* Glide.with(getActivity()).load(R.drawable.heart_beat).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable drawable, Transition<? super Drawable> transition) {
                if (drawable instanceof GifDrawable) {
                    GifDrawable gifDrawable = (GifDrawable) drawable;
                    gifDrawable.setLoopCount(1);
                    mRightButton.setImageDrawable(drawable);
                    gifDrawable.start();
                }
            }

            @Override
            public void onStop() {
                super.onStop();
                mRightButton.setImageResource(R.drawable.home_icon_like);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
                super.onLoadCleared(placeholder);
                mRightButton.setImageResource(R.drawable.home_icon_like);
            }
        });*/
        /*Utils.loadOneTimeGif(getActivity(), R.drawable.heart_beat, mRightButton, 1, new Utils.GifListener() {
            @Override
            public void gifPlayComplete() {
                mRightButton.setImageResource(R.drawable.home_icon_like);
            }
        });*/
        startAnim();
        if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
            like(SPManager.get().getStringValue("uid"), String.valueOf(talent.getUid()));
        }

        //GlideUtil.load(getActivity(), R.drawable.heart_beat, mRightButton);
    }

    @Override
    public void onAdapterAboutToEmpty(int itemsInAdapter) {
        if (itemsInAdapter == 3) {
            //loadData();
            getHomeData();
        }
    }

    @Override
    public void onScroll(float progress, float scrollXProgress) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.swipeLeft:
                if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                    createDialog(getActivity());
                } else {
                    ToastUtils.showShort(R.string.login_error);
                }
                break;
            case R.id.swipeRight:
                //startAnim();
                swipeView.swipeRight();
                //swipeView.swipeRight(250);
                break;
            case R.id.swipeCenter:
                if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                    RongIM.getInstance().startPrivateChat(getActivity(), String.valueOf(adapter.getItem(0).getUid()), adapter.getItem(0).getNickname());
                } else {
                    ToastUtils.showShort(R.string.login_error);
                }
                break;
        }
    }

    private CustomDialog giftDialog;

    private void createDialog(final Context context) {
        addSubscription(AppointApiFactory.getAllGifts()
                .subscribe(new Consumer<GiftPopBean>() {
                    @Override
                    public void accept(@NonNull GiftPopBean dateTheme) throws Exception {
                        GiftPannelView pannelView = new GiftPannelView(context);
                        pannelView.setData(dateTheme.getData(), context);
                        pannelView.setGiftClickCallBack(MainFragment.this);
                        giftDialog = new CustomDialog.Builder(context)
                                .setView(pannelView)
                                .setTouchOutside(true)
                                .setDialogGravity(Gravity.BOTTOM)
                                .build();
                        giftDialog.show();
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
        giftDialog.dismiss();
        //ToastUtils.showShort(adapter.getItem(0).getNickname());
        giftReward(giftBean);
    }

    void getHomeData() {
        //DialogUtil.showLoadingDialog(getActivity(), "", getResources().getColor(R.color.theme));
        HashMap<String, Object> param = new HashMap<>();
        if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
            param.put("uid", SPManager.get().getStringValue("uid"));
        }
        addSubscription(HomeApiFactory.getHomeCardData(param).subscribe(new Consumer<HomeCardBean>() {
            @Override
            public void accept(HomeCardBean homeCardBean) throws Exception {
                //DialogUtil.dismissDialog(true);
                adapter.addAll(homeCardBean.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //DialogUtil.dismissDialog(true);
                // ToastUtils.showShort(R.string.server_error);
            }
        }));
    }

    void startAnim() {
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(mRightButton, "scaleX", 1.2f, 0.8f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(mRightButton, "scaleY", 1.2f, 0.8f);
        AnimatorSet set = new AnimatorSet();
        //set.play(anim1).with(anim2);
        set.playTogether(anim1, anim2);
        set.setDuration(1000);
        set.start();
    }

    void startTextAnim() {
        animLay.setVisibility(View.VISIBLE);
        float s = animLay.getTranslationX();
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(animLay, "alpha", 1f, 0f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(animLay, "translationY", s, s - 100);
        AnimatorSet set = new AnimatorSet();
        //set.play(anim1).with(anim2);
        set.playTogether(anim1, anim2);
        set.setDuration(1200);
        set.start();
    }

    private void showPop() {
        View pannel = View.inflate(getActivity(), R.layout.dialog_photo_upload, null);
        pannel.findViewById(R.id.btn_upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                startActivity(new Intent(getActivity(), UpdateActivity.class));
            }
        });
        if (customDialog == null) {
            customDialog = new CustomDialog.Builder(getActivity())
                    .setView(pannel)
                    .setTouchOutside(true)
                    .setItemWidth(0.6f)
                    .setDialogGravity(Gravity.CENTER)
                    .build();
        }

        customDialog.show();
    }

    private void giftReward(final GiftPopBean.DataBean gift) {
        Map<String, Object> param = new HashMap<>();
        param.put("uid", SPManager.get().getStringValue("uid"));
        param.put("other_uid", adapter.getItem(0).getUid());
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
                            startActivity(new Intent(getActivity(), MyWalletActivity.class));
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

    private void like(String uid, String otherId) {
        addSubscription(Appoint2ApiFactory.likeTwo(uid, otherId).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(@NonNull SimpleResponse movieBean) throws Exception {
                if (movieBean.code == 200) {
                    startTextAnim();
                    //ToastUtils.showShort(movieBean.msg);
                } else if (movieBean.code == 202) {
                    ToastUtils.showShort("不能如此自恋哦");
                } else if (movieBean.code == 203) {
                    //ToastUtils.showShort("每天只可以喜欢一次");
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                LogUtils.error(throwable);
            }
        }));
    }
}
