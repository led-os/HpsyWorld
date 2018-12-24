package com.kuwai.ysy.module.chat.business.act;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.hjq.bar.TitleBar;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.module.chat.business.QuestionListFragment;
import com.kuwai.ysy.module.find.bean.GiftPopBean;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.NavigationLayout;
import com.kuwai.ysy.widget.PasswordInputView;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;

public class SendRedActivity extends BaseActivity implements View.OnClickListener {

    private NavigationLayout mNavigation;
    private EditText mEtRedNum;
    private EditText mEtRedContent;
    private TextView mTvRedNum;
    private SuperButton mBtnSendRed;
    private CustomDialog costDialog, bottomDialog;

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.act_send_redpack;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        mNavigation = findViewById(R.id.navigation);
        mNavigation.hideLeft(true);
        mNavigation.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBottom();
            }
        });
        findViewById(R.id.left_txt).setOnClickListener(this);
        mEtRedNum = findViewById(R.id.et_red_num);
        mEtRedContent = findViewById(R.id.et_red_content);
        mTvRedNum = findViewById(R.id.tv_red_num);
        mBtnSendRed = findViewById(R.id.btn_send_red);

        mBtnSendRed.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_red:
                popCostCustom(mEtRedNum.getText().toString());
                break;
            case R.id.left_txt:
                finish();
                break;
        }
    }

    private void popCostCustom(final String money) {
        if (costDialog == null) {

            View pannel = View.inflate(this, R.layout.dialog_red_psd, null);
            ImageView imageDel = pannel.findViewById(R.id.top_del);
            final PasswordInputView inputView = pannel.findViewById(R.id.psd_red);
            TextView tvMoney = pannel.findViewById(R.id.tv_money);
            tvMoney.setText("-" + money);

            inputView.setOnFinishListener(new PasswordInputView.OnFinishListener() {
                @Override
                public void setOnPasswordFinished(String text) {
                    if (text.length() == 6) {
                        ToastUtils.showShort("输入完毕");
                        costDialog.dismiss();
                        Utils.showOrHide(SendRedActivity.this, inputView);
                    }
                }
            });

            imageDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (costDialog != null) {
                        costDialog.dismiss();
                    }
                }
            });

            costDialog = new CustomDialog.Builder(this)
                    .setView(pannel)
                    .setTouchOutside(true)
                    .setItemHeight(0.4f)
                    .setDialogGravity(Gravity.CENTER)
                    .build();
        }
        costDialog.show();
    }

    private void popBottom() {
        if (bottomDialog == null) {

            View pannel = View.inflate(this, R.layout.dialog_red_more, null);
            TextView imageDel = pannel.findViewById(R.id.top_del);
            TextView record = pannel.findViewById(R.id.tv_record);
            TextView help = pannel.findViewById(R.id.tv_help);

            imageDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bottomDialog != null) {
                        bottomDialog.dismiss();
                    }
                }
            });

            bottomDialog = new CustomDialog.Builder(this)
                    .setView(pannel)
                    .setTouchOutside(true)
                    .setItemHeight(0.4f)
                    .setDialogGravity(Gravity.BOTTOM)
                    .build();
        }
        bottomDialog.show();
    }
}
