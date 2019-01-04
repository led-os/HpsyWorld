package com.kuwai.ysy.rong;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.kuwai.ysy.R;
import com.kuwai.ysy.listener.OnRedPacketDialogClickListener;
import com.kuwai.ysy.rong.bean.RedPacketEntity;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.utils.base.ToastUtils;

import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.emoticon.AndroidEmoji;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.utilities.OptionsPopupDialog;
import io.rong.imkit.widget.provider.IContainerItemProvider;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

@ProviderTag(
        messageContent = QuestionMessage.class,
        showPortrait = false,
        showProgress = false,
        showWarning = false,
        centerInHorizontal = true,
        showSummaryWithName = false
)
public class QuestionMessageItemProvider extends IContainerItemProvider.MessageProvider<QuestionMessage> {
    private static final String TAG = "QuestionMessageItemProvider";

    private View mRedPacketDialogView;
    private RedPacketViewHolder mRedPacketViewHolder;
    private CustomDialog mRedPacketDialog;

    private static class ViewHolder {
        TextView message;
        boolean longClick;
    }

    @Override
    public View newView(Context context, ViewGroup group) {
        View view = LayoutInflater.from(context).inflate(R.layout.rong_question, null);

        QuestionMessageItemProvider.ViewHolder holder = new QuestionMessageItemProvider.ViewHolder();
        //holder.message = (TextView) view.findViewById(R.id.message);
        view.setTag(holder);
        return view;
    }

    @Override
    public Spannable getContentSummary(QuestionMessage data) {
        return null;
    }

    @Override
    public Spannable getContentSummary(Context context, QuestionMessage data) {
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
    public void onItemClick(View view, int position, QuestionMessage content, UIMessage message) {
        //RedPacketEntity entity = new RedPacketEntity("萨顶顶", "http://192.168.1.88/public/static/img/avatar/201812/19/1159e6106c38a19e6dd82d12de770cb5.jpg", "大吉大利，今晚吃鸡");
        //showRedPacketDialog(entity, view.getContext());
        //view.getContext().startActivity(new Intent(view.getContext(), SendRedActivity.class));
        //view.getContext().startActivity(new Intent(view.getContext(), QuestionActivity.class));
    }

    public void showRedPacketDialog(RedPacketEntity entity, Context mContext) {
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
                ToastUtils.showShort("萨顶顶中了五百万大奖");
                mRedPacketDialog.dismiss();
            }
        });

        mRedPacketDialog.show();
    }

    @Override
    public void onItemLongClick(final View view, int position, final QuestionMessage content, final UIMessage message) {

        QuestionMessageItemProvider.ViewHolder holder = (QuestionMessageItemProvider.ViewHolder) view.getTag();
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
                    clipboard.setText(((QuestionMessage) content).getContent());
                } else if (which == 1) {
                    RongIM.getInstance().deleteMessages(new int[]{message.getMessageId()}, null);
                } else if (which == 2) {
                    RongIM.getInstance().recallMessage(message.getMessage(), getPushContent(view.getContext(), message));
                }
            }
        }).show();
    }

    @Override
    public void bindView(final View v, int position, final QuestionMessage content, final UIMessage data) {
        QuestionMessageItemProvider.ViewHolder holder = (QuestionMessageItemProvider.ViewHolder) v.getTag();

        /*if (data.getMessageDirection() == Message.MessageDirection.SEND) {
            holder.message.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_right);
        } else {
            holder.message.setBackgroundResource(io.rong.imkit.R.drawable.rc_ic_bubble_left);
        }*/

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