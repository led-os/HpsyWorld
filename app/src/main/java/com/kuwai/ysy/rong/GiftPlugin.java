package com.kuwai.ysy.rong;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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

public class GiftPlugin implements IPluginModule, GiftClickCallback {

    private CustomDialog customDialog;
    private String targetId;

    @Override
    public Drawable obtainDrawable(Context context) {
        return context.getResources().getDrawable(R.drawable.chat_cd_input_ic_gift);
    }

    @Override
    public String obtainTitle(Context context) {
        return "礼物";
    }

    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {
        createDialog(fragment.getActivity());
        rongExtension.collapseExtension();
        targetId = rongExtension.getTargetId();
        //ToastUtil.getInstance()._short(fragment.getActivity(), "萨顶顶发红包了");
    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {

    }

    private void createDialog(final Context context) {
        AppointApiFactory.getAllGifts()
                .subscribe(new Consumer<GiftPopBean>() {
                    @Override
                    public void accept(@NonNull GiftPopBean dateTheme) throws Exception {
                        GiftPannelView pannelView = new GiftPannelView(context);
                        pannelView.setData(dateTheme.getData(), context);
                        pannelView.setGiftClickCallBack(GiftPlugin.this);
                        customDialog = new CustomDialog.Builder(context)
                                .setView(pannelView)
                                .setTouchOutside(true)
                                .setDialogGravity(Gravity.BOTTOM)
                                .build();
                        customDialog.show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        //mView.showViewError(throwable);
                    }
                });
        //View root = View.inflate(context, R.layout.dialog_gift, null);
    }

    private void sendMessage(GiftPopBean.DataBean gift) {
        GiftSendMessage testMessage = new GiftSendMessage();
        testMessage.setContent(gift.getGirft_img_url());
        testMessage.setExtra(String.valueOf(gift.num));
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
        giftReward(giftBean);
    }

    private void giftReward(final GiftPopBean.DataBean gift) {
        Map<String, Object> param = new HashMap<>();
        param.put("uid", SPManager.get().getStringValue("uid"));
        param.put("other_uid", targetId);
        param.put("g_id", gift.getG_id());
        param.put("g_nums", gift.num);
        param.put("message", "");
        ChatApiFactory.rewardPe(param)
                .subscribe(new Consumer<SimpleResponse>() {
                    @Override
                    public void accept(@NonNull SimpleResponse dateTheme) throws Exception {
                        if (dateTheme.code == 200) {
                            sendMessage(gift);
                        }
                        ToastUtils.showShort(dateTheme.msg);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        //mView.showViewError(throwable);
                    }
                });
    }
}
