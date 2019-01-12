package com.kuwai.ysy.rong;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;

import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.callback.GiftClickCallback;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.find.api.AppointApiFactory;
import com.kuwai.ysy.module.find.bean.GiftPopBean;
import com.kuwai.ysy.widget.GiftPannelView;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;
import io.rong.imkit.RongExtension;
import io.rong.imkit.RongIM;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.LocationMessage;

public class LocationPlugin implements IPluginModule, GiftClickCallback {

    private CustomDialog customDialog;
    private String targetId;

    @Override
    public Drawable obtainDrawable(Context context) {
        return context.getResources().getDrawable(R.drawable.chat_cd_input_ic_pos);
    }

    @Override
    public String obtainTitle(Context context) {
        return "定位";
    }

    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {
        sendMessage();
        rongExtension.collapseExtension();
        targetId = rongExtension.getTargetId();
        //ToastUtil.getInstance()._short(fragment.getActivity(), "萨顶顶发红包了");
    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {

    }

    private void sendMessage() {
        LocationMessage testMessage = LocationMessage.obtain(1, 1, "李四", Uri.parse(SPManager.get().getStringValue("icon")));
        final Message message = Message.obtain(targetId, Conversation.ConversationType.PRIVATE, testMessage);
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
    }

    @Override
    public void giftClick(GiftPopBean.DataBean giftBean) {
        customDialog.dismiss();
    }
}
