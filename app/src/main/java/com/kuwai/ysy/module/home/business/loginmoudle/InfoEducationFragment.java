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
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.SinglePicker;

public class InfoEducationFragment extends BaseFragment implements View.OnClickListener {

    private TextView mTitle;
    private TextView mSubTitle;
    private TextView mTvEducation;
    private View mLine;
    private SuperButton mBtnNext;

    private String eduTv = "大专";
    private CustomDialog eduDialog = null;

    public static InfoEducationFragment newInstance() {
        Bundle args = new Bundle();
        InfoEducationFragment fragment = new InfoEducationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_info_education;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                SPManager.get().putString(C.REGIST_EDUCATION, eduTv);
                start(InfoInviteFragment.newInstance());
                break;
            case R.id.tv_education:
                popEduCustom();
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTitle = mRootView.findViewById(R.id.title);
        mSubTitle = mRootView.findViewById(R.id.sub_title);
        mTvEducation = mRootView.findViewById(R.id.tv_education);
        mLine = mRootView.findViewById(R.id.line);
        mBtnNext = mRootView.findViewById(R.id.btn_next);

        mBtnNext.setOnClickListener(this);
        mTvEducation.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    private void popEduCustom() {
        if (eduDialog == null) {

            View pannel = View.inflate(getActivity(), R.layout.dialog_year_picker, null);
            LinearLayout layout = pannel.findViewById(R.id.wheelview_container);
            SuperButton submit = pannel.findViewById(R.id.submit);
            TextView top = pannel.findViewById(R.id.top);
            top.setText("选择学历");

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
            SinglePicker<GoodsCategory> picker = new SinglePicker<>(getActivity(), data);
            picker.setCanceledOnTouchOutside(false);
            picker.setSelectedIndex(1);
            picker.setOffset(2);
            picker.setCycleDisable(true);
            picker.setDividerColor(0xFF5415f9);
            picker.setTextSize(26);
            picker.setTextColor(getResources().getColor(R.color.balck_28));
            picker.setTextPadding(20);
            picker.setOnWheelListener(new SinglePicker.OnWheelListener<GoodsCategory>() {
                @Override
                public void onWheeled(int index, GoodsCategory item) {
                    eduTv = item.getName();
                }
            });
            //得到选择器视图，可内嵌到其他视图容器，不需要调用show方法
            layout.addView(picker.getContentView());

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (eduDialog != null) {
                        eduDialog.dismiss();
                        mTvEducation.setText(eduTv);
                    }
                }
            });
            eduDialog = new CustomDialog.Builder(getActivity())
                    .setView(pannel)
                    .setItemWidth(0.8f)
                    .setTouchOutside(true)
                    .setDialogGravity(Gravity.CENTER)
                    .build();
        }
        eduDialog.show();
    }
}
