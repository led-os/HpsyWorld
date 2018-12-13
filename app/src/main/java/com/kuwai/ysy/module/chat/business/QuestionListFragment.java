package com.kuwai.ysy.module.chat.business;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.widget.QuestionLayout;
import com.rayhahah.rbase.base.RBasePresenter;

public class QuestionListFragment extends BaseFragment implements View.OnClickListener {

    private QuestionLayout questionLayout1, questionLayout2, questionLayout3;

    public static QuestionListFragment newInstance() {
        Bundle args = new Bundle();
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
        questionLayout1 = mRootView.findViewById(R.id.question1);
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
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
