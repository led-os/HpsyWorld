package com.kuwai.ysy.module.circle.business;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.bean.SimpleResponse;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.module.circle.api.CircleApiFactory;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.MyEditText;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.SPManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;

public class PublishHoleFragment extends BaseFragment implements View.OnClickListener {

    private ImageView mLeftImg;
    private TextView mRightTxt;
    private MyEditText mEtTitle;
    private EditText mEtContent;
    private Switch mBtnComment;
    private Switch mBtnSiliao;

    private int comment = 1;
    private int privateChat = 0;

    public static PublishHoleFragment newInstance() {
        Bundle args = new Bundle();
        PublishHoleFragment fragment = new PublishHoleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setFragmentLayoutRes() {
        return R.layout.fragment_publish_hole;
    }

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_txt:
                if (Utils.isNullString(mEtTitle.getText().toString())) {
                    ToastUtils.showShort("请输入标题");
                    return;
                } else if (Utils.isNullString(mEtContent.getText().toString())) {
                    ToastUtils.showShort("请输入树洞正文");
                    return;
                }
                Map<String, String> params = new HashMap<>();
                params.put("uid", SPManager.get().getStringValue("uid"));
                params.put("title", mEtTitle.getText().toString());
                params.put("text", mEtContent.getText().toString());
                params.put("open_comment", String.valueOf(comment));
                params.put("anonymous_chat", String.valueOf(privateChat));

                addSubscription(CircleApiFactory.publishHole(params).subscribe(new Consumer<SimpleResponse>() {
                    @Override
                    public void accept(SimpleResponse categoryBean) throws Exception {
                        if (categoryBean.code == 200) {
                            getActivity().finish();
                        }
                        ToastUtils.showShort(categoryBean.msg);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //mView.showError(0, "");
                        ToastUtils.showShort(throwable.toString());
                    }
                }));
                break;
        }

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mLeftImg = mRootView.findViewById(R.id.left_img);
        mRightTxt = mRootView.findViewById(R.id.right_txt);
        mEtTitle = mRootView.findViewById(R.id.et_title);
        mEtContent = mRootView.findViewById(R.id.et_content);
        mBtnComment = mRootView.findViewById(R.id.btn_comment);
        mBtnSiliao = mRootView.findViewById(R.id.btn_siliao);

        mRightTxt.setOnClickListener(this);

        mBtnComment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    comment = 1;
                } else {
                    comment = 0;
                }
            }
        });

        mBtnSiliao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    privateChat = 1;
                } else {
                    privateChat = 0;
                }
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
