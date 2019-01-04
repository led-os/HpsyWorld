package com.kuwai.ysy.module.mine.business.visitor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.adapter.ExpandableItemAdapter;
import com.kuwai.ysy.module.mine.bean.TodayBean;
import com.kuwai.ysy.module.mine.bean.VisitorBean;
import com.kuwai.ysy.module.mine.bean.like.ChildLevel;
import com.kuwai.ysy.module.mine.bean.like.ParentLevel;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;

public class LookMeFragment extends BaseFragment<LookMePresenter> implements LookMeContract.IHomeView, View.OnClickListener {

    RecyclerView mRecyclerView;
    ExpandableItemAdapter adapter;
    ArrayList<MultiItemEntity> list = new ArrayList<>();
    private TextView mTvTotalLook;
    private TextView mTvTodayVisitor;
    private TextView mTvTodayLook;

    public static LookMeFragment newInstance() {
        Bundle args = new Bundle();
        LookMeFragment fragment = new LookMeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_look_me;
    }

    @Override
    protected LookMePresenter getPresenter() {
        return new LookMePresenter(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTvTotalLook = mRootView.findViewById(R.id.tv_total_look);
        mTvTodayVisitor = mRootView.findViewById(R.id.tv_today_visitor);
        mTvTodayLook = mRootView.findViewById(R.id.tv_today_look);
        mRecyclerView = mRootView.findViewById(R.id.recyclerView);


    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestHomeData(SPManager.get().getStringValue("uid", "1"), C.LOOK_ME);
    }

    @Override
    public void setHomeData(VisitorBean visitorBean) {
        mTvTotalLook.setText(String.valueOf(visitorBean.getData().getSum()));
        mTvTodayVisitor.setText(String.valueOf(visitorBean.getData().getToday().size()));
        mTvTodayLook.setText(String.valueOf(visitorBean.getData().getToday().size()));

        int earlyDataSize = visitorBean.getData().getEarlier().size();
        int todayDataSize = visitorBean.getData().getToday().size();

        ParentLevel today = new ParentLevel("今天", "");
        for (int i = 0; i < todayDataSize; i++) {
            today.addSubItem(visitorBean.getData().getToday().get(i));
        }
        list.add(today);

        ParentLevel lv0 = new ParentLevel("更早", "");
        for (int j = 0; j < earlyDataSize; j++) {
            lv0.addSubItem(visitorBean.getData().getEarlier().get(j));
        }
        list.add(lv0);

        adapter = new ExpandableItemAdapter(list);

        mRecyclerView.setAdapter(adapter);
        adapter.expandAll();
    }

    @Override
    public void showError(int errorCode, String msg) {

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
}
