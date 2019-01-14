package com.kuwai.ysy.module.mine.business.like;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.adapter.LikeEachOtherAdapter;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.EarlierBean;
import com.kuwai.ysy.module.mine.bean.LikeEach;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;

public class LikeEachOtherFragment extends BaseFragment<LikeEachOtherPresenter> implements LikeEachOtherContract.IHomeView, View.OnClickListener {

    RecyclerView mRecyclerView;
    LikeEachOtherAdapter adapter;
    ArrayList<LikeEach.DataBean> list;
    private int mPage = 1;
    private SmartRefreshLayout mRefreshLayout;

    public static LikeEachOtherFragment newInstance() {
        Bundle args = new Bundle();
        LikeEachOtherFragment fragment = new LikeEachOtherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.smart_refresh;
    }

    @Override
    protected LikeEachOtherPresenter getPresenter() {
        return new LikeEachOtherPresenter(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerView);
        mRefreshLayout = mRootView.findViewById(R.id.mRefreshLayout);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                mPresenter.requestHomeData(SPManager.get().getStringValue("uid"), mPage);
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getMore();
            }
        });
        adapter = new LikeEachOtherAdapter();
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestHomeData(SPManager.get().getStringValue("uid"), mPage);
    }

    @Override
    public void setHomeData(LikeEach todayBean) {
        mRefreshLayout.finishRefresh();
        adapter.replaceData(todayBean.getData());
    }

    private void getMore() {
        addSubscription(MineApiFactory.getLikeEachOther(SPManager.get().getStringValue("uid"), mPage + 1).subscribe(new Consumer<LikeEach>() {
            @Override
            public void accept(LikeEach likeEach) throws Exception {
                mRefreshLayout.finishLoadmore();
                if (likeEach.getCode() == 200) {
                    if (likeEach.getData() != null) {
                        mPage++;
                    }
                    adapter.addData(likeEach.getData());
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
                ToastUtils.showShort(R.string.server_error);
            }
        }));
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
