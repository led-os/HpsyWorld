package com.kuwai.ysy.rong;

import android.net.Uri;

import io.rong.imkit.model.ConversationProviderTag;
import io.rong.imkit.model.UIConversation;
import io.rong.imkit.userInfoCache.RongUserInfoManager;
import io.rong.imkit.widget.provider.GroupConversationProvider;
import io.rong.imkit.widget.provider.IContainerItemProvider;

@ConversationProviderTag(
        conversationType = "group",
        portraitPosition = 1
)
public class MyGroupConversationProvider extends GroupConversationProvider implements IContainerItemProvider.ConversationProvider<UIConversation> {
    public MyGroupConversationProvider() {
    }

    public String getTitle(String groupId) {
        String name;
        if (RongUserInfoManager.getInstance().getGroupInfo(groupId) == null) {
            name = "";
        } else {
            name = RongUserInfoManager.getInstance().getGroupInfo(groupId).getName();
        }

        return name;
    }

    public Uri getPortraitUri(String id) {
        Uri uri;
        if (RongUserInfoManager.getInstance().getGroupInfo(id) == null) {
            uri = null;
        } else {
            uri = null;
            //uri = RongUserInfoManager.getInstance().getGroupInfo(id).getPortraitUri();
        }

        return uri;
    }
}
