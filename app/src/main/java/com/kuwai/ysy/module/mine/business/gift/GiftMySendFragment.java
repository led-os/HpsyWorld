package com.kuwai.ysy.module.mine.business.gift;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.mine.adapter.GiftAdapter;
import com.kuwai.ysy.module.mine.adapter.GiftSendAdapter;
import com.kuwai.ysy.module.mine.bean.GiftAcceptBean;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyRecycleViewDivider;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.List;

public class GiftMySendFragment extends BaseFragment<GiftMySendPresenter> implements GiftMySendContract.IHomeView, View.OnClickListener {

    private GiftSendAdapter mDateAdapter;
    private RecyclerView mDongtaiList;
    private List<CategoryBean> mDataList = new ArrayList<>();
    private int page = 1;

    public static GiftMySendFragment newInstance() {
        Bundle args = new Bundle();
        GiftMySendFragment fragment = new GiftMySendFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.recyclerview;
    }

    @Override
    protected GiftMySendPresenter getPresenter() {
        return new GiftMySendPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDongtaiList = mRootView.findViewById(R.id.recyclerView);
        mDateAdapter = new GiftSendAdapter();
        mDongtaiList.setAdapter(mDateAdapter);
        mDongtaiList.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 0.5f), R.color.line_color));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestHomeData(SPManager.get().getStringValue("uid"), page);

    }

    @Override
    public void setHomeData(GiftAcceptBean giftAcceptBean) {
        mDateAdapter.addData(giftAcceptBean.getData().getGift());

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
