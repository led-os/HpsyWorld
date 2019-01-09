package com.kuwai.ysy.module.mine.business.wallet;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alipay.sdk.app.PayTask;
import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.AliOrderInfoBean;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.adapter.RechargeAdapter;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.RechargeBean;
import com.kuwai.ysy.module.mine.business.updatevideo.UpdateActivity;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.PayResult;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

public class RechargeFragment extends BaseFragment implements View.OnClickListener {

    private NavigationLayout mNavigation;
    private SuperTextView mTvCurrent;
    private RecyclerView mRvVoucher;
    private Button mBtnCash;
    private List<RechargeBean> mDadaList = new ArrayList<>();

    private String balance = "";
    private RechargeAdapter rechargeAdapter;
    private String money = "";
    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        EventBusUtil.sendEvent(new MessageEvent(C.MSG_RECHARGE_SUCC));
                        ToastUtils.showShort("充值成功");
                        pop();
                        //ToastUtils.showShort(getString(R.string.pay_success) + payResult);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastUtils.showShort(getString(R.string.pay_failed) + payResult);
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
    private String TAG = "RE";

    public static RechargeFragment newInstance(String balance) {
        Bundle args = new Bundle();
        args.putString("balance", balance);
        RechargeFragment fragment = new RechargeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.voucher_center_fragment;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cash:
                if (!Utils.isNullString(money)) {
                    requestWritePermission();
                } else {
                    ToastUtils.showShort("请选择充值金额");
                }
                break;
        }
    }

    private void requestWritePermission() {
        RxPermissions rxPermissions = new RxPermissions(getActivity());
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            recharge();
                        }
                    }
                });
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        balance = getArguments().getString("balance");

        mNavigation = mRootView.findViewById(R.id.navigation);
        mNavigation.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        mTvCurrent = mRootView.findViewById(R.id.tv_current);
        mRvVoucher = mRootView.findViewById(R.id.rv_voucher);
        mBtnCash = mRootView.findViewById(R.id.btn_cash);
        mTvCurrent.setRightString(balance);
        mBtnCash.setOnClickListener(this);

        mDadaList.add(new RechargeBean("5桃花币", 5));
        mDadaList.add(new RechargeBean("10桃花币", 10));
        mDadaList.add(new RechargeBean("50桃花币", 50));
        mDadaList.add(new RechargeBean("100桃花币", 100));
        mDadaList.add(new RechargeBean("150桃花币", 150));
        mDadaList.add(new RechargeBean("200桃花币", 200));
        mDadaList.add(new RechargeBean("300桃花币", 300));
        mDadaList.add(new RechargeBean("500桃花币", 500));
        mDadaList.add(new RechargeBean("1000桃花币", 1000));
        mRvVoucher.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rechargeAdapter = new RechargeAdapter();
        rechargeAdapter.replaceData(mDadaList);
        mRvVoucher.setAdapter(rechargeAdapter);

        rechargeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (RechargeBean bean : mDadaList) {
                    bean.setCheck(false);
                }
                mDadaList.get(position).setCheck(true);
                money = String.valueOf(mDadaList.get(position).getPrice());
                rechargeAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    private void recharge() {
        addSubscription(MineApiFactory.getAliOrderInfo(SPManager.get().getStringValue("uid"), "0.01").subscribe(new Consumer<AliOrderInfoBean>() {
            @Override
            public void accept(AliOrderInfoBean infoBean) throws Exception {
                //mView.setAliOrderInfo(infoBean);
                toAliPay(infoBean);
                ToastUtils.showShort(infoBean.toString());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

    private void toAliPay(AliOrderInfoBean infoBean) {
        final String orderInfo = infoBean.getData();
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(mContext);
                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
