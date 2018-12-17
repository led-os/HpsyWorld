package com.kuwai.ysy.module.mine.business.question;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.find.adapter.CustomThemeAdapter;
import com.kuwai.ysy.module.find.bean.theme.DateTheme;
import com.kuwai.ysy.module.mine.bean.MyAskBean;
import com.kuwai.ysy.widget.MyEditText;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.dialoglib.DialogInterface;
import com.rayhahah.dialoglib.NormalAlertDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

public class QuestionFragment extends BaseFragment<AskPresenter> implements AskContract.IHomeView, View.OnClickListener {

    private SuperButton btn_add_question, btn_add_answer;
    private CustomDialog askDialog;

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
                popCustom();
                break;
            case R.id.btn_add_answer:
                new NormalAlertDialog.Builder(getActivity())
                        .setTitleVisible(false)
                        .setContentText("确定删除这条问题？")
                        .setLeftButtonText("确定")
                        .setRightButtonText("取消")
                        .setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<NormalAlertDialog>() {
                            @Override
                            public void clickLeftButton(NormalAlertDialog dialog, View view) {
                                ToastUtils.showShort("确定");
                            }

                            @Override
                            public void clickRightButton(NormalAlertDialog dialog, View view) {
                                dialog.dismiss();
                            }
                        })
                        .setCanceledOnTouchOutside(true)
                        .build().show();
                break;
        }
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        btn_add_question = mRootView.findViewById(R.id.btn_add_question);
        btn_add_question.setOnClickListener(this);
        btn_add_answer = mRootView.findViewById(R.id.btn_add_answer);
        btn_add_answer.setOnClickListener(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.requestHomeData(SPManager.getStringValue("uid","1"));
    }

    private void popCustom() {
        if (askDialog == null) {

            View pannel = View.inflate(getActivity(), R.layout.dialog_question, null);
            ImageView imageDel = pannel.findViewById(R.id.top_del);
            final EditText etContent = pannel.findViewById(R.id.dialog_comment_et);
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
        }
        askDialog.show();
    }

    @Override
    public void setHomeData(MyAskBean askBean) {

    }

    @Override
    public void delAsk(SimpleResponse simpleResponse) {

    }

    @Override
    public void UpdateProblem(SimpleResponse simpleResponse) {

    }

    @Override
    public void setAddAsk(SimpleResponse simpleResponse) {

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
