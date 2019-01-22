package com.kuwai.ysy.module.mine.business.gift;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.CircleImageView;
import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.callback.GiftClickCallback;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.circle.adapter.DyZanAdapter;
import com.kuwai.ysy.module.circle.api.CircleApiFactory;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.circle.bean.HoleMainListBean;
import com.kuwai.ysy.module.find.api.AppointApiFactory;
import com.kuwai.ysy.module.find.bean.GiftPopBean;
import com.kuwai.ysy.module.home.WebviewH5Activity;
import com.kuwai.ysy.module.mine.adapter.GiftAdapter;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.GiftAcceptBean;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.widget.GiftPannelView;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;
import io.rong.imkit.RongIM;

public class GiftMyAcceptFragment extends BaseFragment<GiftMyAcceptPresenter> implements GiftMyAcceptContract.IHomeView, View.OnClickListener, GiftClickCallback {

    private GiftAdapter mDateAdapter;
    private RecyclerView mDongtaiList;
    private int page = 1;
    private CustomDialog customDialog, giftDialog;
    private GiftAcceptBean.DataBean giftBean;

    private GiftPopBean giftPopBean;
    private String otherId = "";
    private SmartRefreshLayout mRefreshLayout;

    public static GiftMyAcceptFragment newInstance() {
        Bundle args = new Bundle();
        GiftMyAcceptFragment fragment = new GiftMyAcceptFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.smart_refresh;
    }

