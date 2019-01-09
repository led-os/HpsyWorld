package com.kuwai.ysy.module.chat.business.redpack;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.library.CircleImageView;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.module.chat.adapter.RedRecordAdapter;
import com.kuwai.ysy.module.chat.api.ChatApiFactory;
import com.kuwai.ysy.module.chat.bean.MyFriends;
import com.kuwai.ysy.module.chat.bean.ReceiveBean;
import com.kuwai.ysy.module.chat.bean.RedRecordBean;
import com.kuwai.ysy.widget.CustomFontTextview;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;
import com.rayhahah.rbase.utils.useful.GlideUtil;
import com.rayhahah.rbase.utils.useful.SPManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class RedRecordActivity extends BaseActivity implements View.OnClickListener {

    private TextView mLeftTxt;
    private TextView mTitle;
    private ImageView mRight;
    private CircleImageView mImgHead;
    private TextView mTvName;
    private CustomFontTextview mTvMoney;
    private TextView mTvNum;
    private RecyclerView mRlRecord;
    private LinearLayout mNumLay;

    private RedRecordAdapter recordAdapter;
    private CustomDialog bottomDialog;

    private SmartRefreshLayout mRefreshLayout;
    private int mPage = 1;
    private String uid;
    private String mType = "2"; //1 发出的红包  2 收到的

    @Override
    protected RBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.act_red_record;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        mLeftTxt = findViewById(R.id.left_txt);
        mTitle = findViewById(R.id.title);
        mRight = findViewById(R.id.right);
        mRight.setOnClickListener(this);
        mImgHead = findViewById(R.id.img_head);
        mTvName = findViewById(R.id.tv_name);
        mTvMoney = findViewById(R.id.tv_money);
        mTvNum = findViewById(R.id.tv_num);
        mNumLay = findViewById(R.id.lay_send);
        mRlRecord = findViewById(R.id.rl_record);
        uid = SPManager.get().getStringValue("uid");

        mRefreshLayout = findViewById(R.id.mRefreshLayout);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                getRedList(mType);
            }
        });

        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getMore(mType);
            }
        });

        recordAdapter = new RedRecordAdapter();
        mRlRecord.setAdapter(recordAdapter);

        getRedList(mType);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_txt:
                finish();
                break;
            case R.id.right:
                popBottom();
                break;
        }
    }

    private void popBottom() {
        if (bottomDialog == null) {

            View pannel = View.inflate(this, R.layout.dialog_red_more, null);
            TextView imageDel = pannel.findViewById(R.id.top_del);
            TextView record = pannel.findViewById(R.id.tv_record);
            TextView help = pannel.findViewById(R.id.tv_help);
            record.setText("收到的红包");
            help.setText("发出的红包");

            record.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mType = "2";
                    mRefreshLayout.autoRefresh();
                    bottomDialog.dismiss();
                }
            });

            help.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mType = "1";
                    mRefreshLayout.autoRefresh();
                    bottomDialog.dismiss();
                }
            });

            imageDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bottomDialog != null) {
                        bottomDialog.dismiss();
                    }
                }
            });

            bottomDialog = new CustomDialog.Builder(this)
                    .setView(pannel)
                    .setTouchOutside(true)
                    .setItemHeight(0.4f)
                    .setDialogGravity(Gravity.BOTTOM)
                    .build();
        }
        bottomDialog.show();
    }

    void getRedList(final String type) {
        addSubscription(ChatApiFactory.redList(uid, type, mPage).subscribe(new Consumer<RedRecordBean>() {
            @Override
            public void accept(RedRecordBean recordBean) throws Exception {
                mRefreshLayout.finishRefresh();
                if (recordBean.getCode() == 200) {
                    recordAdapter.replaceData(recordBean.getData().getArr());
                    GlideUtil.load(RedRecordActivity.this, recordBean.getData().getAvatar(), mImgHead);
                    if ("2".equals(type)) {
                        mNumLay.setVisibility(View.GONE);
                        mTitle.setText("收到的红包");
                        mTvName.setText(recordBean.getData().getNickname() + "共收到");
                        mTvMoney.setText(recordBean.getData().getMoney_sum());
                    } else {
                        mTitle.setText("发出的红包");
                        mTvName.setText(recordBean.getData().getNickname() + "共发出");
                        mTvMoney.setText(recordBean.getData().getMoney_sum());
                        mNumLay.setVisibility(View.VISIBLE);
                        mTvNum.setText(recordBean.getData().getNumber() + "");
                    }

                } else {
                    ToastUtils.showShort(recordBean.getMsg());
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

    private void getMore(String type) {
        addSubscription(ChatApiFactory.redList(uid, type, mPage + 1).subscribe(new Consumer<RedRecordBean>() {
            @Override
            public void accept(RedRecordBean recordBean) throws Exception {
                if (recordBean.getData().getArr() != null && recordBean.getData().getArr().size() > 0) {
                    mPage++;
                }
                mRefreshLayout.finishLoadmore();
                recordAdapter.addData(recordBean.getData().getArr());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                //Log.i(TAG, "accept: " + throwable);
            }
        }));
    }
}
