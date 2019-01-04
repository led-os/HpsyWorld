package com.kuwai.ysy.module.mine.business.change;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.bean.MessageEvent;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.mine.api.MineApiFactory;
import com.kuwai.ysy.utils.EventBusUtil;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import io.reactivex.functions.Consumer;

public class ChangeXuanFragment extends BaseFragment implements View.OnClickListener {

    private NavigationLayout mNavigation;
    private EditText mEtContent;

    public static ChangeXuanFragment newInstance() {
        Bundle args = new Bundle();
        ChangeXuanFragment fragment = new ChangeXuanFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_change_xuan;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mNavigation = mRootView.findViewById(R.id.navigation);
        mEtContent = mRootView.findViewById(R.id.et_content);
        mNavigation.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeXuan();
            }
        });
        mNavigation.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    private void changeXuan() {
        addSubscription(MineApiFactory.updateUserSig(SPManager.get().getStringValue("uid"), mEtContent.getText().toString()).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                if (response.code == 200) {
                    EventBusUtil.sendEvent(new MessageEvent(C.MSG_CHANGE_INFO));
                    pop();
                }
                ToastUtils.showShort(response.msg);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
            }
        }));
    }
}
