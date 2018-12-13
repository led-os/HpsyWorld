package com.kuwai.ysy.module.home.business.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.home.bean.GoodsCategory;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.DateTimePicker;
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

    private CustomDialog themeDialog;
    private String mHeight;

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
                start(InfoEducationFragment.newInstance());
                break;
            case R.id.tv_income:
                popCustom();
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
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    private void popCustom() {
        if (themeDialog == null) {

            View pannel = View.inflate(getActivity(), R.layout.dialog_year_picker, null);
            LinearLayout layout = pannel.findViewById(R.id.wheelview_container);
            SuperButton submit = pannel.findViewById(R.id.submit);
            TextView top = pannel.findViewById(R.id.top);
            top.setText("选择年收入");

            List<GoodsCategory> data = new ArrayList<>();
            data.add(new GoodsCategory(1, "10-20万"));
            data.add(new GoodsCategory(2, "20-30万"));
            data.add(new GoodsCategory(3, "30-40万"));
            data.add(new GoodsCategory(4, "40-50万"));
            data.add(new GoodsCategory(5, "50-80万"));
            data.add(new GoodsCategory(6, "80万以上"));
            SinglePicker<GoodsCategory> picker = new SinglePicker<>(getActivity(), data);
            picker.setCanceledOnTouchOutside(false);
            picker.setSelectedIndex(1);
            picker.setOffset(2);
            picker.setCycleDisable(true);
            picker.setDividerColor(0xFF5415f9);
            picker.setTextSize(26);
            picker.setTextColor(getResources().getColor(R.color.balck_28));
            picker.setTextPadding(20);
           /* picker.setOnItemPickListener(new SinglePicker.OnItemPickListener<GoodsCategory>() {
                @Override
                public void onItemPicked(int index, GoodsCategory item) {
                    mHeight = item.getName();
                    //showToast("index=" + index + ", id=" + item.getId() + ", name=" + item.getName());
                }
            });*/
            picker.setOnWheelListener(new SinglePicker.OnWheelListener<GoodsCategory>() {
                @Override
                public void onWheeled(int index, GoodsCategory item) {
                    mHeight = item.getName();
                }
            });
            //得到选择器视图，可内嵌到其他视图容器，不需要调用show方法
            layout.addView(picker.getContentView());

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showShort(mHeight);
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
}
