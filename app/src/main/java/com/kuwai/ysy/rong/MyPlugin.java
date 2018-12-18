package com.kuwai.ysy.rong;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.Model;
import com.kuwai.ysy.callback.GiftClickCallback;
import com.kuwai.ysy.module.chat.adapter.GridViewAdapter;
import com.kuwai.ysy.module.chat.adapter.ViewPagerAdapter;
import com.kuwai.ysy.widget.GiftPannelView;
import com.rayhahah.dialoglib.CustomDialog;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.MainActivity;
import io.rong.imkit.RongExtension;
import io.rong.imkit.RongIM;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

public class MyPlugin implements IPluginModule, GiftClickCallback {

    private CustomDialog customDialog;

    @Override
    public Drawable obtainDrawable(Context context) {
        return context.getResources().getDrawable(R.drawable.gift4);
    }

    @Override
    public String obtainTitle(Context context) {
        return "鱼水缘";
    }

    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {
        customDialog = createDialog(fragment.getActivity());
        customDialog.show();
        rongExtension.collapseExtension();
        //ToastUtil.getInstance()._short(fragment.getActivity(), "萨顶顶发红包了");
    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {

    }

    private CustomDialog createDialog(Context context) {
        //View root = View.inflate(context, R.layout.dialog_gift, null);
        GiftPannelView pannelView = new GiftPannelView(context);
        pannelView.setGiftClickCallBack(this);
        CustomDialog customDialog = new CustomDialog.Builder(context)
                .setView(pannelView)
                .setTouchOutside(true)
                .setItemHeight(0.4f)
                .setDialogGravity(Gravity.BOTTOM)
                .build();
        return customDialog;
    }

    private void sendMessage(String msg) {
        QuestionMessage testMessage = new QuestionMessage();
        testMessage.setContent(msg);
        final Message message = Message.obtain("2", Conversation.ConversationType.PRIVATE, testMessage);
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
    public void giftClick(int gid,int nums) {
        sendMessage("");
    }
}
