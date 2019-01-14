package com.kuwai.ysy.module.find.business.Myinvited;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.chat.bean.NoticeBean;
import com.kuwai.ysy.module.find.adapter.MyDateAdapter;
import com.kuwai.ysy.module.find.api.AppointApiFactory;
import com.kuwai.ysy.module.find.bean.appointment.MyAppointMent;
import com.kuwai.ysy.module.find.business.CommisDetail.CommisDetailFragment;
import com.kuwai.ysy.module.find.business.MyCommicDetail.CommicDetailMyFragment;
import com.kuwai.ysy.utils.EventBusUtil;
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

public class MydateMainFragment extends BaseFragment<MyInvitedPresenter> implements View.OnClickListener, MyInvitedContract.MyAppointListView {

    private MyDateAdapter mDateAdapter;
    private RecyclerView mDongtaiList;
    private List<MyAppointMent.DataBean> mDataList = new ArrayList<>();
    private int state = -1;
    private SmartRefreshLayout mRefreshLayout;
    private int mPage = 1;

    public static MydateMainFragment newInstance() {
        Bundle args = new Bundle();
        MydateMainFragment fragment = new MydateMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.smart_refresh;
    }

    @Override
    protected MyInvitedPresenter getPresenter() {
        return new MyInvitedPresenter(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        EventBusUtil.register(this);
        mDongtaiList = mRootView.findViewById(R.id.recyclerView);

        mRefreshLayout = mRootView.findViewById(R.id.mRefreshLayout);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                mPresenter.getMyAppointMent(SPManager.get().getStringValue("uid"), mPage, state);
            }
        });
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getMore();
            }
        });

        mDongtaiList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mDateAdapter = new MyDateAdapter(mDataList);
        mDongtaiList.setAdapter(mDateAdapter);

        mDateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("rid", mDateAdapter.getData().get(position).getR_id());

                ((BaseFragment) getParentFragment()).start(CommicDetailMyFragment.newInstance(bundle));

            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.getMyAppointMent(SPManager.get().getStringValue("uid"), mPage, state);
    }

    @Override
    public void getMyAppintMent(MyAppointMent myAppointMent) {
        if (myAppointMent.getCode() == 200) {
            mDateAdapter.replaceData(myAppointMent.getData());
        } else {
            mDateAdapter.getData().clear();
            mDateAdapter.notifyDataSetChanged();
        }

    }

    private void getMore() {
        addSubscription(AppointApiFactory.getMyAppoint(SPManager.get().getStringValue("uid"), mPage + 1,state).subscribe(new Consumer<MyAppointMent>() {
            @Override
            public void accept(MyAppointMent myFriends) throws Exception {
                mRefreshLayout.finishLoadmore();
                if(myFriends.getCode() == 200){
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
    public void showViewLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusUtil.unregister(this);
    }

    @Override
    public void showViewError(Throwable t) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isLogin(MessageEvent event) {
        if (event.getCode() == C.MSG_FILTER_DATE) {
            state = (int) event.getData();
            mPage = 1;
            mPresenter.getMyAppointMent(SPManager.get().getStringValue("uid"), mPage, state);
        }
    }
}
