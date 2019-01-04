package com.kuwai.ysy.module.chat.business;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import io.reactivex.functions.Consumer;

public class ChangeRemarkFragment extends BaseFragment implements View.OnClickListener {

    private NavigationLayout mNavigation;
    private EditText mEtContent;
    private String targetId = "";
    private String title = "";

    public static ChangeRemarkFragment newInstance(String targetid) {
        Bundle args = new Bundle();
        args.putString("id",targetid);
        ChangeRemarkFragment fragment = new ChangeRemarkFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_change_remark;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mNavigation = mRootView.findViewById(R.id.navigation);
        targetId = getArguments().getString("id");
        mEtContent = mRootView.findViewById(R.id.et_content);
        mNavigation.setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });

        mNavigation.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utils.isNullString(mEtContent.getText().toString())){
                    ToastUtils.showShort("请输入备注");
                }else{
                    changeRemark();
                }
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }

    void changeRemark() {
        addSubscription(ChatApiFactory.setRemark(SPManager.get().getStringValue("uid"),targetId,mEtContent.getText().toString() ).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                if (response.code == 200) {
                    pop();
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
