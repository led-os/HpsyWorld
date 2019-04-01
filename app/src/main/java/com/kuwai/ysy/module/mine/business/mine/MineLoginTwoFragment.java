package com.kuwai.ysy.module.mine.business.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.CircleImageView;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.findtwo.MyActActivity;
import com.kuwai.ysy.module.findtwo.MyDateActivity;
import com.kuwai.ysy.module.home.business.loginmoudle.login.LoginActivity;
import com.kuwai.ysy.module.mine.CloseActivity;
import com.kuwai.ysy.module.mine.MyCreditActivity;
import com.kuwai.ysy.module.mine.MyPointActivity;
import com.kuwai.ysy.module.mine.MyWalletActivity;
import com.kuwai.ysy.module.mine.SettingActivity;
import com.kuwai.ysy.module.mine.VipCenterActivity;
import com.kuwai.ysy.module.mine.bean.user.UserInfo;
import com.kuwai.ysy.module.mine.business.gift.GiftActivity;
import com.kuwai.ysy.module.mine.business.homepage.HomePageActivity;
import com.kuwai.ysy.module.mine.business.like.StLikeActivity;
import com.kuwai.ysy.module.mine.business.question.AskQuestionActivity;
import com.kuwai.ysy.module.mine.business.visitor.VisitorActivity;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.Utils;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cc.shinichi.library.ImagePreview;
import www.linwg.org.lib.LCardView;

public class MineLoginTwoFragment extends BaseFragment<MinePresenter> implements View.OnClickListener, MineContract.IMineView {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView mImgScan;
    private ImageView mImgSetting;
    private ImageView mImgAuth;
    private TextView mTvAuth;
    private TextView mTvName;
    private TextView mTvId;
    private TextView mTvLocation;
    private ImageView mImgVip;
    private CircleImageView mImgHead;
    private TextView mTvScore;
    private TextView mTvWallet;
    private LinearLayout mGrid1;
    private LCardView top_lay;
    private TextView mTvClose;
    private TextView mTvVisitor;
    private LinearLayout ll_score, ll_wallet, ll_auth, ll_vip;
    private TextView mTvHomepage;
    private View mLine1;
    private LinearLayout mGrid2;
    private TextView mTvDate;
    private TextView mTvActivity;
    private TextView mTvGift;
    private TextView mVipTv;
    private UserInfo userInfo;

    public static MineLoginTwoFragment newInstance() {
        return new MineLoginTwoFragment();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        EventBusUtil.register(this);
        mLayoutStatusView = mRootView.findViewById(R.id.multipleStatusView);
        swipeRefreshLayout = mRootView.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
        mImgScan = mRootView.findViewById(R.id.img_scan);
        mImgSetting = mRootView.findViewById(R.id.img_setting);
        mImgAuth = mRootView.findViewById(R.id.img_auth);
        top_lay = mRootView.findViewById(R.id.top_lay);
        mTvAuth = mRootView.findViewById(R.id.tv_auth);
        mTvName = mRootView.findViewById(R.id.tv_name);
        mVipTv = mRootView.findViewById(R.id.tv_vip);
        ll_auth = mRootView.findViewById(R.id.ll_auth);
        ll_vip = mRootView.findViewById(R.id.ll_vip);
        mTvId = mRootView.findViewById(R.id.tv_id);
        mTvLocation = mRootView.findViewById(R.id.tv_location);
        mImgVip = mRootView.findViewById(R.id.img_vip);
        mImgHead = mRootView.findViewById(R.id.img_head);
        ll_score = mRootView.findViewById(R.id.ll_score);
        ll_wallet = mRootView.findViewById(R.id.ll_wallet);
        mTvScore = mRootView.findViewById(R.id.tv_score);
        mTvWallet = mRootView.findViewById(R.id.tv_wallet);
        mGrid1 = mRootView.findViewById(R.id.grid1);
        mTvClose = mRootView.findViewById(R.id.tv_close);
        mTvVisitor = mRootView.findViewById(R.id.tv_visitor);
        mTvHomepage = mRootView.findViewById(R.id.tv_homepage);
        mLine1 = mRootView.findViewById(R.id.line1);
        mGrid2 = mRootView.findViewById(R.id.grid2);
        mTvDate = mRootView.findViewById(R.id.tv_date);
        mTvActivity = mRootView.findViewById(R.id.tv_activity);
        mTvGift = mRootView.findViewById(R.id.tv_gift);

        mTvClose.setOnClickListener(this);
        mImgSetting.setOnClickListener(this);
        ll_score.setOnClickListener(this);
        ll_wallet.setOnClickListener(this);
        mTvVisitor.setOnClickListener(this);
        mTvHomepage.setOnClickListener(this);
        mTvGift.setOnClickListener(this);
        mTvDate.setOnClickListener(this);
        ll_vip.setOnClickListener(this);
        ll_auth.setOnClickListener(this);
        mImgHead.setOnClickListener(this);
        mTvActivity.setOnClickListener(this);
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_mine_two;
    }

