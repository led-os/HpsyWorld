package com.kuwai.ysy.module.mine.business.vip;

import android.content.Intent;
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
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.home.WebviewH5Activity;
import com.kuwai.ysy.module.mine.adapter.vip.HuangjinVipFeeAdapter;
import com.kuwai.ysy.module.mine.adapter.vip.TequanAdapter;
import com.kuwai.ysy.module.mine.adapter.vip.VipRightAdapter;
import com.kuwai.ysy.module.mine.bean.vip.VipBean;
import com.kuwai.ysy.module.mine.bean.vip.VipPayBean;
import com.kuwai.ysy.widget.CustomFontTextview;
import com.kuwai.ysy.widget.layoutmanager.MyGridLayoutManager;
import com.rayhahah.dialoglib.DialogInterface;
import com.rayhahah.dialoglib.MDAlertDialog;
import com.rayhahah.dialoglib.NormalAlertDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VipSuperFragment extends BaseFragment<VipHuangjinPresenter> implements VipHuangjinContract.IMineView, View.OnClickListener {

    private RecyclerView rlRight;

    private VipBean.DataBean mVipdata = null;
    private CustomFontTextview sec_price, tv_money;
    private TextView source_price;
    private SuperButton mSubmitBtn;
    private TextView mXieyiTv, mZhengceTv;

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

                new MDAlertDialog.Builder(getActivity())
                        .setTitleVisible(false)
                        .setContentText("确认开通定制会员？")
                        .setHeight(0.16f)
                        .setOnclickListener(new com.rayhahah.dialoglib.DialogInterface.OnLeftAndRightClickListener<MDAlertDialog>() {
                            @Override
                            public void clickLeftButton(MDAlertDialog dialog, View view) {
                                dialog.dismiss();
                            }

                            @Override
                            public void clickRightButton(MDAlertDialog dialog, View view) {
                                dialog.dismiss();
                                Map<String, Object> param = new HashMap<String, Object>();
                                param.put("uid", SPManager.get().getStringValue("uid"));
                                param.put("v_id", mVipdata.getV_id());
                                param.put("type", "365");
                                param.put("source", "Android");
                                mPresenter.getAliOrderInfo(param);
                            }
                        })
                        .setCanceledOnTouchOutside(true)
                        .build().show();
                break;
            case R.id.tv_xieyi:
                Intent intent = new Intent(getActivity(), WebviewH5Activity.class);
                intent.putExtra(C.H5_FLAG, C.H5_URL + C.HUIYUANTIOAKUAN);
                startActivity(intent);
                break;
            case R.id.tv_zhengce:
                Intent intent1 = new Intent(getActivity(), WebviewH5Activity.class);
                intent1.putExtra(C.H5_FLAG, C.H5_URL + C.BAOHUZHENGCE);
                startActivity(intent1);
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mVipdata = (VipBean.DataBean) getArguments().getSerializable("data");
        mXieyiTv = mRootView.findViewById(R.id.tv_xieyi);
        rlRight = mRootView.findViewById(R.id.rl_right);
        mZhengceTv = mRootView.findViewById(R.id.tv_zhengce);
        mXieyiTv.setOnClickListener(this);
        mZhengceTv.setOnClickListener(this);
        tv_money = mRootView.findViewById(R.id.tv_money);
        sec_price = mRootView.findViewById(R.id.sec_price);
        sec_price.setText(mVipdata.getMonthly_card() + "");
        tv_money.setText(mVipdata.getMonthly_card() + "");
        source_price = mRootView.findViewById(R.id.source_price);
        source_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        mSubmitBtn = mRootView.findViewById(R.id.btn_submit);
        mSubmitBtn.setOnClickListener(this);

        rlRight.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rlRight.setAdapter(new VipRightAdapter(mVipdata.getPrivilege(),mVipdata.getV_id()));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    @Override
    public void setAliOrderInfo(SimpleResponse infoBean) {
        if (infoBean.code == 200) {
            //ToastUtils.showShort("");
            initCleanDialog().show();
        }
        ToastUtils.showShort(infoBean.msg);
    }

    private NormalAlertDialog initCleanDialog() {
        return new NormalAlertDialog.Builder(getActivity())
                .setTitleText("提示")
                .setContentText(getResources().getString(R.string.vip_tips))
                .setSingleButtonText("好的，知道了")
                .setSingleMode(true)
                .setSingleListener(new DialogInterface.OnSingleClickListener<NormalAlertDialog>() {
                    @Override
                    public void clickSingleButton(NormalAlertDialog dialog, View view) {
                        dialog.dismiss();
                    }
                })
                .setCanceledOnTouchOutside(true)
                .build();
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
