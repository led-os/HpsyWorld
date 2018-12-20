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
import com.kuwai.ysy.module.mine.bean.vip.VipBean;
import com.kuwai.ysy.module.mine.bean.vip.VipPayBean;
import com.kuwai.ysy.widget.layoutmanager.MyGridLayoutManager;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;
import java.util.List;

public class VipZuanshiFragment extends BaseFragment implements View.OnClickListener {

    private HuangjinVipFeeAdapter mDateAdapter;
    private TequanAdapter mContentAdapter;
    private TequanAdapter mAuthAdapter;
    private TequanAdapter mActAdapter;
    private List<VipPayBean> mDataList = new ArrayList<>();
    private RecyclerView rl_fee, rlContent, rlAuth, rlAct;
    private List<VipBean.DataBean.PrivilegeBean.ArrBean> mContentDataList = new ArrayList<>();
    private List<VipBean.DataBean.PrivilegeBean.ArrBean> mAuthDataList = new ArrayList<>();
    private List<VipBean.DataBean.PrivilegeBean.ArrBean> mActDataList = new ArrayList<>();

    private VipBean.DataBean mVipdata = null;

    public static VipZuanshiFragment newInstance(Bundle args) {
        VipZuanshiFragment fragment = new VipZuanshiFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_zuanshi;
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

        mVipdata = (VipBean.DataBean) getArguments().getSerializable("data");
        mDataList.add(new VipPayBean(3,"月度VIP", String.valueOf(mVipdata.getMonthly_card()), mVipdata.getMonthly_card() + "元/每月", true));
        mDataList.add(new VipPayBean(3,"季度VIP", String.valueOf(mVipdata.getSeason_card()), mVipdata.getSeason_card() + "元/每季", false));
        mDataList.add(new VipPayBean(3,"年度VIP", String.valueOf(mVipdata.getYear_card()), mVipdata.getYear_card() + "元/每年", false));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        mContentDataList.clear();
        mAuthDataList.clear();
        mActDataList.clear();
        for (int i = 0; i < mVipdata.getPrivilege().get(0).getArr().size(); i++) {
            mContentDataList.add(mVipdata.getPrivilege().get(0).getArr().get(i));
        }
        for (int i = 0; i < mVipdata.getPrivilege().get(1).getArr().size(); i++) {
            mActDataList.add(mVipdata.getPrivilege().get(1).getArr().get(i));
        }
        for (int i = 0; i < mVipdata.getPrivilege().get(2).getArr().size(); i++) {
            mAuthDataList.add(mVipdata.getPrivilege().get(2).getArr().get(i));
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
