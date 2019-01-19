package com.kuwai.ysy.receiver;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.gson.Gson;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.module.chat.MyFriendActivity;
import com.kuwai.ysy.module.chat.business.redpack.SendRedActivity;
import com.kuwai.ysy.module.home.WebviewH5Activity;
import com.kuwai.ysy.module.home.business.HomeActivity;
import com.kuwai.ysy.module.mine.business.like.StLikeActivity;
import com.kuwai.ysy.module.mine.business.visitor.VisitorActivity;
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

        if (p.getType() == 1) {
            if (!Utils.isNullString(p.getUrl())) {
                Intent intent = new Intent(context, WebviewH5Activity.class);
                intent.putExtra(C.H5_FLAG, p.getUrl());
                context.startActivity(intent);
            }
        } else if (p.getType() == 4) {
            Intent intent = new Intent(context, VisitorActivity.class);
            context.startActivity(intent);
        } else if (p.getType() == 5) {
            Intent intent = new Intent(context, StLikeActivity.class);
            context.startActivity(intent);
        } else if (p.getType() == 6) {

        } else if (p.getType() == 7) {
            Intent intent = new Intent(context, HomeActivity.class);
            context.startActivity(intent);
        }else if(p.getType() == 8){
            Intent intent = new Intent(context, MyFriendActivity.class);
            context.startActivity(intent);
        }
        return true;
    }
}
