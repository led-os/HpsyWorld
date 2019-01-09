package com.kuwai.ysy.module.mine.business.vip;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.mine.adapter.vip.HuangjinVipFeeAdapter;
import com.kuwai.ysy.module.mine.adapter.vip.TequanAdapter;
import com.kuwai.ysy.module.mine.bean.vip.VipBean;
import com.kuwai.ysy.module.mine.bean.vip.VipPayBean;
import com.kuwai.ysy.widget.CustomFontTextview;
import com.kuwai.ysy.widget.layoutmanager.MyGridLayoutManager;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VipBaijinFragment extends BaseFragment<VipHuangjinPresenter> implements VipHuangjinContract.IMineView,View.OnClickListener {

    private CustomFontTextview tv_money;
    private int selectPos = 0;

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
    private SuperButton mSubmitBtn;

    public static VipBaijinFragment newInstance(Bundle args) {
        VipBaijinFragment fragment = new VipBaijinFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_baijin;
    }

    @Override
    protected VipHuangjinPresenter getPresenter() {
        return new VipHuangjinPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("uid", SPManager.get().getStringValue("uid"));
                param.put("v_id", mDataList.get(selectPos).getVipType());
                param.put("type", mDataList.get(selectPos).getDay());
                param.put("source", "Android");
                mPresenter.getAliOrderInfo(param);
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        rl_fee = mRootView.findViewById(R.id.rl_fee);
        rlContent = mRootView.findViewById(R.id.rl_content);
        rlAuth = mRootView.findViewById(R.id.rl_auth);
        tv_money = mRootView.findViewById(R.id.tv_money);
        rlAct = mRootView.findViewById(R.id.rl_activity);
        mSubmitBtn = mRootView.findViewById(R.id.btn_submit);
        mSubmitBtn.setOnClickListener(this);

        mVipdata = (VipBean.DataBean) getArguments().getSerializable("data");
        tv_money.setText(mVipdata.getMonthly_card() + "");
        mDataList.add(new VipPayBean(mVipdata.getV_id(),"月度VIP", String.valueOf(mVipdata.getMonthly_card()), mVipdata.getMonthly_card() + "元/每月", true,"30"));
        mDataList.add(new VipPayBean(mVipdata.getV_id(),"季度VIP", String.valueOf(mVipdata.getSeason_card()), mVipdata.getSeason_card() + "元/每季", false,"90"));
        mDataList.add(new VipPayBean(mVipdata.getV_id(),"年度VIP", String.valueOf(mVipdata.getYear_card()), mVipdata.getYear_card() + "元/每年", false,"365"));
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
                selectPos = position;
                tv_money.setText(mDataList.get(position).getPrice());
                mDataList.get(position).setCheck(true);
                mDateAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    @Override
    public void setAliOrderInfo(SimpleResponse infoBean) {
        if (infoBean.code == 200) {
            //ToastUtils.showShort("");
        }
        ToastUtils.showShort(infoBean.msg);
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
