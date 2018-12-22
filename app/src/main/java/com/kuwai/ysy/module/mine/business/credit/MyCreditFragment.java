package com.kuwai.ysy.module.mine.business.credit;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.allen.library.SuperTextView;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.bean.CreditBean;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.SPManager;

public class MyCreditFragment extends BaseFragment<MyCreditPresenter> implements MyCreditContract.IHomeView, View.OnClickListener {

    private SuperTextView mAuthTv;
    private NavigationLayout mNavigation;
    private SuperTextView mStHeadicon;
    private SuperTextView mStPhone;
    private SuperTextView mTvAuth;
    private SuperTextView mStEdu;
    private SuperTextView mStHouse;
    private SuperTextView mStCar;

    public static MyCreditFragment newInstance() {
        Bundle args = new Bundle();
        MyCreditFragment fragment = new MyCreditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.my_credit_certification;
    }

    @Override
    protected MyCreditPresenter getPresenter() {
        return new MyCreditPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.st_headicon:
                break;
            case R.id.st_phone:
                break;
            case R.id.tv_auth:

                start(AuthFragment.newInstance());
                break;
            case R.id.st_edu:
                start(EduFragment.newInstance());
                break;
            case R.id.st_house:

                start(HouseFragment.newInstance());
                break;
            case R.id.st_car:
                start(CarFragment.newInstance());
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        mNavigation = (NavigationLayout) mRootView.findViewById(R.id.navigation);
        mStHeadicon = (SuperTextView) mRootView.findViewById(R.id.st_headicon);
        mStPhone = (SuperTextView) mRootView.findViewById(R.id.st_phone);
        mTvAuth = (SuperTextView) mRootView.findViewById(R.id.tv_auth);
        mStEdu = (SuperTextView) mRootView.findViewById(R.id.st_edu);
        mStHouse = (SuperTextView) mRootView.findViewById(R.id.st_house);
        mStCar = (SuperTextView) mRootView.findViewById(R.id.st_car);


    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestHomeData(SPManager.getStringValue("uid", "1"));
    }

    @Override
    public void setHomeData(CreditBean creditBean) {

        if (creditBean.getData().get(0).getIs_avatar() == 1) {
            mStHeadicon.setRightString("已认证")
                    .setRightTvDrawableRight(null)
                    .setRightTextColor(Color.parseColor("#bdbdbd"));
        } else {
            mStHeadicon.setRightString("去认证")
                    .setRightTvDrawableRight(getResources().getDrawable(R.drawable.center_ic_list_enter))
                    .setRightTextColor(Color.parseColor("#ff6161"));
            mStHeadicon.setOnClickListener(this);
        }

        if (creditBean.getData().get(0).getIs_phone() == 1) {
            mStPhone.setRightString("已认证")
                    .setRightTvDrawableRight(null)
                    .setRightTextColor(Color.parseColor("#bdbdbd"));
        } else {
            mStPhone.setRightString("去认证")
                    .setRightTvDrawableRight(getResources().getDrawable(R.drawable.center_ic_list_enter))
                    .setRightTextColor(Color.parseColor("#ff6161"));
            mStPhone.setOnClickListener(this);
        }

        if (creditBean.getData().get(0).getIs_real() == 1) {
            mTvAuth.setRightString("已认证")
                    .setRightTvDrawableRight(null)
                    .setRightTextColor(Color.parseColor("#bdbdbd"));
        } else {
            mTvAuth.setRightString("去认证")
                    .setRightTvDrawableRight(getResources().getDrawable(R.drawable.center_ic_list_enter))
                    .setRightTextColor(Color.parseColor("#ff6161"));
            mTvAuth.setOnClickListener(this);
        }

        if (creditBean.getData().get(0).getIs_education() == 1) {
            mStEdu.setRightString("已认证")
                    .setRightTvDrawableRight(null)
                    .setRightTextColor(Color.parseColor("#bdbdbd"));
        } else {
            mStEdu.setRightString("去认证")
                    .setRightTvDrawableRight(getResources().getDrawable(R.drawable.center_ic_list_enter))
                    .setRightTextColor(Color.parseColor("#ff6161"));
            mStEdu.setOnClickListener(this);
        }

        if (creditBean.getData().get(0).getIs_house() == 1) {
            mStHouse.setRightString("已认证")
                    .setRightTvDrawableRight(null)
                    .setRightTextColor(Color.parseColor("#bdbdbd"));
        } else {
            mStHouse.setRightString("去认证")
                    .setRightTvDrawableRight(getResources().getDrawable(R.drawable.center_ic_list_enter))
                    .setRightTextColor(Color.parseColor("#ff6161"));
            mStHouse.setOnClickListener(this);
        }

        if (creditBean.getData().get(0).getIs_vehicle() == 1) {
            mStCar.setRightString("已认证")
                    .setRightTvDrawableRight(null)
                    .setRightTextColor(Color.parseColor("#bdbdbd"));
        } else {
            mStCar.setRightString("去认证")
                    .setRightTvDrawableRight(getResources().getDrawable(R.drawable.center_ic_list_enter))
                    .setRightTextColor(Color.parseColor("#ff6161"));
            mStCar.setOnClickListener(this);
        }

    }

    @Override
    public void showError(int errorCode, String msg) {

    }

    @Override
    public void showViewLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showViewError(Throwable t) {

    }
}
