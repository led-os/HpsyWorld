package com.kuwai.ysy.module.circle;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.home.bean.HomeBean;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;
import java.util.List;


public class CircleFragment extends BaseFragment{

    private SwipeRefreshLayout mSwipeRefreshLayout;
    //private FriendCircleAdapter mFriendCircleAdapter;
    //private CircleAdapter circleAdapter;
    private List<HomeBean.IssueListBean.ItemListBean> dataList= new ArrayList<>();

    public static CircleFragment newInstance() {
        Bundle args = new Bundle();
        CircleFragment fragment = new CircleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_circle;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mSwipeRefreshLayout = mRootView.findViewById(R.id.swpie_refresh_layout);
        RecyclerView recyclerView = mRootView.findViewById(R.id.recycler_view);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(CircleFragment.this).resumeRequests();
                } else {
                    Glide.with(CircleFragment.this).pauseRequests();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        HomeBean.IssueListBean.ItemListBean bean = new HomeBean.IssueListBean.ItemListBean();
        bean.setData(new HomeBean.IssueListBean.ItemListBean.DataBean());
        bean.getData().setImage("http://pic.chinahpsy.com/home/750/cq.jpg");
        dataList.add(bean);
        //circleAdapter = new CircleAdapter(dataList);
        //recyclerView.setAdapter(circleAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

}
