package com.kuwai.ysy.module.home.business.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.CircleImageView;
import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.rayhahah.rbase.base.RBasePresenter;

public class InfoPosFragment extends BaseFragment implements View.OnClickListener {

    private TextView mTitle;
    private ImageView mImgPos;
    private SuperButton mBtnGetPos;
    private SuperButton mBtnNext;

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
                start(InfoHeightFragment.newInstance());
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
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
