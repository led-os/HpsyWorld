package com.kuwai.ysy.module.home.business.loginmoudle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.home.bean.GoodsCategory;
import com.kuwai.ysy.utils.Utils;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.NumberPicker;
import cn.qqtheme.framework.picker.SinglePicker;

public class InfoHeightFragment extends BaseFragment implements View.OnClickListener {

    private TextView mTitle;
    private TextView mSubTitle;
    private TextView mTvDate;
    private View mLine;
    private TextView mTvYearIncome;
    private TextView mTvIncome;
    private View mLine1;
    private SuperButton mBtnNext;

    private CustomDialog themeDialog, heightDialog;
    private String height = "165";
    private int incomeId = 4;
    private String income = "";

    public static InfoHeightFragment newInstance() {
        Bundle args = new Bundle();
        InfoHeightFragment fragment = new InfoHeightFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_info_height;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                SPManager.get().putString(C.REGIST_INCOME, String.valueOf(incomeId));
                SPManager.get().putString(C.REGIST_HEIGHT, height);
                start(InfoEducationFragment.newInstance());
                break;
            case R.id.tv_income:
                popHeightCustom();
                break;
            case R.id.tv_date:
                popIncomeCustom();
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTitle = mRootView.findViewById(R.id.title);
        mSubTitle = mRootView.findViewById(R.id.sub_title);
        mTvDate = mRootView.findViewById(R.id.tv_date);
        mLine = mRootView.findViewById(R.id.line);
        mTvYearIncome = mRootView.findViewById(R.id.tv_year_income);
        mTvIncome = mRootView.findViewById(R.id.tv_income);
        mLine1 = mRootView.findViewById(R.id.line1);
        mBtnNext = mRootView.findViewById(R.id.btn_next);

        mBtnNext.setOnClickListener(this);
        mTvIncome.setOnClickListener(this);
        mTvDate.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    private void popIncomeCustom() {
        if (themeDialog == null) {

            View pannel = View.inflate(getActivity(), R.layout.dialog_year_picker, null);
            LinearLayout layout = pannel.findViewById(R.id.wheelview_container);
            SuperButton submit = pannel.findViewById(R.id.submit);
            TextView top = pannel.findViewById(R.id.top);
            top.setText("选择身高");

            pannel.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (themeDialog != null) {
                        themeDialog.dismiss();
                    }
                }
            });

            final NumberPicker picker = new NumberPicker(getActivity());
            picker.setWidth(picker.getScreenWidthPixels() / 2);
            picker.setCycleDisable(true);
            picker.setDividerVisible(false);
            picker.setOffset(2);//偏移量
            picker.setRange(145, 210, 1);//数字范围
            picker.setSelectedItem(165);
            picker.setLabel("cm");
            picker.setItemWidth(66);
            picker.setLabelTextColor(getResources().getColor(R.color.balck_28));
            picker.setTextSize(26);
            picker.setTextColor(getResources().getColor(R.color.balck_28));
            picker.setTextPadding(20);
            picker.setOnWheelListener(new NumberPicker.OnWheelListener() {
                @Override
                public void onWheeled(int index, Number item) {
                    height = item + "";
                }
            });
            //得到选择器视图，可内嵌到其他视图容器，不需要调用show方法
            layout.addView(picker.getContentView());

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (themeDialog != null) {
                        themeDialog.dismiss();
                        mTvDate.setText(picker.getSelectedItem() + "cm");
                    }
                }
            });
            themeDialog = new CustomDialog.Builder(getActivity())
                    .setView(pannel)
                    .setItemWidth(0.8f)
                    .setTouchOutside(true)
                    .setDialogGravity(Gravity.CENTER)
                    .build();
        }
        themeDialog.show();
    }

    private void popHeightCustom() {
        if (heightDialog == null) {

            View pannel = View.inflate(getActivity(), R.layout.dialog_year_picker, null);
            LinearLayout layout = pannel.findViewById(R.id.wheelview_container);
            SuperButton submit = pannel.findViewById(R.id.submit);
            TextView top = pannel.findViewById(R.id.top);
            top.setText("选择年收入");

            pannel.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (heightDialog != null) {
                        heightDialog.dismiss();
                    }
                }
            });

            List<GoodsCategory> data = new ArrayList<>();
            data.add(new GoodsCategory(1, "3W以下"));
            data.add(new GoodsCategory(2, "3W-5W"));
            data.add(new GoodsCategory(3, "5W-8W"));
            data.add(new GoodsCategory(4, "8W-10W"));
            data.add(new GoodsCategory(5, "10W-15W"));
            data.add(new GoodsCategory(6, "15W-20W"));
            data.add(new GoodsCategory(7, "20W以上"));
            final SinglePicker<GoodsCategory> picker = new SinglePicker<>(getActivity(), data);
            picker.setCanceledOnTouchOutside(false);
            picker.setSelectedIndex(3);
            picker.setOffset(2);
            picker.setCycleDisable(true);
            picker.setDividerColor(0xFF5415f9);
            picker.setTextSize(26);
            picker.setTextColor(getResources().getColor(R.color.balck_28));
            picker.setTextPadding(20);
            picker.setOnWheelListener(new SinglePicker.OnWheelListener<GoodsCategory>() {
                @Override
                public void onWheeled(int index, GoodsCategory item) {
                    incomeId = item.getId();
                    income = item.getName();
                }
            });
            //得到选择器视图，可内嵌到其他视图容器，不需要调用show方法
            layout.addView(picker.getContentView());

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (heightDialog != null) {
                        heightDialog.dismiss();
                        incomeId = picker.getSelectedItem().getId();
                        mTvIncome.setText(picker.getSelectedItem().getName());
                    }
                }
            });
            heightDialog = new CustomDialog.Builder(getActivity())
                    .setView(pannel)
                    .setItemWidth(0.8f)
                    .setTouchOutside(true)
                    .setDialogGravity(Gravity.CENTER)
                    .build();
        }
        heightDialog.show();
    }
}
