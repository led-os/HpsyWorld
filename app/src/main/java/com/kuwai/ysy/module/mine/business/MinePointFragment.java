package com.kuwai.ysy.module.mine.business;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.IntegralDetailBean;
import com.kuwai.ysy.widget.shadow.StepBean;
import com.kuwai.ysy.widget.shadow.StepsView;
import com.rayhahah.dialoglib.DialogInterface;
import com.rayhahah.dialoglib.NormalAlertDialog;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;

public class MinePointFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout mSignRl;
    private StepsView mStepView;
    private ArrayList<StepBean> mStepBeans = new ArrayList<>();
    private int positon =1;

    public static MinePointFragment newInstance() {
        Bundle args = new Bundle();
        MinePointFragment fragment = new MinePointFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.my_integral_fragment;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_sign:
                mStepView.startSignAnimation(positon);
                positon++;
                break;
            case R.id.ll_rule:
                NormalAlertDialog dialog = initCleanDialog();
                dialog.show();
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mSignRl = mRootView.findViewById(R.id.rl_sign);
        mSignRl.setOnClickListener(this);
        mStepView = mRootView.findViewById(R.id.step_view);

        mRootView.findViewById(R.id.ll_rule).setOnClickListener(this);
        initData();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    private void initData() {
        mStepBeans.add(new StepBean(StepBean.STEP_COMPLETED, "星期一"));
        mStepBeans.add(new StepBean(StepBean.STEP_COMPLETED, "星期二"));
        mStepBeans.add(new StepBean(StepBean.STEP_UNDO, "星期三"));
        mStepBeans.add(new StepBean(StepBean.STEP_UNDO, "星期四"));
        mStepBeans.add(new StepBean(StepBean.STEP_COMPLETED, "星期五"));
        mStepBeans.add(new StepBean(StepBean.STEP_UNDO, "星期六"));
        mStepBeans.add(new StepBean(StepBean.STEP_CURRENT, "星期日"));

        mStepView.setStepNum(mStepBeans, positon);
    }

    private void getPointToday(){
        /*addSubscription(MineApiFactory.getUserIntegralDetails(uid, page).subscribe(new Consumer<IntegralDetailBean>() {
            @Override
            public void accept(IntegralDetailBean integralDetailBean) throws Exception {
                mView.setHomeData(integralDetailBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
            }
        }));*/
    }

    private NormalAlertDialog initCleanDialog() {
        return new NormalAlertDialog.Builder(getActivity())
                .setTitleText("积分怎么兑换鱼币？")
                .setContentText(getResources().getString(R.string.integ_exchange))
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
