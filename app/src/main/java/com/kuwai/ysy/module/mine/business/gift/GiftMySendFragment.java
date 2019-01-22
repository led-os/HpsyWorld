package com.kuwai.ysy.module.mine.business.gift;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.chat.bean.NoticeBean;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.mine.adapter.GiftAdapter;
import com.kuwai.ysy.module.mine.adapter.GiftSendAdapter;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.GiftAcceptBean;
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

public class GiftMySendFragment extends BaseFragment<GiftMySendPresenter> implements GiftMySendContract.IHomeView, View.OnClickListener {

    private GiftSendAdapter mDateAdapter;
    private RecyclerView mDongtaiList;
    private List<CategoryBean> mDataList = new ArrayList<>();
    private SmartRefreshLayout mRefreshLayout;
    private int mPage = 1;

    public static GiftMySendFragment newInstance() {
        Bundle args = new Bundle();
        GiftMySendFragment fragment = new GiftMySendFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.smart_refresh;
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

        mLayoutStatusView = mRootView.findViewById(R.id.multipleStatusView);
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
                getMore(SPManager.get().getStringValue("uid"), mPage + 1);
            }
        });

        mDateAdapter = new GiftSendAdapter();
        mDongtaiList.setAdapter(mDateAdapter);
        mDongtaiList.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 0.5f), R.color.line_color));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestHomeData(SPManager.get().getStringValue("uid"), mPage);
    }

    @Override
    public void setHomeData(GiftAcceptBean giftAcceptBean) {
        mRefreshLayout.finishRefresh();
        if (giftAcceptBean.getCode() == 200 && giftAcceptBean.getData().getGift().size() > 0) {
            mDateAdapter.replaceData(giftAcceptBean.getData().getGift());
            mLayoutStatusView.showContent();
        } else {
            mDateAdapter.getData().clear();
            mDateAdapter.notifyDataSetChanged();
            mLayoutStatusView.showError();
        }
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

    public void getMore(String uid, int page) {
        addSubscription(MineApiFactory.getGiftSend(uid, page).subscribe(new Consumer<GiftAcceptBean>() {
            @Override
            public void accept(GiftAcceptBean giftAcceptBean) throws Exception {
                mRefreshLayout.finishLoadmore();
                if (giftAcceptBean.getCode() == 200) {
                    if (giftAcceptBean.getData().getGift() != null && giftAcceptBean.getData().getGift().size() > 0) {
                        mPage++;
                    }

                    mDateAdapter.addData(giftAcceptBean.getData().getGift());
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //
            }
        }));
    }
}
