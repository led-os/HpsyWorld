package com.kuwai.ysy.module.circle.business.DyDashang;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.adapter.message.DashangAdapter;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.circle.bean.DyRewardlistBean;
import com.kuwai.ysy.module.circle.business.DyDetail.DyDetailContract;
import com.kuwai.ysy.utils.EventBusUtil;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.SPManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class DyDashangListFragment extends BaseFragment<DyDashangPresenter> implements DyDashangContract.IHomeView, View.OnClickListener {

    private DashangAdapter mDateAdapter;
    private RecyclerView mDongtaiList;
    private List<CategoryBean> mDataList = new ArrayList<>();
    private String did;
    private int page = 1;

    public static DyDashangListFragment newInstance(Bundle bundle) {
        DyDashangListFragment fragment = new DyDashangListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.dashang_recyclerview;
    }

    @Override
    protected DyDashangPresenter getPresenter() {
        return new DyDashangPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        EventBusUtil.register(this);
        mLayoutStatusView = mRootView.findViewById(R.id.multipleStatusView);
        mDongtaiList = mRootView.findViewById(R.id.recyclerView);
        //mDongtaiList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mDongtaiList.setLayoutManager(linearLayoutManager);
        //mDongtaiList.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 1), R.color.line_color));
        mDateAdapter = new DashangAdapter();
        mDongtaiList.setAdapter(mDateAdapter);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if ("1".equals(getArguments().getString("type"))) {
            did = getArguments().getString("did");
            mPresenter.requestHomeData(String.valueOf(did), page);
        } else {
            did = getArguments().getString("did");
            mPresenter.requestHoleData(String.valueOf(did), page);
        }
    }


    @Override
    public void setHomeData(DyRewardlistBean dyRewardlistBean) {
        if (dyRewardlistBean.getData() != null) {
            mLayoutStatusView.showContent();
            mDateAdapter.replaceData(dyRewardlistBean.getData());
        } else {
            mLayoutStatusView.showEmpty();
        }

    }

    @Override
    public void showError(int errorCode, String msg) {

    }

    @Override
    public void showViewLoading() {
        mLayoutStatusView.showLoading();
    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showViewError(Throwable t) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusUtil.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isLogin(MessageEvent event) {
        if (event.getCode() == C.MSG_REWARD_DY) {
            mPresenter.requestHomeData(String.valueOf(did), page);
        }else if(event.getCode() == C.MSG_REWARD_HOLE){
            mPresenter.requestHomeData(String.valueOf(did), page);
        }
    }
}
