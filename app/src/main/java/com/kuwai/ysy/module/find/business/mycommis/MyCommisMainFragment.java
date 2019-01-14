package com.kuwai.ysy.module.find.business.mycommis;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.find.adapter.MyComissAdapter;
import com.kuwai.ysy.module.find.bean.appointment.MyCommis;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyRecycleViewDivider;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;
import java.util.List;

public class MyCommisMainFragment extends BaseFragment<MyCommisListPresenter> implements View.OnClickListener, MyCommisListContract.MyCommisListView {

    private MyComissAdapter mDateAdapter;
    private RecyclerView mDongtaiList;

    public static MyCommisMainFragment newInstance() {
        Bundle args = new Bundle();
        MyCommisMainFragment fragment = new MyCommisMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.recyclerview;
    }

    @Override
    protected MyCommisListPresenter getPresenter() {
        return new MyCommisListPresenter(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDongtaiList = mRootView.findViewById(R.id.recyclerView);
        mDongtaiList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mDongtaiList.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 1), R.color.line_color));
       // mDateAdapter = new MyComissAdapter(mDataList);
       // mDongtaiList.setAdapter(mDateAdapter);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
       // mPresenter.getMyCommis("1", 1);
    }

    @Override
    public void getMyCommis(MyCommis myCommis) {
        //mDataList.addAll(myCommis.getData());
        //mDateAdapter.notifyDataSetChanged();
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
