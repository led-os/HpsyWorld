package com.kuwai.ysy.receiver;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

public class MyMessageReceiver extends PushMessageReceiver {
    @Override
    public boolean onNotificationMessageArrived(Context context, PushNotificationMessage pushNotificationMessage) {
        //消息到达客户端时触发
        return false;
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushNotificationMessage pushNotificationMessage) {
        //用户点击通知栏消息时触发  如果需要自定义点击通知时的跳转，return true即可
        /*Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri.Builder builder = Uri.parse("rong://" + this.getPackageName()).buildUpon();

        builder.appendPath("conversation").appendPath(type.getName())
                .appendQueryParameter("targetId", targetId)
                .appendQueryParameter("title", targetName);
        uri = builder.build();
        intent.setData(uri);
        startActivity(intent);*/
        return false;
    }
}
