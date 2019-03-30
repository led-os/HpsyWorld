package com.kuwai.ysy.module.findtwo.business;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.listener.AddressPickTask;
import com.kuwai.ysy.module.find.business.FoundLocation.FoundLocationFragment;
import com.kuwai.ysy.module.findtwo.CityMeetTwoFragment;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;

public class MeetFilterFragment extends BaseFragment implements View.OnClickListener {

    private ImageView mImgLeft;
    private TextView mTvSure;
    private RadioGroup mGroupSort;
    private RadioButton mRadioSortNew;
    private RadioButton mRadioSortRecent;
    private RadioButton mRadioSortNear;
    private RadioGroup mGroupSex;
    private RadioButton mRadioSexAll;
    private RadioButton mRadioSexMan;
    private RadioButton mRadioSexWoman;
    private TextView mTvCity;
    private TextView mTvPlace;
    private RadioGroup mGroupConsume;
    private RadioButton mRadioConsumeAll;
    private RadioButton mRadioConsumeTa;
    private RadioButton mRadioConsumeMe;
    private RadioButton mRadioConsumeAa;

    private String sexType = "0";
    private String consumeType = "3";
    private String takeType = "1";
    private CustomDialog themeDialog;

    public static MeetFilterFragment newInstance() {
        Bundle args = new Bundle();
        MeetFilterFragment fragment = new MeetFilterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mImgLeft = mRootView.findViewById(R.id.img_left);

        mImgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });

        mTvSure = mRootView.findViewById(R.id.tv_sure);
        mGroupSort = mRootView.findViewById(R.id.group_sort);
        mRadioSortNew = mRootView.findViewById(R.id.radio_sort_new);
        mRadioSortRecent = mRootView.findViewById(R.id.radio_sort_recent);
        mRadioSortNear = mRootView.findViewById(R.id.radio_sort_near);
        mGroupSex = mRootView.findViewById(R.id.group_sex);
        mRadioSexAll = mRootView.findViewById(R.id.radio_sex_all);
        mRadioSexMan = mRootView.findViewById(R.id.radio_sex_man);
        mRadioSexWoman = mRootView.findViewById(R.id.radio_sex_woman);
        mTvCity = mRootView.findViewById(R.id.tv_city);
        mTvPlace = mRootView.findViewById(R.id.tv_place);
        mGroupConsume = mRootView.findViewById(R.id.group_consume);
        mRadioConsumeAll = mRootView.findViewById(R.id.radio_consume_all);
        mRadioConsumeTa = mRootView.findViewById(R.id.radio_consume_ta);
        mRadioConsumeMe = mRootView.findViewById(R.id.radio_consume_me);
        mRadioConsumeAa = mRootView.findViewById(R.id.radio_consume_aa);

        mGroupSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_sex_all:
                        sexType = "0";
                        break;
                    case R.id.radio_sex_man:
                        sexType = "1";
                        break;
                    case R.id.radio_sex_woman:
                        sexType = "2";
                        break;
                }
            }
        });
        mGroupConsume.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_consume_aa:
                        consumeType = "0";
                        break;
                    case R.id.radio_consume_ta:
                        consumeType = "2";
                        break;
                    case R.id.radio_consume_me:
                        consumeType = "1";
                    case R.id.radio_consume_all:
                        consumeType = "3";
                        break;
                }
            }
        });
        mGroupSort.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_sort_new:
                        takeType = "1";
                        break;
                    case R.id.radio_sort_recent:
                        takeType = "2";
                        break;
                    case R.id.radio_sort_near:
                        takeType = "3";
                        break;
                }
            }
        });

        mTvCity.setOnClickListener(this);
        mTvPlace.setOnClickListener(this);
        mTvSure.setOnClickListener(this);
        mTvCity.setOnClickListener(this);
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_filter;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_city:
            case R.id.tv_place:
                popCustom();
                //startActivityForResult(new Intent(getActivity(), FoundLocationFragment.class), 0);
                break;
            case R.id.tv_sure:
                Bundle bundle = new Bundle();
                bundle.putString("province", provinceName);
                bundle.putString("city", cityName);
                bundle.putString("sex", sexType);
                bundle.putString("consume", consumeType);
                bundle.putString("type", takeType);
                setFragmentResult(RESULT_OK, bundle);
                pop();
                break;
        }
    }

    private String cityName = "";
    private String provinceName = "";

    private void popCustom() {

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
                    //cityId = city.getAreaId();
                    cityName = city.getName();
                    provinceName = province.getAreaName();
                    mTvCity.setText(provinceName);
                    mTvPlace.setText(cityName);
                    //mBtnGetPos.setText(province.getAreaName() + "省" + city.getAreaName());
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

        //themeDialog.show();
    }
}
