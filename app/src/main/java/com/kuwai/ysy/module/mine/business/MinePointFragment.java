package com.kuwai.ysy.module.mine.business;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.widget.shadow.StepBean;
import com.kuwai.ysy.widget.shadow.StepsView;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;

public class MinePointFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout mSignRl;
    private StepsView mStepView;
    private ArrayList<StepBean> mStepBeans = new ArrayList<>();
    private int positon = 2;

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
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mSignRl = mRootView.findViewById(R.id.rl_sign);
        mSignRl.setOnClickListener(this);
        mStepView = mRootView.findViewById(R.id.step_view);
        initData();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    private void initData() {
        mStepBeans.add(new StepBean(StepBean.STEP_COMPLETED, "今天"));
        mStepBeans.add(new StepBean(StepBean.STEP_COMPLETED, "9.10"));
        mStepBeans.add(new StepBean(StepBean.STEP_UNDO, "9.11"));
        mStepBeans.add(new StepBean(StepBean.STEP_UNDO, "9.12"));
        mStepBeans.add(new StepBean(StepBean.STEP_UNDO, "9.13"));
        mStepBeans.add(new StepBean(StepBean.STEP_UNDO, "9.14"));
        mStepBeans.add(new StepBean(StepBean.STEP_CURRENT, "9.15"));

        mStepView.setStepNum(mStepBeans);
    }
}
