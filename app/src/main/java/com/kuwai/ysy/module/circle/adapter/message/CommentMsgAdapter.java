package com.kuwai.ysy.module.circle.adapter.message;

import android.view.View;
import android.widget.ImageView;

import com.allen.library.CircleImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.app.C;
import com.kuwai.ysy.module.circle.bean.AllCommentBean;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;


public class CommentMsgAdapter extends BaseQuickAdapter<AllCommentBean.DataBean, BaseViewHolder> {


    public CommentMsgAdapter() {
        super(R.layout.item_dy_message_comment);
    }

    @Override
    protected void convert(BaseViewHolder helper, AllCommentBean.DataBean item) {

        CircleImageView head = helper.getView(R.id.img_head);
        GlideUtil.load(mContext, item.getAvatar(), head);
        GlideUtil.load(mContext, item.getAttach(), (ImageView) helper.getView(R.id.img_gift));
        helper.setText(R.id.tv_msg, DateTimeUitl.getStandardDate(String.valueOf(item.getCreate_time())));
        helper.setText(R.id.tv_nick, item.getNickname());
        helper.setText(R.id.content, item.getC_text());

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
