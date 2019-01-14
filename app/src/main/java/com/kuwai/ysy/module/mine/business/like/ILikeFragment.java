package com.kuwai.ysy.module.mine.business.like;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.callback.LookmeCallback;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.adapter.ExpandableItemAdapter;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.EarlierBean;
import com.kuwai.ysy.module.mine.bean.LikeParent;
import com.kuwai.ysy.module.mine.bean.LikeParentBean;
import com.kuwai.ysy.module.mine.bean.VisitorBean;
import com.kuwai.ysy.module.mine.bean.VisitorMore;
import com.kuwai.ysy.module.mine.bean.like.ParentLevel;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;

public class ILikeFragment extends BaseFragment<ILikePresenter> implements ILikeContract.IHomeView, View.OnClickListener {

    RecyclerView mRecyclerView;
    ExpandableItemAdapter adapter;
    ArrayList<MultiItemEntity> res = new ArrayList<>();

    private int mPage = 1;
    private SmartRefreshLayout mRefreshLayout;

    public static ILikeFragment newInstance() {
        Bundle args = new Bundle();
        ILikeFragment fragment = new ILikeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.smart_refresh;
    }

    @Override
    protected ILikePresenter getPresenter() {
        return new ILikePresenter(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerView);

        mRecyclerView.setAdapter(adapter);
        mRefreshLayout = mRootView.findViewById(R.id.mRefreshLayout);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                mPresenter.requestHomeData(SPManager.get().getStringValue("uid"));
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getMore();
            }
        });

        adapter = new ExpandableItemAdapter(res);
        mRecyclerView.setAdapter(adapter);
        // important! setLayoutManager should be called after setAdapter
        //mRecyclerView.setLayoutManager(manager);
        adapter.expandAll();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestHomeData(SPManager.get().getStringValue("uid"));
    }

    @Override
    public void setHomeData(LikeParentBean visitorBean) {
        mRefreshLayout.finishRefresh();
        int earlyDataSize = visitorBean.getData().getEarlier().size();
        int todayDataSize = visitorBean.getData().getToday().size();

        res.clear();
        LikeParent today = new LikeParent("今天", "");
        for (int i = 0; i < todayDataSize; i++) {
            today.addSubItem(visitorBean.getData().getToday().get(i));
        }
        res.add(today);

        LikeParent lv0 = new LikeParent("更早", "");
        for (int j = 0; j < earlyDataSize; j++) {
            lv0.addSubItem(visitorBean.getData().getEarlier().get(j));
        }
        res.add(lv0);

        adapter.replaceData(res);
        adapter.expandAll();
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

    private void getMore() {
        addSubscription(MineApiFactory.getIloveEarlier(SPManager.get().getStringValue("uid"), mPage + 1).subscribe(new Consumer<EarlierBean>() {
            @Override
            public void accept(EarlierBean visitorBean) throws Exception {
                mRefreshLayout.finishLoadmore();
                if (visitorBean.getCode() == 200) {
                    if (visitorBean.getData() != null) {
                        mPage++;
                    }
                    for (int j = 0; j < visitorBean.getData().size(); j++) {
                        res.add(visitorBean.getData().get(j));
                        //((ParentLevel) list.get(topSize + 1)).addSubItem(visitorBean.getData().get(j));
                    }
                    adapter.replaceData(res);
                    adapter.expandAll();
                } else if (visitorBean.getCode() == 400) {

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
}
