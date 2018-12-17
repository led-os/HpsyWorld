package com.kuwai.ysy.module.home.business.loginmoudle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.net.glide.ProgressInterceptor;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.DateTimePicker;

public class InfoSexFragment extends BaseFragment implements View.OnClickListener {

    private TextView mTitle;
    private TextView mSexWarn;
    private RadioGroup mLaySex;
    private RadioButton mTvMan;
    private RadioButton mTvWoman;
    private TextView mTvDateInfo;
    private TextView mTvWarn;
    private TextView mTvDate;
    private SuperButton mBtnNext;

    private CustomDialog themeDialog;
    private String mYear = "2018";
    private String mMonth = "01";
    private String mDay = "01";

    private int sex = 0;

    public static InfoSexFragment newInstance() {
        Bundle args = new Bundle();
        InfoSexFragment fragment = new InfoSexFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_info_sex;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                if (sex == 0) {
                    ToastUtils.showShort("请选择性别");
                } else {
                    SPManager.get().putString(C.REGIST_GENDER, String.valueOf(sex));
                    SPManager.get().putString(C.REGIST_BIRTHDAY, mYear + "-" + mMonth + "-" + mDay);
                    start(InfoPosFragment.newInstance());
                }
                break;
            case R.id.tv_date:
                popCustom();
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTitle = mRootView.findViewById(R.id.title);
        mSexWarn = mRootView.findViewById(R.id.sex_warn);
        mLaySex = mRootView.findViewById(R.id.lay_sex);
        mTvMan = mRootView.findViewById(R.id.tv_man);
        mTvWoman = mRootView.findViewById(R.id.tv_woman);
        mTvDateInfo = mRootView.findViewById(R.id.tv_date_info);
        mTvWarn = mRootView.findViewById(R.id.tv_warn);
        mTvDate = mRootView.findViewById(R.id.tv_date);
        mBtnNext = mRootView.findViewById(R.id.btn_next);

        mBtnNext.setOnClickListener(this);
        mTvDate.setOnClickListener(this);
        mLaySex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tv_man:
                        sex = 1;
                        break;
                    case R.id.tv_woman:
                        sex = 2;
                        break;
                }
            }
        });
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

            pannel.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (themeDialog != null) {
                        themeDialog.dismiss();
                    }
                }
            });

            final DatePicker datePicker = new DatePicker(getActivity(), DateTimePicker.YEAR_MONTH_DAY);
            datePicker.setOffset(2);
            datePicker.setRangeEnd(2020, 12, 31);
            datePicker.setRangeStart(1970, 01, 01);
            datePicker.setSelectedItem(2018, 01, 01);
            datePicker.setTopLineColor(0xFF5415f9);
            datePicker.setLabelTextColor(0xFF5415f9);
            datePicker.setDividerColor(0xFF5415f9);
            datePicker.setTextSize(22);
            datePicker.setTextPadding(20);
            datePicker.setTextColor(getResources().getColor(R.color.balck_28));
            datePicker.setOnWheelListener(new DatePicker.OnWheelListener() {
                @Override
                public void onYearWheeled(int index, String year) {
                    mYear = year;
                }

                @Override
                public void onMonthWheeled(int index, String month) {
                    mMonth = month;
                }

                @Override
                public void onDayWheeled(int index, String day) {
                    mDay = day;
                }
            });
            //得到选择器视图，可内嵌到其他视图容器，不需要调用show方法
            layout.addView(datePicker.getContentView());

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (themeDialog != null) {
                        themeDialog.dismiss();
                    }
                    mTvDate.setText(mYear + "年" + mMonth + "月" + mDay + "日");
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
