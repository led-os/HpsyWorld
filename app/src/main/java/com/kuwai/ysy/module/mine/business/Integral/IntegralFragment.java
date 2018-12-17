package com.kuwai.ysy.module.mine.business.Integral;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.adapter.GiftSendAdapter;
import com.kuwai.ysy.module.mine.adapter.IntegralDetailAdapter;
import com.kuwai.ysy.module.mine.bean.IntegralDetailBean;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyRecycleViewDivider;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.SPManager;

public class IntegralFragment extends BaseFragment<IntegralDetailPresenter> implements IntegralDetailContract.IHomeView {


    private IntegralDetailAdapter mAdapter;
    private RecyclerView mRvIntegral;
    private int page = 1;

    @Override
    public void initView(Bundle savedInstanceState) {
        mRvIntegral = mRootView.findViewById(R.id.rv_integral);
        mAdapter = new IntegralDetailAdapter(R.layout.item_integral_detail);
        mRvIntegral.setAdapter(mAdapter);
        mRvIntegral.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 0.5f), R.color.line_color));

    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.integral_layout;
    }

    @Override
    protected IntegralDetailPresenter getPresenter() {
        return new IntegralDetailPresenter(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestHomeData(SPManager.getStringValue("uid", "1"), page);
    }

    @Override
    public void setHomeData(IntegralDetailBean integralDetailBean) {
        mAdapter.addData(integralDetailBean.getData());
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
