package com.kuwai.ysy.module.chat.business;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.callback.QuestionCallback;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.chat.adapter.ChatQuestionAdapter;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.chat.bean.ChatQuestion;
import com.kuwai.ysy.module.chat.bean.ReceiveBean;
import com.kuwai.ysy.module.chat.business.redpack.RedReceiveActivity;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.NavigationLayout;
import com.kuwai.ysy.widget.QuestionLayout;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

public class QuestionListFragment extends BaseFragment implements View.OnClickListener {

    private QuestionLayout questionLayout1, questionLayout2, questionLayout3;
    private String taretId;
    private ChatQuestionAdapter questionAdapter;
    private RecyclerView rlQuestion;
    private List<ChatQuestion.DataBean> mdataList = new ArrayList<>();

    public static QuestionListFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString("id", id);
        QuestionListFragment fragment = new QuestionListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.act_question_list;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        ((NavigationLayout) mRootView.findViewById(R.id.navigation)).setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        rlQuestion = mRootView.findViewById(R.id.rl_question);
        taretId = getArguments().getString("id");
        questionAdapter = new ChatQuestionAdapter(taretId);
        rlQuestion.setAdapter(questionAdapter);

        questionAdapter.setCallBack(new QuestionCallback() {
            @Override
            public void quesClick(int pos, String answer) {
                sendChatAnswer(pos, answer);
            }
        });
       /* questionLayout1 = mRootView.findViewById(R.id.question1);
        questionLayout2 = mRootView.findViewById(R.id.question2);
        questionLayout3 = mRootView.findViewById(R.id.question3);
        questionLayout1.hasAnswerd(true);
        questionLayout2.hasAnswerd(false);
        questionLayout3.hasAnswerd(false);
        questionLayout1.setArrowClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionLayout1.getIshShow()) {
                    questionLayout1.hideContent();
                } else {
                    questionLayout1.showContent();
                }
                questionLayout2.hideContent();
                questionLayout3.hideContent();
            }
        });
        questionLayout2.setArrowClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionLayout2.getIshShow()) {
                    questionLayout2.hideContent();
                } else {
                    questionLayout2.showContent();
                }
                questionLayout1.hideContent();
                questionLayout3.hideContent();
            }
        });
        questionLayout3.setArrowClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionLayout3.getIshShow()) {
                    questionLayout3.hideContent();
                } else {
                    questionLayout3.showContent();
                }
                questionLayout1.hideContent();
                questionLayout2.hideContent();
            }
        });*/
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        getQuestion();
    }

    void getQuestion() {
        addSubscription(ChatApiFactory.getChatQuestion(SPManager.get().getStringValue("uid"), taretId).subscribe(new Consumer<ChatQuestion>() {
            @Override
            public void accept(ChatQuestion question) throws Exception {
                if (question.getCode() == 200) {
                    mdataList.clear();
                    for (int i = 0; i < question.getData().size(); i++) {
                        if (question.getData().get(i).getIs_answer() == 0) {
                            mdataList.add(question.getData().get(i));
                        }
                    }
                    questionAdapter.replaceData(mdataList);
                } else {
                    ToastUtils.showShort(question.getMsg());
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: "+throwable);
                //ToastUtils.showShort("网络错误");
            }
        }));
    }

    void sendChatAnswer(int pid, final String answer) {
        ChatApiFactory.sendChatAnswer(SPManager.get().getStringValue("uid"), taretId, pid, answer).subscribe(new Consumer<SimpleResponse>() {
            @Override
            public void accept(SimpleResponse question) throws Exception {
                if (question.code == 200) {
                    Utils.showOrHide(getActivity());
                    getActivity().finish();
                    sendMessage(answer);
                } else {
                   // ToastUtils.showShort(question.msg);
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
        final Message message = Message.obtain(taretId, Conversation.ConversationType.PRIVATE, testMessage);
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
