package com.kuwai.ysy.rong;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.listener.OnRedPacketDialogClickListener;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.chat.business.redpack.RedReceiveActivity;
import com.kuwai.ysy.module.chat.business.redpack.RedSendMyActivity;
import com.kuwai.ysy.rong.bean.RedBean;
import com.kuwai.ysy.rong.bean.RedPacketEntity;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.emoticon.AndroidEmoji;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.utilities.OptionsPopupDialog;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;

@ProviderTag(
        messageContent = RedSendMessage.class,
        showProgress = false,
        showWarning = false
)
public class RedSendMessageItemProvider extends IContainerItemProvider.MessageProvider<RedSendMessage> {
    private static final String TAG = "QuestionMessageItemProvider";

    private View mRedPacketDialogView;
    private RedPacketViewHolder mRedPacketViewHolder;
    private CustomDialog mRedPacketDialog;
    private String targetId = "";

    private static class ViewHolder {
        TextView message;
        boolean longClick;
    }

    @Override
    public View newView(Context context, ViewGroup group) {
        View view = LayoutInflater.from(context).inflate(R.layout.rong_repack_mine, null);
        RedSendMessageItemProvider.ViewHolder holder = new RedSendMessageItemProvider.ViewHolder();
        holder.message = (TextView) view.findViewById(R.id.title);
        view.setTag(holder);
        return view;
    }

    @Override
    public Spannable getContentSummary(RedSendMessage data) {
        return null;
    }

    @Override
    public Spannable getContentSummary(Context context, RedSendMessage data) {
        if (data == null)
            return null;

        String content = data.getContent();
        if (content != null) {
            if (content.length() > 100) {
                content = content.substring(0, 100);
            }
            return new SpannableString(AndroidEmoji.ensure(content));
        }
        return null;
    }

    @Override
    public void onItemClick(View view, int position, RedSendMessage content, UIMessage message) {
        if (message.getMessageDirection() == Message.MessageDirection.RECEIVE) {
            targetId = message.getTargetId();
            if (message.getUserInfo() != null) {
                RedPacketEntity entity = new RedPacketEntity(message.getUserInfo().getName(), message.getUserInfo().getPortraitUri(), content.getContent());
                showRedPacketDialog(entity, view.getContext(), content.getExtra(), SPManager.get().getStringValue("uid"));
            }
        } else {
            Intent intent = new Intent(view.getContext(), RedSendMyActivity.class);
            intent.putExtra("rid", content.getExtra());
            view.getContext().startActivity(intent);
        }
    }

    public void showRedPacketDialog(final RedPacketEntity entity, final Context mContext, final String rid, final String uid) {
        if (mRedPacketDialogView == null) {
            mRedPacketDialogView = View.inflate(mContext, R.layout.dialog_red_packet, null);
            mRedPacketViewHolder = new RedPacketViewHolder(mContext, mRedPacketDialogView);
            if (mRedPacketDialog == null) {
                mRedPacketDialog = new CustomDialog.Builder(mContext)
                        .setView(mRedPacketDialogView)
                        .setTouchOutside(true)
                        .setDialogGravity(Gravity.CENTER)
                        .build();
            }
        }

        mRedPacketViewHolder.setData(entity);
        mRedPacketViewHolder.setOnRedPacketDialogClickListener(new OnRedPacketDialogClickListener() {
            @Override
            public void onCloseClick() {
                mRedPacketDialog.dismiss();
            }

            @Override
            public void onOpenClick() {
                //领取红包,调用接口
                receiveRed(rid, entity.remark, mContext);
            }
        });

        mRedPacketDialog.show();
    }

