package com.kuwai.ysy.module.chat.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.chat.bean.NoticeBean;
import com.kuwai.ysy.module.chat.bean.NoticeDateBean;
import com.kuwai.ysy.widget.TextImageView;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.useful.GlideUtil;


public class NoticeDateAdapter extends BaseQuickAdapter<NoticeDateBean.DataBean, BaseViewHolder> {


    public NoticeDateAdapter() {
        super(R.layout.item_notice_date);
    }

    @Override
    protected void convert(BaseViewHolder helper, NoticeDateBean.DataBean item) {
        ImageView vipImg = helper.getView(R.id.img_vip);
        ImageView sexImg = helper.getView(R.id.img_sex);
        helper.setText(R.id.tv_nick, item.getNickname());
        GlideUtil.load(mContext,item.getAvatar(), (TextImageView) helper.getView(R.id.img_head));
        helper.setText(R.id.tv_time, DateTimeUitl.timedate(String.valueOf(item.getCreate_time())));
        helper.setText(R.id.tv_sign, item.getContent());
        vipImg.setVisibility(item.getIs_vip() == 1 ? View.VISIBLE : View.GONE);
        if (item.getGender() == 1) {
            sexImg.setImageResource(R.drawable.ic_user_man);
        } else if (item.getGender() == 2) {
            sexImg.setImageResource(R.drawable.ic_user_woman);
        }
    }

}
