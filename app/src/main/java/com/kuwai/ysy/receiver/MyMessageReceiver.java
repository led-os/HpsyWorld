package com.kuwai.ysy.receiver;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.gson.Gson;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.module.chat.business.redpack.SendRedActivity;
import com.kuwai.ysy.module.home.WebviewH5Activity;
import com.kuwai.ysy.utils.Utils;

import io.rong.push.PushType;
import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

public class MyMessageReceiver extends PushMessageReceiver {

    @Override
    public boolean onNotificationMessageArrived(Context context, PushType pushType, PushNotificationMessage pushNotificationMessage) {
        return false;
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushType pushType, PushNotificationMessage pushNotificationMessage) {
        String jsonStr = pushNotificationMessage.getExtra();
        Gson gson = new Gson();
        PushContent p = gson.fromJson(jsonStr, PushContent.class);
        if (!Utils.isNullString(p.getUrl())) {
            Intent intent = new Intent(context, WebviewH5Activity.class);
            intent.putExtra(C.H5_FLAG, p.getUrl());
            context.startActivity(intent);
        }
        return true;
    }
}
