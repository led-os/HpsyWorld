package com.kuwai.ysy.module.mine.business.homepage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.adapter.TaGiftAdapter;
import com.kuwai.ysy.module.mine.bean.TaGiftBean;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyRecycleViewDivider;
import com.kuwai.ysy.widget.NavigationLayout;

public class TaAcceptGiftFragment extends BaseFragment<TaAcceptGiftPresenter> implements TaAcceptGiftContract.IHomeView, View.OnClickListener {


    private NavigationLayout navigationLayout;
    private RecyclerView mRvgift;
    private TaGiftAdapter mAdapter;
    private int page = 1;
    private String otherid;

    public static TaAcceptGiftFragment newInstance(Bundle bundle) {

        TaAcceptGiftFragment fragment = new TaAcceptGiftFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onClick(View view) {
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
        navigationLayout.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("uid",otherid);

                start(GiftBoxFragment.newInstance(bundle));
            }
        });

        mRvgift = mRootView.findViewById(R.id.rv_gift);
        mAdapter = new TaGiftAdapter();
        mRvgift.setAdapter(mAdapter);
        mRvgift.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 0.5f), R.color.line_color));

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
         otherid = getArguments().getString("uid");
        mPresenter.requestHomeData(otherid, page);
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.ta_accept_gift;
    }

    @Override
    protected TaAcceptGiftPresenter getPresenter() {
        return new TaAcceptGiftPresenter(this);
    }

    @Override
    public void setHomeData(TaGiftBean giftBean) {
        mAdapter.addData(giftBean.getData());
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
