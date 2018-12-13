package com.kuwai.ysy.module.chat.business;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.adapter.PhoneBookAdapter;
import com.kuwai.ysy.module.chat.bean.PhotoBook;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.utils.security.PhoneUtil;
import com.kuwai.ysy.widget.MyRecycleViewDivider;
import com.rayhahah.rbase.base.RBasePresenter;

import java.util.ArrayList;
import java.util.List;

public class ChatSettingFragment extends BaseFragment implements View.OnClickListener {

    private TextView tv_change_remark,tvReport;

    public static ChatSettingFragment newInstance() {
        Bundle args = new Bundle();
        ChatSettingFragment fragment = new ChatSettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_chat_setting;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_change_remark:
                start(ChangeRemarkFragment.newInstance());
                break;
            case R.id.tv_report:
                start(ReportFragment.newInstance());
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        tv_change_remark  =mRootView.findViewById(R.id.tv_change_remark);
        tvReport = mRootView.findViewById(R.id.tv_report);
        tv_change_remark.setOnClickListener(this);
        tvReport.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
