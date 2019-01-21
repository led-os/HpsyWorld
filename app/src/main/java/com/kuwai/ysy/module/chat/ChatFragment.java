package com.kuwai.ysy.module.chat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.chat.bean.MyFriends;
import com.kuwai.ysy.module.chat.bean.UserInfoBean;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.StatusBartext;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import io.reactivex.functions.Consumer;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.model.GroupUserInfo;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;


public class ChatFragment extends BaseFragment implements View.OnClickListener{

    private ImageView imgChat;
    private TextView mNoticeTv;
    private UserInfo userInfo;

    public static ChatFragment newInstance() {
        Bundle args = new Bundle();
        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_conversationlist;
    }

    private void getConversation() {

        //会话列表
        ConversationListFragment conversationListFragment = new ConversationListFragment();
        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话，该会话聚合显示
//                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
//                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
//                .appendQueryParameter(Conversation.ConversationType.PUBLIC_SERVICE.getName(), "false")
                //.appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话，该会话非聚合显示
                .build();
        conversationListFragment.setUri(uri);

        /*FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.conversationlist, conversationListFragment);
        transaction.commit();*/

        /*RongIM.setGroupUserInfoProvider(new RongIM.GroupUserInfoProvider() {
            @Override
            public GroupUserInfo getGroupUserInfo(String s, String s1) {
                //GroupUserInfo userId = new GroupUserInfo();
                return userId;
            }
        });*/
    }

/*    private UserInfo findUserById(String userId) {
        addSubscription(ChatApiFactory.getUserInfo(userId).subscribe(new Consumer<UserInfoBean>() {
            @Override
            public void accept(UserInfoBean userInfoBean) throws Exception {
                if (userInfoBean.getCode() == 200) {
                    userInfo = new UserInfo(String.valueOf(userInfoBean.getData().getUid()),userInfoBean.getData().getNickname(), Uri.parse(userInfoBean.getData().getAvatar()));
                    RongIM.getInstance().refreshUserInfoCache(userInfo);
                } else {
                    //ToastUtils.showShort(myBlindBean.getMsg());
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                ToastUtils.showShort("网络错误");
            }
        }));
        return userInfo;
    }*/

    @Override
    public void initView(Bundle savedInstanceState) {
        mNoticeTv = mRootView.findViewById(R.id.tv_notice);
        imgChat = mRootView.findViewById(R.id.chat);
        imgChat.setOnClickListener(this);
        mNoticeTv.setOnClickListener(this);
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        StatusBartext.StatusBarLightMode(getActivity());
        getConversation();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_notice:
                //RongIM.getInstance().setMessageAttachedUserInfo(true);
                RongIM.getInstance().startPrivateChat(getActivity(), "2", "交易进行中");
                break;
            case R.id.chat:
                startActivity(new Intent(getActivity(),MyFriendActivity.class));
                break;
        }
    }
}
