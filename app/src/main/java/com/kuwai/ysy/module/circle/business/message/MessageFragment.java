package com.kuwai.ysy.module.circle.business.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.allen.library.SuperTextView;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;

public class MessageFragment extends BaseFragment implements View.OnClickListener {

    private NavigationLayout mNavigation;
    private SuperTextView mTvComment;
    private SuperTextView mTvLike;
    private SuperTextView mTvReward;

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
        mNavigation = mRootView.findViewById(R.id.navigation);
        mTvComment = mRootView.findViewById(R.id.tv_comment);
        mTvLike = mRootView.findViewById(R.id.tv_like);
        mTvReward = mRootView.findViewById(R.id.tv_reward);
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
    }
}
