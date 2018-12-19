package com.kuwai.ysy.module.mine.business.vip;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alipay.sdk.app.PayTask;
import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.AliOrderInfoBean;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.mine.adapter.GiftAdapter;
import com.kuwai.ysy.module.mine.adapter.vip.HuangjinVipFeeAdapter;
import com.kuwai.ysy.module.mine.adapter.vip.TequanAdapter;
import com.kuwai.ysy.module.mine.bean.user.UserInfo;
import com.kuwai.ysy.module.mine.bean.vip.VipPayBean;
import com.kuwai.ysy.utils.PayResult;
import com.kuwai.ysy.widget.layoutmanager.MyGridLayoutManager;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VipHuangjinFragment extends BaseFragment<VipHuangjinPresenter> implements VipHuangjinContract.IMineView, View.OnClickListener {

    private HuangjinVipFeeAdapter mDateAdapter;
    private TequanAdapter mContentAdapter;
    private TequanAdapter mAuthAdapter;
    private TequanAdapter mActAdapter;
    private List<VipPayBean> mDataList = new ArrayList<>();
    private RecyclerView rl_fee, rlContent, rlAuth, rlAct;
    private List<CategoryBean> mContentDataList = new ArrayList<>();
    private List<CategoryBean> mAuthDataList = new ArrayList<>();
    private List<CategoryBean> mActDataList = new ArrayList<>();
    private SuperButton mSubmitBtn;

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
                        showAlert(mContext, getString(R.string.pay_success) + payResult);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showAlert(mContext, getString(R.string.pay_failed) + payResult);
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    public static VipHuangjinFragment newInstance() {
        Bundle args = new Bundle();
        VipHuangjinFragment fragment = new VipHuangjinFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_huangjin;
    }

    @Override
    protected VipHuangjinPresenter getPresenter() {
        return  new VipHuangjinPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                requestPermission();
//                ((BaseFragment) getParentFragment()).start(VipDetailFragment.newInstance());
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        rl_fee = mRootView.findViewById(R.id.rl_fee);
        mSubmitBtn = mRootView.findViewById(R.id.btn_submit);
        mSubmitBtn.setOnClickListener(this);
        rlContent = mRootView.findViewById(R.id.rl_content);
        rlAuth = mRootView.findViewById(R.id.rl_auth);
        rlAct = mRootView.findViewById(R.id.rl_activity);

        mDataList.add(new VipPayBean("连续包月", "19", "每月自动续费，可随时关闭", true));
        mDataList.add(new VipPayBean("连续包季", "57", "每季自动续费，可随时关闭", false));
        mDataList.add(new VipPayBean("一个月", "20", "20元/月", false));
        mDataList.add(new VipPayBean("年费", "199", "199元/年", false));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        mContentDataList.clear();
        mAuthDataList.clear();
        mActDataList.clear();
        for (int i = 0; i < 6; i++) {
            mContentDataList.add(new CategoryBean());
        }
        for (int i = 0; i < 2; i++) {
            mActDataList.add(new CategoryBean());
        }
        for (int i = 0; i < 3; i++) {
            mAuthDataList.add(new CategoryBean());
        }

        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rl_fee.setLayoutManager(linearLayoutManager);
        mDateAdapter = new HuangjinVipFeeAdapter(mDataList);
        rl_fee.setAdapter(mDateAdapter);
        rlContent.setLayoutManager(new MyGridLayoutManager(getActivity(), 4));
        rlAuth.setLayoutManager(new MyGridLayoutManager(getActivity(), 4));
        rlAct.setLayoutManager(new MyGridLayoutManager(getActivity(), 4));
        mContentAdapter = new TequanAdapter(mContentDataList);
        mAuthAdapter = new TequanAdapter(mAuthDataList);
        mActAdapter = new TequanAdapter(mActDataList);
        rlContent.setAdapter(mContentAdapter);
        rlAuth.setAdapter(mAuthAdapter);
        rlAct.setAdapter(mActAdapter);

        mDateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (VipPayBean bean : mDataList) {
                    bean.setCheck(false);
                }
                mDataList.get(position).setCheck(true);
                mDateAdapter.notifyDataSetChanged();
            }
        });


    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    private void toAliPay(AliOrderInfoBean infoBean) {
        final String orderInfo = infoBean.getData();
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(mContext);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("", "run: " + result.toString());

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

    private static void showAlert(Context ctx, String info) {
        showAlert(ctx, info, null);
    }

    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton(R.string.confirm, null)
                .setOnDismissListener(onDismiss)
                .show();
    }

    /**
     * 获取权限使用的 RequestCode
     */
    private static final int PERMISSIONS_REQUEST_CODE = 1002;

    /**
     * 检查支付宝 SDK 所需的权限，并在必要的时候动态获取。
     * 在 targetSDK = 23 以上，READ_PHONE_STATE 和 WRITE_EXTERNAL_STORAGE 权限需要应用在运行时获取。
     * 如果接入支付宝 SDK 的应用 targetSdk 在 23 以下，可以省略这个步骤。
     */
    private void requestPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(mContext,
                    new String[]{
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, PERMISSIONS_REQUEST_CODE);

        } else {
            mPresenter.getAliOrderInfo();
//            ToastUtils(this, getString(R.string.permission_already_granted));
        }
    }

    /**
     * 权限获取回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {

                // 用户取消了权限弹窗
                if (grantResults.length == 0) {
//                    showToast(this, getString(R.string.permission_rejected));
                    return;
                }

                // 用户拒绝了某些权限
                for (int x : grantResults) {
                    if (x == PackageManager.PERMISSION_DENIED) {
//                        showToast(this, getString(R.string.permission_rejected));
                        return;
                    }
                }

                mPresenter.getAliOrderInfo();

                // 所需的权限均正常获取
//                showToast(this, getString(R.string.permission_granted));
            }
        }
    }

    @Override
    public void setUserData(UserInfo cityMeetBean) {

    }

    @Override
    public void setAliOrderInfo(AliOrderInfoBean infoBean) {
        toAliPay(infoBean);
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
}