    @Override
    protected MinePresenter getPresenter() {
        return new MinePresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_head:
                ImagePreview
                        .getInstance()
                        // 上下文，必须是activity，不需要担心内存泄漏，本框架已经处理好
                        .setContext(getActivity())
                        // 只有一张图片的情况，可以直接传入这张图片的url
                        .setImage(userInfo.getData().getAvatar())
                        // 加载策略，详细说明见下面“加载策略介绍”。默认为手动模式
                        .setLoadStrategy(ImagePreview.LoadStrategy.AlwaysThumb)
                        .setFolderName("BigImageViewDownload")
                        // 缩放动画时长，单位ms
                        .setZoomTransitionDuration(300)
                        // 是否启用上拉/下拉关闭。默认不启用
                        .setEnableDragClose(true)
                        // 是否显示下载按钮，在页面右下角。默认显示
                        .setShowDownButton(false)
                        // 设置失败时的占位图，默认为R.drawable.load_failed，设置为 0 时不显示
                        .setErrorPlaceHolder(R.drawable.load_failed)
                        .start();
                break;
            case R.id.tv_close:
                startActivity(new Intent(getActivity(), CloseActivity.class));
                break;
            case R.id.ll_score:
                startActivity(new Intent(getActivity(), MyPointActivity.class));
                break;
            case R.id.ll_wallet:
                startActivity(new Intent(getActivity(), MyWalletActivity.class));
                break;
            case R.id.ll_auth:
                startActivity(new Intent(getActivity(), MyCreditActivity.class));
                break;
            case R.id.img_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.ll_vip:
                startActivity(new Intent(getActivity(), VipCenterActivity.class));
                break;
            case R.id.tv_visitor:
                startActivity(new Intent(getActivity(), VisitorActivity.class));
                break;
            case R.id.tv_gift:
                startActivity(new Intent(getActivity(), GiftActivity.class));
                break;
            case R.id.tv_homepage:
                startActivity(new Intent(getActivity(), HomePageActivity.class));
                break;
            case R.id.tv_date:
                startActivity(new Intent(getActivity(), MyDateActivity.class));
                break;
            case R.id.tv_activity:
                startActivity(new Intent(getActivity(), MyActActivity.class));
                break;
        }
    }


    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initData();
    }

    private void initData() {
        if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
            mPresenter.requestUserData(SPManager.get().getStringValue("uid"));
        } else {
            swipeRefreshLayout.setRefreshing(false);
            mLayoutStatusView.showError();
            //SuperButton button = mRootView.findViewById(R.id.no_login);
        }
    }

    @Override
    public void setUserData(UserInfo cityMeetBean) {
        swipeRefreshLayout.setRefreshing(false);
        userInfo = cityMeetBean;
        mLayoutStatusView.showContent();
        if (cityMeetBean.getData().getIs_vip() == 1) {
            top_lay.setCardBackgroundColor(getActivity().getResources().getColor(R.color.bg_vip));
            mTvAuth.setTextColor(getActivity().getResources().getColor(R.color.white));
            mTvName.setTextColor(getActivity().getResources().getColor(R.color.white));
            mTvId.setTextColor(getActivity().getResources().getColor(R.color.white));
            mTvLocation.setTextColor(getActivity().getResources().getColor(R.color.white));
            mVipTv.setTextColor(getActivity().getResources().getColor(R.color.white));
            switch (SPManager.get().getStringValue("grade_")) {
                case "1":
                    mVipTv.setText("黄金会员");
                    mImgVip.setImageResource(R.drawable.mine_icon_gold);
                    break;
                case "2":
                    mVipTv.setText("铂金会员");
                    mImgVip.setImageResource(R.drawable.mine_icon_platinu);
                    break;
                case "3":
                    mVipTv.setText("钻石会员");
                    mImgVip.setImageResource(R.drawable.mine_icon_diamondvip);
                    break;
                case "4":
                    mVipTv.setText("定制会员");
                    mImgVip.setImageResource(R.drawable.mine_icon_diamondvip);
                    break;
            }
        }else{
            top_lay.setCardBackgroundColor(getActivity().getResources().getColor(R.color.white));
            mTvAuth.setTextColor(getActivity().getResources().getColor(R.color.black_66));
            mTvName.setTextColor(getActivity().getResources().getColor(R.color.black_66));
            mTvId.setTextColor(getActivity().getResources().getColor(R.color.black_66));
            mTvLocation.setTextColor(getActivity().getResources().getColor(R.color.black_66));
            mVipTv.setTextColor(getActivity().getResources().getColor(R.color.black_66));
            mImgVip.setImageResource(R.drawable.mine_icon_normaiuser);
            mVipTv.setText("开通会员>");
        }

        mTvName.setText(cityMeetBean.getData().getNickname());
        mTvLocation.setText(SPManager.get().getStringValue("city_"));
        if ("1".equals(SPManager.get().getStringValue("ident_"))) {
            mImgAuth.setImageResource(R.drawable.mine_icon_sur);
            mTvAuth.setText("信用认证");
        } else {
            mImgAuth.setImageResource(R.drawable.mine_icon_not_sure);
            mTvAuth.setText("前去认证>");
        }
        GlideUtil.load(getActivity(), cityMeetBean.getData().getAvatar(), mImgHead);
        mTvId.setText("ID:" + cityMeetBean.getData().getUid());
        //tv_level.setText(cityMeetBean.getData().getGrade() + "");
        //SPManager.get().putInt("isvip_", cityMeetBean.getData().getIs_vip());
        //img_vip.setVisibility(cityMeetBean.getData().getIs_vip() == 1 ? View.VISIBLE : View.GONE);
        mTvWallet.setText(cityMeetBean.getData().getAmount());
        mTvScore.setText(cityMeetBean.getData().getIntegral_exchange() + "");
        SPManager.get().putString("photo", cityMeetBean.getData().getPhoto());
    }

    @Override
    public void retry() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusUtil.unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isLogin(MessageEvent event) {
        if (C.MSG_LOGIN == event.getCode()) {
            initData();
        } else if (C.MSG_CHANGE_INFO == event.getCode()) {
            initData();
        } else if (C.MSG_LOG_OUT == event.getCode()) {
            initData();
        }
    }
}
