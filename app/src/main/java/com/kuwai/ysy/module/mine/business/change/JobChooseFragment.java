package com.kuwai.ysy.module.mine.business.change;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.adapter.message.LikeMsgAdapter;
import com.kuwai.ysy.module.mine.adapter.JobAdapter;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.JobBean;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyRecycleViewDivider;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import io.reactivex.functions.Consumer;

public class JobChooseFragment extends BaseFragment implements View.OnClickListener {

    private NavigationLayout mNavigation;
    private JobAdapter mDateAdapter;
    private RecyclerView mDongtaiList;
    private SmartRefreshLayout mRefreshLayout;

    public static JobChooseFragment newInstance() {
        Bundle args = new Bundle();
        JobChooseFragment fragment = new JobChooseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.msg_recyclerview;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mRefreshLayout = mRootView.findViewById(R.id.mRefreshLayout);
        mLayoutStatusView = mRootView.findViewById(R.id.multipleStatusView);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        mRefreshLayout.setEnableLoadmore(false);
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                changeXuan();
            }
        });

        mDongtaiList = mRootView.findViewById(R.id.recyclerView);
        mDongtaiList.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 0.5f), R.color.line_color));
        mDateAdapter = new JobAdapter();
        mDongtaiList.setAdapter(mDateAdapter);
        mNavigation = mRootView.findViewById(R.id.navigation);
        mNavigation.setTitle("职业");
        /*mNavigation.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeXuan();
            }
        });*/
        mNavigation.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });

        mDateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("job", mDateAdapter.getData().get(position).getName());
                bundle.putString("id",mDateAdapter.getData().get(position).getO_id());
                setFragmentResult(RESULT_OK, bundle);
                pop();
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        changeXuan();
    }

    private void changeXuan() {
        addSubscription(MineApiFactory.getAllJob().subscribe(new Consumer<JobBean>() {
            @Override
            public void accept(JobBean response) throws Exception {
                if (response.getCode() == 200) {
                    mDateAdapter.replaceData(response.getData());
                }
                //ToastUtils.showShort(response.getMsg());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
            }
        }));
    }
}
