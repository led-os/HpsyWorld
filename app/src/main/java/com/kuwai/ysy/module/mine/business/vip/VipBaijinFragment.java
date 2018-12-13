package com.kuwai.ysy.module.mine.business.vip;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.mine.adapter.vip.HuangjinVipFeeAdapter;
import com.kuwai.ysy.module.mine.adapter.vip.TequanAdapter;
import com.kuwai.ysy.module.mine.bean.vip.VipPayBean;
import com.kuwai.ysy.widget.layoutmanager.MyGridLayoutManager;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;
import java.util.List;

public class VipBaijinFragment extends BaseFragment implements View.OnClickListener {

    private HuangjinVipFeeAdapter mDateAdapter;
    private TequanAdapter mContentAdapter;
    private TequanAdapter mAuthAdapter;
    private TequanAdapter mActAdapter;
    private List<VipPayBean> mDataList = new ArrayList<>();
    private RecyclerView rl_fee, rlContent, rlAuth, rlAct;
    private List<CategoryBean> mContentDataList = new ArrayList<>();
    private List<CategoryBean> mAuthDataList = new ArrayList<>();
    private List<CategoryBean> mActDataList = new ArrayList<>();

    public static VipBaijinFragment newInstance() {
        Bundle args = new Bundle();
        VipBaijinFragment fragment = new VipBaijinFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_baijin;
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
        rl_fee = mRootView.findViewById(R.id.rl_fee);
        rlContent = mRootView.findViewById(R.id.rl_content);
        rlAuth = mRootView.findViewById(R.id.rl_auth);
        rlAct = mRootView.findViewById(R.id.rl_activity);

        mDataList.add(new VipPayBean(2, "连续包月", "19", "每月自动续费，可随时关闭", true));
        mDataList.add(new VipPayBean(2, "连续包季", "57", "每季自动续费，可随时关闭", false));
        mDataList.add(new VipPayBean(2, "一个月", "20", "20元/月", false));
        mDataList.add(new VipPayBean(2, "年费", "199", "199元/年", false));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        mContentDataList.clear();
        mAuthDataList.clear();
        mActDataList.clear();
        for (int i = 0; i < 6; i++) {
            mContentDataList.add(new CategoryBean());
        }
        for (int i = 0; i < 2; i++) {
            mActDataList.add(new CategoryBean());
        }
        for (int i = 0; i < 3; i++) {
            mAuthDataList.add(new CategoryBean());
        }

        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rl_fee.setLayoutManager(linearLayoutManager);
        mDateAdapter = new HuangjinVipFeeAdapter(mDataList);
        rl_fee.setAdapter(mDateAdapter);
        rlContent.setLayoutManager(new MyGridLayoutManager(getActivity(), 4));
        rlAuth.setLayoutManager(new MyGridLayoutManager(getActivity(), 4));
        rlAct.setLayoutManager(new MyGridLayoutManager(getActivity(), 4));
        mContentAdapter = new TequanAdapter(mContentDataList);
        mAuthAdapter = new TequanAdapter(mAuthDataList);
        mActAdapter = new TequanAdapter(mActDataList);
        rlContent.setAdapter(mContentAdapter);
        rlAuth.setAdapter(mAuthAdapter);
        rlAct.setAdapter(mActAdapter);

        mDateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (VipPayBean bean : mDataList) {
                    bean.setCheck(false);
                }
                mDataList.get(position).setCheck(true);
                mDateAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
