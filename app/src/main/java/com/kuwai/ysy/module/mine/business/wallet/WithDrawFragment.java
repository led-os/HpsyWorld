package com.kuwai.ysy.module.mine.business.wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.WalletDetailsBean;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import io.reactivex.functions.Consumer;

public class WithDrawFragment extends BaseFragment implements View.OnClickListener {

    private NavigationLayout mNavigation;
    private EditText mEtCashMoney;
    private TextView mTvAll;
    private EditText mEtZhiName;
    private EditText mEtZhiNum;
    private Button mBtnCash;

    private String balance = "";

    public static WithDrawFragment newInstance(String balance) {
        Bundle args = new Bundle();
        args.putString("balance", balance);
        WithDrawFragment fragment = new WithDrawFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.cash_to_zfb_fragment;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_all:
                mEtCashMoney.setText(balance);
                break;
            case R.id.btn_cash:
                if (Utils.isNullString(mEtCashMoney.getText().toString())) {
                    ToastUtils.showShort("请输入提现金额");
                    return;
                } else if (Utils.isNullString(mEtZhiNum.getText().toString())) {
                    ToastUtils.showShort("请输入支付宝账号");
                    return;
                } else if (Utils.isNullString(mEtZhiName.getText().toString())) {
                    ToastUtils.showShort("请输入真实姓名");
                    return;
                } else {
                    withDraw();
                }
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        balance = getArguments().getString("balance");

        mNavigation = mRootView.findViewById(R.id.navigation);
        mEtCashMoney = mRootView.findViewById(R.id.et_cash_money);
        mTvAll = mRootView.findViewById(R.id.tv_all);
        mEtZhiName = mRootView.findViewById(R.id.et_zhi_name);
        mEtZhiNum = mRootView.findViewById(R.id.et_zhi_num);
        mBtnCash = mRootView.findViewById(R.id.btn_cash);
        mTvAll.setOnClickListener(this);
        mBtnCash.setOnClickListener(this);

        mNavigation.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    private void withDraw() {
        addSubscription(MineApiFactory.walletWithdrawals(SPManager.get().getStringValue("uid"), mEtCashMoney.getText().toString(), mEtZhiNum.getText().toString(), mEtZhiName.getText().toString()).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse simpleResponse) throws Exception {
                if (simpleResponse.code == 200) {

                }
                ToastUtils.showShort(simpleResponse.msg);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
            }
        }));
    }
}