    @Override
    protected GiftMyAcceptPresenter getPresenter() {
        return new GiftMyAcceptPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    public void getAllGifts() {
        addSubscription(AppointApiFactory.getAllGifts()
                .subscribe(new Consumer<GiftPopBean>() {
                    @Override
                    public void accept(@NonNull GiftPopBean dateTheme) throws Exception {
                        giftPopBean = dateTheme;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        //mView.showViewError(throwable);
                    }
                }));
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        EventBusUtil.register(this);
        mDongtaiList = mRootView.findViewById(R.id.recyclerView);
        mRefreshLayout = mRootView.findViewById(R.id.mRefreshLayout);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        mLayoutStatusView = mRootView.findViewById(R.id.multipleStatusView);
        mDongtaiList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mDateAdapter = new GiftAdapter();
        mDongtaiList.setAdapter(mDateAdapter);

        mDateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showSelectdialog(position);
            }
        });

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                mPresenter.requestHomeData(SPManager.get().getStringValue("uid"), page);
            }
        });

        mDateAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                requestMore(SPManager.get().getStringValue("uid"), page + 1);
            }
        }, mDongtaiList);
    }

    public void showSelectdialog(final int position) {

        View parent = View.inflate(getActivity(), R.layout.my_gift_details_fragment, null);
        CircleImageView mIvHeadicon;
        TextView mTvNickname;
        ImageView mIvSex;
        ImageView mIvVip;
        TextView mTvMsg;
        SuperButton mSbToBack;
        SuperButton mSbSendMsg;
        LinearLayout mLlGift;
        TextView mTvGiftName;
        LinearLayout mLlCoin;
        TextView mTvValue;
        TextView mTvIntegral;
        ImageView mIvGift;
        TextView mTvGiftNum;
        SuperButton mSbRecharge;
        Button mBtnCash;

        mIvHeadicon = parent.findViewById(R.id.iv_headicon);
        mTvNickname = parent.findViewById(R.id.tv_nickname);
        mIvSex = parent.findViewById(R.id.iv_sex);
        mIvVip = parent.findViewById(R.id.iv_vip);
        mTvMsg = parent.findViewById(R.id.tv_msg);
        mSbToBack = parent.findViewById(R.id.sb_to_back);
        mSbSendMsg = parent.findViewById(R.id.sb_send_msg);
        mLlGift = parent.findViewById(R.id.ll_gift);
        mTvGiftName = parent.findViewById(R.id.tv_gift_name);
        mLlCoin = parent.findViewById(R.id.ll_coin);
        mTvValue = parent.findViewById(R.id.tv_value);
        mTvIntegral = parent.findViewById(R.id.tv_integral);
        mIvGift = parent.findViewById(R.id.iv_gift);
        mTvGiftNum = parent.findViewById(R.id.tv_gift_num);
        mSbRecharge = parent.findViewById(R.id.sb_recharge);
        mBtnCash = parent.findViewById(R.id.btn_cash);
        GlideUtil.load(mContext, giftBean.getGift().get(position).getAvatar(), mIvHeadicon);
        GlideUtil.load(mContext, giftBean.getGift().get(position).getGirft_img_url(), mIvGift);
        mTvGiftName.setText(giftBean.getGift().get(position).getGirft_name() + " x" + giftBean.getGift().get(position).getG_nums());
        mTvNickname.setText(giftBean.getGift().get(position).getNickname());
        mTvGiftNum.setText("x" + giftBean.getGift().get(position).getG_nums());
        mTvMsg.setText(DateTimeUitl.timedate(String.valueOf(giftBean.getGift().get(position).getCreate_time())));
        mTvValue.setText(giftBean.getGift().get(position).getPrice() + " 鱼币");

        switch (giftBean.getGift().get(position).getIs_vip()) {
            case 0:
                mIvVip.setVisibility(View.GONE);
                break;
            case 1:
                mIvVip.setVisibility(View.VISIBLE);
                break;
        }

        switch (giftBean.getGift().get(position).getGender()) {
            case C.Man:
                GlideUtil.load(mContext, R.drawable.ic_user_man, mIvSex);
                break;
            case C.Woman:
                GlideUtil.load(mContext, R.drawable.ic_user_woman, mIvSex);
                break;
        }

        final View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sb_to_back:
                        if (giftPopBean != null) {
                            otherId = String.valueOf(giftBean.getGift().get(position).getUid());
                            GiftPannelView pannelView = new GiftPannelView(getActivity());
                            pannelView.setData(giftPopBean.getData(), getActivity());
                            pannelView.setGiftClickCallBack(GiftMyAcceptFragment.this);
                            if (giftDialog == null) {
                                giftDialog = new CustomDialog.Builder(getActivity())
                                        .setView(pannelView)
                                        .setTouchOutside(true)
                                        .setItemHeight(0.4f)
                                        .setDialogGravity(Gravity.BOTTOM)
                                        .build();
                            }
                            giftDialog.show();
                        }
                        break;
                    case R.id.sb_send_msg:
                        RongIM.getInstance().startPrivateChat(getActivity(), String.valueOf(giftBean.getGift().get(position).getUid()), giftBean.getGift().get(position).getNickname());
                        break;
                    case R.id.sb_recharge:
                        Intent intent = new Intent(getActivity(), WebviewH5Activity.class);
                        intent.putExtra(C.H5_FLAG, C.H5_URL + C.GIFT_CHANGE + SPManager.get().getStringValue("uid") + "&gid=" + giftBean.getGift().get(position).getG_id()
                                + "&num=" + giftBean.getGift().get(position).getG_nums() + "&type=" + giftBean.getGift().get(position).getType());
                        startActivity(intent);
                        break;
                    case R.id.btn_cash:
                        Intent intent1 = new Intent(getActivity(), WebviewH5Activity.class);
                        intent1.putExtra(C.H5_FLAG, C.H5_URL + C.GIFT_RECHARGE + SPManager.get().getStringValue("uid") + "&gid=" + giftBean.getGift().get(position).getG_id()
                                + "&num=" + giftBean.getGift().get(position).getG_nums() + "&type=" + giftBean.getGift().get(position).getType() + "&price=" + giftBean.getGift().get(position).getPrice()
                                + "&integral=" + giftBean.getGift().get(position).getPrice());
                        startActivity(intent1);
                        break;
                }
                customDialog.dismiss();
            }
        };

        mSbSendMsg.setOnClickListener(clickListener);
        mBtnCash.setOnClickListener(clickListener);
        mSbToBack.setOnClickListener(clickListener);
        mSbRecharge.setOnClickListener(clickListener);

        customDialog = new CustomDialog.Builder(getActivity())
                .setView(parent)
                .setTouchOutside(true)
                .setDialogGravity(Gravity.BOTTOM)
                .build();
        customDialog.show();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestHomeData(SPManager.get().getStringValue("uid"), page);
        getAllGifts();
    }

    @Override
    public void setHomeData(GiftAcceptBean giftAcceptBean) {
        giftBean = giftAcceptBean.getData();
        mRefreshLayout.finishRefresh();
        if (giftAcceptBean.getCode() == 200 && giftAcceptBean.getData().getGift().size() > 0) {
            mDateAdapter.replaceData(giftAcceptBean.getData().getGift());
            mLayoutStatusView.showContent();
        } else {
            mDateAdapter.getData().clear();
            mDateAdapter.notifyDataSetChanged();
            mLayoutStatusView.showError();
        }
    }

    public void requestMore(String uid, int page) {
        addSubscription(MineApiFactory.getGiftAccept(uid, page).subscribe(new Consumer<GiftAcceptBean>() {
            @Override
            public void accept(GiftAcceptBean giftAcceptBean) throws Exception {
                setMoreData(giftAcceptBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtils.showShort(R.string.server_error);
            }
        }));
    }

    public void setMoreData(GiftAcceptBean dyMainListBean) {
        if (dyMainListBean.getData().getGift().size() > 0) {
            page++;
            giftBean.getGift().addAll(dyMainListBean.getData().getGift());
            mDateAdapter.addData(dyMainListBean.getData().getGift());
            mDateAdapter.loadMoreComplete();
        } else {
            mDateAdapter.loadMoreEnd();
        }
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

    @Override
    public void giftClick(GiftPopBean.DataBean giftBean) {
        giftDialog.dismiss();
        //打赏别人
        giftReward(giftBean);
    }

    private void giftReward(GiftPopBean.DataBean gift) {
        Map<String, Object> param = new HashMap<>();
        param.put("uid", SPManager.get().getStringValue("uid"));
        param.put("other_uid", otherId);
        param.put("g_id", gift.getG_id());
        param.put("g_nums", gift.num);
        param.put("message", "");
        addSubscription(ChatApiFactory.rewardPe(param)
                .subscribe(new Consumer<SimpleResponse>() {
                    @Override
                    public void accept(@NonNull SimpleResponse dateTheme) throws Exception {
                        if (dateTheme.code == 200) {

                        }
                        ToastUtils.showShort(dateTheme.msg);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        //mView.showViewError(throwable);
                    }
                }));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isLogin(MessageEvent event) {
        if (event.getCode() == C.MSG_GIFT_WITHDRAW_SUCC) {
            mPresenter.requestHomeData(SPManager.get().getStringValue("uid"), page);
        }
    }
}
