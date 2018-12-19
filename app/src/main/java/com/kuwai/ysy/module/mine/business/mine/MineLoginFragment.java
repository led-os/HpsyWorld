package com.kuwai.ysy.module.mine.business.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.allen.library.SuperTextView;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.home.business.loginmoudle.login.LoginActivity;
import com.kuwai.ysy.module.mine.MyCreditActivity;
import com.kuwai.ysy.module.mine.MyPointActivity;
import com.kuwai.ysy.module.mine.MyWalletActivity;
import com.kuwai.ysy.module.mine.SettingActivity;
import com.kuwai.ysy.module.mine.VipCenterActivity;
import com.kuwai.ysy.module.mine.bean.user.UserInfo;
import com.kuwai.ysy.module.mine.business.gift.GiftActivity;
import com.kuwai.ysy.module.mine.business.homepage.HomePageActivity;
import com.kuwai.ysy.module.mine.business.like.StLikeActivity;
import com.kuwai.ysy.module.mine.business.question.AskQuestionActivity;
import com.kuwai.ysy.module.mine.business.visitor.VisitorActivity;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.utils.Utils;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MineLoginFragment extends BaseFragment<MinePresenter> implements View.OnClickListener, MineContract.IMineView {

    private RelativeLayout my_integral;
    private RelativeLayout my_money;
    private SuperTextView st_credit;
    private SuperTextView st_setting;
    private SuperTextView st_menber, st_like, st_visitor, st_gift, st_ask;
    private TextView mHomeLay;
    private TextView tv_text, tv_id, tv_level, tv_money, tv_integral;
    private ImageView img_vip;

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
    protected MinePresenter getPresenter() {
        return new MinePresenter(this);
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
        EventBusUtil.register(this);
        mLayoutStatusView = mRootView.findViewById(R.id.multipleStatusView);
        my_integral = mRootView.findViewById(R.id.my_integral);
        my_money = mRootView.findViewById(R.id.my_money);
        st_credit = mRootView.findViewById(R.id.st_credit);
        mHomeLay = mRootView.findViewById(R.id.lay_home);
        tv_text = mRootView.findViewById(R.id.tv_text);
        mHomeLay.setOnClickListener(this);
        st_ask = mRootView.findViewById(R.id.st_ask);
        st_visitor = mRootView.findViewById(R.id.st_visitor);
        st_setting = mRootView.findViewById(R.id.st_setting);
        st_menber = mRootView.findViewById(R.id.st_menber);
        st_like = mRootView.findViewById(R.id.st_like);
        tv_id = mRootView.findViewById(R.id.tv_id);
        st_gift = mRootView.findViewById(R.id.st_gift);
        img_vip = mRootView.findViewById(R.id.img_vip);
        tv_level = mRootView.findViewById(R.id.tv_level);
        tv_money = mRootView.findViewById(R.id.tv_money);
        tv_integral = mRootView.findViewById(R.id.tv_integral);
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
        initData();
    }

    private void initData() {
        if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
            mPresenter.requestUserData(SPManager.get().getStringValue("uid"));
        } else {
            mLayoutStatusView.showError();
            //SuperButton button = mRootView.findViewById(R.id.no_login);

        }
    }

    @Override
    public void retry() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    /*@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            if (!Utils.isNullString(SPManager.get().getStringValue("uid"))) {
                mLayoutStatusView.showContent();
            } else {
                mLayoutStatusView.showError();
            }
        }
    }*/

    @Override
    public void setUserData(UserInfo cityMeetBean) {
        mLayoutStatusView.showContent();
        tv_text.setText(cityMeetBean.getData().getNickname());
        tv_id.setText("ID:" + cityMeetBean.getData().getUid());
        tv_level.setText(cityMeetBean.getData().getGrade() + "");
        img_vip.setVisibility(cityMeetBean.getData().getIs_vip() == 1 ? View.VISIBLE : View.GONE);
        tv_money.setText(cityMeetBean.getData().getAmount());
        tv_integral.setText(cityMeetBean.getData().getIntegral_sum() + "");
        st_credit.setRightString(cityMeetBean.getData().getAuthentication_sum() + "个未认证");
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusUtil.unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isLogin(MessageEvent event) {
        if (C.MSG_LOGIN == event.getCode()) {
            initData();
        }
    }
}
