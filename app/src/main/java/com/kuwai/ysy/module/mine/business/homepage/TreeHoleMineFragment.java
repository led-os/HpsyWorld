package com.kuwai.ysy.module.mine.business.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.HoleDetailActivity;
import com.kuwai.ysy.module.circle.adapter.TreeHoleAdapter;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.circle.bean.DyMainListBean;
import com.kuwai.ysy.module.circle.business.PublishHoleActivity;
import com.kuwai.ysy.module.home.business.HomeActivity;
import com.kuwai.ysy.module.mine.adapter.TreeHoleMineAdapter;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.PersonalTreeHole;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.utils.glide.GlideImageLoader;
import com.kuwai.ysy.widget.MyRecycleViewDivider;
import com.kuwai.ysy.widget.popwindow.YsyPopWindow;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class TreeHoleMineFragment extends BaseFragment implements View.OnClickListener {

    private TreeHoleMineAdapter mDongtaiAdapter;
    private RecyclerView mDongtaiList;

    private CustomDialog customDialog;

    private SmartRefreshLayout mRefreshLayout;
    private int mPage = 1;
    private String uid = "";
    private PersonalTreeHole mDyMainListBean;

    public static TreeHoleMineFragment newInstance() {
        Bundle args = new Bundle();
        TreeHoleMineFragment fragment = new TreeHoleMineFragment();
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
        switch (v.getId()) {
            case R.id.tv_edit:
                startActivity(new Intent(getActivity(), PublishHoleActivity.class));
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        uid = SPManager.get().getStringValue("uid");
        mDongtaiList = mRootView.findViewById(R.id.recyclerView);

        mRefreshLayout = mRootView.findViewById(R.id.mRefreshLayout);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                getDy(mPage, uid);
            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getMore(mPage + 1, uid);
            }
        });

        mDongtaiList.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 0.5f), R.color.line_color));
        mDongtaiAdapter = new TreeHoleMineAdapter();
        mDongtaiAdapter.setNoMore();
        mDongtaiList.setAdapter(mDongtaiAdapter);

        mDongtaiAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), HoleDetailActivity.class);
                intent.putExtra("tid", String.valueOf(mDyMainListBean.getData().get(position).getT_id()));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getDy(mPage, uid);
    }

    private void showPopListView() {
        View pannel = View.inflate(getActivity(), R.layout.dialog_tree_item_more, null);
        if (customDialog == null) {
            customDialog = new CustomDialog.Builder(getActivity())
                    .setView(pannel)
                    .setTouchOutside(true)
                    .setDialogGravity(Gravity.CENTER)
                    .build();
        }
        customDialog.show();
    }

    private void getDy(int page, String uid) {
        addSubscription(MineApiFactory.getPersonalTreeHole(uid, page).subscribe(new Consumer<PersonalTreeHole>() {
            @Override
            public void accept(PersonalTreeHole dyMainListBean) throws Exception {
                mRefreshLayout.finishRefresh();
                mDyMainListBean = dyMainListBean;
                mDongtaiAdapter.replaceData(dyMainListBean.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

    private void getMore(int page, String uid) {
        addSubscription(MineApiFactory.getPersonalTreeHole(uid, page).subscribe(new Consumer<PersonalTreeHole>() {
            @Override
            public void accept(PersonalTreeHole dyMainListBean) throws Exception {
                if (dyMainListBean.getData() != null) {
                    mPage++;
                }
                mRefreshLayout.finishLoadmore();
                mDyMainListBean.getData().addAll(dyMainListBean.getData());
                mDongtaiAdapter.addData(dyMainListBean.getData());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
            }
        }));
    }
}
