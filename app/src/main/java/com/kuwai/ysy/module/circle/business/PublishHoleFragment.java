package com.kuwai.ysy.module.circle.business;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseFragment;
import com.kuwai.ysy.widget.MyEditText;
import com.rayhahah.rbase.base.RBasePresenter;

public class PublishHoleFragment extends BaseFragment implements View.OnClickListener {

    private ImageView mLeftImg;
    private TextView mRightTxt;
    private MyEditText mEtTitle;
    private EditText mEtContent;
    private Switch mBtnComment;
    private Switch mBtnSiliao;

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

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mLeftImg = mRootView.findViewById(R.id.left_img);
        mRightTxt = mRootView.findViewById(R.id.right_txt);
        mEtTitle = mRootView.findViewById(R.id.et_title);
        mEtContent = mRootView.findViewById(R.id.et_content);
        mBtnComment = mRootView.findViewById(R.id.btn_comment);
        mBtnSiliao = mRootView.findViewById(R.id.btn_siliao);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
