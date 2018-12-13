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
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.StatusBartext;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;


public class ChatFragment extends BaseFragment implements View.OnClickListener{

    private ImageView imgChat;
    private TextView mNoticeTv;

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
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")//设置系统会话，该会话非聚合显示
                .build();
        conversationListFragment.setUri(uri);

        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.conversationlist, conversationListFragment);
        transaction.commit();

    }

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
                startActivity(new Intent(getActivity(),MyFriendActivity.class));
                break;
            case R.id.chat:
                RongIM.getInstance().startPrivateChat(getActivity(), "2", "交易进行中");
                break;
        }
    }
}
