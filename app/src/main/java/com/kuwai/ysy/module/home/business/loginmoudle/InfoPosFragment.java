package com.kuwai.ysy.module.home.business.loginmoudle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.listener.AddressPickTask;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;

public class InfoPosFragment extends BaseFragment implements View.OnClickListener {

    private TextView mTitle;
    private ImageView mImgPos;
    private SuperButton mBtnGetPos;
    private SuperButton mBtnNext;

    private CustomDialog themeDialog;
    private String cityId = "0";

    public static InfoPosFragment newInstance() {
        Bundle args = new Bundle();
        InfoPosFragment fragment = new InfoPosFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_info_city;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                if ("0".equals(cityId)) {
                    ToastUtils.showShort("请选择您所在的城市");
                } else {
                    SPManager.get().putString(C.REGIST_CITY, cityId);
                    start(InfoHeightFragment.newInstance());
                }
                break;
            case R.id.btn_get_pos:
                popCustom();
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mTitle = mRootView.findViewById(R.id.title);
        mImgPos = mRootView.findViewById(R.id.img_pos);
        mBtnGetPos = mRootView.findViewById(R.id.btn_get_pos);
        mBtnNext = mRootView.findViewById(R.id.btn_next);

        mBtnNext.setOnClickListener(this);
        mBtnGetPos.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    private void popCustom() {
        // if (themeDialog == null) {

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
                mBtnGetPos.setText(province.getAreaName() + "省" + city.getAreaName());
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
        themeDialog = new CustomDialog.Builder(getActivity())
                .setView(pannel)
                .setItemWidth(0.8f)
                .setTouchOutside(true)
                .setDialogGravity(Gravity.CENTER)
                .build();
        //}
        //themeDialog.show();
    }
}
