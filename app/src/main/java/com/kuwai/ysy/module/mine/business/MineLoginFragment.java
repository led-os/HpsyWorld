package com.kuwai.ysy.module.mine.business;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.allen.library.SuperTextView;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.home.business.login.LoginActivity;
import com.kuwai.ysy.module.home.business.login.LoginFragment;
import com.kuwai.ysy.module.mine.MyCreditActivity;
import com.kuwai.ysy.module.mine.MyPointActivity;
import com.kuwai.ysy.module.mine.MyWalletActivity;
import com.kuwai.ysy.module.mine.SettingActivity;
import com.kuwai.ysy.module.mine.VipCenterActivity;
import com.kuwai.ysy.module.mine.business.gift.GiftActivity;
import com.kuwai.ysy.module.mine.business.homepage.HomePageActivity;
import com.kuwai.ysy.module.mine.business.like.StLikeActivity;
import com.kuwai.ysy.module.mine.business.question.AskQuestionActivity;
import com.kuwai.ysy.module.mine.business.visitor.VisitorActivity;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MultipleStatusView;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.useful.SPManager;

public class MineLoginFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout my_integral;
    private RelativeLayout my_money;
    private SuperTextView st_credit;
    private SuperTextView st_setting;
    private SuperTextView st_menber, st_like, st_visitor, st_gift, st_ask;
    private LinearLayout mHomeLay;

    public static MineLoginFragment newInstance() {
        Bundle args = new Bundle();
        MineLoginFragment fragment = new MineLoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.my_login_fragment;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.my_integral:
                startActivity(new Intent(getActivity(), MyPointActivity.class));
                break;
            case R.id.my_money:
                startActivity(new Intent(getActivity(), MyWalletActivity.class));
                break;
            case R.id.st_credit:
                startActivity(new Intent(getActivity(), MyCreditActivity.class));
                break;
            case R.id.st_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.st_menber:
                startActivity(new Intent(getActivity(), VipCenterActivity.class));
                break;
            case R.id.st_like:
                startActivity(new Intent(getActivity(), StLikeActivity.class));
                break;
            case R.id.st_visitor:
                startActivity(new Intent(getActivity(), VisitorActivity.class));
                break;
            case R.id.st_gift:
                startActivity(new Intent(getActivity(), GiftActivity.class));
                break;
            case R.id.st_ask:
                startActivity(new Intent(getActivity(), AskQuestionActivity.class));
                break;
            case R.id.lay_home:
                startActivity(new Intent(getActivity(), HomePageActivity.class));
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mLayoutStatusView = mRootView.findViewById(R.id.multipleStatusView);
        my_integral = mRootView.findViewById(R.id.my_integral);
        my_money = mRootView.findViewById(R.id.my_money);
        st_credit = mRootView.findViewById(R.id.st_credit);
        mHomeLay = mRootView.findViewById(R.id.lay_home);
        mHomeLay.setOnClickListener(this);
        st_ask = mRootView.findViewById(R.id.st_ask);
        st_visitor = mRootView.findViewById(R.id.st_visitor);
        st_setting = mRootView.findViewById(R.id.st_setting);
        st_menber = mRootView.findViewById(R.id.st_menber);
        st_like = mRootView.findViewById(R.id.st_like);
        st_gift = mRootView.findViewById(R.id.st_gift);
        my_money.setOnClickListener(this);
        st_gift.setOnClickListener(this);
        st_setting.setOnClickListener(this);
        st_ask.setOnClickListener(this);
        st_credit.setOnClickListener(this);
        st_visitor.setOnClickListener(this);
        my_integral.setOnClickListener(this);
        st_menber.setOnClickListener(this);
        st_like.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    @Override
    public void retry() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                mLayoutStatusView.showContent();
            } else {
                mLayoutStatusView.showError();
            }
        }
    }
}
