package com.kuwai.ysy.module.mine.business;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.allen.library.SuperTextView;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.module.mine.business.teamplayer.SingleListActivity;
import com.kuwai.ysy.utils.DialogUtil;
import com.rayhahah.rbase.bean.MsgEvent;
import com.rayhahah.rbase.fragmentmanage.anim.DefaultHorizontalAnimator;
import com.rayhahah.rbase.fragmentmanage.anim.FragmentAnimator;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class LoginActivity extends BaseActivity<LoginPresenter>
        implements LoginContract.ILoginView, View.OnClickListener {

    private EditText et_mine_login_username, et_mine_login_password;
    private Button btn_mine_login;
    private SuperTextView mMytext;

    @Override
    protected LoginPresenter getPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_home1;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void showViewLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showViewError(Throwable t) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.myText:
                /*if (findFragment(MyFragment.class) == null) {
                    loadRootFragment(R.id.fl_container, MyFragment.newInstance());
                }*/
                //openActivity(TestActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void loginSuccess() {
        ToastUtils.showShort("登陆成功！");
        DialogUtil.dismissDialog(true);
    }

    @Override
    public void loginFailed() {
        ToastUtils.showShort("账号或密码不匹配");
        DialogUtil.dismissDialog(false);
        startActivity(new Intent(this, SingleListActivity.class));
    }

    protected void initView() {
        mMytext = findViewById(R.id.myText);
        mMytext.setOnClickListener(this);
        DialogUtil.showProgressDialog(this, Color.RED);
        //DialogUtil.setProgress(50);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 100; i++) {
                    DialogUtil.setProgress(i);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
       /* et_mine_login_username = findViewById(R.id.et_mine_login_username);
        et_mine_login_password = findViewById(R.id.et_mine_login_password);
        btn_mine_login = findViewById(R.id.btn_mine_login);
        btn_mine_login.setOnClickListener(this);*/
        // 每隔1s执行一次事件
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void isLogin(MsgEvent event) {
        if (C.EventAction.UPDATE_CURRENT_USER.equals(event.getAction())) {
            if (C.TRUE.equals(SPManager.get().getStringValue(C.SP.IS_LOGIN))) {
                finish();
            }
        }
    }

    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
    }
}
