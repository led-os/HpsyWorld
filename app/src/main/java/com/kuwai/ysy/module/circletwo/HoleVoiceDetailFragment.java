package com.kuwai.ysy.module.circletwo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.CircleImageView;
import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.circletwo.adapter.HoleVoiceAdapter;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;
import java.util.List;

public class HoleVoiceDetailFragment extends BaseFragment {

    private NavigationLayout mNavigation;
    private CircleImageView mImgHead;
    private TextView mTvTitle;
    private TextView mTvTime;
    private TextView mTvMoney;
    private RecyclerView mRlVoice;
    private SuperButton mBtnTalk;
    private ImageView mImgAnony;

    private HoleVoiceAdapter mVoiceAdaper;
    private List<CategoryBean> mDataList = new ArrayList<>();

    @Override
    public void initView(Bundle savedInstanceState) {
        mNavigation = mRootView.findViewById(R.id.navigation);
        mImgHead = mRootView.findViewById(R.id.img_head);
        mTvTitle = mRootView.findViewById(R.id.tv_title);
        mTvTime = mRootView.findViewById(R.id.tv_time);
        mTvMoney = mRootView.findViewById(R.id.tv_money);
        mRlVoice = mRootView.findViewById(R.id.rc_voice);
        mBtnTalk = mRootView.findViewById(R.id.btn_talk);
        mImgAnony = mRootView.findViewById(R.id.img_anony);
        mRlVoice.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        for (int i = 0; i < 12; i++) {
            mDataList.add(new CategoryBean());
        }
        mVoiceAdaper = new HoleVoiceAdapter();
        mRlVoice.setAdapter(mVoiceAdaper);
        mVoiceAdaper.replaceData(mDataList);
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_hole_detail_voice;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }
}
