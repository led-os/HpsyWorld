package com.kuwai.ysy.module.mine.business.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aliyun.common.utils.ToastUtil;
import com.allen.library.CircleImageView;
import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.callback.GiftClickCallback;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.MyFriendFragment;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.circle.ReportActivity;
import com.kuwai.ysy.module.circle.VideoPlayActivity;
import com.kuwai.ysy.module.circle.adapter.MyPagerAdapter;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.circle.business.dongtai.DongtaiMainFragment;
import com.kuwai.ysy.module.find.api.AppointApiFactory;
import com.kuwai.ysy.module.find.api.FoundApiFactory;
import com.kuwai.ysy.module.find.bean.GiftPopBean;
import com.kuwai.ysy.module.mine.MyWalletActivity;
import com.kuwai.ysy.module.mine.adapter.PicAdapter;
import com.kuwai.ysy.module.mine.bean.PersolHomePageBean;
import com.kuwai.ysy.module.mine.bean.TabEntity;
import com.kuwai.ysy.module.mine.bean.vip.GallaryBean;
import com.kuwai.ysy.rong.GiftPlugin;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.GiftPannelView;
import com.kuwai.ysy.widget.popwindow.YsyPopWindow;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.SynchronousQueue;

import cc.shinichi.library.ImagePreview;
import io.reactivex.functions.Consumer;
import io.rong.imkit.RongIM;

public class OtherHomepageFragment extends BaseFragment<OtherHomepagePresenter> implements OtherHomepageContract.IHomeView, View.OnClickListener, GiftClickCallback {

    private ImageView mLeft;
    private TextView mTitle;
    private TextView mSubTitle;
    private ImageView mRight;
    private CircleImageView mImgHead;
    private TextView mTvNick;
    private ImageView mImgVip;
    private TextView mTvLevel;
    private TextView mTvSign;
    private ImageView mImgRight;
    private TextView mTvXinyong;
    private ImageView mImgXinyong;
    private View mLine1;
    private TextView mTvShangxian;
    private ImageView mImgShangxian;
    private SuperButton mBtnLike;
    private SuperButton mBtnChat;
    private SuperButton mBtnSendGift;
    private RecyclerView mRlPic;
    private PicAdapter mDateAdapter;
    private LinearLayout lay_has_pic;
    private SuperButton mUpdateBtn;

