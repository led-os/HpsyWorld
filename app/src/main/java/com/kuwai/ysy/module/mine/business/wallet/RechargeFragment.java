package com.kuwai.ysy.module.mine.business.wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.adapter.RechargeAdapter;
import com.kuwai.ysy.module.mine.bean.RechargeBean;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;
import java.util.List;

public class RechargeFragment extends BaseFragment implements View.OnClickListener {

    private NavigationLayout mNavigation;
    private SuperTextView mTvCurrent;
    private RecyclerView mRvVoucher;
    private Button mBtnCash;
    private List<RechargeBean> mDadaList = new ArrayList<>();

    private String balance = "";
    private RechargeAdapter rechargeAdapter;

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
                rechargeAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
