package com.kuwai.ysy.module.home.business;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.home.WebviewH5Activity;
import com.kuwai.ysy.module.home.adapter.HomeAdapter;
import com.kuwai.ysy.module.home.bean.HomeBean;
import com.kuwai.ysy.module.home.bean.HomeMutiBean;
import com.kuwai.ysy.module.home.business.sading.AskFragment;
import com.rayhahah.rbase.utils.base.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.IHomeView {

    private HomeAdapter mHomeAdapter = null;
    private RecyclerView mRecycleView = null;
    private SmartRefreshLayout mRefreshLayout;
    List<HomeMutiBean> list = new ArrayList<>();

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            //接收到的参数处理
        }
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mRecycleView = mRootView.findViewById(R.id.mRecyclerView);
        mRefreshLayout = mRootView.findViewById(R.id.mRefreshLayout);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPresenter.requestHomeData(2);
                mRefreshLayout.finishRefresh();
            }
        });
    }

    @Override
    protected HomePresenter getPresenter() {
        return new HomePresenter(this);
    }

    @Override
    public void setHomeData(HomeBean homeBean) {
        for (int i = 0; i < 10; i++) {
            list.add(new HomeMutiBean(HomeMutiBean.TEXT));
            list.add(new HomeMutiBean(HomeMutiBean.IMG_TEXT));
            list.add(new HomeMutiBean(HomeMutiBean.VIDEO));
            list.add(new HomeMutiBean(HomeMutiBean.VIDEO));
            list.add(new HomeMutiBean(HomeMutiBean.VIDEO));
            list.add(new HomeMutiBean(HomeMutiBean.IMG_TEXT));
            list.add(new HomeMutiBean(HomeMutiBean.VIDEO));
            list.add(new HomeMutiBean(HomeMutiBean.VIDEO));
            list.add(new HomeMutiBean(HomeMutiBean.TEXT));
            list.add(new HomeMutiBean(HomeMutiBean.TEXT));
            list.add(new HomeMutiBean(HomeMutiBean.IMG_TEXT));
        }
        mHomeAdapter = new HomeAdapter(list);
        mRecycleView.setAdapter(mHomeAdapter);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //mRecycleView.addOnScrollListener(new HomeActivity.ListScrollListener());

        mHomeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(), WebviewH5Activity.class));
            }
        });
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
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestHomeData(1);
    }
}
