package com.kuwai.ysy.module.mine.business.homepage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.adapter.GiftBoxAdapter;
import com.kuwai.ysy.module.mine.adapter.TaGiftAdapter;
import com.kuwai.ysy.module.mine.bean.GiftBoxBean;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyRecycleViewDivider;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;

public class GiftBoxFragment extends BaseFragment<GiftBoxPresenter> implements GiftBoxContract.IHomeView {
    private RecyclerView mRVBox;
    private NavigationLayout navigationLayout;
    private GiftBoxAdapter mAdapter;
    private int page = 1;

    public static GiftBoxFragment newInstance(Bundle bundle) {


        GiftBoxFragment fragment = new GiftBoxFragment();
        fragment.setArguments(bundle);
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

        mRVBox = mRootView.findViewById(R.id.rv_box);
        mAdapter = new GiftBoxAdapter();
        mRVBox.setAdapter(mAdapter);
        mRVBox.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 0.5f), R.color.line_color));

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestHomeData(getArguments().getString("uid"));
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.gift_box;
    }

    @Override
    protected GiftBoxPresenter getPresenter() {
        return new GiftBoxPresenter(this);
    }

    @Override
    public void setHomeData(GiftBoxBean giftBoxBean) {
        mAdapter.addData(giftBoxBean.getData());
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
