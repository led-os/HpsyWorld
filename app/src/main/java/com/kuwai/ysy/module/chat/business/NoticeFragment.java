package com.kuwai.ysy.module.chat.business;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.adapter.FindFriendsAdapter;
import com.kuwai.ysy.module.chat.adapter.NoticeDateAdapter;
import com.kuwai.ysy.module.chat.adapter.NoticeTransAdapter;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.chat.bean.MyFriends;
import com.kuwai.ysy.module.chat.bean.NoticeBean;
import com.kuwai.ysy.module.chat.bean.NoticeDateBean;
import com.kuwai.ysy.module.chat.business.redpack.SendRedActivity;
import com.kuwai.ysy.module.find.CommisDetailOtherActivity;
import com.kuwai.ysy.module.find.business.CommisDetailMyActivity;
import com.kuwai.ysy.module.home.WebviewH5Activity;
import com.kuwai.ysy.module.home.bean.HomeVideoBean;
import com.kuwai.ysy.utils.EventBusUtil;
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

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.rong.imkit.RongIM;

public class NoticeFragment extends BaseFragment implements View.OnClickListener {

    private ImageView mDownDate;
    private RecyclerView mRlDate;
    private ImageView mDownSystem;
    private SmartRefreshLayout mMRefreshLayout;
    private RecyclerView mRlSystem;

    private NoticeTransAdapter myFriendsAdapter;
    private NoticeDateAdapter dateAdapter;
    private List<NoticeBean.DataBean> mDatalist = new ArrayList<>();

    private SmartRefreshLayout mRefreshLayout;
    private int mPage = 1;

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
        EventBusUtil.register(this);
        mDownDate = mRootView.findViewById(R.id.down_date);
        mLayoutStatusView = mRootView.findViewById(R.id.multipleStatusView);
        mRlDate = mRootView.findViewById(R.id.rl_date);
        mDownSystem = mRootView.findViewById(R.id.down_system);
        mMRefreshLayout = mRootView.findViewById(R.id.mRefreshLayout);
        mRlSystem = mRootView.findViewById(R.id.rl_system);
        myFriendsAdapter = new NoticeTransAdapter();
        dateAdapter = new NoticeDateAdapter();
        mRlDate.setAdapter(dateAdapter);
        mRlSystem.setAdapter(myFriendsAdapter);
        mRlSystem.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRlSystem.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 1), R.color.line_color));
        mRlDate.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRlDate.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 1), R.color.line_color));

        dateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //跳转约会详情
                if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("rid", dateAdapter.getData().get(position).getR_id());
                    bundle.putString("uid", String.valueOf(dateAdapter.getData().get(position).getUid()));

                    if (Integer.valueOf(SPManager.get().getStringValue("uid")) == (dateAdapter.getData().get(position).getUid())) {
                        Intent intent = new Intent(getActivity(), CommisDetailMyActivity.class);
                        intent.putExtra("data", bundle);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(), CommisDetailOtherActivity.class);
                        intent.putExtra("data", bundle);
                        startActivity(intent);
                    }
                } else {
                    ToastUtils.showShort(R.string.login_error);
                }
                //RongIM.getInstance().startPrivateChat(getActivity(), String.valueOf(dateAdapter.getData().get(position).getUid()), dateAdapter.getData().get(position).getNickname());
            }
        });

        myFriendsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), WebviewH5Activity.class);
                intent.putExtra(C.H5_FLAG, myFriendsAdapter.getData().get(position).getUrl() + "?uid=" + SPManager.get().getStringValue("uid") + "&nid=" + myFriendsAdapter.getData().get(position).getNid());
                startActivity(intent);
            }
        });

        mRefreshLayout = mRootView.findViewById(R.id.mRefreshLayout);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                getFriends();
                getNotice();
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
        if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
            getFriends();
            getNotice();
        } else {
        }
    }

    void getFriends() {
        addSubscription(ChatApiFactory.getSystemNotice(SPManager.get().getStringValue("uid"), mPage).subscribe(new Consumer<NoticeBean>() {
            @Override
            public void accept(NoticeBean noticeBean) throws Exception {
                mRefreshLayout.finishRefresh();
                if (noticeBean.getCode() == 200) {
                    myFriendsAdapter.replaceData(noticeBean.getData());
                } else {
                    //ToastUtils.showShort(noticeBean.getMsg());
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //ToastUtils.showShort("网络错误");
            }
        }));
    }

    void getNotice() {
        addSubscription(ChatApiFactory.getDateNotice(SPManager.get().getStringValue("uid")).subscribe(new Consumer<NoticeDateBean>() {
            @Override
            public void accept(NoticeDateBean noticeBean) throws Exception {
                if (noticeBean.getCode() == 200) {
                    dateAdapter.replaceData(noticeBean.getData());
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
        addSubscription(ChatApiFactory.getSystemNotice(SPManager.get().getStringValue("uid"), mPage + 1).subscribe(new Consumer<NoticeBean>() {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusUtil.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isLogin(MessageEvent event) {
        if (C.MSG_UPDATE_NOTICE == event.getCode()) {
            getFriends();
            getNotice();
        }
    }
}
