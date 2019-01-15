package com.kuwai.ysy.module.mine.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.CircleImageView;
import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.module.mine.bean.BlackListBean;
import com.kuwai.ysy.module.mine.bean.PersonalTreeHole;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.useful.GlideUtil;


public class BlackAdapter extends BaseQuickAdapter<BlackListBean.DataBean, BaseViewHolder> {

    public BlackAdapter() {
        super(R.layout.item_black_user);
    }

    @Override
    protected void convert(BaseViewHolder helper, BlackListBean.DataBean item) {
        helper.addOnClickListener(R.id.btn_yichu);
        TextView msg = helper.getView(R.id.tv_msg);
        CircleImageView head = helper.getView(R.id.iv_headicon);
        GlideUtil.load(mContext, item.getAvatar(), head);

        helper.setText(R.id.msg, "操作于:" + DateTimeUitl.getIntegralTime(String.valueOf(item.getCreate_time())));
        helper.setText(R.id.tv_nickname, item.getNickname());

        switch (item.getIs_vip()) {
            case 0:
                helper.getView(R.id.iv_vip).setVisibility(View.GONE);
                break;
            case 1:
                helper.getView(R.id.iv_vip).setVisibility(View.VISIBLE);
                break;
        }

        switch (item.getGender()) {
            case C.Man:
                GlideUtil.load(mContext, R.drawable.ic_user_man, (ImageView) helper.getView(R.id.iv_sex));
                break;
            case C.Woman:
                GlideUtil.load(mContext, R.drawable.ic_user_woman, (ImageView) helper.getView(R.id.iv_sex));
                break;
        }
    }

}
