package com.kuwai.ysy.module.mine.business.wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.adapter.GiftAdapter;
import com.kuwai.ysy.module.mine.adapter.WalletDetailsAdapter;
import com.kuwai.ysy.module.mine.bean.WalletDetailsBean;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.SPManager;

public class WalletDetailsFragment extends BaseFragment<WalletDetailsPresenter> implements WalletDetailsContract.IHomeView, View.OnClickListener {

    private NavigationLayout navigationLayout;
    private RecyclerView mlist;
    private WalletDetailsAdapter mDateAdapter;
    private int page = 1;

    public static WalletDetailsFragment newInstance() {

        Bundle args = new Bundle();

        WalletDetailsFragment fragment = new WalletDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        navigationLayout = mRootView.findViewById(R.id.navigation);
        navigationLayout.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });

        mlist = mRootView.findViewById(R.id.recyclerView);
        mDateAdapter = new WalletDetailsAdapter();
        mlist.setAdapter(mDateAdapter);
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.walletdetails_layout;
    }

    @Override
    protected WalletDetailsPresenter getPresenter() {
        return new WalletDetailsPresenter(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestHomeData(SPManager.getStringValue("uid", "1"), page);
    }

    @Override
    public void setHomeData(WalletDetailsBean walletDetailsBean) {
        mDateAdapter.addData(walletDetailsBean.getData());
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
    public void onClick(View view) {

    }
}
