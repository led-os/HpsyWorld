package com.kuwai.ysy.module.mine.business.wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.dialoglib.DialogInterface;
import com.rayhahah.dialoglib.NormalAlertDialog;
import com.rayhahah.rbase.base.RBasePresenter;

public class RechargeFragment extends BaseFragment implements View.OnClickListener {

    private NavigationLayout navigationLayout;

    public static RechargeFragment newInstance() {
        Bundle args = new Bundle();
        RechargeFragment fragment = new RechargeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.voucher_center_fragment;
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

        navigationLayout = mRootView.findViewById(R.id.navigation);
        navigationLayout.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                .setTitleText("常见问题")
                .setContentText(getResources().getString(R.string.voucher_ques))
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
