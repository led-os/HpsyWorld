package com.kuwai.ysy.module.find.business.MyBlind;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.find.adapter.MyApplyAdapter;
import com.kuwai.ysy.module.find.bean.MyBlindBean;
import com.kuwai.ysy.module.home.WebviewH5Activity;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.SPManager;

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
        ((NavigationLayout) mRootView.findViewById(R.id.navigation)).setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
//        mDataList.add(new CategoryBean());
//        mDataList.add(new CategoryBean());
//        mDataList.add(new CategoryBean());
        mDongtaiList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //mDongtaiList.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 1), R.color.line_color));
        mDateAdapter = new MyApplyAdapter();
        mDongtaiList.setAdapter(mDateAdapter);

        mDateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), WebviewH5Activity.class);
                intent.putExtra("type", "huodong");
                intent.putExtra("id", mDateAdapter.getData().get(position).getAid());
                intent.putExtra(C.H5_FLAG, C.H5_URL + C.HUODONGXIANGQING + "uid=" + SPManager.get().getStringValue("uid") + "&aid=" + mDateAdapter.getData().get(position).getAid());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestHomeData(page, (Integer.valueOf(SPManager.get().getStringValue("uid"))));
    }

    @Override
    public void setHomeData(MyBlindBean myBlindBean) {
        mDateAdapter.replaceData(myBlindBean.getData());
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
