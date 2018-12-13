package com.kuwai.ysy.module.home.business.sading;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;

/**
 * Created by sunnysa on 2018/7/24.
 */

public class AskFragment extends BaseFragment<AskPresenter> {

    public static AskFragment newInstance() {

        Bundle args = new Bundle();

        AskFragment fragment = new AskFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.ask_fragment;
    }

    @Override
    protected AskPresenter getPresenter() {
        return null;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
