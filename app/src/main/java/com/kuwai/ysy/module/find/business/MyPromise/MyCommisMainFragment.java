package com.kuwai.ysy.module.find.business.MyPromise;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.chat.bean.NoticeBean;
import com.kuwai.ysy.module.find.adapter.MyComissAdapter;
import com.kuwai.ysy.module.find.api.AppointApiFactory;
import com.kuwai.ysy.module.find.bean.BlindBean;
import com.kuwai.ysy.module.find.bean.appointment.MyCommis;
import com.kuwai.ysy.module.find.business.CommisDetail.CommisDetailFragment;
import com.kuwai.ysy.module.find.business.MyCommicDetail.CommicDetailMyFragment;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyRecycleViewDivider;
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

public class MyCommisMainFragment extends BaseFragment<MyPromisePresenter> implements View.OnClickListener, MyPromiseContract.MyCommisListView {

    private MyComissAdapter mDateAdapter;
    private RecyclerView mDongtaiList;
    private int state = -1;
    private SmartRefreshLayout mRefreshLayout;
    private int mPage = 1;

    public static MyCommisMainFragment newInstance() {
        Bundle args = new Bundle();
        MyCommisMainFragment fragment = new MyCommisMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.smart_refresh;
    }

    @Override
    protected MyPromisePresenter getPresenter() {
        return new MyPromisePresenter(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        EventBusUtil.register(this);
        mDongtaiList = mRootView.findViewById(R.id.recyclerView);
        mLayoutStatusView = mRootView.findViewById(R.id.multipleStatusView);

        mRefreshLayout = mRootView.findViewById(R.id.mRefreshLayout);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                mPresenter.getMyCommis(SPManager.get().getStringValue("uid"), mPage, state);
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getMore();
            }
        });

        mDongtaiList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mDongtaiList.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 1), R.color.line_color));
        mDateAdapter = new MyComissAdapter();
        mDongtaiList.setAdapter(mDateAdapter);

        mDateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //if (mMyCommis.getData().get(position).getStatus() == 0) {
                Bundle bundle = new Bundle();
                bundle.putInt("rid", mDateAdapter.getData().get(position).getR_id());

                if ((Integer.valueOf(SPManager.get().getStringValue("uid"))) == (mDateAdapter.getData().get(position).getUid())) {
                    ((BaseFragment) getParentFragment()).start(CommicDetailMyFragment.newInstance(bundle));
                } else {
                    ((BaseFragment) getParentFragment()).start(CommisDetailFragment.newInstance(bundle));

                }
                //} else if (mMyCommis.getData().get(position).getStatus() == 1 || mMyCommis.getData().get(position).getStatus() == 2) {
                //去聊聊
                //}
            }
        });

        mDateAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.sb_status) {
                    if (mDateAdapter.getData().get(position).getStatus() == 0) {
                        mPresenter.getCancelApply(mDateAdapter.getData().get(position).getR_id(),
                                (Integer.valueOf(SPManager.get().getStringValue("uid"))));
                    }
                }
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.getMyCommis(SPManager.get().getStringValue("uid"), mPage, state);
    }

    @Override
    public void getMyCommis(MyCommis myCommis) {
        if (myCommis.getCode() == 200 && myCommis.getData().size() > 0) {
            mDateAdapter.replaceData(myCommis.getData());
            mLayoutStatusView.showContent();
        } else {
            mDateAdapter.getData().clear();
            mDateAdapter.notifyDataSetChanged();
            mLayoutStatusView.showError();
        }
    }

    private void getMore() {
        addSubscription(AppointApiFactory.getMyCommis(SPManager.get().getStringValue("uid"), mPage + 1, state).subscribe(new Consumer<MyCommis>() {
            @Override
            public void accept(MyCommis myFriends) throws Exception {
                mRefreshLayout.finishLoadmore();
                if (myFriends.getCode() == 200) {
                    if (myFriends.getData() != null) {
                        mPage++;
                    }
                    mDateAdapter.addData(myFriends.getData());
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

    @Override
    public void sedCancelApply(SimpleResponse blindBean) {
        ToastUtils.showShort(blindBean.msg);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusUtil.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isLogin(MessageEvent event) {
        if (event.getCode() == C.MSG_FILTER_DATE) {
            mPage = 1;
            state = (int) event.getData();
            mPresenter.getMyCommis(SPManager.get().getStringValue("uid"), mPage, state);
        }
    }
}
