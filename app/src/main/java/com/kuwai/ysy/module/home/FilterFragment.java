package com.kuwai.ysy.module.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.listener.AddressPickTask;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.home.bean.GoodsCategory;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.SinglePicker;
import io.reactivex.functions.Consumer;

public class FilterFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout mRlTop;
    private TextView mLeftTxt;
    private TextView mTvAge;
    private TextView mTvAddress;
    private TextView mTvHeight;
    private TextView mTvSex;
    private TextView mTvEdu;
    private TextView mTvIncome;
    private SuperButton mTvCancel;
    private SuperButton mTvSure;

    private CustomDialog ageDialog, heightDialog, eduDialog, incomeDialog, sexDialog;
    private String[] zeHeightArray = new String[]{"150cm以下", "150-155cm", "155-160cm", "160-165cm", "165-170cm", "170-175cm", "175-180cm", "180-185cm", "185-190cm", "190cm以上"};
    private String[] zeAgeArray = new String[]{"20-25岁", "25-30岁", "30-35岁", "35-40岁", "40-45岁", "45-50岁", "50岁以上"};
    private String[] sexArray = new String[]{"男", "女"};
    private String cityId = "";
    private int incomeId = -1;

    public static FilterFragment newInstance() {
        Bundle args = new Bundle();
        FilterFragment fragment = new FilterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.pop_filter;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_txt:
                getActivity().finish();
                break;
            case R.id.tv_cancel:
                getActivity().finish();
                break;
            case R.id.tv_sure:
                getActivity().finish();
                break;
            case R.id.tv_age:
                popZeAgeCustom();
                break;
            case R.id.tv_address:
                popAddressCustom();
                break;
            case R.id.tv_height:
                popZeHeightCustom();
                break;
            case R.id.tv_edu:
                popEduCustom();
                break;
            case R.id.tv_income:
                popIncomeCustom();
                break;
            case R.id.tv_sex:
                popSexCustom();
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mRlTop = mRootView.findViewById(R.id.rl_top);
        mLeftTxt = mRootView.findViewById(R.id.left_txt);
        mTvAge = mRootView.findViewById(R.id.tv_age);
        mTvAddress = mRootView.findViewById(R.id.tv_address);
        mTvHeight = mRootView.findViewById(R.id.tv_height);
        mTvSex = mRootView.findViewById(R.id.tv_sex);
        mTvEdu = mRootView.findViewById(R.id.tv_edu);
        mTvIncome = mRootView.findViewById(R.id.tv_income);
        mTvCancel = mRootView.findViewById(R.id.tv_cancel);
        mTvSure = mRootView.findViewById(R.id.tv_sure);

        mLeftTxt.setOnClickListener(this);
        mTvAge.setOnClickListener(this);
        mTvAddress.setOnClickListener(this);
        mTvHeight.setOnClickListener(this);
        mTvSex.setOnClickListener(this);
        mTvEdu.setOnClickListener(this);
        mTvIncome.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
        mTvSure.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    private void popZeAgeCustom() {
        if (ageDialog == null) {

            View pannel = View.inflate(getActivity(), R.layout.dialog_year_picker, null);
            LinearLayout layout = pannel.findViewById(R.id.wheelview_container);
            SuperButton submit = pannel.findViewById(R.id.submit);
            TextView top = pannel.findViewById(R.id.top);
            top.setText("请选择年龄");

            pannel.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ageDialog != null) {
                        ageDialog.dismiss();
                    }
                }
            });

            final SinglePicker<String> picker = new SinglePicker<>(getActivity(), Arrays.asList(zeAgeArray));
            picker.setCanceledOnTouchOutside(false);
            picker.setSelectedIndex(1);
            picker.setOffset(2);
            picker.setCycleDisable(true);
            picker.setDividerColor(0xFF5415f9);
            picker.setTextSize(26);
            picker.setTextColor(getResources().getColor(R.color.balck_28));
            picker.setTextPadding(20);
            layout.addView(picker.getContentView());

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ageDialog != null) {
                        ageDialog.dismiss();
                        mTvAge.setText(picker.getSelectedItem());
                    }
                }
            });
            ageDialog = new CustomDialog.Builder(getActivity())
                    .setView(pannel)
                    .setItemWidth(0.8f)
                    .setTouchOutside(true)
                    .setDialogGravity(Gravity.CENTER)
                    .build();
        }
        ageDialog.show();
    }

    private void popSexCustom() {
        if (sexDialog == null) {

            View pannel = View.inflate(getActivity(), R.layout.dialog_year_picker, null);
            LinearLayout layout = pannel.findViewById(R.id.wheelview_container);
            SuperButton submit = pannel.findViewById(R.id.submit);
            TextView top = pannel.findViewById(R.id.top);
            top.setText("请选择性别");

            pannel.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sexDialog != null) {
                        sexDialog.dismiss();
                    }
                }
            });

            final SinglePicker<String> picker = new SinglePicker<>(getActivity(), Arrays.asList(sexArray));
            picker.setCanceledOnTouchOutside(false);
            picker.setSelectedIndex(1);
            picker.setOffset(2);
            picker.setCycleDisable(true);
            picker.setDividerColor(0xFF5415f9);
            picker.setTextSize(26);
            picker.setTextColor(getResources().getColor(R.color.balck_28));
            picker.setTextPadding(20);
            layout.addView(picker.getContentView());

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sexDialog != null) {
                        sexDialog.dismiss();
                        mTvSex.setText(picker.getSelectedItem());
                    }
                }
            });
            sexDialog = new CustomDialog.Builder(getActivity())
                    .setView(pannel)
                    .setItemWidth(0.8f)
                    .setTouchOutside(true)
                    .setDialogGravity(Gravity.CENTER)
                    .build();
        }
        sexDialog.show();
    }

    private void popAddressCustom() {
        View pannel = View.inflate(getActivity(), R.layout.dialog_year_picker, null);
        LinearLayout layout = pannel.findViewById(R.id.wheelview_container);
        SuperButton submit = pannel.findViewById(R.id.submit);
        AddressPickTask task = new AddressPickTask(getActivity());
        task.setHideCounty(true);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
                ToastUtils.showShort("数据初始化失败");
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                cityId = city.getAreaId();
                mTvAddress.setText(province.getAreaName() + "省" + city.getAreaName());
            }
        });
        task.execute("江苏", "苏州市");
        //得到选择器视图，可内嵌到其他视图容器，不需要调用show方法
        //layout.addView(task.getContentView());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ToastUtils.showShort(mYear + "年" + mMonth + "月" + mDay + "日");
            }
        });
    }

    private void popZeHeightCustom() {
        if (heightDialog == null) {

            View pannel = View.inflate(getActivity(), R.layout.dialog_year_picker, null);
            LinearLayout layout = pannel.findViewById(R.id.wheelview_container);
            SuperButton submit = pannel.findViewById(R.id.submit);
            TextView top = pannel.findViewById(R.id.top);
            top.setText("请选择身高");

            pannel.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (heightDialog != null) {
                        heightDialog.dismiss();
                    }
                }
            });

            final SinglePicker<String> picker = new SinglePicker<>(getActivity(), Arrays.asList(zeHeightArray));
            picker.setCanceledOnTouchOutside(false);
            picker.setSelectedIndex(1);
            picker.setOffset(2);
            picker.setCycleDisable(true);
            picker.setDividerColor(0xFF5415f9);
            picker.setTextSize(26);
            picker.setTextColor(getResources().getColor(R.color.balck_28));
            picker.setTextPadding(20);
            //得到选择器视图，可内嵌到其他视图容器，不需要调用show方法
            layout.addView(picker.getContentView());

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (heightDialog != null) {
                        heightDialog.dismiss();
                        mTvHeight.setText(picker.getSelectedItem());
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

    private void popEduCustom() {
        View pannel = View.inflate(getActivity(), R.layout.dialog_year_picker, null);
        LinearLayout layout = pannel.findViewById(R.id.wheelview_container);
        SuperButton submit = pannel.findViewById(R.id.submit);
        TextView top = pannel.findViewById(R.id.top);
        top.setText("请选择学历");

        pannel.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eduDialog != null) {
                    eduDialog.dismiss();
                }
            }
        });

        List<GoodsCategory> data = new ArrayList<>();
        data.add(new GoodsCategory(1, "大专以下"));
        data.add(new GoodsCategory(2, "大专"));
        data.add(new GoodsCategory(3, "本科"));
        data.add(new GoodsCategory(4, "硕士"));
        data.add(new GoodsCategory(5, "博士"));
        data.add(new GoodsCategory(6, "海外留学"));
        final SinglePicker<GoodsCategory> picker = new SinglePicker<>(getActivity(), data);
        picker.setCanceledOnTouchOutside(false);
        picker.setSelectedIndex(1);
        picker.setOffset(2);
        picker.setCycleDisable(true);
        picker.setDividerColor(0xFF5415f9);
        picker.setTextSize(26);
        picker.setTextColor(getResources().getColor(R.color.balck_28));
        picker.setTextPadding(20);
        layout.addView(picker.getContentView());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eduDialog != null) {
                    eduDialog.dismiss();
                    mTvEdu.setText(picker.getSelectedItem().getName());
                }
            }
        });
        eduDialog = new CustomDialog.Builder(getActivity())
                .setView(pannel)
                .setItemWidth(0.8f)
                .setTouchOutside(true)
                .setDialogGravity(Gravity.CENTER)
                .build();
        eduDialog.show();
    }

    private void popIncomeCustom() {
        View pannel = View.inflate(getActivity(), R.layout.dialog_year_picker, null);
        LinearLayout layout = pannel.findViewById(R.id.wheelview_container);
        SuperButton submit = pannel.findViewById(R.id.submit);
        TextView top = pannel.findViewById(R.id.top);
        top.setText("选择年收入");

        pannel.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (incomeDialog != null) {
                    incomeDialog.dismiss();
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
        layout.addView(picker.getContentView());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (incomeDialog != null) {
                    incomeDialog.dismiss();
                    incomeId = picker.getSelectedItem().getId();
                    mTvIncome.setText(picker.getSelectedItem().getName());

                }
            }
        });
        incomeDialog = new CustomDialog.Builder(getActivity())
                .setView(pannel)
                .setItemWidth(0.8f)
                .setTouchOutside(true)
                .setDialogGravity(Gravity.CENTER)
                .build();
        incomeDialog.show();
    }
}
