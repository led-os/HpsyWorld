package com.kuwai.ysy.module.home.business.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.home.adapter.NearPerAdapter;
import com.kuwai.ysy.module.home.api.HomeApiFactory;
import com.kuwai.ysy.module.home.bean.HomeCardBean;
import com.kuwai.ysy.module.home.bean.HomeVideoBean;
import com.kuwai.ysy.module.home.bean.main.NearPerBean;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.EarlierBean;
import com.kuwai.ysy.utils.DialogUtil;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyRecycleViewDivider;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.functions.Consumer;

public class NearPerFragment extends BaseFragment implements View.OnClickListener {

    RecyclerView mRecyclerView;
    NearPerAdapter mAdapter;

    private int mPage = 1;
    private SmartRefreshLayout mRefreshLayout;

    public static NearPerFragment newInstance() {
        Bundle args = new Bundle();
        NearPerFragment fragment = new NearPerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.smart_refresh;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
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
                getHomeData();
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                //getMore();
                getMoreData();
            }
        });
        mRecyclerView.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 0.5f), R.color.line_color));
        mAdapter = new NearPerAdapter();

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), CardDetailActivity.class);
                intent.putExtra("id", String.valueOf(mAdapter.getData().get(position).getUid()));
                startActivity(intent);
            }
        });
        // important! setLayoutManager should be called after setAdapter
        //mRecyclerView.setLayoutManager(manager);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getHomeData();
    }

    void getHomeData() {
        HashMap<String, Object> param = new HashMap<>();
        if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
            param.put("uid", SPManager.get().getStringValue("uid"));
        }
        param.put("longitude", SPManager.get().getStringValue("longitude", "120.525565"));
        param.put("latitude", SPManager.get().getStringValue("latitude", "31.27831"));
        param.put("lastcity", SPManager.get().getStringValue("ysy_city", "苏州市"));
        param.put("lastarea", SPManager.get().getStringValue("ysy_dis", "吴中区"));
        param.put("page", mPage);
        addSubscription(HomeApiFactory.getNearPer(param).subscribe(new Consumer<NearPerBean>() {
            @Override
            public void accept(NearPerBean nearPerBean) throws Exception {
                mAdapter.replaceData(nearPerBean.getData());
                mRefreshLayout.finishRefresh();
                //adapter.addAll(homeCardBean.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtils.showShort(R.string.server_error);
            }
        }));
    }

    void getMoreData() {
        HashMap<String, Object> param = new HashMap<>();
        if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
            param.put("uid", SPManager.get().getStringValue("uid"));
        }
        param.put("longitude", SPManager.get().getStringValue("longitude", "120.525565"));
        param.put("latitude", SPManager.get().getStringValue("latitude", "31.27831"));
        param.put("lastcity", SPManager.get().getStringValue("ysy_city", "苏州市"));
        param.put("lastarea", SPManager.get().getStringValue("ysy_dis", "吴中区"));
        param.put("page", mPage + 1);
        addSubscription(HomeApiFactory.getNearPer(param).subscribe(new Consumer<NearPerBean>() {
            @Override
            public void accept(NearPerBean nearPerBean) throws Exception {
                mRefreshLayout.finishLoadmore();
                if (nearPerBean.getCode() == 200) {
                    if (nearPerBean.getData() != null && nearPerBean.getData().size() > 0) {
                        mPage++;
                    }
                    mAdapter.addData(nearPerBean.getData());
                }
                //adapter.addAll(homeCardBean.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mRefreshLayout.finishLoadmore();
                ToastUtils.showShort(R.string.server_error);
            }
        }));
    }
}
