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
import com.rayhahah.rbase.base.RBasePresenter;

public class MyWalletFragment extends BaseFragment implements View.OnClickListener {

    private Toolbar mToolbar;
    private TextView mTitle;
    private ImageView mIvMoney;
    private LinearLayout mMyYubi;
    private TextView mTvYubi;
    private LinearLayout mMyCash;
    private TextView mTvCash;
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
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
        mSbRecharge = mRootView.findViewById(R.id.sb_recharge);
        mBtnCash = mRootView.findViewById(R.id.btn_cash);

        mSbRecharge.setOnClickListener(this);
        mBtnCash.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
