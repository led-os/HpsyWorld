package com.kuwai.ysy.module.chat.business.redpack;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.rong.RedSendMessage;
import com.kuwai.ysy.rong.bean.RedBean;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.NavigationLayout;
import com.kuwai.ysy.widget.PasswordInputView;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;
import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;

public class SendRedActivity extends BaseActivity implements View.OnClickListener {

    private NavigationLayout mNavigation;
    private EditText mEtRedNum;
    private EditText mEtRedContent;
    private TextView mTvRedNum;
    private SuperButton mBtnSendRed;
    private CustomDialog costDialog, bottomDialog;

    private String targetId;

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
        targetId = getIntent().getStringExtra("target");

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
        mEtRedNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!Utils.isNullString(s.toString())) {
                    mTvRedNum.setText(s.toString());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_red:
                if (Double.parseDouble(mEtRedNum.getText().toString()) <= 0) {
                    ToastUtils.showShort("请输入红包金额");
                } else {
                    popCostCustom(mEtRedNum.getText().toString());
                }
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
                        if (Utils.isNullString(mEtRedContent.getText().toString())) {
                            ToastUtils.showShort("请输入寄语");
                        } else {
                            sendRed(money, Utils.encrypt32(text), mEtRedContent.getText().toString());
                        }
                        inputView.setText("");
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

    void sendRed(String money, String pas, final String jiyu) {
        Map<String, Object> param = new HashMap<>();
        param.put("uid", SPManager.get().getStringValue("uid"));
        param.put("other_uid", targetId);
        param.put("money", money);
        param.put("parment_password", pas);
        param.put("message", mEtRedContent.getText().toString());
        addSubscription(ChatApiFactory.sendRed(param).subscribe(new Consumer<RedBean>() {
            @Override
            public void accept(RedBean redBean) throws Exception {
                if (redBean.getCode() == 200) {
                    //myFriendsAdapter.replaceData(myBlindBean.getData());
                    sendMessage(redBean.getData(), jiyu);
                } else {
                    ToastUtils.showShort(redBean.getMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
                ToastUtils.showShort("网络错误");
            }
        }));
    }

    private void sendMessage(String redid, String jiyu) {
        RedSendMessage testMessage = new RedSendMessage();
        testMessage.setContent(redid);
        testMessage.setExtra(jiyu);
        testMessage.setUserInfo(new UserInfo(SPManager.get().getStringValue("uid"), SPManager.get().getStringValue("nickname"), Uri.parse(SPManager.get().getStringValue("icon"))));
        final Message message = Message.obtain(targetId, Conversation.ConversationType.PRIVATE, testMessage);
        RongIM.getInstance().sendMessage(message, "红包消息", "红包消息", new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {
                Log.i("xxx", "onTokenIncorrect: ");
            }

            @Override
            public void onSuccess(Message message) {
                Log.i("xxx", "onTokenIncorrect: ");
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                Log.i("xxx", "onTokenIncorrect: ");
            }
        });
        finish();
    }
}
