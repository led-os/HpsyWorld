package com.kuwai.ysy.module.mine.business.vip;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.mine.adapter.vip.HuangjinVipFeeAdapter;
import com.kuwai.ysy.module.mine.adapter.vip.TequanAdapter;
import com.kuwai.ysy.module.mine.bean.vip.VipBean;
import com.kuwai.ysy.module.mine.bean.vip.VipPayBean;
import com.kuwai.ysy.widget.CustomFontTextview;
import com.kuwai.ysy.widget.layoutmanager.MyGridLayoutManager;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;
import java.util.List;

public class VipSuperFragment extends BaseFragment implements View.OnClickListener {

    private HuangjinVipFeeAdapter mDateAdapter;
    private TequanAdapter mContentAdapter;
    private TequanAdapter mAuthAdapter;
    private TequanAdapter mActAdapter;
    private RecyclerView rl_fee, rlContent, rlAuth, rlAct;
    private List<VipBean.DataBean.PrivilegeBean.ArrBean> mContentDataList = new ArrayList<>();
    private List<VipBean.DataBean.PrivilegeBean.ArrBean> mAuthDataList = new ArrayList<>();
    private List<VipBean.DataBean.PrivilegeBean.ArrBean> mActDataList = new ArrayList<>();

    private VipBean.DataBean mVipdata = null;
    private CustomFontTextview sec_price;
    private TextView source_price;

    public static VipSuperFragment newInstance(Bundle args) {
        VipSuperFragment fragment = new VipSuperFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_super;
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
        mVipdata = (VipBean.DataBean) getArguments().getSerializable("data");
        rl_fee = mRootView.findViewById(R.id.rl_fee);
        sec_price = mRootView.findViewById(R.id.sec_price);
        sec_price.setText(mVipdata.getMonthly_card() + "");
        rlContent = mRootView.findViewById(R.id.rl_content);
        rlAuth = mRootView.findViewById(R.id.rl_auth);
        rlAct = mRootView.findViewById(R.id.rl_activity);
        source_price = mRootView.findViewById(R.id.source_price);
        source_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

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

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
