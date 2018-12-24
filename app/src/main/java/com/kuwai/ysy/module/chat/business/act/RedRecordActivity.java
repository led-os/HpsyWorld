package com.kuwai.ysy.module.chat.business.act;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.CircleImageView;
import com.allen.library.SuperButton;
import com.kuwai.ysy.R;
import com.kuwai.ysy.common.BaseActivity;
import com.kuwai.ysy.module.chat.adapter.MyFriendsAdapter;
import com.kuwai.ysy.module.chat.adapter.RedRecordAdapter;
import com.kuwai.ysy.module.chat.bean.MyFriends;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.CustomFontTextview;
import com.kuwai.ysy.widget.NavigationLayout;
import com.kuwai.ysy.widget.PasswordInputView;
import com.rayhahah.dialoglib.CustomDialog;
import com.rayhahah.rbase.base.RBasePresenter;
import com.rayhahah.rbase.utils.base.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class RedRecordActivity extends BaseActivity implements View.OnClickListener {

    private TextView mLeftTxt;
    private TextView mTitle;
    private ImageView mRight;
    private CircleImageView mImgHead;
    private TextView mTvName;
    private CustomFontTextview mTvMoney;
    private TextView mTvNum;
    private RecyclerView mRlRecord;

    private RedRecordAdapter recordAdapter;
    private List<MyFriends.DataBean> mDatalist = new ArrayList<>();
    private CustomDialog bottomDialog;

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
        mImgHead = findViewById(R.id.img_head);
        mTvName = findViewById(R.id.tv_name);
        mTvMoney = findViewById(R.id.tv_money);
        mTvNum = findViewById(R.id.tv_num);
        mRlRecord = findViewById(R.id.rl_record);

        recordAdapter = new RedRecordAdapter(mDatalist);
        mRlRecord.setAdapter(recordAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_red:
                // popCostCustom(mEtRedNum.getText().toString());
                break;
            case R.id.left_txt:
                finish();
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
}
