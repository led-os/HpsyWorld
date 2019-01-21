package com.kuwai.ysy.module.home.business;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.adapter.CataAdapter;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.circle.business.CircleContract;
import com.kuwai.ysy.module.circle.business.CirclePresent;
import com.kuwai.ysy.module.home.business.sading.AskFragment;
import com.kuwai.ysy.module.home.business.sading.Home2Fragment;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.utils.language.LocalManageUtil;
import com.rayhahah.rbase.utils.base.StatusBarUtil;

import java.util.ArrayList;

public class MyFragment extends BaseFragment<CirclePresent> implements View.OnClickListener, CircleContract.ICircle {

    private TextView mChineseTv = null;
    private TextView mEnglishTv = null;
    private RecyclerView mRecycleView = null;
    private CataAdapter mHomeAdapter = null;

    public static MyFragment newInstance() {
        return new MyFragment();
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mChineseTv = mRootView.findViewById(R.id.chinese);
        mRecycleView = mRootView.findViewById(R.id.mRecyclerView);
        mLayoutStatusView = mRootView.findViewById(R.id.multipleStatusView);
        mChineseTv.setOnClickListener(this);
        mEnglishTv = mRootView.findViewById(R.id.english);
        mEnglishTv.setOnClickListener(this);

    }

    @Override
    protected CirclePresent getPresenter() {
        return new CirclePresent(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.english:
                selectLanguage(3);
                break;
            case R.id.chinese:
                //start(MyFragment.newInstance());
                selectLanguage(1);
                break;
        }
    }

    private void selectLanguage(int select) {
        LocalManageUtil.saveSelectLanguage(getActivity(), select);
        HomeActivity.reStart(getActivity());
    }

    @Override
    public void setCircleData(ArrayList<CategoryBean> homeBean) {
        mHomeAdapter = new CataAdapter(homeBean);
        mRecycleView.setAdapter(mHomeAdapter);
        mRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        mRecycleView.addItemDecoration(new RecyclerView.ItemDecoration() {
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = parent.getChildPosition(view);
                int offset = Utils.dip2px(getActivity(), 2f);

                outRect.set((position % 2 == 0) ? 0 : offset, offset,
                        (position % 2 == 0) ? offset : 0, offset);
            }
        });
        //mRecycleView.addOnScrollListener(new HomeActivity.ListScrollListener());

        mHomeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //start(HomeFragment.newInstance());
                //loadRootFragment(R.id.fl_tab_container,HomeFragment.newInstance());
                /*getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fl_tab_container, HomeFragment.newInstance(), null)
                        .addToBackStack(null)
                        .commit();*/

                if (getParentFragment() instanceof Home2Fragment) {
                    ((Home2Fragment) getParentFragment()).start(AskFragment.newInstance());
                }
            }
        });
    }

    @Override
    public void showError(int errorCode, String msg) {
        mLayoutStatusView.showError();
    }

    @Override
    public void showViewLoading() {
        mLayoutStatusView.showLoading();
    }

    @Override
    public void dismissLoading() {
        mLayoutStatusView.showContent();
    }

    @Override
    public void showViewError(Throwable t) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestCircleData();
    }
}
