package com.kuwai.ysy.module.mine.business.gift;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.widget.AmountView;
import com.kuwai.ysy.widget.MyEditText;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.dialoglib.DialogInterface;
import com.rayhahah.dialoglib.MDAlertDialog;
import com.rayhahah.dialoglib.NormalAlertDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.CacheUtils;
import com.rayhahah.rbase.utils.base.ToastUtils;

public class GiftRealExchangeFragment extends BaseFragment implements View.OnClickListener {

    private NavigationLayout mNavigation;
    private AmountView mAmount;
    private MyEditText mEtName;
    private MyEditText mEtPhone;
    private MyEditText mEtAddress;
    private MyEditText mEtAddressDetail;
    private EditText mEtNote;
    private Switch mSwitchAddress;
    private TextView mTvTransfee;
    private SuperButton mBtnDuihuan;

    public static GiftRealExchangeFragment newInstance() {
        Bundle args = new Bundle();
        GiftRealExchangeFragment fragment = new GiftRealExchangeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.gift_real_exchange;
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

        mNavigation = mRootView.findViewById(R.id.navigation);
        mAmount = mRootView.findViewById(R.id.amount);
        mEtName = mRootView.findViewById(R.id.et_name);
        mEtPhone = mRootView.findViewById(R.id.et_phone);
        mEtAddress = mRootView.findViewById(R.id.et_address);
        mEtAddressDetail = mRootView.findViewById(R.id.et_address_detail);
        mEtNote = mRootView.findViewById(R.id.et_note);
        mSwitchAddress = mRootView.findViewById(R.id.switch_address);
        mTvTransfee = mRootView.findViewById(R.id.tv_transfee);
        mBtnDuihuan = mRootView.findViewById(R.id.btn_duihuan);

        mNavigation.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NormalAlertDialog dialog = initCleanDialog();
                dialog.show();
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    private NormalAlertDialog initCleanDialog() {
        return new NormalAlertDialog.Builder(getActivity())
                .setTitleText("兑换须知")
                .setContentText(getResources().getString(R.string.duihuan_note))
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
}
