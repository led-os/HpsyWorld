package com.kuwai.ysy.module.circle.business.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.chat.bean.MyFriends;
import com.kuwai.ysy.module.circle.DyDetailActivity;
import com.kuwai.ysy.module.circle.HoleDetailActivity;
import com.kuwai.ysy.module.circle.adapter.message.DashangAdapter;
import com.kuwai.ysy.module.circle.adapter.message.LikeMsgAdapter;
import com.kuwai.ysy.module.circle.api.CircleApiFactory;
import com.kuwai.ysy.module.circle.bean.AllLikeBean;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class DyLikeMesFragment extends BaseFragment implements View.OnClickListener {

    private LikeMsgAdapter mDateAdapter;
    private RecyclerView mDongtaiList;
    private SmartRefreshLayout mRefreshLayout;
    private NavigationLayout navigationLayout;
    private int mPage = 1;
    private String uid = "";
    private AllLikeBean mAllLikeBean;

    public static DyLikeMesFragment newInstance() {
        Bundle args = new Bundle();
        DyLikeMesFragment fragment = new DyLikeMesFragment();
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
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                getFriends();
            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getMore();
            }
        });

        mDongtaiList = mRootView.findViewById(R.id.recyclerView);
        navigationLayout = mRootView.findViewById(R.id.navigation);
        navigationLayout.setTitle("点赞");
        navigationLayout.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        mDongtaiList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //mDongtaiList.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 1), R.color.line_color));
        mDateAdapter = new LikeMsgAdapter();
        mDongtaiList.setAdapter(mDateAdapter);

        mDateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mDateAdapter.getData().get(position).getType() == -1) {
                    //树洞
                    Intent intent = new Intent(getActivity(), HoleDetailActivity.class);
                    intent.putExtra("tid", String.valueOf(mDateAdapter.getData().get(position).getD_id()));
                    startActivity(intent);
                } else {
                    //动态
                    Intent intent = new Intent(getActivity(), DyDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("did", String.valueOf(mDateAdapter.getData().get(position).getD_id()));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        uid = SPManager.get().getStringValue("uid");
        getFriends();
    }

    void getFriends() {
        addSubscription(CircleApiFactory.getAllLikeListData(uid, mPage).subscribe(new Consumer<AllLikeBean>() {
            @Override
            public void accept(AllLikeBean myBlindBean) throws Exception {
                EventBusUtil.sendEvent(new MessageEvent(C.MSG_UNREAD_UPDATE));
                mRefreshLayout.finishRefresh();
                if (myBlindBean.getCode() == 200 && myBlindBean.getData().size() > 0) {
                    mAllLikeBean = myBlindBean;
                    mDateAdapter.replaceData(myBlindBean.getData());
                    mLayoutStatusView.showContent();
                } else {
                    mLayoutStatusView.showError();
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtils.showShort("网络错误");
            }
        }));
    }

    private void getMore() {
        addSubscription(CircleApiFactory.getAllLikeListData(uid, mPage + 1).subscribe(new Consumer<AllLikeBean>() {
            @Override
            public void accept(AllLikeBean myFriends) throws Exception {
                if (myFriends.getData() != null) {
                    mPage++;
                }
                mRefreshLayout.finishLoadmore();
                mAllLikeBean.getData().addAll(myFriends.getData());
                mDateAdapter.addData(myFriends.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
            }
        }));
    }
}
