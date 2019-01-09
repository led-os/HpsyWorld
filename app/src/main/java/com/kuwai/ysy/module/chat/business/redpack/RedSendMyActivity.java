package com.kuwai.ysy.module.chat.business.redpack;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.allen.library.CircleImageView;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.module.chat.adapter.RedRecordAdapter;
import com.kuwai.ysy.module.chat.adapter.RenSendMineAdapter;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.chat.bean.ReceiveBean;
import com.kuwai.ysy.module.chat.bean.RedMySendBean;
import com.kuwai.ysy.widget.CustomFontTextview;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;

import io.reactivex.functions.Consumer;

public class RedSendMyActivity extends BaseActivity implements View.OnClickListener {

    private TextView mLeftTxt;
    private TextView mTitle;
    private TextView mRight;
    private CircleImageView mImgHead;
    private TextView mTvName;
    private TextView mTvJiyu;
    private TextView mTvTitle;
    private RecyclerView mRlRedMySend;
    private RenSendMineAdapter recordAdapter;

    private CustomDialog bottomDialog;
    private String rid = "";

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.act_red_send;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {

        rid = getIntent().getStringExtra("rid");

        mLeftTxt = findViewById(R.id.left_txt);
        mTitle = findViewById(R.id.title);
        mRight = findViewById(R.id.right);
        mImgHead = findViewById(R.id.img_head);
        mTvName = findViewById(R.id.tv_name);
        mTvJiyu = findViewById(R.id.tv_jiyu);
        mTvTitle = findViewById(R.id.tv_title);
        mRlRedMySend = findViewById(R.id.rl_red_my_send);
        getRedDetail();
        mLeftTxt.setOnClickListener(this);
        mRight.setOnClickListener(this);

        recordAdapter = new RenSendMineAdapter();
        mRlRedMySend.setAdapter(recordAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right:
                openActivity(RedRecordActivity.class);
                break;
            case R.id.left_txt:
                finish();
                break;
        }
    }

    void getRedDetail() {
        addSubscription(ChatApiFactory.redDetailMine(SPManager.get().getStringValue("uid"), rid).subscribe(new Consumer<RedMySendBean>() {
            @Override
            public void accept(RedMySendBean receiveBean) throws Exception {
                if (receiveBean.getCode() == 200) {
                    GlideUtil.load(RedSendMyActivity.this, receiveBean.getData().getAvatar(), mImgHead);
                    mTvName.setText(receiveBean.getData().getNickname() + "的红包");
                    mTvJiyu.setText(receiveBean.getData().getMessage());
                    mTvTitle.setText("红包：" + receiveBean.getData().getMoney() + "桃花币");
                    recordAdapter.replaceData(receiveBean.getData().getArr());
                } else {
                    ToastUtils.showShort(receiveBean.getMsg());
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
}
