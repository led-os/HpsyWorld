package com.kuwai.ysy.module.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.module.chat.business.ChatSettingActivity;
import com.kuwai.ysy.module.chat.business.ChatSettingFragment;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.rbase.base.RBasePresenter;


public class ConversationActivity extends BaseActivity {

    private NavigationLayout navigationLayout;

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        navigationLayout = findViewById(R.id.navigation);
        navigationLayout.setRightClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConversationActivity.this, ChatSettingActivity.class));
            }
        });
    }

    @Override
    protected int getLayoutID() {
        return R.layout.conversation;
    }

    /*private void sendMessage(String msg) {
        TestMessage testMessage = new TestMessage();
        testMessage.setContent(msg);
        testMessage.setExtra(mOrderId);
        final Message message = Message.obtain(otherId, Conversation.ConversationType.PRIVATE, testMessage);
        RongIM.getInstance().sendMessage(message, "系统消息", "系统消息", new IRongCallback.ISendMessageCallback() {
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
    }*/
}