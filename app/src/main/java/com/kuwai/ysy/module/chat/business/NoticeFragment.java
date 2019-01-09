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
import com.kuwai.ysy.module.chat.adapter.NoticeTransAdapter;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.chat.bean.MyFriends;
import com.kuwai.ysy.module.chat.bean.NoticeBean;
import com.kuwai.ysy.module.home.bean.HomeVideoBean;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyEditText;
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
import java.util.List;

import io.reactivex.functions.Consumer;

public class NoticeFragment extends BaseFragment implements View.OnClickListener {

    private ImageView mDownDate;
    private RecyclerView mRlDate;
    private ImageView mDownSystem;
    private SmartRefreshLayout mMRefreshLayout;
    private RecyclerView mRlSystem;

    private NoticeTransAdapter myFriendsAdapter;
    private List<NoticeBean.DataBean> mDatalist = new ArrayList<>();

    private SmartRefreshLayout mRefreshLayout;
    private int mPage = 1;
    private String uid;

    public static NoticeFragment newInstance() {
        Bundle args = new Bundle();
        NoticeFragment fragment = new NoticeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_notice;
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
        mDownDate = mRootView.findViewById(R.id.down_date);
        mRlDate = mRootView.findViewById(R.id.rl_date);
        mDownSystem = mRootView.findViewById(R.id.down_system);
        mMRefreshLayout = mRootView.findViewById(R.id.mRefreshLayout);
        mRlSystem = mRootView.findViewById(R.id.rl_system);
        myFriendsAdapter = new NoticeTransAdapter();
        mRlSystem.setAdapter(myFriendsAdapter);
        mRlSystem.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRlSystem.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 1), R.color.line_color));

        mRefreshLayout = mRootView.findViewById(R.id.mRefreshLayout);
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

        myFriendsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.btn_add_friend:
                        //addFriends(String.valueOf(mDatalist.get(position).getUid()));
                        break;
                }
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        uid = SPManager.get().getStringValue("uid");
        uid = "1";
        getFriends();
    }

    void getFriends() {
        addSubscription(ChatApiFactory.getSystemNotice(uid, mPage).subscribe(new Consumer<NoticeBean>() {
            @Override
            public void accept(NoticeBean noticeBean) throws Exception {
                mRefreshLayout.finishRefresh();
                if (noticeBean.getCode() == 200) {
                    myFriendsAdapter.replaceData(noticeBean.getData());
                } else {
                    ToastUtils.showShort(noticeBean.getMsg());
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //ToastUtils.showShort("网络错误");
            }
        }));
    }

    private void getMore() {
        addSubscription(ChatApiFactory.getSystemNotice(uid, mPage + 1).subscribe(new Consumer<NoticeBean>() {
            @Override
            public void accept(NoticeBean myFriends) throws Exception {
                if (myFriends.getData() != null) {
                    mPage++;
                }
                mRefreshLayout.finishLoadmore();
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
