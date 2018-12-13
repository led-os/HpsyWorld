package com.kuwai.ysy.module.find.business.TuoDan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.find.adapter.BlindListAdapter;
import com.kuwai.ysy.module.find.bean.TuoDanBean;
import com.kuwai.ysy.module.find.business.MyBlind.MyApplyFragment;

import java.util.ArrayList;
import java.util.List;

public class BlindListFragment extends BaseFragment<TuoDanPresenter> implements TuoDanContract.IHomeView, View.OnClickListener {

    private BlindListAdapter mDateAdapter;
    private RecyclerView mDongtaiList;
    private List<CategoryBean> mDataList = new ArrayList<>();
    private TextView mRightTv;
    private int page = 1;

    public static BlindListFragment newInstance() {
        Bundle args = new Bundle();
        BlindListFragment fragment = new BlindListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_blind_list;
    }

    @Override
    protected TuoDanPresenter getPresenter() {
        return new TuoDanPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_txt:
                start(MyApplyFragment.newInstance());
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDongtaiList = mRootView.findViewById(R.id.recyclerView);
        mRightTv = mRootView.findViewById(R.id.right_txt);
        mRightTv.setOnClickListener(this);
//        mDataList.add(new CategoryBean());
//        mDataList.add(new CategoryBean());
//        mDataList.add(new CategoryBean());
        mDongtaiList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //mDongtaiList.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 1), R.color.line_color));
        mDateAdapter = new BlindListAdapter();
        mDongtaiList.setAdapter(mDateAdapter);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestHomeData(page);
    }

    @Override
    public void setHomeData(TuoDanBean tuoDanBean) {
        mDateAdapter.addData(tuoDanBean.getData());
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
