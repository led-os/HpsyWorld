package com.kuwai.ysy.module.home.business.loginmoudle.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hjq.bar.TitleBar;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.chat.bean.StsBean;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.umeng.socialize.UMShareAPI;

import io.reactivex.functions.Consumer;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private TitleBar mTitleBar;

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.layout_container;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        getSts();
        loadRootFragment(R.id.container, LoginFragment.newInstance(),false,true);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    void getSts() {
        addSubscription(ChatApiFactory.getSts().subscribe(new Consumer<StsBean>() {
            @Override
            public void accept(StsBean stsBean) throws Exception {
                if (stsBean.getCode() == 200) {
                    SPManager.get().putString(C.ALI_ACID, stsBean.getData().getAccessKeyId());
                    SPManager.get().putString(C.ALI_SECRET, stsBean.getData().getAccessKeySecret());
                    SPManager.get().putString(C.ALI_TOKEN, stsBean.getData().getSecurityToken());
                } else {
                    ToastUtils.showShort(stsBean.getMsg());
                }
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
