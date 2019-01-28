package com.kuwai.ysy.module.mine.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.circle.bean.CategoryBean;
import com.kuwai.ysy.module.mine.bean.GiftAcceptBean;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;


public class GiftSendAdapter extends BaseQuickAdapter<GiftAcceptBean.DataBean.GiftBean, BaseViewHolder> {


    public GiftSendAdapter() {
        super(R.layout.item_gift_send);
    }

    @Override
    protected void convert(BaseViewHolder helper, GiftAcceptBean.DataBean.GiftBean item) {
        GlideUtil.load(mContext, item.getAvatar(), (ImageView) helper.getView(R.id.img_head));
        helper.setText(R.id.tv_nick, item.getNickname());

        switch (item.getGender()) {
            case 1:
                GlideUtil.load(mContext, R.drawable.ic_user_man, (ImageView) helper.getView(R.id.img_sex));
                break;
            case 2:
                GlideUtil.load(mContext, R.drawable.ic_user_man, (ImageView) helper.getView(R.id.img_sex));
                break;
        }

        switch (item.getIs_vip()) {
            case 0:
                helper.getView(R.id.is_vip).setVisibility(View.GONE);
                break;
            case 1:
                helper.getView(R.id.is_vip).setVisibility(View.VISIBLE);
                break;
        }

        helper.setText(R.id.tv_info, DateTimeUitl.timedate(String.valueOf(item.getCreate_time())));

        GlideUtil.loadRetangle(mContext, item.getGirft_img_url(), (ImageView) helper.getView(R.id.img_gift));

        helper.setText(R.id.gift_name, item.getGirft_name());

        helper.setText(R.id.tv_num, "x" + String.valueOf(item.getG_nums()));


    }

}
