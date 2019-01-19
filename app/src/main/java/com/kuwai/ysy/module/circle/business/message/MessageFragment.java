package com.kuwai.ysy.module.circle.business.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.allen.library.SuperTextView;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.api.CircleApiFactory;
import com.kuwai.ysy.module.circle.bean.AllRewardBean;
import com.kuwai.ysy.module.circle.bean.UnreadBean;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.widget.NavigationLayout;
import com.kuwai.ysy.widget.pageitem.RoundMessageView;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.functions.Consumer;

public class MessageFragment extends BaseFragment implements View.OnClickListener {

    private NavigationLayout mNavigation;
    private SuperTextView mTvComment;
    private SuperTextView mTvLike;
    private SuperTextView mTvReward;
    RoundMessageView mPingNum, mZanNum, mRewardNum;

    public static MessageFragment newInstance() {
        Bundle args = new Bundle();
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_message;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_comment:
                start(DyCommentMesFragment.newInstance());
                break;
            case R.id.tv_like:
                start(DyLikeMesFragment.newInstance());
                break;
            case R.id.tv_reward:
                start(DyDashangMesFragment.newInstance());
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        EventBusUtil.register(this);
        mNavigation = mRootView.findViewById(R.id.navigation);
        mTvComment = mRootView.findViewById(R.id.tv_comment);
        mTvLike = mRootView.findViewById(R.id.tv_like);
        mTvReward = mRootView.findViewById(R.id.tv_reward);
        mPingNum = mRootView.findViewById(R.id.red_ping);
        mZanNum = mRootView.findViewById(R.id.red_zan);
        mRewardNum = mRootView.findViewById(R.id.red_reward);
        mTvComment.setOnClickListener(this);
        mTvLike.setOnClickListener(this);
        mTvReward.setOnClickListener(this);
        mNavigation.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getUnreadData();
    }

    private void getUnreadData() {
        addSubscription(CircleApiFactory.getUnreadData(SPManager.get().getStringValue("uid")).subscribe(new Consumer<UnreadBean>() {
            @Override
            public void accept(UnreadBean myBlindBean) throws Exception {
                if (myBlindBean.getCode() == 200) {
                    mPingNum.setMessageNumber(myBlindBean.getData().getComment());
                    mRewardNum.setMessageNumber(myBlindBean.getData().getGift());
                    mZanNum.setMessageNumber(myBlindBean.getData().getLikes());
                } else {
                    ToastUtils.showShort(myBlindBean.getMsg());
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtils.showShort(R.string.server_error);
            }
        }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusUtil.unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isLogin(MessageEvent event) {
        if (event.getCode() == C.MSG_UNREAD_UPDATE) {
            getUnreadData();
        }
    }
}
