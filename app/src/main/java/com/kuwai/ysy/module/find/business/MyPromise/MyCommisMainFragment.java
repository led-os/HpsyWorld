package com.kuwai.ysy.module.find.business.MyPromise;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.find.adapter.MyComissAdapter;
import com.kuwai.ysy.module.find.bean.BlindBean;
import com.kuwai.ysy.module.find.bean.appointment.MyCommis;
import com.kuwai.ysy.module.find.business.CommisDetail.CommisDetailFragment;
import com.kuwai.ysy.module.find.business.MyCommicDetail.CommicDetailMyFragment;
import com.kuwai.ysy.utils.SharedPreferencesUtils;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyRecycleViewDivider;
import com.rayhahah.rbase.utils.base.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class MyCommisMainFragment extends BaseFragment<MyPromisePresenter> implements View.OnClickListener, MyPromiseContract.MyCommisListView {

    private MyComissAdapter mDateAdapter;
    private RecyclerView mDongtaiList;
    private List<MyCommis.DataBean> mDataList = new ArrayList<>();
    private MyCommis mMyCommis;

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
    protected MyPromisePresenter getPresenter() {
        return new MyPromisePresenter(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mDongtaiList = mRootView.findViewById(R.id.recyclerView);
        mDongtaiList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mDongtaiList.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 1), R.color.line_color));
        mDateAdapter = new MyComissAdapter(mDataList);
        mDongtaiList.setAdapter(mDateAdapter);

        mDateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mMyCommis.getData().get(position).getStatus() == 0) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("rid", mMyCommis.getData().get(position).getR_id());

                    if (Integer.valueOf((String) SharedPreferencesUtils.getParam(mContext, "uid", "1")) == (mMyCommis.getData().get(position).getUid())) {
                        ((BaseFragment) getParentFragment()).start(CommicDetailMyFragment.newInstance(bundle));
                    } else {
                        ((BaseFragment) getParentFragment()).start(CommisDetailFragment.newInstance(bundle));

                    }
                } else if (mMyCommis.getData().get(position).getStatus() == 1 || mMyCommis.getData().get(position).getStatus() == 2) {
                    //去聊聊
                }
            }
        });

        mDateAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId()==R.id.sb_status){
                    if (mMyCommis.getData().get(position).getStatus() == 0) {
                        mPresenter.getCancelApply(mMyCommis.getData().get(position).getR_id(),
                                Integer.valueOf((String) SharedPreferencesUtils.getParam(mContext, "uid", "1")));
                    }
                }
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.getMyCommis("1", 1);
    }

    @Override
    public void getMyCommis(MyCommis myCommis) {
        mMyCommis = myCommis;
        mDataList.addAll(myCommis.getData());
        mDateAdapter.notifyDataSetChanged();
    }

    @Override
    public void sedCancelApply(BlindBean blindBean) {
        ToastUtils.showShort(blindBean.getMsg());
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
