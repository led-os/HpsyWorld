package com.kuwai.ysy.module.mine.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allen.library.SuperButton;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.mine.bean.GiftAcceptBean;
import com.kuwai.ysy.module.mine.bean.MyAskBean;
import com.kuwai.ysy.utils.Utils;
import com.rayhahah.rbase.utils.useful.GlideUtil;


public class QuestionAdapter extends BaseQuickAdapter<MyAskBean.DataBean, BaseViewHolder> {


    public QuestionAdapter() {
        super(R.layout.item_chat_question);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyAskBean.DataBean item) {
        SuperButton addbtn = helper.getView(R.id.btn_add_answer);
        TextView content = helper.getView(R.id.tv_content);
        helper.addOnClickListener(R.id.btn_add_answer);
        helper.addOnClickListener(R.id.img_arrow);

        GlideUtil.load(mContext, Utils.isNullString(item.getAnswer()) ? R.drawable.ic_list_unselect : R.drawable.ic_list_select, (ImageView) helper.getView(R.id.img_select));
        helper.setText(R.id.tv_title, item.getProblem());
        if (!Utils.isNullString(item.getAnswer())) {
            content.setVisibility(View.VISIBLE);
            addbtn.setVisibility(View.GONE);
            helper.setText(R.id.tv_content, String.valueOf(item.getAnswer()));
        } else {
            content.setVisibility(View.GONE);
            addbtn.setVisibility(View.VISIBLE);
        }

    }

}
