package com.kuwai.ysy.module.mine.business.wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.bean.MyWalletBean;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

public class MyWalletFragment extends BaseFragment<MyWalletPresenter> implements MyWalletContract.IHomeView, View.OnClickListener {

    private Toolbar mToolbar;
    private TextView mTitle;
    private ImageView mIvMoney;
    private LinearLayout mMyYubi;
    private TextView mTvYubi;
    private LinearLayout mMyCash;
    private TextView mTvCash, mTvCashWithDrawal;
    private SuperButton mSbRecharge;
    private Button mBtnCash;

    public static MyWalletFragment newInstance() {
        Bundle args = new Bundle();
        MyWalletFragment fragment = new MyWalletFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.my_money_fragment;
    }

    @Override
    protected MyWalletPresenter getPresenter() {
        return new MyWalletPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_btn:
                start(WalletDetailsFragment.newInstance());
                break;
            case R.id.sb_recharge:
                start(RechargeFragment.newInstance());
                break;
            case R.id.btn_cash:
                start(WithDrawFragment.newInstance());
                break;
        }

    }

    @Override
    public void initView(Bundle savedInstanceState) {


        mToolbar = mRootView.findViewById(R.id.toolbar);
        mTitle = mRootView.findViewById(R.id.title);
        mIvMoney = mRootView.findViewById(R.id.iv_money);
        mMyYubi = mRootView.findViewById(R.id.my_yubi);
        mTvYubi = mRootView.findViewById(R.id.tv_yubi);
        mMyCash = mRootView.findViewById(R.id.my_cash);
        mTvCash = mRootView.findViewById(R.id.tv_cash);
        mTvCashWithDrawal = mRootView.findViewById(R.id.tv_cash_withdrawal);
        mSbRecharge = mRootView.findViewById(R.id.sb_recharge);
        mBtnCash = mRootView.findViewById(R.id.btn_cash);

        mRootView.findViewById(R.id.right_btn).setOnClickListener(this);
        mSbRecharge.setOnClickListener(this);
        mBtnCash.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestHomeData(SPManager.getStringValue("uid", "1"));
    }

    @Override
    public void setHomeData(MyWalletBean walletBean) {
        mTvCash.setText(walletBean.getData().getAmount());
        mTvCashWithDrawal.setText(walletBean.getData().getForward_amount());
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
