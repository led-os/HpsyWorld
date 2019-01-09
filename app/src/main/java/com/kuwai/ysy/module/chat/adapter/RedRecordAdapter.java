package com.kuwai.ysy.module.chat.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.chat.bean.MyFriends;
import com.kuwai.ysy.module.chat.bean.ReceiveBean;
import com.kuwai.ysy.module.chat.bean.RedRecordBean;
import com.kuwai.ysy.utils.Utils;
import com.kuwai.ysy.widget.TextImageView;
import com.rayhahah.rbase.utils.base.DateTimeUitl;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;


public class RedRecordAdapter extends BaseQuickAdapter<RedRecordBean.DataBean.ArrBean, BaseViewHolder> {
    public RedRecordAdapter() {
        super(R.layout.item_send_red_record);
    }

    @Override
    protected void convert(BaseViewHolder helper, RedRecordBean.DataBean.ArrBean item) {
        helper.setText(R.id.tv_time, DateTimeUitl.timedate(String.valueOf(item.getUpdate_time())));
        helper.setText(R.id.tv_money, item.getMoney() + "桃花币");
        if (Utils.isNullString(item.getNickname())) {
            helper.setText(R.id.red_name, "红包");
            if (item.getStatus() == 1) {
                helper.setText(R.id.tv_statu, "已领完");
            } else {
                helper.setText(R.id.tv_statu, "");
            }
        } else {
            helper.setText(R.id.red_name, item.getNickname());
            helper.setText(R.id.tv_statu, "");
        }

    }

}
