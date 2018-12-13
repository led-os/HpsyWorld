package com.kuwai.ysy.module.mine.business;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.home.adapter.HomeAdapter;
import com.kuwai.ysy.module.home.bean.HomeBean;
import com.kuwai.ysy.module.home.business.HomeContract;
import com.kuwai.ysy.module.home.business.HomePresenter;
import com.kuwai.ysy.utils.glide.GlideImageLoader;
import com.kuwai.ysy.utils.glide.SimpleGlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CircleFragment extends BaseFragment<HomePresenter> implements HomeContract.IHomeView {

    private HomeAdapter mHomeAdapter = null;
    private RecyclerView mRecycleView = null;
    private Banner mBanner = null;
    private List<String> mIcon = new ArrayList<>();
    private String[] imgList = new String[]{"http://pic.chinahpsy.com/home/750/gl.jpg","http://pic.chinahpsy.com/home/750/cq.jpg","http://pic.chinahpsy.com/home/750/yddm.jpg"};

    public static CircleFragment newInstance() {

        Bundle args = new Bundle();
        CircleFragment fragment = new CircleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mRecycleView = mRootView.findViewById(R.id.mRecyclerView);
        mBanner = mRootView.findViewById(R.id.B_banner);
        mPresenter.requestHomeData(1);
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.setImages(Arrays.asList(imgList));
        mBanner.start();
    }

    @Override
    protected HomePresenter getPresenter() {
        return new HomePresenter(this);
    }

    @Override
    public void setHomeData(HomeBean homeBean) {
        mHomeAdapter = new HomeAdapter(homeBean.getIssueList().get(0).getItemList());
        mRecycleView.setAdapter(mHomeAdapter);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
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
