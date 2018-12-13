package com.kuwai.ysy.module.find.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.find.bean.TuoDanBean;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;


public class BlindListAdapter extends BaseQuickAdapter<TuoDanBean.DataBean, BaseViewHolder> {


    public BlindListAdapter() {
        super(R.layout.item_blind);
    }

    @Override
    protected void convert(BaseViewHolder helper, TuoDanBean.DataBean item) {
        helper.setText(R.id.tv_nick, item.getTitle());
        helper.setText(R.id.tv_text, item.getText());
        GlideUtil.load(mContext, item.getAttach(), ((ImageView) helper.getView(R.id.img_pic)));

        switch (item.getStatus()){
            case 1:
                helper.setText(R.id.btn_state,"进行中");
                break;
            case 3:
                helper.setText(R.id.btn_state,"已结束");
                break;
        }

        helper.setText(R.id.tv_num,String.valueOf(item.getEnrolment()));
        helper.addOnClickListener(R.id.rl_activity);


    }

}
