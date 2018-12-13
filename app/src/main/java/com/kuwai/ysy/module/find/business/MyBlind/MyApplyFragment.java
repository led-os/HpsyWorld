package com.kuwai.ysy.module.find.business.MyBlind;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.find.adapter.MyApplyAdapter;
import com.kuwai.ysy.module.find.bean.MyBlindBean;
import com.kuwai.ysy.utils.SharedPreferencesUtils;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;
import java.util.List;

public class MyApplyFragment extends BaseFragment<MyblindPresenter> implements MyblindContract.IHomeView, View.OnClickListener {

    private MyApplyAdapter mDateAdapter;
    private RecyclerView mDongtaiList;
    private List<CategoryBean> mDataList = new ArrayList<>();
    private int page = 1;

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
    protected MyblindPresenter getPresenter() {
        return new MyblindPresenter(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDongtaiList = mRootView.findViewById(R.id.recyclerView);
//        mDataList.add(new CategoryBean());
//        mDataList.add(new CategoryBean());
//        mDataList.add(new CategoryBean());
        mDongtaiList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //mDongtaiList.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 1), R.color.line_color));
        mDateAdapter = new MyApplyAdapter();
        mDongtaiList.setAdapter(mDateAdapter);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestHomeData(page, Integer.valueOf((String) SharedPreferencesUtils.getParam(mContext, "uid", "1")));
    }

    @Override
    public void setHomeData(MyBlindBean myBlindBean) {
        mDateAdapter.addData(myBlindBean.getData());
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