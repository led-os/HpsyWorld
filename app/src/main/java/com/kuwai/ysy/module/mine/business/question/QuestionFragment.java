package com.kuwai.ysy.module.mine.business.question;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.find.adapter.CustomThemeAdapter;
import com.kuwai.ysy.module.find.bean.theme.DateTheme;
import com.kuwai.ysy.module.mine.adapter.QuestionAdapter;
import com.kuwai.ysy.module.mine.bean.MyAskBean;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyEditText;
import com.kuwai.ysy.widget.NavigationLayout;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.dialoglib.DialogInterface;
import com.rayhahah.dialoglib.NormalAlertDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

public class QuestionFragment extends BaseFragment<AskPresenter> implements AskContract.IHomeView, View.OnClickListener {

    private SuperButton btn_add_question;
    private CustomDialog askDialog, answerDialog;
    private QuestionAdapter questionAdapter;
    private RecyclerView mQuestionList;
    private CustomDialog moreDialog;

    private MyAskBean mMyaskBean = null;

    public static QuestionFragment newInstance() {
        Bundle args = new Bundle();
        QuestionFragment fragment = new QuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_my_ask;
    }

    @Override
    protected AskPresenter getPresenter() {
        return new AskPresenter(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_question:
                popQuestion("", 0);
                break;
        }
    }

    void popDelete(final int position) {
        new NormalAlertDialog.Builder(getActivity())
                .setTitleVisible(false)
                .setContentText("确定删除这条问题？")
                .setLeftButtonText("确定")
                .setRightButtonText("取消")
                .setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<NormalAlertDialog>() {
                    @Override
                    public void clickLeftButton(NormalAlertDialog dialog, View view) {
                        mPresenter.sendDelAsk(SPManager.get().getStringValue("uid"), mMyaskBean.getData().get(position).getP_id());
                        dialog.dismiss();
                    }

                    @Override
                    public void clickRightButton(NormalAlertDialog dialog, View view) {
                        dialog.dismiss();
                    }
                })
                .setCanceledOnTouchOutside(true)
                .build().show();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        btn_add_question = mRootView.findViewById(R.id.btn_add_question);
        mQuestionList = mRootView.findViewById(R.id.rl_question);
        ((NavigationLayout) mRootView.findViewById(R.id.navigation)).setLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getActivity().finish();
            }
        });

        btn_add_question.setOnClickListener(this);

        questionAdapter = new QuestionAdapter();
        mQuestionList.setAdapter(questionAdapter);
        questionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.btn_add_answer:
                        popAnswer("", position);
                        break;
                    case R.id.img_arrow:
                        showMore(position);
                        break;
                }
            }
        });
    }

    private void showMore(final int position) {
        View pannel = View.inflate(getActivity(), R.layout.dialog_question_more, null);
        TextView delete = pannel.findViewById(R.id.tv_delete);
        TextView change_question = pannel.findViewById(R.id.tv_change_question);
        TextView change_answer = pannel.findViewById(R.id.tv_change_answer);

        change_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreDialog.dismiss();
                popAnswer(mMyaskBean.getData().get(position).getAnswer(), position);
            }
        });

        change_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreDialog.dismiss();
                popQuestion(mMyaskBean.getData().get(position).getProblem(), position);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreDialog.dismiss();
                popDelete(position);
            }
        });

        if (moreDialog == null) {
            moreDialog = new CustomDialog.Builder(getActivity())
                    .setView(pannel)
                    .setTouchOutside(true)
                    .setDialogGravity(Gravity.CENTER)
                    .build();
        }
        moreDialog.show();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestHomeData(SPManager.get().getStringValue("uid"));
    }

    private void popQuestion(final String question, final int position) {

        View pannel = View.inflate(getActivity(), R.layout.dialog_question, null);
        ImageView imageDel = pannel.findViewById(R.id.top_del);
        SuperButton submit = pannel.findViewById(R.id.submit);
        TextView title = pannel.findViewById(R.id.top);
        final EditText etContent = pannel.findViewById(R.id.dialog_comment_et);
        if (!Utils.isNullString(question)) {
            etContent.setText(question);
            title.setText("修改问题");
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askDialog.dismiss();
                if (!Utils.isNullString(question)) {
                    MyAskBean.DataBean data = mMyaskBean.getData().get(position);
                    mPresenter.getUpdateProblem(SPManager.get().getStringValue("uid"), data.getP_id(), etContent.getText().toString(), "");
                } else {
                    mPresenter.getAddAsk(SPManager.get().getStringValue("uid"), etContent.getText().toString());
                }

            }
        });

        imageDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (askDialog != null) {
                    askDialog.dismiss();
                }
            }
        });

        askDialog = new CustomDialog.Builder(getActivity())
                .setView(pannel)
                .setTouchOutside(true)
                .setDialogGravity(Gravity.CENTER)
                .build();
        askDialog.show();
    }

    private void popAnswer(final String answer, final int position) {
            View pannel = View.inflate(getActivity(), R.layout.dialog_question, null);
            ImageView imageDel = pannel.findViewById(R.id.top_del);
            TextView title = pannel.findViewById(R.id.top);
            SuperButton submit = pannel.findViewById(R.id.submit);
            title.setText("添加回答");
            final EditText etContent = pannel.findViewById(R.id.dialog_comment_et);

            etContent.setHint("请输入您的回答内容...");
            if (!Utils.isNullString(answer)) {
                etContent.setText(answer);
                title.setText("修改回答");
            }

            imageDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (answerDialog != null) {
                        answerDialog.dismiss();
                    }
                }
            });

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    answerDialog.dismiss();
                    MyAskBean.DataBean data = mMyaskBean.getData().get(position);
                    mPresenter.getUpdateProblem(SPManager.get().getStringValue("uid"), data.getP_id(), "", etContent.getText().toString());
                }
            });

            answerDialog = new CustomDialog.Builder(getActivity())
                    .setView(pannel)
                    .setTouchOutside(true)
                    .setDialogGravity(Gravity.CENTER)
                    .build();
        answerDialog.show();
    }

    @Override
    public void setHomeData(MyAskBean askBean) {
        mMyaskBean = askBean;
        if (mMyaskBean.getData().size() > 2) {
            btn_add_question.setVisibility(View.GONE);
        }
        questionAdapter.replaceData(askBean.getData());
    }

    @Override
    public void delAsk(SimpleResponse simpleResponse) {
        ToastUtils.showShort(simpleResponse.msg);
        if (simpleResponse.code == 200) {
            mPresenter.requestHomeData(SPManager.get().getStringValue("uid"));
        }
    }

    @Override
    public void UpdateProblem(SimpleResponse simpleResponse) {
        ToastUtils.showShort(simpleResponse.msg);
        if (simpleResponse.code == 200) {
            mPresenter.requestHomeData(SPManager.get().getStringValue("uid"));
        }
    }

    @Override
    public void setAddAsk(SimpleResponse simpleResponse) {
        ToastUtils.showShort(simpleResponse.msg);
        if (simpleResponse.code == 200) {
            mPresenter.requestHomeData(SPManager.get().getStringValue("uid"));
        }
    }

    @Override
    public void showError(int errorCode, String msg) {

    }

    @Override
    public void showViewLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showViewError(Throwable t) {

    }
}
