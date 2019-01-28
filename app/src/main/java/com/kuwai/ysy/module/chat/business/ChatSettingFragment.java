package com.kuwai.ysy.module.chat.business;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.adapter.PhoneBookAdapter;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.chat.bean.PhotoBook;
import com.kuwai.ysy.module.circle.ReportActivity;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.utils.security.PhoneUtil;
import com.kuwai.ysy.widget.MyRecycleViewDivider;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class ChatSettingFragment extends BaseFragment implements View.OnClickListener {

    private TextView tv_change_remark, tvReport;
    private Switch switch_black;
    private String targetId = "";
    private String title = "";
    private SuperTextView alipay_stv;

    private NavigationLayout navigationLayout;

    public static ChatSettingFragment newInstance(Bundle args) {
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
        switch (v.getId()) {
            case R.id.tv_change_remark:
                start(ChangeRemarkFragment.newInstance(targetId));
                break;
            case R.id.tv_report:
                Bundle bundle = new Bundle();
                bundle.putString("module", "-1");
                bundle.putString("p_id", targetId);
                Intent intent = new Intent(getActivity(), ReportActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        targetId = getArguments().getString("id");
        navigationLayout = mRootView.findViewById(R.id.navigation);
        navigationLayout.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        title = getArguments().getString("name");
        alipay_stv = mRootView.findViewById(R.id.alipay_stv);
        switch_black = mRootView.findViewById(R.id.switch_black);
        alipay_stv.setLeftString(title);
        alipay_stv.setLeftBottomString("ID:" + targetId);
        tv_change_remark = mRootView.findViewById(R.id.tv_change_remark);
        tvReport = mRootView.findViewById(R.id.tv_report);
        tv_change_remark.setOnClickListener(this);
        tvReport.setOnClickListener(this);

        switch_black.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    addBlack();
                } else {
                    cancelBlack();
                }
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    void addBlack() {
        addSubscription(ChatApiFactory.setBlack(SPManager.get().getStringValue("uid"), targetId).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                if (response.code == 200) {
                }
                ToastUtils.showShort(response.msg);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
                //ToastUtils.showShort("网络错误");
            }
        }));
    }

    void cancelBlack() {
        addSubscription(ChatApiFactory.cancelBlack(SPManager.get().getStringValue("uid"), targetId).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                if (response.code == 200) {
                }
                ToastUtils.showShort(response.msg);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
                //ToastUtils.showShort("网络错误");
            }
        }));
    }
}
