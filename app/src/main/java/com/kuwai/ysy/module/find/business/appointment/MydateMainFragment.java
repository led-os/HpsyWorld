package com.kuwai.ysy.module.find.business.appointment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.find.adapter.MyDateAdapter;
import com.kuwai.ysy.module.find.bean.appointment.MyAppointMent;

import java.util.ArrayList;
import java.util.List;

public class MydateMainFragment extends BaseFragment<MyAppointListPresenter> implements View.OnClickListener, AppointMyListContract.MyAppointListView {

    private MyDateAdapter mDateAdapter;
    private RecyclerView mDongtaiList;
    private List<MyAppointMent.DataBean> mDataList = new ArrayList<>();

    public static MydateMainFragment newInstance() {
        Bundle args = new Bundle();
        MydateMainFragment fragment = new MydateMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.recyclerview;
    }

    @Override
    protected MyAppointListPresenter getPresenter() {
        return new MyAppointListPresenter(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDongtaiList = mRootView.findViewById(R.id.recyclerView);
        mDongtaiList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mDateAdapter = new MyDateAdapter(mDataList);
        mDongtaiList.setAdapter(mDateAdapter);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.getMyAppointMent("1", 1, -1);
    }

    @Override
    public void getMyAppintMent(MyAppointMent myAppointMent) {
        mDataList.addAll(myAppointMent.getData());
        mDateAdapter.notifyDataSetChanged();
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
