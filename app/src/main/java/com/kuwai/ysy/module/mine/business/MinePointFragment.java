package com.kuwai.ysy.module.mine.business;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.adapter.CheckAdapter;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.module.mine.bean.CheckInBean;
import com.kuwai.ysy.module.mine.bean.IntegralDetailBean;
import com.kuwai.ysy.module.mine.business.Integral.IntegralFragment;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.AmountView;
import com.kuwai.ysy.widget.MyRecycleViewDivider;
import com.kuwai.ysy.widget.NavigationLayout;
import com.kuwai.ysy.widget.shadow.StepBean;
import com.kuwai.ysy.widget.shadow.StepsView;
import com.rayhahah.dialoglib.DialogInterface;
import com.rayhahah.dialoglib.NormalAlertDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.Date;

import io.reactivex.functions.Consumer;

public class MinePointFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout mSignRl;
    private StepsView mStepView;
    private ArrayList<StepBean> mStepBeans = new ArrayList<>();
    private int positon = 1;
    private TextView mSignTv, mLianTv;
    private TextView tv_more;
    private ImageView imgDown;
    private AmountView amountView;
    private TextView tv_sum;
    private SuperButton mSubmitBtn;

    private LinearLayout mJifenLay, mGuizeLay;
    private CheckInBean mCheckInbean = null;
    private CheckAdapter checkAdapter;
    private RecyclerView mCheckRl;
    private boolean isShowMore = false;

    private TextView tv_integral;
    private boolean isCheck = false;

    int daySize = 0;

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
                checkIn();
                break;
            case R.id.lay_jifen:
                start(IntegralFragment.newInstance());
                break;
            case R.id.lay_guize:
                initCleanDialog().show();
                break;
            case R.id.tv_more:
                if (!isShowMore) {
                    checkAdapter.replaceData(mCheckInbean.getData().getDaily_tasks());
                    isShowMore = true;
                    tv_more.setText("收起");
                    imgDown.setImageResource(R.drawable.center_mark_ic_list_up);
                } else {
                    checkAdapter.replaceData(mCheckInbean.getData().getDaily_tasks().subList(0, daySize > 3 ? 3 : daySize));
                    isShowMore = false;
                    tv_more.setText("更多");
                    imgDown.setImageResource(R.drawable.center_mark_ic_list_down);
                }
                break;
            case R.id.btn_submit:
                exchange();
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        ((NavigationLayout) mRootView.findViewById(R.id.navigation)).setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mSignRl = mRootView.findViewById(R.id.rl_sign);
        amountView = mRootView.findViewById(R.id.amount);
        mJifenLay = mRootView.findViewById(R.id.lay_jifen);
        mGuizeLay = mRootView.findViewById(R.id.lay_guize);
        tv_sum = mRootView.findViewById(R.id.tv_sum);
        mSubmitBtn = mRootView.findViewById(R.id.btn_submit);
        mCheckRl = mRootView.findViewById(R.id.rl_task);
        tv_more = mRootView.findViewById(R.id.tv_more);
        tv_integral = mRootView.findViewById(R.id.tv_integral);
        imgDown = mRootView.findViewById(R.id.img_down);

        mLianTv = mRootView.findViewById(R.id.tv_lian);
        mSignTv = mRootView.findViewById(R.id.tv_sign);
        mStepView = mRootView.findViewById(R.id.step_view);
        mSignRl.setOnClickListener(this);
        tv_more.setOnClickListener(this);
        mJifenLay.setOnClickListener(this);
        mGuizeLay.setOnClickListener(this);
        mSubmitBtn.setOnClickListener(this);
        amountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                tv_sum.setText("兑换所需消耗的积分为：" + amount * 100);
            }
        });

        positon = Utils.getWeekOfDate(new Date());
        if (positon == 0) {
            positon = 7;
        }

        checkAdapter = new CheckAdapter();
        mCheckRl.setAdapter(checkAdapter);
        mCheckRl.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mCheckRl.addItemDecoration(new MyRecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL, Utils.dip2px(getActivity(), 0.5f), R.color.line_color));
        initData();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getPointToday();
    }

    private void initData() {
        mStepBeans.add(new StepBean(StepBean.STEP_UNDO, "星期一"));
        mStepBeans.add(new StepBean(StepBean.STEP_UNDO, "星期二"));
        mStepBeans.add(new StepBean(StepBean.STEP_UNDO, "星期三"));
        mStepBeans.add(new StepBean(StepBean.STEP_UNDO, "星期四"));
        mStepBeans.add(new StepBean(StepBean.STEP_UNDO, "星期五"));
        mStepBeans.add(new StepBean(StepBean.STEP_UNDO, "星期六"));
        mStepBeans.add(new StepBean(StepBean.STEP_CURRENT, "星期日"));

        mStepView.setStepNum(mStepBeans, positon);
    }

    private NormalAlertDialog initCleanDialog() {
        return new NormalAlertDialog.Builder(getActivity())
                .setTitleText("积分如何兑换桃花币")
                .setContentText(getResources().getString(R.string.duihuan_yubi))
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

    private void getPointToday() {
        addSubscription(MineApiFactory.getUserIntegralCheckInList(SPManager.get().getStringValue("uid")).subscribe(new Consumer<CheckInBean>() {
            @Override
            public void accept(CheckInBean checkInBean) throws Exception {
                mCheckInbean = checkInBean;
                tv_integral.setText(checkInBean.getData().getIntegral().getIntegral_exchange() + "");
                mLianTv.setText("已连续签到" + checkInBean.getData().getIntegral().getSign_times() + "天");
                for (int i = 0; i < checkInBean.getData().getSign_in().size(); i++) {
                    for (int j = 0; j < mStepBeans.size(); j++) {
                        if (mStepBeans.get(j).getNumber().equals(Utils.getWeekOfDateString(new Date(checkInBean.getData().getSign_in().get(i).getCreate_time() * 1000)))) {
                            mStepBeans.get(j).setState(StepBean.STEP_COMPLETED);
                        }
                    }
                    if (Utils.getWeekOfDate(new Date()) == Utils.getWeekOfDate(new Date(checkInBean.getData().getSign_in().get(i).getCreate_time() * 1000))) {
                        mSignTv.setText("已签到");
                    }
                }
                if (!isCheck) {
                    mStepView.setStepNum(mStepBeans, positon);
                }
                daySize = checkInBean.getData().getDaily_tasks().size();
                if (daySize > 0) {
                    checkAdapter.replaceData(checkInBean.getData().getDaily_tasks().subList(0, daySize > 3 ? 3 : daySize));
                }
                if (daySize > 3) {
                    tv_more.setEnabled(true);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

    private void checkIn() {
        addSubscription(MineApiFactory.getUserIntegralCheckIn(SPManager.get().getStringValue("uid")).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse simpleResponse) throws Exception {
                if (simpleResponse.code == 200) {
                    isCheck = true;
                    mStepView.startSignAnimation(positon - 1);
                    mSignTv.setText("已签到");
                    EventBusUtil.sendEvent(new MessageEvent(C.MSG_CHANGE_INFO));
                    getPointToday();
                } else {
                    ToastUtils.showShort(simpleResponse.msg);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
            }
        }));
    }

    private void exchange() {
        addSubscription(MineApiFactory.getUserExchange(SPManager.get().getStringValue("uid"), amountView.getAmount() * 100).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse simpleResponse) throws Exception {
                if (simpleResponse.code == 200) {
                    getPointToday();
                    //mStepView.startSignAnimation(positon);
                }
                ToastUtils.showShort(simpleResponse.msg);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
            }
        }));
    }
}