    private ViewPager viewPager;
    private final String[] mTitles = {"资料信息", "动态"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private CommonTabLayout slidingTabLayout;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;
    private String otherid;
    private PersolHomePageBean mPersolHomePageBean;
    private ImageView right;

    private ArrayList<PersolHomePageBean.DataBean.InfoBean.VideoBean> videos = new ArrayList<>();
    private YsyPopWindow mListPopWindow;

    public static OtherHomepageFragment newInstance(String id) {
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        OtherHomepageFragment fragment = new OtherHomepageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_mem_homepage;
    }

    @Override
    protected OtherHomepagePresenter getPresenter() {
        return new OtherHomepagePresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_chat:
                //RongIM.getInstance().setMessageAttachedUserInfo(true);
                RongIM.getInstance().startPrivateChat(getActivity(), otherid, mTvNick.getText().toString());
                //私聊
                break;
            case R.id.tv_shangxian:
                if ("已提醒".equals(mTvShangxian.getText().toString())) {
                    mPresenter.cancelRemind(SPManager.get().getStringValue("uid"), otherid);
                } else {
                    mPresenter.online(SPManager.get().getStringValue("uid"), otherid);
                }

                break;
            case R.id.tv_xinyong:
                //信用度
                break;
            case R.id.btn_like:
                if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                    if ("喜欢".equals(mBtnLike.getText().toString())) {
                        mPresenter.like(SPManager.get().getStringValue("uid"), otherid, 1);
                    } else {
                        mPresenter.like(SPManager.get().getStringValue("uid"), otherid, 2);
                    }
                } else {
                    ToastUtils.showShort(R.string.login_error);
                }
                //喜欢
                break;
            case R.id.btn_send_gift:
                createDialog(getActivity());
                //弹出打赏
                break;
            case R.id.btn_update:
                mPresenter.invite(SPManager.get().getStringValue("uid"), otherid);
                break;
            case R.id.right:
                showPopListView();
                break;
        }
    }

    private void showPopListView() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_page_more, null);

        //处理popWindow 显示内容
        handleListView(contentView);
        //创建并显示popWindow
        mListPopWindow = new YsyPopWindow.PopupWindowBuilder(getActivity())
                .setView(contentView)
                .enableBackgroundDark(true)
                .size(Utils.dp2px(180), ViewGroup.LayoutParams.WRAP_CONTENT)//显示大小
                .create()
                .showAsDropDown(right, (Utils.dp2px(-120)), (Utils.dp2px(-20)));

    }

    private void handleListView(View contentView) {
        TextView report = contentView.findViewById(R.id.tv_report);
        TextView ping = contentView.findViewById(R.id.tv_ping);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("module", "-1");
                bundle.putString("p_id", otherid);
                Intent intent = new Intent(getActivity(), ReportActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                mListPopWindow.dissmiss();
            }
        });
        ping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.isNullString(otherid)) {
                    ping(SPManager.get().getStringValue("uid"), Integer.parseInt(otherid));
                }
                mListPopWindow.dissmiss();
            }
        });
    }

    private CustomDialog customDialog;

    private void createDialog(final Context context) {
        addSubscription(AppointApiFactory.getAllGifts()
                .subscribe(new Consumer<GiftPopBean>() {
                    @Override
                    public void accept(@NonNull GiftPopBean dateTheme) throws Exception {
                        GiftPannelView pannelView = new GiftPannelView(context);
                        pannelView.setData(dateTheme.getData(), context);
                        pannelView.setGiftClickCallBack(OtherHomepageFragment.this);
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
    public void initView(Bundle savedInstanceState) {

        otherid = getArguments().getString("id");
        mLeft = mRootView.findViewById(R.id.left);
        mLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mTitle = mRootView.findViewById(R.id.title);
        right = mRootView.findViewById(R.id.right);
        mSubTitle = mRootView.findViewById(R.id.sub_title);
        mRight = mRootView.findViewById(R.id.right);
        lay_has_pic = mRootView.findViewById(R.id.lay_has_pic);
        mUpdateBtn = mRootView.findViewById(R.id.btn_update);
        mImgHead = mRootView.findViewById(R.id.img_head);
        mTvNick = mRootView.findViewById(R.id.tv_nick);
        mImgVip = mRootView.findViewById(R.id.img_vip);
        mTvLevel = mRootView.findViewById(R.id.tv_level);
        mTvSign = mRootView.findViewById(R.id.tv_sign);
        mImgRight = mRootView.findViewById(R.id.img_right);
        mTvXinyong = mRootView.findViewById(R.id.tv_xinyong);
        mImgXinyong = mRootView.findViewById(R.id.img_xinyong);
        mLine1 = mRootView.findViewById(R.id.line1);
        mTvShangxian = mRootView.findViewById(R.id.tv_shangxian);
        mImgShangxian = mRootView.findViewById(R.id.img_shangxian);
        mBtnLike = mRootView.findViewById(R.id.btn_like);
        mBtnChat = mRootView.findViewById(R.id.btn_chat);
        mBtnSendGift = mRootView.findViewById(R.id.btn_send_gift);
        mRlPic = mRootView.findViewById(R.id.rl_pic);

        mBtnChat.setOnClickListener(this);
        mTvXinyong.setOnClickListener(this);
        mBtnLike.setOnClickListener(this);
        mTvShangxian.setOnClickListener(this);
        right.setOnClickListener(this);
        mBtnSendGift.setOnClickListener(this);
        mUpdateBtn.setOnClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRlPic.setLayoutManager(linearLayoutManager);

        mDateAdapter = new PicAdapter();
        mRlPic.setAdapter(mDateAdapter);

        viewPager = mRootView.findViewById(R.id.vp);
        slidingTabLayout = mRootView.findViewById(R.id.tl_9);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }

        Bundle bundle = new Bundle();
        bundle.putString("id", otherid);

        mFragments.add(PageDetailFragment.newInstance(bundle));
        mFragments.add(DongtaiOtherFragment.newInstance(otherid));
        mAdapter = new MyPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(mAdapter);
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

        mDateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (Utils.isNullString(videos.get(position).getVideo_id())) {
                    //图片
                    ImagePreview
                            .getInstance()
                            // 上下文，必须是activity，不需要担心内存泄漏，本框架已经处理好
                            .setContext(getActivity())
                            // 从第几张图片开始，索引从0开始哦~
                            .setIndex(0)
                            // 只有一张图片的情况，可以直接传入这张图片的url
                            .setImage(videos.get(position).getAttach())
                            // 加载策略，详细说明见下面“加载策略介绍”。默认为手动模式
                            .setLoadStrategy(ImagePreview.LoadStrategy.AlwaysThumb)
                            // 保存的文件夹名称，会在SD卡根目录进行文件夹的新建。
                            // (你也可设置嵌套模式，比如："BigImageView/Download"，会在SD卡根目录新建BigImageView文件夹，并在BigImageView文件夹中新建Download文件夹)
                            .setFolderName("YsyDownload")
                            // 缩放动画时长，单位ms
                            .setZoomTransitionDuration(300)
                            // 是否启用上拉/下拉关闭。默认不启用
                            .setEnableDragClose(true)
                            // 是否显示下载按钮，在页面右下角。默认显示
                            .setShowDownButton(false)
                            // 设置是否显示顶部的指示器（1/9）默认显示
                            //.setShowIndicator(false)
                            // 设置失败时的占位图，默认为R.drawable.load_failed，设置为 0 时不显示
                            .setErrorPlaceHolder(R.drawable.load_failed)
                            // 开启预览
                            .start();
                } else {
                    //视频
                    Intent intent = new Intent(getActivity(), VideoPlayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("vid", videos.get(position).getVideo_id());
                    bundle.putString("imgurl", videos.get(position).getAttach());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        mPresenter.requestHomeData(otherid, SPManager.get().getStringValue("uid"));
    }

    @Override
    public void setHomeData(PersolHomePageBean persolHomePageBean) {
        mPersolHomePageBean = persolHomePageBean;
        if (persolHomePageBean.getData().getReminding_online() == 1) {
            mTvShangxian.setText("已提醒");
        } else {
            mTvShangxian.setText("上线提醒");
        }
        mTitle.setText(persolHomePageBean.getData().getInfo().getNickname());
        if (persolHomePageBean.getData().getLove() == 1) {
            mBtnLike.setText("取消喜欢");
        }
        List<String> subtitle = new ArrayList<>();
        if (!TextUtils.isEmpty(persolHomePageBean.getData().getInfo().getAge())) {
            subtitle.add(persolHomePageBean.getData().getInfo().getAge() + "岁");
        }
        if (!TextUtils.isEmpty(String.valueOf(persolHomePageBean.getData().getInfo().getHeight()))) {
            subtitle.add(String.valueOf(persolHomePageBean.getData().getInfo().getHeight()) + "cm");
        }
        if (!TextUtils.isEmpty(persolHomePageBean.getData().getInfo().getEducation())) {
            subtitle.add(persolHomePageBean.getData().getInfo().getEducation());
        }
        if (!TextUtils.isEmpty(persolHomePageBean.getData().getInfo().getAnnual_income())) {
            subtitle.add(persolHomePageBean.getData().getInfo().getAnnual_income());
        }
        mSubTitle.setText(StringUtils.join(subtitle.toArray(), "|"));

        GlideUtil.load(mContext, persolHomePageBean.getData().getInfo().getAvatar(), mImgHead);
        mTvNick.setText(persolHomePageBean.getData().getInfo().getNickname());

        switch (persolHomePageBean.getData().getInfo().getIs_vip()) {
            case 0:
                mImgVip.setVisibility(View.GONE);
                break;
            case 1:
                mImgVip.setVisibility(View.VISIBLE);
                break;
        }

        mTvLevel.setText(String.valueOf(persolHomePageBean.getData().getInfo().getGrade()));

        List<String> info = new ArrayList<>();
        if (!TextUtils.isEmpty(String.valueOf(persolHomePageBean.getData().getInfo().getUid()))) {
            info.add("ID:" + String.valueOf(persolHomePageBean.getData().getInfo().getUid()));
        }
        if (!TextUtils.isEmpty(persolHomePageBean.getData().getInfo().getCity())) {
            info.add(persolHomePageBean.getData().getInfo().getCity());
        }
        mTvSign.setText(StringUtils.join(info.toArray(), "|"));

        videos.clear();
        for (PersolHomePageBean.DataBean.InfoBean.VideoBean img : persolHomePageBean.getData().getInfo().getVideo()) {
            videos.add(img);
        }
        for (PersolHomePageBean.DataBean.InfoBean.ImageBean img : persolHomePageBean.getData().getInfo().getImage()) {
            PersolHomePageBean.DataBean.InfoBean.VideoBean bean = new PersolHomePageBean.DataBean.InfoBean.VideoBean();
            bean.setAttach(img.getImg());
            videos.add(bean);
        }
        if (videos.size() > 0) {
            mRlPic.setVisibility(View.VISIBLE);
            lay_has_pic.setVisibility(View.GONE);
            mDateAdapter.replaceData(videos);
        }
    }

    @Override
    public void showError(int errorCode, String msg) {

    }

    @Override
    public void likeResult(SimpleResponse response) {
        if (response.code == 200) {
            if ("喜欢".equals(mBtnLike.getText().toString())) {
                mBtnLike.setText("取消喜欢");
                ToastUtils.showShort("喜欢成功");
            } else {
                mBtnLike.setText("喜欢");
            }
        }
    }

    @Override
    public void onlineResult(SimpleResponse response) {
        if (response.code == 200) {
            mTvShangxian.setText("已提醒");
        }
        ToastUtils.showShort(response.msg);
    }

    @Override
    public void cancelResult(SimpleResponse response) {
        if (response.code == 200) {
            mTvShangxian.setText("上线提醒");
        }
        ToastUtils.showShort(response.msg);
    }

    @Override
    public void inviteResult(SimpleResponse response) {
        if (response.code == 200) {

        }
        ToastUtils.showShort(response.msg);
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

    @Override
    public void giftClick(GiftPopBean.DataBean giftBean) {
        customDialog.dismiss();
        giftReward(giftBean);
    }

    private void giftReward(final GiftPopBean.DataBean gift) {
        Map<String, Object> param = new HashMap<>();
        param.put("uid", SPManager.get().getStringValue("uid"));
        param.put("other_uid", otherid);
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

    public void ping(String uid, int tid) {
        addSubscription(FoundApiFactory.userPing(uid, tid).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                if (response.code == 200) {
                    ToastUtils.showShort("屏蔽成功");
                } else {
                    ToastUtils.showShort(response.msg);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
            }
        }));
    }
}
