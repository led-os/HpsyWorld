package com.kuwai.ysy.module.mine.business.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.adapter.DyZanAdapter;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.circle.bean.DyMainListBean;
import com.kuwai.ysy.module.circle.business.publishdy.PublishDyActivity;
import com.kuwai.ysy.module.mine.adapter.homepage.MineDyAdapter;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyRecycleViewDivider;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class PageDyMineFragment extends BaseFragment implements View.OnClickListener {

    private MineDyAdapter mDateAdapter;
    private RecyclerView mDongtaiList;
    private ImageView imgAdd;
    private SmartRefreshLayout mRefreshLayout;
    private int mPage = 1;
    private String uid = "";
    private DyMainListBean mDyMainListBean;

    public static PageDyMineFragment newInstance() {
        Bundle args = new Bundle();
        PageDyMineFragment fragment = new PageDyMineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_page_dy_mine;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_add:
                startActivity(new Intent(getActivity(), PublishDyActivity.class));
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        uid = SPManager.get().getStringValue("uid");
        mDongtaiList = mRootView.findViewById(R.id.recyclerView);
        imgAdd = mRootView.findViewById(R.id.img_add);
        imgAdd.setOnClickListener(this);

        mRefreshLayout = mRootView.findViewById(R.id.mRefreshLayout);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                getDy(mPage, uid);
            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getMore(mPage + 1, uid);
            }
        });

        mDongtaiList.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 0.5f), R.color.line_color));
        mDateAdapter = new MineDyAdapter();
        mDongtaiList.setAdapter(mDateAdapter);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getDy(mPage, uid);
    }

    private void getDy(int page, String uid) {
        addSubscription(MineApiFactory.getPersonalDynamic(uid, page).subscribe(new Consumer<DyMainListBean>() {
            @Override
            public void accept(DyMainListBean dyMainListBean) throws Exception {
                mRefreshLayout.finishRefresh();
                mDyMainListBean = dyMainListBean;
                mDateAdapter.replaceData(dyMainListBean.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

    private void getMore(int page, String uid) {
        addSubscription(MineApiFactory.getPersonalDynamic(uid, page).subscribe(new Consumer<DyMainListBean>() {
            @Override
            public void accept(DyMainListBean dyMainListBean) throws Exception {
                if (dyMainListBean.getData() != null) {
                    mPage++;
                }
                mRefreshLayout.finishLoadmore();
                mDyMainListBean.getData().addAll(dyMainListBean.getData());
                mDateAdapter.addData(dyMainListBean.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
            }
        }));
    }
}
