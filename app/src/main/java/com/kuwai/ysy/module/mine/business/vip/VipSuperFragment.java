package com.kuwai.ysy.module.mine.business.vip;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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

public class VipSuperFragment extends BaseFragment<VipHuangjinPresenter> implements VipHuangjinContract.IMineView, View.OnClickListener {

    private HuangjinVipFeeAdapter mDateAdapter;
    private TequanAdapter mContentAdapter;
    private TequanAdapter mAuthAdapter;
    private TequanAdapter mActAdapter;
    private RecyclerView rl_fee, rlContent, rlAuth, rlAct;
    private List<VipBean.DataBean.PrivilegeBean.ArrBean> mContentDataList = new ArrayList<>();
    private List<VipBean.DataBean.PrivilegeBean.ArrBean> mAuthDataList = new ArrayList<>();
    private List<VipBean.DataBean.PrivilegeBean.ArrBean> mActDataList = new ArrayList<>();

    private VipBean.DataBean mVipdata = null;
    private CustomFontTextview sec_price, tv_money;
    private TextView source_price;
    private SuperButton mSubmitBtn;

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
    protected VipHuangjinPresenter getPresenter() {
        return new VipHuangjinPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("uid", SPManager.get().getStringValue("uid"));
                param.put("v_id", mVipdata.getV_id());
                param.put("type", "365");
                param.put("source", "Android");
                mPresenter.getAliOrderInfo(param);
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mVipdata = (VipBean.DataBean) getArguments().getSerializable("data");
        rl_fee = mRootView.findViewById(R.id.rl_fee);
        tv_money = mRootView.findViewById(R.id.tv_money);
        sec_price = mRootView.findViewById(R.id.sec_price);
        sec_price.setText(mVipdata.getMonthly_card() + "");
        tv_money.setText(mVipdata.getMonthly_card() + "");
        rlContent = mRootView.findViewById(R.id.rl_content);
        rlAuth = mRootView.findViewById(R.id.rl_auth);
        rlAct = mRootView.findViewById(R.id.rl_activity);
        source_price = mRootView.findViewById(R.id.source_price);
        source_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        mSubmitBtn = mRootView.findViewById(R.id.btn_submit);
        mSubmitBtn.setOnClickListener(this);

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
