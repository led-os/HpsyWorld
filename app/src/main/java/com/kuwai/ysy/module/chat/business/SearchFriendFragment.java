package com.kuwai.ysy.module.chat.business;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.adapter.FindFriendsAdapter;
import com.kuwai.ysy.module.chat.adapter.MyFriendsAdapter;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.chat.bean.MyFriends;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyEditText;
import com.kuwai.ysy.widget.MyRecycleViewDivider;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

public class SearchFriendFragment extends BaseFragment implements View.OnClickListener {

    private TextView search;
    private RecyclerView mFriendRl;
    private FindFriendsAdapter myFriendsAdapter;
    private MyEditText et_search;
    private List<MyFriends.DataBean> mDatalist = new ArrayList<>();

    private SmartRefreshLayout mRefreshLayout;
    private int mPage = 1;
    private String uid;
    private MyFriends mDyMainListBean;

    public static SearchFriendFragment newInstance() {
        Bundle args = new Bundle();
        SearchFriendFragment fragment = new SearchFriendFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_search_friend;
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
        ((ImageView) mRootView.findViewById(R.id.navigation)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        search = mRootView.findViewById(R.id.search);
        mFriendRl = mRootView.findViewById(R.id.rl_my_friend);
        myFriendsAdapter = new FindFriendsAdapter(mDatalist);
        et_search = mRootView.findViewById(R.id.et_search);
        mFriendRl.setAdapter(myFriendsAdapter);
        mFriendRl.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mFriendRl.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 1), R.color.line_color));
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPage = 1;
                getFriends(et_search.getText().toString());
            }
        });

        mRefreshLayout = mRootView.findViewById(R.id.mRefreshLayout);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                getFriends(et_search.getText().toString());
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getMore(et_search.getText().toString());
            }
        });

        myFriendsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.btn_add_friend:
                        addFriends(String.valueOf(mDatalist.get(position).getUid()));
                        break;
                }
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    void addFriends(String otherId) {
        addSubscription(ChatApiFactory.addFriends(SPManager.get().getStringValue("uid"), otherId).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                if (response.code == 200) {
                    //newFriendsAdapter.replaceData(myBlindBean.getData());
                }
                ToastUtils.showShort(response.msg);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
                //ToastUtils.showShort("网络错误");
            }
        }));
    }

    void getFriends(String text) {
        addSubscription(ChatApiFactory.searchFriends(text, mPage).subscribe(new Consumer<MyFriends>() {
            @Override
            public void accept(MyFriends myBlindBean) throws Exception {
                if (myBlindBean.getCode() == 200) {
                    mRefreshLayout.finishRefresh();
                    mDyMainListBean = myBlindBean;
                    myFriendsAdapter.replaceData(myBlindBean.getData());
                } else {
                    ToastUtils.showShort(myBlindBean.getMsg());
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //ToastUtils.showShort("网络错误");
            }
        }));
    }

    private void getMore(String text) {
        addSubscription(ChatApiFactory.getTuiFriends(text, mPage + 1).subscribe(new Consumer<MyFriends>() {
            @Override
            public void accept(MyFriends myFriends) throws Exception {
                if (myFriends.getData() != null) {
                    mPage++;
                }
                mRefreshLayout.finishLoadmore();
                mDyMainListBean.getData().addAll(myFriends.getData());
                myFriendsAdapter.addData(myFriends.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
            }
        }));
    }
}