    @Override
    public void onItemLongClick(final View view, int position, final RedSendMessage content, final UIMessage message) {

        RedSendMessageItemProvider.ViewHolder holder = (RedSendMessageItemProvider.ViewHolder) view.getTag();
        holder.longClick = true;
        if (view instanceof TextView) {
            CharSequence text = ((TextView) view).getText();
            if (text != null && text instanceof Spannable)
                Selection.removeSelection((Spannable) text);
        }

        String[] items;

        long deltaTime = RongIM.getInstance().getDeltaTime();
        long normalTime = System.currentTimeMillis() - deltaTime;
        boolean enableMessageRecall = false;
        int messageRecallInterval = -1;
        boolean hasSent = (!message.getSentStatus().equals(Message.SentStatus.SENDING)) && (!message.getSentStatus().equals(Message.SentStatus.FAILED));

        try {
            enableMessageRecall = RongContext.getInstance().getResources().getBoolean(io.rong.imkit.R.bool.rc_enable_message_recall);
            messageRecallInterval = RongContext.getInstance().getResources().getInteger(io.rong.imkit.R.integer.rc_message_recall_interval);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        if (hasSent
                && enableMessageRecall
                && (normalTime - message.getSentTime()) <= messageRecallInterval * 1000
                && message.getSenderUserId().equals(RongIM.getInstance().getCurrentUserId())
                && !message.getConversationType().equals(Conversation.ConversationType.CUSTOMER_SERVICE)
                && !message.getConversationType().equals(Conversation.ConversationType.APP_PUBLIC_SERVICE)
                && !message.getConversationType().equals(Conversation.ConversationType.PUBLIC_SERVICE)
                && !message.getConversationType().equals(Conversation.ConversationType.SYSTEM)
                && !message.getConversationType().equals(Conversation.ConversationType.CHATROOM)) {
            items = new String[]{view.getContext().getResources().getString(io.rong.imkit.R.string.rc_dialog_item_message_copy), view.getContext().getResources().getString(io.rong.imkit.R.string.rc_dialog_item_message_delete), view.getContext().getResources().getString(io.rong.imkit.R.string.rc_dialog_item_message_recall)};
        } else {
            items = new String[]{view.getContext().getResources().getString(io.rong.imkit.R.string.rc_dialog_item_message_copy), view.getContext().getResources().getString(io.rong.imkit.R.string.rc_dialog_item_message_delete)};
        }

        OptionsPopupDialog.newInstance(view.getContext(), items).setOptionsPopupDialogListener(new OptionsPopupDialog.OnOptionsItemClickedListener() {
            @Override
            public void onOptionsItemClicked(int which) {
                if (which == 0) {
                    @SuppressWarnings("deprecation")
                    ClipboardManager clipboard = (ClipboardManager) view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboard.setText(((RedSendMessage) content).getContent());
                } else if (which == 1) {
                    RongIM.getInstance().deleteMessages(new int[]{message.getMessageId()}, null);
                } else if (which == 2) {
                    RongIM.getInstance().recallMessage(message.getMessage(), getPushContent(view.getContext(), message));
                }
            }
        }).show();
    }

    void receiveRed(final String rid, final String remark, final Context context) {
        ChatApiFactory.receiveRed(SPManager.get().getStringValue("uid"), rid).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse response) throws Exception {
                mRedPacketDialog.dismiss();
                if (response.code == 200) {
                    sendMessage(rid, remark);
                    Intent intent = new Intent(context, RedReceiveActivity.class);
                    intent.putExtra("rid", rid);
                    context.startActivity(intent);
                } else {
                    ToastUtils.showShort(response.msg);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
                //ToastUtils.showShort("网络错误");
            }
        });
    }

    private void sendMessage(String redid, String jiyu) {
        RedReceiveMessage testMessage = new RedReceiveMessage();
        testMessage.setContent(jiyu);
        testMessage.setExtra(redid);
        final Message message = Message.obtain(targetId, Conversation.ConversationType.PRIVATE, testMessage);
        RongIM.getInstance().sendMessage(message, "红包消息", "红包消息", new IRongCallback.ISendMessageCallback() {
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
    public void bindView(final View v, int position, final RedSendMessage content, final UIMessage data) {
        RedSendMessageItemProvider.ViewHolder holder = (RedSendMessageItemProvider.ViewHolder) v.getTag();

        if (data.getMessageDirection() == Message.MessageDirection.SEND) {
            holder.message.setText(content.getContent());
        } else {
            holder.message.setText(content.getContent());
        }

        /*final TextView textView = holder.message;
        textView.setText(content.getContent());

        holder.message.setMovementMethod(new LinkTextViewMovementMethod(new ILinkClickListener() {
            @Override
            public boolean onLinkClick(String link) {
                RongIM.ConversationBehaviorListener listener = RongContext.getInstance().getConversationBehaviorListener();
                boolean result = false;
                if (listener != null) {
                    result = listener.onMessageLinkClick(v.getContext(), link);
                }
                if (listener == null || !result) {
                    String str = link.toLowerCase();
                    if (str.startsWith("http") || str.startsWith("https")) {
                        Intent intent = new Intent(RongKitIntent.RONG_INTENT_ACTION_WEBVIEW);
                        intent.setPackage(v.getContext().getPackageName());
                        intent.putExtra("url", link);
                        v.getContext().startActivity(intent);
                        result = true;
                    }
                }
                return result;
            }
        }));*/
    }
}