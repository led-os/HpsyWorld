package com.kuwai.ysy.module.chat.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kuwai.ysy.R;
import com.kuwai.ysy.module.chat.bean.MyFriends;
import com.kuwai.ysy.widget.TextImageView;
import com.rayhahah.rbase.utils.useful.GlideUtil;

import java.util.List;


public class RedRecordAdapter extends BaseQuickAdapter<MyFriends.DataBean, BaseViewHolder> {
    public RedRecordAdapter(List data) {
        super(R.layout.item_send_red_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyFriends.DataBean item) {

    }

}
