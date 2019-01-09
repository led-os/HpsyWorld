package com.kuwai.ysy.module.chat.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.chat.bean.RedMySendBean;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.useful.GlideUtil;


public class RenSendMineAdapter extends BaseQuickAdapter<RedMySendBean.DataBean.ArrBean, BaseViewHolder> {
    public RenSendMineAdapter() {
        super(R.layout.item_red_mine_send);
    }

    @Override
    protected void convert(BaseViewHolder helper, RedMySendBean.DataBean.ArrBean item) {
        GlideUtil.load(mContext, item.getAvatar(), (ImageView) helper.getView(R.id.img_head));
        helper.setText(R.id.tv_time, DateTimeUitl.timedate(String.valueOf(item.getUpdate_time())));
        helper.setText(R.id.tv_nick, item.getNickname());
        helper.setText(R.id.tv_money, item.getMoney() + "桃花币");

    }

}
