package com.kuwai.ysy.module.find.business;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.find.adapter.MyApplyAdapter;
import com.kuwai.ysy.module.find.adapter.MyComissAdapter;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyRecycleViewDivider;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;
import java.util.List;

public class MyApplyFragment extends BaseFragment implements View.OnClickListener {

    private MyApplyAdapter mDateAdapter;
    private RecyclerView mDongtaiList;
    private List<CategoryBean> mDataList = new ArrayList<>();

    public static MyApplyFragment newInstance() {
        Bundle args = new Bundle();
        MyApplyFragment fragment = new MyApplyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_my_apply;
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
        mDongtaiList = mRootView.findViewById(R.id.recyclerView);
        ((NavigationLayout) mRootView.findViewById(R.id.navigation)).setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        mDataList.add(new CategoryBean());
        mDataList.add(new CategoryBean());
        mDataList.add(new CategoryBean());
        mDongtaiList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //mDongtaiList.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 1), R.color.line_color));
        mDateAdapter = new MyApplyAdapter();
        mDongtaiList.setAdapter(mDateAdapter);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
