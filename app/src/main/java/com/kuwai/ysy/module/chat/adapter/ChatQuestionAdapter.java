package com.kuwai.ysy.module.chat.adapter;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.callback.QuestionCallback;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.chat.bean.ChatQuestion;
import com.kuwai.ysy.module.chat.bean.PhotoBook;
import com.kuwai.ysy.rong.RedReceiveMessage;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.CircleTextView;
import com.kuwai.ysy.widget.QuestionLayout;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.List;

import io.reactivex.functions.Consumer;
import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;


public class ChatQuestionAdapter extends BaseQuickAdapter<ChatQuestion.DataBean, BaseViewHolder> {
    private String targetId;
    private QuestionCallback questionCallback;
    private LinearLayout parent;

    public ChatQuestionAdapter(String id) {
        super(R.layout.item_chat_ques);
        targetId = id;
    }

    public void setCallBack(QuestionCallback callBack){
        this.questionCallback = callBack;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ChatQuestion.DataBean item) {
        //0在线   1离线
        final QuestionLayout layout = helper.getView(R.id.question1);
        layout.setTitle(item.getProblem());
        parent = helper.getView(R.id.parent);
        //layout.setContent(Utils.isNullString(item.getAnswer()) ? "" : item.getAnswer());
        if (item.getIs_answer() == 0) {
            parent.setVisibility(View.VISIBLE);
        } else {
            parent.setVisibility(View.GONE);
        }
        layout.setContent("");
        layout.setSendClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questionCallback!=null){
                    questionCallback.quesClick(item.getP_id(),layout.getContent());
                }
                //sendChatAnswer(item.getP_id(), layout.getContent());
            }
        });
        //GlideUtil.load(mContext, item.getBgPicture(), ((ImageView) helper.getView(R.id.iv_category)));
        //helper.addOnClickListener(R.id.fbl_item_team);
    }

    void sendChatAnswer(int pid, final String answer) {
        ChatApiFactory.sendChatAnswer(SPManager.get().getStringValue("uid"), targetId, pid, answer).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse question) throws Exception {
                if (question.code == 200) {
                    sendMessage(answer);
                } else {
                    ToastUtils.showShort(question.msg);
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

    private void sendMessage(String answer) {
        TextMessage testMessage = TextMessage.obtain(answer);
        final Message message = Message.obtain(targetId, Conversation.ConversationType.PRIVATE, testMessage);
        RongIM.getInstance().sendMessage(message, "消息", "消息", new IRongCallback.ISendMessageCallback() {
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
}
