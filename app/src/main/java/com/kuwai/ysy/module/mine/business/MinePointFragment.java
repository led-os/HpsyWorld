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
}
